package com.rpsg.rpg.utils.game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	static Date date=new Date();
	static SimpleDateFormat format=new SimpleDateFormat("s"),format2=new SimpleDateFormat("hh:mm:ss");
	public static void init(){
		
	}
	
	public static void logic(){
		
	}
	
	public static String getGameRunningTime(){
		return formatDuring((new Date().getTime()-date.getTime()));
	}
	
	private static String formatDuring(long mss) {  
	    long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);  
	    long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);  
	    long seconds = (mss % (1000 * 60)) / 1000;  
	    return hours + ":" + minutes + ":"   + seconds;  
	}  
}
