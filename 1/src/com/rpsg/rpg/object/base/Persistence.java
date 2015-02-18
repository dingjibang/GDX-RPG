package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.FileIO;

public class Persistence implements Serializable{
	private static final long serialVersionUID = 1L;
	public boolean antiAliasing = true;
	public boolean scaleAliasing = true;
	public boolean useGL3 = false;
	public boolean useClearFont = false;
	public int MemorySize = 512;
	public int volume = 100;
	public int musicVolume = 70;
	public int seVolume = 100;
	public int textSpeed = 2;
	public boolean showFPS = false;
	public boolean debugMod = false;
	public boolean onErrorSendMsg = true;
	public boolean touchMod = false;
	public boolean betterLight = true;
	public boolean cacheResource = true;
	
	public static String PersistenceFileName = Setting.GAME_PERSISTENCE+"persistence.es";
	
	public static Persistence read(){
		Object o=FileIO.load(PersistenceFileName);
		if(null!=o)
			return (Persistence)o;
		Persistence p=new Persistence();
		//TODO TOUCHMOD
		FileIO.save(p,PersistenceFileName);
		return p;
	}
	
	public static void save(){
		FileIO.save(Setting.persistence,PersistenceFileName);
	}
}
