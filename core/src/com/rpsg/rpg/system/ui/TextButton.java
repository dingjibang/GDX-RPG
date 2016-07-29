package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.object.base.Index.IndexType;

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
	
	public String text(){
		return getText().toString();
	}
	
	public TextButton click(){
		return query.click().getItem(getClass());
	}

	public TextButton style(CustomRunnable<ButtonStyle> call) {
		call.run(this.getStyle());
		return this;
	}

	public TextButton object(IndexType actor) {
		setUserObject(actor);
		return this;
	}

	public Object x(float x) {
		setX(x);
		return this;
	}
	
	public Object y(float y) {
		setY(y);
		return this;
	}
	
	public Object position(float x, float y) {
		setPosition(x, y);
		return this;
	}

}
