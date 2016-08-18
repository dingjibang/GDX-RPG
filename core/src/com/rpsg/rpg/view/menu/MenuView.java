package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.GdxFrame;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.gdxQuery.GdxQueryRunnable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.IOMode;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.controller.MenuController;
import com.rpsg.rpg.system.controller.MenuController.Menu;
import com.rpsg.rpg.system.ui.MenuCheckBox;
import com.rpsg.rpg.system.ui.IMenuView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.MenuHeroBox;
import com.rpsg.rpg.system.ui.StackView;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameDate.Time;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;
import com.rpsg.rpg.view.hover.LoadView;
import com.rpsg.rpg.view.hover.SaveView;
//基本菜单视图
public class MenuView extends StackView{
	
	public static Stage stage;
	GdxFrame frames;
	public Hero current;
	List<GdxQuery> boxs;
	boolean status=false;
	@Override
	public View init() {
		
		//TODO for debug
		RPG.ctrl.task.forceStop(1);
		RPG.ctrl.task.endAchievement(1);
		
		final WidgetGroup leftBar;//左边栏
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.stage_width, GameUtil.stage_height, new OrthographicCamera()));
		//左边栏相关
		$.add(leftBar=new WidgetGroup()).appendTo(stage).setPosition(-500, 0).addAction(Actions.moveTo(0, 0,0.3f,Interpolation.pow2Out));
		final Actor hr;//左边栏hr下方
		final Actor exit;
		final WidgetGroup fgGroup=(WidgetGroup) $.add(new WidgetGroup()).appendTo(stage).getItem();//右侧
		$.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"bg.png")).setHeight(1024).setPosition(0, 0).appendTo(leftBar);
		final WidgetGroup ld=(WidgetGroup) $.add(new WidgetGroup()).appendTo(leftBar).getItem();
		$.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"ico_pos.png")).setPosition(-100, 330).appendTo(ld).addAction(Actions.moveTo(35, 330,0.55f,Interpolation.pow2Out));
		$.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"ico_gold.png")).setPosition(-100, 275).appendTo(ld).addAction(Actions.moveTo(35, 275,0.55f,Interpolation.pow2Out));
		$.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"ico_flag.png")).setPosition(-100, 212).appendTo(ld).addAction(Actions.moveTo(35, 212,0.55f,Interpolation.pow2Out));
		frames=$.add($.add(new Label("",24).position(0, 548)).appendTo(ld).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.3f),Actions.moveTo(150,545,0.5f))),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText(/*RPG.global.tyear+"/"+*/RPG.global.date.getMonth()+"月"+RPG.global.date.getDay()+"日");
		}});
		frames.add($.add(new Label("",24).right().position(0, 548)).appendTo(ld).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.3f),Actions.moveTo(400,545,0.5f,Interpolation.pow2Out))),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText(((RPG.global.date.getTime()==Time.DAY?"上午":(RPG.global.date.getTime()==Time.NIGHT?"夜晚":"黄昏"))+" "+RPG.ctrl.weather.getName()));
		}}); 
		frames.add($.add(new Label("",18).right().position(400, 512)).appendTo(ld).setColor(1,1,1,0).addAction(Actions.fadeIn(0.7f)),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("游戏已进行"+RPG.time.getGameRunningTime());
		}});
		$.add(hr=Res.get(Setting.IMAGE_MENU_GLOBAL+"hr.png")).setPosition(-200, 490).appendTo(leftBar).setColor(1,1,1,0).addAction(Actions.delay(0.2f, Actions.parallel(Actions.fadeIn(0.1f),Actions.moveTo(20, 490,0.1f))));
		frames.add($.add(new Label("",24)).setPosition(-300, 357).appendTo(ld).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.3f),Actions.moveTo(75,357,0.5f,Interpolation.pow2Out))),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText((String)RPG.maps.getName()+" [ "+RPG.ctrl.hero.getHeadHero().mapx+" , "+RPG.ctrl.hero.getHeadHero().mapy+" ]");
		}});
		frames.add($.add(new Label("",24)).setPosition(-300,302).appendTo(ld).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.3f),Actions.moveTo(75,302,0.5f,Interpolation.pow2Out))),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("持有 "+RPG.global.gold+" 金币");
		}});
		frames.add($.add(new Label("",24)).setPosition(-300, 245).appendTo(ld).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.3f),Actions.moveTo(75,245,0.5f,Interpolation.pow2Out))),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("任务模块制作中");
		}});
		frames.add($.add(new Label("",16)).setPosition(-300, 215).appendTo(ld).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.3f),Actions.moveTo(80,215,0.5f,Interpolation.pow2Out))),new GdxQueryRunnable() {public void run(GdxQuery self) {
			((Label)self.getItem()).setText("任务模块制作中");
		}});
		$.add(exit=new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"ico_exit.png"),Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"ico_exit_p.png"))).setPosition(-100, 510).fadeOut().addAction(Actions.parallel(Actions.fadeIn(0.5f),Actions.moveTo(20, 510,0.6f,Interpolation.pow2Out))).click(new Runnable() {
			public void run() {
				Music.playSE("snd210.wav");
				onkeyDown(Keys.ESCAPE);
			}
		}).appendTo(leftBar);
		final Label menuLabel=new Label("", 32);
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_GLOBAL+"btn_more.png"))).click(new Runnable() {public void run() {
			if(status) return;
			status=true;
			ld.addAction(Actions.parallel(Actions.moveTo(-500, 0,0.5f,Interpolation.exp5),Actions.fadeOut(0.2f)));
			leftBar.addAction(Actions.moveTo(-230,0,0.5f,Interpolation.pow4));
			exit.addAction(Actions.moveTo(255, 510,0.5f,Interpolation.pow4));
			fgGroup.addAction(Actions.moveTo(115,0,0.5f,Interpolation.pow4Out));
			$.add(fgGroup).children().find("card").addAction(Actions.parallel(Actions.moveTo(730, 80,0.5f,Interpolation.pow4Out),Actions.fadeOut(0.3f)));
			hr.addAction(Actions.parallel(Actions.sizeTo(147, hr.getHeight(),0.2f),Actions.moveTo(250, 490,0.3f)));
			$.add(menuLabel.text("状态")).setPosition(-190, 535).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.5f),Actions.moveTo(325,535,0.3f))).appendTo(leftBar);
			
			final GdxQuery table=$.add(new Table()).appendTo($.add(new ScrollPane(null)).appendTo(stage).setPosition(-250, 0).setSize(220, 475).addAction(Actions.moveTo(-7,0,0.5f,Interpolation.pow4)).getItem());
			
			CheckBoxStyle cstyle=new CheckBoxStyle();
			cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png");
			cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"menu_button_select.png");
			cstyle.font=Res.font.get(22);
			for(int i=0;i<MenuController.generate().size();i++){
				final boolean firstFlag=i==0;
				final Menu currentMenu=MenuController.generate().get(i);
				$.add(new MenuCheckBox("", cstyle).setFgOff(0).foreground(Res.getNP(Setting.IMAGE_MENU_GLOBAL+"m_"+currentMenu.fileName+".png"))).appendTo(table.getItem()).run(new GdxQueryRunnable() {public void run(final GdxQuery self) {
					self.click(()->{
						for(Actor box:table.children().getItems())
							$.add(box).notUserObject(self.getItem()).setChecked(false).addAction(Actions.moveTo(21, box.getY(),0.1f,Interpolation.pow4)).setDisabled(false).run(new GdxQueryRunnable() {public void run(GdxQuery self2) {
								if(self2.length()!=0) ((MenuCheckBox)self2.getItem()).setOther(null);
							}});
						((MenuCheckBox)self.getItem()).setOther(Res.getNP(Setting.IMAGE_MENU_GLOBAL+"menu_button_box.png")).setOtherPosition(166, -12);
						self.cleanActions().addAction(Actions.moveTo(31, self.getY(),0.2f,Interpolation.pow4Out)).setDisabled(true).setChecked(true);
						menuLabel.text(currentMenu.name);
						if(currentMenu.view != null){
							tryToAdd(currentMenu.view);
							fgGroup.clearActions();
							if(!currentMenu.view.equals(StatusView.class))
								fgGroup.addAction(Actions.parallel(Actions.moveTo(500,0),Actions.fadeOut(0)));
							else
								fgGroup.addAction(Actions.parallel(Actions.moveTo(115,0,0.6f,Interpolation.pow4Out),Actions.fadeIn(0.5f)));
						}
					});
					((Table)table.getItem()).getCell(self.getItem()).padTop(5).padBottom(5).prefSize(179,76);
					self.addAction(Actions.delay(0.4f,Actions.run(()->{
						if(firstFlag) self.click();
					})));
				}}).setSize(179, 76);
			}
		}}).appendTo(ld).setSize(370, 50).setPosition(-100, 20).addAction(Actions.moveTo(23, 20, .5f,Interpolation.pow2Out)).getCell().prefSize(370,50);
		
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_GLOBAL+"btn_save.png"))).click(new Runnable() {public void run() {
			RPG.popup.add(SaveView.class);
		}}).appendTo(ld).setSize(172, 76).setPosition(-100, 90).addAction(Actions.moveTo(23, 90, 0.5f,Interpolation.pow2Out)).getCell().prefSize(172,76);
		$.add(new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(Res.get(Setting.IMAGE_MENU_GLOBAL+"btn_load.png"))).click(new Runnable() {public void run() {
			RPG.popup.add(LoadView.class);
		}}).appendTo(ld).setSize(172, 76).setPosition(0, 90).addAction(Actions.moveTo(221, 90, 0.5f,Interpolation.pow2Out)).getCell().prefSize(172,76);
		boxs=new ArrayList<GdxQuery>();//角色框
		
		for(int i=0;i<RPG.ctrl.hero.currentHeros.size();i++)
			boxs.add($.add(new MenuHeroBox(RPG.ctrl.hero.currentHeros.get(i))).appendTo(ld).setPosition(-i*100, 400).addAction(Actions.moveTo(i*100+25, 400,0.7f,Interpolation.pow2Out)).run(self-> self.click(()->{
				if(current==null || current!=((MenuHeroBox)self.getItem()).hero){
					for(GdxQuery _box:boxs)
						((MenuHeroBox) _box.getItem()).setSelect(false);
					((MenuHeroBox) self.getItem()).setSelect(true);
					$.add(fgGroup).children().removeAll();
					Hero hero=((MenuHeroBox) self.getItem()).hero;
					Image dfg = hero.defaultFG();
					$.add(hero.defaultFG()).appendTo(fgGroup).setScaleX(-0.33f).setScaleY(0.33f).setOrigin(Align.bottomLeft).setPosition(1200, 0).setColor(0,0,0,0).addAction(Actions.parallel(Actions.color(new Color(0,0,0,0.3f),1f),Actions.moveTo(GameUtil.stage_width - 120 + (dfg.getWidth() / 10), 0,0.75f,Interpolation.pow2Out)));
					$.add(hero.defaultFG()).appendTo(fgGroup).setScaleX(-0.33f).setScaleY(0.33f).setOrigin(Align.bottomLeft).setPosition(1200, 0).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.5f),Actions.moveTo(GameUtil.stage_width - 150 + (dfg.getWidth() / 10), 0,0.7f,Interpolation.pow2Out)));
					if(hero.target.getProp("dead") == (Target.TRUE)) $.add(Res.get(Setting.IMAGE_MENU_GLOBAL+"dead.png")).appendTo(fgGroup).setOrigin(Align.bottomLeft).setPosition(1200, 5).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.5f),Actions.moveTo(650, 50,0.7f,Interpolation.pow2Out)));
					if(!status) $.add(Res.get(Setting.IMAGE_FG+hero.fgname+"/card.png")).appendTo(fgGroup).setOrigin(Align.bottomLeft).setPosition(1200, 80).setColor(1,1,1,0).addAction(Actions.parallel(Actions.fadeIn(0.6f),Actions.moveTo(520, 80,0.6f,Interpolation.pow2Out))).setUserObject("card");
					
					current=((MenuHeroBox)self.getItem()).hero;
				}
			})));
		boxs.get(MathUtils.random(boxs.size()-1)).click();
		//设置点击音效
		$.add(ld).children().find(ImageButton.class,MenuHeroBox.class).click(()->Music.playSE("snd210.wav"));
		
		
		stage.setDebugAll(Setting.persistence.uiDebug);
		
		return this;
	}
	
	public void click(Hero hero){
		for(GdxQuery box:boxs)
			if(((MenuHeroBox)box.getItem()).hero.equals(hero))
				box.click();
		generateStatusView();
	}
	
	public void click(){ 	
//		if(viewStack.size()>=1 && !(viewStack.get(0) instanceof StatusView))
		for(GdxQuery box:boxs)
			if(((MenuHeroBox)box.getItem()).hero.equals(current))
				box.click();
		generateStatusView();
	}
	
	private void generateStatusView(){
		for(IMenuView view:viewStack)
			if(view instanceof StatusView)
				((StatusView)view).generate();
	}

	@Override
	public void draw(SpriteBatch batch) {
		frames.logic();
		stage.act();
		stage.draw();
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).draw(batch);
	}

	@Override
	public void logic() {
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).logic();
		List<View> removeList=new ArrayList<View>();
		for(View view:viewStack){
			if(view.disposed){
				view.dispose();
				removeList.add(view);
			}
		}
		viewStack.removeAll(removeList);
	}

	public void onkeyTyped(char character) {
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).onkeyTyped(character);
	}

	public void onkeyDown(int keyCode,boolean disposes) {
		if( (viewStack.size()==0 || (viewStack.size()>0 && viewStack.get(viewStack.size()-1).allowEsc() )) && (Keys.ESCAPE==keyCode || keyCode==Keys.X)){
			for(View view:viewStack)
				view.dispose();
			this.dispose();
			com.rpsg.rpg.system.controller.InputController.currentIOMode=IOMode.MapInput.normal;
			frames=null;
			stage=null;
			GameViews.gameview.stackView=null;
			MenuController.bg.dispose();
	 		MenuController.pbg.dispose();
		}else{
			if(viewStack.size()!=0){
				viewStack.get(viewStack.size()-1).onkeyDown(keyCode);
			}
		}
	}
	
	public void onkeyDown(int keyCode){
		onkeyDown(keyCode, false);
	}
	
	List<Hero> heros;
	public List<Hero> getHeros(){
		if(heros==null){
			heros=new ArrayList<Hero>();
			for(GdxQuery query:boxs)
				heros.add(((MenuHeroBox)query.getItem()).hero);
		}
		return heros;
	}
	

	public void onkeyUp(int keyCode) {
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).onkeyUp(keyCode);
	}

	public void dispose() {
		System.gc();
		stage.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		stage.touchDown(screenX, screenY, pointer, button);
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).touchDown(screenX, screenY, pointer, button);
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		stage.touchUp(screenX, screenY, pointer, button);
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).touchUp(screenX, screenY, pointer, button);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		stage.touchDragged(screenX, screenY, pointer);
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).touchDragged(screenX, screenY, pointer);
		return false;
	}
	
	public void tryToAdd(Class<? extends IMenuView> iv){
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
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
	}

	@Override
	public boolean scrolled(int amount) {
		if(viewStack.size()!=0)
			viewStack.get(viewStack.size()-1).scrolled(amount);
		return false;
	}
	
	
	public boolean prev(){
		if(getHeros().indexOf(current)!=0){
			click(getHeros().get(getHeros().indexOf(current)-1));
			return true;
		}
		return false;
	}
	
	public boolean next(){
		if(getHeros().indexOf(current)!=getHeros().size()-1){
			click(getHeros().get(getHeros().indexOf(current)+1));
			return true;
		}
		return false;
	}
	
}
