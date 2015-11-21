package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.game.GameUtil;

public class Toast extends Group {
	
	boolean animate;
	Actor proxy;
	static ParticleEffect p;
	static{
		p = new ParticleEffect();
		p.load(Gdx.files.internal(Setting.PARTICLE+"toast.p"),Gdx.files.internal(Setting.PARTICLE));
	}
	
	public Toast(String msg, Color color, int fontSize) {
		this(msg,color,22,false);
	}
	
	public Toast(String msg, Color color) {
		this(msg,color,22);
	}
	
	public Toast(String msg) {
		this(msg,Color.WHITE,22);
	}
	
	public Toast(String msg, Color color, int fontSize, boolean animate) {
		this.animate = animate;
		
		Label label = new Label(msg,fontSize).position(40, 8).warp(true).align(Align.center).markup(true);
		Image image = new Image(Setting.UI_TOAST).color(color).width(label.getWidth()+80).height(label.getHeight()+16);
		
		addActor(image);
		addActor(label);
		
		setSize(image.getWidth(),image.getHeight());
		
		setColor(1,1,1,0);
		
		float delay = 1 + (float)label.getText().length * .1f;
		
		setPosition(GameUtil.screen_width / 2 - image.getWidth() / 2, 100);
		
		addAction(Actions.sequence(
			Actions.fadeIn(.5f),
			Actions.delay(delay),
			Actions.fadeOut(.5f),
			Actions.run(new Runnable() {
				public void run() {
					remove();
				}
			})
		));
		
		$.add(this).setOrigin(Align.center).children().setOrigin(Align.center);
		
		if(animate){
			setScale(3f);
			proxy = new Actor();
			proxy.setColor(1,1,1,0);
			proxy.addAction(Actions.sequence(
				Actions.delay(.0f),
				Actions.color(new Color(1,1,1,.5f)),
				Actions.parallel(
					Actions.scaleTo(2f, 2f,.4f),
					Actions.fadeOut(.3f)
				)
			));
			
			addAction(Actions.sequence(
					Actions.scaleTo(1, 1 ,.3f),
					Actions.scaleTo(1.2f, 1.2f ,.1f),
					Actions.scaleTo(1, 1 ,.1f)
			));
			
			p.reset();
			p.getEmitters().get(0).getTint().setColors(new float[]{1-color.r,1-color.g,1-color.b});
			p.setPosition(getX()+getWidth()/2, getY()+getHeight()/2);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		
		if(animate){
			proxy.act(Gdx.graphics.getDeltaTime());
			Color pc = proxy.getColor().cpy();
			Color oc = getColor().cpy();
			float ps = proxy.getScaleX();
			float os = getScaleX();
			
			setColor(pc);
			setScale(ps);
			super.draw(batch, parentAlpha);
			setScale(os);
			setColor(oc);
			p.draw(batch,Gdx.graphics.getDeltaTime());
		}
		
		super.draw(batch, parentAlpha);
	}
}
