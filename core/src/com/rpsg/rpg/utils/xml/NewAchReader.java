package com.rpsg.rpg.utils.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.rpsg.rpg.object.base.BaseAchievement;

public class NewAchReader {

	public static List<BaseAchievement> get(String path) {
		
		List<BaseAchievement>  l= new ArrayList<BaseAchievement>();
		FileHandle file = new FileHandle(new File(path) );
		
		XmlReader reader = new XmlReader();
		
		try {
			Array<Element> a =reader.parse(file).getChildrenByName("ach");
			for (Element e : a){
				
				BaseAchievement ach = new BaseAchievement(
						e.get("number"), e.get("title"),
						e.get("content"), e.get("type"),
						e.get("status"),
						e.get("compare"), e.get("judge"),
						e.get("deal"));
				
				l.add(ach);
				
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		return l;

	}

	public static void main(String[] args) {
		for (BaseAchievement ach:NewAchReader.get("src/com/rpsg/rpg/game/achievement/Ach.xml")){;
		System.out.println(ach.judgeString);}
	}

}
