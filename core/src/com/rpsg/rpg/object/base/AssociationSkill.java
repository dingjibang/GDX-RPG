package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.rpsg.rpg.core.Setting;

public abstract class AssociationSkill implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name = "";
	public String illustration = "";
	public String imagePath=Setting.IMAGE_MENU_TACTIC+"link_default_icon.png";
	public int t_level=0;

	public void exec() {
	};
	
	@Override
	public boolean equals(Object skill){
		return this.getClass().equals(skill);
	}
	
	@Override
	public String toString(){
		return name+"("+getClass().getSimpleName()+")";
	}
}
