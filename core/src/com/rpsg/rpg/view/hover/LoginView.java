package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;


public class LoginView extends SidebarView {
	public void init() {
		enableXKey = false;
		
		TextFieldStyle tstyle = new TextFieldStyle();
		tstyle.font = Res.font.get(20);
		tstyle.fontColor = Color.GRAY;
		tstyle.cursor = tstyle.selection = Res.getDrawable(Setting.UI_BASE_IMG);
		tstyle.focusedFontColor = Color.WHITE;
		
		$.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"userbox.png")).appendTo(group).setPosition(480, 370);
		$.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"passbox.png")).appendTo(group).setPosition(480, 300);
		
		$.add(new TextField("用户名", tstyle)).appendTo(group).setPosition(550, 370).setSize(320,50);
		$.add(new TextField("密码", tstyle)).appendTo(group).setPosition(550, 300).setSize(320,50);
		
	}
	
}
