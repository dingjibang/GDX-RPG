package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.object.base.items.Buff;
import com.rpsg.rpg.object.base.items.Buff.BuffType;
import com.rpsg.rpg.system.base.Res;


public class ViewBuffView extends PopupView{
	public void init() {
		
		Buff buff = (Buff) param.get("buff");
		
		Table table = new Table();
		
		table.add(Res.get(buff.name,30)).row();
		if(buff.type != null)
		table.add(Res.get((buff.type == BuffType.buff ? "增益" : "有害") + "Buff",14).color(buff.type == BuffType.buff ? Color.GREEN : Color.RED)).row();
		table.add(Res.get(buff.turn == 0 ? "本回合结束" : "剩余 " + buff.turn + " 回合",14)).row();
		
		$.each(table.left().bottom().pad(10).getCells(),c -> c.padTop(5).padBottom(5).left());
		
		table.add(Res.get(buff.description,14).markup(true)).padTop(20).left().row();
		
		param.put("table", table);
		
		super.init();
	}
	
}
