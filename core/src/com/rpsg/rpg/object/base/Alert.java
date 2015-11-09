package com.rpsg.rpg.object.base;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.game.GameUtil;

public class Alert extends Group {
	private Image box;
	public boolean dispose=false;
	private boolean hide=false;
	private Label label;
	private Action last;
	private int showTime = 100;
	
	private int size;
	
	private String str;

	private Image type;

	private int xoffset;

	public Alert(Image type, String str, int size) {
		this.type = type;
		this.str = str;
		this.size = size;
		init();
	}
	
	private void init(){
		label = new Label("",size);
		box=new Image(new NinePatchDrawable(AlertUtil.box));
		box.setWidth(Res.font.getTextWidth(label.getStyle().font, str)+100);
		box.setHeight(50);
		xoffset = (int) (box.getWidth() + 100);
		addActor(box);
		addActor(label);
		addActor(type);
	}

	public void update(int oy) {
		if (hide)
			if ((xoffset -= 40) < 0)
				this.dispose = true;

		if (showTime-- == 0)
			hide = true;
		box.setX(GameUtil.screen_width + 70 - xoffset);

		if (last != null)
			box.removeAction(last);
		
		last = Actions.moveTo(box.getX(), 30 + oy, 0.03f);
		
		box.addAction(last);
		
		label.setPosition((int)box.getX()-size+70, (int)box.getY()+15+size/2);
		label.setText(str);
		
		type.setX(box.getX()+10);
		type.setY(box.getY()+9);
		
	}
}
