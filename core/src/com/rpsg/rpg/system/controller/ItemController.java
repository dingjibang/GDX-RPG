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
//			item.count++; //TODO DEBUG!!!!
			RPG.global.items.add(get(id));
	}
	
	public void put(Item item){
		put(item.id);
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
	 * @param _cType 类型
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
				e.onlyFor=(Class<? extends Hero>) (result.has("onlyFor")?Class.forName("com.rpsg.rpg.game.hero."+result.getString("onlyFor")):null);
				e.equipType=result.getString("equipType");
			}else if(type.equalsIgnoreCase(SpellCard.class.getSimpleName())){				//TODO
				SpellCard e =(SpellCard)(item=new SpellCard());
				e.illustration2 = result.has("illustration2")?result.getString("illustration2"):"";
			}else{
				item = new Item();
			}
			
			item.id=id;
			item.disable=false;
			item.illustration=result.getString("illustration");
			item.throwable=result.has("throwable")?result.getBoolean("throwable"):true;
			item.name=result.getString("name");
			item.useScript=result.has("use")?result.getString("use"):"";
			item.type=result.getString("type");
			
			
			return (T) item;
		} catch (Exception e) {
			System.out.println("无法读取物品："+id);
			e.printStackTrace();
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
	
	public synchronized Item search(int id){
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
	
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> search(String type,Class<T> classType){
		return (ArrayList<T>)search(type);
	}
	
	private static void initReader(){
		if(reader==null)
			reader = new JsonReader();
	}
	
	/**
	 * 使用一个道具继承对象（道具 或 符卡 或 装备 等）
	 * @param id 要使用道具的ID
	 * @return 是否成功
	 */
	public boolean use(int id){
		return use(search(id));
	}
	
	/**
	 * 使用一个道具（道具 或 符卡 或 装备 等）
	 * @param item 要使用的道具
	 * @return 是否成功
	 */
	public boolean use(Item item){
		if(item==null)
			return false;
		
		item.use();
		
		if(item instanceof Equipment){
			if(item.user==null)
				return false;
			Equipment equip=(Equipment)item;
			
			takeOff(equip);
			
			item.user.equips.put(equip.equipType, equip);
			replace(item.user, equip, true);//计算穿上装备后的Hero属性数值变化
			remove(equip);
		}
		return true;
	}
	
	/**
	 * 从某个角色上脱下某件装备
	 * @param item 新装备对比（不是要脱下的装备）（看不懂的话就别用这个方法……用下面那个方法）
	 * @return 是否成功脱下
	 */
	public boolean takeOff(Item item){
		if(!(item instanceof Equipment))
			return false;
		return takeOff(item.user,((Equipment)item).equipType);
	}
	
	/**
	 * 从某个角色上脱下某件装备
	 * @param hero 角色
	 * @param equipType 装备的类型（如{@link Equipment.EQUIP_SHOES}）
	 * @return 是否成功脱下
	 */
	public boolean takeOff(Hero hero,String equipType){
		if(hero.equips.get(equipType)!=null){//脱下原先的装备（如果有）
			Equipment tmp=hero.equips.get(equipType);
			put(tmp);
			replace(hero, tmp, false);//计算脱下装备后的Hero属性数值变化
			return true;
		}
		return false;
	}
	
	private static void replace(Hero hero,Equipment equip,boolean add){
		for(String key:equip.prop.keySet())
			hero.prop.put(key, add?hero.prop.get(key)+equip.prop.get(key):hero.prop.get(key)-equip.prop.get(key));
	}
	
	public Equipment getHeroEquip(Hero hero,String equipType){
		return hero.equips.get(equipType);
	}
	
}
