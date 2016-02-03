package com.rpsg.rpg.system.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.object.rpg.GetSpeedAble;
import com.rpsg.rpg.object.rpg.Hero;

public class Timer extends Group {
	
	private List<TimerClass> list = new ArrayList<>();
	private CustomRunnable<GetSpeedAble> callback;
	private static int total = 10000;
	
	public Timer(List<Hero> heroList, List<Enemy> enemyList,CustomRunnable<GetSpeedAble> callback) {
		this.callback = callback;
		
		List<GetSpeedAble> objectList = new ArrayList<>();
		objectList.addAll(heroList);
		objectList.addAll(enemyList);
		
		//复制速度值
		$.each(objectList, (obj) -> list.add(new TimerClass(obj, obj.getSpeed())));
		
		avg();
	}
	
	public void act(float delta) {
		$.each(list,(obj)->{
			if((obj.current += obj.speed) > total){
				//callback and reset
				callback.run(obj.object);
				obj.current = 0;
			}
		});
		super.act(delta);
	}
	
	public void remove(GetSpeedAble object){
		$.removeIf(list, (obj)->obj.object == object);
	}
	
	public void setSpeed(GetSpeedAble object,int value){
		$.getIf(list, (obj) -> obj.object == object, (obj) -> obj.speed = value);
	}
	
	public void addSpeed(GetSpeedAble object,int value){
		$.getIf(list, (obj) -> obj.object == object, (obj) -> obj.speed += value);
	}
	
	public void pause(boolean flag){
		$.each(list, (obj) -> obj.golbalPause = flag);
	}
	
	public void pause(GetSpeedAble object,boolean flag){
		$.getIf(list, (obj) -> obj.object == object, (obj) -> obj.pause = flag);
	}
	
	private void avg(){
		if(list.isEmpty()) return;
		Collections.sort(list);
		TimerClass max = list.get(0);
		float scale = 100 / max.speed;
		$.each(list, (obj) -> obj.speed *= scale);
	}
	
	public class TimerClass implements Comparable<TimerClass>{
		public GetSpeedAble object;
		public int speed;
		public int current = 0;
		public boolean pause = false,golbalPause = false;
		
		public TimerClass(GetSpeedAble object, int speed) {
			this.object = object;
			this.speed = speed;
		}

		public int compareTo(TimerClass o) {
			return this.speed - o.speed;
		}
	}
	
}
