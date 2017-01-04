package com.rpsg.rpg.view;

import com.rpsg.rpg.controller.MapController;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.ui.view.View;
import com.rpsg.rpg.view.game.MessageBox;

/**
 * GDX-RPG 游戏视窗<br>
 */
public class GameView extends View{
	
	/**是否允许资源加载完成后就在屏幕画图，默认为true，他可以在JS脚本加载完毕后经由JS设置为false，这样可以在画图之前搞些事情*/
	public boolean renderable = true;
	/**地图*/
	public MapController map;
	/**对话框*/
	public MessageBox msg;
	
	
	
	public void create() {
		Game.view = this;
		
		stage = Game.stage();
		map = new MapController();
		
		//当加载地图之前
		map.setBeforeLoad(() -> {
			//恢复到什么也没加载的状态
			renderable = true;
		});
		
		
		//载入地图
		map.load(Game.archive.get().mapName);
	}
	
	public void draw() {
		if(renderable){
			map.draw(stage.getBatch());//画地图
			stage.draw();//画UI
		}
	}

	public void act() {
		stage.act();
		map.act();
	}
	
	public void onRemove() {
		//当视窗被移除时，代表已经不玩游戏了，将存档卸载
		Game.archive.clear();
		//将地图控制器卸载
		map.dispose();
		
		Game.view = null;
	}

}
