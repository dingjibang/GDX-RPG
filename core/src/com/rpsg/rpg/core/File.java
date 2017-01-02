package com.rpsg.rpg.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 *	GDX-RPG I/O 类 
 */
public class File {
	
	private static JsonReader jsonReader;
	
	/**
	 * 将一个对象存储到硬盘上
	 */
	public static void save(Serializable o, String filePath){
		try {
			FileOutputStream fos = new FileOutputStream(Gdx.files.local(filePath).file());
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(o);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从路径中读取对象<i>(可能为null)</i>
	 */
	public static Serializable load(String fileName) {
		try {
			Object o;
			
			FileInputStream fis = new FileInputStream(Gdx.files.local(fileName).file());
			ObjectInputStream ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
			return (Serializable)o;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 从文件中读取内容并把内容返回为{@link String}
	 */
	public static String readString(String fileName) {
		return Gdx.files.internal(fileName).readString("utf-8");
	}
	
	/**
	 * 从文件中读取{@link JsonValue json}
	 */
	public static JsonValue readJSON(String fileName) {
		return (jsonReader == null ? jsonReader = new JsonReader() : jsonReader).parse(Gdx.files.internal(fileName));
	}
}
