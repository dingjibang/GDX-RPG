package com.rpsg.rpg.utils.display;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.ui.Image;

public class NumberUtil {
	public static Image[] throwimg=new Image[10];
	static{
		for(int i=0;i<throwimg.length;i++){
			throwimg[i]=new Image(Setting.GAME_RES_IMAGE_NUMBER+i+".png");
		}
	}
	
	public static Image get(int num){
		try{
			return throwimg[num];
		}catch(Exception e){
			AlertUtil.add(e.getMessage(), AlertUtil.Red);
			return null;
		}
	}
	
	public static Image get(String num){
		return get(Integer.parseInt(num));
	}
}
