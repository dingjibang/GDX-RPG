package com.rpsg.rpg.object.base.items;

/**
 * GDX-RPG Item types
 * Register a new item type from here.
 * @author dingjibang
 *
 */
public enum ItemType {
	item("道具"),
	cooking("料理"),	
	equipment("装备"),
	metrial("材料"),	
	spellcard("符卡"),
	task("任务")
	
	;
	private String value;
	private ItemType(String value){
		this.value=value;
	}
	
	public String value() {
		return value;
	}
}
