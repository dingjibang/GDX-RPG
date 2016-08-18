package com.rpsg.rpg.object.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.NativeJavaObject;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.ScriptableObject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.BattleParam;
import com.rpsg.rpg.object.base.FGType;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.base.Weather;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.rpg.PublicNPC;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FG;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.display.SelectUtil;
import com.rpsg.rpg.utils.game.Base;
import com.rpsg.rpg.utils.game.GameDate;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Heros;
import com.rpsg.rpg.utils.game.Move;
import com.rpsg.rpg.utils.game.Timer;
import com.rpsg.rpg.view.GameViews;

public class Script extends Thread {

	public NPC npc;
	public CollideType callType;
	public String script = "";

	public Script generate(NPC npc, CollideType type, String script) {
		setName(npc.toString() + (npc instanceof PublicNPC ? ":(ID:" + ((PublicNPC) npc).getId() : "") + " (collide: "
				+ type + ")),ThreadId:" + getId());
		this.npc = npc;
		this.callType = type;
		this.script = script;
		return this;
	}

	@Override
	public void run() {
		try {
			Context ctx = Context.enter();
			if (!GameUtil.isDesktop)
				ctx.setOptimizationLevel(-1);
			ScriptableObject scope = ctx.initStandardObjects();
			scope.setPrototype(((NativeJavaObject) Context.javaToJS(Script.this, scope)));
			scope.put("Hero", scope, Context.javaToJS(RPG.ctrl.hero.getHeadHero(), scope));

			if (script.contains("////import")) {
				String s = script.substring(script.indexOf("////import"));
				s = s.substring(0, s.indexOf("/r/n"));
				String s1 = s.substring(s.indexOf("t") + 1).replaceAll(" ", "");
				if (!s1.contains("."))
					s1 += ".js";
				String sc = Gdx.files.internal(Setting.SCRIPT_MAP + s1).readString("utf-8");				
				script.replace(s, sc);
			}
			ctx.evaluateString(scope, script, null, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	public String load(String fileName) {
		return Gdx.files.internal(Setting.SCRIPT_MAP + fileName).readString("utf-8");
	}

	public void print(Object o) {
		System.out.println(o);
	}

	public void end() {
		try {
			Context.exit();
			currentExeced = exeMode.stop;
			dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void end(boolean dispose) {
		try {
			Context.exit();
			this.run();
			if (dispose)
				dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void hold() {
		while (currentExeced != exeMode.stop) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void holdSync() {
		int frame = GameViews.gameview.frame;
		while (frame == GameViews.gameview.frame)
			;
	}

	public boolean isAlive = false;

	public void dispose() {
		this.isAlive = false;
	}

	public BaseScriptExecutor currentScript;

	public int point = -1;
	public exeMode currentExeced = exeMode.first;

	public static enum exeMode {
		first, running, stop
	}

	public void setRenderAble(boolean r) {
		GameViews.gameview.renderable = r;
	}

	public void step() {
		if (currentExeced == exeMode.first && currentScript != null)
			if (currentScript instanceof ScriptExecutor) {
				((ScriptExecutor) currentScript).toInit();
			} else {
				currentScript.init();
				currentExeced = exeMode.stop;
			}
		if (currentScript instanceof ScriptExecutor && currentExeced == exeMode.running)
			((ScriptExecutor) currentScript).step();
	}

	public NPC getNPC(String s) {
		NPC npc = null;
		try {
			for (int i = 0; i < GameViews.gameview.stage.getActors().size; i++) {
				Actor _npc = GameViews.gameview.stage.getActors().get(i);
				if (_npc != null && _npc instanceof PublicNPC && ((PublicNPC) _npc).getId().equals(s))
					npc = (PublicNPC) _npc;
			}
			return npc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return npc;
	}

	/**
	 * 插入一个新的执行器到堆栈的最后一位
	 */
	public BaseScriptExecutor set(BaseScriptExecutor exe) {
		currentExeced = exeMode.first;
		currentScript = exe;
		hold();
		return exe;
	}

	/**
	 * 在屏幕上打印出一句话
	 * 
	 * @param str
	 *            要说的话
	 * @param title
	 *            要说话的人
	 * @param size
	 *            文本字号
	 * @return
	 */
	public BaseScriptExecutor say(String str, String title, int size) {
		return RPG.ctrl.msg.say(this, str, title, size);
	}

	/**
	 * 在屏幕上打印出一句话
	 * 
	 * @param str
	 *            要说的话
	 * @param title
	 *            要说话的人
	 * @return
	 */
	public BaseScriptExecutor say(String str, String title) {
		return RPG.ctrl.msg.say(this, str, title, 22);
	}

	/**
	 * 包装一个脚本执行器
	 * 
	 * @param exe
	 *            执行器
	 * @return
	 */
	public BaseScriptExecutor and(final BaseScriptExecutor exe) {
		if (exe instanceof ScriptExecutor)
			set(new ScriptExecutor(this) {
				ScriptExecutor proxy = (ScriptExecutor) exe;

				public void init() {
					proxy.init();
				}

				public void step() {
					if (proxy.script.currentExeced == exeMode.running)
						proxy.step();
					else
						dispose();
				}
			});
		else
			set(new BaseScriptExecutor() {
				public void init() {
					exe.init();
				}
			});
		return exe;
	}

	/**
	 * 显示/隐藏菜单
	 * 
	 * @param flag
	 *            是否显示
	 * @return
	 */
	public BaseScriptExecutor showMenu(boolean flag) {
		return PostUtil.showMenu(this, flag);
	}

	/**
	 * 在屏幕上打印出一句话
	 * 
	 * @param str
	 *            要说的话
	 * @return
	 */
	public BaseScriptExecutor say(String str) {
		return RPG.ctrl.msg.say(this, str, "", 22);
	}

	/**
	 * 锁定玩家键盘，此时玩家只能按z键对话。
	 * 
	 * @param b
	 *            是否锁定
	 * @return
	 */
	public BaseScriptExecutor setKeyLocker(boolean b) {
		return RPG.ctrl.msg.setKeyLocker(this, b);
	}

	/**
	 * 锁定当前NPC，禁止其转向或移动
	 * 
	 * @param b
	 *            是否锁定
	 * @return
	 */
	public BaseScriptExecutor lock(boolean b) {
		return Move.lock(this, b);
	}

	/**
	 * 让当前NPC面向玩家
	 * 
	 * @return
	 */
	public BaseScriptExecutor faceToHero() {
		return Move.faceToHero(this);
	}

	/**
	 * 让当前NPC面向一个方向
	 * 
	 * @param face
	 *            {@link IRPGObject.FACE_?} 要面向的方向
	 * @return
	 */
	public BaseScriptExecutor faceTo(int face) {
		return Move.turn(this, face);
	}

	public void faceTo(NPC npc, int face) {
		npc.turn(face);
	}

	/**
	 * 让当前的NPC移动
	 * 
	 * @param step
	 *            移动多少步
	 * @return
	 */
	public BaseScriptExecutor move(int step) {
		return Move.move(this, step);
	}

	public void move(NPC npc, int step) {
		npc.walk(step).testWalk();
	}

	/**
	 * 当前脚本暂停
	 * 
	 * @param frame
	 *            暂停的毫秒数
	 * @return
	 */
	public BaseScriptExecutor pause(int frame) {
		return Timer.wait(this, frame);
	}

	/**
	 * 立即移除当前脚本自身
	 * 
	 * @return
	 */
	public BaseScriptExecutor removeSelf() {
		return Base.removeSelf(this);
	}

	public GdxQuery $(Object... o) {
		return new GdxQuery(o);
	}

	/**
	 * 播放音乐
	 * 
	 * @return
	 */
	public BaseScriptExecutor playMusic(String musicName) {
		return Music.playMusic(this, musicName);
	}

	/**
	 * 播放音效
	 * 
	 * @return
	 */
	public BaseScriptExecutor playSE(String musicName) {
		return Music.playSE(this, musicName);
	}

	public BaseScriptExecutor playSE(String musicName, boolean loop) {
		return Music.playSE(this, musicName, loop);
	}

	/**
	 * 立即移除当前脚本自身并换为另一个脚本
	 * 
	 * @param script
	 *            脚本的class类型
	 * @return
	 */
	public BaseScriptExecutor changeSelf(String script) {
		return Base.changeSelf(this, script);
	}

	/**
	 * 显示对话框
	 * 
	 * @param {@link
	 * 			MsgType} 要显示什么样的对话框
	 * @return
	 */
	public BaseScriptExecutor showMSG(MsgType msgType) {
		return RPG.ctrl.msg.show(this, msgType.path());
	}

	/**
	 * 停止所有SE播放
	 */
	public BaseScriptExecutor stopAllSE(float time) {
		return Music.stopAllSE(this, time);
	}

	public BaseScriptExecutor stopAllSE(float time, String without) {
		return Music.stopAllSE(this, time, without);
	}

	public BaseScriptExecutor setSEVolume(float time, float volume) {
		return Music.setSEVolume(this, volume, time);
	}

	/**
	 * 显示默认的对话框
	 * 
	 * @return
	 */
	public BaseScriptExecutor showMSG() {
		return RPG.ctrl.msg.show(this, MsgType.正常.path());
	}

	/**
	 * 隐藏对话框
	 * 
	 * @return
	 */
	public BaseScriptExecutor hideMSG() {
		return RPG.ctrl.msg.hide(this);
	}

	/**
	 * 隐藏立绘
	 * 
	 * @param position
	 *            左侧的立绘还是右侧的立绘 {@link FG}
	 * @return
	 */
	public BaseScriptExecutor hideFG(int position) {
		return RPG.ctrl.fg.hide(this, position);
	}

	/**
	 * 隐藏全部当前屏幕上的立绘
	 * 
	 * @return
	 */
	public BaseScriptExecutor hideFG() {
		return RPG.ctrl.fg.hideAll(this);
	}

	/**
	 * 在屏幕左侧显示一张立绘
	 * 
	 * @param people
	 *            要显示谁的立绘
	 * @param {@link
	 * 			FGType} 立绘的类型
	 * @return
	 */
	public BaseScriptExecutor showFGLeft(FGType people, FGType look) {
		return RPG.ctrl.fg.show(this, Setting.IMAGE_FG + people.value() + "/" + look.value() + ".png", FG.LEFT);
	}

	/**
	 * 在屏幕右侧显示一张立绘
	 * 
	 * @param people
	 *            要显示谁的立绘
	 * @param {@link
	 * 			FGType} 立绘的类型
	 * @return
	 */
	public BaseScriptExecutor showFGRight(FGType people, FGType look) {
		return RPG.ctrl.fg.show(this, Setting.IMAGE_FG + people.value() + "/" + look.value() + ".png", FG.RIGHT);
	}

	/**
	 * 将当前英雄队列的某个人和第一个人（head）交换
	 * 
	 * @param position
	 *            要交换的英雄在队列里的位置
	 * @return
	 */
	public BaseScriptExecutor swapHeroQueue(int position) {
		return Heros.swapHeroQueue(this, position);
	}

	/**
	 * 将当前英雄队列的某个人和另一个人交换
	 * 
	 * @param position
	 *            小张
	 * @param position2
	 *            小王
	 * @return
	 */
	public BaseScriptExecutor swapHeroQueue(int position, int position2) {
		return Heros.swapHeroQueue(this, position, position2);
	}

	/**
	 * 设置当前游戏的时间
	 * 
	 * @param {@link
	 * 			GameDate.Time} time 游戏时间（上午？下午
	 * @return
	 */
	public BaseScriptExecutor setGameTime(GameDate.Time time) {
		return ColorUtil.set(this, time);
	}

	/**
	 * 显示选择框，可以让玩家进行游戏选项
	 * 
	 * @param args
	 *            要选择的内容
	 * @return
	 */
	public BaseScriptExecutor select(String... args) {
		return GameViews.selectUtil.select(this, args);
	}

	/**
	 * 当前玩家选择的内容
	 * 
	 * @return
	 */
	public String currentSelect() {
		return SelectUtil.currentSelect;
	}

	/**
	 * 设置天气
	 * 
	 * @param type
	 *            天气的类型，请查询enum#Weather
	 * @return
	 */
	public BaseScriptExecutor setWeather(Weather type) {
		return RPG.ctrl.weather.setWeather(this, type);
	}

	/**
	 * 移动相机，偏移值根据HERO的当前位置，x和y可以为负数，这样就是反方向移动了qwq
	 * 
	 * @param x
	 *            往x方向移动多少
	 * @param y
	 *            往y方向移动多少
	 * @param wait
	 *            是否等待移动完毕才结束当前执行器www
	 * @return
	 */
	public BaseScriptExecutor setCameraPositionWithHero(final int x, final int y, final boolean wait) {
		return MoveController.setCameraPositionWithHero(this, x, y, wait);
	}

	/**
	 * 等待相机移动完毕
	 * 
	 * @return
	 */
	public BaseScriptExecutor waitCameraMove() {
		return MoveController.waitCameraMove(this);
	}

	/**
	 * 移动相机，根据地图的左下角(0,0)为偏移
	 * 
	 * @param x
	 *            往x方向移动多少
	 * @param y
	 *            往y方向移动多少
	 * @param wait
	 *            是否等待移动完毕才结束当前执行器www
	 * @return
	 */
	public BaseScriptExecutor setCameraPositionWithAbsolute(final int x, final int y, final boolean wait) {
		return MoveController.setCameraPositionWithAbsolute(this, x, y, wait);
	}

	/**
	 * 当前玩家选择的是否是某个字符串
	 * 
	 * @param equ
	 *            字符串
	 * @return
	 */
	public boolean currentSelect(String equ) {
		return SelectUtil.currentSelect.equals(equ);
	}

	/**
	 * 给当前某个Hero显示一个心情气球
	 * 
	 * @param c
	 *            Hero类型
	 * @param type
	 *            气球类型
	 * @return
	 */
	public void setBalloon(int hero, BalloonType type) {
		RPG.ctrl.hero.getHero(hero).setBalloon(type);
	}

	/**
	 * 触发战斗
	 */
	public void battle(NativeObject param) {
		RPG.ctrl.battle.start(RPG.jsToJava(BattleParam.class, param));
	}

	/**
	 * 给当前NPC显示一个心情气球
	 * 
	 * @param type
	 *            气球类型
	 * @return
	 */
	public BaseScriptExecutor setBalloon(BalloonType type) {
		return Heros.balloon(this, type);
	}

	public void setBalloon(NPC who, BalloonType type) {
		who.setBalloon(type);
	}

	public BaseScriptExecutor teleport(String map, int x, int y, int z) {
		if (!map.endsWith(".tmx"))
			map += ".tmx";
		return Move.teleportAnotherMap(this, map, x, y, z);
	}

	public void toggleAllHero(boolean v) {
		for (Hero h : RPG.ctrl.hero.currentHeros)
			h.setVisible(v);
	}

}
