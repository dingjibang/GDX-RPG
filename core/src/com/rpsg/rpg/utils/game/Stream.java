package com.rpsg.rpg.utils.game;

import java.util.List;

import java8.util.stream.RefStreams;

public class Stream {
	@SuppressWarnings("unchecked")
	public static <T> java8.util.stream.Stream<T> of(List<T> list){
		return RefStreams.of((T[])list.toArray());
	}
}
