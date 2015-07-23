package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rpsg.rpg.system.ui.IMenuView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.display.BlurUtil;
import com.rpsg.rpg.utils.display.ScreenUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.menu.EquipView;
import com.rpsg.rpg.view.menu.ItemView;
import com.rpsg.rpg.view.menu.MenuView;
import com.rpsg.rpg.view.menu.SpellCardView;
import com.rpsg.rpg.view.menu.StatusView;
import com.rpsg.rpg.view.menu.SystemView;
import com.rpsg.rpg.view.menu.TacticView;

public class MenuController {
	public static Image bg;
	public static Pixmap pbg;
	public static void createMenu(){
		pbg=ScreenUtil.getScreenshot(0, 0, GameUtil.getScreenWidth(), GameUtil.getScreenHeight(), false);
		bg=new Image(new TextureRegion(new Texture(pbg),0,GameUtil.getScreenHeight(),GameUtil.getScreenWidth(),-GameUtil.getScreenHeight()));
		GameViews.gameview.stackView=new MenuView();
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
	
	public static class Menu{
		public String fileName;
		public Class<? extends IMenuView> view;
		public Menu(String fileName, Class<? extends IMenuView> view,String name) {
			this.fileName = fileName;
			this.view = view;
			this.name=name;
		}
		public String name;
	}
	
	static List<Menu> menus=new ArrayList<Menu>();
	static{
		menus.add(new Menu("status", StatusView.class, "状态"));
		menus.add(new Menu("equip", EquipView.class, "装备"));
		menus.add(new Menu("item", ItemView.class, "物品"));
		menus.add(new Menu("spellcard", SpellCardView.class, "符卡"));
		menus.add(new Menu("tactic", TacticView.class, "战术"));
		menus.add(new Menu("note", null, "记录"));
		menus.add(new Menu("system", SystemView.class, "系统"));
	}
	public static List<Menu> generate(){
		return menus;
	}
}
