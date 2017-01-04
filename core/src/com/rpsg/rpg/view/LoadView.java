package com.rpsg.rpg.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.ui.view.View;

/**
 * 载入动画视图<br>
 * {@link LoadView}在一般情况下，是为{@link Res}工作的，当其正在加载东西时，LoadView将在游戏最上层画出加载动画，但也可以手动的调用{@link #start(String)}让其工作。
 */
public class LoadView extends View{

	/**当为false时，本视图将被绘制*/
	private boolean updated = true;
	/**id列表*/
	private List<String> idList = new ArrayList<>();
	
	public void create() {
		stage = Game.stage();
		
		Res.get(Path.IMAGE_LOAD + "sprite.png").query().action(Actions.forever(Actions.rotateBy(360, .5f))).position(Game.STAGE_WIDTH - 70, 30).appendTo(stage);
		
		stage.getRoot().getColor().a = 0; 
	}

	public void draw() {
		//如果Res正在处理资源，或有自定义资源正在处理，则画图
		if(!updated || !idList.isEmpty() || stage.getRoot().getActions().size != 0){
			stage.act();
			stage.draw();
		}
	}

	public void act() {
		//更新资源
		updated = Res.act();
		
		if(stage.getRoot().getActions().size == 0){
			if(updated && idList.isEmpty() && stage.getRoot().getColor().a == 1f)
				stage.addAction(Actions.fadeOut(.3f));
			
			if((!updated || !idList.isEmpty()) && stage.getRoot().getColor().a == 0)
				stage.addAction(Actions.fadeIn(.3f));
		}
	}
	
	public void start(String id) {
		idList.add(id);
		
		stage.getRoot().clearActions();
		stage.addAction(Actions.fadeIn(.3f));
	}
	
	public void stop(String id) {
		idList.remove(id);
	}
	
	public boolean updated() {
		return updated;
	}
	
}
