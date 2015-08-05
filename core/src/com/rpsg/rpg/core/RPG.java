package com.rpsg.rpg.core;

import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.system.controller.Hover;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.game.TimeUtil;


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
	public static AlertUtil alert = new AlertUtil();
	public static TimeUtil time = new TimeUtil();
	
	public static void putMessage(String msg,Image image){
		alert.add(msg, image);
	}
}
