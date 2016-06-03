package com.rpsg.rpg.object.base.items;

import com.badlogic.gdx.scenes.scene2d.EventListener;

public interface GetItemAble {
	public BaseItem getItem();
	public boolean addListener(EventListener InputListener);
}
