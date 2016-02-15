package com.rpsg.rpg.utils.game;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.base.Initialization;


public class Move {
	public static BaseScriptExecutor move(Script script,final int step){
		return script.set(new ScriptExecutor(script) {
			@Override
			public void step() {
				if(script.npc.walkStack.size()==0)
					this.dispose();
			}
			@Override
			public void init() {
				if(step==0)
					dispose();
				script.npc.walk(step);
				script.npc.testWalk();
			}
		});
	}
	
	public static BaseScriptExecutor turn(final Script script,final int faceTo){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.turn(faceTo);
			}
		});
	}
	
	
	public static BaseScriptExecutor faceToHero(final Script script){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.turn(RPG.ctrl.hero.getHeadHero().getReverseFace());
			}
		});
	}
	
	public static BaseScriptExecutor faceToPoint(final Script script, final int x, final int y){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.turn(script.npc.getFaceByPoint(x, y));
			}
		});
	}
	
	public static BaseScriptExecutor teleportAnotherMap(Script script, final String map, final int x, final int y, final int z){
		return script.set(new BaseScriptExecutor() {
			@Override
			public void init() {
				RPG.global.map = map.indexOf('/') < 0 ? ("test/" + map) : map;
				RPG.global.x = x;
				RPG.global.y = y;
				RPG.global.z = z;
				RPG.ctrl.thread.pool.clear();
				RPG.global.npcs.clear();
				Initialization.restartGame();
				RPG.ctrl.hero.reinit(true);
			}
		});
	}
	
	public static BaseScriptExecutor lock(Script script, final boolean flag){
		return script.set(new ScriptExecutor(script) {
			@Override
			public void init(){
				if(flag){
					for (Script sc : script.npc.threadPool) {
						if(sc.callType.equals(CollideType.auto))
							sc.dispose();
					}

//				script.npc.threadPool.removeIf((Script sc)->sc.callType.equals(DefaultNPC.AUTO_SCRIPT));
				}else if(script.npc.scripts.get(CollideType.auto)!=null)
					script.npc.pushThreadAndTryRun(CollideType.auto);
			}
			
			@Override
			public void step(){
				try {
					com.rpsg.rpg.object.rpg.MoveStack wk= script.npc.walkStack.get(0);
					wk.step=0;
					script.npc.walkStack.clear();
					script.npc.walkStack.add(wk);
					if(!script.npc.walked)
						script.npc.toWalk();
					else
						dispose();
				} catch (Exception e) {
					dispose();
				}
			}
		});
	}
}
