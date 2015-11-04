package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.CustomRunnable;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Counter;
import com.rpsg.rpg.system.ui.Icon;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.TextButton;


public class ThrowItemView extends SidebarView{

	public void init() {
		Icon icon = (Icon) param.get("item");
		final Counter counter;
		group.addActor(Res.get(Setting.UI_BASE_IMG).size(545, 160).position(400, 280).a(.13f));
		group.addActor(new Image(icon).x(435).y(300).scale(.6f));
		group.addActor(Res.get(icon.item.name, 38).position(575, 365).width(330).overflow(true).color(Color.valueOf("ff6600")));
		group.addActor(Res.get("持有 "+icon.item.count+" 个", 22).position(575, 330).width(330).overflow(true));
		group.addActor(counter = new Counter(icon.item.count).position(435, 120));
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
		$.add(new TextButton("确定丢弃",tstyle)).appendTo(group).setSize(454,55).setPosition(435,40).onClick(new Runnable() {@SuppressWarnings("unchecked")
			public void run() {
				if(counter.get() != 0)
					((CustomRunnable<Integer>)param.get("callback")).run(counter.get());
				ThrowItemView.this.keyDown(Keys.ESCAPE);
			}
		}).getCell().prefSize(454,55);
		
//		stage.setDebugAll(true);
	}
}
