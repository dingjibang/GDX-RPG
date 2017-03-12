package com.rpsg.rpg.ui.widget;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Res;

public class LabelImageCheckbox extends Group{
	
	Image downimage, upimage, downbg, upbg;
	Label text;
	
	int padding = 10;
	
	boolean checked = false;
	
	Runnable onClick = null;
	
	public LabelImageCheckbox(String text, int fontSize, Image downimage, Image upimage, Image downbg, Image upbg) {
		this.downbg = downbg;
		this.downimage = downimage.disableTouch();
		this.upbg = upbg;
		this.upimage = upimage.disableTouch();
		this.text = Res.text.getLabel(text, fontSize);
		
		addActor(downbg);
		addActor(upbg);
		addActor(downimage);
		addActor(upimage);
		addActor(this.text);
		
		$.add(upbg, downbg).click(() -> {
			checked(!checked);
			
			if(onClick != null)
				onClick.run();
		});
		
		checked(false);
		
	}
	
	public LabelImageCheckbox click(Runnable onClick){
		this.onClick = onClick;
		return this;
	}
	
	public LabelImageCheckbox padding(int pad){
		this.padding = pad;
		sizeChanged();
		return this;
	}
	
	public boolean checked(){
		return checked;
	}

	public LabelImageCheckbox checked(boolean flag){
		checked = flag;
		
		$.add(upbg, upimage).visible(!flag);
		$.add(downbg, downimage).visible(flag);
		
		return this;
	}
	
	protected void sizeChanged() {
		int width = text.prefWidth() + (int)downimage.getWidth() + padding;
		int x = (int)getWidth() / 2 - width / 2;
		text.setSize(text.prefWidth(), getHeight());
		text.setPosition(padding + downimage.getWidth() + x, 0);
		
		$.add(downimage, upimage).position(x, getHeight() / 2 - downimage.getHeight() / 2);
		
		$.add(upbg, downbg).size(getWidth(), getHeight());
		
		super.sizeChanged();
	}
	
	protected void positionChanged() {
		sizeChanged();
		super.positionChanged();
	}
	
	public LabelImageCheckbox size(int width, int height){
		setSize(width, height);
		return this;
	}
}
