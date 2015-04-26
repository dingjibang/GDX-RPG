package com.rpsg.rpg.system.controller;

import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.view.GameViews;

public class HeroController {
	public static List<Hero> heros;
	public static List<Hero> allHeros;
	
	public static void act(){
		for(Hero hero:heros)
			hero.act(0);
	}
	
	public static Hero getHeadHero(){
		return heros.get(0);
	}
	
	public static Hero newHero(Class<? extends Hero> c){
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
	
	public static void addHero(Class<? extends Hero> c){
		heros.add(getHero(c));
	}
	
	public static void addHero(Class<? extends Hero> c,int position){
		heros.add(position,getHero(c));
	}
	
	public static void removeHero(Class<? extends Hero> c){
		heros.remove(getHero(c));
	}
	
	public static void deleteHero(Class<? extends Hero> c){
		allHeros.remove(getHero(c));
	}
	
	public static void swapHero(int position){
		Collections.swap(heros, position, 0);
		reinit();
	}
	
	public static void swapHero(Class<? extends Hero> c){
		Collections.swap(heros, heros.indexOf(getHero(c)), 0);
		reinit();
	}
	
	public static void swapHero(int position,int swapposition){
		Collections.swap(heros, position, swapposition);
		reinit();
	}
	
	public static void swapHero(Class<? extends Hero> c,Class<? extends Hero> swapc){
		Collections.swap(heros, heros.indexOf(getHero(c)), heros.indexOf(getHero(swapc)));
		reinit();
	}
	
	public static Hero getHero(Class<? extends Hero> c){
		for(Hero hero:allHeros)
			if(hero.getClass().equals(c))
				return hero;
		return null;
	}
	
	public static void initControler(){
		heros=GameViews.global.currentHeros;
		allHeros= GameViews.global.heros;
	}
	
	public static void initHeros(Stage s){
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
	
	public static void reinit(){
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
	
	public static void reinitByTeleport(){
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
	
	public static void generatePosition(int x,int y,int z){
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
	}

	
	public static void dispose() {
		for(Hero hero:heros)
			hero.dispose();
	}
	static boolean walk;
	public static void walk(int step){	
		//TODO FUCKME2
		walk=getHeadHero().walk(step).testWalk();
		if(walk)
		for(int i=1;i<heros.size();i++){
			heros.get(i).walk(heros.get(i-1).lastWalkSize);
			heros.get(i).layer=heros.get(i-1).lastZ;
		}
	}
	
	public static void turn(int face){
		for(int i=0;i<heros.size();i++){
			if(i==0)
				heros.get(i).turn(face);
			else
				if(walk)
					heros.get(i).turn(heros.get(i-1).lastFace);
		}
	}
	
	public static void testWalk(){
		for(int i=0;i<heros.size();i++){
			if(i==0)
				heros.get(i).testWalk();
			else
				heros.get(i).testWalk();
		}
	}
	
	public static boolean walked(){
		return getHeadHero().walked;
	}
	
	public static void setWalkSpeed(int speed){
		for(int i=0;i<heros.size();i++){
			heros.get(i).setWalkSpeed(speed);
		}
	}
}
