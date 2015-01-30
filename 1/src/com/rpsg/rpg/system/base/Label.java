/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.rpsg.rpg.system.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.StringBuilder;
import com.rpsg.rpg.utils.display.FontUtil;

public class Label extends Widget {
	static private final Color tempColor = new Color();
	
	private int width,pad;
	
	private final StringBuilder text = new StringBuilder();
	public int fontSize;

	public Label (CharSequence text,int fontSize) {
		if (text != null) this.text.append(text);
		this.fontSize=fontSize;
		setSize(getWidth(), getHeight());
	}



	/** @param newText May be null. */
	public void setText (CharSequence newText) {
		if (newText instanceof StringBuilder) {
			if (text.equals(newText)) return;
			text.setLength(0);
			text.append((StringBuilder)newText);
		} else {
			if (newText == null) newText = "";
			if (textEquals(newText)) return;
			text.setLength(0);
			text.append(newText);
		}
		invalidateHierarchy();
	}

	public boolean textEquals (CharSequence other) {
		int length = text.length;
		char[] chars = text.chars;
		if (length != other.length()) return false;
		for (int i = 0; i < length; i++)
			if (chars[i] != other.charAt(i)) return false;
		return true;
	}

	public String getText () {
		return text.toString();
	}

	public void invalidate () {
		super.invalidate();
	}



	public void layout () {

	}

	public void draw (Batch batch, float parentAlpha) {
		validate();
		Color color = tempColor.set(getColor());
		color.a *= parentAlpha;
//		cache.tint(color);
//		cache.setPosition(getX(), getY());
//		cache.draw(batch);
		FontUtil.draw((SpriteBatch)batch, getText().toString(), fontSize, color, (int)getX(), (int)getY(),(int)getWidth(),pad,0);
	}
	
	public float getWidth() {
		return width;
	}
	public Label setWidth(int width) {
		this.width = width;
		return this;
	}
	
	public Label setPad(int pad) {
		this.pad = pad;
		return this;
	}

	public int getPad() {
		return pad;
	}
}
