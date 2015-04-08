package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.BlurUtil;
import com.rpsg.rpg.utils.display.ScreenUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.menu.GameMenuView;

public class MenuController {
	public static Image bg,blurbg;
	public static Pixmap pbg,bbg;
	public static void createMenu(){
		GameViews.gameview.stackView=new GameMenuView();
		pbg=ScreenUtil.getScreenshot(0, 0, GameUtil.getScreenWidth(), GameUtil.getScreenHeight(), false);
		MenuController.bg=new Image(new TextureRegion(new Texture(pbg),0,GameUtil.getScreenHeight(),GameUtil.getScreenWidth(),-GameUtil.getScreenHeight()));
		bbg=BlurUtil.blur(pbg, 5, 5, false);
		blurbg= new Image(new TextureRegion(new Texture(bbg),0,GameUtil.getScreenHeight(),GameUtil.getScreenWidth(),-GameUtil.getScreenHeight()));
		GameViews.gameview.stackView.params.put("bg",MenuController.bg);
		GameViews.gameview.stackView.params.put("blurbg",blurbg);
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
