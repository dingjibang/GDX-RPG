package com.rpsg.rpg.object.base;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

public class BattleRes {
	public static TextButtonStyle textButtonStyle;
	public static Drawable heroSelectBox;
	
	static{
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.down = Setting.UI_BUTTON;
		textButtonStyle.up = Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"throwbut.png");
		textButtonStyle.font = Res.font.get(22);
		
		heroSelectBox = Res.getDrawable(Setting.IMAGE_BATTLE+"herobox_select.png");
	}
}
