package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Files;
import com.rpsg.rpg.object.base.Index;
import com.rpsg.rpg.object.base.Index.IndexType;
import com.rpsg.rpg.utils.game.Stream;

import java8.util.stream.Collectors;


/** 图鉴控制器 **/
public class IndexController {
	private static final String path = Setting.PERSISTENCE + "index.es";
	private List<Index> list = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public void init(){
		Object info = Files.load(path);
		
		//如果游戏第一次初始化，没有载入图鉴，则自动读取
		if(info != null){
			list = (List<Index>)info;
		}else{
			list = Stream.of(list(Gdx.files.internal(Setting.SCRIPT_DATA_INDEX))).map(Index::fromJSON).collect(Collectors.toList());
			save();
		}
	}
	
	public void save() {
		Files.save(list, path);
	}
	
	public void unlock(IndexType type, int index){
		$.getIf(get(type), idx -> idx.index == index, Index::unlock);
	}
	
	public void unlockFG(int index, String fg){
		$.getIf(get(IndexType.actor), idx -> idx.index == index, idx -> idx.addFG(fg));
	}

	public List<Index> get(IndexType type){
		return $.sort(Stream.of(list).filter(idx -> idx.type == type).collect(Collectors.toList()), (a,b) -> Integer.compare(a.index, b.index));
	}
	
	public List<Index> get(IndexType type, int start, int length){
		return get(type).subList(start, length);
	}
	
	public Index get(IndexType type,int index){
		return $.getIf(get(type), idx -> idx.index == index);
	}
	
	public int count(IndexType type){
		return get(type).size();
	}
	
	private List<Integer> list(FileHandle file) {
		List<Integer> list = new ArrayList<>();
		int count = 0;
		while(file.child(++count + ".grd").exists())
			list.add(count);
		return list;
	}
}
