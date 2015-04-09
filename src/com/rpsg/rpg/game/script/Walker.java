package com.rpsg.rpg.game.script;


import com.badlogic.gdx.math.Vector2;
import com.rpsg.rpg.object.script.Script;


public class Walker extends Script{

	public void init() {
		randomWalk(new Vector2(3,3));
	}

}
