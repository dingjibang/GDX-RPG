package com.rpsg.rpg.util;

import com.rpsg.gdxQuery.GdxFrame;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.core.UI;

public class UIUtil {

	public static GdxQuery $ (Object... a){
		return new GdxQuery(a);
	}

	public static GdxQuery $(String text, int size){
		return Res.text.getLabel(text, size).query();
	}

	public static GdxQuery $ (String param){
		if(param.equals("base"))
			return new GdxQuery(UI.base());

		return new GdxQuery(Res.get(param));
	}

	public static GdxFrame $(GdxQuery query, GdxQueryRunnable runnable){
		return new GdxFrame().add(query, runnable);
	}

}