
package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.gdxQuery.GdxQuery;

/**
 * GDXRPG引擎 checkbox组件
 */
public class MenuCheckBox extends com.badlogic.gdx.scenes.scene2d.ui.CheckBox {
	Image fg,other;
	
	int fgoff=0;
	
	boolean hideText = false;
	
	int otherX,otherY;

	private GdxQuery query;

	
	public MenuCheckBox (String text, CheckBoxStyle style) {
		super(text, style);
		query=$.add(this);
	}
	
	public MenuCheckBox (CheckBoxStyle style){
		this("",style);
	}
	public MenuCheckBox check(boolean b){
		this.setChecked(b);
		return this;
	}
	
	public MenuCheckBox click(){
		return query.click().getItem(MenuCheckBox.class);
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
	
	public MenuCheckBox hideText(boolean b){
//		getLabel().getColor().a=b?0:1;
//		getLabel().setTouchable(b?Touchable.disabled:Touchable.enabled);
		getLabel().setVisible(!b);
		return this;
	}
	
	public MenuCheckBox onClick(Runnable run){
		return query.click(run).getItem(getClass());
	}
	
	public MenuCheckBox onClick(final CustomRunnable<MenuCheckBox> run){
		return query.click(new Runnable() {
			public void run() {
				run.run(MenuCheckBox.this);
			}
		}).getItem(getClass());
	}
	
	public MenuCheckBox setFgOff(int off){
		fgoff=off;
		return this;
	}
	
	public MenuCheckBox foreground(Image draw){
		fg=draw;
		return this;
	}
	
	public MenuCheckBox setOther(Image draw){
		other=draw;
		return this;
	}
	
	public MenuCheckBox setOtherPosition(int x,int y){
		otherX=x;otherY=y;
		return this;
	}

}
