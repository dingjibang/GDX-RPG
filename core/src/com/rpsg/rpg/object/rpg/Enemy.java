package com.rpsg.rpg.object.rpg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.EnemyAction.EnemyActionProp;
import com.rpsg.rpg.object.rpg.EnemyAction.PropType;
import com.rpsg.rpg.object.rpg.EnemyAction.RemoveType;
import com.rpsg.rpg.system.controller.ItemController;

public class Enemy {
	public int id;
	public String imgPath;
	public String name;
	public Map<String, String> prop;
	public Integer aiLevel;
	public List<EnemyAction> actions;

	static JsonReader reader = new JsonReader();

	public static List<Enemy> get(int id) {
		List<Enemy> list = new ArrayList<Enemy>();
		JsonValue value = getJSON(id);
		
		String enemyType = value.getString("type");
		
		if(enemyType.equalsIgnoreCase("simple"))
			list.add(getEnemy(id,value));
		else
			for(JsonValue enemyId : value.get("group"))
				list.add(getEnemy(enemyId.asInt(),getJSON(enemyId.asInt())));
		
		return list;
	}

	public  static Enemy getEnemy(int id,JsonValue value) {
		Enemy enemy = new Enemy();
		enemy.name = value.getString("name");
		enemy.prop = ItemController.getProp(value.get("prop"));
		enemy.aiLevel = value.has("aiLevel") ? value.getInt("aiLevel") : 1;
		enemy.id = id;
		enemy.imgPath = Setting.IMAGE_ENEMY + id + ".png";
		
		List<EnemyAction> actions = new ArrayList<>();
		
		for(JsonValue actionValue : value.get("action")){
			EnemyAction action = new EnemyAction();
			action.turn = actionValue.getInt("turn");
			action.propbabitly = actionValue.getFloat("propbabitly");
			action.act = RPG.ctrl.item.get(actionValue.getInt("act"), Spellcard.class);
			action.buff = actionValue.has("buff") ? actionValue.get("buff").asIntArray() : null;
			action.special = actionValue.has("special") ? actionValue.getString("special") : null;
			action.remove = actionValue.has("remove") ? RemoveType.valueOf(actionValue.getString("remove")) : RemoveType.no;
			action.forawrd = actionValue.has("forward") ? ItemForward.valueOf(actionValue.getString("forward")) : ItemForward.hero;
			action.range =  actionValue.has("range") ? ItemRange.valueOf(actionValue.getString("range")) : ItemRange.one;
			List<EnemyActionProp> propList =  new ArrayList<>();
			if(actionValue.has("prop")){
				for(JsonValue propValue : actionValue.get("prop")){
					EnemyActionProp prop = new EnemyActionProp();
					prop.type = PropType.valueOf(propValue.getString("type"));
					prop.forward = ItemForward.valueOf(propValue.getString("forward"));
					prop.prop = ItemController.getProp(propValue.get("prop"));
					propList.add(prop);
				}
			}
			action.prop = propList;
		}
		
		enemy.actions = actions;
		return enemy;
	}
	
	private static JsonValue getJSON(int id){
		return reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA_ENEMY + id + ".grd").readString());
	}

}
