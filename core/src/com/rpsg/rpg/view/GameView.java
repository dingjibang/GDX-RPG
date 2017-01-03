package com.rpsg.rpg.view;

import com.rpsg.rpg.controller.MapController;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Views;

/**
 * GDX-RPG 游戏视窗<br>
 */
public class GameView extends View{
	
	/**是否允许资源加载完成后就在屏幕画图，默认为true，他可以在JS脚本加载完毕后经由JS设置为false，这样可以在画图之前搞些事情*/
	boolean renderable = true;
	/**是否加载完成的，当资源加载完成后，将执行一个回调，该变量变为true*/
	boolean inited = false;
	
	public void create() {
		stage = Game.stage();
		
		Game.map = new MapController();
		
		//当加载地图之前
		Game.map.setBeforeLoad(() -> {
			//恢复到什么也没加载的状态
			inited = false;
			renderable = true;
			stage.clear();
		});
		
		//当加载地图之后
		Game.map.setLoaded(() -> {
			inited = true;
		});
		
		//载入地图
		Game.map.load(Game.archive.get().mapName);
	}
	
	public void draw() {
		if(inited && renderable){
			//画地图
			Game.map.draw(stage.getBatch());
		}
	}

	public void act() {
		if(!inited){
			//等待资源加载完成
			inited = Views.loadView.updated();
		}else{
			stage.act();
			//TODO
		}
	}
	
	public void onRemove() {
		//当视窗被移除时，代表已经不玩游戏了，将存档卸载
		Game.archive.clear();
		//将地图控制器卸载
		Game.map = null;
	}

}
