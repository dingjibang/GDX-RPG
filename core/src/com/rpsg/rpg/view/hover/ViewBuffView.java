package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Buff;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;


public class ViewBuffView extends HoverView{
	
	public void init() {
		WidgetGroup group = new WidgetGroup();
		stage.addActor(group);
		
		Buff buff = (Buff) param.get("buff");
		int left = (int) param.get("left");
		int top = (int) param.get("top");
		
		group.setPosition(left, top);
		
		$.add(Res.get(Setting.UI_BASE_IMG).color(1,1,1,.7f)).appendTo(group).fillParent();
		$.add(Res.get(buff.name,16));
	}
	
}
