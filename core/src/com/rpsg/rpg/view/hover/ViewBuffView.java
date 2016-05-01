package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode.GameInput;
import com.rpsg.rpg.object.base.items.Buff;
import com.rpsg.rpg.object.base.items.Buff.BuffType;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;


public class ViewBuffView extends HoverView{
	public void init() {
		
		Buff buff = (Buff) param.get("buff");
		
		Table table = new Table();
		stage.addActor(table);
		
		table.add(Res.get(buff.name,30)).row();
		table.add(Res.get((buff.type == BuffType.buff ? "增益" : "有害") + "Buff",14).color(buff.type == BuffType.buff ? Color.GREEN : Color.RED)).row();
		table.add(Res.get("剩余 " + buff.turn + " 回合",14)).row();
		
		$.each(table.left().bottom().pad(10).getCells(),c -> c.padTop(5).padBottom(5).left());
		
		table.add(Res.get(buff.description,14)).padTop(20).left().row();
		
		Image bg = $.add(Res.get(Setting.UI_BASE_IMG).color(0,0,0,.8f)).getItem(Image.class);
		table.layout();
		table.validate();
		
		float h = table.getMinHeight(),w = table.getMinWidth();
		
		float left = (float) param.get("left") + 10;
		float top = (float) param.get("top") - h;
		
		if(top < 0)
			top = (float) param.get("top");
		
		table.setPosition(left, top);
		
		table.addActor(bg.size(w,h));
		bg.setZIndex(0);
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		disposed = true;
		
		GameInput state = Input.state;
		Input.state = GameInput.normal;
		RPG.input.mouseMoved(screenX, screenY);
		Input.state = state;
		
		return super.mouseMoved(screenX, screenY);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		disposed = true;
		
		GameInput state = Input.state;
		Input.state = GameInput.normal;
		RPG.input.keyDown(keycode);
		Input.state = state;
		
		return super.keyDown(keycode);
	}
	
}
