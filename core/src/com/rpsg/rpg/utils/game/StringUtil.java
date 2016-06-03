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
	
	public static boolean isNumber(String str){
		try {
			if(isEmpty(str)) return false;
			Double.valueOf(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isEmpty(String str){
		return str == null || str.length() == 0;
	}
	
	public static String join(List<String> list,char sp){
		String result = "";
		for(String str : list)
			result += str + sp;
		if(result.length() != 0)
			result = result.substring(0, result.length() - 1);
		return result;
	}
	
}
