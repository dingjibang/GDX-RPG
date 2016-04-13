package com.rpsg.rpg.object.rpg;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
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

public class Enemy implements Time {
	public int id;
	public String imgPath;
	public String name;
	public Integer aiLevel;
	public List<EnemyAction> actions;
	public Color color;
	private int no = 0;
	
	public Target target = new Target();
	
	public Enemy() {
		color = new Color(MathUtils.random(.4f,.8f),MathUtils.random(.4f,.8f),MathUtils.random(.4f,.8f),1);
	}
	
	
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
	
	public static List<Enemy> name(List<Enemy> list) {
		List<Integer> ids = new ArrayList<>();
		
		for(Enemy enemy : list){
			boolean include = false;
			for(int i:ids) if(enemy.id == i) include = true;
			if(!include){
				int count = 0;
				enemy.name += ++count;
				enemy.no = count;
				for(Enemy child : list)
					if(child != enemy && child.id == enemy.id)
						child.name += (child.no = ++count);
				
				ids.add(enemy.id);
			}
		}
		
		return list;
	}

	public static Enemy getEnemy(int id,JsonValue value) {
		Enemy enemy = new Enemy();
		enemy.name = value.getString("name");
		enemy.target.prop.putAll(ItemController.getIntProp(value.get("prop")));;
		enemy.aiLevel = value.has("aiLevel") ? value.getInt("aiLevel") : 1;
		enemy.id = id;
		enemy.imgPath = Setting.IMAGE_ENEMY + id + ".png";
		
		List<EnemyAction> actions = new ArrayList<>();
		
		for(JsonValue actionValue : value.get("action")){
			EnemyAction action = new EnemyAction();
			action.turn = actionValue.getInt("turn");
			action.propbabitly = actionValue.getFloat("probability");
			action.act = RPG.ctrl.item.get(actionValue.getInt("act"), Spellcard.class);
			action.buff = actionValue.has("buff") ? actionValue.get("buff").asIntArray() : null;
			action.special = actionValue.has("special") ? actionValue.getString("special") : null;
			action.remove = actionValue.has("remove") ? RemoveType.valueOf(actionValue.getString("remove")) : RemoveType.no;
			action.forawrd = actionValue.has("forward") ? ItemForward.valueOf(actionValue.getString("forward")) : ItemForward.friend;
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
	
	public int getSpeed(){
		return Integer.valueOf(target.prop.get("speed"));
	}
	
	@Override
	public String toString() {
		return "Enemy:"+name;
	}
	
	@Override
	public Color getObjectColor() {
		return color;
	}
	
	@Override
	public String getSimpleName() {
		String name = this.name.substring(0, 1);
		if(this.no != 0) name+= this.no;
		return name;
	}
	
	@Override
	public Object getThis() {
		return this;
	}

}
