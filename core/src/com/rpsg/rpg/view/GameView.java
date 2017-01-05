package com.rpsg.rpg.view;

import com.rpsg.rpg.controller.MapController;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.ui.view.View;
import com.rpsg.rpg.view.game.MessageBox;

/**
 * GDX-RPG 游戏视窗<br>
 */
public class GameView extends View{
	
	
	/**地图*/
	public MapController map = new MapController();
	/**对话框*/
	public MessageBox msg;
	
	
	
	public void create() {
		Game.view = this;
		
		stage = Game.stage();
		
		//载入地图
		map.load(Game.archive.get().mapName);
		
		//添加输入处理
		addProcessor(msg = new MessageBox());
	}
	
	public void draw() {
		if(map.renderable){
			map.draw(stage.getBatch());//画地图
			stage.draw();//画UI
		}
		msg.draw(stage.getBatch());
	}

	public void act() {
		map.act();
		stage.act();
		
	}
	
	public void onRemove() {
		//当视窗被移除时，代表已经不玩游戏了，将存档卸载
		Game.archive.clear();
		//将地图控制器卸载
		map.dispose();
		
		Game.view = null;
	}
	
}
