package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

/**
 * A Item(or equip.) image list view
 * @author dingjibang
 *
 */
public class ImageList extends Group{
	
	java.util.List<Icon> items;
	Icon current;
	
	public ImageList(java.util.List<Icon> items) {
		super();
		this.items=items;
	}
	
	public Icon getCurrent(){
		return current;
	}
	
	public ImageList generate(){
		Table inner = new Table();
		ScrollPane pane =new ScrollPane(inner);
		pane.setSize(getWidth(), getHeight());
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"scrollbarin.png");
		int padding = 6;
		inner.padTop(9).padBottom(1);
		int col = (int) (getWidth()/(70+padding*2));
		int row = 0;
//		System.out.println(items);
		int currentCol = -1;
		
		inner.align(Align.topLeft);
		
		for(final Icon i:items){
			if(currentCol++ == col-1){
				row++;
				currentCol = 0;
				inner.row();
			}
			inner.add($.add(i).setPosition(i.getWidth()*currentCol+padding, i.getHeight()*row+padding).setSize(70, 70).onClick(new Runnable() {public void run() {
				setCurrent(i);
			}}).getItem()).align(Align.topLeft).pad(padding).prefSize(70,70);
		}
		
		inner.setSize(getWidth(), (row+1)*(70+padding/2));
		
		addActor(pane);
		return this;
	}

	protected void setCurrent(Icon i) {
		current=i;
	}
	
}
