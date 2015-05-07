package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.TextButton.TextButtonStyle;
import com.rpsg.rpg.utils.game.GameUtil;

public class TacticView extends DefaultIView {
	int page=2;
	WidgetGroup group;
	TextButtonStyle butstyle;
	ParticleEffect eff;
	Image linkerc,linkerl,linkerr;
	Image linkbox1,linkbox2;
	public void init() {
		eff=new ParticleEffect();
		eff.load(Gdx.files.internal(Setting.GAME_RES_PARTICLE+"link.p"),Gdx.files.internal(Setting.GAME_RES_PARTICLE));
		
		linkerc=Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_effect.png").disableTouch();
		linkerl=Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_effect_left.png").disableTouch();
		linkerr=Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_effect_right.png").disableTouch();
		
		linkbox1=Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"linking_heroselbox2.png").disableTouch();
		linkbox2=Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"linking_heroselbox2.png").disableTouch();
		
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
		butstyle=new TextButtonStyle();
		butstyle.down=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_but_active.png");
		butstyle.up=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_but.png");
		
		group=new WidgetGroup();
		group.setSize(GameUtil.screen_width*page,  GameUtil.screen_height);
		stage.addActor(group);
		
		stage.addActor(linkerc.color(1,1,1,0));
		stage.addActor(linkerl.color(1,1,1,0));
		stage.addActor(linkerr.color(1,1,1,0));
		stage.addActor(linkbox1.color(1,1,1,0));
		stage.addActor(linkbox2.color(1,1,1,0));
		
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
	int speeda=8;
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb = (SpriteBatch) stage.getBatch();
		sb.begin();
		if(linkeff){
			int speed=speeda++;
			if((left+=speed) > right){
				linkeff=false;
				eff.allowCompletion();
				linkerc.action(Actions.fadeOut(0.2f));
				linkerl.action(Actions.fadeOut(0.2f));
				linkerr.action(Actions.fadeOut(0.2f));
				linkbox1.action(Actions.fadeOut(0.2f));
				linkbox2.action(Actions.fadeOut(0.2f));
				speeda=8;
			}else{
				if(eff.isComplete())
					eff.reset();
				linkerc.setWidth(linkerc.getWidth()+speed);
				linkerr.setPosition(left+linkerl.getWidth(), 265);
				eff.setPosition(left, 270);
			}
		}
		eff.draw(batch, Gdx.graphics.getDeltaTime());
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
		eff.dispose();
	}
	
	ArrayList<HeroImg> imglist;
	private void generateHeroImage() {
		imglist=new ArrayList<HeroImg>();
		for(int i=0;i<4;i++){
			Hero hero=null;
			try{
				hero=HeroController.heros.get(i);
			}catch(Exception e){}
			imglist.add(new HeroImg(hero,i));
		}
	}
	HeroImg currentLinking;
	class HeroImg{
		Hero hero;
		int idx;
		HeroImg that = this;
		public HeroImg(Hero hero,int idx){
			this.hero=hero;
			this.idx=idx;
			Image bg=Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_herobg.png");
			group.addActor(bg.position(212*idx+174, 170).onClick(()->{
				if((currentLinking==null) || (currentLinking!=null && currentLinking.hero!=that.hero && that.hero.linkTo==null)){
					Iterator<Actor> it=group.getChildren().iterator();
					while(it.hasNext()){
						Actor a=it.next();
						if(currentLinking==null){
							if(a.getUserObject()!=null && a.getUserObject() instanceof HeroImgMask)
								it.remove();
						}else{
							if(a.getUserObject()!=null && a.getUserObject() instanceof HeroImgMask2)
								it.remove();
						}
					}
					if(currentLinking==null){
						group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_heroselbox.png").position(212*idx+171, 167).disableTouch().object(new HeroImgMask3()).action(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE,0.5f),Actions.color(new Color(1,1,1,0.5f),0.5f)))));
						group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_herosel.png").position(212*idx+174, 170).disableTouch().object(new HeroImgMask()));
					}else{
						group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_herosel.png").position(212*idx+174, 170).disableTouch().object(new HeroImgMask2()));
					}
					if(hero.linkTo!=null && getIDX(hero.linkTo)!=-1){
						int tmpIDX=getIDX(hero.linkTo);
						group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"linked_herosel.png").position(212*tmpIDX+174, 170).disableTouch().object(new HeroImgMask()));
						group.addActor(new Label("连携中", 35).setPos(212*tmpIDX+190, 305).userObj(new HeroImgMask()).setWidth(1000));
						group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"linking_heroselbox.png").position(212*tmpIDX+118, 124).disableTouch().object(new HeroImgMask3()).color(Color.valueOf("ffffff77")));
						TextButton but=new TextButton("取消连携关系", butstyle,18);
						but.setUserObject(new HeroImgMask());
						but.setPosition(212*idx+189, 264);
						but.offset=4;
						but.hof=-2;
						but.onClick(()->{
							that.hero.linkTo.linkTo=null;
							that.hero.linkTo=null;
							bg.click();
						});
						group.addActor(but);
					}else{
						TextButton but=new TextButton(currentLinking==null?"连携此角色":"连携此角色", butstyle,18);
						but.setUserObject(currentLinking==null?new HeroImgMask():new HeroImgMask2());
						but.setPosition(212*idx+189, 264);
						but.offset=4;
						but.hof=-2;
						Runnable fin=()->{
							currentLinking=null;
							Iterator<Actor> it2=group.getChildren().iterator();
							while(it2.hasNext()){
								Actor a=it2.next();
								if(a.getUserObject()!=null && a.getUserObject() instanceof HeroImgMask2)
									it2.remove();
							}
							bg.click();
						};
						but.onClick(currentLinking==null?()->{
							but.setText("取消选择");
							setLinkBorder();
							but.setUserObject(new HeroImgMask());
							currentLinking=that;
							but.offset=8;
							but.onClick(fin);
							for(HeroImg img:imglist){
								if(img!=that && img.hero.linkTo==null){
									group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"linking_heroselbox.png").position(212*img.idx+118, 124).disableTouch().object(new HeroImgMask3()).color(Color.valueOf("0660f600")).action(Actions.fadeIn(0.3f)));
								}else if(img.hero.linkTo!=null){
									group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_disable.png").position(212*img.idx+174, 170).disableTouch().object(new HeroImgMask()).color(1,1,1,0).action(Actions.fadeIn(0.3f)));
								}
							}
						}:()->{
							currentLinking.hero.linkTo=that.hero;
							that.hero.linkTo=currentLinking.hero;
							linkEffect(idx,getIDX(that.hero.linkTo));
							fin.run();
						});
						group.addActor(but);
					}
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
		
		void setLinkBorder(){
			group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_TACTIC+"link_heroselbox.png").position(212*idx+171, 167).disableTouch().object(new HeroImgMask()).action(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.color(Color.WHITE,0.5f),Actions.color(new Color(1,1,1,0.5f),0.5f)))).object(new HeroImgMask2()));
		}
	}
	
	int getIDX(Hero hero){
		for(HeroImg img:imglist)
			if(img.hero.equals(hero))
				return img.idx;
		return -1;
	}
	
	boolean linkeff=false;
	int left,right;
	void linkEffect(int idx2, int idx3) {
		if(!linkeff){
			linkeff=true;
			left=212*(idx2<idx3?idx2:idx3)+200;
			right=212*(idx2<idx3?idx3:idx2)+240;
			eff.reset();
			eff.setPosition(left, 270);
			linkerl.setPosition(left, 265);
			linkerr.setPosition(left+linkerl.getWidth(), 265);
			linkerc.setPosition(left+linkerl.getWidth(), 265);
			linkerc.setWidth(0);
			linkerl.action(Actions.fadeIn(0.4f)).color(1,1,1,0);
			linkerr.action(Actions.fadeIn(0.4f)).color(1,1,1,0);
			linkerc.action(Actions.fadeIn(0.4f)).color(1,1,1,0);
			linkbox1.position(left-81, 124).color(Color.valueOf("7eff4500")).action(Actions.fadeIn(0.2f));
			linkbox2.position(right-121, 124).color(Color.valueOf("ff458200")).action(Actions.fadeIn(0.2f));
		}
		
	}
	
	class HeroImgMask{}
	class HeroImgMask2{}
	class HeroImgMask3 extends HeroImgMask{}
}
