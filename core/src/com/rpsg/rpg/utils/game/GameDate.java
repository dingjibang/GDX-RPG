package com.rpsg.rpg.utils.game;

import java.io.Serializable;
public class GameDate implements Serializable {

	private static final long serialVersionUID = 1L;

	private Time time;
	private int month=1;
	private int day=1;

	public static enum Time {
		DAY, NIGHT, DUSK
	}

	public void addDay(int count) {
		int status = 1;
		int[] daycount = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334,365};
		int days = daycount[this.month-1] + this.day + count;
		if (days > 365) {
			days -= 365;
			status = 0;
		}
		for (int i = status*(month - 1); i <= 12; i++) {
			if (days <= daycount[i]) {
				this.month = i;
				this.day = days - daycount[i-1];
				break;
			}
		}

	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}
