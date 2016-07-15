package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.system.base.Res;

public class ListItem extends Table{
	Label label = Res.get("", 22);
	Image icon = Res.base().width(0).height(0).hide();
	Table insert = new Table();
	Cell<Image> iconCell;
	Cell<Label> labelCell;
	Cell<Table> insertCell;
	boolean selected = false;
	Image bg;
	
	public ListItem() {
		setTouchable(Touchable.enabled);
		left();
		insertCell = add(insert).left().padLeft(25).padRight(15);
		iconCell = add(icon).left();
		labelCell = add(label.left()).left();
		addActor(bg = Res.base().hide());
	}
	
	public ListItem addText(String text,int fontSize){
		this.label = Res.get(text, fontSize);
		labelCell.setActor(label.left()).left();
		return this;
	}
	
	public ListItem insert(Actor a){
		insert.add(a);
		return this;
	}
	
	public <T extends Actor> ListItem insert(T a,CustomRunnable<Cell<T>> callback){
		callback.run(insert.add(a));
		return this;
	}
	
	public void draw(Batch batch, float parentAlpha) {
		bg.width(getWidth()).height(getHeight()).color(1,1,1,.2f).setVisible(selected);
		super.draw(batch, parentAlpha);
	}
	
	public ListItem addIcon(Image icon){
		this.icon = icon;
		iconCell.setActor(icon).padRight(30);
		return this;
	}
	
	public boolean select(){
		return selected;
	}
	
	public ListItem select(boolean s){
		this.selected = s;
		return this;
	}

	public ListItem size(int i, int j) {
		setSize(i, j);
		return this;
	}
	
}
