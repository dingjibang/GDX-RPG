package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.RPG;
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


public class Msg {
	public Image msgbox;
	private BitmapFont font;
	
	public Msg() {
		msgbox=new Image(Setting.MESSAGE+MsgType.正常.path());
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
		font = Res.font.get(22);
		show=false;
		Logger.info("文本模块初始化完成。");
	}
	
	String currentText="";
	int currentTextPoint=0;
	int TEXT_DISPLAY_SPEED=30;
	private int DISPLAY_OFFSET=0;
	boolean show=false;
	boolean firstZPress=false;
	Color titleColor=Color.WHITE;
	public BaseScriptExecutor say(final Script script,final String str,final String title,final int size){
		firstZPress=false;
		return script.set(new ScriptExecutor(script) {
			SpriteBatch batch= (SpriteBatch) RPG.ctrl.fg.stage.getBatch();
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
				font.draw(batch, currentText.substring(0, currentTextPoint), 50, 130,(msgbox.getWidth()-100),10,false);
				font.setColor(titleColor);
				font.draw(batch, title, getOrPosX(title, 22), 178, (int) (msgbox.getWidth()-60),10,false);
				font.setColor(Color.WHITE);
				batch.end();
			}
			public void init() {
				currentText=str;
				currentTextPoint=0;
			}
		});
	}
	
	public BaseScriptExecutor setKeyLocker(Script script,final boolean flag){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				com.rpsg.rpg.system.controller.InputController.currentIOMode = flag ? IOMode.MapInput.MESSAGING : IOMode.MapInput.NORMAL;
			}
		});
	}
	
	private int getOrPosX(String txt,int size){
		int o=190/2+(Res.font.getTextWidth(txt, size))/2;
		return o;
	}
	
	public BaseScriptExecutor show(Script script,final String msgType){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				show = true;
				final Image i = Res.getNP(Setting.MESSAGE + msgType);
				msgbox.setDrawable(i.getDrawable());
				titleColor=getColor(msgType);
			}
		});
	}
	
	public BaseScriptExecutor hide(Script script){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				show = false;
			}
		});
	}
	
	public void draw(SpriteBatch sb){
		SpriteBatch batch= (SpriteBatch) RPG.ctrl.fg.stage.getBatch();
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
	
	public Color getColor(String mt){
		if(mt.equals(MsgType.紫.path()) || mt.equals(MsgType.梅莉.path()) || mt.equals(MsgType.正常.path()))
			return Color.BLACK;
		else
			return Color.WHITE;
	}
	
	
}
