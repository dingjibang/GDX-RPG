package com.rpsg.rpg.object.hero;

import java.io.Serializable;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.object.game.GrowableTarget;
import com.rpsg.rpg.object.game.Target;
import com.rpsg.rpg.object.game.Targetable;
import com.rpsg.rpg.object.item.Equipment;
import com.rpsg.rpg.object.map.MapSprite;

/**
 * GDX-RPG Hero<br>
 * Hero为游戏里的玩家角色，他从[assets/script/data/hero/]中读取相应的JSON信息
 */
public class Hero implements Targetable, Serializable{
	private static final long serialVersionUID = 1L;

	public int id;
	/**Hero的数值数据*/
	private GrowableTarget target;
	/**hero在地图上的行走精灵*/
	public MapSprite sprite;
	/**hero名称*/
	public String name;
	/**hero日文名*/
	public String jname;
	/**hero的立绘文件夹路径*/
	public String fg;
	/**hero的颜色hex值(UI用)*/
	public String color;
	
	public Hero(int id) {
		this(id, File.readJSON(Path.SCRIPT_DATA_HERO + id + ".grd"));
	}
	
	public Hero(int id, JsonValue json) {
		this.id = id;
		name = json.getString("name");
		jname = json.getString("jname");
		fg = json.getString("fg");
		color = json.getString("color");
		
		sprite = new MapSprite(0, 0, 0, 4, Path.WALK + "heros/" + json.getString("spriteImage"));
		
		target = new GrowableTarget(json.get("prop"), json.get("grow"));
		
		//初始化装备
		for(JsonValue equip : json.get("equip"))
			target.setEquipment(Equipment.Parts.valueOf(equip.name), equip.isNull() ? null : Game.item.get(equip.asInt(), Equipment.class));
		
	}
	
	public Target getTarget() {
		return target;
	}

	public boolean equals(Object obj) {
		return obj instanceof Hero && ((Hero)obj).id == id;
	}	
}
