package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

public class ListItem extends Table{
	Label label = Res.get("", 22);
	Image icon = Res.base().width(0).height(0).hide();
	Cell<Image> iconCell;
	Cell<Label> labelCell;
	boolean selected = false;
	
	public ListItem() {
		left();
		addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("your mother fucker");
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		setDebug(true);
		iconCell = add(icon).left();
		labelCell = add(label.left()).left();
	}
	
	public ListItem addText(String text,int fontSize){
		this.label = Res.get(text, fontSize);
		labelCell.setActor(label.left()).left();
		return this;
	}
	
	public ListItem addIcon(Image icon){
		this.icon = icon;
		iconCell.setActor(icon).padRight(30);
		return this;
	}
	
	public boolean select(){
		return selected;
	}
	
	public ListItem select(boolean s){
		this.selected = s;
		setBackground(s ? Res.getDrawable(Setting.UI_GRAY_IMG) : null);
		return this;
	}
	
}
