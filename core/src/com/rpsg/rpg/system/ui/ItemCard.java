package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.system.base.Res;

public class ItemCard extends WidgetGroup {
	
	Image bg,icon,outer;
	Label name;
	BaseItem item;
	
	boolean select = false;

	public ItemCard(BaseItem item) {
		this.item = item;
		
		$.add(icon = Res.get(item.getIcon())).fadeOut().appendTo(this);
		$.add(outer = Res.get(Setting.IMAGE_BATTLE + "card_outer.png")).setTouchable(null).fadeOut().appendTo(this);
		
		$.add(name = Res.get(item.name, 22)).fadeOut().appendTo(this);
		$.add(bg = Res.get(Setting.IMAGE_BATTLE + "card.png")).appendTo(this);
		
		
	}
	
	public ItemCard onClick(Runnable callback){
		super.setSize(100,100);
		bg.onClick(callback);
		return this;
	}
	
	public ItemCard select(){
		this.select = true;
		return this;
	}
	
	public ItemCard animate(){
		if(select) outer.addAction(Actions.forever(Actions.sequence(Actions.alpha(.8f,.5f),Actions.alpha(.5f,.5f))));
//		bg.addAction(action);
		return this;
	}
	
	@Override
	public void setWidth(float width) {
	}
	
	@Override
	public void setHeight(float height) {
	}
	
	@Override
	public void setSize(float width, float height) {
	}
}
