package com.rpsg.rpg.view.menu;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.items.equipment.Sunshade;
import com.rpsg.rpg.game.items.medicine.CopyOfYaoWan;
import com.rpsg.rpg.object.base.ListItem;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.tip.TipItem;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.TextButton.TextButtonStyle;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.ItemUtil;
import com.rpsg.rpg.view.GameViews;

public class ItemView extends DefaultIView{
	Image map,topbarSel;
	
	
	com.rpsg.rpg.system.ui.List<Item> elist;
	
	int layer=0;
	
	ShapeRenderer render;
	
	ParticleEffect add;
	ScrollPane pane;
	Table topbar;
	Item item=new TipItem();
	Group group;
	int currentCount=0;

	public void init() {
		add=new ParticleEffect();
		add.load(Gdx.files.internal(Setting.GAME_RES_PARTICLE+"addp.p"),Gdx.files.internal(Setting.GAME_RES_PARTICLE));
		add.setPosition(835, 111);
		
		render=new ShapeRenderer();
		render.setAutoShapeType(true);
		
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
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
		
		Image bg=Res.get(Setting.GAME_RES_IMAGE_MENU_ITEM+"item_bg.png");
		bg.setColor(1,1,1,0);
		bg.setPosition(160,28);
		bg.addAction(Actions.fadeIn(0.2f));
		stage.addActor(bg);
		
		ListStyle style=new ListStyle();
		style.font=FontUtil.generateFont(" ".toCharArray()[0], 22);
		style.selection=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"equipsel.png");
		style.fontColorSelected=blue;
		elist=new com.rpsg.rpg.system.ui.List<Item>(style);
		elist.onClick(()->{
			item=elist.getSelected();
		});
		elist.layout();
		pane=new ScrollPane(elist);
		pane.getStyle().vScroll=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_ITEM+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_ITEM+"scrollbarin.png");
		pane.setForceScroll(false, true);
		pane.layout();
		
		Table table=new Table();
		table.add(pane);
		table.padRight(20);
		table.setPosition(170, 40);
		table.setSize(270, 390);
		table.getCell(pane).width(table.getWidth()).height(table.getHeight()-20);
		table.setColor(1,1,1,0);
		table.addAction(Actions.fadeIn(0.2f));
		stage.addActor(table);
		topbarSel=new Image(Res.get(Setting.GAME_RES_IMAGE_MENU_ITEM+"topsel.png"));
		topbar=new Table();
		topbar.setBackground(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_ITEM+"topbar.png"));
		topbar.setSize(818, 42);
		topbar.setPosition(168, 455);
		int tmpI=0,offsetX=135;
		topbar.add(new TopBar("medicine", tmpI++*offsetX));
		topbar.add(new TopBar("material", tmpI++*offsetX));
		topbar.add(new TopBar("cooking", tmpI++*offsetX));
		topbar.add(new TopBar("equipment", tmpI++*offsetX));
		topbar.add(new TopBar("spellcard", tmpI++*offsetX));
		topbar.add(new TopBar("important", tmpI++*offsetX));
		topbar.getCells().forEach((cell)->cell.padLeft(42).padRight(42));
		stage.addActor(topbar);
		
		generateLists("medicine");
		
		Actor mask=new Actor();
		mask.setWidth(GameUtil.screen_width);
		mask.setHeight(GameUtil.screen_height);
		mask.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		stage.addActor(mask);
		
		scuse=Res.get(Setting.GAME_RES_IMAGE_MENU_SC+"sc_use.png");
		scuse.setPosition((int)(GameUtil.screen_width/2-scuse.getWidth()/2), (int)(GameUtil.screen_height/2-scuse.getHeight()/2));
		sellist=new com.rpsg.rpg.system.ui.List<ListItem>(style);
		sellist.onDBClick(()->sellist.getSelected().run.run());
		can =()->{
			scuse.visible=false;
			sellist.setVisible(false);
			mask.setVisible(false);
			layer=0;
		};
		sellist.setPosition(368, 240);
		sellist.setSize(254, 100);
		sellist.layout();
		
		stage.addActor(sellist);
		
		Actor mask2=new Actor();
		mask2.setWidth(GameUtil.screen_width);
		mask2.setHeight(GameUtil.screen_height);
		mask2.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		
		elist.onDBClick(()->{
			scuse.visible=true;
			sellist.getItems().clear();
			if(item.type==Item.TYPE_USEINMAP)
				sellist.getItems().add(new ListItem("使用").setRunnable(()->{
					scfor.visible=true;
					herolist.setVisible(true);
					mask2.setVisible(true);
					layer=2;
				}));
			if(item.throwable)
				sellist.getItems().add(new ListItem("丢弃").setRunnable(()->{
					group.setVisible(true);
					mask2.setVisible(true);
					currentCount=1;
					layer=3;
				}));
			sellist.getItems().add(new ListItem("取消").setRunnable(()->can.run()));
			sellist.onDBClick(()->sellist.getSelected().run.run());
			sellist.setVisible(true);
			sellist.setSelectedIndex(0);
			mask.setVisible(true);
			layer=1;
		});
		
		
		stage.addActor(mask2);			
		scfor=Res.get(Setting.GAME_RES_IMAGE_MENU_ITEM+"selbg.png");
		scfor.setPosition(500, 87);
		
		
		herolist=new com.rpsg.rpg.system.ui.List<ListItem>(style);
		herolist.getItems().add(new ListItem("取消"));
		HeroControler.heros.forEach((h)->herolist.getItems().add(new ListItem(h.name).setUserObject(h)));
		herolist.onDBClick(()->{
			if(herolist.getSelected().name.equals("取消")){
				can2.run();
			}else{
				if(herolist.getSelected().userObject!=null)
					if(item.use((Hero)herolist.getSelected().userObject)){
						can2.run();
						can.run();
						generateLists(currentBar.name);
						item= new TipItem();
					}
				drawp=true;
			}
		});
		herolist.setPosition(492, 273);
		herolist.setSize(257, 140);
		herolist.layout();
		stage.addActor(herolist);
		
		can2 =()->{
			scfor.visible=false;
			herolist.setVisible(false);
			mask2.setVisible(false);
			layer=1;
		};
		TextButtonStyle butstyle=new TextButtonStyle();
		butstyle.over=butstyle.checkedOver=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button_hover.png");
		butstyle.down=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button_active.png");
		butstyle.up=Res.getDrawable(Setting.GAME_RES_IMAGE_GLOBAL+"button.png");
		group=new Group();
		Image tbg=new Image(Setting.GAME_RES_IMAGE_MENU_SC+"throw.png");
		tbg.setPosition(350, 200);
		group.addActor(tbg);
		TextButton button=new TextButton("确定", butstyle).onClick(()->{
			ItemUtil.throwItem(currentBar.name,item,currentCount);
			AlertUtil.add("丢弃成功。", AlertUtil.Yellow);
			generateLists(currentBar.name);
			item= new TipItem();
			can3.run();
		});
		button.setPosition(630, 290);
		button.setSize(100, 50);
		group.addActor(button);
		TextButton button2=new TextButton("取消", butstyle).onClick(()->{
			can3.run();
		});
		button2.setPosition(630, 225);
		button2.setSize(100, 50);
		group.addActor(button2);
		Table buttable=new Table();
		buttable.add(new TextButton("最大", butstyle,16).onClick(()->{
			currentCount=item.count==0?1:item.count;
		})).size(80,33).row();
		buttable.add(new TextButton("+1", butstyle,16).onClick(()->{
			if(currentCount<item.count)
				currentCount++;
		})).size(80,35).row();
		buttable.add(new TextButton("-1", butstyle,16).onClick(()->{
			if(currentCount>1)
				currentCount--;
		})).size(80,35).row();
		buttable.add(new TextButton("最小", butstyle,16).onClick(()->{
			currentCount=1;
		})).size(80,33).row();
		buttable.getCells().forEach((c)->c.padTop(2).padBottom(2));
		buttable.setPosition(575, 300);
		group.addActor(buttable);
		stage.addActor(group);
		can3=()->{
			group.setVisible(false);
			mask2.setVisible(false);
			can.run();
		};
		can3.run();
		can2.run();
		can.run();
		
	}
	Image scuse,scfor;
	com.rpsg.rpg.system.ui.List<ListItem> sellist,herolist;
	Runnable can,can2,can3;
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	Color green=new Color(219f/255f,255f/255f,219f/255f,1);
	Color cblue=new Color(219f/255f,238f/255f,255f/255f,1);
	boolean drawp=false;
	
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		sb.begin();
		FontUtil.draw(sb, item.name, 22, Color.WHITE, 455, 134, 1000);
		FontUtil.draw(sb, item.illustration, 18, blue, 459, 100, 490);
		scuse.draw(sb);
		if(sellist.isVisible()) sellist.draw(sb, 1);
		scfor.draw(sb);
		if(herolist.isVisible()){
			herolist.draw(sb, 1);
			sb.flush();
			if(herolist.getSelectedIndex()!=-1 && (Hero)herolist.getSelected().userObject!=null){
				Hero h=((Hero)herolist.getSelected().userObject);
				render.begin(ShapeType.Filled);
				render.setColor(green);
				render.rect(575, 142, (float)((float)h.prop.get("hp")/(float)h.prop.get("maxhp"))*176,20);
				render.setColor(cblue);
				render.rect(575, 105, (float)((float)h.prop.get("mp")/(float)h.prop.get("maxmp"))*176,20);
				render.end();
				FontUtil.draw(sb, h.prop.get("hp")+"/"+h.prop.get("maxhp"), 20, blue, 565+176/2-FontUtil.getTextWidth(h.prop.get("hp")+"/"+h.prop.get("maxhp"), 20,-7)/2, 161, 400,-7,0);
				FontUtil.draw(sb, h.prop.get("mp")+"/"+h.prop.get("maxmp"), 20, blue, 565+176/2-FontUtil.getTextWidth(h.prop.get("mp")+"/"+h.prop.get("maxmp"), 20,-7)/2, 124, 400,-7,0);
				FontUtil.draw(sb, "正常", 18, blue, 563+176/2-FontUtil.getTextWidth("正常", 18)/2, 196, 400);
				FontUtil.draw(sb, h.toString(), 18, Color.WHITE, 515, 227, 200);
				if(drawp){
					add.draw(sb,Gdx.graphics.getDeltaTime());
					drawp=!add.isComplete();
				}else
					add.reset();
				sb.draw(h.images[1].getRegion(),810,97);
			}
			sb.flush();
		}
		if(group.isVisible()){
			group.draw(batch, 1);
			FontUtil.draw(sb, currentCount>10?(currentCount>100?currentCount+"":"0"+currentCount):"00"+currentCount, 80, blue, 367, 337, 200,-40,0);
		}
		sb.end();
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
		if(Keys.M==keyCode){
			ItemUtil.addItem(new Sunshade());
		}
		if(Keys.V==keyCode){
			ItemUtil.addItem(new CopyOfYaoWan());
			
		}
		if(Keys.ESCAPE==keyCode || keyCode==Keys.X){
			if(layer==0)
				this.disposed=true;
			else if(layer==1)
				can.run();
			else if(layer==2)
				can2.run();
			else if(layer==3)
				can3.run();
		}else
			stage.keyDown(keyCode);
	}

	public void dispose() {
		topbar.getCells().forEach((cell)->((TopBar)cell.getActor()).dispose());
		stage.dispose();
		render.dispose();
		add.dispose();
	}

	TopBar currentBar=null;
	public void generateLists(String type){
		float y=pane.getScrollX();
		Array<Item> sc = elist.getItems();
		sc.clear();
		GameViews.global.items.get(type).forEach((e)->{
			sc.add(e);
		});
		elist.layout();
		topbar.getCells().forEach((cell)->
			((TopBar)cell.getActor()).select(((TopBar)cell.getActor()).name.equals(type))
		);
		pane.setScrollX(y);
	}
	
	
	class TopBar extends Actor{
		String name;
		boolean selected=false;
		Image bigImg,miniImg;
		public TopBar(String name,int offsetX){
			bigImg=new Image(Setting.GAME_RES_IMAGE_MENU_ITEM+name+".png");
			bigImg.setPosition(700, 300);
			miniImg=new Image(Setting.GAME_RES_IMAGE_MENU_ITEM+name+"_m.png");
			miniImg.setPosition(230+offsetX, 465);
			this.name=name;
			this.setTouchable(miniImg.getTouchable());
			addListener(new InputListener(){
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					generateLists(name);
					return false;
				}
			});
			this.setSize(50, 30);
			this.setPosition(230+offsetX,465);
		}
		public void select(boolean select){
			miniImg.setColor((this.selected=select)?blue:Color.WHITE);
			if(this.selected){
				topbarSel.setPosition(miniImg.getX()-30, miniImg.getY()-17);
				currentBar=this;
			}
		}
		public void draw (Batch batch, float parentAlpha) {
			if(this.selected){
				bigImg.draw(batch);
				topbarSel.draw(batch);
			}
			miniImg.draw(batch);
		}
		public void dispose(){
			bigImg.dispose();
			miniImg.dispose();
		}
	}
	
	
}
