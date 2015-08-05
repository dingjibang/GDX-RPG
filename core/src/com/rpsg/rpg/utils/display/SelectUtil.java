package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.controller.InputController;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;

public class SelectUtil implements InputProcessor {
	Image but,click,hover,mask;
	Stage stage;
	ImageButtonStyle style;
	public SelectUtil(){
		but=new Image(Setting.IMAGE_GLOBAL+"option.png");
		hover=new Image(Setting.IMAGE_GLOBAL+"optionhover.png");
		click=new Image(Setting.IMAGE_GLOBAL+"optionclick.png");
		mask=new Image(Setting.IMAGE_GLOBAL+"selmask.png");
		style=new ImageButtonStyle();
		style.over=hover.getDrawable();
		style.down=click.getDrawable();
		style.imageUp=but.getDrawable();
		mask.setWidth(GameUtil.screen_width);
		mask.setPosition(0, 0);
		stage=new Stage();
	}
	
	public static String currentSelect="";
	boolean select=false;
	boolean isLocked;
	public BaseScriptExecutor select(Script script, final String ...str){
		final SelectUtil that=this;
		return script.$(new ScriptExecutor(script) {
			public void step() {
				if(!select){
					stage.act();
					stage.getBatch().begin();
					for (Actor but : stage.getActors()) {
						but.draw(stage.getBatch(), 1);
						if(but instanceof ImageButton){
							String str=(String)but.getUserObject();
							FontUtil.draw((SpriteBatch) stage.getBatch(), str, 22, but.getColor(), 124+777/2-FontUtil.getTextWidth(str, 22)/2,(int)but.getY()+33, 1000);
						}
					}

					stage.getBatch().end();
				}else{
					Gdx.input.setInputProcessor(RPG.input);
					if(!isLocked)
						InputController.currentIOMode=IOMode.MapInput.NORMAL;
					stage.clear();
					dispose();
				}
			}
			public void init() {
				select=false;
				Gdx.input.setInputProcessor(that);
				isLocked=(InputController.currentIOMode==IOMode.MapInput.MESSAGING);
				InputController.currentIOMode=IOMode.MapInput.MESSAGING;
				int yoff=0;
				mask.setColor(1,1,1,0);
				mask.addAction(Actions.fadeIn(0.1f));
				stage.addActor(mask);
				for(final String s:str){
					final ImageButton button=new ImageButton(style);
					button.setUserObject(s);
					button.addListener(new InputListener(){
						public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
							if(x>0 && x<button.getWidth() && y>0 && y<button.getHeight()){
								button.addAction(Actions.sequence(new Action() {
									public boolean act(float delta) {
										for (Actor a : stage.getActors()) {
											if(!a.equals(button))
												a.addAction(Actions.fadeOut(0.1f));
										}

										mask.addAction(Actions.fadeOut(0.2f));
										return true;
									}
								},Actions.alpha(0.6f,0.08f),Actions.alpha(1,0.08f),Actions.alpha(0.6f,0.08f),Actions.alpha(1,0.08f),
								Actions.parallel(Actions.fadeOut(0.15f),Actions.moveBy(0, -30,0.15f))
								,Actions.after(new Action() {
									public boolean act(float delta) {
										select=true;
										currentSelect=s;
										return false;
									}
								})));
							} 
						}
						public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
							return true;
						}
					});
					button.setColor(1,1,1,0);
					button.setPosition(GameUtil.screen_width/2-button.getWidth()/2, (yoff+=70)-30);
					button.addAction(Actions.parallel(Actions.fadeIn(0.1f),Actions.moveBy(0, +30,0.1f)));
					stage.addActor(button);
				}
			}
		});
	}

	public boolean keyDown(int keycode) {
		return stage.keyDown(keycode);
	}

	public boolean keyUp(int keycode) {
		return stage.keyUp(keycode);
	}

	public boolean keyTyped(char character) {
		return stage.keyTyped(character);
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stage.touchDown(screenX, screenY, pointer, button);
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stage.touchUp(screenX, screenY, pointer, button);
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return stage.touchDragged(screenX, screenY, pointer);
	}

	public boolean mouseMoved(int screenX, int screenY) {
		return stage.mouseMoved(screenX, screenY);
	}

	public boolean scrolled(int amount) {
		return stage.scrolled(amount);
	}
	

}

