package com.rpsg.rpg.system.base;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.utils.Disposable;

public abstract class StackView extends IView implements Disposable{
	public List<IView> viewStack=new ArrayList<IView>();
	
}