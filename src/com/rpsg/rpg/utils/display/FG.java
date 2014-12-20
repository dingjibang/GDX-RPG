package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.ResourcePool;
import com.rpsg.rpg.utils.game.GameUtil;


public class FG {
	static Image currentImageL;
	static Image currentImageR;
	
	public static final int LEFT=0;
	public static final int RIGHT=1;
	
	public static BaseScriptExecutor show(final Script script,final String imgPath,final int position){
		return script.add((BaseScriptExecutor)()->{
			if(position==LEFT)
				currentImageL=ResourcePool.get(imgPath);
			else
				currentImageR=ResourcePool.get(imgPath);
		});
	}
	
	public static BaseScriptExecutor hide(final Script script,final String imgPath,final int position){
		return script.add((BaseScriptExecutor)()->{
			if(position==LEFT)
				currentImageL=null;
			else
				currentImageR=null;
		});
	}
	
	public static BaseScriptExecutor hideAll(final Script script){
		return script.add((BaseScriptExecutor)()->{
			currentImageL=null;
			currentImageR=null;
		});
	}
	
	public static void draw(SpriteBatch sb){
		sb.end();
		sb.begin();
		if(currentImageL!=null){
			currentImageL.setScale(0.7f);
			currentImageL.draw(sb);
		}
		if(currentImageR!=null){
			currentImageR.setScale(0.7f);
			currentImageR.setScaleX(-0.7f);
			currentImageR.setX(GameUtil.screen_width);
			currentImageR.draw(sb);
		}
	}
}

