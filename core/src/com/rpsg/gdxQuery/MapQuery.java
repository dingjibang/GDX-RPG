package com.rpsg.gdxQuery;

import java.util.HashMap;

public class MapQuery<T1,T2> extends HashMap<T1, T2>{
	private static final long serialVersionUID = 1L;

	public MapQuery(T1 t1, T2 t2) {
		add(t1,t2);
	}
	
	public MapQuery(Class<T1> t1, Class<T2> t2) {
		
	}

	public MapQuery() {
	}

	public MapQuery<T1,T2> add(T1 t1,T2 t2){
		put(t1,t2);
		return this;
	}

}
