package com.rpsg.rpg.object;


public class ScriptCollide {
	public IRPGObject rpgobject;
	public static final int COLLIDE_TYPE_Z=0;
	public static final int COLLIDE_TYPE_NEAR=1;
	public static final int COLLIDE_TYPE_FACE=2;
	public static final int COLLIDE_TYPE_FOOT=3;
	public int collideType;
	
	public ScriptCollide(IRPGObject o,int type){
		collideType=type;
		rpgobject=o;
	}

	@Override
	public String toString() {
		return "ScriptCollide [rpgobject=" + rpgobject + ", collideType="
				+ collideType + "]";
	}
	
}
