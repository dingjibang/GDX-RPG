package com.rpsg.rpg.object.script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8Executor;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.FGType;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.base.ParamReturnRunnable;
import com.rpsg.rpg.object.base.ParamRunnable;
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
import com.rpsg.rpg.utils.game.Heros;
import com.rpsg.rpg.utils.game.Move;
import com.rpsg.rpg.utils.game.Timer;
import com.rpsg.rpg.view.GameViews;


public abstract class Script implements MsgType,FGType{
	private int waitTime=0;
	
	public void sleep(int frame){
		waitTime+=frame;
	}
	
	public NPC npc;
	public CollideType callType;
	public Script generate(NPC npc,CollideType type){
		this.npc=npc;
		this.callType=type;
		try {
			start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public void start(V8Object worker, String... s) {
		String script = (String) s[0];
		V8Executor executor = new V8Executor(script, true, "messageHandler") {
			@Override
			protected void setup(V8 runtime) {
				configureWorker(runtime);
			}
		};
		worker.getRutime().registerV8Executor(worker, executor);
		executor.start();
	}

	V8Executor mainExecutor;
	public void start() throws InterruptedException {
		String result=Gdx.files.internal(Setting.SCRIPT_MAP+"test.js").readString();
		mainExecutor = new V8Executor(result) {
			@Override
			protected void setup(V8 runtime) {
				configureWorker(runtime);
			}
		};
		mainExecutor.start();
		
	}
	
	private void register(V8 runtime,String jsMethod,final Object callback,final boolean hold){
		runtime.registerJavaMethod(new JavaCallback() {
			public Object invoke(V8Object receiver, V8Array parameters) {
				if(callback instanceof ParamRunnable){
					((ParamRunnable)callback).run(parameters);
					if(hold){
						currentExeced=exeMode.first;
						hold();
					}else{
						currentExeced=exeMode.stop;
					}
					return null;
				}else{
					currentExeced=exeMode.stop;
					return ((ParamReturnRunnable)callback).run(parameters);
				}
			}
		}, jsMethod);
	}
	
	private void register(V8 runtime,String jsMethod,final Object callback){
		register(runtime, jsMethod, callback,true);
	}
	
	
	
	private void hold(){
		while(currentExeced!=exeMode.stop){
//			System.out.println("t2:running");
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public abstract void init();
	
	public boolean isAlive=false;
	
	public boolean isAlive(){
		return isAlive ;
	}
	
	public void dispose(){
		this.isAlive=false;
	}
	
	public BaseScriptExecutor currentScript;
	
	public int point=-1;
	public exeMode currentExeced=exeMode.first;
	private Class<? extends NPC>[] type;
	
	public static enum exeMode{
		first,running,stop
	}
	
	
	public void run(){
//		System.out.println("maint:"+currentExeced);
		if(waitTime>0){
			waitTime--;
			return;
		}
		if(currentExeced==exeMode.first && currentScript!=null)
			if(currentScript instanceof ScriptExecutor){
				((ScriptExecutor)currentScript).toInit();
			}else{
				currentScript.init();
				currentExeced=exeMode.stop;
			}
		if(currentScript instanceof ScriptExecutor && currentExeced==exeMode.running)
			((ScriptExecutor)currentScript).step();
	}
	
	private void configureWorker(final V8 runtime) {
		register(runtime, "say",new ParamRunnable() {public void run(V8Array param) {
			if(param.length()==1)
				RPG.ctrl.msg.say(Script.this, (String)param.get(0), "", 22);
			else if(param.length()==2)
				RPG.ctrl.msg.say(Script.this, (String)param.get(0), (String)param.get(1), 22);
			else
				RPG.ctrl.msg.say(Script.this, (String)param.get(0), (String)param.get(1), (Integer)param.get(2));
		}});
		register(runtime, "shwoMenu",new ParamRunnable() {public void run(V8Array param) {
			PostUtil.showMenu=param.getBoolean(0);
		}},false);
		register(runtime, "keyLock",new ParamRunnable() {public void run(V8Array param) {
			RPG.ctrl.msg.setKeyLocker(Script.this, param.getBoolean(0));
		}});
		register(runtime, "dispose",new ParamRunnable() {public void run(V8Array param) {
			dispose();
			mainExecutor.shutdown();
			mainExecutor.forceTermination();
			mainExecutor.stop();
		}});
		register(runtime, "lock",new ParamRunnable() {public void run(V8Array param) {
			 Move.lock(Script.this, param.getBoolean(0));
		}});
		register(runtime, "faceToHero",new ParamRunnable() {public void run(V8Array param) {
			 Move.faceToHero(Script.this);
		}});
		register(runtime, "faceTo",new ParamRunnable() {public void run(V8Array param) {
			Move.turn(Script.this, param.getInteger(0));
		}});
		register(runtime, "move",new ParamRunnable() {public void run(V8Array param) {
			Move.move(Script.this, param.getInteger(0));
		}});
		register(runtime, "move",new ParamRunnable() {public void run(V8Array param) {
			Move.move((Script)param.get(0),param.getInteger(0));
		}});
		register(runtime, "wait",new ParamRunnable() {public void run(V8Array param) {
			Timer.wait(Script.this, param.getInteger(0));
		}});
		register(runtime, "findNPC",new ParamReturnRunnable() {public Object run(V8Array param) {
			GdxQuery query =$.add();
			try {
				query.add(GameViews.gameview.stage.getActors().items).find(ClassLoader.getSystemClassLoader().loadClass("com.rpsg.rpg.game.object."+param.get(0)));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return query.isEmpty()?null:((NPC) query.getItem()).script(); 
		}});
		register(runtime, "findPublicNPC",new ParamReturnRunnable() {public Object run(V8Array param) {
			for(Actor npc:GameViews.gameview.stage.getActors())
				if(npc instanceof PublicNPC && ((PublicNPC)npc).getId().equals(param.getInteger(0)))
					return (PublicNPC)npc;
			return null;
		}});
		register(runtime, "removeSelf",new ParamRunnable() {public void run(V8Array param) {
			Base.removeSelf(Script.this);
		}});
		register(runtime, "playMusic",new ParamRunnable() {public void run(V8Array param) {
			Music.playMusic(Script.this, param.getString(0));
		}});
		register(runtime, "playSE",new ParamRunnable() {public void run(V8Array param) {
			Music.playSE(Script.this, param.getString(0));
		}});
		register(runtime, "changeSelf",new ParamRunnable() {public void run(V8Array param) {
//			Base.changeSelf(this,script);
		}});
		register(runtime, "showMSG",new ParamRunnable() {public void run(V8Array param) {
			if(param.length()==1)
				RPG.ctrl.msg.show(Script.this, param.getString(0));
			else
				RPG.ctrl.msg.show(Script.this,正常);
		}});
		register(runtime, "stopAllSE",new ParamRunnable() {public void run(V8Array param) {
			if(param.length()==1)
				Music.stopAllSE(Script.this,(float) param.getDouble(0));
			else if(param.get(1) instanceof String)
				Music.stopAllSE(Script.this,(float) param.getDouble(0),param.getString(1));
		}});
		register(runtime, "setSEVolume",new ParamRunnable() {public void run(V8Array param) {
			Music.setSEVolume(Script.this,(float) param.getDouble(0),(float) param.getDouble(1));
		}});
		register(runtime, "hideMSG",new ParamRunnable() {public void run(V8Array param) {
			RPG.ctrl.msg.hide(Script.this);
		}});
		register(runtime, "hideFG",new ParamRunnable() {public void run(V8Array param) {
			if(param.length()==1)
				RPG.ctrl.fg.hide(Script.this, param.getInteger(0));
			else
				RPG.ctrl.fg.hideAll(Script.this);
		}});
		register(runtime, "showFGLeft",new ParamRunnable() {public void run(V8Array param) {
			RPG.ctrl.fg.show(Script.this, Setting.IMAGE_FG+param.getString(0)+param.getString(1)+".png", FG.LEFT);
		}});
		register(runtime, "showFGRight",new ParamRunnable() {public void run(V8Array param) {
			RPG.ctrl.fg.show(Script.this, Setting.IMAGE_FG+param.getString(0)+param.getString(1)+".png", FG.RIGHT);
		}});
		register(runtime, "swapHeroQueue",new ParamRunnable() {public void run(V8Array param) {
			if(param.length()==1)
				Heros.swapHeroQueue(Script.this, param.getInteger(0));
			else
				Heros.swapHeroQueue(Script.this, param.getInteger(0),param.getInteger(1));
		}});
		register(runtime, "select",new ParamRunnable() {public void run(V8Array param) {
			String[] str=new String[param.length()];
			for(int i=0;i<param.length();i++)
				str[i]=param.get(i).toString();
			GameViews.selectUtil.select(Script.this, str);
		}});
		register(runtime, "currentSelect",new ParamReturnRunnable() {public Object run(V8Array param) {
			return SelectUtil.currentSelect;
		}});
		register(runtime, "setWeather",new ParamRunnable() {public void run(V8Array param) {
			RPG.ctrl.weather.setWeather(Script.this, param.getInteger(0));
		}});
		register(runtime, "waitCameraMove",new ParamRunnable() {public void run(V8Array param) {
			MoveController.waitCameraMove(Script.this);
		}});
		register(runtime, "print",new ParamRunnable() {public void run(V8Array param) {
			System.out.println("Script:"+param.get(0).toString());
		}},false);
	}
	
	/**
	 * 插入一个新的执行器到堆栈的最后一位
	 */
	public BaseScriptExecutor $ (BaseScriptExecutor exe){
		currentScript=exe;
		return exe;
	}
	
	
	/**
	 * 设置当前游戏的时间
	 * @param {@link GameDate.Time} time 游戏时间（上午？下午
	 * @return
	 */
	public BaseScriptExecutor setGameTime(GameDate.Time time){
		return ColorUtil.set(this, time);
	}
	
	/**
	 * 让当前NPC进行随机移动
	 * @param speed 移动速度
	 * @param length 最大随机步伐 random(0 to length)
	 * @return
	 */
	public BaseScriptExecutor randomWalk(int speed,int length){
		return Move.random(this, speed,length,null, null);
	}
	
	/**
	 * 让当前NPC在某个矩形区域内进行随机移动（不保证NPC会随机移动出判定矩阵，不过会尽快在下一次随机移动里回到矩阵内的）
	 * @param speed 移动速度
	 * @param length 最大随机步伐 random(0 to length)
	 * @param bounds 越界矩形区域（x和y）
	 * @param poi poi！！！啊，不是，是给参数某个点，NPC会在这个点附近进行移动而不是瞎逼走，如果想让NPC瞎逼走就把poi设为null就好了
	 * @return
	 */
	public BaseScriptExecutor randomWalk(int speed,int length,Vector2 bounds,Vector2 poi){
		return Move.random(this, speed,length,bounds, poi);
	}
	
	/**
	 * 让当前的NPC随机移动，并且慢慢靠近HERO的所在位置wwwww
	 * @param speed 移动速度
	 * @param length 最大随机步伐 random(0 to length)
	 * @return
	 */
	public BaseScriptExecutor randomWalkByHero(int speed,int length){
		return Move.random(this,speed,length,null,new Vector2(-1,-1));
	}
	
	/**
	 * 让当前的NPC随机移动，并且尽可能的在自己的出生点附近移动而不是随机的瞎逼走wwww
	 * @param speed 移动速度
	 * @param length 最大随机步伐 random(0 to length)
	 * @return
	 */
	public BaseScriptExecutor randomWalkBySelf(int speed,int length){
		return Move.random(this,speed,length,new Vector2(3,3),new Vector2(this.npc.mapx,this.npc.mapy));
	}
	
	
	/**
	 * 移动相机，偏移值根据HERO的当前位置，x和y可以为负数，这样就是反方向移动了qwq
	 * @param x 往x方向移动多少
	 * @param y 往y方向移动多少
	 * @param wait 是否等待移动完毕才结束当前执行器www
	 * @return
	 */
	public BaseScriptExecutor setCameraPositionWithHero(final int x,final int y,final boolean wait){
		return MoveController.setCameraPositionWithHero(this,x,y,wait);
	}
	
	/**
	 * 移动相机，根据地图的左下角(0,0)为偏移
	 * @param x 往x方向移动多少
	 * @param y 往y方向移动多少
	 * @param wait 是否等待移动完毕才结束当前执行器www
	 * @return
	 */
	public BaseScriptExecutor setCameraPositionWithAbsolute(final int x,final int y,final boolean wait){
		return MoveController.setCameraPositionWithAbsolute(this, x, y, wait);
	}
	
	/**
	 * 给当前某个Hero显示一个心情气球
	 * @param c Hero类型
	 * @param type 气球类型
	 * @return
	 */
	public BaseScriptExecutor setBalloon(Class<? extends Hero> c,BalloonType type){
		return Heros.balloon(this, c, type);
	}
	
	/**
	 * 给当前NPC显示一个心情气球
	 * @param type 气球类型
	 * @return
	 */
	public BaseScriptExecutor setBalloon(BalloonType type){
		return Heros.balloon(this, type);
	}
	
	public BaseScriptExecutor setBalloon(Script who,BalloonType type){
		return Heros.balloon(who, type);
	}
}
