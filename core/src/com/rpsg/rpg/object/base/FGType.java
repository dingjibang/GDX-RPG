package com.rpsg.rpg.object.base;

public enum FGType {
	生气("Angry"),
	自信("Confident"),
	哭("Cry"),
	大笑("Happy"),
	呆("Horseface"),
	普通("Normal"),
	悲伤("Sad"),
	惊("Serious"),
	羞("Shy"),
	叹("Sigh"),
	笑("Smile"),
	思考("Thinking"),
	傲娇("Tsundere"),
	
	爱丽丝("alice/"),
	魔理沙("marisa/"),
	有栖("arisu/");
	
	private String value;
	private FGType(String value){
		this.value=value;
	}
	
	public String value() {
		return value;
	}
}
