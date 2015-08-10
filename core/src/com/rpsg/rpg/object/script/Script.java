package com.rpsg.rpg.object.script;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
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
import com.rpsg.rpg.object.rpg.RPGObject;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.system.ui.Image;
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


public class Script implements MsgType,FGType{
	
	public NPC npc;
	public CollideType callType;
	public String script="";
	private Map<Long,Object> bindObjectPool = new HashMap<Long,Object>();
	private long sequence = 0;
	
	private long nextSeq(){
		return ++sequence;
	}
	
	private long bind(Object object){
		long id = nextSeq();
		bindObjectPool.put(id, object);
		return id;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getBind(long l,Class<T> type){
		return (T)bindObjectPool.get(l);
	}
	
	private Object getBind(long l){
		return getBind(l,Object.class);
	}
	
	public Script generate(NPC npc,CollideType type,String script){
		this.npc=npc;
		this.callType=type;
		this.script=script;
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}
	
	V8ExecutorEx mainExecutor;
	public void start() throws InterruptedException {
		try {
			mainExecutor = new V8ExecutorEx(script) {
				@Override
				protected void setup(V8 runtime) {
					configureWorker(runtime);
				}
			};
			mainExecutor.setName(npc.toString()+(npc instanceof PublicNPC?" & ID:"+((PublicNPC)npc).getId():"")+" (collide: "+callType+") : "+mainExecutor.getId());
			mainExecutor.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
	public static enum exeMode{
		first,running,stop
	}
	
	
	public void run(){
//		System.out.println("maint:"+currentExeced);
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
	
	private synchronized void configureWorker(final V8 runtime) {
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
		register(runtime, "end",new ParamRunnable() {public void run(V8Array param) {
			try{
				dispose();
				mainExecutor.forceTermination();
				mainExecutor.shutdown();
				mainExecutor.stop();
			}catch(Exception e){
				e.printStackTrace();
			}
		}});
		register(runtime, "lock",new ParamRunnable() {public void run(V8Array param) {
			 Move.lock(Script.this, param.getBoolean(0));
		}});
		register(runtime, "faceToHero",new ParamRunnable() {public void run(V8Array param) {
			 Move.faceToHero(Script.this);
		}});
		register(runtime, "faceTo",new ParamRunnable() {public void run(V8Array param) {
			if(param.length()==1)
				Move.turn(Script.this, param.getInteger(0));
		}});
		register(runtime, "otherFaceTo",new ParamRunnable() {public void run(V8Array param) {
			getNPC((String) param.get(0)).turn((int)param.get(1));
		}},false);
		register(runtime, "move",new ParamRunnable() {public void run(V8Array param) {
				Move.move(Script.this, param.getInteger(0));
		}});
		register(runtime, "otherMove",new ParamRunnable() {public void run(V8Array param) {
			getNPC((String) param.get(0)).walk((int)param.get(1)).testWalk();
		}},false);
		register(runtime, "wait",new ParamRunnable() {public void run(V8Array param) {
			Timer.wait(Script.this, param.getInteger(0));
		}});
		register(runtime, "findNPC",new ParamReturnRunnable() {public Object run(final V8Array param) {
			try {
				return param.get(0);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		
		V8Object rpgo=new V8Object(runtime);
		rpgo.add("FACE_L", RPGObject.FACE_L);
		rpgo.add("FACE_R", RPGObject.FACE_R);
		rpgo.add("FACE_U", RPGObject.FACE_U);
		rpgo.add("FACE_D", RPGObject.FACE_D);
		rpgo.registerJavaMethod(new JavaCallback() {public Object invoke(V8Object receiver, V8Array parameters) {
			return RPGObject.getReverseFace((int) parameters.get(0));
		}}, "getReverseFace");
		runtime.add("RPGObject", rpgo);
		rpgo.release();
		
		V8Object self=new V8Object(runtime);
		self.registerJavaMethod(new JavaCallback() {public Object invoke(V8Object receiver, V8Array parameters) {
			return npc.getFaceByPoint((int)parameters.get(0), (int)parameters.get(1));
		}}, "getFaceByPoint");
		runtime.add("NPC", self);
		self.release();
		
		V8Object hero=new V8Object(runtime);
		hero.add("mapx", RPG.ctrl.hero.getHeadHero().mapy);
		hero.add("mapy", RPG.ctrl.hero.getHeadHero().mapy);
		runtime.add("Hero", hero);
		hero.release();
		
		V8Object setting=new V8Object(runtime);
		for(Field f:Setting.class.getFields())
			if(f.getType().equals(String.class))
				try {
					setting.add(f.getName(), f.get(Setting.class).toString());
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		runtime.add("Setting",setting);
		setting.release();
		
		runtime.registerJavaMethod(new JavaCallback() {public Object invoke(V8Object receiver, V8Array parameters) {
			try {
				Image i =Res.fuckOPENGL(parameters.get(0).toString());
				receiver.add("bind", bind(i));
				registerBridge(receiver,i ,true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return receiver;
		}}, "getImage");
		
		V8Object cg=new V8Object(runtime);
		cg.registerJavaMethod(new JavaVoidCallback() {public void invoke(V8Object receiver, V8Array parameters) {
			RPG.ctrl.cg.push(getBind(parameters.getObject(0).getInteger("bind"),Image.class));
		}}, "push");
		runtime.add("CG",cg);
		cg.release();
		V8Object actions=new V8Object(runtime);
		for(final Method m:Actions.class.getMethods()){
			actions.registerJavaMethod(new JavaCallback() {
				public Object invoke(V8Object receiver, V8Array parameters) {
					try {
						Object[] arr=readObjectArray(parameters);
						Method _m =null;
						for(final Method __m:Actions.class.getMethods()){
							if(__m.getName().equals(m.getName()) && __m.getParameterTypes().length== parameters.length())
								_m=__m;
						}
						if(_m==null){
							return null;
						}
						Object o = _m.invoke(Actions.class, arr);
						receiver.add("bind", bind(o));
						return receiver;
					} catch (Throwable e) {
						e.printStackTrace();
						return null;
					}
				}
			}, m.getName());
		}
		runtime.add("Actions",actions);
		actions.release();
		
	}
	
	protected NPC getNPC(String s) {
		NPC npc = null;
		try {
			for(Actor _npc:GameViews.gameview.stage.getActors())
				if(_npc instanceof PublicNPC && ((PublicNPC)_npc).getId().equals(s))
					npc= (PublicNPC)_npc;
			return npc;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 插入一个新的执行器到堆栈的最后一位
	 */
	public BaseScriptExecutor $ (BaseScriptExecutor exe){
		currentScript=exe;
		return exe;
	}
	
	private Object[] readObjectArray(V8Array parameters){
		Object[] arr=new Object[parameters.length()];
		for(int i=0;i<parameters.length();i++){
			Object o = parameters.get(i);
			if(o instanceof Double){
				Float f = new Float((Double)o);
				arr[i]=f;
			}else
				arr[i]=o;
		}
		return arr;
	}
	
	@SuppressWarnings("unused")
	private Class<?>[] readClassArray(V8Array parameters){
		Class<?>[] arr=new Class<?>[parameters.length()];
		for(int i=0;i<parameters.length();i++){
			Object o = parameters.get(i);
			if(o instanceof Double){
				arr[i]=Float.class;
			}else
				arr[i]=o.getClass();
		}
		return arr;
	}
	
	public void registerBridge(V8Object v8o, final Object obj,boolean superClass) {
			Class<?> c = !superClass?obj.getClass():obj.getClass().getSuperclass();
			Method[] methods = c.getDeclaredMethods();
			for (final Method m : methods){ 
				try{
					if(Modifier.isPublic(m.getModifiers()))
						v8o.registerJavaMethod(new JavaCallback() {
							public Object invoke(V8Object receiver, V8Array parameters) {
								Object[] arr=readObjectArray(parameters);
								try {
									Object o=m.invoke(obj,arr);
									if(o==null || !(o instanceof V8Object))
										return receiver;
									else
										return o;
								} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
									e.printStackTrace();
								}
								return null;
							}
						}, m.getName());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
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
	 * 移动相机，偏移值根据HERO的当前位置，x和y可以为负数，这样就是反方向移动了qwq
	 * @param x 往x方向移动多少
	 * @param y 往y方向移动多少
	 * @param wait 是否等待移动完毕才结束当前执行器www
	 * @return
	 */
	public BaseScriptExecutor setCameraPositionWithHero(final int x,final int y,final boolean wait){
		return MoveController.setCameraPositionWithHero(this,x,y,wait);
	}
	
	public void and(final BaseScriptExecutor exe){
		if(exe instanceof ScriptExecutor)
			$(new ScriptExecutor(this) {
				ScriptExecutor proxy=(ScriptExecutor)exe;
				public void init() {
					proxy.init();
				}
				public void step(){
					if(proxy.script.currentExeced==exeMode.running)
						proxy.step();
					else
						dispose();
				}
			});
		else
			$(new BaseScriptExecutor() {
				public void init() {
					exe.init();
				}
			});
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
