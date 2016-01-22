package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.CheckBox;


public class LoginView extends SidebarView {
	public void init() {
		enableXKey = false;
		
		TextFieldStyle tfstyle = new TextFieldStyle();
		tfstyle.font = Res.font.get(20);
		tfstyle.fontColor = Color.GRAY;
		tfstyle.cursor = tfstyle.selection = Res.getDrawable(Setting.UI_BASE_IMG);
		tfstyle.focusedFontColor = Color.WHITE;
		
		$.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"userbox.png")).appendTo(group).setPosition(480, 370);
		$.add(Res.get(Setting.IMAGE_MENU_SYSTEM+"passbox.png")).appendTo(group).setPosition(480, 300);
		
		TextField user,pass;
		$.add(user = new TextField("", tfstyle)).appendTo(group).setPlaceHolder("用户名").setPosition(550, 370).setSize(320,50);
		$.add(pass = new TextField("", tfstyle)).appendTo(group).setPlaceHolder("密码").setPosition(550, 300).setSize(320,50);
		
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_GLOBAL+"optb_s.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_GLOBAL+"optb.png");
		cstyle.font=Res.font.get(22);
		
		$.add(new CheckBox("    记住密码",cstyle).onClick(new CustomRunnable<CheckBox>() {public void run(CheckBox t) {
			
		}})).appendTo(group).setPosition(749, 255);
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
	}
	
}
