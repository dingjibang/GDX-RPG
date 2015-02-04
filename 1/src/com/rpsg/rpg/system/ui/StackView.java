package com.rpsg.rpg.system.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Disposable;

public abstract class StackView extends View implements Disposable{
	public List<View> viewStack=new ArrayList<View>();
	public Map<String,Object> params=new HashMap<String, Object>(); 
}