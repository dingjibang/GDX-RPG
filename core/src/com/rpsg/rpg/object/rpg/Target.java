package com.rpsg.rpg.object.rpg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.Resistance.ResistanceType;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.BaseItem.Context;
import com.rpsg.rpg.object.base.items.Buff;
import com.rpsg.rpg.object.base.items.CallbackBuff;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.Item.ItemForward;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Prop;
import com.rpsg.rpg.object.base.items.Spellcard;

/**
 * GDX-RPG Hero/Enemy 数据模块
 * @author dingjibang
 *
 */
public class Target implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final int TRUE = 1;
	public static final int FALSE = 0;
	
	public Hero parentHero;
	public Enemy parentEnemy;
	
	public int rank;
	private ArrayList<Buff> buffList = new ArrayList<>();
	private ArrayList<CallbackBuff> callbackBuffList = new ArrayList<>();
	
	
	public Target lastAttackTarget = null;
	
	/**回合数（从1开始）*/
	private int turn = 1;
	
	/**是否修改过buff了（节省UI重绘时间）*/
	boolean modifiedBuff = false;
	
	public boolean modifiedBuff(){
		return modifiedBuff ? !(modifiedBuff = false) : false;
	}
	
	public void modifiedBuff(boolean flag){
		modifiedBuff = flag;
	}
	
	@SuppressWarnings("unchecked")
	public List<Buff> getBuffList(){
		return(List<Buff>)buffList.clone();
	}
	
	public Target hero(Hero hero){
		this.parentHero = hero;
		return this;
	}
	
	public Target enemy(Enemy enemy){
		this.parentEnemy = enemy;
		return this;
	}
	
	public int getTurn(){
		return turn;
	}
	
	public void nextTurn(){
		turn++;
		$.removeIf(buffList, b -> b.nextTurn() <= 0 && (modifiedBuff = true));
	}
	
	public Target clear(){
		turn = 0;
		lastAttackTarget = null;
		buffList.clear();
		modifiedBuff = true;
		callbackBuffList.clear();
		return this;
	}
	
	public Target addBuff(Buff buff){
		if(buff == null) return this;
		buffList.add(buff);
		modifiedBuff = true;
		return this;
	}
	
	public Target removeBuff(Buff buff){
		if(buff == null)
			return removeBuff();
		
		Buff target = null;
		for(Buff b : buffList)
			target = b.id == buff.id ? buff : target;
		if(target != null)
			buffList.remove(target);
		
		modifiedBuff = true;
		return this;
	}
	
	public Target removeBuff(){
		buffList.clear();
		
		modifiedBuff = true;
		return this;
	}
	
	private Map<String, Integer> prop = new HashMap<String, Integer>();
	{
		//等级
		prop.put("level", 0);
		//经验
		prop.put("exp", 0);
		//最大经验值至下一次升级
		prop.put("maxexp", 0);
		//生命值
		prop.put("hp", 0);
		//最大生命值
		prop.put("maxhp", 0);
		//魔法量
		prop.put("mp", 0);
		//魔法量
		prop.put("maxmp", 0);
		//攻击
		prop.put("attack", 0);
		//魔法攻击
		prop.put("magicAttack", 0);
		//防御
		prop.put("defense", 0);
		//魔法防御
		prop.put("magicDefense", 0);
		//速度
		prop.put("speed", 0);
		//闪避
		prop.put("evasion", 0);
		//准确率
		prop.put("hit", 0);
		//最大可携带副卡量
		prop.put("maxsc", 0);
		//是否是死亡状态的
		prop.put("dead", FALSE);
	};
	
	/**抗性*/
	public LinkedHashMap<String, Resistance> resistance = new LinkedHashMap<String, Resistance>();

	{
		resistance.put("sun", new Resistance(ResistanceType.normal,0));
		resistance.put("moon", new Resistance(ResistanceType.normal,0));
		resistance.put("star", new Resistance(ResistanceType.normal,0));
		resistance.put("metal", new Resistance(ResistanceType.normal,0));
		resistance.put("water", new Resistance(ResistanceType.normal,0));
		resistance.put("earth", new Resistance(ResistanceType.normal,0));
		resistance.put("fire", new Resistance(ResistanceType.normal,0));
		resistance.put("wood", new Resistance(ResistanceType.normal,0));
		resistance.put("physical", new Resistance(ResistanceType.normal,0));
	}
	
	public static List<Target> parse(List<?> list){
		List<Target> result = new ArrayList<>();
		
		if(list == null || list.isEmpty())
			return result;
		
		$.each(list, e -> {
			Target t = parse(e);
			if(t != null)
				result.add(t);
		});
		return result;
	}
	
	public static Target parse(Object o){
		if(o == null) return null;
		if(o instanceof Target)
			return (Target) o;
		if(o instanceof Hero)
			return (((Hero)o).target);
		if(o instanceof Enemy)
			return (((Enemy)o).target);
		throw new GdxRuntimeException("target must be hero or enemy.");
	}

	public boolean isDead(){
		return getProp("dead")==TRUE;
	}


	public int getProp(String key) {
		Integer val = this.prop.get(key);
		if(val == null) val = 0;
		
		int result = val;
		
		for(Buff buff : buffList){
			Prop prop = buff.prop.get(key);
			if(prop == null) continue;
			
			result += calcProp(val, prop.formula);
		}
		
		return result;
	}
	
	public Set<String> keySet(){
		return prop.keySet();
	}
	
	//计算两个数值相加（百分比或绝对值）
	public static Integer calcProp(int baseValue,String append){
		try {
			if(append.endsWith("%")){
				return ((Float)(((float)Integer.valueOf(append.substring(0, append.length() - 1)) / 100f) * baseValue)).intValue();
			}else{
				return Integer.valueOf(append);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**防止hp/mp数值溢出*/
	public void postOverflow(){
		for(String name:prop.keySet()){
			if(getProp(name)<0)
				prop.put(name, 0);
		}
		
		if (getProp("hp") > getProp("maxhp"))
			prop.put("hp", getProp("maxhp"));

		if (getProp("mp") > getProp("maxmp"))
			prop.put("mp", getProp("maxmp"));

	}
	
	/**
	 * 增加/覆盖 某属性的值
	 * @param name 属性名
	 * @param p 属性值（可以为%）
	 * @param post 是否处理数值溢出
	 * @param overflow 覆盖模式还是增加模式，增加模式即为在原来的数值上进行计算，覆盖则直接赋予新的值
	 */
	public void addProp(String name, String p, boolean post,boolean overflow) {
		if(!p.contains("%")){
			Integer val = getProp(name);
			Integer i = Integer.parseInt(p);
			prop.put(name,overflow ? i : (val == null ? 0 : val) + i);
		}else{
			float f = Float.parseFloat(p.split("%")[0]);
			prop.put(name, getProp(name) * (int)(f / 100));//TODO ?overflow模式下。。
		}
		
		if(name.equals("dead")){
			prop.put(name, Integer.parseInt(p));
		}
		
		if(post)
			postOverflow();
	}
	
	/**
	 * 增加属性
	 */
	public void addProp(String name, String p) {
		addProp(name, p,true,false);
	}
	
	public void addProp(String name, Integer p) {
		addProp(name, p.toString(),true,false);
	}
	
	public boolean hasProp(String name){
		return prop.containsKey(name);
	}
	
	/**
	 * 返回原始数据，谨慎使用
	 */
	public Map<String,Integer> getProp(){
		return prop;
	}
	
	/**
	 * 覆盖属性
	 */
	public void setProp(String name,Integer d){
		addProp(name, d+"",false,true);
	}
	
	/**
	 * 增加一组属性
	 */
	public void addProps(Map<String,String> map){
		for(String key:map.keySet())
			addProp(key,map.get(key));
	}
	
	/**
	 * 检测是否满血/满蓝
	 */
	public boolean full(String name) {
		if (name.equals("hp") || name.equals("mp"))
			return getProp("max" + name) == (getProp(name));
		return false;
	}

	public Target refresh() {
		if(getProp("hp") <= 0)
			setProp("dead", TRUE);
		return this;
	}
	
	public static int avg(List<Target> list, String prop){
		int avg = 0;
		for(Target enemy : list) avg += enemy.getProp(prop);
		return avg / list.size();
	}

	public boolean isDying() {
		return ((float)getProp("hp") / (float)getProp("maxhp")) > .123f;
	}

	public static String name(Object obj) {
		if(obj == null) return null;
		if(obj instanceof Hero) return ((Hero) obj).name;
		if(obj instanceof Enemy) return ((Enemy) obj).name;
		if(obj instanceof Target) return $.notNull(name(((Target) obj).parentEnemy),name(((Target) obj).parentHero)); 
		return null;
	}
	
	public static List<Target> getTargetList(BaseItem item,Context ctx){
		List<Target> targetList = new ArrayList<>();
		
		if(!(item instanceof Spellcard || item instanceof Item)) return targetList;
		
		ItemForward forward = item instanceof Spellcard ? ((Spellcard)item).forward : ((Item)item).forward;
		ItemRange range = item instanceof Spellcard ? ((Spellcard)item).range : ((Item)item).range;
		
		//如果item针对我方敌方所有人
		if(forward == ItemForward.all && range == ItemRange.all){
			targetList.addAll(ctx.friend);
			targetList.addAll(ctx.enemies);
			targetList.add(ctx.enemy);
		}
		//如果item针对我方所有人
		if(forward == ItemForward.friend && range == ItemRange.all)
			targetList.addAll(ctx.friend);
		//如果item针对敌方所有人
		if(forward == ItemForward.enemy && range == ItemRange.all)
			targetList.addAll(ctx.enemies);
		//如果指向自己
		if(forward == ItemForward.self)
			targetList.add(ctx.self);
		//如果item针对我方敌方单人
		if(forward == ItemForward.enemy && range == ItemRange.one)
			targetList.add(ctx.enemy);
		if(forward == ItemForward.friend && range == ItemRange.one)
			targetList.add(ctx.enemy);
		
		return targetList;
	}

	public ArrayList<CallbackBuff> getCallbackBuffList() {
		return callbackBuffList;
	}

	public void addCallbackBuff(CallbackBuff buff) {
		modifiedBuff = true;
		callbackBuffList.add(buff);
	}

	
}
