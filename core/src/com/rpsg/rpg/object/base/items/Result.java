package com.rpsg.rpg.object.base.items;

import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.rpg.Target;

/**
 * 一波战斗的结果
 * @author dingjibang
 *
 */
public class Result {
	//产生的动画
	public int animateId;
	//攻击是否成功
	public boolean success;
	//攻击的目标（们）
	public List<Target> targetList = new ArrayList<>();
	
	public static Result success(int id,List<Target> list){
		Result r = new Result();
		r.success = true;
		r.animateId = id;
		r.targetList = list;
		return r;
	}
	
	public static Result faild(){
		Result r = new Result();
		r.success = false;
		return r;
	}

	public static Result success() {
		Result r = new Result();
		r.success = true;
		return r;
	}
}
