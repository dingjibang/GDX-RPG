package com.rpsg.rpg.game.script;

import java.util.Random;

import com.rpsg.rpg.object.rpg.IRPGObject;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.utils.game.Move;


public class Walker extends Script{

	public void init() {
		$(new ScriptExecutor(this) {
			Random r;
			int sleepMaxTime=60,sleepTime=0,count=-1,maxCount=-1;
			int maxWalkLength=3;
			public void init() {
				r=new Random();
			}
			
			public void step(){
				if(sleepTime++>sleepMaxTime){
					sleepTime=0;
					if(count != -1 && count++ > maxCount)
						dispose();
					int face = r.nextInt(4);
					if(face == 3) face=IRPGObject.FACE_D;
					else if(face == 2) face=IRPGObject.FACE_U;
					else if(face == 1) face=IRPGObject.FACE_L;
					else if(face == 0) face=IRPGObject.FACE_R;
					__$(Move.move(script, r.nextInt(maxWalkLength)));
					__$(Move.turn(script, face));
				}
			}
		});
	}

}
