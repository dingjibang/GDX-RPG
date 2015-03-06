package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.ObjectRunnable;
import com.rpsg.rpg.system.base.Confirm;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.TextButton.*;

public class ConfirmView extends HoverView{
	
	public ConfirmView(String msg,Confirm ...confirms){
		TextButtonStyle style=new TextButtonStyle();
		style.up=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebut.png");
		style.down=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebuth.png");
		for(Confirm con:confirms){
			stage.addActor(new TextButton(con.name, style));
		}
	}
	
	public void init() {
		
	}
	
	
	public void logic() {
		stage.act();
	}

	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	public void close() {
		
	}

	public boolean keyDown(int keycode) {
		if(keycode==Keys.ESCAPE)
			disposed=true;
		return stage.keyDown(keycode);
	}
	
	public static ConfirmView generateOKCancelConfirmView(String msg,ObjectRunnable okCallBack){
		ConfirmView view = new ConfirmView(msg,Confirm.OK(okCallBack),Confirm.CANCEL((view2)->((HoverView)view2).disposed=true));
		return view;
	}
}
