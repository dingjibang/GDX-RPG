package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.system.base.Res;


public class LoginView extends SidebarView {
	public void init() {
		TextFieldStyle tstyle = new TextFieldStyle();
		tstyle.font = Res.font.get(20);
		TextField text = new TextField("", tstyle);
		$.add(text).appendTo(group);
	}
	
}
