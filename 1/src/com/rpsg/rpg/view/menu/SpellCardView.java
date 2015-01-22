package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.rpsg.rpg.object.base.ListItem;
import com.rpsg.rpg.object.base.items.SpellCard;
import com.rpsg.rpg.object.base.items.tip.TipSpellCard;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.HeroImage;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.game.GameUtil;

public class SpellCardView extends IView{
	Stage stage;
	List<HeroImage> heros=new ArrayList<HeroImage>();
	Image map;
	
	int currentSelectHero=0;
	int currentSelectSpell=0;
	
	com.rpsg.rpg.system.base.List<SpellCard> elist;
	
	int layer=0;
	
	ShapeRenderer render;
	
	ParticleEffect add;
	
	SpellCard spell=new TipSpellCard();
	public void init() {
		add=new ParticleEffect();
		add.load(Gdx.files.internal(Setting.GAME_RES_PARTICLE+"addp.p"),Gdx.files.internal(Setting.GAME_RES_PARTICLE));
		add.setPosition(845, 261);
		
		
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
		
		Image bg=Res.get(Setting.GAME_RES_IMAGE_MENU_SC+"bg.png");
		bg.setColor(1,1,1,0);
		bg.setPosition(160,28);
		bg.addAction(Actions.fadeIn(0.2f));
		stage.addActor(bg);
		
		ImageButton left=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"left.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"lefts.png"));
		left.setPosition(262, 383);
		left.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				prevHero();
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(left);
		
		ImageButton right=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"right.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"rights.png"));
		right.setPosition(395, 383);
		right.addListener(new InputListener(){
			public void touchUp (InputEvent event, float x, float y, int pointer, int b) {
				nextHero();
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(right);
		
		
		ListStyle style=new ListStyle();
		style.font=FontUtil.generateFont(" ".toCharArray()[0], 22);
		style.selection=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"equipsel.png");
		style.fontColorSelected=blue;
		elist=new com.rpsg.rpg.system.base.List<SpellCard>(style);
		elist.onClick(()->{
			spell=elist.getSelected();
		});
		generateLists();
		elist.layout();
		ScrollPane pane=new ScrollPane(elist);
		pane.getStyle().vScroll=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"scrollbarin.png");
		pane.setForceScroll(false, true);
		pane.layout();
		Table table=new Table();
		table.add(pane);
		table.padRight(20);
		table.setPosition(206, 40);
		table.setSize(270, 250);
		table.getCell(pane).width(table.getWidth()).height(table.getHeight()-20);
		
		table.setColor(1,1,1,0);
		table.addAction(Actions.fadeIn(0.2f));
		stage.addActor(table);
		
		generateHero(currentSelectHero);
		
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
		scuse.setPosition(GameUtil.screen_width/2-scuse.getWidth()/2, GameUtil.screen_height/2-scuse.getHeight()/2);
		sellist=new com.rpsg.rpg.system.base.List<ListItem>(style);
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
		
		elist.onDBClick(()->{
			if(spell.type==SpellCard.TYPE_USEINMAP){
				scuse.visible=true;
				sellist.setVisible(true);
				sellist.setSelectedIndex(0);
				mask.setVisible(true);
				layer=1;
			}
		});
		
		Actor mask2=new Actor();
		mask2.setWidth(GameUtil.screen_width);
		mask2.setHeight(GameUtil.screen_height);
		mask2.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		
		sellist.getItems().add(new ListItem("使用").setRunnable(()->{
			scfor.visible=true;
			herolist.setVisible(true);
			mask2.setVisible(true);
			layer=2;
		}));
		sellist.getItems().add(new ListItem("取消").setRunnable(()->can.run()));
		
		stage.addActor(mask2);			
		scfor=Res.get(Setting.GAME_RES_IMAGE_MENU_SC+"sc_for.png");
		scfor.setPosition(500, 87);
		
		
		herolist=new com.rpsg.rpg.system.base.List<ListItem>(style);
		herolist.getItems().add(new ListItem("取消"));
		HeroControler.heros.forEach((h)->herolist.getItems().add(new ListItem(h.name).setUserObject(h)));
		herolist.onDBClick(()->{
			if(herolist.getSelected().name.equals("取消")){
				can2.run();
			}else{
				if(herolist.getSelected().userObject!=null)
					if(spell.use(HeroControler.heros.get(currentSelectHero),((Hero)herolist.getSelected().userObject)))
						can2.run();
				drawp=true;
			}
		});
		herolist.setPosition(500, 343);
		herolist.setSize(257, 140);
		herolist.layout();
		stage.addActor(herolist);
		
		can2 =()->{
			scfor.visible=false;
			herolist.setVisible(false);
			mask2.setVisible(false);
			layer=1;
		};
		
		can2.run();
		can.run();
		
		
	}
	Image scuse,scfor;
	com.rpsg.rpg.system.base.List<ListItem> sellist,herolist;
	Runnable can,can2;
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	Color green=new Color(219f/255f,255f/255f,219f/255f,1);
	Color cblue=new Color(219f/255f,238f/255f,255f/255f,1);
	boolean drawp=false;
	
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		Hero hero=HeroControler.heros.get(currentSelectHero);
		sb.begin();
		FontUtil.draw(sb,hero.prop.get("level")+"", 30, blue, 154+60/2-FontUtil.getTextWidth(hero.prop.get("level")+"", 30), 487, 1000);
		FontUtil.draw(sb, hero.prop.get("maxsc")+"", 18, blue, 317, 325, 200,-7,0);
		FontUtil.draw(sb, hero.name, 22, Color.WHITE, 215, 480, 1000);
		FontUtil.draw(sb, spell.name, 40, Color.WHITE, 475, 435, 1000);
		FontUtil.draw(sb, spell.illustration, 18, Color.WHITE, 485, 375, 400);
		FontUtil.draw(sb, spell.story, 18, Color.WHITE, 485, 225, 400);
		FontUtil.draw(sb, spell.magicConsume+"", 16,blue, 850+64/2-FontUtil.getTextWidth(spell.magicConsume+"", 16,-5)/2, 404, 400,-5,0);
		heroImage.draw(sb, step==3?1:step);
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
				render.rect(578, 258, (float)((float)h.prop.get("hp")/(float)h.prop.get("maxhp"))*189,22);
				render.setColor(cblue);
				render.rect(578, 224, (float)((float)h.prop.get("mp")/(float)h.prop.get("maxmp"))*189,22);
				render.end();
				FontUtil.draw(sb, h.prop.get("hp")+"/"+h.prop.get("maxhp"), 20, blue, 560+190/2-FontUtil.getTextWidth(h.prop.get("hp")+"/"+h.prop.get("maxhp"), 20,-7)/2, 278, 400,-7,0);
				FontUtil.draw(sb, h.prop.get("mp")+"/"+h.prop.get("maxmp"), 20, blue, 560+190/2-FontUtil.getTextWidth(h.prop.get("mp")+"/"+h.prop.get("maxmp"), 20,-7)/2, 244, 400,-7,0);
				FontUtil.draw(sb, "正常", 18, blue, 560+190/2-FontUtil.getTextWidth("正常", 20)/2, 208, 400);
				FontUtil.draw(sb, "目标："+h, 18, Color.WHITE, 495, 310, 200);
				if(drawp){
					add.draw(sb,Gdx.graphics.getDeltaTime());
					drawp=!add.isComplete();
				}else
					add.reset();
				sb.draw(h.images[1].getRegion(),820,258);
			}
			sb.flush();
			render.begin(ShapeType.Filled);
			render.setColor(cblue);
			render.rect(578, 116, (float)((float)hero.prop.get("mp")/(float)hero.prop.get("maxmp"))*189,22);
			render.end();
			FontUtil.draw(sb, "技能使用者："+hero, 18, Color.WHITE, 495, 170, 200);
			FontUtil.draw(sb, hero.prop.get("mp")+"/"+  hero.prop.get("maxmp"), 20, blue, 560+190/2-FontUtil.getTextWidth(hero.prop.get("mp")+"/"+  hero.prop.get("maxmp"), 20,-7)/2, 136, 400,-7,0);
			sb.draw(hero.images[1].getRegion(),820,102);
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
	
	
	public void onkeyTyped(char character) {
		stage.keyTyped(character);
	}

	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode){
			if(layer==0)
				this.disposed=true;
			else if(layer==1)
				can.run();
			else if(layer==2)
				can2.run();
		}else
			stage.keyDown(keyCode);
	}

	public void onkeyUp(int keyCode) {
		stage.keyUp(keyCode);
	}

	public void dispose() {
		stage.dispose();
		render.dispose();
		add.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return stage.touchDown(screenX, screenY, pointer, button);
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return stage.touchDragged(screenX, screenY, pointer);
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return stage.touchUp(screenX, screenY, pointer, button);
	}

	HeroImage heroImage;
	public void generateHero(int index){
		heroImage=HeroImage.generateImage(HeroControler.heros.get(index).images, 316, 370);
		generateLists();
	}
	
	public void generateLists(){
		Array<SpellCard> sc = elist.getItems();
		sc.clear();
		HeroControler.heros.get(currentSelectHero).sc.forEach((s)->sc.add(s));
	}
	
	public void nextHero(){
		if(currentSelectHero!=HeroControler.heros.size()-1){
			currentSelectHero++;
			generateHero(currentSelectHero);
		}
	}
	
	public void prevHero(){
		if(currentSelectHero!=0){
			currentSelectHero--;
			generateHero(currentSelectHero);
		}
	}

	@Override
	public boolean scrolled(int amount) {
		return stage.scrolled(amount);
	}
	
}
