package com.rpsg.rpg.system.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Align;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.utils.game.GameUtil;

public class Status extends Group {

	Label label,group;
	List<String> history = new ArrayList<>();
	
	public Status() {
		addActor(Res.get(Setting.UI_BASE_IMG).size(GameUtil.screen_width, 28).color(0,0,0,.8f));
		addActor(label = Res.get("asfdasas", 20).align(Align.center).position(0, 2).width(GameUtil.screen_width));
//		addActor(Res.);
		label.getStyle().font.getData().markupEnabled = true;
	}
	
	public Status add(String str){
		label.setText(str);
		append(str);
		return this;
	}
	
	private void append(String str){
		history.add(str);
	}
	
}
