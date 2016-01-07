package com.rpsg.rpg.system.controller;

import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.view.BattleView;
import com.rpsg.rpg.view.GameViews;

/** 战斗控制器 **/
public class BattleController {
	
	/**
	 * flag && param != null 准备战斗，下一帧触发
	 * !flag && param != null 开始战斗
	 * !flag && param == null 没有战斗
	 */
	boolean flag;
	BattleParam param;
	
	public void start(BattleParam param) {
		if(isBattle())
			return;
		
		InputController.saveIOMode(IOMode.MapInput.battle);
		
		this.param = param;
		flag = true;
	}
	
	public void stop(){
		flag = false;
		param = null;
		
		InputController.loadIOMode();
		
		//TODO 需要dispose？？？
//		GameViews.gameview.battleView = null;
	}
	
	public boolean isBattle(){
		return !flag && param != null;
	}
	
	public boolean logic(){
		if(flag && param != null){//开始战斗*queue
			flag = false;
			GameViews.gameview.battleView = new BattleView(param).init();
			return true;
		}
		return isBattle();
	}
}
