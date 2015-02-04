package com.rpsg.rpg.object.script;

import java.util.LinkedList;
import java.util.List;

import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.FGType;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FG;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.game.Base;
import com.rpsg.rpg.utils.game.Heros;
import com.rpsg.rpg.utils.game.Move;
import com.rpsg.rpg.utils.game.Timer;
import com.rpsg.rpg.view.GameViews;


public abstract class Script implements MsgType,FGType{
	private int waitTime=0;
	
	public void sleep(int frame){
		waitTime+=frame;
	}
	
	public NPC npc;
	public String callType;
	public void generate(NPC npc,String type){
		this.npc=npc;
		this.callType=type;
		init();
	}
	
	public abstract void init();
	
	public boolean isAlive=false;
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void dispose(){
		this.isAlive=false;
	}
	
	public List<BaseScriptExecutor> scripts=new LinkedList<BaseScriptExecutor>();
	
	public int point=-1;
	public boolean currentExeced=true;
	
	
	public void run(){
		if(waitTime>0){
			waitTime--;
			return;
		}
		if(currentExeced)
			if(++point==scripts.size()){
				this.dispose();
				return;
			}else{
				if(scripts.get(point) instanceof ScriptExecutor){
					((ScriptExecutor)scripts.get(point)).toInit();
					currentExeced=false;
				}else{
					scripts.get(point).init();
				}
			}
		if(scripts.get(point) instanceof ScriptExecutor)
			((ScriptExecutor)scripts.get(point)).step();
	}
	
	
	public BaseScriptExecutor _$(BaseScriptExecutor exe){
		scripts.remove(scripts.size()-1);
		scripts.add(point+1, exe);
		return exe;
	}
	
	public BaseScriptExecutor $ (BaseScriptExecutor exe){
		scripts.add(exe);
		return exe;
	}
	
	protected BaseScriptExecutor say(String str,String title,int size){
		return Msg.say(this, str, title, size);
	}
	
	protected BaseScriptExecutor say(String str){
		return Msg.say(this, str, "", 22);
	}
	
	protected BaseScriptExecutor setKeyLocker(boolean b){
		return Msg.setKeyLocker(this, b);
	}
	
	protected BaseScriptExecutor faceToHero(){
		return Move.faceToHero(this);
	}
	
	protected BaseScriptExecutor faceTo(int face){
		return Move.turn(this, face);
	}
	
	protected BaseScriptExecutor move(int step){
		return Move.move(this, step);
	}
	
	protected BaseScriptExecutor wait(int frame){
		return Timer.wait(this, frame);
	}
	
	protected BaseScriptExecutor removeSelf(){
		return Base.removeSelf(this);
	}
	
	protected BaseScriptExecutor changeSelf(Class<? extends Script> script){
		return Base.changeSelf(this,script);
	}
	
	protected BaseScriptExecutor showMSG(String msgType){
		return Msg.show(this, msgType);
	}
	
	protected BaseScriptExecutor showMSG(){
		return Msg.show(this, Õý³£);
	}
	
	protected BaseScriptExecutor hideMSG(){
		return Msg.hide(this);
	}
	
	protected BaseScriptExecutor hideFG(int position){
		return FG.hide(this, position);
	}
	
	protected BaseScriptExecutor hideFG(){
		return FG.hideAll(this);
	}
	
	protected BaseScriptExecutor showFGLeft(String people,String look){
		return FG.show(this, Setting.GAME_RES_IMAGE_FG+people+look+".png", FG.LEFT);
	}
	
	protected BaseScriptExecutor showFGRight(String people,String look){
		return FG.show(this, Setting.GAME_RES_IMAGE_FG+people+look+".png", FG.RIGHT);
	}
	
	protected BaseScriptExecutor swapHeroQueue(int position){
		return Heros.swapHeroQueue(this, position);
	}
	
	protected BaseScriptExecutor swapHeroQueue(Class<? extends Hero> c){
		return Heros.swapHeroQueue(this, c);
	}
	
	protected BaseScriptExecutor swapHeroQueue(int position,int position2){
		return Heros.swapHeroQueue(this, position, position2);
	}
	
	protected BaseScriptExecutor swapHeroQueue(Class<? extends Hero> c1,Class<? extends Hero> c2){
		return Heros.swapHeroQueue(this, c1 , c2);
	}
	
	protected BaseScriptExecutor setGameTime(int color){
		return ColorUtil.set(this, color);
	}
	
	protected BaseScriptExecutor select(String ... args){
		return GameViews.selectUtil.select(this, args);
	}
}
