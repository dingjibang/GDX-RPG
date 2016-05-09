package com.rpsg.rpg.object.rpg;

import com.rpsg.gdxQuery.CustomRunnable;

public interface Selectable {
	public void select(CustomRunnable<Target> onSelect);
}
