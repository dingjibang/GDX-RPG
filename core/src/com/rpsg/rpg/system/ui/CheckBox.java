
package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.gdxQuery.GdxQuery;

/**
 * GDXRPG引擎 checkbox组件
 */
public class CheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox {
	Image fg;
	
	boolean hideText = false;
	

	private GdxQuery query;

	
	public CheckBox (String text, CheckBoxStyle style) {
		super(text, style);
		query=$.add(this);
	}
	
	public CheckBox (CheckBoxStyle style){
		this("",style);
	}
	public CheckBox check(boolean b){
		this.setChecked(b);
		return this;
	}
	
	public CheckBox click(){
		return query.click().getItem(CheckBox.class);
	}
	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		
		if(fg!=null){
			fg.setX(getX()+(getWidth()/2-fg.getWidth()/2));
			fg.setY(getY()+(getHeight()/2-fg.getHeight()/2));
			fg.setColor(getColor());
			fg.draw(batch);
		}
	}
	
	public CheckBox hideText(boolean b){
		getLabel().setVisible(!b);
		return this;
	}
	
	public CheckBox onClick(Runnable run){
		query.click(run);
		return this;
	}
	
	public CheckBox onClick(final CustomRunnable<CheckBox> run){
		query.click(run);
		return this;
	}
	
	public CheckBox foreground(Image draw){
		fg=draw;
		return this;
	}

	public CheckBox text(String string) {
		setText(string);
		return this;
	}

}
