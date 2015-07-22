package com.rpsg.rpg.utils.game;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AchUtil {
	
    public static List<Class<?>> getAllAssignedClass(Class<?> cls) throws Exception {  
        List<Class<?>> classes = new ArrayList<Class<?>>();  
        for (Class<?> c : getClasses(cls)) {  
            if (cls.isAssignableFrom(c) && !cls.equals(c)) {  
                classes.add(c);  
            }  
        }  
        return classes;  
    }  

    public static List<Class<?>> getClasses(Class<?> cls) throws Exception {  
        String pk = cls.getPackage().getName();  
        String path = pk.replace('.', '/');  
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();  
        URL url = classloader.getResource(path);  
        return getClasses(new File(url.getFile()), pk);  
    }  
   
    private static List<Class<?>> getClasses(File dir, String pk) throws ClassNotFoundException {  
        List<Class<?>> classes = new ArrayList<Class<?>>();  
        if (!dir.exists()) {  
            return classes;  
        }  
        for (File f : dir.listFiles()) {  
            if (f.isDirectory()) {  
                classes.addAll(getClasses(f, pk + "." + f.getName()));  
            }  
            String name = f.getName();  
            if (name.endsWith(".class")) {  
                classes.add(Class.forName(pk + "." + name.substring(0, name.length() - 6)));  
            }  
        }  
        return classes;  
    } 

}
