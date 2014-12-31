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
			Logger.info("´æµµ["+f.getAbsolutePath()+"]³É¹¦");
		} catch (Exception e) {
			Logger.error("´æµµ["+f.getAbsolutePath()+"]Ê§°Ü",e);
		}
	}
	
	public static Object load(){
		try {
			Object o;
			FileInputStream fis = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fis);
			o = ois.readObject();
			ois.close();
			Logger.info("¶Áµµ["+f.getAbsolutePath()+"]³É¹¦");
			return o;
		} catch (Exception e) {
			Logger.error("¶Áµµ["+f.getAbsolutePath()+"]Ê§°Ü",e);
		}
		return null;
	}
	
	public static String getABSPath(){
		return System.getProperty("user.dir");
	}
}
