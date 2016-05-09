package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Item.ItemOccasion;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.SpellcardIcon;
import com.rpsg.rpg.utils.game.GameUtil;

public class SelectSpellcardView extends HoverView{

	public void init() {
		Hero hero = (Hero)param.get("hero");
		@SuppressWarnings("unchecked") CustomRunnable<Spellcard> callback = (CustomRunnable<Spellcard>)param.get("callback");
		
		stage.addAction(Actions.parallel(Actions.alpha(0),Actions.alpha(1,0.3f,Interpolation.pow4Out)));
		$.add(Res.get(Setting.UI_BASE_IMG)).setPosition(100, 20).setSize(815, 515).appendTo(stage).setColor(.25f,.25f,.25f,9f);
		$.add(new Actor()).setSize(GameUtil.screen_width, GameUtil.screen_height).click(()->disposed = true).appendTo(stage);
		
		ImageButton exit=new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"file_exit.png"),Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"file_exit_active.png"));
		exit.setPosition(845, 480);
		exit.addAction(Actions.moveTo(845, 465, 0.1f));
		exit.addListener(new InputListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int b) {
				disposed = true;
			}
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(exit);
		
		final Group group = new Group(),outer = $.add(new Group()).setPosition(-100, 10).appendTo(stage).getItem(Group.class);
		
		$.add(Res.get("选择符卡", 45)).appendTo(outer).setPosition(240,450);
		$.add(Res.get(Setting.UI_BASE_IMG).size(303, 383).color(.05f,.05f,.05f,.8f).position(240,38)).appendTo(outer);
		$.add(Res.get(Setting.UI_BASE_IMG).size(403, 293).color(.05f,.05f,.05f,.8f).position(575,129)).appendTo(outer);
		$.add(Res.get(Setting.UI_BASE_IMG).size(303, 30).color(.85f,.85f,.85f,.3f).position(240,38)).appendTo(outer);
		final ImageButton apply;
		$.add(apply = new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_ITEM+"but_use.png")).fgSelfColor(true)).appendTo(outer).setSize(405,58).setPosition(575, 39).getCell().prefSize(405,58);
		
		$.add(new Label(hero.name+"可持有"+hero.target.getProp("maxsc")+"张符卡",18).position(240, 42).width(303).center()).appendTo(outer);
		
		
		final Table items = new Table().left().top();
		for(Spellcard sc:hero.sc){
			items.add(new SpellcardIcon(sc).onClick(t -> {
					$.add(items).children().each(si->((SpellcardIcon) si).select(false));
					t.select(true);
					group.clear();
					$.add(new Label(t.sc.name,40)).appendTo(group).setPosition(600, 360);
					$.add(new Label("消耗 "+t.sc.cost+" 点妖力",18).right().width(300).color(Color.ORANGE)).appendTo(group).setPosition(655, 360);
					$.add(Res.get(Setting.UI_BASE_IMG)).appendTo(group).setPosition(600, 345).setSize(352,2);
					Table labels = new Table().align(Align.topLeft);
					labels.add(new Label(t.sc.description,20).warp(true)).align(Align.topLeft).prefWidth(343).row();
					labels.add(new Label(t.sc.description2,20).warp(true).color(Color.ORANGE)).align(Align.topLeft).prefWidth(343).padTop(20).row();
					if(t.sc.cost > hero.target.getProp("mp")){
						if(t.sc.occasion == ItemOccasion.all || t.sc.occasion == ItemOccasion.battle){
							apply.onClick(()->{
								callback.run(sc);
								disposed = true;
							}).fg.a(1);
						}else{
							apply.onClick(null).fg.a(.3f);
							labels.add(new Label("无法在战斗时使用。",20).warp(true).color(Color.GRAY)).align(Align.topLeft).prefWidth(343).padTop(20).row();
						}
					}else{
						apply.onClick(null).fg.a(.3f);
						labels.add(new Label("魔法不足，无法使用。",20).warp(true).color(Color.GRAY)).align(Align.topLeft).prefWidth(343).padTop(20).row();
					}
					
					ScrollPane pane = new ScrollPane(labels);
					pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
					pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
					pane.setSize(353, 190);
					pane.setPosition(600,140);
					pane.setFadeScrollBars(false);
					group.addActor(pane);
			})).align(Align.topLeft).size(303,70).row();
		}
		
		ScrollPane pane = new ScrollPane(items);
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
		pane.setSize(303, 353);
		pane.setPosition(240,68);
		pane.setFadeScrollBars(false);
		pane.setScrollingDisabled(true, false);
		outer.addActor(pane);
		outer.addActor(group);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.ESCAPE)
			disposed = true;
		return super.keyDown(keycode);
	}
}
