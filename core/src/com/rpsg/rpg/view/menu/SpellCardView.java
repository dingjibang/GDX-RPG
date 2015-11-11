package com.rpsg.rpg.view.menu;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.HeroImage;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.SpellcardIcon;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;

public class SpellCardView extends DefaultIView{
	
	@Override
	public View init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		generate();
		return this;
	}
	
	private void generate(){
		stage.clear();
		stage.setDebugAll(true||Setting.persistence.uiDebug);
		Hero hero = parent.current;
		
		final Group data = $.add(new Group()).setAlpha(0).appendTo(stage).getItem(Group.class);
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(137,79).setColor(0,0,0,.52f).setPosition(240,470).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(680,48).setColor(0,0,0,.85f).setPosition(377,486).appendTo(stage);
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"info.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"info_p.png");// help button press
		cstyle.font = Res.font.get(20);
		$.add(new CheckBox("", cstyle)).appendTo(stage).setPosition(880,486).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {self.onClick(new Runnable() {public void run() {
			data.addAction(self.isChecked()?Actions.fadeIn(.3f):Actions.fadeOut(.3f));
		}});}});
		
		$.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"m_right.png")).appendTo(stage).setScale(.8f).setPosition(367, 483).onClick(new Runnable() {public void run() {
			if(parent.next())
				generate();
		}}).addAction(Actions.fadeIn(.2f)).setColor(1,1,1,0);
		$.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"m_right.png")).setScale(.8f).setScaleX(-.8f).appendTo(stage).setPosition(196, 483).onClick(new Runnable() {public void run() {
			if(parent.prev())
				generate();
		}}).addAction(Actions.fadeIn(.2f)).setColor(1,1,1,0);
		
		$.add(Res.get(Setting.IMAGE_MENU_EQUIP+"data.png").disableTouch()).setSize(187, 312).setPosition(838,174).appendTo(data);
		$.add(new HeroImage(hero,0)).appendTo(stage).setPosition(285, 480);
		$.add(new Label(hero.name,30)).setPosition(420, 495).appendTo(stage);
		$.add(new Label(hero.jname,24)).setAlpha(.1f).setPosition(420, 483).appendTo(stage);
		
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize((float)hero.prop.get("hp")/(float)hero.prop.get("maxhp")*161,20).setPosition(851, 456).appendTo(data).setColor(Color.valueOf("c33737"));
		$.add(Res.get(Setting.UI_BASE_PRO)).setSize((float)hero.prop.get("mp")/(float)hero.prop.get("maxmp")*161,20).setPosition(851, 429).appendTo(data).setColor(Color.valueOf("3762c3"));
		$.add(new Label(hero.prop.get("hp")+"/"+hero.prop.get("maxhp"),18).align(851, 455).width(161)).setColor(Color.valueOf("2BC706")).appendTo(data);
		$.add(new Label(hero.prop.get("mp")+"/"+hero.prop.get("maxmp"),18).align(851, 428).width(161)).setColor(Color.YELLOW).appendTo(data);
		
		int pad = 38,off = 421,x = 942;
		$.add(new Label(hero.prop.get("hit"),22).align(x, off-=pad).width(80)).appendTo(data);
		$.add(new Label(hero.prop.get("speed"),22).align(x, off-=pad).width(80)).appendTo(data);
		$.add(new Label(hero.prop.get("defense"),22).align(x, off-=pad).width(80)).appendTo(data);
		$.add(new Label(hero.prop.get("magicDefense"),22).align(x, off-=pad).width(80)).appendTo(data);
		$.add(new Label(hero.prop.get("attack"),22).align(x, off-=pad).width(80)).appendTo(data);
		
		$.add(new Label(hero.prop.get("magicAttack"),22).align(x, off-=pad).width(80)).appendTo(data);
		$.add(data).children().setTouchable(Touchable.disabled);
		
		$.add(Res.get(Setting.UI_BASE_IMG).size(303, 383).color(.15f,.15f,.15f,.9f).position(240,38)).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG).size(303, 30).color(.85f,.85f,.85f,.3f).position(240,38)).appendTo(stage);
		$.add(new Label(hero.name+"可持有"+hero.prop.get("maxsc")+"张符卡",18).position(240, 42).width(303).center()).appendTo(stage);
		
		final Table items = new Table().left().top();
		for(Spellcard sc:hero.sc){
			items.add(new SpellcardIcon(sc).onClick(new CustomRunnable<SpellcardIcon>() {
				public void run(SpellcardIcon t) {
					System.out.println("????");
					$.add(items).children().each(new CustomRunnable<SpellcardIcon>() {
						public void run(SpellcardIcon t) {
							t.select(false);
						}
					});
					t.select(true);
				}
			})).align(Align.topLeft).size(303,70).row();
		}
		
		ScrollPane pane = new ScrollPane(items);
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
		pane.setSize(303, 353);
		pane.setPosition(240,68);
		stage.addActor(pane);
		
	}

	@Override
	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	@Override
	public void logic() {
		stage.act();
	}
	
	@Override
	public void onkeyDown(int keyCode) {
		if(keyCode == Keys.R)
			generate();
		super.onkeyDown(keyCode);
	}
}
