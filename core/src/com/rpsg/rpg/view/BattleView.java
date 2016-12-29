package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.object.base.BattleRes;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.CallbackBuff;
import com.rpsg.rpg.object.base.items.EffectBuff;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Result;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Selectable;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.system.base.BattleFilter;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Animations;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.EnemyGroup;
import com.rpsg.rpg.system.ui.HeroGroup;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Progress;
import com.rpsg.rpg.system.ui.Status;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.Timer;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.hover.BattleStopView;
import com.rpsg.rpg.view.hover.SelectItemView;
import com.rpsg.rpg.view.hover.SelectSpellcardView;

public class BattleView extends DefaultIView{
	
	public BattleParam param;
	public HeroGroup heroGroup;
	public EnemyGroup enemyGroup;
	public Status status;
	Animations animations = new Animations(this);
	public Timer timer;
	Progress p;
	boolean createBattlStopView = false;
	
	public BattleView(BattleParam param) {
		this.param = param;
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.stage_width, GameUtil.stage_height, new OrthographicCamera()));
	}
	
	@Override
	public BattleView init() {
		stage.clear();
		stage.setDebugAll(Setting.persistence.uiDebug);
		createBattlStopView = false;
		$.add(Res.base().size(1024,576).color(.5f,.5f,.5f,1)).appendTo(stage);//TODO debug;
		
		$.add(status = new Status()).setPosition(0, 0).appendTo(stage);
		
		ArrayList<Hero> heros = RPG.ctrl.hero.currentHeros();
		
		heroGroup = new HeroGroup(heros);
		$.add(heroGroup).appendTo(stage).setPosition(0, 0);
		
		enemyGroup = new EnemyGroup(param.enemy);
		$.add(enemyGroup).appendTo(stage).setPosition(GameUtil.stage_width/2 - enemyGroup.getWidth()/2, GameUtil.stage_height/2 - enemyGroup.getHeight()/2 + 50).setAlign(Align.center);
		$.add(new TextButton("结束战斗！",BattleRes.textButtonStyle)).appendTo(stage).setPosition(100,170).click(RPG.ctrl.battle::stop);
		
		$.add(timer = new Timer(heros, enemyGroup.list(), this::onTimerToggle, this::onBattleStop)).appendTo(stage);
		
		status.add("fuck you");
		
		$.add(Res.base().size(GameUtil.stage_width, GameUtil.stage_height).color(0,0,0,1)).appendTo(stage).addAction(Actions.sequence(Actions.fadeOut(.3f,Interpolation.pow2In),Actions.removeActor()));
		
		status.setZIndex(999999);
		this.
		stage.addActor(animations);
		
		return this;
	}
	
	//因为停止战斗消息是战斗结束后，持续每帧发送的，为了防止建多个view，所以使用布尔变量进行判断
	 public void onBattleStop(){
		 if(createBattlStopView) return;
		 createBattlStopView = false;
		 RPG.popup.add(BattleStopView.class,$.omap("view", this).add("callback", (Runnable)RPG.ctrl.battle::stop));
	 }
	
	 /**
	  *	当触发某个角色的回合时，obj为{@link Hero}或{@link Enemy} 
	  */
	public void onTimerToggle(Object obj){
		//暂停战斗
		timer.pause(true);
		//获取obj名称
		String name = obj instanceof Enemy ? ((Enemy)obj).name : ((Hero)obj).name;
		//获取obj的target
		Target target = Target.parse(obj);
		
		
		//生成战斗结束的后处理方法
		Runnable end = () -> {
			timer.pause(false);
			target.nextTurn();
		};
		
		//添加说明到玩家屏幕
		status.add("[#ff7171]"+name+"[] 的战斗回合");
		
		//遍历触发对象的所有CallbackBuff
		List<CallbackBuff> buffList = CallbackBuff.nextTurn(target);
		if(buffList.size() != 0){
			new Runnable() {
				public void run() {
					
					if(buffList.size() == 0){
						end.run();
						return;
					}
					
					CallbackBuff buff = buffList.get(0);
					buffList.remove(buff);
					Result result = buff.callback().run();
					animations.play(result, ()->{
						checkDead();
						this.run();
					});
				}
			}.run();
			return;
		}
		
		//-------		拦截器：onTurnBegin
		for(EffectBuff ebuff : target.getBuffList()){
			if(ebuff.buff.filter != null && ebuff.buff.filter.onTurnBegin != null){
				List<?> friend = obj instanceof Hero ? RPG.ctrl.hero.currentHeros() : enemyGroup.list();
				List<?> enemies = obj instanceof Hero ? enemyGroup.list() : RPG.ctrl.hero.currentHeros();
				Map<String, Object> result = BattleFilter.exec(ebuff.buff.filter.onTurnBegin, Item.Context.battle(target, null, friend, enemies));
				if(result.containsKey("flag") && !(Boolean.valueOf(result.get("flag").toString()))){
					TimeUtil.add(end, 1000);
					return;
				}
			}
			//		BattleFilter.exec(js, context)
		}
		//-------END	拦截器：onTurnBegin
		
		
		//如果有Lock状态的buff（也就是触发这个buff时候不能做别的事情），则结束战斗
		if(CallbackBuff.hasLockedBuff(target)){
			TimeUtil.add(end, 1000);
			status.add(Target.name(target) + "在持续吟唱中……");
			return;
		}
		
		//如果触发对象为Hero
		if(obj instanceof Hero){
			Hero hero = (Hero)obj;
			
			//------画出UI start
			Image fg = $.add(hero.defaultFG()).appendTo(stage).setScaleX(-0.33f).setScaleY(0.33f).setOrigin(Align.bottomLeft).setPosition(GameUtil.stage_width+500, 0).addAction(Actions.moveBy(-400, 0,1f,Interpolation.pow4Out)).setZIndex(1).getItem(Image.class);
			Table menu = $.add(new Table()).appendTo(stage).setPosition(600, 220).getItem(Table.class);
			//------画出UI end
			
			//生成战斗结束的后处理方法
			Runnable stopCallback = ()->{
				checkDead();
				fg.remove();
				menu.remove();
				end.run();
			};
			
			//画出菜单
			menu.add(new TextButton("攻击",BattleRes.textButtonStyle).onClick(() -> attack(hero,stopCallback)));
			
			menu.add(new TextButton("防御",BattleRes.textButtonStyle).onClick(() -> defense(hero,stopCallback)));
			
			menu.add(new TextButton("符卡",BattleRes.textButtonStyle).onClick(() -> spellcard(hero,stopCallback)));
			
			menu.add(new TextButton("物品",BattleRes.textButtonStyle).onClick(() -> item(hero,stopCallback)));
			
			menu.add(new TextButton("逃跑",BattleRes.textButtonStyle).onClick(() ->
				escape(hero,flag -> { 
					stopCallback.run();
					if(flag) 
						RPG.ctrl.battle.stop();
					else 
						timer.addDelay(hero, 10);
				})
			));
			
			$.each(menu.getCells(), cell -> cell.size(150,30));
			
			//如果触发者为Enemy
		}else{
			Enemy enemy = (Enemy)obj;
			//使Enemy根据自身AI进行行动
			Result result = enemy.act(Item.Context.battle(enemy, null, (List<?>) enemyGroup.list().clone(), (List<?>) RPG.ctrl.hero.currentHeros.clone()));
			//播放AI执行后的行动动画
			animations.play(result, end);
		}
	}
	
	//执行逃跑，Hero为执行逃跑的角色，callback为逃跑（无论失败与否）执行完毕的回调
	public void escape(Hero hero, CustomRunnable<Boolean> callback){
		double random = Math.random();
		boolean flag = random > .5;
		status.add(hero.getName()+" 尝试逃跑").append(".",5).append(".",10).append(".",15).append(flag ? "成功了" : "但是失败了", 40);
		TimeUtil.add(()->callback.run(flag), 1000);
	}
	
	//执行防御，Hero为执行防御的角色，callback为防御动画播放完毕后的回调
	private void defense(Hero hero, Runnable callback){
		useSpellcard(Spellcard.defense(),hero,null,()->{
			timer.addDelay(hero, hero.target.getProp("defenseDelay"));
			callback.run();
		});
	}
	
	//执行攻击，Hero为执行防御的角色，callback为攻击动画播放完毕后的回调
	private void attack(Hero hero,Runnable callback){
		enemyGroup.select(target -> useSpellcard(hero.target.attack, hero, target, () -> {
			timer.addDelay(hero, hero.target.getProp("attackDelay"));
			callback.run();
		}),ItemDeadable.no);
	}

	//调出选择符卡菜单，Hero为执行者，stopCallback为使用符卡后的回调
	private void spellcard(Hero hero, Runnable stopCallback) {
		//打开选择符卡菜单
		RPG.popup.add(SelectSpellcardView.class,$.omap("hero",hero).add("callback", /*当选择符卡后的回调，其中传给回调者玩家所选择的符卡*/(CustomRunnable<Spellcard>)sc -> {
			//如果符卡的范围为个体，则调出选择个体的界面
			//TODO 需要制作当玩家取消选择时的回调
			if(sc.range == ItemRange.one)
				getGroup(sc.forward,hero).select(/*选择界面完成后，回调变量为target*/target -> useSpellcard(sc,hero,target,stopCallback),sc.deadable);
			else
				useSpellcard(sc,hero,null,stopCallback);
		}));
	}
	
	//同符卡
	private void item(Hero hero,Runnable stopCallback){
		RPG.popup.add(SelectItemView.class,$.omap("hero",hero).add("callback", (CustomRunnable<Item>)item -> {
			if(item.range == ItemRange.one)
				getGroup(item.forward,hero).select(target -> useItem(item,hero,target,stopCallback),item.deadable);
			else
				useItem(item,hero,null,stopCallback);
		}));
	}
	
	/**
	 * 使用一个道具
	 * @param item 道具
	 * @param hero 使用者
	 * @param target 被使用者
	 * @param callback 使用完成后的回调
	 */
	private void useItem(Item item,Hero hero,Target target,Runnable callback){
		status.add(Target.name(target) + " 使用了道具『" + item.name + "』");
		Result result = item.use(BaseItem.Context.battle(hero, target, RPG.ctrl.hero.currentHeros(), enemyGroup.list()));
		animations.play(result, callback);
	}
	
	/**
	 * 检查是否有人死亡，以更新状态
	 */
	private void checkDead(){
		$.getIf(enemyGroup.list(), e -> e.target.isDead(), e -> {
			status.add(e.name + "已死亡");
			enemyGroup.remove(e);
			timer.remove(e);
		});
	}
	
	/**
	 * 调出选择单位的UI，以选择单位
	 * @param forward 选择朝向（我方还是敌方）
	 * @param hero 选择者
	 */
	private Selectable getGroup(ItemForward forward, Hero hero){
		if(forward == ItemForward.enemy)
			return enemyGroup;
		if(forward == ItemForward.self)
			return (onSelect,deadable) -> onSelect.run(hero.target);
		return heroGroup; 
	}
	
	private void useSpellcard(Spellcard sc,Object hero,Object target,Runnable onUsed){
		use(null,sc,hero,target,onUsed);
	}
	
	/**
	 * 使用一张附卡
	 * @param _txt 打印在玩家屏幕上的说明
	 * @param sc 符卡
	 * @param _hero 使用者（也有可能是敌人）
	 * @param target 被使用者
	 * @param onUsed 当使用完成后的回调
	 */
	private void use(String _txt,Spellcard sc,Object _hero,Object target,Runnable onUsed){
		String text = _txt;
		String tname = Target.name(target);
		String hname = Target.name(_hero);
		if(text == null)
			if(Spellcard.isAttack(sc)) 
				text = hname + " 攻击了 " + tname;
			else if(Spellcard.isDefense(sc)) 
				text = hname + "展开了防御";
			else 
				text = hname + " 对 " + tname + " 使用符卡『 " + sc.name + "』"; 
			
		status.add(text);
		Result result = sc.use(Item.Context.battle(_hero, target, RPG.ctrl.hero.currentHeros(), enemyGroup.list()));
		animations.play(result, onUsed);
	}
	
	@Override
	public void draw(SpriteBatch batch) {
		logic();
		stage.draw();
	}

	@Override
	public void logic() {
		stage.act();
	}
	
	@Override
	public void onkeyDown(int keyCode) {
		if(keyCode == Keys.R) init();
		if(keyCode == Keys.S) {
//			onBattleStop();
		}
		if(keyCode == Keys.D) status.append(" & "+Math.random());
		if(keyCode == Keys.F) status.append("[#ffaabb]彩色测试[]");
		if(keyCode == Keys.P) {
			RPG.ctrl.hero.currentHeros.get(0).target.setProp("hp", MathUtils.random(0,100));
			RPG.ctrl.hero.currentHeros.get(0).target.setProp("mp", MathUtils.random(0,100));
			RPG.ctrl.hero.currentHeros.get(1).target.setProp("hp", MathUtils.random(0,100));
			RPG.ctrl.hero.currentHeros.get(1).target.setProp("mp", MathUtils.random(0,100));
		}
		super.onkeyDown(keyCode);
	}

}
