package com.rpsg.rpg.utils.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.utils.display.FontUtil;

public class GameUtil {
	public static int screen_width;
	public static int screen_height;
	
	public static void drawFPS(SpriteBatch batch){
		FontUtil.draw(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(),14,Color.BLACK,13,GameUtil.screen_height-13,1000,0,10);
		FontUtil.draw(batch, "FPS:"+Gdx.graphics.getFramesPerSecond(),14,Color.WHITE,12,GameUtil.screen_height-12,1000,0,10);
	}
	
	public static SpriteBatch resetBacth(SpriteBatch batch){
		batch.end();
		batch.begin();
		return batch;
	}
}
