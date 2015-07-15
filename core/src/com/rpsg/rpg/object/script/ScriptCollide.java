package com.rpsg.rpg.object.script;

import com.rpsg.rpg.object.rpg.NPC;


public class ScriptCollide {
	public NPC rpgobject;
	public static enum COLLIDE_TYPE{
		Z("z"),NEAR("near"),FACE("face"),FOOT("foot"),FACE_Z("faceZ"),AUTO("auto");
	
		 private String value = "";

		 private COLLIDE_TYPE(String value) {  
		    this.value = value;
		 }

		 public String value() {
		    return this.value;
		 }		
		
	}
	public COLLIDE_TYPE collideType;
	public ScriptCollide(NPC o,COLLIDE_TYPE type){
		collideType=type;
		rpgobject=o;
	}

	@Override
	public String toString() {
		return "ScriptCollide [rpgobject=" + rpgobject + ", collideType=" + collideType.value + "]";
	}
	
	public void toCollide(){
		this.rpgobject.toCollide(this);
	}
	
}
