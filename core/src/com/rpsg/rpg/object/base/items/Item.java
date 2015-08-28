package com.rpsg.rpg.object.base.items;

import java.io.Serializable;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;

/**
 * GDX-RPG 道具（ITEM）超类
 * @author dingjibang
 *
 */
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//本道具是否可以丢弃
	public boolean throwable = true;
	
	//道具名称
	public String name = "";
	
	//道具数量
	public int count = 1;
	
	//道具描述信息
	public String illustration = "";
	
	//道具是否可用
	public boolean disable = false;
	
	//物品ID（需要唯一性/ID和物品的图标有关联）
	public int id=0;
	
	//物品类型
	public String type=null;

	public String useScript = "";
	
	//注入到js的变量
	public Hero user;
	
	public String getIcon(){
		return Setting.IMAGE_ICONS+"i"+id+".png";
	}
	
	public static String getDefaultIcon(){
		return Setting.IMAGE_ICONS+"i0.png";
	}
	
	public Item setUser(Hero user){
		this.user=user;
		return this;
	}
	
	/**
	 * 使用这个道具<br>
	 * 原理：执行变量script里寄存的js语句。<br>
	 * 注意：使用use()方法前，可能需要进行变量 <i>注入</i> （比如使用这个道具的人(user))
	 * @return
	 */
	public boolean use(){
		if(useScript!=null && useScript.length()==0)
			return RPG.executeJS(useScript);
		return false;
	}
	
	public String toString() {
		return name;
	}
	
	public void remove(){
		RPG.ctrl.item.remove(this);
	}
	
}
