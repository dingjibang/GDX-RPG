package com.rpsg.rpg.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;

/**
 *	GDX-RPG I/O 类 
 */
public class File {
	
	/**
	 * 将一个对象存储到硬盘上
	 */
	public static void save(Object o, String filePath){
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
	public static Object load(String fileName) {
		try {
			Object o;
			
			FileInputStream fis = new FileInputStream(Gdx.files.local(fileName).file());
			ObjectInputStream ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
			return o;
		} catch (Exception e) {
			return null;
		}
	}
}
