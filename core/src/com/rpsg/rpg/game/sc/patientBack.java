//package com.rpsg.rpg.game.sc;
//
//import com.rpsg.rpg.core.RPG;
//import com.rpsg.rpg.io.Music;
//import com.rpsg.rpg.object.base.items.SpellCard;
//import com.rpsg.rpg.object.rpg.Hero;
//import com.rpsg.rpg.utils.display.AlertUtil;
//
//public class patientBack extends SpellCard{
//	private static final long serialVersionUID = 1L;
//
//	public patientBack(){
//		name="回春术";
//		illustration="使用后，将对非满身疮痍状态的队友恢复50点+10%魔攻的生命值，可以在非战斗状态下使用。";
//		story="return of spring（滚";
//		magicConsume=30;
//		addParam.put("hp", 50);
//		added=10;
//		type=TYPE_USEINMAP;
//	}
//	
//	public boolean use(Hero user,Hero to){
//		if(!to.full("hp"))
//			if(user.subProp("mp",magicConsume)){
//				RPG.putMessage(to.toString()+"成功恢复了"+addParam.get("hp")+"点生命值。", AlertUtil.Green);
//				to.addProp("hp", addParam.get("hp"));
//				Music.playSE("bc");
//			}else{
//				RPG.putMessage(user.toString()+"的妖力不足以使用这个技能。", AlertUtil.Red);
//				Music.playSE("err");
//			}
//		else{
//			RPG.putMessage(to.toString()+"的生命值已满。", AlertUtil.Yellow);
//			Music.playSE("err");
//		}
//		return false;
//	}
//}
