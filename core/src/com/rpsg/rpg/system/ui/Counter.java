package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;

public class Counter extends Group {
	private int max = 0;
	private Label l,r;
	private int count = 0;

	public Counter(int max) {
		setMax(max);
		generateUI();
	}

	private void generateUI() {
		addActor(Res.base().size(166,133).a(.15f));
		addActor(Res.base().size(74,121).position(6,6).a(.1f));
		addActor(Res.base().size(74,121).position(86, 6).a(.1f));
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(18);
		
		$.add(new TextButton("+1", tstyle)).appendTo(this).click(new Runnable() {public void run() {
			add(1);
		}}).setPosition(198,100).setSize(120,40).getCell().prefSize(140,40);
		$.add(new TextButton("-1", tstyle)).appendTo(this).click(new Runnable() {public void run() {
			add(-1);
		}}).setPosition(333,100).setSize(120,40).getCell().prefSize(140,40);
		
		$.add(new TextButton("+10", tstyle)).appendTo(this).click(new Runnable() {public void run() {
			add(10);
		}}).setPosition(198,50).setSize(120,40).getCell().prefSize(140,40);
		$.add(new TextButton("-10", tstyle)).appendTo(this).click(new Runnable() {public void run() {
			add(-10);
		}}).setPosition(333,50).setSize(120,40).getCell().prefSize(140,40);
		
		$.add(new TextButton("MAX", tstyle)).appendTo(this).click(new Runnable() {public void run() {
			set(getMax());
		}}).setPosition(198,0).setSize(120,40).getCell().prefSize(140,40);
		$.add(new TextButton("C", tstyle)).appendTo(this).click(new Runnable() {public void run() {
			set(0);
		}}).setPosition(333,0).setSize(120,40).getCell().prefSize(140,40);
		
		$.add(l = new Label(0,85).align(6,23).width(74)).appendTo(this);
		$.add(r = new Label(0,85).align(86,23).width(74)).appendTo(this);
	}
	
	public Counter set(int count){
		if(count > max)
			count = max;
		if(count < 0)
			count = 0;
		
		l.setText((count / 10)+"");
		r.setText((count % 10)+"");
		
		this.count = count;
		
		return this;
	}
	
	public Counter add(int count){
		return set(this.count+count);
	}
	
	public int get(){
		return count;
	}

	public int getMax() {
		return max;
	}

	public Counter setMax(int max) {
		this.max = max;
		return this;
	}
	
	public Counter position(int x,int y){
		setPosition(x, y);
		return this;
	}
}
