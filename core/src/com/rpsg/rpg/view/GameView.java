package com.rpsg.rpg.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.rpsg.rpg.controller.GameMenuController;
import com.rpsg.rpg.controller.MapController;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.ui.view.View;
import com.rpsg.rpg.view.game.FG;
import com.rpsg.rpg.view.game.MessageBox;

/**
 * GDX-RPG 游戏视窗<br>
 */
public class GameView extends View{
	
	
	/**地图*/
	public MapController map;
	/**对话框*/
	public MessageBox msg;
	/**立绘*/
	public FG fg;
	/**菜单*/
	public GameMenuController menu;
	
	
	public void create() {
		Game.view = this;
		
		stage = Game.stage();
		
		//载入地图
		map = new MapController();
		map.load(Game.archive.get().mapName);
		
		//init
		//添加输入处理
		addProcessor(msg = new MessageBox());
		fg = new FG();
		
		menu = new GameMenuController();
		
		Log.i("Game-view[created]");
	}
	
	public void draw() {
		Batch batch = stage.getBatch();
		
		if(map.renderable && map.loaded()){
			map.draw(batch);//画地图
			
			stage.draw();//画UI
		}
		
		fg.draw(batch);
		msg.draw(batch);
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
		//卸载游戏菜单
		menu.dispose();
		
		Game.view = null;
		Log.i("Game-view[disposed]");
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.ESCAPE)
			menu.show();

		return super.keyDown(keycode);
	}
	
	public void resize() {
		map.resize();
		super.resize();
	}
}