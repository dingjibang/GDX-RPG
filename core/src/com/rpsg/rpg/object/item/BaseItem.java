package com.rpsg.rpg.object.item;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.object.game.Iconable;
import com.rpsg.rpg.ui.Image;

/**
 * GDX-RPG 物品模块<br>
 * {@link BaseItem}为游戏中所有物品的基类，物品基本就是道具、装备、符卡之类的东西啦。<br>
 * <br>
  GDX-RPG的所有道具数据，均存储于[/assets/script/data]这个位置。<br>
 * 存储的规范是，使用数字（即道具的唯一ID）进行命名，文件后缀为.grd(GDX RPG Data)。<br>
 * 相应的，存储的格式是<b>Json<b>格式。<br>
 */
public class BaseItem implements Iconable, Serializable{
	
	private static final long serialVersionUID = 1L;

	/**本道具是否可以丢弃*/
	public boolean throwable;
	/**道具名称*/
	public String name = "";
	/**道具数量*/
	public int count = 1;
	/**道具描述信息*/
	public String description;
	/**道具是否不可用*/
	public boolean disable;
	/**道具ID（需要唯一性/ID和物品的图标有关联）*/
	public int id;
	/**道具类型*/
	public String type;
	/**买入金钱**/
	public int buy;
	/**卖出金钱**/
	public int sell;
	/**道具是否可叠加的*/
	public boolean packable = true; 
	
	public BaseItem(int id, JsonValue value) {
		this.id = id;
		this.disable = value.has("disable") ? value.getBoolean("disable") : false;
		this.description = value.getString("description");
		this.throwable = value.has("throwable") ? value.getBoolean("throwable") : true;
		this.name = value.getString("name");
		this.type = value.getString("type");
		this.packable = value.has("packable") ? value.getBoolean("packable") : true;
		this.buy = value.has("buy") ? value.getInt("buy") : 0;
		this.sell = value.has("sell") ? value.getInt("sell") : 0;
	}
	
	public Image getIcon() {
		String name = Path.IMAGE_ICONS + "i" + id + ".png";
		if (Gdx.files.internal(name).exists())
			return Res.get(name);
		return getDefaultIcon();
	}

	public static Image getDefaultIcon() {
		return Res.get(Path.IMAGE_ICONS + "i0.png");
	}
	
	public String toString() {
		return name;
	}
	
	public void remove(){
		Game.item.remove(this);
	}
	
	
}
