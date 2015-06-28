package com.rpsg.gdxQuery;

import java.util.HashMap;
import java.util.Map;

public class GdxFrame {
	private Map<GdxQuery,GdxQueryRunnable> frames = new HashMap<GdxQuery,GdxQueryRunnable>();
	
	public GdxFrame add(GdxQuery query,GdxQueryRunnable runnable){
		frames.put(query,runnable);
		return this;
	}
	
	public GdxFrame logic(){
		for(GdxQuery query:frames.keySet())
			frames.get(query).run(query);
		return this;
	}
}
