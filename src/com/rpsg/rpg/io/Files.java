package com.rpsg.rpg.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.utils.game.Logger;

public class Files {

	static File f=new File(getABSPath()+"/save/1.dat");
	public static void save(Object o){
		try {
			FileOutputStream fos=new FileOutputStream(f);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(o);
			oos.close();
			Logger.info("存档["+f.getAbsolutePath()+"]成功");
		} catch (Exception e) {
			Logger.error("存档["+f.getAbsolutePath()+"]失败",e);
		}
	}
	
	public static boolean empty(String filePath){
		return !Gdx.files.internal(filePath).exists();
	}
	
	public static Object load(){
		try {
			Object o;
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
			Logger.info("读档["+f.getAbsolutePath()+"]成功");
			return o;
		} catch (Exception e) {
			Logger.error("读档["+f.getAbsolutePath()+"]失败",e);
		}
		return null;
	}
	
	public static String getABSPath(){
		return System.getProperty("user.dir");
	}
	
	public static Object load(String fileName){
		try {
			Object o;
			FileInputStream fis = new FileInputStream(new File(getABSPath()+fileName));
			ObjectInputStream ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void save(Object o,String fileName){
		try {
			FileOutputStream fos=new FileOutputStream(new File(getABSPath()+fileName));
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(o);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
