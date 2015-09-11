package com.rpsg.rpg.utils.game;

public class Point {

	public int x;
	public int y;
	public Point parentPoint;// 父节点
	public int g;// 路径耗费
	public int h;// 预估耗费
	public int f;// f=g+h

	public Point() {
	}

	public Point(int x, int y, Point parentPoint) {
		this.x = x;
		this.y = y;
		this.parentPoint = parentPoint;
	}

	public int compare(Point p1, Point p2) {
		return (p1.f - p1.f);
	}
}
