package com.rpsg.rpg.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.utils.game.GameUtil;

public class BattleView extends DefaultIView{
	
	BattleParam param;
	public BattleView(BattleParam param) {
		this.param = param;
	}
	
	@Override
	public BattleView init() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		System.out.println(param.getEnemy());
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

}
