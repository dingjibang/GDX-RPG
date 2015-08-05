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
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.items.equipment.Sunshade;
import com.rpsg.rpg.game.items.medicine.CopyOfYaoWan;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.ListItem;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.tip.TipItem;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.system.ui.TextButton.TextButtonStyle;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.display.NumberUtil;
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
	
	Image[] throwimg=new Image[10];
	public View init() {
		add=new ParticleEffect();
		add.load(Gdx.files.internal(Setting.PARTICLE + "addp.p"), Gdx.files.internal(Setting.PARTICLE));
		add.setPosition(835, 111);
		
		render=new ShapeRenderer();
		render.setAutoShapeType(true);
		
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		
		Image bg=Res.get(Setting.IMAGE_MENU_ITEM+"item_bg.png");
		bg.setColor(1, 1, 1, 0);
		bg.setPosition(160, 28);
		bg.addAction(Actions.fadeIn(0.2f));
		stage.addActor(bg);
		
		ListStyle style=new ListStyle();
		style.font=FontUtil.generateFont(" ".toCharArray()[0], 22);
		style.selection=Res.getDrawable(Setting.IMAGE_MENU_EQUIP + "equipsel.png");
		style.fontColorSelected=Color.valueOf("f5e70c");
		elist=new com.rpsg.rpg.system.ui.List<Item>(style);
		elist.onClick(new Runnable() {
			@Override
			public void run() {
				item = elist.getSelected();
				Music.playSE("snd210.wav");
			}
		});
		elist.layout();
		pane=new ScrollPane(elist);
		pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_ITEM+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_ITEM+"scrollbarin.png");
		pane.setForceScroll(false, true);
		pane.layout();
		
		Table table=new Table();
		table.add(pane);
		table.padRight(20);
		table.setPosition(170, 40);
		table.setSize(270, 390);
		table.getCell(pane).width(table.getWidth()).height(table.getHeight() - 20);
		table.setColor(1, 1, 1, 0);
		table.addAction(Actions.fadeIn(0.2f));
		stage.addActor(table);
		topbarSel=Res.get(Setting.IMAGE_MENU_ITEM+"topsel.png");
		topbar=new Table();
		topbar.setBackground(Res.getDrawable(Setting.IMAGE_MENU_ITEM + "topbar.png"));
		topbar.setSize(818, 42);
		topbar.setPosition(160, 455);
		int tmpI=0,offsetX=135;
		topbar.add(new TopBar("medicine", tmpI++*offsetX));
		topbar.add(new TopBar("material", tmpI++*offsetX));
		topbar.add(new TopBar("cooking", tmpI++*offsetX));
		topbar.add(new TopBar("equipment", tmpI++*offsetX));
		topbar.add(new TopBar("spellcard", tmpI++*offsetX));
		topbar.add(new TopBar("important", tmpI++ * offsetX));
		for (Cell cell : topbar.getCells()) {
			cell.padLeft(50).padRight(34).height(40);
			cell.getActor().addListener(new InputListener() {
				public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
					Music.playSE("snd210.wav");
					return true;
				}
			});
		}

		stage.addActor(topbar);
		
		generateLists("medicine");
		
		final Actor mask=new Actor();
		mask.setWidth(GameUtil.screen_width);
		mask.setHeight(GameUtil.screen_height);
		mask.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		stage.addActor(mask);
		scuse=Res.get(Setting.IMAGE_MENU_SC+"sc_use.png");
		scuse.loaded= new Runnable() {
			@Override
			public void run() {
				scuse.setPosition((int) (GameUtil.screen_width / 2 - scuse.getWidth() / 2), (int) (GameUtil.screen_height / 2 - scuse.getHeight() / 2));
			}
		};
		sellist=new com.rpsg.rpg.system.ui.List<ListItem>(style);
		sellist.onDBClick(new Runnable() {
			@Override
			public void run() {
				sellist.getSelected().run.run();
			}
		});
		can = new Runnable() {
			@Override
			public void run() {
				scuse.visible = false;
				sellist.setVisible(false);
				mask.setVisible(false);
				layer = 0;
			}
		};
		sellist.setPosition(368, 240);
		sellist.setSize(254, 100);
		sellist.layout();
		
		stage.addActor(sellist.onClick(new Runnable() {
			@Override
			public void run() {
				Music.playSE("snd210.wav");
			}
		}));
		
		final Actor mask2=new Actor();
		mask2.setWidth(GameUtil.screen_width);
		mask2.setHeight(GameUtil.screen_height);
		mask2.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		
		elist.onDBClick(new Runnable() {
			@Override
			public void run() {
				scuse.visible = true;
				sellist.offsetX2 = 20;
				sellist.getItems().clear();
				if (item.type == Item.TYPE_USEINMAP)
					sellist.getItems().add(new ListItem("使用").setUserObject(Res.get(Setting.IMAGE_ICONS + "yes.png")).setRunnable(new Runnable() {
						@Override
						public void run() {
							scfor.visible = true;
							herolist.setVisible(true);
							mask2.setVisible(true);
							layer = 2;
						}
					}));
				if (item.throwable)
					sellist.getItems().add(new ListItem("丢弃").setUserObject(Res.get(Setting.IMAGE_ICONS + "bin.png")).setRunnable(new Runnable() {
						@Override
						public void run() {
							group.setVisible(true);
							mask2.setVisible(true);
							can.run();
							currentCount = 1;
							layer = 3;
						}
					}));
				sellist.getItems().add(new ListItem("取消").setUserObject(Res.get(Setting.IMAGE_ICONS + "no.png")).setRunnable(new Runnable() {
					@Override
					public void run() {
						can.run();
					}
				}));
				sellist.onDBClick(new Runnable() {
					@Override
					public void run() {
						sellist.getSelected().run.run();
					}
				});
				sellist.setVisible(true);
				sellist.setSelectedIndex(0);
				sellist.setItemHeight(27);
				sellist.padTop = 2;
				mask.setVisible(true);
				layer = 1;
			}
		});
		
		
		stage.addActor(mask2);			
		scfor=Res.get(Setting.IMAGE_MENU_ITEM+"selbg.png");
		scfor.setPosition(500, 87);
		
		herolist=new com.rpsg.rpg.system.ui.List<ListItem>(style);
		herolist.offsetX2=20;
		herolist.getItems().add(new ListItem("取消").setUserObject(Res.get(Setting.IMAGE_ICONS+"no.png")));
		for (Hero h : RPG.ctrl.hero.heros) {
			herolist.getItems().add(new ListItem(h.name).setUserObject(h));
		}

		herolist.onDBClick(new Runnable() {
			@Override
			public void run() {

			}
		}).onClick(new Runnable() {
			@Override
			public void run() {
				Music.playSE("snd210.wav");
			}
		});
		herolist.setPosition(492, 273);
		herolist.setSize(257, 140);
		herolist.layout();
		stage.addActor(herolist);
		
		can2 = new Runnable() {
			@Override
			public void run() {
				scfor.visible = false;
				herolist.setVisible(false);
				mask2.setVisible(false);
				layer = 1;
			}
		};
		TextButtonStyle butstyle=new TextButtonStyle();
		butstyle.over=butstyle.checkedOver=Res.getDrawable(Setting.IMAGE_GLOBAL+"button_RPG.hover.png");
		butstyle.down=Res.getDrawable(Setting.IMAGE_GLOBAL+"button_active.png");
		butstyle.up=Res.getDrawable(Setting.IMAGE_GLOBAL+"button.png");
		group=new Group();
		Image tbg=new Image(Setting.IMAGE_MENU_SC+"throw.png");
		tbg.setPosition(350, 200);
		group.addActor(tbg);
		TextButton button;
		button = new TextButton("确定", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				ItemUtil.throwItem(currentBar.name, item, currentCount);
				RPG.putMessage("丢弃成功。", AlertUtil.Yellow);
				ItemView.this.generateLists(currentBar.name);
				item = new TipItem();
				can3.run();
			}
		});
		button.setPosition(630, 290);
		button.setSize(100, 50);
		group.addActor(button);
		TextButton button2=new TextButton("取消", butstyle).onClick(new Runnable() {
			@Override
			public void run() {
				can3.run();
			}
		});
		
		button2.setPosition(630, 225);
		button2.setSize(100, 50);
		group.addActor(button2);
		ImageButton upbutton1=new ImageButton(Res.getDrawable(Setting.IMAGE_NUMBER+"up.png"), Res.getDrawable(Setting.IMAGE_NUMBER+"ups.png"));
		upbutton1.onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount + 100 <= item.count)
					currentCount += 100;
			}
		}).setPosition(395, 340);
		group.addActor(upbutton1);
		ImageButton upbutton2=new ImageButton(Res.getDrawable(Setting.IMAGE_NUMBER+"up.png"), Res.getDrawable(Setting.IMAGE_NUMBER+"ups.png"));
		upbutton2.onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount + 10 <= item.count)
					currentCount += 10;
			}
		}).setPosition(435, 340);
		group.addActor(upbutton2);
		ImageButton upbutton3=new ImageButton(Res.getDrawable(Setting.IMAGE_NUMBER+"up.png"), Res.getDrawable(Setting.IMAGE_NUMBER+"ups.png"));
		upbutton3.onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount + 1 <= item.count)
					currentCount += 1;
			}
		}).setPosition(475, 340);
		group.addActor(upbutton3);
		ImageButton dbutton1=new ImageButton(Res.getDrawable(Setting.IMAGE_NUMBER+"down.png"), Res.getDrawable(Setting.IMAGE_NUMBER+"downs.png"));
		dbutton1.onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount - 100 >= 1)
					currentCount -= 100;
			}
		}).setPosition(395, 240);
		group.addActor(dbutton1);
		ImageButton dbutton2=new ImageButton(Res.getDrawable(Setting.IMAGE_NUMBER+"down.png"), Res.getDrawable(Setting.IMAGE_NUMBER+"downs.png"));
		dbutton2.onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount - 10 >= 1)
					currentCount -= 10;
			}
		}).setPosition(435, 240);
		group.addActor(dbutton2);
		ImageButton dbutton3=new ImageButton(Res.getDrawable(Setting.IMAGE_NUMBER+"down.png"), Res.getDrawable(Setting.IMAGE_NUMBER+"downs.png"));
		dbutton3.onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount - 1 >= 1)
					currentCount -= 1;
			}
		}).setPosition(475, 240);
		group.addActor(dbutton3);
		
		Table buttable=new Table();
		buttable.add(new TextButton("最大", butstyle,16).onClick(new Runnable() {
			@Override
			public void run() {
				currentCount = item.count == 0 ? 1 : item.count;
			}
		})).size(80,33).row();
		buttable.add(new TextButton("+1", butstyle,16).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount < item.count)
					currentCount++;
			}
		})).size(80,35).row();
		buttable.add(new TextButton("-1", butstyle,16).onClick(new Runnable() {
			@Override
			public void run() {
				if (currentCount > 1)
					currentCount--;
			}
		})).size(80,35).row();
		buttable.add(new TextButton("最小", butstyle,16).onClick(new Runnable() {
			@Override
			public void run() {
				currentCount = 1;
			}
		})).size(80,33).row();
		for (Cell c : buttable.getCells()) {
			c.padTop(2).padBottom(2);
		}

		buttable.setPosition(575, 300);
		group.addActor(buttable);
		stage.addActor(group);
		for (final Actor a : group.getChildren()) {
			a.addListener(new InputListener(){
				public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
					if(!(a instanceof Image))
						Music.playSE("snd210.wav");
					return true;
				}
			});
		}

		can3= new Runnable() {
			@Override
			public void run() {
				group.setVisible(false);
				mask2.setVisible(false);
				can.run();
			}
		};
		can3.run();
		can2.run();
		can.run();
		return this;
		
		
	}
	Image scuse,scfor;
	com.rpsg.rpg.system.ui.List<ListItem> sellist,herolist;
	Runnable can,can2,can3;
	Color blue=Color.DARK_GRAY;
	Color green=new Color(219f/255f,255f/255f,219f/255f,1);
	Color cblue=new Color(219f/255f,238f/255f,255f/255f,1);
	boolean drawp=false;
	
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		sb.begin();
		FontUtil.draw(sb, item.name, 22, Color.WHITE, 455, 134, 1000);
		FontUtil.draw(sb, item.illustration, 18, Color.DARK_GRAY, 459, 100, 490);
		scuse.draw(sb);
		if(sellist.isVisible()) sellist.draw(sb, 1);
		scfor.draw(sb);
		if(herolist.isVisible()){
			herolist.draw(sb, 1);
			sb.flush();
			if(herolist.getSelectedIndex()!=-1 && herolist.getSelected().userObject!=null && !(herolist.getSelected().userObject instanceof Image)){
				Hero h=((Hero)herolist.getSelected().userObject);
				render.begin(ShapeType.Filled);
				render.setColor(green);
				render.rect(575, 142, (float)((float)h.prop.get("hp")/(float)h.prop.get("maxhp"))*176,20);
				render.setColor(cblue);
				render.rect(575, 105, (float)((float)h.prop.get("mp")/(float)h.prop.get("maxmp"))*176,20);
				render.end();
				FontUtil.draw(sb, h.prop.get("hp")+"/"+h.prop.get("maxhp"), 16, blue, 572+176/2-FontUtil.getTextWidth(h.prop.get("hp")+"/"+h.prop.get("maxhp"), 20,-7)/2, 158, 400,-5,0);
				FontUtil.draw(sb, h.prop.get("mp")+"/"+h.prop.get("maxmp"), 16, blue, 572+176/2-FontUtil.getTextWidth(h.prop.get("mp")+"/"+h.prop.get("maxmp"), 20,-7)/2, 121, 400,-5,0);
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
			String tmp=String.valueOf(currentCount);
			tmp=tmp.length()==1?"00"+tmp:tmp.length()==2?"0"+tmp:tmp;
			Image i1=NumberUtil.get(tmp.substring(0, 1));
			i1.setPosition(390, 275);
			i1.draw(sb);
			Image i2=NumberUtil.get(tmp.substring(1, 2));
			i2.setPosition(430, 275);
			i2.draw(sb);
			Image i3=NumberUtil.get(tmp.substring(2, 3));
			i3.setPosition(470, 275);
			i3.draw(sb);
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
		for (Cell cell : topbar.getCells()) {
			((TopBar)cell.getActor()).dispose();
		}

		stage.dispose();
//		render.dispose();
		add.dispose();
	}

	TopBar currentBar=null;
	public void generateLists(String type){
		elist.offsetX2=type.equals("equipment")?20:0;
		float y=pane.getScrollX();
		Array<Item> sc = elist.getItems();
		sc.clear();
		for (Item e : RPG.global.items.get(type)) {
			sc.add(e);
		}

		elist.layout();
		for (Cell cell : topbar.getCells()) {
			((TopBar)cell.getActor()).select(((TopBar)cell.getActor()).name.equals(type));
		}

		pane.setScrollX(y);
	}
	
	
	class TopBar extends Actor{
		String name;
		boolean selected=false;
		Image bigImg,miniImg;
		public TopBar(final String name,int offsetX){
			bigImg=Res.get(Setting.IMAGE_MENU_ITEM+name+".png");
			bigImg.setPosition(700, 300);
			miniImg=Res.get(Setting.IMAGE_MENU_ITEM+name+"_m.png");
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
			
		}
	}
	
	
}
