package com.rpsg.rpg.io;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.game.Logger;

public class Music {
	public static com.badlogic.gdx.audio.Music MUSIC;
	public static Map<String,com.badlogic.gdx.audio.Music> bgm=new HashMap<String,com.badlogic.gdx.audio.Music>();
	public static Map<String,Sound> se=new HashMap<String, Sound>();
	public static Sound hint,err;
	
	public static void playMusic(String music){
		if(null==bgm.get(music)){
			bgm.put(music, Gdx.audio.newMusic(Gdx.files.internal(Setting.GAME_RES_MUSIC_BGM+music)));
			Logger.info("成功创建音乐："+music);
		}
		bgm.get(music).play();
		MUSIC=bgm.get(music);
	}
	
	public static void stopCurrentMusic(){
		if(MUSIC!=null && MUSIC.isPlaying())
			MUSIC.stop();
	}
	
	public static void playSE(String name){
		if(null==se.get(name)){
			se.put(name, Gdx.audio.newSound(Gdx.files.internal(Setting.GAME_RES_MUSIC_SE+name+".wav")));
			Logger.info("成功创建音效："+name);
		}
		se.get(name).play();
	}
	public static void fadeOutMusic(){
		for(int i=500000;i>0;i--){
			MUSIC.setVolume((float)i/500000);
		}
		MUSIC.stop();
	}
}
