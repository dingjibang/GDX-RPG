package com.rpsg.rpg.object.rpg;

import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.EnemyAction.RemoveType;

public class EnemyAction {
	public int propbabitly;
	public Spellcard act;
	public String formula;
	public RemoveType remove;
	
	public String target;
	
	//精准对象
	public Target calcedTarget;
	
	public int rank = 0;

	public static enum RemoveType {
		no, // 不删除
		success, // 当触发成功后删除
		always// 无论是否成功，只触发一次
	}
	
	public static EnemyAction attack(){
		EnemyAction action = new EnemyAction();
		action.propbabitly = 100;
		action.act = Spellcard.attack();
		action.formula = "true";
		action.remove = RemoveType.no;
		return action;
	}
	
	public static EnemyAction defense(){
		EnemyAction action = new EnemyAction();
		action.propbabitly = 100;
		action.act = Spellcard.defense();
		action.formula = "true";
		action.remove = RemoveType.no;
		return action;
	}
	
	public EnemyAction cpy(){
		EnemyAction cpy = new EnemyAction();
		cpy.propbabitly = this.propbabitly;
		cpy.act = this.act;
		cpy.formula = this.formula;
		cpy.remove = this.remove;
		cpy.target = this.target;
		return cpy;
	}
	
	public static EnemyAction fromJSON(JsonValue actionValue){
		EnemyAction action = new EnemyAction();
		action.propbabitly = actionValue.getInt("probability");
		action.act = RPG.ctrl.item.get(actionValue.getInt("act"), Spellcard.class);
		action.remove = actionValue.has("remove") ? RemoveType.valueOf(actionValue.getString("remove")) : RemoveType.no;
		action.formula = actionValue.has("formula") ? actionValue.getString("formula") : null;
		action.target = actionValue.has("target") ? actionValue.getString("target") : null;
		return action;
	}

}
