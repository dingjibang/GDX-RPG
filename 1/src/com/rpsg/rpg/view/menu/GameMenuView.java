package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.StackView;
import com.rpsg.rpg.system.control.InputControler;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.MouseUtil;
import com.rpsg.rpg.view.GameViews;

public class GameMenuView extends StackView{
	Image bluredBG;
	Stage stage;
 	public void init(){
 		this.viewStack.add(new MenuBaseView());
 		this.viewStack.get(0).init();
 		stage=new Stage();
 		bluredBG=new Image(new TextureRegion((Texture)params.get("bg"),0,GameUtil.screen_height,GameUtil.screen_width,-GameUtil.screen_height));
 		bluredBG.setColor(1,1,1,0);
 		bluredBG.addAction(Actions.fadeIn(0.1f));
 		stage.addActor(bluredBG);
 		MouseUtil.setHWCursorVisible(true);
 	}
 	
 	public void draw(SpriteBatch batch){
 		stage.draw();
 		for(IView view:viewStack)
 			view.draw(batch);
 	}
 	
 	public void logic(){
 		stage.act();
 		for(IView view:viewStack){
			if(view.disposed)
				view.dispose();
			else
				view.logic();
		}
 	}
 	
 	public void onkeyTyped(char character) {
		viewStack.get(viewStack.size()-1).onkeyTyped(character);
	}

	public void onkeyDown(int keyCode) {
		if(viewStack.size()==1 && Keys.ESCAPE==keyCode){
			this.dispose();
			InputControler.currentIOMode=IOMode.MAP_INPUT_NORMAL;
			GameViews.gameview.stackView=null;
		}else{
			viewStack.get(viewStack.size()-1).onkeyDown(keyCode);
		}
	}

	public void onkeyUp(int keyCode) {
		viewStack.get(viewStack.size()-1).onkeyUp(keyCode);
	}

	public void dispose() {
		stage.dispose();
 		bluredBG.dispose();
		for(IView view:viewStack)
			view.dispose();
		MouseUtil.setHWCursorVisible(false);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		viewStack.get(viewStack.size()-1).touchDown(screenX, screenY, pointer, button);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		viewStack.get(viewStack.size()-1).touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		viewStack.get(viewStack.size()-1).touchDragged(screenX, screenY, pointer);
		return false;
	}

}
