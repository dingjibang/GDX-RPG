package com.rpsg.rpg.system.ui;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.LazyBitmapFontConctoller;
import com.rpsg.rpg.view.hover.ViewBuffView;

public class HeroStatusBox extends Group {

	Image bg;
	Label hp, mp;
	Hero hero;
	public Progress hpbar,mpbar;
	Group buffTable = new Table();

	public HeroStatusBox(Hero hero) {
		this.hero = hero;
		
		addActor(bg = Res.get(Setting.IMAGE_BATTLE+"herobox.png"));
		addActor(Res.get(Setting.IMAGE_FG+hero.fgname+"/bhead.png"));
//		addActor($.add(Res.get(hero.jname, 26)).setPosition(45,63).setAlpha(.1f).getItem());
//		addActor($.add(Res.get(hero.name, 28)).setPosition(25,73).getItem());
		
		addActor(hpbar = new Progress(Res.getNP(Setting.IMAGE_BATTLE+"bg_00001_00001.png").scale(.5f), Res.getNP(Setting.IMAGE_BATTLE+"hp_00001_00001.png").scale(.5f), 
				Res.getNP(Setting.IMAGE_BATTLE+"cache_00001_00001.png").scale(.5f), 0, hero.target.getProp("maxhp")));
		hpbar.setPosition(102, 64);
		
		addActor(mpbar = new Progress(Res.getNP(Setting.IMAGE_BATTLE+"bg_00001_00001.png").scale(.5f), Res.getNP(Setting.IMAGE_BATTLE+"mp_00001_00001.png").scale(.5f), 
				Res.getNP(Setting.IMAGE_BATTLE+"cache_00001_00001.png").scale(.5f), 0, hero.target.getProp("maxmp")));
		mpbar.setPosition(102, 29);
		
		Color shit = new Color(.33f,.16f,.07f,1);
		addActor(hp = Res.font.getLabel("", 20,LazyBitmapFontConctoller.ENGLISH_GENERATOR).position(119, 85).align(Align.left).color(shit));
		addActor(mp = Res.font.getLabel("", 20,LazyBitmapFontConctoller.ENGLISH_GENERATOR).position(119, 50).align(Align.left).color(shit));
		addActor(buffTable);
		
	}
	
	public HeroStatusBox position(int x,int y){
		setPosition(x, y);
		return this;
	}
	
	@Override
	public void act(float delta) {
		hpbar.value(hero.target.getProp("hp"));
		mpbar.value(hero.target.getProp("mp"));
		hp.setText("HP:  "+hero.target.getProp("hp") + "/" + hero.target.getProp("maxhp"));
		mp.setText("MP:  "+hero.target.getProp("mp") + "/" + hero.target.getProp("maxmp"));
		
		if(hero.target.modifiedBuff()){
			buffTable.clear();
			$.each(hero.target.getBuffList(), (index,buff) -> {
				$.add(buff.getIcon()).appendTo(buffTable).setX(index * 32 + 5).run(self -> {
					self.click(()->{
						RPG.popup.add(ViewBuffView.class,new HashMap<Object,Object>(){
							private static final long serialVersionUID = 1L;
							{
								put("top",self.getX());
								put("left",self.getX());
								put("buff",buff);
							}
						});
					});
				}).getCell().prefSize(32, 32);
			});
		}
		
		super.act(delta);
	}
}
