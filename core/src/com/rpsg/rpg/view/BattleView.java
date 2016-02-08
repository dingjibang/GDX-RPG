package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.object.base.BattleRes;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.EnemyBox;
import com.rpsg.rpg.system.ui.HeroStatusBox;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Status;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.Timer;
import com.rpsg.rpg.utils.game.GameUtil;

public class BattleView extends DefaultIView{
	
	BattleParam param;
	List<HeroStatusBox> statusBox = new ArrayList<>();
	List<EnemyBox> enemyBox = new ArrayList<>();
	Status status;
	Timer timer;
	
	public BattleView(BattleParam param) {
		this.param = param;
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
	}
	
	@Override
	public BattleView init() {
		stage.clear();
		statusBox.clear();
		enemyBox.clear();
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(1024,576).color(.5f,.5f,.5f,1)).appendTo(stage);//TODO debug;
		
		$.add(status = new Status()).setPosition(0, 0).appendTo(stage);
		
		List<Hero> heros = RPG.ctrl.hero.currentHeros;
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(GameUtil.screen_width,115).color(0,0,0,.5f)).setPosition(0, 28).appendTo(stage);
		for(int i = 0; i < heros.size(); i++)
			statusBox.add($.add(new HeroStatusBox(heros.get(i)).position(i * 256, 28)).appendTo(stage).getItem(HeroStatusBox.class));
		
		List<Enemy> enemyList = Enemy.name(Enemy.get(param.enemy));
		
		Table table = new Table();
		$.each(enemyList, (int idx,Enemy enemy)->{
			EnemyBox box = new EnemyBox(enemy);
			table.add(box).padLeft(35).padRight(35);
		});
		$.add(table).appendTo(stage).setPosition(GameUtil.screen_width/2 - table.getWidth()/2, GameUtil.screen_height/2 - table.getHeight()/2 + 50).setAlign(Align.center);
		
		$.add(new TextButton("结束战斗！",BattleRes.textButtonStyle)).appendTo(stage).setPosition(100,170).onClick(()->{
			RPG.ctrl.battle.stop();
		});
		
		$.add(timer = new Timer(heros,enemyList,(obj)->{
			timer.pause(true);
			String name = obj instanceof Enemy ? ((Enemy)obj).name : ((Hero)obj).name;
			status.add("[#ff7171]"+name+"[] 的战斗回合");
			if(obj instanceof Hero){
				Hero hero = (Hero)obj;
				Image fg = $.add(Res.get(Setting.IMAGE_FG+hero.fgname+"/Normal.png")).appendTo(stage).setScaleX(-0.33f).setScaleY(0.33f).setOrigin(Align.bottomLeft).setPosition(GameUtil.screen_width+500, 0).addAction(Actions.moveBy(-400, 0,1f,Interpolation.pow4Out)).setZIndex(1).getItem(Image.class);
				Table menu = $.add(new Table()).appendTo(stage).setPosition(600, 220).getItem(Table.class);
				menu.add(new TextButton("攻击",BattleRes.textButtonStyle).onClick(()->{
					RPG.ctrl.battle.stop();
				}));
				menu.add(new TextButton("防御",BattleRes.textButtonStyle).onClick(()->{
					RPG.ctrl.battle.stop();
				}));
				menu.add(new TextButton("符卡",BattleRes.textButtonStyle).onClick(()->{
					RPG.ctrl.battle.stop();
				}));
				menu.add(new TextButton("物品",BattleRes.textButtonStyle).onClick(()->{
					RPG.ctrl.battle.stop();
				}));
				menu.add(new TextButton("逃跑",BattleRes.textButtonStyle).onClick(()->{
					escape(hero);
					menu.remove();
					fg.remove();
					timer.pause(false);
				}));
				$.each(menu.getCells(),(cell) -> cell.size(150,30));
			}
		})).appendTo(stage);
		
		status.add("fuck you");
		stage.setDebugAll(!false);
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(GameUtil.screen_width,GameUtil.screen_height).color(0,0,0,1)).appendTo(stage).addAction(Actions.sequence(Actions.fadeOut(.3f,Interpolation.pow2In),Actions.removeActor()));
		
		status.setZIndex(999999);
		
		return this;
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
		if(keyCode == Keys.S) status.add("随便说一句话："+Math.random());
		if(keyCode == Keys.D) status.append(" & "+Math.random());
		if(keyCode == Keys.F) status.append("[#ffaabb]彩色测试[]");
		super.onkeyDown(keyCode);
	}
	
	public boolean escape(Hero hero){
		double random = Math.random();
		boolean flag = random > .5;
		status.add(hero.getName()+" 尝试逃跑……").append(flag ? "成功了" : "但是失败了",10);
		return flag;
	}

}
