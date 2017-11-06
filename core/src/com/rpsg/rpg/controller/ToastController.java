package com.rpsg.rpg.controller;

import com.rpsg.rpg.core.Views;

import script.ui.view.ToastView;

/**
 * GDX-RPG Toast控制器
 */
public class ToastController {

	public void show(String text){
		show(text, ToastView.FONT_SIZE);
	}

	public void show(String text,int fontSize){
		ToastView vw = new ToastView();
		vw.text = text;
		vw.fontSize = fontSize;
		vw.create();
		Views.addView(vw);
	}
	
}
