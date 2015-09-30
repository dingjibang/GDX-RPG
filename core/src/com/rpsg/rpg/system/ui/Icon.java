package com.rpsg.rpg.system.ui;

import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.system.base.Res;

public class Icon extends BGActor implements Comparable<Icon>{
	public Item item = null;
	public boolean enable = true;
	
	public Image icon;
	
	public Icon(Item item){
		this.item=item;
		generateIcon();
	}
	
	public Icon(Item item,boolean enable){
		this.item=item;
		this.enable=enable;
		generateIcon();
	}
	
	public Icon generateIcon(){
		this.icon=Res.exist(item.getIcon())?Res.get(item.getIcon()):Res.get(Item.getDefaultIcon());
		this.image=this.icon;
		setSize(70, 70);
		if(!enable)
			setColor(.7f,.7f,.7f,1);
		return this;
	}

	@Override
	public int compareTo(Icon o) {
		return (o.enable && this.enable) ? 0 : ((o.enable && !this.enable) ? 1 : -1 );
	}
	
//	public

}
