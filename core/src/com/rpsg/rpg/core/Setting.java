package com.rpsg.rpg.core;

import java.io.Serializable;

/**
 * GDX-RPG 系统设置<br>
 * 从 {@link RPG.setting} 中访问
 */
public class Setting implements Serializable{
	private static final long serialVersionUID = -2067767488295945018L;
	
	
	/**是否进行纹理过滤*/
	public boolean filter = true;
	
	
	/**
	 * 创建系统设置，如果硬盘已存在则直接读取
	 */
	public static Setting create() {
		Object obj = File.load(Path.SAVE + "/setting.sav");
		if(obj == null)
			return new Setting();
		return (Setting)obj; 
	}
}
