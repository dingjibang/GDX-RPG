package com.rpsg.rpg.object.rpgobj;


import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Equip;

public abstract class Hero extends IRPGObject {
	
	private static final long serialVersionUID = 1L;
	public static final int HERO_WIDTH=48;
	public static final int HERO_HEIGHT=64;
	public static final String RES_PATH=Setting.GAME_RES_WALK+"heros/";
	
	public String name;
	
	public int level=1;
	public int hp;
	public int maxhp=1;
	public int mp=1;
	public int maxmp=1;
	public int attack;
	public int magicAttack;
	public int defense;
	public int magicDefense;
	public int speed;
	public int hit;
	
	public Equip shoes=null;
	public Equip clothes=null;
	public Equip weapon=null;
	public Equip ornament1=null;
	public Equip ornament2=null;
	
	public String getShoesName(){
		return shoes==null?"无":shoes.name;
	}
	
	public int fullHP(){
		int tmp=hp;
		if(shoes!=null) tmp+=shoes.hp;
		if(clothes!=null) tmp+=clothes.hp;
		if(weapon!=null) tmp+=weapon.hp;
		if(ornament1!=null) tmp+=ornament1.hp;
		if(ornament2!=null) tmp+=ornament2.hp;
		return tmp;
	}
	
	public int fullMP(){
		int tmp=mp;
		if(shoes!=null) tmp+=shoes.mp;
		if(clothes!=null) tmp+=clothes.mp;
		if(weapon!=null) tmp+=weapon.mp;
		if(ornament1!=null) tmp+=ornament1.mp;
		if(ornament2!=null) tmp+=ornament2.mp;
		return tmp;
	}
	public int fullAttack(){
		int tmp=attack;
		if(shoes!=null) tmp+=shoes.attack;
		if(clothes!=null) tmp+=clothes.attack;
		if(weapon!=null) tmp+=weapon.attack;
		if(ornament1!=null) tmp+=ornament1.attack;
		if(ornament2!=null) tmp+=ornament2.attack;
		return tmp;
	}
	public int fullMagicAttack(){
		int tmp=magicAttack;
		if(shoes!=null) tmp+=shoes.magicAttack;
		if(clothes!=null) tmp+=clothes.magicAttack;
		if(weapon!=null) tmp+=weapon.magicAttack;
		if(ornament1!=null) tmp+=ornament1.magicAttack;
		if(ornament2!=null) tmp+=ornament2.magicAttack;
		return tmp;
	}
	public int fullDefense(){
		int tmp=defense;
		if(shoes!=null) tmp+=shoes.defense;
		if(clothes!=null) tmp+=clothes.defense;
		if(weapon!=null) tmp+=weapon.defense;
		if(ornament1!=null) tmp+=ornament1.defense;
		if(ornament2!=null) tmp+=ornament2.defense;
		return tmp;
	}
	public int fullMagicDefense(){
		int tmp=magicDefense;
		if(shoes!=null) tmp+=shoes.magicDefense;
		if(clothes!=null) tmp+=clothes.magicDefense;
		if(weapon!=null) tmp+=weapon.magicDefense;
		if(ornament1!=null) tmp+=ornament1.magicDefense;
		if(ornament2!=null) tmp+=ornament2.magicDefense;
		return tmp;
	}
	public int fullSpeed(){
		int tmp=speed;
		if(shoes!=null) tmp+=shoes.speed;
		if(clothes!=null) tmp+=clothes.speed;
		if(weapon!=null) tmp+=weapon.speed;
		if(ornament1!=null) tmp+=ornament1.speed;
		if(ornament2!=null) tmp+=ornament2.speed;
		return tmp;
	}
	public int fullHit(){
		int tmp=hit;
		if(shoes!=null) tmp+=shoes.hit;
		if(clothes!=null) tmp+=clothes.hit;
		if(weapon!=null) tmp+=weapon.hit;
		if(ornament1!=null) tmp+=ornament1.hit;
		if(ornament2!=null) tmp+=ornament2.hit;
		return tmp;
	}
	
	public String getClothesName(){
		return clothes==null?"无":clothes.name;
	}
	
	public String getWeaponName(){
		return weapon==null?"无":weapon.name;
	}
	
	public String getOrnament1Name(){
		return ornament1==null?"无":ornament1.name;
	}
	
	public String getOrnament2Name(){
		return ornament2==null?"无":ornament2.name;
	}
	
	public Hero() {
		super();
		this.waitWhenCollide=false;
	}
	
	public abstract void first();
	public abstract void init();
	
	public Hero(String path) {
		super(RES_PATH+path, HERO_WIDTH, HERO_HEIGHT);
		this.waitWhenCollide=false;
	}
	
}
