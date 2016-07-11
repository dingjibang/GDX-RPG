package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.system.base.Res;

public class MenuHeroBox extends Group {
	
	Image lvbg,lv,select,hp,mp;
	Image image = Res.base();
	boolean isSelect=false;
	static final Color HPColor=Color.valueOf("ff0000"),MPColor=Color.valueOf("00a0e9");
	public Hero hero;
	public MenuHeroBox(Hero hero) {
		this.hero=hero;
		this.setSize(70, 70);
		image.size((int)getWidth(),(int)getHeight()).setColor(Color.valueOf(hero.target.isDead()?"555555":hero.color));
		addActor(image);
		addActor($.add(new HeroImage(hero)).setPosition(10, 5).getItem());
		addActor(lv=Res.base().size(70, 17).disableTouch().color(hero.target.getProp("dead")==(Target.TRUE)?Color.valueOf("666666"):new Color(1,1,1,0.9f)));
		addActor($.add(Res.font.getLabel(hero.target.getProp("level")+"",15)).setColor(0, 0, 0, 1).setPosition(32, 0).setAlign(Align.center).getItem());
		addActor($.add(Res.base()).setSize(0,4).setY(-4).setColor(hero.target.getProp("dead")==(Target.TRUE)?Color.valueOf("424242"):HPColor).addAction(Actions.sizeTo((float)hero.target.getProp("hp")/(float)hero.target.getProp("maxhp")*70, 4,0.8f,Interpolation.pow4)).getItem());
		addActor($.add(Res.base()).setSize(0,4).setY(-8).setColor(hero.target.getProp("dead")==(Target.TRUE)?Color.valueOf("505050"):MPColor).addAction(Actions.sizeTo((float)hero.target.getProp("mp")/(float)hero.target.getProp("maxmp")*70, 4,0.8f,Interpolation.pow4)).getItem());
		addActor(select=(Image) $.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"hero_select.png").disableTouch()).setPosition(-3, -10).addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE, 0.5f), Actions.color(new Color(1, 1, 1, 0.5f), 0.5f)))).getItem());
	}
	
	public MenuHeroBox setSelect(boolean b){
		isSelect=b;
		return this;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		select.setVisible(isSelect);
		super.draw(batch, parentAlpha);
	}
	
}
