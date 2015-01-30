package com.rpsg.rpg.system.control;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.utils.display.BlurUtil;
import com.rpsg.rpg.utils.display.ScreenUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.menu.GameMenuView;

public class MenuControl {
	public static void createMenu(){
		GameViews.gameview.stackView=new GameMenuView();
		Pixmap bg=ScreenUtil.getScreenshot(0, 0, GameUtil.screen_width, GameUtil.screen_height, false);
		GameViews.gameview.stackView.params.put("bg",new Image(new TextureRegion(new Texture(bg),0,GameUtil.screen_height,GameUtil.screen_width,-GameUtil.screen_height)));
		GameViews.gameview.stackView.params.put("blurbg", new Image(new TextureRegion(new Texture(BlurUtil.blur(bg, 5, 5, true)),0,GameUtil.screen_height,GameUtil.screen_width,-GameUtil.screen_height)));
		GameViews.gameview.stackView.init();
		Logger.info("菜单创建完成。");
	}
	
	public static void keyDown(int keyCode){
		if(GameViews.gameview.stackView!=null){
			GameViews.gameview.stackView.onkeyDown(keyCode);
		}
	}
	
	public static void keyUp(int keyCode){
		if(GameViews.gameview.stackView!=null){
			GameViews.gameview.stackView.onkeyUp(keyCode);
		}
	}
	
	public static void keyType(char key){
		
	}
	
	public static boolean touchDown(int screenX, int screenY, int pointer, int button){
		if(GameViews.gameview.stackView!=null){
			GameViews.gameview.stackView.touchDown(screenX, screenY, pointer, button);
		}
		return false;
	}

	public static boolean touchDragged(int screenX, int screenY, int pointer) {
		if(GameViews.gameview.stackView!=null){
			GameViews.gameview.stackView.touchDragged(screenX, screenY, pointer);
		}
		return false;
		
	}

	public static boolean touchUp(int screenX, int screenY, int pointer, int button){
		if(GameViews.gameview.stackView!=null){
			GameViews.gameview.stackView.touchUp(screenX, screenY, pointer, button);
		}
		return false;
	}

	public static void scrolled(int amount) {
		if(GameViews.gameview.stackView!=null){
			GameViews.gameview.stackView.scrolled(amount);
		}
	}
}
