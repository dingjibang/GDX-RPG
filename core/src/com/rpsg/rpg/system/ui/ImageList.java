package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
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
		addActor(Res.get(Setting.UI_BASE_IMG).size((int)this.getWidth(),(int)this.getHeight()).color(1,1,1,0.3f));
		Group inner = new Group();
		ScrollPane pane =new ScrollPane(inner);
		pane.setSize(getWidth(), getHeight());
		
		int padding = 6;
		
		int col = (int) (getWidth()/(70+(padding/2)));
		int row = 0;
		System.out.println(items);
		int currentCol = -1;
		for(Icon i:items){
			if(currentCol++ > col){
				row++;
				currentCol = -1;
			}
			inner.addActor($.add(i).setPosition(i.getWidth()*currentCol+padding, i.getHeight()*row+padding).setSize(70, 70).getItem());
		}
		
		inner.setSize(getWidth(), (row+1)*(70+padding/2));
		
//		pane.setDebug(true);
		inner.setDebug(true);
		addActor(pane);
		return this;
	}
	
}
