package com.rpsg.rpg.game.achievement.specific;

import com.rpsg.rpg.game.achievement.BaseAchievement;
import com.rpsg.rpg.object.base.Global;

public class Achievement002 extends BaseAchievement {
	public static final int number = 2;
	public static final String title = "百万富翁";
	public static final String content = "角色的金钱达到100W";

	public static final int type = 1;

	public static int status = 0;
	
	public static String compare = "com.rpsg.rpg.object.base.Global.gold";
	
	public void judge(Global g) {
		if (g.gold >= 1000000 && status == 0) {
			status = 1;
			deal(g);			
		}
	}

	public void deal(Global g) {
		if (Achievement001.status == 1) {
			g.gold += 100000;		
			super.display();
		}
	}

	public void setStatus(int status,Global g) {
		Achievement001.status = status;
		this.judge(g);
	}

}
