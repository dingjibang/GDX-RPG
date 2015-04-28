package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.controller.HoverController;
import com.rpsg.rpg.system.ui.CheckBox;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.Slider;
import com.rpsg.rpg.system.ui.Slider.SliderStyle;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.CheckBox.CheckBoxStyle;
import com.rpsg.rpg.system.ui.TextButton.TextButtonStyle;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.ConfirmView;
import com.rpsg.rpg.view.hover.SaveView;

public class TacticView extends DefaultIView {
	int page=2;
	WidgetGroup group;
	public void init() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
		group=new WidgetGroup();
		group.setSize(GameUtil.screen_width*page,  GameUtil.screen_height);
		stage.addActor(group);
		
		generateHeroImage();
		
		ImageButton exit = new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL + "exit.png"), Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL + "exitc.png"));
		exit.setPosition(960, 550);
		exit.addAction(Actions.moveTo(960, 510, 0.1f));
		exit.addListener(new InputListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int b) {
				disposed = true;
			}

			public boolean touchDown(InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(exit);
	}

	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb = (SpriteBatch) stage.getBatch();
		sb.begin();
		sb.end();
	}

	public void logic() {
		stage.act();
	}

	public void onkeyDown(int keyCode) {
		if (Keys.ESCAPE == keyCode || keyCode == Keys.X) {
			this.disposed = true;
		} else
			stage.keyDown(keyCode);
	}

	public void dispose() {
		stage.dispose();
	}
	
	ArrayList<HeroImg> imglist;
	private void generateHeroImage() {
		for(int i=0;i<4;i++){
			Hero hero=null;
			try{
				hero=HeroController.heros.get(i);
			}catch(Exception e){}
			imglist=new ArrayList<HeroImg>();
			imglist.add(new HeroImg(hero,i));
		}
	}
	
	class HeroImg{
		Hero hero;
		int idx;
		public HeroImg(Hero hero,int idx){
			this.hero=hero;
			this.idx=idx;
			group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_herobg.png").position(212*idx+174, 170).onClick(()->{
				Iterator<Actor> it=group.getChildren().iterator();
				while(it.hasNext()){
					Actor a=it.next();
					if(a.getUserObject()!=null && a.getUserObject() instanceof HeroImgMask)
						it.remove();
				}
				group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_heroselbox.png").position(212*idx+171, 167).disableTouch().object(new HeroImgMask()).action(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE,0.5f),Actions.color(new Color(1,1,1,0.5f),0.5f)))));
				group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_herosel.png").position(212*idx+174, 170).disableTouch().object(new HeroImgMask()));
				if(hero.linkTo!=null && getIDX(hero.linkTo)!=-1){
					int tmpIDX=getIDX(hero.linkTo);
					group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_herosel.png").position(212*idx+174, 170).disableTouch().object(new HeroImgMask()));
				}
			}));
			if(hero!=null){
				group.addActor(Res.get(Setting.GAME_RES_IMAGE_FG+hero.fgname+"/head.png").position(212*idx+174, 170).disableTouch());
			}
			group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_mask.png").position(212*idx+174, 170).disableTouch());
			group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_level.png").position(212*idx+174+7, 177).color((hero!=null && hero.lead)?Color.valueOf("cc3333"):Color.valueOf("528431")).disableTouch());
			group.addActor(new Label(hero!=null?hero.name:"", 28).setWidth(1000).align(212*idx+247, 240));
			group.addActor(new Label(hero!=null?(hero.lead?"LEADER":"Level "+hero.association.level):"<空>", 22).setPad(-7).setWidth(1000).align(212*idx+252, 202));
		}
		
		int getIDX(Hero hero){
			for(HeroImg img:imglist)
				if(img.hero==hero)
					return img.idx;
			return -1;
		}
	}
	
	class HeroImgMask{}

}
