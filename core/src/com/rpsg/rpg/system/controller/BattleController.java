package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.base.IOMode.MapInput;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.BattleView;
import com.rpsg.rpg.view.GameViews;



/** 战斗控制器 **/
public class BattleController {
	
	public static enum State {
		prepare,//准备战斗
		wait,//战斗前动画（淡出）
		battle,//战斗中
		stop//战斗结束
	}
	BattleParam param;
	public State state = State.stop;
	
	public boolean start(BattleParam param) {
		if(state != State.stop)
			return false;
		
		this.param = param;
		state = State.prepare;
		
		return true;
	}
	
	public void stop(){
		state = State.stop;
		
		InputController.loadIOMode();
		
		if(param.stopCallback != null) param.stopCallback.run();
		param = null;
		
		//TODO 需要dispose？？？
//		GameViews.gameview.battleView = null;
	}
	
	public boolean isBattle(){
		return state == State.battle;
	}
	
	public boolean logic(){
		if(state == State.prepare && param != null){//开始战斗*queue
			Image black = Res.getSync(Setting.UI_BASE_IMG);
			Drawable prepareDrawable = Res.getDrawable(Setting.IMAGE_BATTLE+"battle_prepare.png");
			
			List<Image> images = new ArrayList<>();
			int count = 20;
			
			for(int i=0;i<count;i++){
				Image image = new Image(prepareDrawable);
				boolean top = i % 2 == 0;
				image.action(Actions.forever(Actions.run(()->{
					if(image.x(image.getX()-2).getX()<-image.getWidth() * 2 + 34)
						image.setX(GameUtil.screen_width);
				}))).position(((i-i%2)/2)*image.getWidth(),top ? -155 * 2 : GameUtil.screen_height - image.getHeight() + 155 * 2 )
				.action(Actions.moveBy(0, top ? 155 : -155, .5f,Interpolation.pow2Out));
				
				images.add(image);
			}
			
			RPG.ctrl.cg.pushAll(images);
			
			MoveController.offsetActor.addAction(Actions.sequence(Actions.delay(.3f),Actions.scaleTo(.75f,.75f,1.5f,Interpolation.pow2In),Actions.scaleTo(1f, 1f)));
			black.size(GameUtil.screen_width, GameUtil.screen_height).color(0,0,0,0).action(Actions.sequence(Actions.delay(.8f),Actions.fadeIn(1f,Interpolation.pow4In),Actions.run(()->{
				
				InputController.loadIOMode();
				InputController.saveIOMode(IOMode.MapInput.battle);
				
				try {
					state = State.battle;
					BattleView bv = new BattleView(param);
					bv.init();
					GameViews.gameview.battleView = bv;
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if(param.startCallback != null) param.startCallback.run();
				
				RPG.ctrl.cg.dispose(black).dispose(images);
				
			})));
			
			RPG.ctrl.cg.push(black);
			
			state = State.wait;
			InputController.saveIOMode(MapInput.wait);
			
			return false;
		}
		
		return isBattle();
	}

}
