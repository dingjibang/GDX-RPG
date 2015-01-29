package com.rpsg.rpg.system.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.game.Logger;


public class ThreadPool {

	public static List<List<Script>> pool =new LinkedList<List<Script>>();
	
	private static List<Script> removeList=new ArrayList<Script>();
	public static synchronized void logic(){
		removeList.clear();
		try{
			for(int i=0;i<pool.size();i++){
				List<Script> list=pool.get(i);
				for(Script s:list)
					if(s.isAlive)
						s.run();
					else
						removeList.add(s);
				list.removeAll(removeList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static synchronized void init(){
		for(int i=0;i<pool.size();i++){
			pool.get(i).clear();
		}
		pool.clear();
	}
}
