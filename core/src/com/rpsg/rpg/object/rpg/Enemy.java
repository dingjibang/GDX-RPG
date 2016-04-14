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
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.Resistance.ResistanceType;
import com.rpsg.rpg.object.base.items.BattleContext;
import com.rpsg.rpg.object.base.items.EnemyContext;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Prop;
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

	public static Enemy getEnemy(int id,JsonValue value) {
		Enemy enemy = new Enemy();
		enemy.name = value.getString("name");
		enemy.target.prop.putAll(ItemController.getIntProp(value.get("prop")));;
		enemy.aiLevel = value.has("aiLevel") ? value.getInt("aiLevel") : 1;
		enemy.id = id;
		enemy.imgPath = Setting.IMAGE_ENEMY + id + ".png";
		Map<String,Resistance> rmap = new HashMap<>();
		
		if(value.has("resistance")) for(JsonValue r : value.get("resistance"))
			rmap.put(r.name(), new Resistance(ResistanceType.valueOf(r.asString()), 0));
		
		enemy.target.resistance.putAll(rmap);
		
		List<EnemyAction> actions = new ArrayList<>();
		
		for(JsonValue actionValue : value.get("action")){
			EnemyAction action = new EnemyAction();
			action.propbabitly = actionValue.getInt("probability");
			action.act = RPG.ctrl.item.get(actionValue.getInt("act"), Spellcard.class);
			action.remove = actionValue.has("remove") ? RemoveType.valueOf(actionValue.getString("remove")) : RemoveType.no;
		}
		
		enemy.actions = actions;
		return enemy;
	}
	
	private static JsonValue getJSON(int id){
		return reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_ENEMY + id + ".grd").readString());
	}
	
	public int getSpeed(){
		return Integer.valueOf(target.prop.get("speed"));
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
	public void act(BattleContext battleContext) {
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
		
		EnemyAction action = null;
		//如果有多个待选动作， 则随机选择一个动作
		if(!evalActionList.isEmpty())
			action = evalActionList.get(MathUtils.random(0,evalActionList.size()));
		
		//如果没有任何可选动作，则根据AI等级使用攻击/防御的动作
		if(action == null)
			action = random(getDefenseRate()) ? EnemyAction.defense() : EnemyAction.attack();
			
		//如果符卡指向队友，或符卡不是指向单人的，则不进行判定，直接使用符卡
		if(action.act.range != ItemRange.one || action.act.forward == ItemForward.friend){
			action.act.use(battleContext);
			return;
		}
		
		//获取团队平均数值
		int avgHP = Target.avg(enemies, "hp");
		int avgAtk = Target.avg(enemies, "attack");
		int avgDef = Target.avg(enemies, "defence");
		
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
				switch(t.resistance.get(prop.type).type){
					case normal : break;
					case weak : t.rank *= 1.5f;
					case tolerance : t.rank *= .8f;
					case invalid : t.rank *= .3f;
					case reflect : t.rank *= .95f;
					case absorb : t.rank *= .4f;
				}
			}
		};
		
		Collections.sort(enemies, (t1,t2) -> t1.rank - t2.rank);
		
		Target enemy = null;
		//根据AI等级和目标评分来计算攻击目标
		switch(aiLevel){
			case 0:{//傻逼 - 选择最难对付的对手
				enemy = enemies.get(enemies.size() - 1);
			}
			case 1:{//正常 - 随机选择一个对手
				enemy = enemies.get(MathUtils.random(0,enemies.size() - 1));
			}
			case 2:{//聪明 - 50%几率选择一个稍微比较好对付的对手
				enemy = MathUtils.random(0,100) > 50 ? enemies.get(MathUtils.random(0,enemies.size() / 2)) : enemies.get(MathUtils.random(0,enemies.size() - 1));
			}
			case 3:{//挂比 - 80%几率选择最好对付的对手
				enemy = MathUtils.random(0,100) > 80 ? enemies.get(0) : enemies.get(MathUtils.random(0,enemies.size() - 1));
			}
		}
		
		GameViews.gameview.battleView.status.add("攻击了" + enemy.parentHero.name + "");
		action.act.use(battleContext.target(enemy));
			
	}
	
	public boolean random(int val){
		return MathUtils.random(0,100) <= val;
	}
	
	/**
	 * 根据AI等级进行计算是否攻击/防御
	 * @return
	 */
	public int getDefenseRate(){
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
