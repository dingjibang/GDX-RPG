package com.rpsg.rpg.view.menu;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.EmptyAssociation;
import com.rpsg.rpg.object.base.Global;
import com.rpsg.rpg.object.base.Resistance;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.HeroController;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.display.RadarUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;

public class StatusView extends DefaultIView{
	Hero hero=HeroController.getHeadHero();
	int heroIndex=0;
	Group group;
	ScrollPane pane;
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),GameMenuView.stage.getBatch());
		group=new Group();
		group.setHeight(2000);
		tree=new Group();
		tree.setHeight(1200);
		generate();
		pane=new ScrollPane(group);
		pane.setSize(GameUtil.screen_width, GameUtil.screen_height);
		stage.addActor(pane);
		$.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exit.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exitc.png"))).setPosition(960, 550).fadeOut().addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(960, 510,0.1f))).onClick(new Runnable() {
			@Override
			public void run() {
				Music.playSE("snd210");
				GameViews.gameview.stackView.disposes();
			}
		}).appendTo(stage);
		
		$.add(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"min.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"minc.png"))).setPosition(910, 550).fadeOut().addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveTo(910, 510,0.1f))).onClick(new Runnable() {
			@Override
			public void run() {
				GameViews.gameview.stackView.onkeyDown(Keys.ESCAPE);
				Music.playSE("snd210");
			}
		}).appendTo(stage);

		
	}
	Image fg,fgs;
	Group tree;
	public void generate(){
		hero=HeroController.heros.get(heroIndex);
		
		group.clear();
		
		Image lvbox=Res.get(Setting.GAME_RES_IMAGE_MENU_STATUS+"lvbox.png");
		lvbox.setColor(1,1,1,0);
		lvbox.setPosition(160,y(325));
		lvbox.addAction(Actions.parallel(Actions.fadeIn(0.2f),Actions.moveBy(0, 50,0.1f)));
		group.addActor(lvbox);
		
		group.addActor(new Label(hero.prop.get("level")+"",100).setWidth(1000).setPad(-30).align(230,y(115)));
		group.addActor(new Label(hero.name,70).setWidth(1000).align(540, y(90)));
		group.addActor(new Label(hero.jname,20).setPad(-5).setWidth(1000).align(620, y(160)));
		
		group.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_STATUS+"left.png"), Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_STATUS+"left_s.png"))
		.pos(340, y(144)).onClick(new Runnable() {
					@Override
					public void run() {
						StatusView.this.prevHero();
					}
				}));
		group.addActor(new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_STATUS+"right.png"), Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_STATUS+"right_s.png"))
		.pos(770, y(144)).onClick(new Runnable() {
					@Override
					public void run() {
						StatusView.this.nextHero();
					}
				}));
		if(fg!=null) fg.remove();
		if(fgs!=null) fgs.remove();
		fgs= (Image) $.add(Res.get(Setting.GAME_RES_IMAGE_FG+hero.fgname+"/Normal.png").
				color(0,0,0,.5f).scaleX(-0.32f).scaleY(0.32f).position(1300, 0).disableTouch()).setOrigin(Align.bottomLeft).getItem();
		stage.addActor(fgs);
		fg= (Image) $.add(Res.get(Setting.GAME_RES_IMAGE_FG + hero.fgname + "/Normal.png").
				scaleX(-0.32f).scaleY(0.32f).position(1300, 0).disableTouch()).setOrigin(Align.bottomLeft).getItem();;
		stage.addActor(fg);
		fg.setZIndex(1);
		fgs.setZIndex(0);
		Global global=GameViews.global;
		tree.clear();
		tree.setX(100);
		tree.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_STATUS+"/tree.png"));
		
		tree.addActor(new Label(hero.tag,24).setWidth(1000).setPos(405,y(774)));
		tree.addActor(new Label("还需要"+(hero.prop.get("maxexp")-hero.prop.get("exp"))+"点经验升级至等级"+(hero.prop.get("level")+1),24).setWidth(1000).setPos(405,y(828)));
		tree.addActor(new Label("状态正常",24).setWidth(1000).setPos(405,y(928)));
		tree.addActor(new Label(global.gold+"G",22).setWidth(1000).setPad(-5).align(215,y(999)));
		
		tree.addActor(new Label(hero.prop.get("hp")+"/"+hero.prop.get("maxhp"),22).setWidth(1000).setPad(-5).align(595,y(1069)));
		tree.addActor(new Label(hero.prop.get("mp")+"/"+hero.prop.get("maxmp"),22).setWidth(1000).setPad(-5).align(595,y(1128)));
		
		tree.addActor(new Label(TimeUtil.getGameRunningTime(),22).setWidth(1000).setPad(-5).align(220,y(1199)));
		tree.addActor(new Label(TimeUtil.getGameRunningTime(),22).setWidth(1000).setPad(-5).align(220,y(1264)));
		tree.addActor(new Label(TimeUtil.getGameRunningTime(),22).setWidth(1000).setPad(-5).align(220,y(1401)));
		
		tree.addActor(new Label(""+hero.prop.get("attack"),22).setWidth(1000).setPad(-5).align(725,y(1188)));
		tree.addActor(new Label(""+hero.prop.get("magicAttack"),22).setWidth(1000).setPad(-5).align(725,y(1244)));
		tree.addActor(new Label(""+hero.prop.get("defense"),22).setWidth(1000).setPad(-5).align(725,y(1298)));
		tree.addActor(new Label(""+hero.prop.get("magicDefense"),22).setWidth(1000).setPad(-5).align(725,y(1356)));
		tree.addActor(new Label(""+hero.prop.get("speed"),22).setWidth(1000).setPad(-5).align(725,y(1412)));
		tree.addActor(new Label(""+hero.prop.get("hit"),22).setWidth(1000).setPad(-5).align(725,y(1467)));
		
		tree.addActor(new Label(hero.getEquipName(Equipment.EQUIP_WEAPON),22).setWidth(1000).align(182,y(1480)));
		tree.addActor(new Label(hero.getEquipName(Equipment.EQUIP_SHOES),22).setWidth(1000).align(182,y(1533)));
		tree.addActor(new Label(hero.getEquipName(Equipment.EQUIP_CLOTHES),22).setWidth(1000).align(182,y(1587)));
		tree.addActor(new Label(hero.getEquipName(Equipment.EQUIP_ORNAMENT1),22).setWidth(1000).align(182,y(1641)));
		tree.addActor(new Label(hero.getEquipName(Equipment.EQUIP_ORNAMENT2),22).setWidth(1000).align(182,y(1696)));
		
		addT("earth",500,1650);
		addT("fire",608,1620);
		addT("metal",720,1650);
		addT("moon",775,1750);
		addT("physical",447,1747);
		addT("star",467,1857);
		addT("sun",750,1857);
		addT("water",670,1940);
		addT("wood",550,1940);
		
		tree.addActor(new Label(hero.prop.get("chop")==Hero.TRUE?"可":"不可",34).setWidth(1000).align(40,y(1890)));
		tree.addActor(new Label(hero.prop.get("shoot")==Hero.TRUE?"可":"不可",34).setWidth(1000).align(150,y(1890)));
		tree.addActor(new Label(hero.prop.get("prick")==Hero.TRUE?"可":"不可",34).setWidth(1000).align(260,y(1890)));
		
		Image min=Res.get(Setting.GAME_RES_IMAGE_MENU_STATUS+"min.png").position(554, y(1830));
		min.setColor(1,1,1,0);
		min.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(Actions.fadeIn(0.5f),Actions.fadeOut(0.5f))));
		tree.addActor(min);
		
		if(hero.lead){
			RadarUtil.max=100;
			RadarUtil.setRotate(-18);
			RadarUtil.show(new int[]{hero.prop.get("knowledge"),hero.prop.get("perseverance"),hero.prop.get("courage"),hero.prop.get("express"),hero.prop.get("respect")},50,400,400,116);
			tree.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_STATUS+"radar.png").position(212, y(2300)));
			tree.addActor(new Label(hero.prop.get("courage")+"",18).setPad(-5).setWidth(1000).align(265,y(2065)));
			tree.addActor(new Label(hero.prop.get("express")+"",18).setPad(-5).setWidth(1000).align(223,y(2215)));
			tree.addActor(new Label(hero.prop.get("perseverance")+"",18).setPad(-5).setWidth(1000).align(443,y(2067)));
			tree.addActor(new Label(hero.prop.get("knowledge")+"",18).setPad(-5).setWidth(1000).align(490,y(2208)));
			tree.addActor(new Label(hero.prop.get("respect")+"",18).setPad(-5).setWidth(1000).align(354,y(2308)));
		}else{
			RadarUtil.display=false;
			if(!hero.association.getClass().equals(EmptyAssociation.class)){
				tree.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_STATUS+"associationCard.png").position(126, y(2125)));
				tree.addActor(new Label(hero.association.name,55).setWidth(1000).color(1,0.2f,0.1f,1).align(290,y(2023)));
				tree.addActor(new Label(hero.association.level+"",55).setWidth(1000).align(483,y(2023)));
			}
		}
		
		group.addActor(tree);
		
	}
	
	private void addT(String r,int x,int y){
		tree.addActor(getR(r).position(x, y(y)));
		tree.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_STATUS+r+".png").position(x+5, y(y-5)));
		tree.addActor(new Label(getP(r),18).setWidth(1000).align(x+20,y(y-27)));
	}
	
	private String getP(String r){
		Resistance i=hero.resistance.get(r);
		if(i==Resistance.absorb)
			return "吸收";
		else if(i==Resistance.normal)
			return "—";
		else if(i==Resistance.invalid)
			return "无效";
		else if(i==Resistance.reflect)
			return "反射";
		else if(i==Resistance.tolerance)
			return "耐性";
		else
			return "虚弱";
	}
	
	private Image getR(String r){
		Image bg=Res.get(Setting.GAME_RES_IMAGE_MENU_STATUS+"propbg.png");
		Resistance re=hero.resistance.get(r);
		if(re==Resistance.weak){
			bg.color(237f/255f,22f/255f,250f/255f,1);
		}
		if(re==Resistance.reflect){
			bg.color(239f/255f,234f/255f,58f/255f,1);
		}
		if(re==Resistance.invalid){
			bg.color(255f/255f,51f/255f,51f/255f,1);
		}
		if(re==Resistance.tolerance){
			bg.color(98f/255f,255f/255f,50f/255f,1);
		}
		if(re==Resistance.absorb){
			bg.color(102f/255f,153f/255f,255f/255f,1);
		}
		return bg;
	}
	
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	Color green=new Color(219f/255f,255f/255f,219f/255f,1);
	Color cblue=new Color(219f/255f,238f/255f,255f/255f,1);
	public void draw(SpriteBatch batch) {
		sc();
		stage.draw();
		if(hero.lead){
			RadarUtil.setTransformMatrix(stage.getBatch().getTransformMatrix());
			RadarUtil.setProjectionMatrix(stage.getBatch().getProjectionMatrix());
			RadarUtil.draw();
			RadarUtil.setY(pane.getVisualScrollY()-1183);
			RadarUtil.setX(525);
		}
	}
	private void sc(){
		if(pane.getScrollY()>50){
			fg.getActions().clear();
			fg.addAction(Actions.moveTo(1500, 0,0.3f));
			fgs.getActions().clear();
			fgs.addAction(Actions.moveTo(1500, 0,0.3f));
			tree.getActions().clear();
			tree.addAction(Actions.moveTo(165, y(1600),0.2f));
		}else{
			fg.getActions().clear();
			fg.addAction(Actions.moveTo(1120, 0,0.3f));
			fgs.getActions().clear();
			fgs.addAction(Actions.moveTo(1090, 0,0.3f));
			tree.getActions().clear();
			tree.addAction(Actions.moveTo(0, y(1600),0.2f));
		}
	}

	int step=0;
	int frame=0;
	public void logic() {
		stage.act();
		if(++frame==30){
			frame=0;
			if(++step==4)
				step=0;
		}
	}
	
	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode || keyCode==Keys.X){
			this.disposed=true;
		}else
			stage.keyDown(keyCode);
	}

	public void dispose() {
		stage.dispose();
	}
	
	public void nextHero(){
		if(heroIndex!=HeroController.heros.size()-1){
			heroIndex++;
			generate();
			Music.playSE("snd210" );
		}else
			Music.playSE("snd211");
	}
	
	public void prevHero(){
		if(heroIndex!=0){
			heroIndex--;
			generate();
			Music.playSE("snd210");
		}else
			Music.playSE("snd211");
	}
	
	private int y(int y){
		return (int) (group.getHeight()-y);
	}
}
