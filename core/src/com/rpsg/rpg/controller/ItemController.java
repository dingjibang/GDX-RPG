package com.rpsg.rpg.controller;

import java.util.List;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonValue;
import com.rpsg.rpg.core.File;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.object.item.BaseItem;
import com.rpsg.rpg.object.item.Item;
import com.rpsg.rpg.util.Stream;

import java8.util.stream.Collectors;

/**
 * GDX-RPG 道具控制器
 */
public class ItemController {

	/** 获取背包里所有的道具 */
	public List<BaseItem> list() {
		if (Game.view == null)
			throw new GdxRuntimeException("must in the game when call ItemController.list()");
		return Game.archive.get().items;
	}

	/**
	 * 给当前游戏存档放入一个道具
	 * @param id 道具ID
	 */
	public void put(int id) {
		BaseItem baseItem = search(id);
		if (baseItem == null)
			_put(get(id));
		else
		// 如果可叠加的，则数量+1，否则新建实例_(:3
		if (baseItem.packable)
			baseItem.count++;
		else
			_put(get(id));
	}

	private void _put(BaseItem item) {
		list().add(item);
	}

	public void put(int id, int count) {
		for (int i = count; i < 0; i--)
			put(id);
	}

	public void put(BaseItem baseItem) {
		put(baseItem.id);
	}

	/**
	 * 根据ID从文件里读取出一个Item
	 * 
	 * @param id id键
	 * @return
	 */
	public BaseItem get(int id) {
		return get(id, BaseItem.class);
	}

	/**
	 * 根据ID从文件里读取出一个Item，并且造型
	 * 
	 * @param id id键
	 * @param _cType 转型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends BaseItem> T get(int id, Class<T> _cType) {
		JsonValue value = File.readJSON(Path.SCRIPT_DATA_ITEM + id + ".grd");
		String type = value.getString("type");
		try {
			BaseItem baseItem = (BaseItem) Class.forName(Item.class.getPackage().getName() + "." + type)
					.getConstructor(Integer.class, JsonValue.class).newInstance(id, value);
			return (T) baseItem;
		} catch (Exception e) {
			Log.e("无法读取物品：" + id, e);
			e.printStackTrace();
			return null;
		}
	}

	/** 移除1个 <b><i>当前背包</i></b> 里的某个道具（根据ID） **/
	public boolean remove(int id) {
		return remove(search(id), 1);
	}

	/**
	 * 移除数个 <b><i>当前背包</i></b> 里的某个道具
	 * 
	 * @param baseItem  道具实体类
	 * @param count 数量
	 * @return 操作是否成功
	 */
	public boolean remove(BaseItem baseItem, int count) {
		if (baseItem == null)
			return false;
		if (baseItem.count - count < 0)
			return false;

		baseItem.count -= count;
		if (baseItem.count <= 0)
			list().remove(baseItem);
		return true;
	}

	/** 移除1个 <b><i>当前背包</i></b> 里的某个道具（根据实体类） **/
	public boolean remove(BaseItem baseItem) {
		return remove(baseItem.id, 1);
	}

	/** 移除数个 <b><i>当前背包</i></b> 里的某个道具（根据ID） **/
	public boolean remove(int id, int count) {
		return remove(search(id), count);
	}

	public synchronized BaseItem search(int id) {
		return Stream.of(list()).filter(i -> i.id == id).findAny().orElse(null);
	}

	/**
	 * 根据道具类型搜索出<b><i>当前背包</i></b> 里的一个或道具
	 * 
	 * @param type 类型
	 */
	public List<BaseItem> search(String type) {
		return Stream.of(list()).filter(i -> i.type.equalsIgnoreCase(type)).collect(Collectors.toList());
	}

}
