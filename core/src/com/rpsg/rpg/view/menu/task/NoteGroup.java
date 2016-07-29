package com.rpsg.rpg.view.menu.task;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.items.Note;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.ListItem;
import com.rpsg.rpg.system.ui.UI;

public class NoteGroup extends Group{
	
	ScrollPane pane;
	Group group;
	
	public NoteGroup() {
		Res.base().query().setSize(320, 423).setColor(0,0,0,.75f).setPosition(222, 25).appendTo(this);
		Res.base().query().setSize(418, 423).setColor(0,0,0,.75f).setPosition(572, 25).appendTo(this);
		$.add(group = new Group()).appendTo(this);
		generateList();
	}

	private void generateList() {
		if(pane != null)
			pane.remove();
		
		Table table = new Table().left().top();
		
		float top = pane == null ? 0 : pane.getScrollPercentY();
		$.add(pane = UI.scrollPane(table)).appendTo(this).setSize(320, 424).setPosition(222, 25);
		
		for(Note note : $.sort(RPG.ctrl.item.search("note", Note.class), (a,b)->Long.compare(a.index, b.index))){
			ListItem item = new ListItem();
			item.insert(Res.get("No." + note.index + " " +note.name, 20)).left().setUserObject(note);
			table.add(item).row();
		}
		
		$.add(table.padTop(1)).eachCells(ListItem.class, c -> c.size(320,35).getActor(a -> a.size(320,30)).left().click(() -> {
			c.getActor().select(true);
			$.each(c.others(), c2 -> ((ListItem)c2.getActor()).select(false));
			set(c.object());
		}));
		
		pane.setSmoothScrolling(false);
		pane.layout();
		pane.setScrollPercentY(top);
	}
	
	private void set(Object obj){
		if(obj == null) return;
		Note note = (Note)obj;
		
		group.clear();
		
		Res.get(note.name, 34).query().setPosition(600, 390).appendTo(group);
		UI.hr(360).query().setPosition(600, 370).appendTo(group);
		
		Res.get(note.description + (note.hasSpellcard() ? "\n\n[#aaaaaa]- 获得笔记符卡：" + note.getSpellcard().name +"[]": ""), 20).markup(true).warp(true).position(600, 45).size(360,300).align(Align.topLeft).query().appendTo(group);
	
		
	}
	
}
