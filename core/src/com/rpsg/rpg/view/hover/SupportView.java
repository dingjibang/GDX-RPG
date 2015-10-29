package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
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
			stage.addActor(new Image(Res.NO_TEXTURE).size(GameUtil.screen_width, GameUtil.screen_height).position(0, 0).onClick(new Runnable() {
				@Override
				public void run() {
					SupportView.this.keyDown(Keys.ESCAPE);
				}
			}));
			stage.addActor(group);
			group.pack();
			group.addActor(Res.get(Setting.IMAGE_MENU_TACTIC + "sup_detabox.png").size(784, GameUtil.screen_height).position(240, 0));
			$.add(Res.get(Setting.IMAGE_FG+hero.fgname+"/Normal.png").position(1100, 0).scaleY(0.35f).color(1,1,1,.5f).scaleX(-0.35f)).setOrigin(Align.bottomLeft).appendTo(group);
			group.addActor($.add(Res.get(hero.name,55)).setPosition(490, 520).getItem());
			group.addActor($.add(Res.get(hero.jname,43)).setPosition(560, 490).setColor(1, 1, 1, .3f).getItem());
			group.addActor($.add(Res.get("角色等级"+hero.prop.get("level")+"，社群("+hero.association.name+")等级"+hero.association.level,36)).setPosition(420, 440).setColor(1, 1, 1, .7f).getItem());
			group.addActor(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"left.png"),Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"left_p.png")).pos(430, 465).onClick(new Runnable() {
				public void run() {
					SupportView.this.keyDown(Keys.ESCAPE);
				}
			}));
			group.addActor($.add(Res.get(hero.support.name,50)).setPosition(430, 280).setColor(hero.support.r, hero.support.g, hero.support.b, hero.support.a).getItem());
			group.addActor($.add(Res.get(hero.support.illustration,20)).setPosition(480, 210).getItem());
			
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
