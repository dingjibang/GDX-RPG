package com.rpsg.rpg.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;

public class BattleView extends DefaultIView{
	
	BattleParam param;
	
	public BattleView(BattleParam param) {
		this.param = param;
	}
	
	@Override
	public BattleView init() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		$.add(new Label("啊啊啊啊战斗中QAQ",30)).setPosition(233,400).appendTo(stage);
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
		$.add(new TextButton("结束战斗！",tstyle)).appendTo(stage).setPosition(300,70).onClick(()->{
			RPG.ctrl.battle.stop();
		});
		
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
