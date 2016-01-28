package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;



/** 战斗控制器 **/
public class BattleController {
	
	public static enum State {
		prepare,//准备战斗
		wait,//战斗前动画（淡出）
		battle,//战斗中
		stop//战斗结束
	}
	BattleParam param;
	State state;
	
	public void start(BattleParam param) {
		if(isBattle())
			return;
		
		this.param = param;
		state = State.prepare;
	}
	
	public void stop(){
		state = State.stop;
		param = null;
		
		InputController.loadIOMode();
		
		if(param.stopCallback != null) param.stopCallback.run();
		
		//TODO 需要dispose？？？
//		GameViews.gameview.battleView = null;
	}
	
	public boolean isBattle(){
		return state == State.battle;
	}
	
	public boolean logic(){
		
		if(state == State.prepare && param != null){//开始战斗*queue
			Image black = Res.get(Setting.UI_BASE_IMG);
			Drawable prepareDrawable = Res.getDrawable(Setting.IMAGE_GLOBAL+"battle_prepare.png");
			MoveController.offsetActor.addAction(Actions.sequence(Actions.scaleTo(.5f,.5f,.5f,Interpolation.pow2),Actions.scaleTo(1f, 1f)));
			black.size(GameUtil.screen_width, GameUtil.screen_height).color(0,0,0,0).action(Actions.sequence(Actions.fadeIn(.5f),Actions.run(()->{
				
				List<Image> images = new ArrayList<>();
				//TODO 加入动画
//				state = State.battle;
//				GameViews.gameview.battleView = new BattleView(param).init();
//				InputController.saveIOMode(IOMode.MapInput.battle);
//				if(param.startCallback != null) param.startCallback.run();
//				RPG.ctrl.cg.dispose(black);
				
			})));
			
			RPG.ctrl.cg.push(black);
			
			state = State.wait;
			
			return false;
		}
		
		return isBattle();
	}

}
