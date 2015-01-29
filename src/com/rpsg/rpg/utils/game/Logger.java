package com.rpsg.rpg.utils.game;

import com.badlogic.gdx.Application;

public class Logger {
	
	static com.badlogic.gdx.utils.Logger infolog;
	static com.badlogic.gdx.utils.Logger errorlog;
	static com.badlogic.gdx.utils.Logger faildlog;
	
	public static void init(){
		infolog=new com.badlogic.gdx.utils.Logger("–≈œ¢");
		errorlog=new com.badlogic.gdx.utils.Logger("“Ï≥£");
		faildlog=new com.badlogic.gdx.utils.Logger("±¿¿£");
		infolog.setLevel(Application.LOG_INFO);
		errorlog.setLevel(Application.LOG_ERROR);
		faildlog.setLevel(Application.LOG_ERROR);
	}
	
	private static long time=0;
	private static String getTime(){
		long cur=System.currentTimeMillis();
		long m=cur-time;
		time=cur;
		return "["+m+" ms]";
	}
	
	public static void error(String s){
		errorlog.error(s);
	}
	
	public static void error(String s,Exception e){
		errorlog.error(s,e);
	}
	
	public static void info(String s){
		infolog.info(getTime()+s);
	}
	
	public static void faild(String s){
		faildlog.info(s);
	}
	
	public static void faild(String s,Exception e){
		faildlog.info(s,e);
	}
}
