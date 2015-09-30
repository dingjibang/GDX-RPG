package com.rpsg.rpg.system.ui;

import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.system.base.Res;

public class IconObject implements Comparable<IconObject>{
	public Item item = null;
	public boolean enable = true;
	
	public Image icon;
	
	public IconObject(Item item){
		this.item=item;
		generateIcon();
	}
	
	public IconObject(Item item,boolean enable){
		this.item=item;
		this.enable=enable;
		generateIcon();
	}
	
	public IconObject generateIcon(){
		this.icon=Res.exist(item.getIcon())?Res.get(item.getIcon()):Res.get(Item.getDefaultIcon());
		return this;
	}

	@Override
	public int compareTo(IconObject o) {
		return (o.enable && this.enable) ? 0 : ((o.enable && !this.enable) ? 1 : -1 );
	}
	
//	public

}
