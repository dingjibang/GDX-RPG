package com.rpsg.rpg.view.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.rpsg.lazyFont.LazyBitmapFont;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.core.Script;
import com.rpsg.rpg.core.Text;
import com.rpsg.rpg.object.game.ScriptExecutor;
import com.rpsg.rpg.ui.Image;
import com.rpsg.rpg.util.InputProcessorEx;

/**
 * GDX-RPG 对话消息框
 */
public class MessageBox extends InputProcessorEx{
	
	/**对话框*/
	private Image box;
	/**对话文字*/
	private LazyBitmapFont font, titleFont;
	
	
	/**要显示的文本*/
	private String text;
	/**要显示的标题*/
	private String title;
	/**文本字号*/
	private int fontSize;
	
	/**正在显示的文本*/
	private String currentText;

	private JsonValue defaultCfg;
	
	public MessageBox() {
		if((defaultCfg  = Game.prop.get("msg", "default")) == null)
			throw new GdxRuntimeException("missing default property of MessageBox!");
		
		box = Res.sync(Path.MESSAGE + defaultCfg.getString("backgroundImage"));
		box.setScaling(Scaling.fit);
		box.setWidth(Game.STAGE_WIDTH - 40);
		box.setOrigin(Align.bottom);
		box.setPosition(20, -35);
		box.setColor(1, 1, 1, 0);
		
	}
	
	public void draw(Batch batch) {
		box.act(Gdx.graphics.getDeltaTime());
		
		if(!box.isTransparent()){
			batch.begin();
			
			box.draw(batch, 1);
			
			
			font.setColor(Color.WHITE);
			
			font.getCache().getColor().a = titleFont.getCache().getColor().a = box.getColor().a;
			
			font.draw(batch, currentText,  58, 143, (box.getWidth() - 60), 10, false);
			titleFont.draw(batch, title,  170 - Text.getTextWidth(titleFont, title) / 2, 211);
			
			batch.end();
		}
		
	}
	
	public void allowInput(boolean flag) {
		bubble(flag);
	}
	
	public void hide() {
		box.clearActions();
		box.addAction(Actions.fadeOut(.5f));
	}
	
	public void say(Script _script, JsonValue _cfg, String title, String _text) {
		_script.set(new ScriptExecutor() {
			
			/**输入控制*/
			InputProcessor input;
			/**游标*/
			int position = 0, offset = 100, colorPosition = 0;
			/**当前文字颜色*/
			String textColor;
			
			public void create() {
				//读取配置
				JsonValue cfg = _cfg; 
				
				if(cfg == null)
					cfg = defaultCfg;
				
				box.setDrawable(Res.getDrawable(Path.MESSAGE + cfg.getString("backgroundImage"))); 
				
				//初始化变量
				text = _text;
				currentText = "";
				fontSize = cfg.has("fontSize") ? cfg.getInt("fontSize") : defaultCfg.getInt("fontSize");
				font = Res.text.get(fontSize);
				font.getData().markupEnabled = true;
				titleFont = Res.text.get(cfg.has("titleFontSize") ? cfg.getInt("titleFontSize") : defaultCfg.getInt("titleFontSize"));
				titleFont.setColor(Color.valueOf(cfg.getString("titleTextColor")));
				textColor = "ffffff";
				MessageBox.this.title = title == null ? cfg.getString("title") : title;
				
				//淡入聊天图片
				box.clearActions();
				box.addAction(Actions.fadeIn(.5f));
				
				//加入监听器
				input = Game.view.addProcessor(new InputProcessorEx(){
					public boolean keyDown(int keycode) {
						if(keycode == Keys.CONTROL_LEFT || keycode == Keys.Z) {
							if(!finished())
								forceFinish();
							else
								executed();
						}
						return super.keyDown(keycode);
					}
					
					public boolean touchUp(int screenX, int screenY, int pointer, int button) {
						keyDown(Keys.Z);
						return super.touchDown(screenX, screenY, pointer, button);
					}
				});
			}
			
			public void act() {
				boolean pressCtrl = Gdx.input.isKeyPressed(Keys.CONTROL_LEFT);
				if(pressCtrl){
					forceFinish();
					executed();
					return;
				}
				
				offset -= 30;
				if(offset <= 0){
					offset = 100;
					next(1);
				}
				
				currentText = calc();
				
			}
			
			/**跳到下N个字符*/
			public void next(int count) {
				for(int i = 0; i < count; i ++){
					if(finished()) 
						return;
					position ++;
					calc();
				}
			}
			
			/**
			 * 计算当前[要在屏幕]上显示的字符，添加/补全颜色markup标志
			 */
			public String calc() {
				String originText = text.substring(0, position);
				
				if(text.length() - originText.length() > 0) {
					if(originText.endsWith("[") && text.substring(0, position + 1).endsWith("#")) {
						textColor = text.substring(position + 1, position + 7);
						position += 9;
						colorPosition = position - 1;
						originText = originText.substring(0, originText.length() - 2);
					}
					
					if(originText.endsWith("[") && text.substring(0, position + 1).endsWith("]")) {
						textColor = "ffffff";
						position += 1;
						originText = originText.substring(0, originText.length() - 1);
					}
				}
				
				if(!textColor.equals("ffffff")) {
					originText = text.substring(0, colorPosition) + "[#" + textColor + "]" + text.substring(colorPosition, position) + "[]"; 
				}
				
				font.setColor(Color.WHITE);
				
				if(Text.getTextWidth(font, originText) > (box.getWidth() - 60)){
					text = text.substring(0, position - 1) + "\n" + text.substring(position - 1, text.length());
					originText += "\n";
				}
				
				return originText;
			}
			
			
			public void executed() {
				Game.view.removeInputProcessor(input);
				currentText = calc();
				super.executed();
			}
			
			public boolean finished() {
				return position == text.length();
			}
			
			public void forceFinish() {
				while(!finished())
					next(1);
			}
		});
	}
	
	
}
