package com.rpsg.rpg.core;

import com.badlogic.gdx.utils.Scaling;

import java.io.Serializable;

/**
 * GDX-RPG 系统设置<br>
 * 从 {@link Game#setting} 变量中访问
 */
public class Setting implements Serializable{
	private static final long serialVersionUID = -2067767488295945018L;
	
	
	/**是否进行纹理过滤*/
	public boolean filter = true;
	
	/**总音量*/
	public float volume = 1f;
	/**音效音量*/
	public float SEVolume = 1f;
	/**音乐音量*/
	public float musicVolume = 1f;
	
	/**启用画面二次渲染*/
	public boolean enablePost = true;
	
	/**是否启用更清晰的字体<br>
	 * see {@link Text#get(int, com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator) Text.get}
	 * */
	public boolean hdFont = false;

	/**使用新的文字渲染模块，性能会些许提升但是内存占用较大*/
	public boolean newTextRender = true;
	
	/**是否允许缓存资源（如纹理、图片）以提高游戏速度*/
	public boolean cache = true;
	
	/**窗口缩放模式，true为{@link Scaling#fit 适应缩放}，false为{@link Scaling#stretch 拉抻缩放}*/
	public boolean fitScaling = true;

	/**【仅用于调试】是否开启“错误后继续运行游戏”，开启后将忽视关键性错误继续运行游戏，但是会造成严重性能下降以及未知的情况。*/
	public boolean onErrorResumeNext = true;

	/**【仅用于调试】是否开启UI调试，开启后将显示UI边框。*/
	public boolean uiDebug = false;
	
	/**
	 * 创建系统设置，如果硬盘已存在则直接读取
	 */
	public static Setting create() {
		Object obj = File.load(Path.SAVE + "/setting.sav");
		if(obj == null)
			return new Setting();
		return (Setting)obj; 
	}

	private Setting(){}
}
