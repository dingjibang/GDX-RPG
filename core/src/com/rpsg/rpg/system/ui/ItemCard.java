package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.system.base.Res;

public class ItemCard extends WidgetGroup {
	
	Image bg,icon,outer,bg2;
	Label name;
	public BaseItem item;
	
	boolean select = false;

	public ItemCard(BaseItem item) {
		this.item = item;
		
		boolean hasItem = item != null;
		
		$.add(outer = Res.get(Setting.IMAGE_BATTLE + "card_outer.png")).setTouchable(null).setPosition(-27, -27).fadeOut().appendTo(this);
		
		$.add(bg2 = Res.get(Setting.IMAGE_BATTLE + "card2.png")).fadeOut().appendTo(this);
		$.add(bg = Res.get(Setting.IMAGE_BATTLE + "card.png")).appendTo(this);
		$.add(icon = Res.get(hasItem ? item.getIcon() : Setting.UI_BASE_IMG)).fadeOut().appendTo(this).setPosition(41, 68).setSize(100, 100).setTouchable(null);
		$.add(name = Res.get(hasItem ? item.name : "无物品", 22).center()).fadeOut().appendTo(this).setWidth(180).setY(20).setTouchable(null);
		
	}
	
	public ItemCard onClick(Runnable callback){
		bg.onClick(callback);
		return this;
	}
	
	public ItemCard select(){
		this.select = true;
		return this;
	}
	
	@Override
	public boolean addListener(EventListener listener) {
		return bg2.addListener(listener);
	}
	
	public ItemCard animate(Runnable callback){
		bg.addAction(Actions.sequence(Actions.scaleTo(0, 1, .2f),Actions.run(()->{
			if(select) outer.addAction(Actions.forever(Actions.sequence(Actions.alpha(.8f,.5f),Actions.alpha(.5f,.5f))));
			bg2.setScaleX(0);
			bg2.setAlpha(select ? 1 : .8f);
			bg2.addAction(Actions.scaleTo(1, 1, .1f));
			name.setColor(.2f,.2f,.2f,0);
			name.addAction(Actions.delay(.2f,Actions.sequence(Actions.fadeIn(.3f),Actions.run(callback))));
			icon.setScale(0,1);
			icon.addAction(Actions.scaleTo(1, 1, .1f));
			icon.setAlpha(1);
		})));
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
