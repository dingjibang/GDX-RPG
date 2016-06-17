package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.InputProcessorEx;
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
	private BitmapFont font,titleFont;
	
	public Msg() {
		msgbox=new Image(Setting.MESSAGE+MsgType.正常.path());
		float ss=msgbox.getWidth();
		msgbox.setWidth(GameUtil.stage_width-40);
		ss=ss/msgbox.getWidth();
		msgbox.setHeight((float)msgbox.getHeight()/ss);
		msgbox.setX(GameUtil.stage_width/2-msgbox.getWidth()/2);
		msgbox.setY(25);
		msgbox.setColor(1,1,1,0);
		currentText="";
		currentTextPoint=0;
		TEXT_DISPLAY_SPEED=30;
		DISPLAY_OFFSET=0;
		show=false;
		titleFont = Res.font.get(22);
		Logger.info("文本模块初始化完成。");
	}
	
	String currentText="";
	int currentTextPoint=0;
	int TEXT_DISPLAY_SPEED=30;
	private int DISPLAY_OFFSET=0;
	boolean show=false;
	boolean firstZPress=false;
	Color titleColor=Color.WHITE;

	public BaseScriptExecutor say(final Script script, final String str, final String title, final int size) {
		firstZPress = false;

		return script.set(new ScriptExecutor(script) {

			SpriteBatch batch = (SpriteBatch) RPG.ctrl.fg.stage.getBatch();
			String color = "ffffff";
			int colorPoint = 0;
			InputProcessorEx ex = null;
			
			
			public void step(){
				boolean isPressCtrl = Gdx.input.isKeyPressed(Keys.CONTROL_LEFT);
				
				if(!isPressCtrl){
					TEXT_DISPLAY_SPEED = 30;
				}else{
					TEXT_DISPLAY_SPEED = 100;
				}
				
				DISPLAY_OFFSET -= TEXT_DISPLAY_SPEED;
				if (DISPLAY_OFFSET <= 0) {
					DISPLAY_OFFSET = 100;
					if (!isEnd()){
						if(!isPressCtrl)
							++currentTextPoint;
						else if(isPressCtrl && currentTextPoint + 2 <= currentText.length())
							next(4);
						else if(isPressCtrl)
							dispose();
					}else if(isPressCtrl)
						dispose();
				}
				
				String txt = calc();
				batch.begin();
				font.draw(batch, txt, 50, 130, (msgbox.getWidth() - 60), 10, false);
				titleFont.setColor(titleColor);
				titleFont.draw(batch, title, getOrPosX(title, 22), 178, (int) (msgbox.getWidth() - 60), 10, false);
				batch.end();
			}

			public void init() {
				font = Res.font.get(size);
				font.setColor(Color.WHITE);
				font.getData().markupEnabled = true;
				currentText = str;
				currentTextPoint = 0;
				
				ex = RPG.input.addListener(new InputProcessorEx(){
					public boolean keyTyped(char character) {
						boolean isZ = (character == 'z' || character == 'Z'); 
						if (isEnd() && !firstZPress && isZ)
							dispose();
						else if(!isEnd() && isZ)
							next(currentText.length() - currentTextPoint);
						return super.keyTyped(character);
					}
					
					public boolean touchUp(int screenX, int screenY, int pointer, int button) {
						if(isEnd())
							dispose();
						else
							next(currentText.length() - currentTextPoint);
						return super.touchUp(screenX, screenY, pointer, button);
					}
				});
			}
			
			public boolean isEnd(){
				return currentTextPoint >= currentText.length();
			}
			
			public String next(int count){
				String calced = null;
				for(int i=0;i<count;i++){
					if(!isEnd())
						currentTextPoint++;
					calced = calc();
				}
				return calced;
			}
			
			public String calc(){
				String txt = currentText.substring(0, currentTextPoint);
				
				if(currentText.length() - txt.length() > 0){
					if(txt.endsWith("[") && currentText.substring(0, currentTextPoint + 1).endsWith("#")){
						color = currentText.substring(currentTextPoint + 1,currentTextPoint + 7);
						currentTextPoint += 9;
						colorPoint = currentTextPoint - 1;
						txt = txt.substring(0, txt.length() - 2);
					}
					
					if(txt.endsWith("[") && currentText.substring(0, currentTextPoint + 1).endsWith("]")){
						color = "ffffff";
						currentTextPoint += 1;
						txt = txt.substring(0, txt.length() - 1);
					}
				}
				
				if(!color.equals("ffffff")) {
					txt = currentText.substring(0,colorPoint) + "[#" + color + "]" + currentText.substring(colorPoint,currentTextPoint) + "[]"; 
				}
				
				font.setColor(Color.WHITE);
				
				if(font.getCache().addText(txt, 0, 0).width > (msgbox.getWidth() - 60)){
					currentText = currentText.substring(0,currentTextPoint - 1) + "\n" + currentText.substring(currentTextPoint - 1, currentText.length());
					txt += "\n";
				}
				
				return txt;
			}
			
			public void dispose() {
				RPG.input.removeListener(ex);
				super.dispose();
			}

		});
	}
	
	public BaseScriptExecutor setKeyLocker(Script script,final boolean flag){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				com.rpsg.rpg.system.controller.InputController.currentIOMode = flag ? IOMode.MapInput.messaging : IOMode.MapInput.normal;
			}
		});
	}
	
	private int getOrPosX(String txt,int size){
		int o=80;
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
		if(mt.equals(MsgType.紫.path()) || mt.equals(MsgType.梅莉.path()) || mt.equals(MsgType.魔理沙.path()) || mt.equals(MsgType.正常.path()))
			return Color.BLACK;
		else
			return Color.WHITE;
	}
	
	
}
