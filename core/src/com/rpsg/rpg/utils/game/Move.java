package com.rpsg.rpg.utils.game;

import java.util.Random;



import com.badlogic.gdx.math.Vector2;
import com.rpsg.rpg.object.rpg.DefaultNPC;
import com.rpsg.rpg.object.rpg.IRPGObject;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptCollide.COLLIDE_TYPE;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.base.Initialization;
import com.rpsg.rpg.system.base.ThreadPool;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.view.GameViews;


public class Move {
	public static BaseScriptExecutor move(Script script,final int step){
		return script.$(new ScriptExecutor(script) {
			public void step() {
				if(script.npc.walkStack.size()==0)
					this.dispose();
			}
			public void init() {
				if(step==0)
					dispose();
				script.npc.walk(step);
				script.npc.testWalk();
			}
		});
	}
	
	public static BaseScriptExecutor turn(final Script script,final int faceTo){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.turn(faceTo);
			}
		});
	}
	
	//around the current hero if point.x and y = -1 
	public static BaseScriptExecutor random(Script script, final int speed, final int maxLength, final Vector2 bounds, final Vector2 point){
		return script.$(new ScriptExecutor(script) {
			Random r;
			int sleepMaxTime=speed,sleepTime=0,count=-1,maxCount=-1;
			int maxWalkLength=maxLength;
			Vector2 bo2;
			public void init() {
				r=new Random();
				if(bounds!=null)
					bo2=new Vector2(0,0);
			}
			
			public void step(){
				if(sleepTime++>sleepMaxTime){
					sleepTime=0;
					if(count != -1 && count++ > maxCount)
						dispose();
					int step = r.nextInt(maxWalkLength);
					int face;
					if(point==null){
						face = r.nextInt(4);
						if(face == 3) face=IRPGObject.FACE_D;
						else if(face == 2) face=IRPGObject.FACE_U;
						else if(face == 1) face=IRPGObject.FACE_L;
						else if(face == 0) face=IRPGObject.FACE_R;
					}else if(point.x!= -1 && point.y!= -1){
						face = script.npc.getFaceByPoint((int)point.x, (int)point.y);
					}else{
						face = script.npc.getFaceByPoint(HeroController.getHeadHero().mapx,HeroController.getHeadHero().mapy);
					}
					if(bo2!=null){
						if(face==IRPGObject.FACE_D){
							if(bo2.y+step<bounds.y){
								bo2.y+=step;
							}else{
								if(bo2.y<bounds.y) step=(int)bounds.y-step; else step=0;
								bo2.y=bounds.y;
							}
						}else if(face==IRPGObject.FACE_U){
							if(bo2.y-step>-bounds.y){
								bo2.y-=step;
							}else{
								if(bo2.y>-bounds.y) step=Math.abs((int) Math.abs(bounds.y)-step); else step=0;
								bo2.y=-bounds.y;
							}
						}else if(face==IRPGObject.FACE_R){
							if(bo2.x+step<bounds.x){
								bo2.x+=step;
							}else{
								if(bo2.x<bounds.x) step=(int)bounds.x-step; else step=0;
								bo2.x=bounds.x;
							}
						}else if(face==IRPGObject.FACE_L){
							if(bo2.x-step>-bounds.x){
								bo2.x-=step;
							}else{
								if(bo2.x>-bounds.x) step=Math.abs((int) Math.abs(bounds.x)-step); else step=0;
								bo2.x=-bounds.x;
							}
						}
					}
					script.__$(Move.move(script, step>0?step:0));
					script.__$(Move.turn(script, step>0?face:IRPGObject.getReverseFace(face)));
				}
			}
		});
	}
	
	public static BaseScriptExecutor faceToHero(final Script script){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.turn(HeroController.getHeadHero().getReverseFace());
			}
		});
	}
	
	public static BaseScriptExecutor faceToPoint(final Script script, final int x, final int y){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				script.npc.turn(script.npc.getFaceByPoint(x, y));
			}
		});
	}
	
	public static BaseScriptExecutor teleportAnotherMap(Script script, final String map, final int x, final int y, final int z){
		return script.$(new BaseScriptExecutor() {
			@Override
			public void init() {
				GameViews.global.map = "test/" + map;
				GameViews.global.x = x;
				GameViews.global.y = y;
				GameViews.global.z = z;
				ThreadPool.pool.clear();
				Initialization.restartGame();
				HeroController.reinitByTeleport();
			}
		});
	}
	
	public static BaseScriptExecutor lock(Script script, final boolean flag){
		return script.$(new ScriptExecutor(script) {
			public void init(){
				if(flag){
					for (Script sc : script.npc.threadPool) {
						if(sc.callType.equals(COLLIDE_TYPE.AUTO.value()))
							sc.dispose();
					}

//				script.npc.threadPool.removeIf((Script sc)->sc.callType.equals(DefaultNPC.AUTO_SCRIPT));
				}else if(script.npc.scripts.get(COLLIDE_TYPE.AUTO.value())!=null)
					script.npc.pushThreadAndTryRun(COLLIDE_TYPE.AUTO.value());
				script._$(script.setKeyLocker(flag));
				System.out.println("die");
			}
			
			public void step(){
				try {
					com.rpsg.rpg.object.rpg.Walker wk= script.npc.walkStack.get(0);
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
