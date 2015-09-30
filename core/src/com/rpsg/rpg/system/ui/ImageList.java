package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

/**
 * A Item(or equip.) image list view
 * @author dingjibang
 *
 */
public class ImageList extends Group{
	
	java.util.List<IconObject> items;
	public ImageList(java.util.List<IconObject> items) {
		super();
		this.items=items;
	}
	
	public ImageList generate(){
		addActor(Res.get(Setting.UI_BASE_IMG).size((int)this.getWidth(),(int)this.getHeight()).color(1,1,1,0.3f));
		Group inner = new Group();
		ScrollPane pane =new ScrollPane(inner);
		pane.setSize(getWidth(), getHeight());
		addActor(pane);
		return this;
	}
	
}
