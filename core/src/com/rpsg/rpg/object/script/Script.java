package com.rpsg.rpg.object.script;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.FGType;
import com.rpsg.rpg.object.base.MsgType;
import com.rpsg.rpg.object.rpg.Balloon.BalloonType;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.rpg.PublicNPC;
import com.rpsg.rpg.system.controller.MoveController;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FG; 
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.utils.display.PostUtil;
import com.rpsg.rpg.utils.display.SelectUtil;
import com.rpsg.rpg.utils.display.WeatherUtil;
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
		init();
		return this;
	}
	
	public abstract void init();
	
	public boolean isAlive=false;
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public void dispose(){
		this.isAlive=false;
	}
	
	public List<BaseScriptExecutor> scripts=new LinkedList<BaseScriptExecutor>();
	
	public int point=-1;
	public boolean currentExeced=true;
	private Class<? extends NPC>[] type;
	
	
	public void run(){
		if(waitTime>0){
			waitTime--;
			return;
		}
		if(currentExeced)
			if(++point==scripts.size()){
				this.dispose();
				return;
			}else{
				if(scripts.get(point) instanceof ScriptExecutor){
					((ScriptExecutor)scripts.get(point)).toInit();
					currentExeced=false;
				}else{
					scripts.get(point).init();
				}
			}
		if(scripts.get(point) instanceof ScriptExecutor)
			((ScriptExecutor)scripts.get(point)).step();
	}
	
	/**
	 * 插入一个新的执行器到当前指针
	 */
	public BaseScriptExecutor _$(BaseScriptExecutor exe){
		scripts.remove(scripts.size()-1);
		scripts.add(point+1, exe);
		return exe;
	}
	
	/**
	 * 插入一个新的执行器到当前指针后一位
	 */
	public BaseScriptExecutor __$(BaseScriptExecutor exe){
		scripts.remove(scripts.size()-1);
		scripts.add(point==-1?0:point, exe);
		if(point>=0)
			point--;
		currentExeced=true;
		return exe;
	}
	
	/**
	 * 插入一个新的执行器到堆栈的最后一位
	 */
	public BaseScriptExecutor $ (BaseScriptExecutor exe){
		scripts.add(exe);
		return exe;
	}
	
	/**
	 * 在屏幕上打印出一句话
	 * @param str 要说的话
	 * @param title 要说话的人
	 * @param size 文本字号
	 * @return
	 */
	public BaseScriptExecutor say(String str,String title,int size){
		return Msg.say(this, str, title, size);
	}
	
	/**
	 * 在屏幕上打印出一句话
	 * @param str 要说的话
	 * @param title 要说话的人
	 * @return
	 */
	public BaseScriptExecutor say(String str,String title){
		return Msg.say(this, str, title, 22);
	}
	
	/**
	 * 包装一个脚本执行器
	 * @param exe 执行器
	 * @return
	 */
	public void and(final BaseScriptExecutor exe){
		if(exe instanceof ScriptExecutor)
			$(new ScriptExecutor(this) {
				ScriptExecutor proxy=(ScriptExecutor)exe;
				public void init() {
					proxy.init();
				}
				public void step(){
					if(!proxy.script.currentExeced)
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
	 * 显示/隐藏菜单
	 * @param flag 是否显示
	 * @return
	 */
	public BaseScriptExecutor showMenu(boolean flag){
		return PostUtil.showMenu(this, flag);
	}
	
	/**
	 * 在屏幕上打印出一句话
	 * @param str 要说的话
	 * @return
	 */
	public BaseScriptExecutor say(String str){
		return Msg.say(this, str, "", 22);
	}
	
	/**
	 * 锁定玩家键盘，此时玩家只能按z键对话。
	 * @param b 是否锁定
	 * @return
	 */
	public BaseScriptExecutor setKeyLocker(boolean b){
		return Msg.setKeyLocker(this, b);
	}
	
	/**
	 * 锁定当前NPC，禁止其转向或移动
	 * @param b 是否锁定
	 * @return
	 */
	public BaseScriptExecutor lock(boolean b){
		return Move.lock(this, b);
	}
	
	/**
	 * 让当前NPC面向玩家
	 * @return
	 */
	public BaseScriptExecutor faceToHero(){
		return Move.faceToHero(this);
	}
	
	/**
	 * 让当前NPC面向一个方向
	 * @param face {@link IRPGObject.FACE_?} 要面向的方向
	 * @return
	 */
	public BaseScriptExecutor faceTo(int face){
		return Move.turn(this, face);
	}
	
	public BaseScriptExecutor faceTo(Script who,int face){
		return Move.turn(who, face);
	}
	
	/**
	 * 让当前的NPC移动
	 * @param step 移动多少步
	 * @return
	 */
	public BaseScriptExecutor move(int step){
		return Move.move(this, step);
	}
	
	public BaseScriptExecutor move(Script who,int step){
		return Move.move(who, step);
	}
	
	/**
	 * 当前脚本暂停
	 * @param frame 暂停的毫秒数
	 * @return
	 */
	public BaseScriptExecutor wait(int frame){
		return Timer.wait(this, frame);
	}
	/**
	 * 根据类型查找当前游戏内的NPC
	 * @param class 类型
	 * @return
	 */
	public NPC findNPC(Class<? extends NPC>... type){
		GdxQuery query=$.add(GameViews.gameview.stage.getActors().items).find(type);
		return query.isEmpty()?null:(NPC)query.getItem(); 
	}
	
	public NPC findNPC (String id){
		for(Actor npc:GameViews.gameview.stage.getActors())
			if(npc instanceof PublicNPC && ((PublicNPC)npc).getId().equals(id))
				return (PublicNPC)npc;
		return null;
	}
	
	/**
	 * 立即移除当前脚本自身
	 * @return
	 */
	public BaseScriptExecutor removeSelf(){
		return Base.removeSelf(this);
	}
	
	/**
	 * 播放音乐
	 * @return
	 */
	public BaseScriptExecutor playMusic(String musicName){
		return Music.playMusic(this, musicName);
	}
	
	/**
	 * 播放音效
	 * @return
	 */
	public BaseScriptExecutor playSE(String musicName){
		return Music.playSE(this, musicName);
	}
	
	/**
	 * 立即移除当前脚本自身并换为另一个脚本
	 * @param script 脚本的class类型
	 * @return
	 */
	public BaseScriptExecutor changeSelf(Class<? extends Script> script){
		return Base.changeSelf(this,script);
	}
	
	/**
	 * 显示对话框
	 * @param {@link MsgType} 要显示什么样的对话框 
	 * @return
	 */
	public BaseScriptExecutor showMSG(String msgType){
		return Msg.show(this, msgType);
	}
	
	/**
	 * 停止所有SE播放
	 */
	public BaseScriptExecutor stopAllSE(float time){
		return Music.stopAllSE(this,time);
	}
	
	public BaseScriptExecutor stopAllSE(float time,String without){
		return Music.stopAllSE(this,time,without);
	}
	
	public BaseScriptExecutor setSEVolume(float time,float volume){
		return Music.setSEVolume(this, volume, time);
	}
	
	/**
	 * 显示默认的对话框
	 * @return
	 */
	public BaseScriptExecutor showMSG(){
		return Msg.show(this, 正常);
	}
	
	/**
	 * 隐藏对话框
	 * @return
	 */
	public BaseScriptExecutor hideMSG(){
		return Msg.hide(this);
	}
	
	/**
	 * 隐藏立绘
	 * @param position 左侧的立绘还是右侧的立绘 {@link FG}
	 * @return
	 */
	public BaseScriptExecutor hideFG(int position){
		return FG.hide(this, position);
	}
	
	/**
	 * 隐藏全部当前屏幕上的立绘
	 * @return
	 */
	public BaseScriptExecutor hideFG(){
		return FG.hideAll(this);
	}
	
	/**
	 * 在屏幕左侧显示一张立绘
	 * @param people 要显示谁的立绘 
	 * @param {@link FGType} 立绘的类型
	 * @return
	 */
	public BaseScriptExecutor showFGLeft(String people,String look){
		return FG.show(this, Setting.GAME_RES_IMAGE_FG+people+look+".png", FG.LEFT);
	}
	
	/**
	 * 在屏幕右侧显示一张立绘
	 * @param people 要显示谁的立绘 
	 * @param {@link FGType} 立绘的类型
	 * @return
	 */
	public BaseScriptExecutor showFGRight(String people,String look){
		return FG.show(this, Setting.GAME_RES_IMAGE_FG+people+look+".png", FG.RIGHT);
	}
	
	/**
	 * 将当前英雄队列的某个人和第一个人（head）交换
	 * @param position 要交换的英雄在队列里的位置
	 * @return
	 */
	public BaseScriptExecutor swapHeroQueue(int position){
		return Heros.swapHeroQueue(this, position);
	}
	
	/**
	 * 将当前英雄队列的某个人和第一个人（head）交换
	 * @param c 要交换的英雄的class类型
	 * @return
	 */
	public BaseScriptExecutor swapHeroQueue(Class<? extends Hero> c){
		return Heros.swapHeroQueue(this, c);
	}
	
	/**
	 * 将当前英雄队列的某个人和另一个人交换
	 * @param position 小张
	 * @param position2 小王
	 * @return
	 */
	public BaseScriptExecutor swapHeroQueue(int position,int position2){
		return Heros.swapHeroQueue(this, position, position2);
	}
	
	/**
	 * 将当前英雄队列的某个人和另一个人交换
	 * @param 小张的class类型
	 * @param 小王的class类型
	 * @return
	 */
	public BaseScriptExecutor swapHeroQueue(Class<? extends Hero> c1,Class<? extends Hero> c2){
		return Heros.swapHeroQueue(this, c1 , c2);
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
	 * 显示选择框，可以让玩家进行游戏选项
	 * @param args 要选择的内容
	 * @return
	 */
	public BaseScriptExecutor select(String ... args){
		return GameViews.selectUtil.select(this, args);
	}
	
	/**
	 * 当前玩家选择的内容
	 * @return
	 */
	public String currentSelect(){
		return SelectUtil.currentSelect;
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
	 * 设置天气
	 * @param type 天气的类型，请查询WeatherUtil TODO 需要改进成enum下吧
	 * @return
	 */
	public BaseScriptExecutor setWeather(int type){
		return WeatherUtil.setWeather(this, type);
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
	 * 等待相机移动完毕
	 * @return
	 */
	public BaseScriptExecutor waitCameraMove(){
		return MoveController.waitCameraMove(this);
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
	 * 当前玩家选择的是否是某个字符串
	 * @param equ 字符串
	 * @return
	 */
	public boolean currentSelect(String equ){
		return SelectUtil.currentSelect.equals(equ);
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
