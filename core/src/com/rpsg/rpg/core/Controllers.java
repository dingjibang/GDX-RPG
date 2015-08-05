package com.rpsg.rpg.core;

import com.rpsg.rpg.system.base.ThreadPool;
import com.rpsg.rpg.system.controller.CGController;
import com.rpsg.rpg.system.controller.DrawController;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.utils.display.FG;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.display.WeatherUtil;

public class Controllers {
	public HeroController hero = new HeroController();
	public DrawController draw = new DrawController();
	public CGController cg = new CGController();
	public FG fg = new FG();
	public Msg msg = new Msg();
	public WeatherUtil weather = new WeatherUtil();
	public ThreadPool thread = new ThreadPool();
}
