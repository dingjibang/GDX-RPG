package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;







import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.InputController;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.StackView;
import com.rpsg.rpg.utils.display.TipUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

public class GameMenuView extends StackView{
	Image bluredBG;
	Image normalBG;
	Stage stage;
 	public void init(){
 		this.viewStack.add(new MenuBaseView());
 		this.viewStack.get(0).init();
 		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
 		bluredBG=(Image)(params.get("blurbg"));
 		normalBG=(Image)(params.get("bg"));
 		bluredBG.setColor(1,1,1,0);
 		bluredBG.addAction(Actions.parallel(Actions.color(new Color(0.65f,0.65f,0.65f,1),.5f)));
 		bluredBG.setSize(GameUtil.screen_width, GameUtil.screen_height);
 		
 		Image bg=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"bg.png");
		bg.setPosition(50, 0);
		bg.setColor(1,1,1,0);
		bg.addAction(Actions.fadeIn(0.2f));
		stage.addActor(bg);
		Image magic=Res.get(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"bg_magic.png");
		magic.setPosition(600, 150);
		magic.setColor(1,1,1,0.15f);
		magic.setOrigin(0);
		magic.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(0.1f)));
		stage.addActor(magic);
		
		Table table=new Table();
		table.setBackground(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"leftbar_bg.png"));
		final ImageButton button=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_equip.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_equip_p.png"));
		button.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				tryToAdd(EquipView.class);
				return false;
			}
		});
		table.add(button);
		table.row();
		final ImageButton button2 =new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_sc.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_sc_p.png"));
		button2.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				tryToAdd(SpellCardView.class);
				return false;
			}
		});
		table.add(button2);
		table.row();
		final ImageButton button3 =new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_item.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_item_p.png"));
		button3.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				tryToAdd(ItemView.class);
				return false;
			}
		});
		table.add(button3);
		table.row();
		final ImageButton button4 =new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_status.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_status_p.png"));
		button4.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				tryToAdd(StatusView.class);
				return false;
			}
		});
		table.add(button4);
		table.row();
		final ImageButton button5 =new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_tactic.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_tactic_p.png"));
		table.add(button5);
		table.row();
		final ImageButton button6 =new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_note.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_note_p.png"));
		table.add(button6);
		table.row();
		final ImageButton button7 =new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_system.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"lbut_system_p.png"));
		button7.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				tryToAdd(SystemView.class);
				return false;
			}
		});
		table.add(button7);
		table.getCells().forEach((c)->{
			c.padTop(3);
			c.padBottom(3);
			c.getActor().addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				Music.playSE("snd210");
				return true;
			}
		});
		});
		ScrollPane pane=new ScrollPane(table);
		pane.setPosition(0, 0);
		pane.setSize(128,GameUtil.screen_height);
		pane.setScrollingDisabled(false, true);
		pane.setSmoothScrolling(true);
		pane.addListener(new InputListener() {
			public void touchDragged (InputEvent event, float x, float y, int pointer) {
//				return false;
			}
			public void touchUp (InputEvent event, float x, float y, int pointer,int botton) {
//				return false;
			}
			
			public boolean touchDown (InputEvent event, float x, float y, int pointer,int botton) {
				return true;
			}
		});
		stage.addActor(pane);
 	}
 	
 	public void draw(SpriteBatch batch){
 		bluredBG.act(Gdx.graphics.getDeltaTime());
 		stage.getBatch().begin();
 		bluredBG.draw(stage.getBatch(),bluredBG.getColor().a);
 		stage.getBatch().end();
 		stage.draw();
 		viewStack.get(viewStack.size()-1).draw(batch);
 		TipUtil.draw();
 	}
 	
 	List<View> removeList=new ArrayList<View>();
 	public void logic(){
 		stage.act();
 		removeList.clear();
 		viewStack.get(viewStack.size()-1).logic();
 		for(View view:viewStack){
			if(view.disposed){
				view.dispose();
				removeList.add(view);
			}
		}
		viewStack.removeAll(removeList);
 	}
 	
 	public void onkeyTyped(char character) {
		viewStack.get(viewStack.size()-1).onkeyTyped(character);
	}

	public void onkeyDown(int keyCode) {
		if(viewStack.size()==1 && (Keys.ESCAPE==keyCode || keyCode==Keys.X)){
			this.dispose();
			InputController.currentIOMode=IOMode.MAP_INPUT_NORMAL;
			GameViews.gameview.stackView=null;
		}else{
			viewStack.get(viewStack.size()-1).onkeyDown(keyCode);
		}
	}

	public void onkeyUp(int keyCode) {
		viewStack.get(viewStack.size()-1).onkeyUp(keyCode);
	}

	public void dispose() {
		stage.dispose();
// 		bluredBG.dispose();
// 		normalBG.dispose();
 		MenuController.bg.dispose();
//		MenuControl.pbg.dispose();
 		MenuController.blurbg.dispose();
 		MenuController.bbg.dispose();
		for(View view:viewStack)
			view.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		stage.touchDown(screenX, screenY, pointer, button);
		viewStack.get(viewStack.size()-1).touchDown(screenX, screenY, pointer, button);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		stage.touchUp(screenX, screenY, pointer, button);
		viewStack.get(viewStack.size()-1).touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		stage.touchDragged(screenX, screenY, pointer);
		viewStack.get(viewStack.size()-1).touchDragged(screenX, screenY, pointer);
		return false;
	}
	
	public void tryToAdd(Class<? extends View> iv){
		boolean inc=false;
		for(int i=0;i<viewStack.size();i++){
			Class<? extends View> view=viewStack.get(i).getClass();
			if(view.equals(iv)){
				Collections.swap(viewStack, i, viewStack.size()-1);
				inc=true;
				break;
			}
		}
		if(!inc)
			try {
				viewStack.add(iv.newInstance());
				viewStack.get(viewStack.size()-1).init();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
	}

	@Override
	public boolean scrolled(int amount) {
		stage.scrolled(amount);
		viewStack.get(viewStack.size()-1).scrolled(amount);
		return false;
	}

}
