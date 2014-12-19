package com.rpsg.rpg.object.script;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


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
