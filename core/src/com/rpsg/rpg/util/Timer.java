package com.rpsg.rpg.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 延时运行工具
 */
public class Timer {
	static List<TimerInstance> list = new ArrayList<>();
	
	public static void add(int delayFrame, Runnable run) {
		TimerInstance timer = new TimerInstance();
		timer.run = run;
		timer.time = delayFrame;
		
		list.add(timer);
	}
	
	public static void act() {
		if(!list.isEmpty()){
			List<TimerInstance> removeList = new ArrayList<>();
			for(TimerInstance timer : list)
				if(timer.time -- < 0){
					removeList.add(timer);
					timer.run.run();
				}
			
			list.removeAll(removeList);
					
		}
	}
	
	static class TimerInstance {
		int time;
		Runnable run;
	}
}
