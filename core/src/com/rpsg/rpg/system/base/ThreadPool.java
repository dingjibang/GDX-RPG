package com.rpsg.rpg.system.base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.utils.game.Logger;

/**
 * 名字好像很牛逼啊<br>
 * 线程池233<br>
 * <br>
 * 其实并不，只是单线程模拟多线程而已<br>
 * @author dingjibang
 *
 */
public class ThreadPool {

	public List<List<Script>> pool =new LinkedList<List<Script>>();
	 
	private List<Script> removeList=new ArrayList<Script>();
	public synchronized void logic(){
		removeList.clear();
		try{
			for(int i=0;i<pool.size();i++){
				List<Script> list=pool.get(i);
				for(Script s:list)
					if(s.isAlive)
						s.step();
					else
						removeList.add(s);
				list.removeAll(removeList);
			}
		}catch(Exception e){
			Logger.error("脚本迭代错误");
			e.printStackTrace();
		}
	}
	
	public synchronized void init(){
		for(int i=0;i<pool.size();i++){
			pool.get(i).clear();
		}
		pool.clear();
	}
}
