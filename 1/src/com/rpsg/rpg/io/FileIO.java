package com.rpsg.rpg.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.rpsg.rpg.utils.game.Logger;

public class FileIO {

	static File f=new File(getABSPath()+"/save/1.dat");
	public static void save(Object o){
		try {
			FileOutputStream fos=new FileOutputStream(f);
			ObjectOutputStream oos=new ObjectOutputStream(fos);
			oos.writeObject(o);
			oos.close();
			Logger.info("�浵["+f.getAbsolutePath()+"]�ɹ�");
		} catch (Exception e) {
			Logger.error("�浵["+f.getAbsolutePath()+"]ʧ��",e);
		}
	}
	
	public static Object load(){
		try {
			Object o;
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
			Logger.info("����["+f.getAbsolutePath()+"]�ɹ�");
			return o;
		} catch (Exception e) {
			Logger.error("����["+f.getAbsolutePath()+"]ʧ��",e);
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
		}
		return null;
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
