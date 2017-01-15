package com.rpsg.rpg.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.controller.MapController;
import com.rpsg.rpg.controller.PostController;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
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
	/**画面二次处理*/
	private static PostController post;
	
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
		post = new PostController();
		
		//创建stage菜单
		$.add(new Button(Res.getDrawable(Path.IMAGE_MENU_GLOBAL + "btn_menu.png"), Res.getDrawable(Path.IMAGE_MENU_GLOBAL + "btn_menu_a.png")))
			.size(60, 60).position(Game.STAGE_WIDTH - 90, 30).click(() -> System.out.println("asd")).appendTo(stage);
		
		Log.i("Game-view[created]");
	}
	
	public void draw() {
		Batch batch = stage.getBatch();
		
		if(map.renderable){
			post.begin();
				map.draw(batch);//画地图
			post.end();
			
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
		//卸载高级画质
		post.dispose();
		
		Game.view = null;
		
		Log.i("Game-view[disposed]");
	}
	
}
