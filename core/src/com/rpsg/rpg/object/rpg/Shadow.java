package com.rpsg.rpg.object.rpg;

import java.io.Serializable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;

public class Shadow extends Actor implements Serializable {
	private static final long serialVersionUID = 1L;
	static final String SHADOW = Setting.GAME_RES_WALK + "walk_shadow.png";
	
	Image shadowimage = new Image();
	public Image getShadowimage() {
		return shadowimage;
	}
	public void setShadowimage(Image shadowimage) {
		this.shadowimage = shadowimage;
	}
	public Shadow() {
		Texture tex = Res.getTexture(SHADOW);
		this.shadowimage= new Image(new TextureRegion(tex));
	}

}