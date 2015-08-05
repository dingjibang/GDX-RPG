package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.RPG;

public class DrawController {

	public void draw(){
		SpriteBatch batch= (SpriteBatch) RPG.ctrl.fg.stage.getBatch();
		RPG.ctrl.fg.stage.draw();
		batch.begin();
		RPG.ctrl.cg.draw(batch);
		RPG.ctrl.fg.draw(batch);
		RPG.ctrl.msg.draw(batch);
		batch.end();
	}
}
