package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.MoveStack;
import com.rpsg.rpg.system.controller.BattleController.State;
import com.rpsg.rpg.view.GameViews;

/**
 * 用来控制一组{@link Hero 英雄}进行行走、转向、增删等，他同时应用在蚂蚁队列上（HeadHero行走，后面的Hero自动跟随）
 * @author dingjibang
 *
 */
public class HeroController {
	public ArrayList<Hero> allHeros;
	public ArrayList<Hero> currentHeros;
	
	public boolean show = true;
	
	public ArrayList<MoveStack> stack = new ArrayList<>();
	
	public boolean thisFrameGeneratedPosition=false;
	
	boolean walk;
	
	public void initList(){
		allHeros = RPG.global.allHeros;
		currentHeros = RPG.global.currentHeros;
	}
	
	/**
	 * cloner of currentheros
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Hero> currentHeros(){
		return (ArrayList<Hero>)currentHeros.clone();
	}
	
	public synchronized void act(){
		if(currentHeros == null || currentHeros.size() == 0) {
			initList();
			return;
		};
		for(Hero hero:currentHeros)
			hero.act(0);
		
		if(walked(false) && !stack.isEmpty() && RPG.ctrl.battle.state != State.wait){
			MoveStack last=stack.get(0);
			stack.remove(0);
			turn(last.face).walk(last.step,false).testWalk();
		}
	}
	
	public Hero add(int id) {
		Hero hero = getHero(id);
		if(hero==null){
			hero = new Hero();
			RPG.executeJS(Gdx.files.internal(Setting.SCRIPT_DATA_HERO+id+".grd").readString("utf-8"), hero);
			allHeros.add(hero);
		}
		currentHeros.add(hero);
		return hero;
	}
	
	public void generatePosition(int x,int y,int z){
		for(int i=0;i<currentHeros.size();i++){
			if(i==0){
				currentHeros.get(i).generatePosition(x, y, z);
			}else
				if(currentHeros.get(i-1).getCurrentFace()==Hero.FACE_D)
					currentHeros.get(i).generatePosition(x, y-1, z);
				else if(currentHeros.get(i-1).getCurrentFace()==Hero.FACE_U)
					currentHeros.get(i).generatePosition(x, y+1, z);
				else if(currentHeros.get(i-1).getCurrentFace()==Hero.FACE_L)
					currentHeros.get(i).generatePosition(x+1, y, z);
				else if(currentHeros.get(i-1).getCurrentFace()==Hero.FACE_R)
					currentHeros.get(i).generatePosition(x-1, y, z);
		}
		thisFrameGeneratedPosition=true;
	}
	
	public Hero getHeadHero(){
		if(currentHeros == null || currentHeros.size() == 0) return null;
		return currentHeros.get(0);
	}
	
	public Hero getHero(int id){
		if(allHeros == null) initList();
		for(Hero hero:allHeros)
			if(hero.id == id)
				return hero;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Hero> getOtherHeros(boolean filter){
		ArrayList<Hero> list = (ArrayList<Hero>)allHeros.clone();
		list.removeAll(currentHeros);
		if(filter) $.removeIf(list, (obj)->obj.support == null);
		return list;
	}
	
	public void initHeros(Stage s){
		initList();
		for(Hero hero:currentHeros){
			hero.init();
			hero.enableCollide=false;
			hero.waitWhenCollide=false;
			s.addActor(hero);
		}
		if(currentHeros!=null && !currentHeros.isEmpty()){
			getHeadHero().enableCollide=true;
			getHeadHero().waitWhenCollide=false;
		}
	}
	
	public synchronized void pushStack(ArrayList<MoveStack> stack){
		this.stack=stack;
	}
	
	public void reinit(boolean byTeleport){
		initList();
		for(int i=0;i<currentHeros.size();i++){
			if(i!=0){
				currentHeros.get(i).currentImageNo=currentHeros.get(0).currentImageNo;
				currentHeros.get(i).lastZ=currentHeros.get(0).lastZ;
				currentHeros.get(i).lastWalkSize=0;
			}
			currentHeros.get(i).enableCollide=false;
			currentHeros.get(i).waitWhenCollide=false;
			
			if(byTeleport){
				currentHeros.get(i).walkStack.clear();
				currentHeros.get(i).walked=true;
			}
		}
		generatePosition(getHeadHero().mapx, getHeadHero().mapy, getHeadHero().layer);
		if(currentHeros!=null && !currentHeros.isEmpty()){
			getHeadHero().enableCollide=true;
			getHeadHero().waitWhenCollide=false;
		}
	}
	
	public void remove(Hero hero){
		currentHeros.remove(hero);
		GameViews.gameview.stage.getActors().removeValue(hero, true);
	}
	
	public void remove(int id){
		remove(getHero(id));
	}
	
	public void removeHero(int id){
		currentHeros.remove(getHero(id));
	}
	
	public void setVisible(boolean visible){
		for(Hero hero:currentHeros)
			hero.setVisible(visible);
	} 
	
	public void setWalkSpeed(int speed){
		for(Hero hero:currentHeros)
			hero.setWalkSpeed(speed);
	}
	public void stopStack(){
		stack.clear();
	}

	
	public void swapHero(int position){
		Collections.swap(currentHeros, position, 0);
		reinit(false);
	}
	public void swapHero(int position,int swapposition){
		Collections.swap(currentHeros, position, swapposition);
		reinit(false);
	}
	
	public HeroController testWalk(){
		for(Hero hero:currentHeros)
			hero.testWalk();
		return this;
	}
	
	
	public void toWalk() {
		for(Hero hero:currentHeros)
			hero.toWalk();
	}
	
	public HeroController turn(int face){
		for(int i=0;i<currentHeros.size();i++){
			if(i==0)
				currentHeros.get(i).turn(face);
			else
				if(walk)
					currentHeros.get(i).turn(currentHeros.get(i-1).lastFace);
		}
		return this;
	}
	
	public HeroController walk(int step){
		return walk(step,true);
	}
	
	public HeroController walk(int step,boolean clear){	
		walk=getHeadHero().walk(step).testWalk();
		if(walk)
		for(int i=1;i<currentHeros.size();i++){
			currentHeros.get(i).walk(currentHeros.get(i-1).lastWalkSize);
			currentHeros.get(i).layer=currentHeros.get(i-1).lastZ;
		}
		if(clear)
			stopStack();
		return this;
	}
	
	public boolean walked(){
		return walked(true);
	}

	public boolean walked(boolean dirty){
		if(dirty)
			stopStack();
		return getHeadHero().walked;
	}
}
