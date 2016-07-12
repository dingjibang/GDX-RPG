package com.rpsg.rpg.view.menu.task;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.Task;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.ListItem;
import com.rpsg.rpg.system.ui.UI;

public class TaskGroup extends Group{
	
	ScrollPane pane;
	
	public TaskGroup() {
		Res.base().query().setSize(768, 251).setColor(0,0,0,.75f).setPosition(222, 187).appendTo(this);
		
		generateList();
		
	}
	
	private void generateList(){
		List<Task> list = RPG.ctrl.task.task();
		if(pane != null) pane.remove();
		
		Table table = new Table().left().top();
		table.setDebug(true);
		
		ScrollPane pane;
		$.add(pane = UI.scrollPane(table)).appendTo(this).setSize(768, 251).setPosition(222, 187);
		
		table.add(label("your mother fucker1")).row();
		table.add(label("your mother fucker2")).row();
		table.add(label("your mother fucker3")).row();
		table.add(label("your mother fucker4")).row();
		table.add(label("your mother fucker5")).row();
		table.add(label("your mother fucker6")).row();
		table.add(label("your mother fucker7")).row();
		table.add(label("your mother fucker8")).row();
		table.add(label("your mother fucker9")).row();
		table.add(label("your mother fucker10")).row();
		table.add(label("your mother fucker11")).row();
		table.add(label("your mother fucker12")).row();
		table.add(label("your mother fucker13")).row();
		table.add(label("your mother fucker14")).row();
		table.add(label("your mother fucker15")).row();
		table.add(label("your mother fucker16")).row();
		table.add(label("your mother fucker17")).row();
		table.add(label("your mother fucker18")).row();
		table.add(label("your mother fucker19")).row();
		table.add(label("your mother fucker20")).row();
		table.add(label("your mother fucker21")).row();
		table.add(label("your mother fucker22")).row();
		table.add(label("your mother fucker23")).row();
		table.add(label("your mother fucker24")).row();
		table.add(label("your mother fucker25")).row();
		table.add(label("your mother fucker26")).row();
		table.add(label("your mother fucker27")).row();
		table.add(label("your mother fucker28")).row();
		
		table.addCaptureListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				System.out.println("your mother fucker2");
				return true;
			}
		});
		
		$.add(table).eachCells(ListItem.class, c -> c.padLR(30).size(108,30).left().click(() -> {
			c.getActor().select(true);
			$.each(c.others(), c2 -> ((ListItem)c2.getActor()).select(false));
		}));
		
	}
	
	private ListItem label(String text){
		return new ListItem().addText(text, 20);
	}
}
