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
	
	public ImageList(java.util.List<Icon> items) {
		super();
		this.items=items;
	}
	
	public ImageList generate(){
		Table inner = new Table();
		ScrollPane pane =new ScrollPane(inner);
		pane.setSize(getWidth(), getHeight());
		
		int padding = 6;
		inner.padTop(9).padBottom(1);
		int col = (int) (getWidth()/(70+padding*2));
		int row = 0;
//		System.out.println(items);
		int currentCol = -1;
		
		inner.align(Align.topLeft);
		
		for(Icon i:items){
			if(currentCol++ == col-1){
				row++;
				currentCol = 0;
				inner.row();
			}
			inner.add($.add(i).setPosition(i.getWidth()*currentCol+padding, i.getHeight()*row+padding).setSize(70, 70).getItem()).align(Align.topLeft).pad(padding).prefSize(70,70);
		}
		
		inner.setSize(getWidth(), (row+1)*(70+padding/2));
		
		addActor(pane);
		return this;
	}
	
}
