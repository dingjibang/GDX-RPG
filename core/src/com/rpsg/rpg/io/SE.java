package com.rpsg.rpg.io;

import com.badlogic.gdx.audio.Sound;


public class SE {
	private Sound sound;
	private long id;
	private float volume;
	public SE(Sound sound, long id) {
		this.sound = sound;
		this.setId(id);
	}
	
	public SE(Sound sound) {
		this.sound = sound;
	}
	public Sound getSound() {
		return sound;
	}
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	public SE() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public void setVolume(float volume){
		this.volume=volume;
		sound.setVolume(getId(), getVolume());
	}

	public float getVolume() {
		return volume;
	}
	
}
