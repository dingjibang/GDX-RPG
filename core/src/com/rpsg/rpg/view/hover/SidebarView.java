package com.rpsg.rpg.view.hover;

import java.util.Map;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.utils.game.GameUtil;

/**
 * 侧边栏滑动式UI
 * @author dingjibang
 *
 */
public abstract class SidebarView extends HoverView{
	
	WidgetGroup group=new WidgetGroup();
	Image mask;
	
	public HoverView superInit(Map<Object, Object> initParam){
		
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		this.param = initParam;
		
		stage.addActor(mask = new Image(Setting.UI_BASE_IMG).color(1, 1, 1, 0).action(Actions.alpha(.2f,.2f)).size(GameUtil.screen_width, GameUtil.screen_height).position(0, 0).onClick(new Runnable() {
			@Override
			public void run() {
				SidebarView.this.keyDown(Keys.ESCAPE);
			}
		}));
		stage.addActor(group);
		group.pack();
		
		group.setPosition(GameUtil.screen_width-200, 0);

		int width = 0;
		if(initParam!=null && initParam.get("width")!=null)
			width = (Integer)initParam.get("width");
		group.addAction(Actions.moveTo(width, 0,0.4f,Interpolation.pow3));
		
		Image bg;
		group.addActor(bg = Res.get(Setting.IMAGE_MENU_TACTIC + "sup_databox.png").size(784, GameUtil.screen_height).position(240, 0).a(.9f));
		bg.addCaptureListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(x<150)
					mask.click();
				return true;
			}
		});
		
		group.addActor(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"left.png"),Res.getDrawable(Setting.IMAGE_MENU_TACTIC+"left_p.png")).pos(430, 465).onClick(new Runnable() {
			public void run() {
				SidebarView.this.keyDown(Keys.ESCAPE);
			}
		}));
		
		if(initParam!=null && initParam.get("title")!=null){
			group.addActor($.add(Res.get(initParam.get("title"),55)).setPosition(520, 470).getItem());
		}
		
		init();
		return this;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode==Keys.ESCAPE || keycode==Keys.X){
			group.clearActions();
			group.addAction(Actions.sequence(Actions.moveTo(GameUtil.screen_width,0,0.2f,Interpolation.pow3),Actions.after(new Action() {
				public boolean act(float delta) {
					disposed=true;
					return false;
				}
			})));
			
			mask.addAction(Actions.fadeOut(.2f));
		}
		return super.keyDown(keycode);
	}
}
