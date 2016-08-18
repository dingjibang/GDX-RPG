package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.ItemController;
import com.rpsg.rpg.system.ui.Image;

/** 图鉴模型 */
public class Index implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int index;
	boolean unlock = false;// 图鉴是否被开启
	public IndexType type;
	
	public String name,jname,description;
	
	public String path;
	private List<String> fgList;
	
	
	
	public static Index fromJSON(int id) {
		JsonReader reader = ItemController.reader();
		JsonValue value = reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_INDEX + id + ".grd"));
		
		Index index = new Index();
		
		index.index = value.getInt("index");
		index.unlock = false;
		index.type = IndexType.valueOf(value.getString("type"));
		index.path = value.getString("path");
		index.name = value.getString("name");
		index.jname = value.has("jname") ? value.getString("jname") : "";
		index.description = value.has("description") ? value.getString("description") : "";
		
		if(index.type == IndexType.actor)
			index.fgList = $.list("Normal").get();
		
		return index;
	}
	
	public List<Image> imageList(){
		if(type == IndexType.enemy)
			return $.list(Res.get(Setting.IMAGE_ENEMY + path)).get();
		
		return $.map($.map(fgList, str -> Setting.IMAGE_FG + path + "/" + str + ".png"), Res::get);
	}
	
	public Image image(){
		return type == IndexType.enemy ? Res.getNP(Setting.IMAGE_ENEMY + path + ".png") : Res.getNP(Setting.IMAGE_FG + path + "/" + "index.png"); 
	}
	
	public void addFG(String fgType){
		if(type == IndexType.actor)
			fgList.add(fgType);
		
		RPG.ctrl.index.save();
	}
	
	public void addFG(FGType fgType){
		addFG(fgType.value());
	}
	
	public void unlock(){
		unlock = true;
		RPG.ctrl.index.save();
	}
	
	public boolean isUnlock(){
		return unlock;
	}
	
	public static enum IndexType {
		actor, enemy
	}
}
