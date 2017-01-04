package com.rpsg.rpg.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 延时运行工具<br>
 * LibGDX本身也有个{@link Timer}工具，但是他的实现是靠多线程的，为了避免吃到意外的屎，只好造一个轮子了2333，该轮子不依靠线程，而是在主线程的主循环里工作。
 */
public class Timer {
	static List<Task> list = new ArrayList<>();
	public static final int FOREVER = -1;
	
	public static Task add(int delayFrame, int repeat, Runnable run) {
		Task task = new Task();
		task.run = run;
		task.time = task.originTime = delayFrame;
		task.repeat = repeat;
		list.add(task);
		
		return task;
	}
	
	public static Task add(int delayFrame, Runnable run) {
		return add(delayFrame, 1, run);
	}
	
	public static void act() {
		if(!list.isEmpty()){
			List<Task> removeList = new ArrayList<>();
			for(Task timer : list)
				if(timer.time -- < 0){
					timer.run.run();
					if(timer.repeat != FOREVER &&  -- timer.repeat == 0)
						removeList.add(timer);
					else
						timer.time = timer.originTime;
				}
			
			list.removeAll(removeList);
					
		}
	}
	
	public static void remove(Task timer) {
		list.remove(timer);
	}
	
	public static class Task{
		public int time, originTime;
		public int repeat = 1;
		public Runnable run;
		
	}
}
