package com.rpsg.rpg.object.base.items;

import java.util.ArrayList;
import java.util.List;

import com.rpsg.rpg.object.rpg.Target;

public class BattleResult {
	public int animateId;
	public boolean success;
	public List<Target> targetList = new ArrayList<>();
	
	public static BattleResult success(int id,List<Target> list){
		BattleResult r = new BattleResult();
		r.success = true;
		r.animateId = id;
		r.targetList = list;
		return r;
	}
	
	public static BattleResult faild(){
		BattleResult r = new BattleResult();
		r.success = false;
		return r;
	}
}
