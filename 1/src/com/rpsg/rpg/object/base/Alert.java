package com.rpsg.rpg.object.base;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.game.GameUtil;

public class Alert {
	public Image type;
	public String str;
	public int size;
	public Image box;
	
	public int x;
	public int y;
	public int width;
	public int height;
	
	public boolean dispose=false,hide=false;
	
	public Alert(Image type, String str, int size) {
		this.type = type;
		this.str = str;
		this.size = size;
		init();
	}
	
	public Alert(Image type, String str) {
		this.type = type;
		this.str = str;
		this.size = 20;
		init();
	}
	
	private void init(){
		box=new Image(AlertUtil.box);
		box.setWidth(FontUtil.getTextWidth(str, size)+100);
		box.setHeight(50);
		xoffset=(int) (box.getWidth()+100);
	}
	int xoffset;
	
	public void hide(){
		
	}
	
	public int showTime=100;
	Action last;
	public void draw(SpriteBatch sb,int oy){
		if(hide)
			if((xoffset-=10)<0)
				this.dispose=true;
		
		if(showTime--==0)
			hide=true;
		box.setX(GameUtil.screen_width+70-xoffset);
		
		if(last!=null)
			box.removeAction(last);
		last=Actions.moveTo(box.getX(),30+oy,0.03f);
		box.addAction(last);
		box.act(1f/60f);
		box.draw(sb);
		
		FontUtil.draw(sb, str, size,Color.WHITE,(int)box.getX()-size+55,(int)box.getY()+25+size/2+2,(int)box.getWidth());
		type.setX(box.getX()+10);
		type.setY(box.getY()+12);
		type.draw(sb);
	}
}
