package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.RemoveTest;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Collide;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.RPGObject;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptCollide;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameView;

public class MoveController {

	public static int MAP_MAX_OUT_X = 512;
	public static int MAP_MAX_OUT_Y = 288;
	static boolean wu = false, wd = false, wl = false, wr = false;
	public static Actor offsetActor=new Actor(),bufferActor=new Actor();

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

	public static void logic(GameView gv) {
		for (Actor a : gv.stage.getActors()) {
			if (a instanceof RPGObject && ((RPGObject) a).enableCollide) {
				RPGObject o = (RPGObject) a;
				o.collide.testCollide(o.mapx, o.mapy, ((TiledMapTileLayer) RPG.maps.loader.layer.get(o.layer)), gv.stage.getActors(), o);
			}
		}
		for (ScriptCollide sc : Collide.testNPCCollide(gv, RPG.ctrl.hero.getHeadHero(), gv.stage.getActors())) {
			sc.toCollide();
		}
		RPG.ctrl.hero.setWalkSpeed(Input.isPress(Keys.CONTROL_LEFT) ? 8 : 4);
		if (InputController.currentIOMode == IOMode.MapInput.NORMAL && RPG.hover.isEmpty()) {
			if ((Input.isPress(Keys.RIGHT) || Input.isPress(Keys.D) || wr) && RPG.ctrl.hero.walked()) {
				wr = false;
				RPG.ctrl.hero.turn(Hero.FACE_R);
				RPG.ctrl.hero.walk(1);
				RPG.ctrl.hero.testWalk();
			}
			if ((Input.isPress(Keys.LEFT) || Input.isPress(Keys.A) || wl) && RPG.ctrl.hero.walked()) {
				wl = false;
				RPG.ctrl.hero.turn(Hero.FACE_L);
				RPG.ctrl.hero.walk(1);
				RPG.ctrl.hero.testWalk();
			}
			if ((Input.isPress(Keys.UP) || Input.isPress(Keys.W) || wu) && RPG.ctrl.hero.walked()) {
				wu = false;
				RPG.ctrl.hero.turn(Hero.FACE_U);
				RPG.ctrl.hero.walk(1);
				RPG.ctrl.hero.testWalk();
			}
			if ((Input.isPress(Keys.DOWN) || Input.isPress(Keys.S) || wd) && RPG.ctrl.hero.walked()) {
				wd = false;
				RPG.ctrl.hero.turn(Hero.FACE_D);
				RPG.ctrl.hero.walk(1);
				RPG.ctrl.hero.testWalk();
			}
		}
		// core
		// twidth theight 是地图的总宽度和高度
		int twidth = RPG.maps.loader.mapWidth;
		int theight = RPG.maps.loader.mapHeight;

		// 这两个坐标herox heroy 来确定了hero的位置
		float herox = RPG.ctrl.hero.getHeadHero().position.x + (RPG.ctrl.hero.getHeadHero().getWidth() / 2);
		float heroy = RPG.ctrl.hero.getHeadHero().position.y + (RPG.ctrl.hero.getHeadHero().getHeight() / 2);
		
		
		Vector3 pos = Setting.persistence.softCamera?new Vector3():gv.camera.position;
		
		if(GameUtil.screen_width<twidth){
			if (herox > MAP_MAX_OUT_X && herox < (twidth) - MAP_MAX_OUT_X)// 如果角色没有到达地图的x边界，那么相机的x中央点就设定为hero的x位置
				pos.x = herox;
			else if (!(herox > MAP_MAX_OUT_X))//
				pos.x = MAP_MAX_OUT_X;
			else
				pos.x = (twidth) - MAP_MAX_OUT_X;
		}else{
			pos.x=twidth/2;
		}
		if(GameUtil.screen_height<theight){
			if (heroy > MAP_MAX_OUT_Y && heroy < (theight) - MAP_MAX_OUT_Y)// 同理，设定到y
				pos.y = heroy;
			else if (!(heroy > MAP_MAX_OUT_Y))
				pos.y = MAP_MAX_OUT_Y;
			else
				pos.y = (theight) - MAP_MAX_OUT_Y;
		}else{
			pos.y=theight/2;
		}

		offsetActor.act(Gdx.graphics.getDeltaTime());
		if(Setting.persistence.softCamera){
//			System.out.println(offsetActor.getX());
			bufferActor.clearActions();
			if(!(RPG.ctrl.hero.thisFrameGeneratedPosition?!(RPG.ctrl.hero.thisFrameGeneratedPosition=false):false))
				bufferActor.addAction(Actions.moveTo(pos.x, pos.y,0.5f,Interpolation.pow2Out));
			else
				bufferActor.addAction(Actions.moveTo(pos.x, pos.y));
			bufferActor.act(Gdx.graphics.getDeltaTime());
			gv.camera.position.set((int)bufferActor.getX()+offsetActor.getX(),(int)bufferActor.getY()+offsetActor.getY(),0);
		}else{
			pos.x += offsetActor.getX();
			pos.y += offsetActor.getY();
		}
		gv.camera.zoom=offsetActor.getScaleX();
		gv.camera.update();
	}

	public static void setCameraPosition(int x, int y) {
		$.removeIf(offsetActor.getActions(), new RemoveTest<Action>() {
			public boolean test(Action object) {
				return object instanceof MoveToAction;
			}
		});
		offsetActor.addAction(Actions.moveTo(x, y,0.8f,Interpolation.pow4Out));
	}

	public static boolean isCameraMoving() {
		return offsetActor.getActions().size!=0;
	}

	public static boolean testCameraPos(GameView gv) {
		float herox = RPG.ctrl.hero.getHeadHero().position.x + (RPG.ctrl.hero.getHeadHero().getWidth() / 2);
		float heroy = RPG.ctrl.hero.getHeadHero().position.y + (RPG.ctrl.hero.getHeadHero().getHeight() / 2);
		return !(herox > MAP_MAX_OUT_X && herox < (48 * 48) - MAP_MAX_OUT_X) && (heroy > MAP_MAX_OUT_Y && heroy < (48 * 48) - MAP_MAX_OUT_Y);
	}

	public static void keyUp(int keycode, GameView gv) {
	}

	public static void keyDown(int keycode, GameView gv) {
	}

	public static BaseScriptExecutor setCameraPositionWithHero(Script script, final int x, final int y, final boolean wait) {
		return script.$(new ScriptExecutor(script) {
			public void init() {
				MoveController.setCameraPosition(x, y);
			}

			public void step() {
				if (!wait || (wait && !MoveController.isCameraMoving()))
					dispose();
			}
		});
	}

	public static BaseScriptExecutor waitCameraMove(Script script) {
		return script.$(new ScriptExecutor(script) {
			public void init() {
			}

			public void step() {
				if (MoveController.isCameraMoving())
					dispose();
			}
		});
	}

	/**
	 * X/Y BASE ( LEFT/BOTTOM )
	 */
	public static BaseScriptExecutor setCameraPositionWithAbsolute(Script script, final int x, final int y, final boolean wait) {
		int herox = (int) (RPG.ctrl.hero.getHeadHero().position.x + (RPG.ctrl.hero.getHeadHero().getWidth() / 2));
		int heroy = (int) (RPG.ctrl.hero.getHeadHero().position.y + (RPG.ctrl.hero.getHeadHero().getHeight() / 2));
		return setCameraPositionWithHero(script, herox - x, heroy - y, wait);
	}

}
