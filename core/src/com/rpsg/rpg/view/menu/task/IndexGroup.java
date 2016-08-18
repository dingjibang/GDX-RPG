package com.rpsg.rpg.view.menu.task;

import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.object.base.Index;
import com.rpsg.rpg.object.base.Index.IndexType;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.UI;

public class IndexGroup extends Group{
	
	private int start = 1, current = 1, max;
	private IndexType type = IndexType.actor;
	private Group group = new Group();
	
	public IndexGroup() {
		Res.base().query().setSize(768, 333).setPosition(222, 115).setColor(0,0,0,.75f).appendTo(this);
		
		$.add(
				UI.textButton("角色图鉴", 17).object(IndexType.actor).x(222), 
				UI.textButton("敌人图鉴", 17).object(IndexType.enemy).x(344)
			)
			.appendTo(this).setSize(122, 41).setY(15)
			.run(self -> self.click(a -> {
				self.setChecked(true).not(a).setChecked(false);
				type = (IndexType) self.getUserObject();
			}))
			.first().click();
		
		generate();
		
		addActor(group);
	}
	
	private void generate(){
		System.out.println(start + "," + current);
		group.clear();
		
		List<Index> list = RPG.ctrl.index.get(type);
		
		list = $.multi(list, 50);//TODO DEBUG
		
		this.max = MathUtils.ceil((float)list.size() / 10f) + 1;
		
		//generate page widget
		Table pages = new Table().right();
		
		$.add(pages).cell(UI.textButton("<<", 17)).click(() -> to(current - 5));
		
		for(int i = start; i < start + 5; i++){
			$.add(pages).cell(UI.textButton(i + "", 17)).getActor(button -> button.onClick(() -> to(button.text()))).getActor().setChecked(current == i);
		}
		
		$.add(pages).cell(UI.textButton(">>", 17)).click(() -> to(current + 5));
		
		
		$.add(pages).eachCells(c -> c.size(50, 41).padLeft(15)).setPosition(591, 35).setWidth(400).debug();
		
		group.addActor(pages);
		//end 
	}
	
	//page prev 
	private void prev(){
		current--;
		calcPage();
		generate();
	}
	
	//page next
	private void next(){
		if(current < max)
			current++;
		calcPage();
		generate();
	}
	
	private void calcPage(){
		current = current <= 1 ? 1 : current;
		start = current - 2;
		start = start <= 1 ? 1 : start;
		
		if(current + 2 >= max)
			start = current - (4 - (max - current));
		start = start <= 1 ? 1 : start;
	}
	
	private void to(String _page){
		to(Integer.valueOf(_page));
	}
	
	private void to(int page){
		int cpy = current;
		for(int i = cpy; i != page; i = (page > cpy) ? i + 1 : i - 1){
			if(page > cpy) 
				next();
			else
				prev();
		}
		generate();
	}
}
