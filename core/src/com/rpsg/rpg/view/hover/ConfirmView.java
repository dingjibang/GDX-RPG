package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Confirm;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.utils.game.GameUtil;

public class ConfirmView extends HoverView{
	
	String msg;
	Confirm[] confirms;
	public ConfirmView(String msg,Confirm ...confirms){
		this.msg=msg;
		this.confirms=confirms;
	}
	
	static TextButtonStyle style=new TextButtonStyle();
	static{	
		style.up=Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"savebut.png");
		style.down=Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"savebuth.png");
		style.font=Res.font.get(20);
		style.font.getData().markupEnabled = true;
	}
	public void init() {
		int offset=0;
		stage.addActor(Res.get(Setting.IMAGE_MENU_SYSTEM+"savebl.png").size(GameUtil.screen_width, GameUtil.screen_height).color(0,0,0,0.85f));
		for(final Confirm con:confirms){
			Actor but= $.add(new TextButton(con.name, style)).onClick(new Runnable() {
				public void run() {
					con.callBack.run(ConfirmView.this);
				}
			}).setSize(200,50).getItem();//.getCell().size(200,50).getActor();
			
			but.setPosition(offset+=277, 150);
			stage.addActor(but);
		}
		stage.addActor($.add(Res.font.getLabel(msg,22).width(800).warp(true).markup(true)).setHeight(300).setPosition(115, 230).setAlign(Align.center).getItem());
	}
	
	public boolean keyDown(int keycode) {
		if(keycode==Keys.ESCAPE)
			disposed=true;
		return stage.keyDown(keycode);
	}
	
	public static ConfirmView okCancel(String msg,CustomRunnable<HoverView> okCallBack){
		ConfirmView view = new ConfirmView(msg,Confirm.OK(okCallBack),Confirm.CANCEL(new CustomRunnable<HoverView>() {
			@Override
			public void run(HoverView view2) {
				view2.disposed = true;
			}
		}));
		view.superInit();
		return view;
	}
	
	public static ConfirmView ok(String msg,CustomRunnable<HoverView> okCallBack){
		ConfirmView view = new ConfirmView(msg,Confirm.OK(okCallBack));
		view.superInit();
		return view;
	}
	
	public static ConfirmView ok(String msg){
		ConfirmView view = new ConfirmView(msg,Confirm.OK(new CustomRunnable<HoverView>() {
			@Override
			public void run(HoverView view2) {
				view2.disposed = true;
			}
		}));
		view.superInit();
		return view;
	}
}
