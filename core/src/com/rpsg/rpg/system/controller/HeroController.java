package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.MoveStack;

/**
 * 用来控制一组{@link Hero 英雄}进行行走、转向、增删等，他同时应用在蚂蚁队列上（HeadHero行走，后面的Hero自动跟随）
 * @author dingjibang
 *
 */
public class HeroController {
	public ArrayList<Hero> heros;
	public ArrayList<Hero> allHeros;
	
	public ArrayList<MoveStack> stack = new ArrayList<>();
	
	public void pushStack(ArrayList<MoveStack> stack){
		this.stack=stack;
	}
	
	public void act(){
		for(Hero hero:heros)
			hero.act(0);
		if(walked(false) && !stack.isEmpty()){
			MoveStack last=stack.get(0);
			stack.remove(0);
			RPG.maps.loader.removePath();
			turn(last.face);
			walk(last.step,false).testWalk();
		}
	}
	
	public void stopStack(){
		stack.clear();
		RPG.maps.loader.removeAllPath();
	}
	
	public Hero getHeadHero(){
		return heros.get(0);
	}
	
	public Hero newHero(Class<? extends Hero> c){
		if(getHero(c)!=null)
			return null;
		Hero h=null;
		try {
			h=(Hero)c.newInstance();
			h.first();
			allHeros.add(h);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return h;
	}
	
	public void addHero(Class<? extends Hero> c){
		heros.add(getHero(c));
	}
	
	public void setVisible(boolean visible){
		for(Hero hero:heros)
			hero.setVisible(visible);
	}
	
	public void addHero(Class<? extends Hero> c,int position){
		heros.add(position,getHero(c));
	}
	
	public void removeHero(Class<? extends Hero> c){
		heros.remove(getHero(c));
	}
	
	public void deleteHero(Class<? extends Hero> c){
		allHeros.remove(getHero(c));
	}
	
	public void swapHero(int position){
		Collections.swap(heros, position, 0);
		reinit();
	}
	
	public void swapHero(Class<? extends Hero> c){
		Collections.swap(heros, heros.indexOf(getHero(c)), 0);
		reinit();
	}
	
	public void swapHero(int position,int swapposition){
		Collections.swap(heros, position, swapposition);
		reinit();
	}
	
	public void swapHero(Class<? extends Hero> c,Class<? extends Hero> swapc){
		Collections.swap(heros, heros.indexOf(getHero(c)), heros.indexOf(getHero(swapc)));
		reinit();
	}
	
	public Hero getHero(Class<? extends Hero> c){
		for(Hero hero:allHeros)
			if(hero.getClass().equals(c))
				return hero;
		return null;
	}
	
	public void initControler(){
		heros=RPG.global.currentHeros;
		allHeros= RPG.global.heros;
	}
	
	public void initHeros(Stage s){
		for(Hero hero:heros){
			hero.init();
			hero.enableCollide=false;
			hero.waitWhenCollide=false;
			s.addActor(hero);
		}
		if(heros!=null && !heros.isEmpty()){
			getHeadHero().enableCollide=true;
			getHeadHero().waitWhenCollide=false;
		}
	}
	
	public void reinit(){
		for(int i=0;i<heros.size();i++){
			if(i!=0){
				heros.get(i).currentImageNo=heros.get(0).currentImageNo;
				heros.get(i).lastZ=heros.get(0).lastZ;
				heros.get(i).lastWalkSize=0;
			}
			heros.get(i).enableCollide=false;
			heros.get(i).waitWhenCollide=false;
		}
		generatePosition(getHeadHero().mapx, getHeadHero().mapy, getHeadHero().layer);
		if(heros!=null && !heros.isEmpty()){
			getHeadHero().enableCollide=true;
			getHeadHero().waitWhenCollide=false;
		}
	} 
	
	public void reinitByTeleport(){
		for(int i=0;i<heros.size();i++){
			if(i!=0){
				heros.get(i).currentImageNo=heros.get(0).currentImageNo;
				heros.get(i).lastZ=heros.get(0).lastZ;
				heros.get(i).lastWalkSize=0;
			}
			heros.get(i).enableCollide=false;
			heros.get(i).waitWhenCollide=false;
			heros.get(i).walkStack.clear();
			heros.get(i).walked=true;
		}
		generatePosition(getHeadHero().mapx, getHeadHero().mapy, getHeadHero().layer);
		if(heros!=null && !heros.isEmpty()){
			getHeadHero().enableCollide=true;
			getHeadHero().waitWhenCollide=false;
		}
	}
	
	public boolean thisFrameGeneratedPosition=false;
	public void generatePosition(int x,int y,int z){
		for(int i=0;i<heros.size();i++){
			if(i==0){
				heros.get(i).generatePosition(x, y, z);
			}else
				if(heros.get(i-1).getCurrentFace()==Hero.FACE_D)
					heros.get(i).generatePosition(x, y-1, z);
				else if(heros.get(i-1).getCurrentFace()==Hero.FACE_U)
					heros.get(i).generatePosition(x, y+1, z);
				else if(heros.get(i-1).getCurrentFace()==Hero.FACE_L)
					heros.get(i).generatePosition(x+1, y, z);
				else if(heros.get(i-1).getCurrentFace()==Hero.FACE_R)
					heros.get(i).generatePosition(x-1, y, z);
		}
		thisFrameGeneratedPosition=true;
	}

	
	boolean walk;
	public HeroController walk(int step,boolean clear){	
		walk=getHeadHero().walk(step).testWalk();
		if(walk)
		for(int i=1;i<heros.size();i++){
			heros.get(i).walk(heros.get(i-1).lastWalkSize);
			heros.get(i).layer=heros.get(i-1).lastZ;
		}
		if(clear)
			stopStack();
		return this;
	}
	
	public HeroController walk(int step){
		return walk(step,true);
	}
	
	
	public HeroController turn(int face){
		for(int i=0;i<heros.size();i++){
			if(i==0)
				heros.get(i).turn(face);
			else
				if(walk)
					heros.get(i).turn(heros.get(i-1).lastFace);
		}
		return this;
	}
	
	public void testWalk(){
		for(Hero hero:heros)
			hero.testWalk();
	}
	
	public boolean walked(){
		return walked(true);
	}
	
	public boolean walked(boolean dirty){
		if(dirty)
			stopStack();
		return getHeadHero().walked;
	}
	
	public void setWalkSpeed(int speed){
		for(Hero hero:heros)
			hero.setWalkSpeed(speed);
	}

	public void toWalk() {
		for(Hero hero:heros)
			hero.toWalk();
	}
}
