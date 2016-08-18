package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Files;
import com.rpsg.rpg.object.base.Achievement;
import com.rpsg.rpg.object.base.BaseTask;
import com.rpsg.rpg.object.base.Task;
import com.rpsg.rpg.object.base.Task.TriggerType;
import com.rpsg.rpg.object.base.TaskInfo;

/** 成就、任务控制器 **/
@SuppressWarnings("unchecked")
public class TaskController {

	ArrayList<Task> currentTask;
	ArrayList<Achievement> currentAchievement = new ArrayList<>();
	List<TaskInfo<Achievement>> achHistory = new ArrayList<>();
	List<TaskInfo<Task>> taskHistory = new ArrayList<>();

	/** 读取成就目录 */
	public void init() {
		Object ach = Files.load(Achievement.fileName);
		Object info = Files.load(TaskInfo.fileName);

		if (null != ach) {
			currentAchievement = (ArrayList<Achievement>) ach;
		}else{
			/**没有成就文件时，新建成就文件并读取所有成就*/
			for(int id : list(Gdx.files.internal(Setting.SCRIPT_DATA_ACHIEVEMENT)))
				currentAchievement.add(Achievement.fromJSON(id));
			saveAchievement();
		}
		
		if (null != info) 
			achHistory = (List<TaskInfo<Achievement>>) info;
	}
	
	private List<Integer> list(FileHandle file) {
		List<Integer> list = new ArrayList<>();
		int count = 0;
		while(file.child(++count + ".grd").exists())
			list.add(count);
		return list;
	}
	
	public void saveAchievementHistory() {
		Files.save(achHistory, TaskInfo.fileName);
	}

	public void initTask(){
		currentTask = RPG.global.currentTask;
		taskHistory = RPG.global.taskHistory;
	}

	/** 保存成就目录到文件 */
	public void saveAchievement() {
		Files.save(currentAchievement, Achievement.fileName);
	}
	
	/** 遍历任务和成就，判断完成情况 */
	public void logic(){
		List<BaseTask> list = new ArrayList<>();
		if(currentTask != null){
			for(Task task : currentTask)
				if(task.trigger == TriggerType.auto)
					list.add(task);
		}
		for(Achievement ach : currentAchievement)
			if(ach.trigger == TriggerType.auto)
				list.add(ach);
		
		$.removeIf(list, BaseTask::canEnd, this::end);
	}
	
	
	/** 增加任务 */
	public void add(Task task){
		currentTask.add(task);
	}
	
	/** 增加任务 */
	public void add(int id){
		add(Task.fromJSON(id));
	}
	
	private Task getTask(int id){
		return $.getIf(currentTask, t-> t.id == id);
	}
	
	private Achievement getAchievement(int id){
		return $.getIf(currentAchievement, t-> t.id == id);
	}
	
	/** 查询是否正在进行任务*/
	public boolean has(int id){
		return currentTask.size() != 0 && $.anyMatch(currentTask, t-> t.id == id);
	}
	
	/** 查询是否做过某个任务(不是成就)*/
	public boolean hasDone(int id){
		return $.allMatch(taskHistory, t-> t.task.id == id && t.task.getClass().equals(Task.class));
	}
	
	
	/** 查询某个任务是否可以完成，前提是这个任务存在的情况下*/
	public boolean canBeDone(int id){
		return has(id) && getTask(id).canEnd();
	}
	
	/** 获得当前进行中的任务（副本） */
	public List<Task> task(){
		return (List<Task>) currentTask.clone();
	}
	
	/** 获得当前进行中的成就（副本）*/
	public List<Achievement> achievement(){
		return (List<Achievement>) currentAchievement.clone();
	}
	
	/** 获得全部成就（进行中以及已完成的）（副本）*/
	public List<Achievement> allAchievement(){
		List<Achievement> list = achievement();
		list.addAll($.map(achHistory, TaskInfo::task));
		return list;
	}
	
	public boolean isAchievementDoing(Achievement ach){
		return currentAchievement.contains(getAchievement(ach.id));
	}
	
	
	/**完成一个任务（非成就）**/
	public void endTask(int id){
		end(getTask(id));
	}
	
	/**完成一个成就（非任务）**/
	public void endAchievement(int id){
		end(getAchievement(id));
	}
	
	/**强制让一个任务状态变为已完成（但并不提交）*/
	public Task forceStop(int id){
		Task task = getTask(id);
		if(task == null) return task;
		task.end.forceStop();
		return task;
	}
	
	/** 完成一个任务或成就 */
	public void end(BaseTask task) {
		if(task == null) return;
		
		boolean isTask = task instanceof Task;
		if (isTask){
			currentTask.remove(task);
			taskHistory.add(TaskInfo.create((Task)task));
			task.gain();
		}else{
			currentAchievement.remove(task);
			saveAchievement();
			achHistory.add(TaskInfo.create((Achievement)task).gained(!task.hasGain()));
			saveAchievementHistory();
		}
		
		
		RPG.toast.add((isTask ? "完成任务" : "获得成就") + "\n\"" + task.name + "\"\n" + task.description2, Color.SKY, 22, true, task.getIcon());
	}
	
	/**放弃一个任务*/
	public void removeTask(int taskId) {
		$.removeIf(currentTask, t -> t.id == taskId);
	}
	
	public int taskIndexOf(int taskId){
		int index = -1;
		
		for(int i = 0; i < currentTask.size(); i++)
			if(currentTask.get(i).id == taskId)
				return i;
		
		return index;
	}

	public List<TaskInfo<Achievement>> achievementHistory() {
		return achHistory;
	}

	public boolean isAchievementGained(Achievement ach) {
		TaskInfo<?> info = $.getIf(achHistory, i -> i.task == ach);
		boolean gained = info != null ? info.gained() : false; 
		return gained;
	}
}
