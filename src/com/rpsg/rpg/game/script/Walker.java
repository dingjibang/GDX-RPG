package com.rpsg.rpg.game.script;


import com.rpsg.rpg.object.script.Script;


public class Walker extends Script{

	public void init() {
//		randomWalk(new Vector2(3,3));
		randomWalkByHero(60,3);
//		randomWalkBySelf(60, 3);
	}

}
