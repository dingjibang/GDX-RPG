package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.system.base.Res;

/**
 * A Item(or equip.) image list view
 * @author dingjibang
 *
 */
public class ImageList extends Group{
	
	java.util.List<Icon> items;
	CustomRunnable<Icon> change;
	Icon current;
	Table inner;
	ScrollPane pane;
	
	public ImageList(java.util.List<Icon> items) {
		super();
		this.items=items;
	}
	
	public Icon getCurrent(){
		return current;
	}
	
	public ImageList onChange(CustomRunnable<Icon> onchange){
		change=onchange;
		return this;
	}
	
	public ImageList change(Icon i){
		if(change!=null)
			change.run(i);
		return this;
	}
	
	public float getScrollPercentY(){
		return pane.getScrollPercentY();
	}
	
	public ImageList setScrollPercentY(float per){
		pane.setSmoothScrolling(false);
		pane.layout();
		pane.setScrollPercentY(per);
		return this;
	}
	
	public ImageList generate(){
		return this.generate(null);
	}
	
	public ImageList setCurrent(BaseItem baseItem){
		for(Icon icon:items)
			if(icon.item==baseItem)
				setCurrent(icon);
		return this;
	}
	
	public ImageList generate(Icon before){
		inner = new Table();
		pane=new ScrollPane(inner);
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"scrollbarin.png");
		pane.setFadeScrollBars(false);
		pane.setScrollingDisabled(true, false);
		int padding = 5;
		int col = (int) (getWidth()/(70+padding*2));
		int row = 0;
//		System.out.println(items);
		int currentCol = -1;
		
		inner.align(Align.topLeft);
		
		if(before!=null && before.item!=null)
			items.add(0,before);
		
		for(final Icon i:items){
			if(currentCol++ == col-1){
				row++;
				currentCol = 0;
				inner.row();
			}
			inner.add($.add(i).setPosition(i.getWidth()*currentCol+padding, i.getHeight()*row+padding).setSize(70, 70).click(new Runnable() {public void run() {
//				if(i.enable)
					setCurrent(i);
			}}).getItem()).align(Align.topLeft).pad(padding).prefSize(70,70).getActor().setColor(1,1,1,i.enable?1:.5f);;
		}
		pane.setSize(getWidth(), getHeight());
		addActor(pane);
		return this;
	}

	protected void setCurrent(Icon i) {
		current=i;
		for(Icon icon:items)
			icon.select=icon==current;
		change(i);
	}

}
