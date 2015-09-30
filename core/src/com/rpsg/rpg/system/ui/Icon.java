package com.rpsg.rpg.system.ui;

import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.system.base.Res;

public class Icon extends Image implements Comparable<Icon>{
	public Item item = null;
	public boolean enable = true;
	
	public Icon() {
		super();
	}
	
	public Icon generateIcon(Item item,final boolean enable){
		this.item=item;
		final ProxyImage i = (ProxyImage)(Res.exist(item.getIcon())?Res.get(item.getIcon()):Res.get(Item.getDefaultIcon()));
		i.loaded=new Runnable() {
			public void run() {
				Icon.this.setDrawable(i.getDrawable());
				if(!enable)
					setColor(.7f,.7f,.7f,1);
			}
		};
		
		i.lazyLoad();
		
		return this;
	}

	@Override
	public int compareTo(Icon o) {
		return (o.enable && this.enable) ? 0 : ((o.enable && !this.enable) ? 1 : -1 );
	}
	
//	public

}
