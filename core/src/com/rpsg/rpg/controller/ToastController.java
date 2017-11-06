package com.rpsg.rpg.controller;

import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.util.*;
import com.rpsg.rpg.util.Timer.TimeType;

import script.ui.view.ToastView;

/**
 * GDX-RPG Toast控制器
 */
public class ToastController {

	public static final int def_delaytime = 3000;
	
	public void showToast(String text,int fontsize)	{
		showToast(text,fontsize,def_delaytime);
	}
	
	public void showToast(String text,int fontsize,int delaytime){
		ToastView vw = new ToastView();
		vw.text = text;
		vw.fontsize = fontsize;
		vw.create();
		Views.addView(vw);
		vw.setRemoveMessage(delaytime);
	}
	
	public void showToast(String text,ToastViewSetter Setter,int delaytime){
		ToastView vw = new ToastView();
		vw.text = text;
		if(Setter!= null) Setter.setter(vw);
		vw.create();
		Views.addView(vw);
		vw.setRemoveMessage(delaytime);
	}
	
	public static abstract class ToastViewSetter{
		public abstract void setter(ToastView vw);
	}
}
