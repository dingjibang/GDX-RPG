package com.rpsg.rpg.controller;

import com.rpsg.rpg.core.Views;
import com.rpsg.rpg.util.*;
import com.rpsg.rpg.util.Timer.TimeType;

import script.ui.view.ToastView;

/**
 * GDX-RPG Toast控制器
 */
public class ToastController {

	public static final int def_delaytime = 5000;
	
	public void showToast(String text,int fontsize)
	{
		showToast(text,fontsize,def_delaytime);
	}
	
	public void showToast(String text,int fontsize,int delaytime)
	{
		showToast(text,fontsize,delaytime,ToastView.def_bgcolor);
	}
	
	public void showToast(String text,int fontsize,int delaytime,String bgcolor)
	{
		ToastView vw = new ToastView();
		vw.text = text;
		vw.fontsize = fontsize;
		vw.bgcolor = bgcolor;
		//if(Setter!= null) Setter.setter(vw);
		vw.create();
		Views.addView(vw);
		Timer.add(TimeType.millisecond, delaytime, new Runnable(){
			public void run()
			{
				vw.remove();
			}
		});
	}
	
	public void showToast(String text,ToastViewSetter Setter,int delaytime)
	{
		ToastView vw = new ToastView();
		vw.text = text;
		if(Setter!= null) Setter.setter(vw);
		vw.create();
		Views.addView(vw);
		Timer.add(TimeType.millisecond, delaytime, new Runnable(){
			public void run()
			{
				vw.remove();
			}
		});
	}
	
	public static abstract class ToastViewSetter
	{
		public abstract void setter(ToastView vw);
	}
}
