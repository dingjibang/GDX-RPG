package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.utils.game.GameUtil;


public class MsgUtil {
	public static Image msgbox;
	
	public static void init(){
		msgbox=new Image(Setting.GAME_RES_MESSAGE+"msgbox.png");
		float ss=msgbox.getWidth();
		msgbox.setWidth(GameUtil.screen_width-40);
		ss=ss/msgbox.getWidth();
		msgbox.setHeight((float)msgbox.getHeight()/ss);
		msgbox.setX(GameUtil.screen_width/2-msgbox.getWidth()/2);
		msgbox.setY(25);
	}
	
	static String currentText;
	static int currentTextPoint=0;
	static Script currentScript;
	static SpriteBatch currentBatch;
	static int TEXT_DISPLAY_SPEED=20;
	private static int DISPLAY_OFFSET=0;
	public static ScriptExecutor MSG(final SpriteBatch batch,final String str,final String title,final int size,final Script script){
		return script.add(new ScriptExecutor(script) {
			public void step() {
				DISPLAY_OFFSET-=TEXT_DISPLAY_SPEED;
				if(DISPLAY_OFFSET<=0){
					DISPLAY_OFFSET=100;
					if(++currentTextPoint>=currentText.length())
						dispose();
				}
				GameUtil.resetBacth(currentBatch);
				msgbox.setColor(0.4f,0.67f,0.98f,1);
				msgbox.draw(batch);
				FontUtil.draw(batch, currentText.substring(0,currentTextPoint), size, Color.WHITE, 50, 130, (int) (msgbox.getWidth()-60));
				FontUtil.draw(batch, title, 22, Color.WHITE, getOrPosX(title, 22), 178, (int) (msgbox.getWidth()-60));
			}
			public void init() {
				currentText=str;
				currentTextPoint=0;
				currentBatch=batch;
			}
		});
	}
	
	private static int getOrPosX(String txt,int size){
		int o=(200/2-FontUtil.getTextWidth(txt, size, Setting.STRING_PADDING_LR)/2);
		return 20+o;
	}
	
}
