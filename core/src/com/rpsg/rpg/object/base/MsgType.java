package com.rpsg.rpg.object.base;


public enum MsgType {
	爱丽丝("alice.png"),
	芙兰("flandre.png"),
	正常("default.png"),
	布都("futo.png"),
	有栖("hero.png"),
	辉夜("kaguya.png"),
	恋("koishi.png"),
	觉("kokoro.png"),
	魔理沙("marisa.png"),
	妹红("mekou.png"),
	神子("miko.png"),
	电脑("npc.png"),
	其他("other.png"),
	灵梦("reimu.png"),
	蕾米("remilia.png"),
	莲子("renko.png"),
	咲夜("sakura.png"),
	早苗("sanae.png"),
	妖梦("youmu.png"),
	紫("yukari.png"),
	梅莉("mari.png");
	
	private String path;
	private MsgType(String path){
		this.path=path;
	}
	
	public String path() {
		return path;
	}

}
