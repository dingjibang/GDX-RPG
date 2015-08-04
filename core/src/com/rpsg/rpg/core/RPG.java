package com.rpsg.rpg.core;

import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.system.controller.Hover;


/**
 * GDX-RPG Engine Context Class <br>
 * All RPG game object you can visitd by this class
 * @author dingjibang
 */
public class RPG {
	public static Controllers ctrl = new Controllers();
	public static Maps maps = new Maps();
	public static Hover hover = new Hover();
	public static Input input = new Input();
	public static Global global = new Global();
}
