package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class MenuHeroBox extends BGActor {
	
	Image lvbg,lv,select,hp,mp;
	boolean isSelect=false;
	static final Color HPColor=Color.valueOf("ff0000"),MPColor=Color.valueOf("00a0e9");
	public Hero hero;
	public MenuHeroBox(Hero hero) {
		this.hero=hero;
		this.setSize(70, 70);
		image.setColor(Color.valueOf(hero.color));
		actors.add($.add(new HeroImage(hero)).setPosition(10, 5).getItem());
		actors.add(lv=Res.get(Setting.UI_BASE_IMG).size(70, 17).disableTouch().color(1,1,1,0.9f));
		actors.add(new Label(hero.prop.get("level")+"",18).color(0, 0, 0, 1).align(25).setXOffset(true).setPos(4, 15).setPad(-5));
		actors.add($.add(Res.get(Setting.UI_BASE_IMG)).setSize(0,4).setY(-4).setColor(HPColor).addAction(Actions.sizeTo((float)hero.prop.get("hp")/(float)hero.prop.get("maxhp")*70, 4,0.8f,Interpolation.pow4)).getItem());
		actors.add($.add(Res.get(Setting.UI_BASE_IMG)).setSize(0,4).setY(-8).setColor(MPColor).addAction(Actions.sizeTo((float)hero.prop.get("mp")/(float)hero.prop.get("maxmp")*70, 4,0.8f,Interpolation.pow4)).getItem());
		actors.add(select=(Image) $.add(Res.get(Setting.GAME_RES_IMAGE_MENU_NEW_GLOBAL+"hero_select.png").disableTouch()).setPosition(-3, -10).addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE, 0.5f), Actions.color(new Color(1, 1, 1, 0.5f), 0.5f)))).getItem());
	}
	
	public MenuHeroBox setSelect(boolean b){
		isSelect=b;
		return this;
	}
	
	@Override
	public void drawBefore() {
		select.setVisible(isSelect);
	}
	
}
