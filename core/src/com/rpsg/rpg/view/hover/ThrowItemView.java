package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Align;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Icon;
import com.rpsg.rpg.system.ui.Image;


public class ThrowItemView extends SidebarView{

	public void init() {
		Icon icon = (Icon) param.get("item");
		group.addActor(Res.get(Setting.UI_BASE_IMG).size(535, 130).position(400, 240).a(.13f));
		group.addActor(new Image(icon).X(590).Y(300).scale(.7f));
		group.addActor(Res.get(icon.item.name, 35).align(425, 250).width(475).overflow(true).color(Color.valueOf("ff6600")));
//		stage.setDebugAll(true);
	}
}
