package com.rpsg.gdxQuery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class $ {
	
	public static GdxQuery add (Object... a){
		return new GdxQuery(a);
	}
	
	public static Texture getTexture (Drawable d){
		if(d instanceof TextureRegionDrawable)
			return ((TextureRegionDrawable)d).getRegion().getTexture();
		return null;
	}
	
	public static GdxQuery image (String filePath){
		return image(new Texture(Gdx.files.internal(filePath)));
	}
	
	public static <T1,T2> MapQuery<T1,T2> map(T1 t1,T2 t2){
		return new MapQuery<T1, T2>(t1,t2);
	}
	
	public static <T1,T2> MapQuery<T1,T2> map(Class<T1> t1,Class<T2> t2){
		return new MapQuery<T1, T2>();
	}
	
	public static MapQuery<Object,Object> map(){
		return new MapQuery<Object,Object>();
	}
	
	public static MapQuery<Object,Object> omap(Object o1,Object o2){
		return new MapQuery<Object,Object>(o1,o2);
	}
	
	public static GdxQuery image (String filePath,AssetManager manager){
		if(!manager.isLoaded(filePath)){
			manager.load(filePath, Texture.class);
			while(manager.update());
		}
		return image((Texture)manager.get(filePath));
	}
	
	public static GdxQuery image (Texture texture){
		return new GdxQuery(new Image(texture));
	}
	
	public static GdxFrame frame(){
		return new GdxFrame();
	}
	
	public static GdxFrame add(GdxQuery query,GdxQueryRunnable runnable){
		return new GdxFrame().add(query, runnable);
	}
	
	
	public static <T> void removeIf(Iterable<T> c,RemoveTest<T> test){
		Iterator<T> it=c.iterator();
		while(it.hasNext()){
			T obj=it.next();
			if(test.test(obj))
				it.remove();
		}
	}
	
	public static <T> void removeIf(Iterable<T> c,RemoveTest<T> test,CustomRunnable<T> get){
		Iterator<T> it=c.iterator();
		while(it.hasNext()){
			T obj=it.next();
			if(test.test(obj)){
				it.remove();
				get.run(obj);
			}
		}
	}
	
	public static <T> void each(Iterable<T> c,CustomRunnable<T> test){
		Iterator<T> it=c.iterator();
		while(it.hasNext())
			test.run(it.next());
	}
	
	public static float absoluteX(Actor actor){
		float val = 0;
		val += actor.getX();
		Actor parent = actor;
		while(true){
			Actor _p = parent.getParent();
			if(_p == null) break;
			val += parent.localToParentCoordinates(new Vector2(0,0)).x;
			parent = _p;
		}
		return val;
	}
	
	public static float absoluteY(Actor actor){
		float val = 0;
		val += actor.getY();
		Actor parent = actor;
		while(true){
			Actor _p = parent.getParent();
			if(_p == null) break;
			val += parent.localToParentCoordinates(new Vector2(parent.getX(),parent.getY())).y;
			parent = parent.getParent();
		}
		return val;
	}
	
	public static <T> void getIf(Iterable<T> c,RemoveTest<T> test,CustomRunnable<T> callback){
		Iterator<T> it=c.iterator();
		while(it.hasNext()){
			T t = it.next();
			if(test.test(t))
				callback.run(t);
		}
	}
	
	public static <T> T getIf(Iterable<T> c,RemoveTest<T> test){
		T result = null;
		for(T t : c)
			if(test.test(t))
				result = t;
		return result;
	}
	
	public static <T> void each(Iterable<T> c,Each<T> test){
		Iterator<T> it=c.iterator();
		int i=0;
		while(it.hasNext())
			test.run(i++,it.next());
	}
	
	public static String notNull(String... _str){
		for(String str : _str)
			if(str != null)
				return str;
		return null;
	}
	
	public static <T> boolean allMatch(Iterable<T> list,CustomCallback<T,Boolean> test){
		for(T t: list)
			if(!test.run(t)) return false;
		return true;
	}
	
	public static <T> boolean anyMatch(Iterable<T> list,CustomCallback<T,Boolean> test){
		for(T t: list)
			if(test.run(t)) return true;
		return false;
	}


	public static boolean has(List<?> list, Class<?> obj) {
		for(Object o : list)
			if(o.getClass().equals(obj))
				return true;
		return false;
	}
	
	public static <T,R> List<R> map(List<T> list, Map<T,R> map){
		List<R> result = new ArrayList<>();
		for(T t : list)
			result.add(map.run(t));
		return result;
	}
	
	public static <T> List<T> sort(List<T> list,Comparator<T> c){
		Collections.sort(list,c);
		return list;
	}
	
	public static <T1> ListQuery<T1> list(T1 t1){
		return new ListQuery<T1>(t1);
	}
	
	public static <T> List<T> multi(List<T> list, int multiCount){
		List<T> result = new ArrayList<>();
		for(int i = 0; i < multiCount; i++)
			result.addAll(list);
		return result;
	}
	
	
	
}