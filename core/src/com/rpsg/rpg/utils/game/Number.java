package com.rpsg.rpg.utils.game;

import com.rpsg.gdxQuery.CustomCallback;

public class Number<T extends java.lang.Number> {
	T num,num2;
	private Number(T num){
		this.num = num;
		this.num2 = num;
	}
	
	public Number<T> set(CustomCallback<T,T> run){
		num = run.run(this.num);
		return this;
	}
	
	public T get(){
		return num;
	}
	
	public T fin(){
		return num2;
	}
	
	private Number(){}
	
	public static <T2 extends java.lang.Number> Number<T2> of(T2 t2){
		return new Number<T2>(t2);
	}
	
	@Override
	public String toString() {
		return num.toString();
	}
}
