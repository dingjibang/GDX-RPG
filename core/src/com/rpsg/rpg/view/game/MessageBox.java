package com.rpsg.rpg.view.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.Script;

/**
 * GDX-RPG 对话消息框
 */
public class MessageBox {
	
	/**对话框*/
	private Image box;
	/**对话文字*/
	private BitmapFont font;
	
	/**要显示的文本*/
	private String text;
	/**要显示的标题*/
	private String title;
	/**正显示的文本*/
	private String currentText;
	/**标题字体颜色*/
	private Color color = Color.WHITE;
	
	
	/**是否显示对话框*/
	boolean display = false;
	
	public MessageBox() {
		box = new com.rpsg.rpg.ui.Image();
	}
	
	public void draw(Batch batch) {
		
	}
	
	public boolean finished() {
		return text == null || text.length() == currentText.length();
	}

	public void say(Script _script, JsonValue cfg, String title, String str) {
//		color = cfg == .get("")
	}
	
	
}
