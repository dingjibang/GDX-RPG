package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.system.base.Res;

public class SpellcardIcon extends Actor{
	Label label;
	Image icon,bg;
	public Spellcard sc;
	boolean select;
	GdxQuery query;
	
	public SpellcardIcon(Spellcard sc) {
		super();
		this.sc = sc;
		generateIcon(sc);
		label = new Label(sc.name,22);
		label.setText(sc.name);
		label.setAlignment(Align.center);
		bg = Res.get(Setting.UI_BASE_IMG);
		query = $.add(this);
	}
	
	public SpellcardIcon onClick(final CustomRunnable<SpellcardIcon> run){
		query.click(new Runnable(){
			public void run() {
				run.run(SpellcardIcon.this);
			}
		});
		return this;
	}
	
	public SpellcardIcon click(){
		query.click();
		return this;
	}
	
	public SpellcardIcon setText(String text) {
		label.setText(text);
		return this;
	}
	
	public SpellcardIcon generateIcon(BaseItem baseItem) {
		icon=Res.get(baseItem.getIcon()).disableTouch();
		return this;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(select){
			bg.setPosition(getX(), getY());
			bg.setSize(getWidth(), getHeight());
			bg.a(.2f);
			bg.draw(batch);
		}
		label.setPosition((int)(getX()+getHeight()+25), (int)(getY()));
		label.setHeight(getHeight());
		icon.setPosition(getX()+7, getY()+7);
		icon.setHeight(getHeight()-14);
		icon.setWidth(getHeight()-14);
		icon.draw(batch);
		label.draw(batch, parentAlpha);
		super.draw(batch, parentAlpha);
	}
	
	public SpellcardIcon select(boolean flag){
		select = flag;
		return this;
	}
	
	public boolean select(){
		return select;
	}
}
