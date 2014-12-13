package com.rpsg.rpg.object;

import com.rpsg.rpg.object.heros.NPC;


public class ScriptCollide {
	public NPC rpgobject;
	public static final int COLLIDE_TYPE_Z=0;
	public static final int COLLIDE_TYPE_NEAR=1;
	public static final int COLLIDE_TYPE_FACE=2;
	public static final int COLLIDE_TYPE_FOOT=3;
	public static final int COLLIDE_TYPE_FACE_Z=4;
	public int collideType;
	
	public ScriptCollide(NPC o,int type){
		collideType=type;
		rpgobject=o;
	}

	@Override
	public String toString() {
		return "ScriptCollide [rpgobject=" + rpgobject + ", collideType="
				+ collideType + "]";
	}
	
	public void toCollide(){
		this.rpgobject.toCollide(this);
	}
	
}
