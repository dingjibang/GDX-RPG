package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Files;
import com.rpsg.rpg.utils.game.GameUtil;

public class Persistence implements Serializable{
	private static final long serialVersionUID = 1L;
	public boolean antiAliasing = true;
	public boolean scaleAliasing = true;
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
	public boolean softCamera=true;
	public boolean uiDebug = false;
	
	public String errorMessage="";
	
	public static String PersistenceFileName = Setting.PERSISTENCE+"persistence.es";
	

	public static Persistence read(){
		Object o=Files.load(PersistenceFileName);
		if(null!=o)
			return (Persistence)o;
		//create new settings if the "save" floder not exise
		Gdx.files.local(Setting.PERSISTENCE).mkdirs();
		Persistence p=new Persistence();
		p.touchMod= !GameUtil.isDesktop;
		Files.save(p,PersistenceFileName);
		return p;
	}
	
	public static void save(){
		Files.save(Setting.persistence,PersistenceFileName);
	}
}
