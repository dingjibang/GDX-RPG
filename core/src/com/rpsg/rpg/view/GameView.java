package com.rpsg.rpg.view;

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
		loadResource();
	}
	
	/**
	 * 加载游戏资源（如地图），和{@link #create()}不同，可以多次被调用
	 */
	public void loadResource() {
		//恢复到什么也没加载的状态
		inited = false;
		renderable = true;
		stage.clear();
		
		//加载地图资源
		Game.map.load(Game.archive.get().mapName, true, map -> {
			inited = true;
		});
	}

	public void draw() {
		if(inited && renderable){
			//画地图
			Game.map.draw(stage);
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

}
