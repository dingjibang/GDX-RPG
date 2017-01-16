package com.rpsg.rpg.controller;

import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.ui.view.View;
import com.rpsg.rpg.view.JSView;

/**
 * GDX-RPG 菜单管理器<br>
 * 负责统一的游戏菜单创建、销毁等工作
 */
public class GameMenuController {
	View menu;
	
	/**显示菜单*/
	public void show() {
		if(menu != null) return;
		
		menu = JSView.of("view/MenuView.js");
		menu.create();
		Views.addView(menu);
	}
	
	/**关闭菜单*/
	public void hide() {
		menu.remove();
		menu = null;
	}
	
	public boolean visible() {
		return menu == null;
	}
	
	public void dispose() {
		hide();
	}
}
