package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.util.Date;

import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;

public class TaskInfo<T extends BaseTask> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static String fileName = Setting.PERSISTENCE+"history.es";
	
	public Date date;
	public T task;
	boolean gained = true;
	
	public static <T2 extends BaseTask> TaskInfo<T2> create(T2 task){
		TaskInfo<T2> info = new TaskInfo<T2>();
		info.date = new Date();
		info.task = task;
		return info;
	}
	
	public TaskInfo<T> gained(boolean flag){
		this.gained = flag;
		RPG.ctrl.task.saveAchievement();
		RPG.ctrl.task.saveAchievementHistory();
		return this;
	}
	
	public boolean gained(){
		return gained;
	}
	
	public T task(){
		return task;
	}
}
