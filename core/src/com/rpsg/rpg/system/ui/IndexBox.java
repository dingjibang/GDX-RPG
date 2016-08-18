package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Index;
import com.rpsg.rpg.system.base.Res;

public class IndexBox extends Group{
	
	Image fg,bg;
	public boolean has,select;
	
	public IndexBox(int x, int y, Index index, int idxNum) {
		if(has = index != null){
			Image bg = index.image().disableTouch();
			bg.setScaling(Scaling.fit);
			if(bg.getWidth() > 133) bg.width(133 * .9f).setAlign(Align.center);
			if(bg.getHeight() > 154) bg.height(154 * .9f).setAlign(Align.center);
			
			this.bg = Res.get(Setting.IMAGE_ENEMY + "index_b.png");
			this.bg.position(x, y).query().appendTo(this);
			
			Res.get(Setting.IMAGE_ENEMY + "index_h.png").position(x, y).disableTouch().query().appendTo(this);
			bg.query().setPosition(x + (133 / 2 - bg.getWidth() / 2), y + (154 / 2 - bg.getHeight() / 2)).appendTo(this);
			
		}else{
			Res.get(Setting.IMAGE_ENEMY + "index_none.png").query().setPosition(x, y).appendTo(this);
		}
		
		Res.base().disableTouch().query().setColor(0, 0, 0, .7f).setSize(45, 25).setPosition(x + 133 - 45, y).appendTo(this);
		Res.get(idxNum, 16).center().disableTouch().query().setSize(45, 25).setPosition(x + 133 - 45, y).appendTo(this);
		
		fg = Res.get(Setting.IMAGE_ENEMY + "index_select.png").disableTouch().hide();
		fg.query().setPosition(x, y).appendTo(this);
		fg.addAction(Actions.forever(Actions.sequence(Actions.alpha(.5f,.2f),Actions.alpha(1f,.2f))));
		
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	public IndexBox select(boolean flag){
		fg.setVisible(select = flag);
		return this;
	}
	
	public IndexBox onClick(Runnable run){
		if(bg != null)
		bg.onClick(run);
		return this;
	}
}
