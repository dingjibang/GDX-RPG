package com.rpsg.rpg.view.hover;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Input;
import com.rpsg.rpg.object.base.IOMode.GameInput;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;


public class PopupView extends HoverView{
	Table table;
	Image bg;
	public void init() {
		table = (Table)param.get("table");
		stage.addActor(table);
		
		
		bg = $.add(Res.base().color(0,0,0,.8f)).getItem(Image.class);
		table.layout();
		table.validate();
		
		float h = table.getMinHeight(),w = table.getMinWidth();
		
		float left = (float) param.get("left") + 10;
		float top = (float) param.get("top") - h;
		
		if(top < 0)
			top = (float) param.get("top");
		
		if(left + w > GameUtil.stage_width)
			left = left - w;
		
		table.setPosition(left, top);
		
		table.addActor(bg.size(w, h));
		bg.setZIndex(0);
	}
	
	@Override
	public void draw() {
//		float h = table.getMinHeight(),w = table.getMinWidth();
//		bg.setSize(w, h);
		super.draw();
	}
	
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		disposed = true;
		GameInput state = Input.state;
		
		List<HoverView> list = RPG.popup.list();

		boolean hasMoreHoverView = list.size() >= 2 && list.get(list.size() - 1) == this;
		
		if(!hasMoreHoverView){
			Input.state = GameInput.normal;
			RPG.input.mouseMoved(screenX, screenY);
			Input.state = state;
		}else{
			RPG.popup.mouseMoved(screenX, screenY, list.size() - 2);
		}
		
		return super.mouseMoved(screenX, screenY);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		disposed = true;
		
		
		return super.keyDown(keycode);
	}
	
}
