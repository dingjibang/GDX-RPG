package com.rpsg.rpg.object.base.items;

import java.util.ArrayList;
import java.util.List;

import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.Callback;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.system.ui.Image;

/**
 * 借助buff模块来做的一个“回调”buff，也就是说玩家拥有一个不可见的、N回合后执行任意代码的buff
 * @author dingjibang
 *
 */
public class CallbackBuff extends Buff{
	
	private static final long serialVersionUID = 1L;
	
	Callback<Result> callback;
	BaseItem item;
	boolean wait;
	int turn;//多少回合后执行
	
	public CallbackBuff(Target target, BaseItem itemOrSpellcard, Callback<Result> callback, boolean wait) {
		this.item = itemOrSpellcard;
		this.callback = callback;
		this.wait = wait;
		
		String type = itemOrSpellcard instanceof Item ? "道具" : "符卡";
		String name = itemOrSpellcard.name;
		String action = itemOrSpellcard instanceof Item ? "使用" : "吟唱";
		
		super.type = null;
		super.name = "持续"+action+"中";
		super.description = Target.name(target) + "为" + type + "『" +name + "』" + "而准备" + action + "中…";
		super.description += "\n";
		super.description += "[#aaaaaa]" + (wait ? "不" : "") + "允许在" + action + "时进行其他动作[]";
	}
	
	@Override
	public Image getIcon() {
		return getIcon(item.id);
	}
	
	public Callback<Result> callback() {
		return callback;
	}
	
	public boolean isWait() {
		return wait;
	}
	
	private int nextTurn() {
		return turn--;
	}
	
	public static List<CallbackBuff> nextTurn(Target target){
		List<CallbackBuff> list = new ArrayList<>();
		for(CallbackBuff buff : target.getCallbackBuffList()){
			buff.nextTurn();
			if(buff.turn == 0){
				list.add(buff);
				target.modifiedBuff(true);
			}
		}
		target.getCallbackBuffList().removeAll(list);
		return list;
	}
	
	
	public static boolean hasLockedBuff(Target target){
		return $.anyMatch(target.getCallbackBuffList(), CallbackBuff::isWait);
	}
}
