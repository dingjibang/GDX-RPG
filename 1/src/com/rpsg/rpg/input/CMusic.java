package com.rpsg.rpg.input;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.rpsg.rpg.system.text.Setting;

public class CMusic {
	public static Music MUSIC;
	public static Map<String,Music> bgm=new HashMap<String,Music>();
	public static Sound hint,err;
	
	public static void playMusic(String music){
		if(null==bgm.get(music))
			bgm.put(music, Gdx.audio.newMusic(Gdx.files.internal(Setting.GAME_RES_MUSIC_BGM+music)));
		bgm.get(music).play();
		MUSIC=bgm.get(music);
	}
	
	public static void playSE(int senum){
		
	}
	public static void fadeOutMusic(){
		for(int i=500000;i>0;i--){
			MUSIC.setVolume((float)i/500000);
		}
		MUSIC.stop();
	}
}
