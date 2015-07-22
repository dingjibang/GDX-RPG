package com.rpsg.rpg.game.achievement;

import java.io.Serializable;

import com.rpsg.rpg.game.achievement.specific.Achievement002;
import com.rpsg.rpg.object.base.Global;

public class BaseAchievement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int number; // 编号
	public static String title;// 标题
	public static String content;// 内容	
	public static int type; // 0 全局判定，1 局部判定，2 手动判定
	public static int status;// -1 失败，0待判定，1成功	
	public static String compare;

	public void judge() {

	}

	public void deal(){
		
	}
	public void setStatus(int status) {
		BaseAchievement.status = status;
		this.judge();
	}
	public void display(){ //显示达成成就的动画
		System.out.println("达成成就"+this.number);		
	}
}
