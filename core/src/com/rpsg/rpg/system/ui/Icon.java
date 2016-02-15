package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.system.base.Res;

public class Icon extends Image implements Comparable<Icon>{
	public BaseItem item = null;
	public boolean enable = true;
	public boolean select = false;
	private Image selectBox,hover,currentMask;
	public boolean current = false;
	private static Color selectColor = Color.valueOf("33FF5F");
	
	public Icon() {
		super();
		selectBox = new Image(Setting.UI_BUTTON).size(74,74);
		hover = Res.get(Setting.UI_BASE_IMG).size(74,74).action(Actions.forever(Actions.sequence(Actions.alpha(.3f,.5f),Actions.alpha(.5f,.5f))));
	}
	
	public Icon setCurrent(boolean c){
		current = c;
		if(current)
			currentMask=Res.get(Setting.IMAGE_MENU_EQUIP+"current.png");
		return this;
	}
	
	public Icon generateIcon(BaseItem baseItem,final boolean enable){
		if(baseItem==null)
			return this;
		
		this.item=baseItem;
		final ProxyImage i = (ProxyImage)(Res.exist(baseItem.getIcon())?Res.get(baseItem.getIcon()):Res.get(BaseItem.getDefaultIcon()));
		i.loaded=new Runnable() {
			@Override
			public void run() {
				Icon.this.setDrawable(i.getDrawable());
				if(!enable)
					setColor(.7f,.7f,.7f,1);
			}
		};
		
		i.lazyLoad();
		
		return this;
	}

	@Override
	public int compareTo(Icon o) {
		return (o.enable && this.enable) ? 0 : ((o.enable && !this.enable) ? 1 : -1 );
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if(select)
			selectBox.position(getX()-2, getY()-2).color(selectColor).draw(batch);
		super.draw(batch, parentAlpha);
		
		if(select)
			hover.position(getX()-2, getY()-2).actAndDraw(batch);
		else
			hover.color(Color.WHITE);
		
		if(current)
			currentMask.position(getX(), getY()).actAndDraw(batch);
	}
	
//	public

}
