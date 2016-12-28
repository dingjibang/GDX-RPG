package com.rpsg.rpg.object.base;

import com.rpsg.rpg.system.ui.Image;

public interface Iconable {
	public Image getIcon();
	
	public static Image getDefaultIcon(){
		return null;
	};
}
