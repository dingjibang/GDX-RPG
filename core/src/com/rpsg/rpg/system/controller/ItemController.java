package com.rpsg.rpg.system.controller;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.SpellCard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.utils.game.Logger;

/**
 * GDX-RPG 道具核心管理器
 * @author dingjibang
 *
 */
public class ItemController {
	
	private static JsonReader reader = null;
	
	/**
	 * 给当前游戏存档放入一个道具
	 * @param id 道具ID
	 */
	public void put(int id){
		Item item =search(id);
		if(item==null)
			RPG.global.items.add(get(id));
		else
			item.count++;
	}
	
	/**
	 * 根据ID从文件里读取出一个Item
	 * @param id id键
	 * @return
	 */
	public Item get(int id){
		return get(id,Item.class);
	}
	
	/**
	 * 根据ID从文件里读取出一个Item，并且造型
	 * @param id id键
	 * @param type 类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(int id,Class<T> _cType){
		initReader();
		JsonValue result=reader.parse(Gdx.files.internal(Setting.SCRIPT_DATA+id+".grd"));
		String type=result.getString("type");
		try {
			Item item;
			
			if(type.equalsIgnoreCase(Equipment.class.getSimpleName())){
				Equipment e =(Equipment)(item=new Equipment());
				e.illustration2 = result.has("illustration2")?result.getString("illustration2"):"";
				e.onlyFor=(Class<? extends Hero>) (result.has("onlyFor")?Class.forName("com.rpsg.rpg.game.hero"+result.getString("onlyFor")):null);
				e.equipType=result.getString("equipType");
			}else if(type.equalsIgnoreCase(SpellCard.class.getSimpleName())){				//TODO
				SpellCard e =(SpellCard)(item=new SpellCard());
				e.illustration2 = result.has("illustration2")?result.getString("illustration2"):"";
			}else{
				item = new Item();
			}
			
			item.id=result.getInt("id");
			item.disable=false;
			item.illustration=result.getString("illustration");
			item.throwable=result.has("throwable")?result.getBoolean("throwable"):true;
			item.name=result.getString("name");
			item.useScript=result.has("use")?result.getString("use"):"";
			item.type=result.getString("type");
			
			
			return (T) item;
		} catch (Exception e) {
			Logger.error("无法读取物品："+id, e);
			return null;
		}
	}
	
	/** 移除1个 <b><i>当前背包</i></b> 里的某个道具（根据ID）**/
	public boolean remove(int id){
		return remove(search(id),1);
	}
	
	/**
	 * 移除数个 <b><i>当前背包</i></b> 里的某个道具
	 * @param item 道具实体类
	 * @param count 数量
	 * @return 操作是否成功
	 */
	public boolean remove(Item item,int count){
		if(item==null)
			return false;
		if(item.count-count < 0)
			return false;
		if(item.count-count == 0)
			RPG.global.items.remove(item);
		else
			item.count-=count;
		return true;
	}
	
	/** 移除1个 <b><i>当前背包</i></b> 里的某个道具（根据实体类）**/
	public boolean remove(Item item){
		return remove(item,1);
	}
	
	/** 移除数个 <b><i>当前背包</i></b> 里的某个道具（根据ID）**/
	public boolean remove(int id,int count){
		return remove(search(id),count);
	}
	
	public Item search(int id){
		for(Item item:RPG.global.items)
			if(item.id==id)
				return item;
		return null;
	}
	
	/**
	 * 根据道具类型搜索出 <b><i>当前背包</i></b> 里的一个或道具
	 * @param type 类型
	 * @return
	 */
	public ArrayList<Item> search(String type){
		ArrayList<Item> result = new ArrayList<Item>();
		for(Item item:RPG.global.items)
			if(item.type.equals(type))
				result.add(item);
		return result;
	}
	
	private static void initReader(){
		if(reader==null)
			reader = new JsonReader();
	} 
}
