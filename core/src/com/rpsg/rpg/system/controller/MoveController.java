package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Collide;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.IRPGObject;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.view.GameView;

public class MoveController {

	public static int MAP_MAX_OUT_X = 512;
	public static int MAP_MAX_OUT_Y = 288;
	static boolean wu = false, wd = false, wl = false, wr = false;

	public static void up() {
		wu = true;
		wd = wl = wr = false;
	}

	public static void down() {
		wd = true;
		wu = wl = wr = false;
	}

	public static void left() {
		wl = true;
		wd = wu = wr = false;
	}

	public static void right() {
		wr = true;
		wd = wu = wl = false;
	}

	static int xoff, yoff, bufx, bufy;

	static final int ACCELERATION = 1;// 绝对值表示
	static final int MAXSPEED = 10;// 绝对值表示
	static int MAXSPEEDX = 15;
	static int MAXSPEEDY = 15;
	static int speedx = 0;
	static int speedy = 0;
	static int tempmaxspeedx = MAXSPEED; // 绝对值表示
	static int tempmaxspeedy = MAXSPEED;

	public static void logic(GameView gv) {
		for (Actor a : gv.stage.getActors()) {
			if (a instanceof IRPGObject && ((IRPGObject) a).enableCollide) {
				IRPGObject o = (IRPGObject) a;
				o.collide.testCollide(o.mapx, o.mapy, ((TiledMapTileLayer) MapController.layer.get(o.layer)), gv.stage.getActors(), o);
			}
		}
		for (ScriptCollide sc : Collide.testNPCCollide(gv, HeroController.getHeadHero(), gv.stage.getActors())) {
			sc.toCollide();
		}
		HeroController.setWalkSpeed(Input.isPress(Keys.CONTROL_LEFT) ? 8 : 4);
		if (InputController.currentIOMode == IOMode.MAP_INPUT_NORMAL && HoverController.isEmpty()) {
			if ((Input.isPress(Keys.RIGHT) || Input.isPress(Keys.D) || wr) && HeroController.walked()) {
				wr = false;
				HeroController.turn(Hero.FACE_R);
				HeroController.walk(1);
				HeroController.testWalk();
			}
			if ((Input.isPress(Keys.LEFT) || Input.isPress(Keys.A) || wl) && HeroController.walked()) {
				wl = false;
				HeroController.turn(Hero.FACE_L);
				HeroController.walk(1);
				HeroController.testWalk();
			}
			if ((Input.isPress(Keys.UP) || Input.isPress(Keys.W) || wu) && HeroController.walked()) {
				wu = false;
				HeroController.turn(Hero.FACE_U);
				HeroController.walk(1);
				HeroController.testWalk();
			}
			if ((Input.isPress(Keys.DOWN) || Input.isPress(Keys.S) || wd) && HeroController.walked()) {
				wd = false;
				HeroController.turn(Hero.FACE_D);
				HeroController.walk(1);
				HeroController.testWalk();
			}
		}
		// core
		// twidth theight 是地图的总宽度和告诉
		int twidth = MapController.mapWidth;
		int theight = MapController.mapHeight;

		// 这两个坐标herox heroy 来确定了hero的位置
		float herox = HeroController.getHeadHero().position.x + (HeroController.getHeadHero().getWidth() / 2);
		float heroy = HeroController.getHeadHero().position.y + (HeroController.getHeadHero().getHeight() / 2);

		if (herox > MAP_MAX_OUT_X && herox < (twidth) - MAP_MAX_OUT_X)// 如果角色没有到达地图的x边界，那么相机的x中央点就设定为hero的x位置
			gv.camera.position.x = herox;
		else if (!(herox > MAP_MAX_OUT_X))//
			gv.camera.position.x = MAP_MAX_OUT_X;
		else
			gv.camera.position.x = (twidth) - MAP_MAX_OUT_X;
		if (heroy > MAP_MAX_OUT_Y && heroy < (theight) - MAP_MAX_OUT_Y)// 同理，设定到y
			gv.camera.position.y = heroy;
		else if (!(heroy > MAP_MAX_OUT_Y))
			gv.camera.position.y = MAP_MAX_OUT_Y;
		else
			gv.camera.position.y = (theight) - MAP_MAX_OUT_Y;

		Vector3 pos = gv.camera.position;

		int SX = (ACCELERATION + MAXSPEEDX) * MAXSPEEDX / ACCELERATION / 2 - MAXSPEEDX;
		int SY = (ACCELERATION + MAXSPEEDY) * MAXSPEEDY / ACCELERATION / 2 - MAXSPEEDY;
		if (xoff != 0) {
			if (tempmaxspeedx != MAXSPEED) {
				if (speedx < tempmaxspeedx * (xoff > bufx ? 1 : -1)) {
					speedx += ACCELERATION * (speedx < 0 ? -1 : 1);
				} else if (Math.abs((ACCELERATION + speedx) * speedx / ACCELERATION / 2 - speedx) >= Math.abs(xoff - bufx)) {
					speedx -= ACCELERATION * (speedx < 0 ? -1 : 1);
				}
			} else {
				if ((Math.abs(speedx) < MAXSPEEDX && Math.abs(xoff - bufx) > SX) || (speedx * (xoff - bufx) < 0)) {
					speedx += ACCELERATION * (bufx > xoff ? -1 : 1);
				} else if (Math.abs(xoff - bufx) <= SX) {
					speedx -= ACCELERATION * (speedx < 0 ? -1 : 1);// 减速部分有待优化
				}
			}
		}
		if (yoff != 0) {
			if (tempmaxspeedy != MAXSPEED) {
				if (speedy < tempmaxspeedy * (yoff > bufy ? 1 : -1)) {
					speedy += ACCELERATION * (speedy < 0 ? -1 : 1);
				} else if (Math.abs((ACCELERATION + speedy) * speedy / ACCELERATION / 2 - speedy) >= Math.abs(yoff - bufy)) {
					speedy -= ACCELERATION * (speedy < 0 ? -1 : 1);
				}
			} else {
				if ((Math.abs(speedy) < MAXSPEEDY && Math.abs(yoff - bufy) > SY) || (speedy * (yoff - bufy) < 0)) {
					// System.out.print("Y           ");
					speedy += ACCELERATION * (bufy > yoff ? -1 : 1);
				} else if (Math.abs(yoff - bufy) <= SY) {
					speedy -= ACCELERATION * (speedy < 0 ? -1 : 1);
				}
			}
		}
		if ((bufx > xoff) ^ ((bufx + speedx) > xoff)) {
			bufx = xoff;
		}
		if ((bufy > yoff) ^ ((bufy + speedy) > yoff)) {
			bufy = yoff;
		}
		if (bufx == xoff) {
			speedx = 0;
		}
		if (bufy == yoff) {
			speedy = 0;

		}
		bufx += speedx;
		bufy += speedy;
		// System.out.println("x:" + xoff + ",y:" + yoff + ",bx:" + bufx + "by:"
		// + bufy + "spx" + speedx + "spy" + speedy);
		pos.x += bufx;
		pos.y += bufy;
		gv.camera.update();
	}

	public static void setCameraPosition(int x, int y) {
		tempmaxspeedx = MAXSPEED;
		tempmaxspeedy = MAXSPEED;
		xoff = x;
		yoff = y;
		tempmaxspeedx = (int) Math.sqrt(Math.abs(x - bufx) * ACCELERATION + speedx * speedx / 2);
		tempmaxspeedy = (int) Math.sqrt(Math.abs(y - bufy) * ACCELERATION + speedy * speedy / 2);
		if (tempmaxspeedx >= MAXSPEED) {
			tempmaxspeedx = MAXSPEED;
		}

		if (tempmaxspeedy >= MAXSPEED) {
			tempmaxspeedy = MAXSPEED;
		}

		/*
		 * if (Math.abs(x - bufx) < Math.abs(y - bufy)) { MAXSPEEDX = MAXSPEED;
		 * MAXSPEEDY = MAXSPEED Math.abs(y - bufx) / (Math.max((MAXSPEED *
		 * MAXSPEED / ACCELERATION), Math.abs(x - bufy))); } if (Math.abs(x -
		 * bufx) > Math.abs(y - bufy)) { MAXSPEEDY = MAXSPEED; MAXSPEEDX =
		 * MAXSPEED Math.abs(x - bufx) / (Math.max((MAXSPEED * MAXSPEED /
		 * ACCELERATION), Math.abs(y - bufy))); }
		 */
		MAXSPEEDX = MAXSPEEDY = MAXSPEED;
		// System.out.println(MAXSPEEDX);
		// System.out.println(MAXSPEEDY);
	}

	public static boolean isCameraMoving() {
		return speedx != 0 && speedy != 0 && xoff == bufx && yoff == bufy;
	}

	public static boolean testCameraPos(GameView gv) {
		float herox = HeroController.getHeadHero().position.x + (HeroController.getHeadHero().getWidth() / 2);
		float heroy = HeroController.getHeadHero().position.y + (HeroController.getHeadHero().getHeight() / 2);
		return !(herox > MAP_MAX_OUT_X && herox < (48 * 48) - MAP_MAX_OUT_X) && (heroy > MAP_MAX_OUT_Y && heroy < (48 * 48) - MAP_MAX_OUT_Y);
	}

	public static void keyUp(int keycode, GameView gv) {
	}

	public static void keyDown(int keycode, GameView gv) {
	}

	public static BaseScriptExecutor setCameraPositionWithHero(Script script, final int x, final int y, final boolean wait) {
		return new ScriptExecutor(script) {
			public void init() {
				MoveController.setCameraPosition(x, y);
			}

			public void step() {
				if (!wait || (wait && !MoveController.isCameraMoving()))
					dispose();
			}
		};
	}

	public static BaseScriptExecutor waitCameraMove(Script script) {
		return new ScriptExecutor(script) {
			public void init() {
			}

			public void step() {
				if (MoveController.isCameraMoving())
					dispose();
			}
		};
	}

	/**
	 * X/Y BASE ( LEFT/BOTTOM )
	 */
	public static BaseScriptExecutor setCameraPositionWithAbsolute(Script script, final int x, final int y, final boolean wait) {
		int herox = (int) (HeroController.getHeadHero().position.x + (HeroController.getHeadHero().getWidth() / 2));
		int heroy = (int) (HeroController.getHeadHero().position.y + (HeroController.getHeadHero().getHeight() / 2));
		return setCameraPositionWithHero(script, herox - x, heroy - y, wait);
	}

}
