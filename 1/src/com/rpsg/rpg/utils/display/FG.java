package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;


public class FG {
	static Image currentImageL;
	static Image currentImageR;
	
	public static final int LEFT=0;
	public static final int RIGHT=1;
	
	static boolean leftFade=true;
	static boolean rightFade=true;
	
	public static void init(){
		currentImageL=currentImageR=null;
		Logger.info("立绘控制器初始化完成。");
	}
	
	public static BaseScriptExecutor show(final Script script,final String imgPath,final int position){
		return script.$((BaseScriptExecutor)()->{
			if(position==LEFT){
				boolean nul=currentImageL==null;
				currentImageL=Res.get(imgPath);
				currentImageL.setScale(0.7f);
				if(nul)
					currentImageL.setColor(1,1,1,0f);
				leftFade=true;
			}else{
				boolean nul=currentImageR==null;
				currentImageR=Res.get(imgPath);
				currentImageR.setScale(0.7f);
				currentImageR.setScaleX(-0.7f);
				currentImageR.setX(GameUtil.screen_width);
				if(nul)
					currentImageR.setColor(1,1,1,0f);
				rightFade=true;
			}
		});
	}
	
	public static BaseScriptExecutor hide(final Script script,final String imgPath,final int position){
		return script.$((BaseScriptExecutor)()->{
			if(position==LEFT)
				currentImageL=null;
			else
				currentImageR=null;
		});
	}
	
	public static BaseScriptExecutor hideAll(final Script script){
		return script.$((BaseScriptExecutor)()->{
			leftFade=false;
			rightFade=false;
		});
	}
	
	public static BaseScriptExecutor hide(final Script script,final int position){
		return script.$((BaseScriptExecutor)()->{
			if(position==LEFT)
				leftFade=false;
			else
				rightFade=false;
		});
	}
	
	public static void draw(SpriteBatch sb){
		sb.end();
		sb.begin();
		if(currentImageL!=null){
			if(leftFade && currentImageL.getColor().a<1)
				currentImageL.setColor(1,1,1,currentImageL.getColor().a+0.1f);
			if(!leftFade && currentImageL.getColor().a>0)
				currentImageL.setColor(1,1,1,currentImageL.getColor().a-0.2f);
			if(!leftFade && currentImageL.getColor().a<=0)
				currentImageL=null;
			if(currentImageL!=null)
				currentImageL.draw(sb);
		}
		if(currentImageR!=null){
			if(rightFade && currentImageR.getColor().a<1)
				currentImageR.setColor(1,1,1,currentImageR.getColor().a+0.1f);
			if(!rightFade && currentImageR.getColor().a>0)
				currentImageR.setColor(1,1,1,currentImageR.getColor().a-0.2f);
			if(!rightFade && currentImageR.getColor().a<=0)
				currentImageR=null;
			if(currentImageR!=null)
				currentImageR.draw(sb);
		}
	}

}

