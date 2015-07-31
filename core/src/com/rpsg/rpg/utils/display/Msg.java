package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;


public class Msg {
	public static Image msgbox;
	
	public static void dispose(){
		if(msgbox!=null){
			msgbox.dispose();
			msgbox=null;
		}
	}
	
	public static void init(){
		dispose();
		msgbox=new Image(Setting.GAME_RES_MESSAGE+MsgType.正常);
		float ss=msgbox.getWidth();
		msgbox.setWidth(GameUtil.screen_width-40);
		ss=ss/msgbox.getWidth();
		msgbox.setHeight((float)msgbox.getHeight()/ss);
		msgbox.setX(GameUtil.screen_width/2-msgbox.getWidth()/2);
		msgbox.setY(25);
		msgbox.setColor(1,1,1,0);
		currentText="";
		currentTextPoint=0;
		TEXT_DISPLAY_SPEED=30;
		DISPLAY_OFFSET=0;
		show=false;
		Logger.info("文本模块初始化完成。");
	}
	
	static String currentText="";
	static int currentTextPoint=0;
	static int TEXT_DISPLAY_SPEED=30;
	private static int DISPLAY_OFFSET=0;
	static boolean show=false;
	static boolean firstZPress=false;
	static Color titleColor=Color.WHITE;
	public static BaseScriptExecutor say(final Script script,final String str,final String title,final int size){
		firstZPress=false;
		return script.$(new ScriptExecutor(script) {
			SpriteBatch batch= (SpriteBatch) FG.stage.getBatch();
			public void step() {
				if(Input.isPress(Keys.CONTROL_LEFT)){
					if(currentTextPoint<=currentText.length()-5)
						currentTextPoint+=5;
					else
						currentTextPoint=currentText.length();
				}else if((Gdx.input.isKeyJustPressed(Keys.Z) || Gdx.input.justTouched()) && currentTextPoint!=currentText.length()){
					currentTextPoint=currentText.length()-2;
				}else
					TEXT_DISPLAY_SPEED=30;
				DISPLAY_OFFSET-=TEXT_DISPLAY_SPEED;
				if(DISPLAY_OFFSET<=0){
					DISPLAY_OFFSET=100;
					if(currentTextPoint>=currentText.length()){
						if(!firstZPress && (Gdx.input.isKeyPressed(Keys.Z) || Input.isPress(Keys.CONTROL_LEFT) || Gdx.input.isTouched())){
							dispose();
						}
					}else
						++currentTextPoint;
				}
				batch.begin();
				FontUtil.draw(batch, currentText.substring(0, currentTextPoint), size, Color.WHITE, 50, 130, (int) (msgbox.getWidth() - 60));
				FontUtil.draw(batch, title, 22, titleColor, getOrPosX(title, 22), 178, (int) (msgbox.getWidth()-60));
				batch.end();
			}
			public void init() {
				currentText=str;
				currentTextPoint=0;
			}
		});
	}
	
	public static BaseScriptExecutor setKeyLocker(Script script,final boolean flag){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				com.rpsg.rpg.system.controller.InputController.currentIOMode = flag ? IOMode.MapInput.MESSAGING : IOMode.MapInput.NORMAL;
			}
		});
	}
	
	private static int getOrPosX(String txt,int size){
		int o=(200/2-FontUtil.getTextWidth(txt, size, Setting.STRING_PADDING_LR)/2);
		return 20+o;
	}
	
	public static BaseScriptExecutor show(Script script,final String msgType){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				show = true;
				final Image i = Res.getNP(Setting.GAME_RES_MESSAGE + msgType);
				msgbox.setDrawable(i.getDrawable());
				titleColor=getColor(msgType);
			}
		});
	}
	
	public static BaseScriptExecutor hide(Script script){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				show = false;
			}
		});
	}
	
	public static void draw(SpriteBatch sb){
		SpriteBatch batch= (SpriteBatch) FG.stage.getBatch();
		if(show)
			if(msgbox.getColor().a<1)
				msgbox.setColor(1,1,1,msgbox.getColor().a+=0.1f);
		if(!show)
			if(msgbox.getColor().a>0)
				msgbox.setColor(1,1,1,msgbox.getColor().a-=0.2f);
		if(msgbox.getColor().a>0){
//			GameUtil.resetBacth(batch);
			msgbox.draw(batch);
		}
	}
	
	public static Color getColor(String mt){
		switch(mt){
		case MsgType.紫:case MsgType.妖梦:case MsgType.梅莉:
			return Color.BLACK;
		default:
			return Color.WHITE;
		}
	}
	
	
}
