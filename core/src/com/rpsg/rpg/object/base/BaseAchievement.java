package com.rpsg.rpg.object.base;

import java.io.Serializable;
import java.lang.reflect.Field;

import com.rpsg.rpg.utils.game.AchievementManager;
import com.rpsg.rpg.view.GameViews;

public class BaseAchievement implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int number; // 编号
	public String title;// 标题
	public String content;// 内容
	public int type; // 0 全局判定，1 局部判定，2 手动判定
						// 0 主要用于和金钱，物品数量，角色等级等随时可能变化的数据相关的判断
						// 1 主要用于战斗相关的判断：角色状态，回合数，伤害值等
						// 2 主要用于游戏进程中可以明确确定其判断时间节点及结果的情况：完成某个结局，任务等
						// 可能会添加，和计数有关的判断
	public int status;// -1 失败或未判定，0待判定，1成功 全局判断和手动判断时，该初始值为0，其它情况，初始值为-1
	public String compare;// 参与判断的变量
	public String judgeString;
	public String dealString;

	// 缺少内部计数器

	public BaseAchievement() {
	}

	public BaseAchievement(String number, String title, String content, String type, String status, String compare, String judgeString, String dealString) {
		this.number = Integer.parseInt(number);
		this.title = title;
		this.content = content;
		this.type = Integer.parseInt(type);
		this.status = Integer.parseInt(status);
		this.compare = compare;
		this.judgeString = judgeString;
		this.dealString = dealString;
	}

	public void judge() {
		if (this.status == 1) {
			this.deal();
			return;
		}
		String[] ss = judgeString.split(",");
		for (String s : ss) {// 即将废弃
			// 匹配运算符 >= <= == !=
			int temp = 1;
			// boolean flag = false;
			int copnum = 0;
			if (s.contains("=")) {
				if (s.contains("==")) {
					temp = 0;
				}

				String field = s.substring(0, s.indexOf('=') - temp);
				try {
					Field f = GameViews.global.getClass().getField(field); // 获取参与判定的参数的对应的值
					copnum = (int) f.getInt(GameViews.global); // 因为参与数值比较，所以默认为整形(global中所有的数值变量都不可以使用包装类)
				} catch (Exception e) {
					e.printStackTrace();
				}
				int num = Integer.parseInt(s.substring(s.indexOf('=') + 2 - temp));
				switch (s.substring(s.indexOf('=') - temp, s.indexOf('=') + 2 - temp)) {

				case ">=":
					// flag = (copnum >= num); break;
					if (copnum < num)
						return;
					break;
				case "<=":
					if (copnum > num)
						return;
					break;
				case "==":
					if (copnum != num)
						return;
					break;
				case "!=":
					if (copnum == num)
						return;
					break;
				}
			}
			// if (flag == false){
			// return;
			// }
		}
		this.status = 1;
		this.deal();
	}

	public void deal() {
		if (this.status == 1) {
			GameViews.global.$(this.dealString);
			display();
		}
		AchievementManager.flag = true;
	}

	public void deal(int i) {// i~-1,1 用于手动判断，即某任务成功或失败，直接调用该方法
		this.status = i;
		this.deal();
	}

	public void setStatus(int status) {// 用于局部判定
		this.status = status;
		this.judge();
	}

	public void display() { // 显示达成成就的动画
		System.out.println("达成成就" + this.title);
	}
}
