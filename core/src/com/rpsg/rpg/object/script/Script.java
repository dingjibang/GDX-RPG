package com.rpsg.rpg.object.script;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.FGType;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.MapController;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FG; 
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.display.SelectUtil;
import com.rpsg.rpg.utils.display.WeatherUtil;
import com.rpsg.rpg.utils.game.Base;
import com.rpsg.rpg.utils.game.GameDate;
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
	public CollideType callType;
	public void generate(NPC npc,CollideType type){
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
	
	public BaseScriptExecutor __$(BaseScriptExecutor exe){
		scripts.remove(scripts.size()-1);
		scripts.add(point==-1?0:point, exe);
		if(point>=0)
			point--;
		currentExeced=true;
		return exe;
	}
	
	public BaseScriptExecutor $ (BaseScriptExecutor exe){
		scripts.add(exe);
		return exe;
	}
	
	public BaseScriptExecutor say(String str,String title,int size){
		return Msg.say(this, str, title, size);
	}
	
	public BaseScriptExecutor say(String str){
		return Msg.say(this, str, "", 22);
	}
	
	public BaseScriptExecutor setKeyLocker(boolean b){
		return Msg.setKeyLocker(this, b);
	}
	
	public BaseScriptExecutor lock(boolean b){
		return Move.lock(this, b);
	}
	
	public BaseScriptExecutor faceToHero(){
		return Move.faceToHero(this);
	}
	
	public BaseScriptExecutor faceTo(int face){
		return Move.turn(this, face);
	}
	
	public BaseScriptExecutor move(int step){
		return Move.move(this, step);
	}
	
	public BaseScriptExecutor wait(int frame){
		return Timer.wait(this, frame);
	}
	
	public BaseScriptExecutor removeSelf(){
		return Base.removeSelf(this);
	}
	
	public BaseScriptExecutor changeSelf(Class<? extends Script> script){
		return Base.changeSelf(this,script);
	}
	
	public BaseScriptExecutor showMSG(String msgType){
		return Msg.show(this, msgType);
	}
	
	public BaseScriptExecutor showMSG(){
		return Msg.show(this, 正常);
	}
	
	public BaseScriptExecutor hideMSG(){
		return Msg.hide(this);
	}
	
	public BaseScriptExecutor hideFG(int position){
		return FG.hide(this, position);
	}
	
	public BaseScriptExecutor hideFG(){
		return FG.hideAll(this);
	}
	
	public BaseScriptExecutor showFGLeft(String people,String look){
		return FG.show(this, Setting.GAME_RES_IMAGE_FG+people+look+".png", FG.LEFT);
	}
	
	public BaseScriptExecutor showFGRight(String people,String look){
		return FG.show(this, Setting.GAME_RES_IMAGE_FG+people+look+".png", FG.RIGHT);
	}
	
	public BaseScriptExecutor swapHeroQueue(int position){
		return Heros.swapHeroQueue(this, position);
	}
	
	public BaseScriptExecutor swapHeroQueue(Class<? extends Hero> c){
		return Heros.swapHeroQueue(this, c);
	}
	
	public BaseScriptExecutor swapHeroQueue(int position,int position2){
		return Heros.swapHeroQueue(this, position, position2);
	}
	
	public BaseScriptExecutor swapHeroQueue(Class<? extends Hero> c1,Class<? extends Hero> c2){
		return Heros.swapHeroQueue(this, c1 , c2);
	}
	
	public BaseScriptExecutor setGameTime(GameDate.Time time){
		return ColorUtil.set(this, time);
	}
	
	public BaseScriptExecutor select(String ... args){
		return GameViews.selectUtil.select(this, args);
	}
	
	public String currentSelect(){
		return SelectUtil.currentSelect;
	}
	
	
	public BaseScriptExecutor randomWalk(int speed,int length){
		return Move.random(this, speed,length,null, null);
	}
	
	public BaseScriptExecutor randomWalk(int speed,int length,Vector2 bounds,Vector2 poi){
		return Move.random(this, speed,length,bounds, poi);
	}
	
	public BaseScriptExecutor randomWalkByHero(int speed,int length){
		return Move.random(this,speed,length,null,new Vector2(-1,-1));
	}
	
	public BaseScriptExecutor randomWalkBySelf(int speed,int length){
		return Move.random(this,speed,length,new Vector2(3,3),new Vector2(this.npc.mapx,this.npc.mapy));
	}
	
	public BaseScriptExecutor setWeather(int type){
		return WeatherUtil.setWeather(this, type);
	}
	
	public BaseScriptExecutor setCameraPositionWithHero(final int x,final int y,final boolean wait){
		return MoveController.setCameraPositionWithHero(this,x,y,wait);
	}
	
	public BaseScriptExecutor waitCameraMove(){
		return MoveController.waitCameraMove(this);
	}
	
	/**
	 * X/Y BASE ( LEFT/BOTTOM ) 
	 */
	public BaseScriptExecutor setCameraPositionWithAbsolute(final int x,final int y,final boolean wait){
		return MoveController.setCameraPositionWithAbsolute(this, x, y, wait);
	}
	
	
	public boolean currentSelect(String equ){
		return SelectUtil.currentSelect.equals(equ);
	}
}
