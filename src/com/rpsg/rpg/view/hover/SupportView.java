package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;

public class SupportView extends HoverView {
	Hero hero;
	public SupportView(Hero hero){
		this.hero=hero;
	}
	WidgetGroup group;
	public void init() {
		if(hero.support!=null){
			group=new WidgetGroup();
			stage.addActor(new Image(Res.NO_TEXTURE).size(GameUtil.screen_width, GameUtil.screen_height).position(0, 0).onClick(()->keyDown(Keys.ESCAPE)));
			stage.addActor(group);
			group.pack();
			group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"sup_detabox.png").size(784, GameUtil.screen_height).position(240, 0));
			group.addActor(Res.get(Setting.GAME_RES_IMAGE_FG+hero.fgname+"/Normal.png").position(1100, 0).scaleY(0.35f).color(1,1,1,.5f).scaleX(-0.35f));
			group.addActor(new Label(hero.name,55).setPos(490, 520).setWidth(1000));
			group.addActor(new Label(hero.jname,43).setPos(560, 490).setWidth(1000).color(1, 1, 1, .3f).setPad(-18));
			group.addActor(new Label("角色等级"+hero.prop.get("level")+"，社群("+hero.association.name+")等级"+hero.association.level,36).setPos(420, 440).setWidth(1000).color(1, 1, 1, .7f).setPad(-1));
			group.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_TACTIC+"left.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_TACTIC+"left_p.png")).pos(430, 465).onClick(()->keyDown(Keys.ESCAPE)));
			group.addActor(new Label(hero.support.name,50).setPos(430, 280).setWidth(1000).color(hero.support.color.r, hero.support.color.g, hero.support.color.b, hero.support.color.a));
			group.addActor(new Label(hero.support.illustration,20).setPos(480, 210).setWidth(1000));
			group.setPosition(GameUtil.screen_width-200, 0);
			group.addAction(Actions.moveTo(0, 0,0.4f,Interpolation.pow3));
		}
	}

	public void logic() {
		stage.act();
	}

	public void draw(SpriteBatch batch) {
		stage.draw();
	}

	public void close() {
		
	}
	
	public boolean keyDown(int keycode) {
		if(keycode==Keys.ESCAPE){
			group.clearActions();
			group.addAction(Actions.sequence(Actions.moveTo(GameUtil.screen_width,0,0.3f,Interpolation.pow3),Actions.after(new Action() {
				public boolean act(float delta) {
					disposed=true;
					return false;
				}
			})));
		}
			
		return stage.keyDown(keycode);
	}

}
