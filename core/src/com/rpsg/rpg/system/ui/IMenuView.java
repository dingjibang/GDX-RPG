package com.rpsg.rpg.system.ui;

import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.menu.MenuView;


public abstract class IMenuView extends DefaultIView{
	public MenuView parent=(MenuView) GameViews.gameview.stackView;
	public boolean allowEsc(){
		return true;
	};
	
	public void onResume(){};
}