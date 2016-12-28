package com.rpsg.rpg.object.base.items;

import com.badlogic.gdx.scenes.scene2d.EventListener;

public interface GetItemable {
	public BaseItem getItem();
	public boolean addListener(EventListener InputListener);
}
