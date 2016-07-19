package com.rpsg.rpg.view.menu.task;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.Achievement;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.ListItem;
import com.rpsg.rpg.system.ui.UI;

public class AchievementGroup extends Group {
	ScrollPane pane;
	public AchievementGroup() {
		Res.base().query().setSize(768, 423).setPosition(222, 25).setColor(0,0,0,.75f).appendTo(this);
		generateList();
	}
	
	void generateList(){
		if(pane != null)
			pane.remove();
		
		Table table = new Table().left().top();
		
		float top = pane == null ? 0 : pane.getScrollPercentY();
		$.add(pane = UI.scrollPane(table)).appendTo(this).setSize(768, 424).setPosition(222, 25);
		
		for(Achievement ach : RPG.ctrl.task.allAchievement()){
			ListItem item = new ListItem();
			item.addIcon(ach.getIcon().size(76, 76));
			item.insertAfter(Res.get(ach.name, 24), Cell::row);
			item.insertAfter(Res.get(ach.description, 14).color(Color.LIGHT_GRAY), c -> c.padTop(10));
			
			if(ach.hasGain() && !ach.gained() && !RPG.ctrl.task.isAchievementDoing(ach))
				item.add(UI.textButton("领取奖励", 16).style(UI::redButton).onClick(()->{
					ach.gain();
					generateList();
					RPG.putMessage("获得奖励："+ach.description2, Color.RED);
				})).expand().size(120, 35).right().padRight(30);
			
			table.add(item).row();
		}
		
		$.add(table.padBottom(40).padTop(1)).eachCells(ListItem.class, c -> c.size(768,95).getActor(a -> a.size(708,30)).left().click(() -> {
			c.getActor().select(true);
			$.each(c.others(), c2 -> ((ListItem)c2.getActor()).select(false));
		}));
		
		pane.setSmoothScrolling(false);
		pane.layout();
		pane.setScrollPercentY(top);
	}
}
