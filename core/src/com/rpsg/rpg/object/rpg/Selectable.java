package com.rpsg.rpg.object.rpg;

import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;

public interface Selectable {
	public void select(CustomRunnable<Target> onSelect,ItemDeadable deadable);
}
