package com.rpsg.rpg.system.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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
	
	public Status() {
		table = new Table();
		table.align(Align.bottom);
		
		pane = new ScrollPane(table);
		pane.setPosition(0, GameUtil.screen_height - 28);
		pane.setOverscroll(false, false);
		pane.setHeight(28);
		pane.setWidth(GameUtil.screen_width - 28);
		pane.setScrollingDisabled(true, false);
		pane.getStyle().background = Res.getDrawable(Setting.UI_GRAY_IMG);
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
		pane.setFadeScrollBars(false);
		
		addActor(pane);
		
		addActor(Res.get(Setting.IMAGE_BATTLE+"status_more.png").position(GameUtil.screen_width - 28, GameUtil.screen_height-28).onClick(()->toggle()));
		
	}
	
	public Status add(String str){
		table.add(label = Res.get("["+ getTime() + "] " + str, 20).align(getAlign()).overflow(false).markup(true).warp(true)).padBottom(2).padTop(1).width(GameUtil.screen_width - 28).row();
		setPanelToScrollBattom();
		return this;
	}
	
	public void toggle(){
		flag = !flag;
		pane.clearActions();
		pane.addAction(Actions.moveTo(0, GameUtil.screen_height - (flag ? 500 : 28),.4f,Interpolation.pow4Out));
		pane.addAction(Actions.sequence(Actions.sizeTo(GameUtil.screen_width - 28, flag ? 500 : 28,.4f,Interpolation.pow4Out),Actions.run(()->{
			if(!flag) setPanelToScrollBattom();
		})));
		
		$.add(table).children().setAlign(getAlign());
	}
	
	public Status append(String str){
		if(label == null) add("");
		label.setText(label.getText() + str);
		setPanelToScrollBattom();
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
	}
	
}
