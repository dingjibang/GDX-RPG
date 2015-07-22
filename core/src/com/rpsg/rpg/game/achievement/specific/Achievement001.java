package com.rpsg.rpg.game.achievement.specific;

import com.rpsg.rpg.game.achievement.BaseAchievement;
import com.rpsg.rpg.object.base.Global;

public class Achievement001 extends BaseAchievement {//暂时性使用，将会废弃
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int number = 1;
	public static final String title = "十万富翁";
	public static final String content = "角色的金钱达到10W";

	public static final int type = 1;

	public static int status = 0;
	
	public static String compare = "com.rpsg.rpg.object.base.Global.gold";
	
	public void judge(Global g) {
		if (g.gold >= 100000 && status == 0) {
			status = 1;
			deal(g);
		}
	}

	public void deal(Global g) {
		if (Achievement001.status == 1) {
			g.gold += 10000;
			super.display();
		}
	}

	public void setStatus(int status,Global g) {
		Achievement001.status = status;
		this.judge(g);
	}
	
	public void display(){ //显示达成成就的动画
		System.out.println("达成成就"+Achievement001.number);	
	}
	
}
