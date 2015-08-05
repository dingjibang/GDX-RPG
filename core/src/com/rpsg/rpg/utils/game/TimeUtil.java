package com.rpsg.rpg.utils.game;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	Date date=new Date();
	SimpleDateFormat format=new SimpleDateFormat("s"),format2=new SimpleDateFormat("hh:mm:ss");
	
	public String getGameRunningTime(){
		return formatDuring((new Date().getTime()-date.getTime()));
	}
	
	private String formatDuring(long mss) {  
		String hours = String.valueOf((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		hours=hours.length()<=1?"0"+hours:hours;
	    String minutes = String.valueOf((mss % (1000 * 60 * 60)) / (1000 * 60));
	    minutes=minutes.length()<=1?"0"+minutes:minutes;
	    String seconds = String.valueOf((mss % (1000 * 60)) / 1000);
	    seconds=seconds.length()<=1?"0"+seconds:seconds;
	    return hours + ":" + minutes + ":"   + seconds;  
	}  
}
