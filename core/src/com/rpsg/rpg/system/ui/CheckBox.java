
package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.gdxQuery.GdxQuery;

/**
 * GDXRPG引擎 checkbox组件
 */
public class CheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox {
	Image fg,other;
	
	int fgoff=0;
	
	boolean hideText = false;
	
	int otherX,otherY;

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
			fg.setX(getX()+(getPrefWidth()/2-fg.getWidth()/2)+fgoff);
			fg.setY(getY()+(getHeight()/2-fg.getHeight()/2));
			fg.setColor(getColor());
			fg.draw(batch);
		}
		if(other!=null){
			other.setX(getX()+otherX);
			other.setY(getY()+otherY);
			other.setColor(getColor());
			other.draw(batch);
		}
	}
	
	public CheckBox hideText(boolean b){
//		getLabel().getColor().a=b?0:1;
//		getLabel().setTouchable(b?Touchable.disabled:Touchable.enabled);
		getLabel().setVisible(!b);
		return this;
	}
	
	public CheckBox onClick(Runnable run){
		return query.click(run).getItem(getClass());
	}
	
	public CheckBox onClick(final CustomRunnable<CheckBox> run){
		return query.click(new Runnable() {
			public void run() {
				run.run(CheckBox.this);
			}
		}).getItem(getClass());
	}
	
	public CheckBox setFgOff(int off){
		fgoff=off;
		return this;
	}
	
	public CheckBox setForeground(Image draw){
		fg=draw;
		return this;
	}
	
	public CheckBox setOther(Image draw){
		other=draw;
		return this;
	}
	
	public CheckBox setOtherPosition(int x,int y){
		otherX=x;otherY=y;
		return this;
	}

}
