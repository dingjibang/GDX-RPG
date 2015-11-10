package com.rpsg.rpg.system.ui;

import java.lang.reflect.Field;

import com.badlogic.gdx.utils.StringBuilder;
import com.rpsg.gdxQuery.CustomRunnable;

/**
 * frameAble label widget.
 * @author dingjibang
 *
 */
public class FrameLabel extends Label {
	
	CustomRunnable<FrameLabel> frame;
	
	StringBuilder text;
	public FrameLabel(Object text, int fontsize) {
		super(text, fontsize);
		try {
			Field f = this.getClass().getSuperclass().getSuperclass().getDeclaredField("text");
			f.setAccessible(true);
			this.text = (StringBuilder)f.get(this);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public FrameLabel frame(){
		if(frame==null)
			return this;
		frame.run(this);
		return this;
	}
	
	public FrameLabel frame(CustomRunnable<FrameLabel> onFrame){
		this.frame = onFrame;
		return this;
	}
	
	public void setNoLayoutText(CharSequence newText) {
		text.setLength(0);
		text.append(newText);
		this.layout();
	}
	
	@Override
	public void act(float delta) {
		frame();
		super.act(delta);
	}

}
