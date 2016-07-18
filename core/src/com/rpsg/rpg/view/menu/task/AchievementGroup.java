package com.rpsg.rpg.view.menu.task;

import com.badlogic.gdx.scenes.scene2d.Group;
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
			item.addText(ach.name, 22);
			table.add(item).row();
		}
		
		pane.setSmoothScrolling(false);
		pane.layout();
		pane.setScrollPercentY(top);
	}
}
