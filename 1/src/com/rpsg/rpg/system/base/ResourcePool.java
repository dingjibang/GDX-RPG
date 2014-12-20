package com.rpsg.rpg.system.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ResourcePool {
	private static Map<String,Image> pool=new HashMap<String, Image>();
	
	public static Image get(String resPath){
		System.out.println(pool.size());
		if(pool.size()>CACHE_MAX_SIZE){
			Iterator<String> it=pool.keySet().iterator();
			int poi=0;
			while(it.hasNext()){
				String key=it.next();
				if(++poi>CACHE_MAX_SIZE)
					pool.remove(key);
			}
		}
		if(pool.get(resPath)==null)
			pool.put(resPath, new Image(resPath));
		return new Image(pool.get(resPath));
	}
	
	public static int CACHE_MAX_SIZE=30;
	
}
