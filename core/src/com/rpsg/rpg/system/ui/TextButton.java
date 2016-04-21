package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;

/** A button with a child {@link Label} to display text.
 * @author Nathan Sweet */
public class TextButton extends com.badlogic.gdx.scenes.scene2d.ui.TextButton {

	private GdxQuery query;
	
	public TextButton(String text, TextButtonStyle style) {
		super(text, style);
		query = $.add(this);
	}
	
	public TextButton check(boolean b){
		this.setChecked(b);
		return this;
	}
	
	public TextButton onClick(Runnable run){
		return query.click(run).getItem(getClass());
	}
	
	public TextButton click(){
		return query.click().getItem(getClass());
	}

}
