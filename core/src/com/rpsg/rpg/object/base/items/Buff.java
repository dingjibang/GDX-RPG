package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;

public class Buff implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	public String name;
	public BuffType type = BuffType.buff;
	public Map<String, Prop> prop = new HashMap<>();
	public String description;
	public int turn;
	
	public static Image getDefaultIcon(){
		return Res.get(Setting.IMAGE_ICONS + "b0.png");
	}
	
	public Image getIcon(){
		String path = Setting.IMAGE_ICONS + "b" + id + ".png";
		return Gdx.files.internal(path).exists() ? Res.get(path) : getDefaultIcon();
	}
	
	public static enum BuffType{
		buff,debuff
	}
	
	public Prop getProp(String key){
		return prop.get(key);
 	}
}
