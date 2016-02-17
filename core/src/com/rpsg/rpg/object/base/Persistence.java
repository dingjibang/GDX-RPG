package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Account;
import com.rpsg.rpg.io.Files;
import com.rpsg.rpg.utils.game.GameUtil;

/**
 * GDX-RPG 用户设定类<br>
 * 他被保存在根目录/save/文件夹下（开发时则保存在/android/assets/save/下），文件名为perisitence.es。<br>
 * 此文件存在时，则进行反持久化读入，不存在则新建此文件并根据下方的初始设定进行持久化。
 * @author dinjibang
 *
 */
public class Persistence implements Serializable{
	private static final long serialVersionUID = 1L;
	/**抗锯齿*/
	public boolean antiAliasing = true;
	/**纹理抗拉抻(filter)*/
	public boolean scaleAliasing = true;
	/**全局音量*/
	public int volume = 100;
	/**音乐音量*/
	public int musicVolume = 70;
	/**音效音量*/
	public int seVolume = 100;
	/**文本卷动速度*/
	public int textSpeed = 2;
	/**显示"FPS"在屏幕左上角*/
	public boolean showFPS = false;
	/**全局音量*/
	public boolean debugMod = false;
	/**当程序异常时是否上报错误 TODO*/
	public boolean onErrorSendMsg = true;
	/**是否将UI设定为触屏模式*/
	public boolean touchMod = true;
	/**是否启用更好的视觉效果*/
	public boolean betterDisplay = true;
	/**是否启用天气系统*/
	public boolean weather = true;
	/**是否缓存资源到内存中，以便于快速进图*/
	public boolean cacheResource = true;
	/**是否启用柔和的摄像机移动*/
	public boolean softCamera=true;
	/**是否启用UI的调试模式（开发者）*/
	public boolean uiDebug = false;
	/**总是奔跑的*/
	public boolean runmod = false;
	/**触屏粒子*/
	public boolean touchParticle = false;
	
	/**账户信息*/
	public Account account;
	
	
	/**启动自动寻路*/
	public boolean pathFind = true;
	
	public String errorMessage="";
	/**高清文字*/
	public boolean hdFont = false;
	
	public static String PersistenceFileName = Setting.PERSISTENCE+"persistence.es";
	

	public static Persistence read(){
		Object o=Files.load(PersistenceFileName);
		if(null!=o)
			return (Persistence)o;
		//create new settings if the "save" floder not exise
		Gdx.files.local(Setting.PERSISTENCE).mkdirs();
		Persistence p = new Persistence();
		p.account = new Account();
		p.touchMod= !GameUtil.isDesktop;
		Files.save(p,PersistenceFileName);
		return p;
	}
	
	public static void save(){
		Files.save(Setting.persistence,PersistenceFileName);
	}
}
