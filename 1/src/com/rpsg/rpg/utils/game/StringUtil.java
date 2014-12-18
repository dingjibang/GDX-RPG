package com.rpsg.rpg.utils.game;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static String dereplication(String str){
		 List<String> data = new ArrayList<String>();
	        for (int i = 0; i < str.length(); i++) {
	            String s = str.substring(i, i + 1);
	            if (!data.contains(s))
	                data.add(s);
	        }
	        String result = "";
	        for (String s : data) 
	            result += s;
	        return result;
	}
	
	public static String remove(String str,String chars){
		return str.substring(0,str.indexOf(chars))+str.substring(str.indexOf(chars)+1,str.length());
	}
	
}
