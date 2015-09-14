package com.rpsg.rpg.utils.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Collide;
import com.rpsg.rpg.object.rpg.RPGObject;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;

public class Path {
	public static boolean flag = true;// 是否开启寻路

	private int[][] map; // 1可通过 0不可通过
	private List<Point> openList;
	private List<Point> closeList;

	private int row;// 行
	private int column;// 列

	public Path(int[][] map, int row, int column) {
		this.map = map;
		this.row = row;
		this.column = column;
		openList = new ArrayList<Point>();
		closeList = new ArrayList<Point>();
	}

	// 查找坐标
	public int search(int x1, int y1, int x2, int y2) {
		if (x1 < 0 || x1 >= row || x2 < 0 || x2 >= row || y1 < 0
				|| y1 >= column || y2 < 0 || y2 >= column) {
			return -1;
		}
		if (map[x1][y1] == 0 || map[x2][y2] == 0) {
			return -1;
		}
		Point spoint = new Point(x1, y1, null);
		Point epoint = new Point(x2, y2, null);
		openList.add(spoint);
		List<Point> resultList = search(spoint, epoint);
		if (resultList.size() == 0) {
			return 0;
		}
		for (Point p : resultList) {
			map[p.x][p.y] = 2;
		}
		return 1;
	}

	// 核心算法
	private List<Point> search(Point sPoint, Point ePoint) {
		List<Point> resultList = new ArrayList<Point>();
		boolean isFind = false;
		Point point = null;
		while (openList.size() > 0) {
			// 取出开启列表中最低F值
			point = openList.get(0);
			if (point.x == ePoint.x && point.y == ePoint.y) {
				isFind = true;
				break;
			}
			// 上
			if ((point.y - 1) >= 0) {
				checkPath(point.x, point.y - 1, point, ePoint, 10);
			}
			// 下
			if ((point.y + 1) < column) {
				checkPath(point.x, point.y + 1, point, ePoint, 10);
			}
			// 左
			if ((point.x - 1) >= 0) {
				checkPath(point.x - 1, point.y, point, ePoint, 10);
			}
			// 右
			if ((point.x + 1) < row) {
				checkPath(point.x + 1, point.y, point, ePoint, 10);
			}
			closeList.add(openList.remove(0));
			Collections.sort(openList, new PointFComparator());
		}
		if (isFind) {
			getPath(resultList, point);
		}
		return resultList;
	}

	// 查询此路是否能走通
	private boolean checkPath(int x, int y, Point parentPoint, Point ePoint,
			int cost) {
		Point point = new Point(x, y, parentPoint);

		if (map[x][y] == 0) {
			closeList.add(point);
			return false;
		}

		if (isListContains(closeList, x, y) != -1) {
			return false;
		}

		int index = -1;
		if ((index = isListContains(openList, x, y)) != -1) {

			if ((parentPoint.g + cost) < openList.get(index).g) {
				point.parentPoint = parentPoint;
				countG(point, ePoint, cost);
				countF(point);
				openList.set(index, point);
			}
		} else {

			point.parentPoint = parentPoint;
			count(point, ePoint, cost);
			openList.add(point);
		}
		return true;
	}

	// 集合中是否包含某个元素
	private int isListContains(List<Point> list, int x, int y) {
		for (int i = 0; i < list.size(); i++) {
			Point point = list.get(i);
			if (point.x == x && point.y == y) {
				return i;
			}
		}
		return -1;
	}

	// 从终点往返回到起点
	private void getPath(List<Point> resultList, Point point) {
		if (point.parentPoint != null) {
			getPath(resultList, point.parentPoint);
		}
		resultList.add(point);
	}

	private void count(Point point, Point ePoint, int cost) {
		countG(point, ePoint, cost);
		countH(point, ePoint);
		countF(point);
	}

	private void countG(Point point, Point eNode, int cost) {
		if (point.parentPoint == null) {
			point.g = cost;
		} else {
			point.g = (point.parentPoint.g + cost);
		}
	}

	private void countH(Point point, Point eNode) {
		point.f = ((Math.abs(point.x - eNode.x) + Math.abs(point.y - eNode.y)) * 10);
	}

	// 计算F值
	private void countF(Point point) {
		point.f = point.g + point.h;
	}

	public static void main(String[] args) {
		int[][] map = new int[][] {// 地图数组
		new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
				new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }

		};
		Path p = new Path(map, 10, 12);
		int flag = p.search(6, 2, 7, 8);
		if (flag == -1) {
			System.out.println("传输数据有误！");
		} else if (flag == 0) {
			System.out.println("没找到！");
		} else {
			for (int x = 0; x < 10; x++) {
				System.out.println();
				for (int y = 0; y < 12; y++) {

					if (map[x][y] == 1) {
						System.out.print("　");
					} else if (map[x][y] == 0) {
						System.out.print("〓");
					} else if (map[x][y] == 2) {// 输出搜索路径
						System.out.print("※");
					}
				}
				System.out.println();
			}
		}
	}

	public static void click(int x, int y) {
		Vector3 pos = Setting.persistence.softCamera ? new Vector3()
				: GameViews.gameview.camera.position;

		int twidth = RPG.maps.loader.mapWidth;
		int theight = RPG.maps.loader.mapHeight;

		float herox = RPG.ctrl.hero.getHeadHero().position.x
				+ (RPG.ctrl.hero.getHeadHero().getWidth() / 2);
		float heroy = RPG.ctrl.hero.getHeadHero().position.y
				+ (RPG.ctrl.hero.getHeadHero().getHeight() / 2);
		if (GameUtil.screen_width < twidth) {
			if (herox > 512 && herox < (twidth) - 512)
				pos.x = herox;
			else if (!(herox > 512))//
				pos.x = 512;
			else
				pos.x = (twidth) - 512;
		} else {
			pos.x = twidth / 2;
		}
		if (GameUtil.screen_height < theight) {
			if (heroy > 288 && heroy < (theight) - 288)
				pos.y = heroy;
			else if (!(heroy > 288))
				pos.y = 288;
			else
				pos.y = (theight) - 288;
		} else {
			pos.y = theight / 2;
		}

		int goalX = (int) Math.ceil((pos.x + (x - 512)) / 48f);
		int goalY = (int) Math.ceil((pos.y - (y - 288)) / 48f);

		System.out.println(goalX);
		System.out.println(goalY);
		// int a = RPG.ctrl.hero.getHeadHero().layer;
		// TiledMapTileLayer tileLayer = (TiledMapTileLayer)
		// RPG.maps.loader.layer
		// .get(a);
		// int height = tileLayer.getHeight();
		// int width = tileLayer.getWidth();
		// int[][] s = new int[height][width];
		// for (int i = 0; i < width; i++) {
		// for (int j = height -1; j >=0; j--) {
		// if(Collide.getID(tileLayer,i,j)==0){
		// s[j][i] = 0;
		// }else{
		// s[j][i] = 1;
		// }
		// }
		// }
		// ;
		// for (int i = 0; i < GameViews.gameview.stage.getActors().size; i++) {
		// Actor actor=GameViews.gameview.stage.getActors().get(i);
		// int ax = (int) Math.ceil((float) actor.getX() / 48f);
		// int ay = (int) Math.ceil((float) actor.getY() / 48f);
		// s[ay-1][ay-1] = 1;
		// }

		// if (layer instanceof TiledMapTileLayer) {
		// TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;
		// System.out.println(Collide.getID(tileLayer,goalX,goalY));
		//
		// }

	}
}
