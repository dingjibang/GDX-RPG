package com.rpsg.rpg.view.hover;

import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.system.ui.HeroSelectBox;
import com.rpsg.rpg.system.ui.Icon;

public class UseItemView extends SidebarView {

	@Override
	public void init() {
		Icon icon = (Icon) param.get("item");
		BaseItem item = icon.item;
		
		boolean forAll = false;
		if(item instanceof Item)
			forAll = ((Item)item).range == Item.ItemRange.all;
		
		group.addActor(new HeroSelectBox(460,200,forAll).position(430, 240));
	}

}
	