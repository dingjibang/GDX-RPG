package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.utils.game.GameDate;

/**
 * RPG全局变量类，存储了游戏内所需保存的变量、对象等。持久化本类即为保存游戏，反持久化则为读取游戏。<br>
 * 如果需要更改初始设置（如初始坐标、初始金钱、初始装备等），请勿修改本类，您需要修改 [ROOT]/android/assets/script/system/global.js 这个文件，在游戏第一次运行时会读取本脚本进行注入。
 * @author dingjibang
 */
public class Global implements Serializable {
	private static final long serialVersionUID = 1L;
	// 初始地图
	public String map;
	
	// 地图相关
	public ArrayList<NPC> npcs = new ArrayList<>();
	//allHeros 为 所有的hero（被初始化的、有数据的），而currentHeros为当前【队列】里的hero。
	public ArrayList<Hero> currentHeros = new ArrayList<>();
	public ArrayList<Hero> allHeros = new ArrayList<>();
	public ArrayList<Hero> support = new ArrayList<>();
	public int x;
	public int y;
	public int z;

	public boolean first = true;

	// 时间模块
	public GameDate date = new GameDate();
	public int weather;

	// 金钱
	public int gold;
	
	//全局变量存储
	public Map<Object,Object> flag = new HashMap<>();

	// 物品、装备等道具

	public List<BaseItem> items = new ArrayList<>(); 

	public void read(){
		RPG.executeJS( Gdx.files.internal(Setting.SCRIPT_SYSTEM+"global.js").readString("utf-8"), this);
	}
	
	public Global addSupport(){
		return this;
	}
}
