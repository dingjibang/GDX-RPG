package com.rpsg.rpg.controller;

import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.controller.PostController.Status;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.ui.view.View;
import com.rpsg.rpg.ui.widget.Button;
import com.rpsg.rpg.util.Timer;
import script.ui.view.MenuView;

/**
 * GDX-RPG 菜单管理器<br>
 * 负责统一的游戏菜单创建、销毁等工作
 */
public class GameMenuController {
	View menu;
	GdxQuery menuButton;
	
	public GameMenuController() {
		//创建stage菜单
		menuButton = $.add(new Button(Path.IMAGE_MENU_GLOBAL + "btn_menu.png"))
			.size(60, 60).position(Game.STAGE_WIDTH - 90, 30).click(this::show).to(Game.view.stage);

		Timer.then(this::init);

	}

	private void init(){
		MenuView preInit = new MenuView();
		preInit.create();
		preInit.remove();
	}
	
	/**显示菜单*/
	public void show() {
		if(menu != null) return;
		menu = new MenuView();
		menu.create();
		Views.addView(menu);
		
		Game.view.map.post.setStatus(Status.menu, false);
		
		Game.view.stage.unfocusAll();
		menuButton.cleanActions().fadeOut(.15f);

		//暂停游戏计时
		Timer.pause();
	}
	
	/**关闭菜单*/
	public void hide() {
		menu.remove();
		menu = null;
		
		Game.view.map.post.setStatus(Status.normal, false);
		menuButton.cleanActions().fadeIn(.15f);

		//恢复游戏计时
		Timer.resume();
	}
	
	public boolean visible() {
		return menu != null;
	}
	
	public void dispose() {
		hide();
	}
}
