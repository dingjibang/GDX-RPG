package com.rpsg.rpg.utils.game;

import java.util.Comparator;

public class PointFComparator implements Comparator<Point> {
	@Override
	public int compare(Point p1, Point p2) {
		return p1.f - p2.f;
	}
}
