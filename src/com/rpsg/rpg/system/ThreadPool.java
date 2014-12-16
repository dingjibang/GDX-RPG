package com.rpsg.rpg.system;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.rpsg.rpg.object.Script;

public class ThreadPool {
	public static List<List<Script>> pool =new LinkedList<List<Script>>();
	
	private static List<Script> removeList=new ArrayList<Script>();
	public static void logic(){
		for(List<Script> list:pool){
			removeList.clear();
			for(Script s:list)
				if(s.isAlive)
					s.run();
				else
					removeList.add(s);
			list.removeAll(removeList);
		}
	}
}
