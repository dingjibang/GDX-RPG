package com.rpsg.rpg.object.prop;

/**
 * 攻击所附加的属性
 */
public enum PropType {
	none("无"),//叫void的话显然不太对劲hhh
	sun("日"),
	moon("月"),
	metal("金"),
	water("水"),
	earth("土"),
	fire("火"),
	wood("木");
	
	private String description;
	
	private PropType(String str) {
		this.description = str;
	}
	
	public String description() {
		return description;
	}
}
