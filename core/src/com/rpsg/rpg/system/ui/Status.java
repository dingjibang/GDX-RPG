package com.rpsg.rpg.system.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;

public class Status extends Group {

	Label label,group;
	List<String> history = new ArrayList<>();
	ScrollPane pane;
	Table table;
	boolean flag;
	long time = System.currentTimeMillis();
	private List<DelayTask> tasks = new ArrayList<>();
	Color shit = new Color(.33f,.16f,.07f,1);
	Image up = Res.getNP(Setting.IMAGE_BATTLE+"info_u.png"),down = Res.getNP(Setting.IMAGE_BATTLE+"info_d.png");
	
	public Status() {
		table = new Table();
		table.align(Align.bottom);
		pane = new ScrollPane(table);
		pane.setPosition(0, GameUtil.screen_height - 31);
		pane.setOverscroll(false, false);
		pane.setHeight(31);
		pane.setWidth(GameUtil.screen_width);
		pane.setScrollingDisabled(true, false);
		pane.getStyle().background = new NinePatchDrawable(GameUtil.processNinePatchFile(Setting.IMAGE_BATTLE+"infobox.png", 2,2,1,1,5,5,0,3));
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
		pane.setFadeScrollBars(false);
		
		$.add(up,down).setPosition(GameUtil.screen_width / 2 - up.getWidth() / 2, GameUtil.screen_height - 37).appendTo(this);
		
		addActor(pane);
		
		$.add(pane).onClick(()->toggle());
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		$.removeIf(tasks, task-> task.time-- <= 0, task -> task.callback.run());
	}
	
	public Status add(String str){
		return add(str,0);
	}
	
	public Status add(String str,long delay){
		tasks.add(new DelayTask(delay,()->{
			table.add(label = Res.get("["+ getTime() + "] " + str, 20).color(shit).align(getAlign()).markup(true)).padLeft(10).padRight(10).height(28).width(GameUtil.screen_width - 48).row();
			setPanelToScrollBattom();
		}));
		return this;
	}
	
	public void toggle(){
		flag = !flag;
		pane.clearActions();
		pane.addAction(Actions.moveTo(0, GameUtil.screen_height - (flag ? 500 : 31),.4f,Interpolation.pow4Out));
		up.addAction(Actions.moveTo(GameUtil.screen_width / 2 - up.getWidth() / 2, GameUtil.screen_height - (flag ? 506 : 37),.4f,Interpolation.pow4Out));
		down.addAction(Actions.moveTo(GameUtil.screen_width / 2 - up.getWidth() / 2, GameUtil.screen_height - (flag ? 506 : 37),.4f,Interpolation.pow4Out));
		if(flag) up.setZIndex(23333); else down.setZIndex(2333);
		pane.addAction(Actions.sequence(Actions.sizeTo(GameUtil.screen_width, flag ? 500 : 31,.4f,Interpolation.pow4Out),Actions.run(()->{
			if(!flag) setPanelToScrollBattom();
		})));
		
		$.add(table).children().setAlign(getAlign());
	}
	
	public Status append(String str){
		return append(str,0);
	}
	
	public Status append(String str,long frameDelay){
		tasks.add(new DelayTask(frameDelay,()->{
			if(label == null) add("");
			label.setText(label.getText() + str);
			setPanelToScrollBattom();
		}));
		return this;
	}
	
	private int getAlign(){
		return flag ? Align.left : Align.center;
	}
	
	private String getTime(){
		return TimeUtil.formatDuring(System.currentTimeMillis() - time);
	}
	
	private void setPanelToScrollBattom(){
		pane.cancel();
		pane.layout();
		pane.setScrollPercentY(1);
		System.out.println($.add(table).children());
//		$.add(table).children().each((label)->((Label)label).set);
	}
	
	private static class DelayTask{
		long time;
		Runnable callback;
		public DelayTask(long time, Runnable callback) {
			super();
			this.time = time;
			this.callback = callback;
		}
	}
}
