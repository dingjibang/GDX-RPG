package com.rpsg.rpg.system.ui;

import com.rpsg.rpg.object.base.CustomRunnable;

/**
 * frameAble label widget.
 * @author dingjibang
 *
 */
public class FrameLabel extends Label {
	
	CustomRunnable<Label> frame;
	
	public FrameLabel(Object text, int fontsize) {
		super(text, fontsize);
	}
	
	public FrameLabel frame(){
		if(frame==null)
			return this;
		frame.run(this);
		return this;
	}
	
	public FrameLabel frame(CustomRunnable<Label> onFrame){
		this.frame = onFrame;
		return this;
	}
	
	@Override
	public void act(float delta) {
		frame();
		super.act(delta);
	}

}
