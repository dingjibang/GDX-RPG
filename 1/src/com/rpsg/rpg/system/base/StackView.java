package com.rpsg.rpg.system.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Disposable;

public abstract class StackView extends IView implements Disposable{
	public List<IView> viewStack=new ArrayList<IView>();
	public Map<String,Object> params=new HashMap<String, Object>(); 
}