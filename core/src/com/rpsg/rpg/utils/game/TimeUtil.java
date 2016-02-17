package com.rpsg.rpg.utils.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.rpsg.gdxQuery.$;

public class TimeUtil {
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("s"), format2 = new SimpleDateFormat("hh:mm:ss");

	public String getGameRunningTime() {
		return formatDuring((new Date().getTime() - date.getTime()));
	}

	public static String formatDuring(long mss) {
		String hours = String.valueOf((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		hours = hours.length() <= 1 ? "0" + hours : hours;
		String minutes = String.valueOf((mss % (1000 * 60 * 60)) / (1000 * 60));
		minutes = minutes.length() <= 1 ? "0" + minutes : minutes;
		String seconds = String.valueOf((mss % (1000 * 60)) / 1000);
		seconds = seconds.length() <= 1 ? "0" + seconds : seconds;
		return hours + ":" + minutes + ":" + seconds;
	}

	private static List<Task> list = new ArrayList<Task>();

	private static class Task {
		Runnable run;
		int ms;
		Date date;
	}

	public static void logic() {
		Date now = new Date();
		$.removeIf(list, (task) -> now.getTime() - task.date.getTime() > task.ms, (task) -> task.run.run());
	}

	public static void add(Runnable run, int ms) {
		Task task = new Task();
		task.run = run;
		task.ms = ms;
		task.date = new Date();
		list.add(task);
	}
}
