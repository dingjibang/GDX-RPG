package com.rpsg.rpg.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.rpsg.gdxQuery.GdxFrame;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.gdxQuery.TypedGdxQuery;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.core.UI;
import com.rpsg.rpg.ui.widget.Image;

public class UIUtil {

	public static GdxQuery $ (Object... a){
		return new GdxQuery(a);
	}

	public static <T extends Actor> TypedGdxQuery<T> $(T t){
		return new TypedGdxQuery<T>(t);
	}

	public static TypedGdxQuery<Label> $(String text, int size){
		return new TypedGdxQuery<>(Res.text.getLabel(text, size));
	}

	public static TypedGdxQuery<Image> $ (String param){
		if(param.equals("base"))
			return $();
		return new TypedGdxQuery<>(Res.get(param));
	}

	public static TypedGdxQuery<Image> $(){
		return new TypedGdxQuery<Image>(UI.base());
	}

	public static GdxFrame $(GdxQuery query, GdxQueryRunnable runnable){
		return new GdxFrame().add(query, runnable);
	}

	public static GdxQuery q(Object... a){
		if(a[0].equals("base"))
			return new GdxQuery(UI.base());
		return new GdxQuery(a);
	}

}