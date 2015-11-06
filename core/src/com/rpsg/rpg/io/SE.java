package com.rpsg.rpg.io;

import com.badlogic.gdx.audio.Sound;


public class SE {
	private Sound sound;
	private long id;
	private float volume;
	private String path;
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

	public long getId() {
		return id;
	}

	public SE setId(long id) {
		this.id = id;
		return this;
	}
	
	public SE setVolume(float volume){
		this.volume=volume;
		sound.setVolume(getId(), getVolume());
		return this;
	}

	public float getVolume() {
		return volume;
	}

	public String getPath() {
		return path;
	}

	public SE setPath(String path) {
		this.path = path;
		return this;
	}
	
}
