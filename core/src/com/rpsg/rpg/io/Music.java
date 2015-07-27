package com.rpsg.rpg.io;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.script.BaseScriptExecutor;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.object.script.ScriptExecutor;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.Logger;

public class Music {
	public static com.badlogic.gdx.audio.Music MUSIC;
	public static Map<String,com.badlogic.gdx.audio.Music> bgm=new HashMap<String,com.badlogic.gdx.audio.Music>();
	public static Map<String,SE> se=new HashMap<String, SE>();
	public static Sound hint,err;
	
	public static void playMusic(String music){
		if(null==bgm.get(music)){
			bgm.put(music, Gdx.audio.newMusic(Gdx.files.internal(Setting.GAME_RES_MUSIC_BGM+music)));
			Logger.info("成功创建音乐："+music);
		}
		bgm.get(music).play();
		MUSIC=bgm.get(music);
	}
	
	public static BaseScriptExecutor playMusic(Script script,final String music){
		return script.$(new BaseScriptExecutor() {
			public void init() {
				playMusic(music);
			}
		});
	}
	
	public static BaseScriptExecutor playSE(Script script,final String se){
		return script.$(new BaseScriptExecutor() {
			public void init() {
				playSE(se);
			}
		});
	}
	
	public static void stopCurrentMusic(){
		if(MUSIC!=null && MUSIC.isPlaying())
			MUSIC.stop();
	}
	
	public static void playSE(String name){
		if(null==se.get(name)){
			se.put(name, new SE(Gdx.audio.newSound(Gdx.files.internal(Setting.GAME_RES_MUSIC_SE+name))));
			Logger.info("成功创建音效："+name);
		}
		se.get(name).setId(se.get(name).getSound().play());
	}
	public static void fadeOutMusic(){
		for(int i=500000;i>0;i--){
			MUSIC.setVolume((float)i/500000);
		}
		MUSIC.stop();
	}

	public static BaseScriptExecutor stopAllSE(Script script,final float time) {
		return script.$(new ScriptExecutor(script) {
			Actor proxy=new Actor();
			public void init() {
				proxy.addAction(Actions.fadeOut(time));
			}
			
			public void step(){
				proxy.act(Gdx.graphics.getDeltaTime());
				for(String key:se.keySet()){
					se.get(key).getSound().setVolume(se.get(key).getId(), proxy.getColor().a);
				}
				if(proxy.getColor().a==0)
					dispose();
			}
		});
	}
	
}
