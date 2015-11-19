package com.rpsg.rpg.object.script;

import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.NPC;


public class ScriptCollide {
	public NPC rpgobject;
	public CollideType collideType;
	
	public ScriptCollide(NPC o,CollideType type){
		collideType=type;
		rpgobject=o;
	}

	@Override
	public String toString() {
		return "ScriptCollide [rpgobject=" + rpgobject + ", collideType=" + collideType.name() + "]";
	}
	
	public void toCollide(){
		this.rpgobject.toCollide(this);
	}
	
}
