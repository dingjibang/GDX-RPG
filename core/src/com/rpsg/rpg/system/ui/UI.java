package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.ImageButton.ImageButtonStyle;

public class UI {
	public static ImageButton imageButton(){
		ImageButtonStyle style = new ImageButtonStyle();
		style.up = Res.getDrawable(Setting.UI_GRAY_IMG);
		style.down = Setting.UI_BUTTON;
		
		return new ImageButton(style);
	}
	
	public static CheckBox checkbox(){
		CheckBoxStyle style = new CheckBoxStyle();
		style.up = Res.getDrawable(Setting.UI_GRAY_IMG);
		style.checkboxOn = Setting.UI_BUTTON;
		style.font = Res.font.get(22);
		return new CheckBox(style);
	}
	
	public static CheckBox checkbox(String fg){
		return checkbox().foreground(Res.get(fg));
	}
	
	public static ScrollPane scrollPane(Actor inner){
		ScrollPane pane = new ScrollPane(inner);
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"scrollbarin.png");
		return pane;
	}
	
	public static ScrollPane miniPane(Actor inner){
		ScrollPane pane = new ScrollPane(inner);
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
		return pane;
	}

	public static TextButton textButton(String text, int fontSize) {
		TextButtonStyle style = new TextButtonStyle();
		style.up = Res.getDrawable(Setting.UI_GRAY_IMG);
		style.down = Setting.UI_BUTTON;
		style.checked = Setting.UI_BUTTON;
		style.font = Res.font.get(fontSize);
		
		TextButton button = new TextButton(text, style);
		
		return button;
	}
	
	public static ButtonStyle redButton(ButtonStyle style){
		style.up = Setting.UI_BUTTON_RED;
		style.down = Setting.UI_BUTTON;
		style.checked = Setting.UI_BUTTON;
		return style;
	}

	public static Image hr(int width) {
		return Res.base().width(width).height(2);
	}
}
