package com.rpsg.rpg.object.base.items;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.ItemController;
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
	
	public int nextTurn(){
		return --turn;
	}
	
	public int turn(){
		return turn;
	}
	
	public Buff turn(int turn) {
		this.turn = turn;
		return this;
	}
	
	public Image getIcon(){
		return getIcon(id);
	}
	
	public static Image getIcon(int id){
		String path = Setting.IMAGE_ICONS + "b" + id + ".png";
		return Gdx.files.internal(path).exists() ? Res.get(path) : getDefaultIcon();
	}
	
	public static enum BuffType{
		buff,debuff
	}
	
	public Prop getProp(String key){
		return prop.get(key);
 	}
	
	public static Buff getById(int id){
		JsonReader reader = ItemController.reader();
		Buff buff = new Buff();
		
		JsonValue value = reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_BUFF+id+".grd").readString());
		
		buff.id = id;
		buff.type = value.has("type") ? BuffType.valueOf(value.getString("type")) : BuffType.buff;
		buff.name = value.has("name") ? value.getString("name") : "(??)";
		buff.prop = ItemController.getPropObject(value.get("prop"));
		buff.turn = value.has("turn") ? value.getInt("turn") : 0;
		buff.description = value.has("description") ? value.getString("description") : "";
		
		return buff;
	}
}
