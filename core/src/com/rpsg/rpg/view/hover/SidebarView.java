package com.rpsg.rpg.view.hover;

import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;

/**
 * 侧边栏滑动式UI
 * @author dingjibang
 *
 */
public abstract class SidebarView extends HoverView{
	
	WidgetGroup group=new WidgetGroup();
	boolean enableXKey = true;
	Image mask;
	int count,width;
	
	public HoverView superInit(Map<Object, Object> initParam){
		WidgetGroup base = new WidgetGroup();
		
		group.addActor(base);
		
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		this.param = initParam;
		
		stage.addActor(mask = new Image(Setting.UI_BASE_IMG).color(1, 1, 1, 0).action(Actions.alpha(.2f,.2f)).size(GameUtil.screen_width, GameUtil.screen_height).position(0, 0).onClick(()->SidebarView.this.keyDown(Keys.ESCAPE)));
		stage.addActor(group);
		group.pack();
		
		group.setPosition(GameUtil.screen_width-200, 0);

		if(initParam!=null && initParam.get("width")!=null)
			width = (Integer)initParam.get("width");
		group.addAction(Actions.moveTo(width, 0,0.4f,Interpolation.pow3));
		
		Image bg;
		base.addActor(bg = Res.get(Setting.IMAGE_MENU_TACTIC + "sup_databox.png").size(784, GameUtil.screen_height).position(240, 0).a(.9f));
		bg.addCaptureListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(x<150)
					mask.click();
				return true;
			}
		});
		
		ImageButton closeButton;
		base.addActor(closeButton = new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"left.png"),Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"left_p.png")).pos(430, 465).onClick(() -> SidebarView.this.keyDown(Keys.ESCAPE)));
		
		Label title;
		base.addActor($.add(title = Res.get((initParam!=null && initParam.get("title")!=null) ? initParam.get("title") : "",55)).setPosition(520, 470).getItem());
		
		init();
		
		$.add(group).children().not(base).add(title,closeButton).each(new CustomRunnable<Actor>() {
			public void run(Actor t) {
				int ran = (int) (-120 - width - ++count * 70);
				float a = t.getColor().a;
				t.addAction(Actions.sequence(Actions.moveBy(-ran, 0,0f),Actions.moveBy(ran, 0,0.85f,Interpolation.pow4Out)));
				t.addAction(Actions.sequence(Actions.fadeOut(0f),Actions.alpha(a,0.4f,Interpolation.pow4)));
			}
		});
		
		return this;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==Keys.ESCAPE || (keycode==Keys.X && enableXKey)){
			close();
		}
		return super.keyDown(keycode);
	}
	
	public void close(final Runnable callback){
		group.clearActions();
		group.addAction(Actions.sequence(Actions.moveTo(GameUtil.screen_width,0,0.2f,Interpolation.pow3),Actions.after(new Action() {
			public boolean act(float delta) {
				disposed=true;
				if(callback != null) callback.run();
				return false;
			}
		})));
		
		mask.addAction(Actions.fadeOut(.2f));
	}
	
	public void close(){
		close(null);
	}
}
