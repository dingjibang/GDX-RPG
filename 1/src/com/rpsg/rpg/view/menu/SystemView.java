package com.rpsg.rpg.view.menu;


import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.CheckBox;
import com.rpsg.rpg.system.base.CheckBox.CheckBoxStyle;
import com.rpsg.rpg.system.base.DefaultIView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.Label;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.base.TextButton;
import com.rpsg.rpg.system.base.TextButton.TextButtonStyle;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.TimeUtil;
import com.rpsg.rpg.view.GameViews;

public class SystemView extends DefaultIView{
	Label lvl5;
	public void init() {
		
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		Table table = new Table();
		
		TextButtonStyle butstyle=new TextButtonStyle();
		butstyle.over=butstyle.checkedOver=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button_hover.png");
		butstyle.down=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button_active.png");
		butstyle.up=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button.png");
		
		CheckBoxStyle cs=new CheckBoxStyle();
		cs.checkboxOff=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"optb_s.png");
		cs.checkboxOn=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"optb.png");
		
		WidgetGroup group=new WidgetGroup();
		group.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"savebar.png"));
		Label lvl=new Label("LV "+GameViews.global.level,40).setWidth(1000).setPad(-15);
		lvl.setPosition(350, 190);
		group.addActor(lvl);
		Label lvl2=new Label("【"+(String)GameViews.gameview.map.getProperties().get("name")+"】",40).setWidth(1000).setPad(0);
		lvl2.setPosition(470, 190);
		group.addActor(lvl2);
		Label lvl3=new Label("档案所在位置：",18).setWidth(1000).setPad(0);
		lvl3.setPosition(360, 140);
		group.addActor(lvl3);
		Label lvl4=new Label(" ["+HeroControler.getHeadHero().mapx+","+HeroControler.getHeadHero().mapy+"]",18).setWidth(1000).setPad(0);
		lvl4.setPosition(470, 140);
		group.addActor(lvl4);
		Label lvl5h=new Label("档案进行时间：",18).setWidth(1000).setPad(0);
		lvl5h.setPosition(360, 110);
		group.addActor(lvl5h);
		lvl5=new Label(TimeUtil.getGameRunningTime(),18).setWidth(1000).setPad(-3);
		lvl5.setPosition(490, 110);
		group.addActor(lvl5);
		TextButton sbutton=new TextButton("保存游戏", butstyle).onClick(()->{
		});
		sbutton.setOffset(34).setSize(250,60);
		sbutton.setPosition(180, 14);
		group.addActor(sbutton);
		TextButton sbutton2=new TextButton("读取游戏", butstyle).onClick(()->{
		});
		sbutton2.setOffset(34).setSize(250,60);
		sbutton2.setPosition(444, 14);
		group.addActor(sbutton2);
		TextButton sbutton3=new TextButton("回到菜单", butstyle).onClick(()->{
			GameViews.state=GameViews.STATE_LOGO;
			GameViews.gameview.dispose();
			GameViews.gameview=null;
		});
		sbutton3.setOffset(34).setSize(250,60);
		sbutton3.setPosition(710, 14);
		group.addActor(sbutton3);
		Image screens = (Image)GameViews.gameview.stackView.params.get("bg");
		screens.setSize(172,97);
		screens.setPosition(182, 91);
		group.addActor(screens);
		table.add(group).prefSize(1024,329);
		table.row();
		
		WidgetGroup group2=new WidgetGroup();
		group2.addActor(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"graphics.png"));
		CheckBox box=new CheckBox("", cs,22);
		box.setPosition(190,484);
		group2.addActor(box.onClick(()->{
			System.out.println(box.isChecked());
		}));
		CheckBox box2=new CheckBox("", cs,22);
		box2.setPosition(190,357);
		group2.addActor(box2.onClick(()->{
		}));
		CheckBox box3=new CheckBox("", cs,22);
		box3.setPosition(190,252);
		group2.addActor(box3.onClick(()->{
		}));
		CheckBox box4=new CheckBox("", cs,22);
		box4.setPosition(190,169);
		group2.addActor(box4.onClick(()->{
		}));
		CheckBox box5=new CheckBox("", cs,22);
		box5.setPosition(190,59);
		group2.addActor(box5.onClick(()->{
		}));
		table.add(group2).prefSize(1024, 600);
		table.row();
		
		table.add(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"performance.png"));
		table.row();
		table.add(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"sound.png"));
		table.row();
		table.add(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"game.png"));
		table.row();
		table.add(Res.get(Setting.GAME_RES_IMAGE_MENU_SYSTEM+"about.png"));
		table.getCells().forEach((c)->c.padTop(40).padBottom(50));
//		table.setDebug(true);
//		group.setSize(table.getWidth(), table.getHeight());
		
		
		ScrollPane pane=new ScrollPane(table);
		pane.setSize(1024,576);
		pane.validate();
		pane.layout();
		stage.addActor(pane);
//		Image bg=Res.get(Setting.GAME_RES_IMAGE_MENU_ITEM+"item_bg.png");
//		bg.setColor(1,1,1,0);
//		bg.setPosition(160,28);
//		bg.addAction(Actions.fadeIn(0.2f));
//		stage.addActor(bg);
		
		ImageButton exit=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exit.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_GLOBAL+"exitc.png"));
		exit.setPosition(960, 550);
		exit.addAction(Actions.moveTo(960, 510,0.1f));
		exit.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				disposed=true;
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(exit);
	}
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	Color green=new Color(219f/255f,255f/255f,219f/255f,1);
	Color cblue=new Color(219f/255f,238f/255f,255f/255f,1);
	
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		sb.begin();
		sb.end();
	}

	public void logic() {
		stage.act();
		lvl5.setText(TimeUtil.getGameRunningTime());
	}
	
	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode){
			this.disposed=true;
		}else
			stage.keyDown(keyCode);
	}

	public void dispose() {
		stage.dispose();
	}

	
}
