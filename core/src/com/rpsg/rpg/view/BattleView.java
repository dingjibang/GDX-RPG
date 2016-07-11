package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;

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
	Timer timer;
	Progress p;
	boolean createBattlStopView = false;
	
	public BattleView(BattleParam param) {
		this.param = param;
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.stage_width, GameUtil.stage_height, new OrthographicCamera()));
	}
	
	@Override
	public BattleView init() {
		stage.clear();
		createBattlStopView = false;
		$.add(Res.base().size(1024,576).color(.5f,.5f,.5f,1)).appendTo(stage);//TODO debug;
		
		$.add(status = new Status()).setPosition(0, 0).appendTo(stage);
		
		ArrayList<Hero> heros = RPG.ctrl.hero.currentHeros();
		
		heroGroup = new HeroGroup(heros);
		$.add(heroGroup).appendTo(stage).setPosition(0, 0);
		
		enemyGroup = new EnemyGroup(param.enemy);
		$.add(enemyGroup).appendTo(stage).setPosition(GameUtil.stage_width/2 - enemyGroup.getWidth()/2, GameUtil.stage_height/2 - enemyGroup.getHeight()/2 + 50).setAlign(Align.center);
		$.add(new TextButton("结束战斗！",BattleRes.textButtonStyle)).appendTo(stage).setPosition(100,170).click(RPG.ctrl.battle::stop);
		
		$.add(timer = new Timer(heros,enemyGroup.list(),this::onTimerToggle,this::onBattleStop)).appendTo(stage);
		
		status.add("fuck you");
		stage.setDebugAll(!!false);
		
		$.add(Res.base().size(GameUtil.stage_width,GameUtil.stage_height).color(0,0,0,1)).appendTo(stage).addAction(Actions.sequence(Actions.fadeOut(.3f,Interpolation.pow2In),Actions.removeActor()));
		
		status.setZIndex(999999);
		this.
		stage.addActor(animations);
		
		return this;
	}
	
	 public void onBattleStop(){
		 if(createBattlStopView) return;
		 createBattlStopView = !createBattlStopView;
		 RPG.popup.add(BattleStopView.class,$.omap("view", this).add("callback", (Runnable)RPG.ctrl.battle::stop));
	 }
	
	public void onTimerToggle(Object obj){
		timer.pause(true);
		String name = obj instanceof Enemy ? ((Enemy)obj).name : ((Hero)obj).name;
		Target target = Target.parse(obj);
		
		target.nextTurn();
		
		status.add("[#ff7171]"+name+"[] 的战斗回合");
		
		List<CallbackBuff> buffList = CallbackBuff.nextTurn(target);
		if(buffList.size() != 0){
			new Runnable() {
				public void run() {
					
					if(buffList.size() == 0){
						timer.pause(false);
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
		
		if(CallbackBuff.hasLockedBuff(target)){
			TimeUtil.add(()->timer.pause(false), 1000);
			status.add(Target.name(target) + "在持续吟唱中……");
			return;
		}
		
		if(obj instanceof Hero){
			Hero hero = (Hero)obj;
			
			Image fg = $.add(hero.defaultFG()).appendTo(stage).setScaleX(-0.33f).setScaleY(0.33f).setOrigin(Align.bottomLeft).setPosition(GameUtil.stage_width+500, 0).addAction(Actions.moveBy(-400, 0,1f,Interpolation.pow4Out)).setZIndex(1).getItem(Image.class);
			Table menu = $.add(new Table()).appendTo(stage).setPosition(600, 220).getItem(Table.class);
			
			Runnable stopCallback = ()->{
				checkDead();
				fg.remove();
				menu.remove();
				timer.pause(false);
			};
			
			menu.add(new TextButton("攻击",BattleRes.textButtonStyle).onClick(()->{
				attack(hero,stopCallback);
			}));
			
			menu.add(new TextButton("防御",BattleRes.textButtonStyle).onClick(()->{
				define(hero,stopCallback);
			}));
			
			menu.add(new TextButton("符卡",BattleRes.textButtonStyle).onClick(()->{
				spellcard(hero,stopCallback);
			}));
			
			menu.add(new TextButton("物品",BattleRes.textButtonStyle).onClick(()->{
				item(hero,stopCallback);
			}));
			
			menu.add(new TextButton("逃跑",BattleRes.textButtonStyle).onClick(()->{
				escape(hero,flag -> { 
					stopCallback.run();
					if(flag) RPG.ctrl.battle.stop();
				});
			}));
			
			$.each(menu.getCells(), cell -> cell.size(150,30));
		}else{
			Enemy enemy = (Enemy)obj;
			Result result = enemy.act(Item.Context.battle(enemy, null, (List<?>) enemyGroup.list().clone(), (List<?>) RPG.ctrl.hero.currentHeros.clone()));
			animations.play(result,() -> timer.pause(false));
		}
	}
	
	public void escape(Hero hero,CustomRunnable<Boolean> callback){
		double random = Math.random();
		boolean flag = random > .5;
		status.add(hero.getName()+" 尝试逃跑").append(".",5).append(".",10).append(".",15).append(flag ? "成功了" : "但是失败了",40);
		TimeUtil.add(()->callback.run(flag),1000);
	}
	
	private void define(Hero hero,Runnable callback){
		useSpellcard(Spellcard.defense(),hero,null,callback);
	}
	
	private void attack(Hero hero,Runnable callback){
		enemyGroup.select(target -> useSpellcard(Spellcard.attack(),hero,target,callback),ItemDeadable.no);
	}

	private void spellcard(Hero hero, Runnable stopCallback) {
		RPG.popup.add(SelectSpellcardView.class,$.omap("hero",hero).add("callback", (CustomRunnable<Spellcard>)sc -> {
			if(sc.range == ItemRange.one)
				getGroup(sc.forward,hero).select(target -> useSpellcard(sc,hero,target,stopCallback),sc.deadable);
			else
				useSpellcard(sc,hero,null,stopCallback);
		}));
	}
	
	private void item(Hero hero,Runnable stopCallback){
		RPG.popup.add(SelectItemView.class,$.omap("hero",hero).add("callback", (CustomRunnable<Item>)item -> {
			if(item.range == ItemRange.one)
				getGroup(item.forward,hero).select(target -> useItem(item,hero,target,stopCallback),item.deadable);
			else
				useItem(item,hero,null,stopCallback);
		}));
	}
	
	private void useItem(Item item,Hero hero,Target target,Runnable callback){
		status.add(Target.name(target) + " 使用了道具『" + item.name + "』");
		Result result = item.use(BaseItem.Context.battle(hero, target, RPG.ctrl.hero.currentHeros(), enemyGroup.list()));
		animations.play(result, callback);
	}
	
	
	private void checkDead(){
		$.getIf(enemyGroup.list(), e -> e.target.isDead(), e -> {
			status.add(e.name + "已死亡");
			enemyGroup.remove(e);
			timer.remove(e);
		});
	}
	
	private Selectable getGroup(ItemForward forward, Hero hero){
		if(forward == ItemForward.enemy)
			return enemyGroup;
		if(forward == ItemForward.self)
			return (onSelect,deadable) -> onSelect.run(hero.target);
		return heroGroup; 
	}
	
	private void useSpellcard(Spellcard sc,Object hero,Object target,Runnable callback){
		use(null,sc,hero,target,callback);
	}
	
	private void use(String _txt,Spellcard sc,Object _hero,Object target,Runnable callback){
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
		Result result = sc.use(Item.Context.battle(_hero, target,RPG.ctrl.hero.currentHeros(), enemyGroup.list()));
		animations.play(result,callback);
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
