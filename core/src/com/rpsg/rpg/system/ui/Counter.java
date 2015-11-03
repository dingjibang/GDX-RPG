package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

public class Counter extends Group {
	private int max;

	public Counter(int max) {
		setMax(max);
		generateUI();
	}

	private void generateUI() {
		addActor(Res.get(Setting.UI_BASE_IMG).size(166,133));
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
	
	public Counter position(int x,int y){
		setPosition(x, y);
		return this;
	}
}
