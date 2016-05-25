package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.utils.game.GameUtil;

public class Toast extends Table {

	String msg;
	Color color;
	int fontSize;
	boolean animate;
	Image icon;

	Actor proxy;
	static ParticleEffect p;
	boolean startP = false;
	
	public boolean generated = false;

	public static void init() {
		p = new ParticleEffect();
		p.load(Gdx.files.internal(Setting.PARTICLE + "toast.p"), Gdx.files.internal(Setting.PARTICLE));
		p.getEmitters().get(0).setAligned(true);// 并没有卵用？！
	}

	public Toast(String msg, Color color, int fontSize) {
		this(msg, color, fontSize, false, null);
	}

	public Toast(String msg, Color color) {
		this(msg, color, 22);
	}

	public Toast(String msg) {
		this(msg, Color.WHITE, 22);
	}

	public Toast(String msg, final Color color, int fontSize, boolean animate, Image icon) {
		setTransform(true);
		this.animate = animate;
		this.msg = msg;
		this.color = color;
		this.fontSize = fontSize;
		this.icon = icon;
	}

	public Toast generate() {
		generated = true;
		
		Label label = new Label(msg, fontSize).position(icon == null ? 40 : 90, 8).warp(true).align(Align.center).markup(true);

		setBackground(Setting.UI_TOAST);

		Cell<? extends Actor> c_icon = null, c_label;

		if (icon != null)
			c_icon = add(icon).size(75, 75).pad(15).padRight(20);

		c_label = add(label).size(label.getWidth(), label.getHeight()).pad(15);

		setWidth(c_label.getMaxWidth() + 40);
		setHeight(c_label.getMaxHeight() + 25);

		if (icon != null) {
			setWidth(getWidth() + c_icon.getMaxWidth() + 70);
			if (c_label.getMaxHeight() >= c_icon.getMaxHeight())
				setHeight(c_label.getMaxHeight() + 30);
			else
				setHeight(c_icon.getMaxHeight() + 30);
		}

		// addActor(toastImage);
		// addActor(label);

		setPosition(GameUtil.stage_width / 2 - getWidth() / 2, 100);

		setColor(1, 1, 1, 0);

		float delay = 0 + (float) label.getText().length * .1f;
		delay = delay > 5 ? 5 : delay;

		addAction(Actions.sequence(Actions.fadeIn(.5f), Actions.delay(delay), Actions.fadeOut(.5f), Actions.run(new Runnable() {
			public void run() {
				remove();
			}
		})));

		$.add(this).setOrigin(Align.center).children().setOrigin(Align.center);

		if (animate) {
			setScale(3f);
			proxy = new Actor();
			proxy.setColor(1, 1, 1, 0);
			proxy.addAction(Actions.sequence(Actions.delay(.35f), Actions.color(new Color(1, 1, 1, .9f)), Actions.parallel(Actions.scaleTo(2f, 2f, .5f), Actions.fadeOut(.5f))));

			addAction(Actions.scaleTo(1, 1, 1.7f, Interpolation.elasticOut));

			addAction(Actions.sequence(Actions.delay(.1f), Actions.run(new Runnable() {
				public void run() {
					p.reset();
					p.getEmitters().get(0).getTint().setColors(new float[] { 1 - color.r, 1 - color.g, 1 - color.b });
					p.setPosition(getX() + getWidth() / 2, getY() + getHeight() / 2);
					startP = true;
				}
			})));
		}
		return this;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		if (animate) {
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
			if (startP)
				p.draw(batch, Gdx.graphics.getDeltaTime());
		}

		// if(icon!=null)
		// icon.color(getColor()).draw(batch,parentAlpha);

		super.draw(batch, parentAlpha);
	}
}
