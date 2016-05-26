package com.rpsg.rpg.object.base.items;

import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.rpg.Target;

public class Result {
	public int animateId;
	public boolean success;
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
}
