package com.rpsg.rpg.util;

import java.util.Collection;
import java8.util.stream.StreamSupport;

/**
 * {@link java8.util.stream.Stream Stream}的快速访问类
 */
public class Stream {
	
	public static <T> java8.util.stream.Stream<T> of(Collection<T> list){
		return StreamSupport.stream(list);
	}
}
