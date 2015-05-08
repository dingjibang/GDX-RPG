package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.rpsg.rpg.core.Setting;

public abstract class AssociationSkill implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name = "";
	public String illustration = "";
	public String imagePath=Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_default_icon.png";

	public void exec() {
	};
}
