package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.IMenuView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.CheckBox.CheckBoxStyle;
import com.rpsg.rpg.utils.game.GameUtil;

public class EquipView extends IMenuView{
	Stage stage;
	
	public EquipView init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(137,79).setColor(0,0,0,0.52f).setPosition(240,455).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(680,48).setColor(0,0,0,0.75f).setPosition(377,471).appendTo(stage);
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"info.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"info_p.png");// help button press
		$.add(new CheckBox("", cstyle, 1)).appendTo(stage).setPosition(880,471).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
//			phelp.addAction(self.isChecked()?Actions.fadeIn(0.3f):Actions.fadeOut(0.3f)); TODO LOGIC for info button
		}});}});
		
		return this;
	}
	
	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	public void logic() {
		stage.act();
	}
	
	public void onkeyTyped(char character) {
		stage.keyTyped(character);
	}

	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode || Keys.X==keyCode)
			this.disposed=true;
		stage.keyDown(keyCode);
	}

	public void onkeyUp(int keyCode) {
		stage.keyUp(keyCode);
	}

	public void dispose() {
		stage.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stage.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return stage.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stage.touchUp(screenX, screenY, pointer, button);
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
