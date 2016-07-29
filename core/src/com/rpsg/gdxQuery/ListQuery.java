package com.rpsg.gdxQuery;

import java.util.ArrayList;
import java.util.List;

public class ListQuery<T1>{
	
	private List<T1> list = new ArrayList<>();

	public ListQuery(T1 t1) {
		add(t1);
	}
	
	public ListQuery(Class<T1> t1) {
		
	}

	public ListQuery() {
	}

	public ListQuery<T1> add(T1 t1){
		list.add(t1);
		return this;
	}
	
	public List<T1> get(){
		return list;
	}

}
