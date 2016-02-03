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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
import com.rpsg.rpg.system.ui.Status;
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
		
		List<Hero> heros = RPG.ctrl.hero.currentHeros;
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(GameUtil.screen_width,115).color(0,0,0,.5f)).setPosition(0, 28).appendTo(stage);
		for(int i = 0; i < heros.size(); i++)
			statusBox.add($.add(new HeroStatusBox(heros.get(i)).position(i * 256, 28)).appendTo(stage).getItem(HeroStatusBox.class));
		
		List<Enemy> enemyList = Enemy.get(param.enemy);
		Table table = new Table();
		$.each(enemyList, (int idx,Enemy enemy)->{
			EnemyBox box = new EnemyBox(enemy);
			table.add(box).padLeft(25).padRight(25);
		});
		$.add(table).appendTo(stage).setPosition(GameUtil.screen_width/2 - table.getWidth()/2, GameUtil.screen_height/2 - table.getHeight()/2 + 50).setAlign(Align.center);
		
		$.add(new TextButton("结束战斗！",BattleRes.textButtonStyle)).appendTo(stage).setPosition(100,170).onClick(()->{
			RPG.ctrl.battle.stop();
		});
		
		$.add(timer = new Timer(heros,enemyList,(obj)->{
			System.out.println(obj);
		})).appendTo(stage);
		
		$.add(status = new Status()).setPosition(0, 0).appendTo(stage);
		status.add("fuck you");
		stage.setDebugAll(!!false);
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(GameUtil.screen_width,GameUtil.screen_height).color(0,0,0,1)).appendTo(stage).addAction(Actions.sequence(Actions.fadeOut(.3f,Interpolation.pow2In),Actions.removeActor()));
		
		
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

}
