package com.rpsg.rpg.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

/**
 * GDX-RPG 音频管理器<br>
 * 未完成
 *
 */
public class Music {
	
	static OnCompletionListener seOnCompletionListener = self -> self.dispose();
	
	/**
	 * 播放一个音效
	 */
	public static void se(String soundPath){
		se(soundPath, 1f);
	}
	
	/**
	 * 播放一个音效
	 */
	public static void se(String soundPath, float vol){
		Gdx.audio.newSound(Gdx.files.internal(soundPath)).play(vol);
	}
}
