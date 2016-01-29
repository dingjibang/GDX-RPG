package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.object.base.BattleRes;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.EnemyBox;
import com.rpsg.rpg.system.ui.HeroStatusBox;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;

public class BattleView extends DefaultIView{
	
	BattleParam param;
	List<HeroStatusBox> statusBox = new ArrayList<>();
	List<EnemyBox> enemyBox = new ArrayList<>();//TODO 实现
	
	public BattleView(BattleParam param) {
		this.param = param;
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
	}
	
	@Override
	public BattleView init() {
		stage.clear();
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(1024,576).color(.5f,.5f,.5f,1)).appendTo(stage);//TODO debug;
		
		$.add(new Label("啊啊啊啊战斗中QAQ"+Math.random(),30)).setPosition(233,400).appendTo(stage);
		List<Hero> heros = RPG.ctrl.hero.currentHeros;
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(GameUtil.screen_width,115).color(0,0,0,.5f)).setPosition(0, 28).appendTo(stage);
		
		for(int i = 0; i < heros.size(); i++)
			statusBox.add($.add(new HeroStatusBox(heros.get(i)).position(i * 256, 28)).appendTo(stage).getItem(HeroStatusBox.class));
		
		$.add(new TextButton("结束战斗！",BattleRes.textButtonStyle)).appendTo(stage).setPosition(300,270).onClick(()->{
			RPG.ctrl.battle.stop();
		});
		
		stage.setDebugAll(!false);
		
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
		if(keyCode == Keys.R)
			init();
		super.onkeyDown(keyCode);
	}

}
