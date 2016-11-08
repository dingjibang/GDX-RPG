package com.rpsg.rpg.object.rpg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.EnemyContext;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Prop;
import com.rpsg.rpg.object.base.items.Result;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.EnemyAction.RemoveType;
import com.rpsg.rpg.system.controller.ItemController;
import com.rpsg.rpg.view.GameViews;

public class Enemy implements Time {
	public int id;
	public String imgPath;
	public String name;
	public Integer aiLevel;
	public List<EnemyAction> actions;
	public Color color;
	private int no = 0;
	public int rank = 0;
	public int exp;
	public List<EnemyDrop> drop;
	
	
	public Target target = new Target().enemy(this);
	
	public Enemy() {
		color = new Color(MathUtils.random(.4f,.8f),MathUtils.random(.4f,.8f),MathUtils.random(.4f,.8f),1);
	}
	
	
	static JsonReader reader = new JsonReader();

	public static List<Enemy> get(int id) {
		List<Enemy> list = new ArrayList<Enemy>();
		JsonValue value = getJSON(id);
		
		String enemyType = value.getString("type");
		
		if(enemyType.equalsIgnoreCase("simple"))
			list.add(getEnemy(id,value));
		else
			for(JsonValue enemyId : value.get("group"))
				list.add(getEnemy(enemyId.asInt(),getJSON(enemyId.asInt())));
		
		return list;
	}
	
	public static List<Enemy> name(List<Enemy> list) {
		List<Integer> ids = new ArrayList<>();
		
		for(Enemy enemy : list){
			boolean include = false;
			for(int i:ids) if(enemy.id == i) include = true;
			if(!include){
				int count = 0;
				enemy.name += ++count;
				enemy.no = count;
				for(Enemy child : list)
					if(child != enemy && child.id == enemy.id)
						child.name += (child.no = ++count);
				
				ids.add(enemy.id);
			}
		}
		
		return list;
	}

	public static Enemy getEnemy(int id, JsonValue value) {
		Enemy enemy = new Enemy();
		enemy.name = value.getString("name");
		enemy.target.getProp().putAll(ItemController.getIntProp(value.get("prop")));;
		enemy.aiLevel = value.has("aiLevel") ? value.getInt("aiLevel") : 1;
		enemy.id = id;
		enemy.imgPath = Setting.IMAGE_ENEMY + id + ".png";
		enemy.exp = value.has("exp") ? value.getInt("exp") : 0;
		Map<String, Float> rmap = new HashMap<>();
		
		if(value.has("resistance")) for(JsonValue r : value.get("resistance"))
			rmap.put(r.name(), r.asFloat());
		
		enemy.target.resistance.putAll(rmap);
		
		List<EnemyAction> actions = new ArrayList<>();
		
		for(JsonValue actionValue : value.get("action")){
			EnemyAction action = new EnemyAction();
			action.propbabitly = actionValue.getInt("probability");
			action.act = RPG.ctrl.item.get(actionValue.getInt("act"), Spellcard.class);
			action.remove = actionValue.has("remove") ? RemoveType.valueOf(actionValue.getString("remove")) : RemoveType.no;
		}
		
		enemy.actions = actions;
		
		enemy.drop = new ArrayList<>();
		
		if(value.has("drop")) for(JsonValue v : value.get("drop"))
			enemy.drop.add(new EnemyDrop(v.getInt("item"), v.getInt("rate")));
		
		return enemy;
	}
	
	public List<EnemyDrop> getDrop(){
		List<EnemyDrop> drop = new ArrayList<>();
		for(EnemyDrop d : this.drop)
			if(MathUtils.random(0,100) < d.rate)
				drop.add(d);
		
		return drop;
	}
	
	public static int getExp(List<Enemy> list){
		int exp = 0;
		for(Enemy e : list) exp += e.exp;
		return exp;
	}
	
	private static JsonValue getJSON(int id){
		return reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_ENEMY + id + ".grd"));
	}
	
	public int getSpeed(){
		return Integer.valueOf(target.getProp("speed"));
	}
	
	@Override
	public String toString() {
		return "Enemy:"+name;
	}
	
	@Override
	public Color getObjectColor() {
		return color;
	}
	
	@Override
	public String getSimpleName() {
		String name = this.name.substring(0, 1);
		if(this.no != 0) name+= this.no;
		return name;
	}
	
	@Override
	public Object getThis() {
		return this;
	}

	/**
	 * 	AI核心算法
	 */
	public Result act(final Item.Context battleContext) {
		//获取战斗上下文
		List<Target> friend = battleContext.friend;
		List<Target> enemies = battleContext.enemies;
		
		//遍历动作
		List<EnemyAction> evalActionList = new ArrayList<>();
		
		for(EnemyAction action : actions){
			//根据符卡使用朝向，得到判定目标
			List<Target> targetList = new ArrayList<>();
			if(action.act.forward == ItemForward.friend) targetList = friend;
			if(action.act.forward == ItemForward.enemy) targetList = enemies;
			
			for(Target t : targetList)
				if(EnemyContext.eval(this.target, t, friend, enemies, action) && random(action.propbabitly))
					evalActionList.add(action);
		}
		
		//如果没有任何可选动作，则根据AI等级使用攻击/防御的动作
		if(evalActionList.isEmpty())
			evalActionList.add(random(getDefenseRate()) ? EnemyAction.defense() : EnemyAction.attack());
		
		
		EnemyAction evalAction = null;
		//遍历所有动作，取得最优动作
		for(EnemyAction action : evalActionList){
			if(action.act.forward == ItemForward.enemy)
				action.rank = rankEnemy(action,enemies);
			if(action.act.forward == ItemForward.self || action.act.forward == ItemForward.friend)
				action.rank = rankFriend(action,friend);
			if(action.act.forward == ItemForward.all)
				action.rank = 0;
			
			//判断cost值是否足够
			int cost = action.act.cost;
			if(cost < target.getProp("mp"))
				action.rank = Integer.MIN_VALUE;
			
			if(evalAction == null || action.rank > evalAction.rank)
				evalAction = action;
		}
		
		
		//如果符卡指向多人则直接使用
		if(evalAction.act.range == ItemRange.all){
			return evalAction.act.use(battleContext);
		}
		
		//如果指向队友
		if(evalAction.act.forward == ItemForward.friend){
			rankFriend(evalAction,friend);
			Target target = getTarget(friend);
			return evalAction.act.use(battleContext.target(target));
		}else{//如果指向敌人
			rankEnemy(evalAction,enemies);
			Target target = getTarget(enemies);
			GameViews.gameview.battleView.status.add("攻击了" + target.parentHero.name);
			return evalAction.act.use(battleContext.target(target));
		}
	}
	
	private int rankFriend(EnemyAction action,List<Target> friend){
		//获取团队平均数值
		int avgHP = Target.avg(friend, "hp");
		
		int sum = 0;
		
		for(Target t : friend){
			t.rank = 0;
			
			t.rank += avgHP - t.getProp("hp");
			t.rank *= t.isDying() ? 1.3f : 0;
		}
		
		return sum * 5;
	}
	
	/**
	 * 评分系统
	 * //TODO
	 * 评分系统改进：
	 * aoe情况下秒奶妈
	 * 自身血厚攻击dps
	 * 攻击多回合施法 TODO 多回合施法
	 * 直接死亡 加分
	 * 自己没蓝了，减分（0分）
	 * 根据速度闪避rank
	 * 
	 * js控制额外的评分
	 * 
	 * @return 分数总和
	 */
	private int rankEnemy(EnemyAction action,List<Target> enemies){

		//获取团队平均数值
		int avgHP = Target.avg(enemies, "hp");
		int avgAtk = Target.avg(enemies, "attack");
		int avgDef = Target.avg(enemies, "defence");
		
		int sum = 0;
		
		//对目标进行评分
		for(Target t : enemies){
			//清理冗余评分
			t.rank = 0;
			
			//根据对自身威胁程度进行加分
			t.rank += avgHP - t.getProp("hp");
			t.rank += avgAtk - t.getProp("attack") * 4;
			t.rank -= avgDef - t.getProp("defence") * 2;
			
			//根据抗性进行加分
			for(String key : action.act.effect.prop.keySet()){
				Prop prop = action.act.effect.prop.get(key);
				if(prop.type == null || prop.type.length() == 0)
					continue;
				t.rank *= t.resistance.get(prop.type);
			}
			
			//可以秒杀对方进行加分
			t.rank *= Spellcard.damage(action.act.effect, target, t, "hp") > 0 ? 1 : 2;
			
			t.rank += t.getProp("rank");
			
			sum += t.rank;
		}
		
		Collections.sort(enemies, (t1,t2) -> t2.rank - t1.rank);
		return sum;
	}
	
	/**
	 * 根据AI等级和目标评分来计算攻击目标
	 */
	private Target getTarget(List<Target> sortedTargetList){
		Target enemy = null;
		switch(aiLevel){
		case 0:{//傻逼 - 选择最难对付的对手
			enemy = getTargetByRate(sortedTargetList, 10);
			break;
		}
		case 1:{//正常 - 随机选择一个对手
			enemy = getTargetByRate(sortedTargetList, 40);
			break;
		}
		case 2:{//聪明 - 50%几率选择一个稍微比较好对付的对手
			enemy = getTargetByRate(sortedTargetList, 60);
			break;
		}
		case 3:{//挂比 - 80%几率选择最好对付的对手
			enemy = getTargetByRate(sortedTargetList, 80);
			break;
		}
		}
		return enemy;
	}
	
	private Target getTargetByRate(List<Target> sortedTargetList, int rate){
		Target target = null;
		
		for(int i=0; i<sortedTargetList.size(); i++){
			Target _t = sortedTargetList.get(i);
			
			if(MathUtils.random(0,100) < rate + _t.getProp("rankRate")){
				target = _t; 
				break;
			}
			
			if(i == sortedTargetList.size() - 1)
				target = _t;
		}
		
		return target;
	}
	
	private boolean random(int val){
		return MathUtils.random(0,100) <= val;
	}
	
	/**
	 * 根据AI等级进行计算是否攻击/防御
	 * @return
	 */
	private int getDefenseRate(){
		switch (aiLevel){
			case 0:{//傻逼
				return 0;//永远不会防御
			}
			case 1:{//正常
				return 5;//5%几率防御
			}
			case 2:{//聪明
				if((float)target.getProp("maxhp") * .2f > target.getProp("hp"))//血量不足20%时会增大防御的机会
					return 20;
				return 5;
			}
			case 3:{//挂比
				if((float)target.getProp("maxhp") * .2f > target.getProp("hp"))//血量不足20%时会增大防御的机会
					return 20;
				return 0;//否则持续攻击
			}
		}
		return 0;
	}
	
}
