package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.equip.TestEquip;
import com.rpsg.rpg.object.base.Equip;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.HeroImage;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

public class EquipView extends IView{
	Stage stage;
	List<HeroImage> heros=new ArrayList<HeroImage>();
	Image map;
	
	int currentSelectHero=0;
	
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		
		Image walkerbox=Res.get(Setting.GAME_RES_IMAGE_MENU_EQUIP+"walkerbox.png");
		walkerbox.setPosition(160, 350);
		walkerbox.setColor(1,1,1,0);
		walkerbox.addAction(Actions.fadeIn(0.2f));
		stage.addActor(walkerbox);
		
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
		
		ImageButton left=new ImageButton(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"left.png"),Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"lefts.png"));
		left.setPosition(245, 390);
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
		right.setPosition(377, 390);
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
		com.rpsg.rpg.system.base.List<Equip> elist=new com.rpsg.rpg.system.base.List<Equip>(style);
		elist.onDBClick(()->{
			System.out.println("asd");
		});
		Array<Equip> item = elist.getItems();
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		item.add(new TestEquip());
		ScrollPane pane=new ScrollPane(elist);
		pane.getStyle().vScroll=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"scrollbarin.png");
		pane.layout();
		Table table=new Table();
		table.setBackground(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"equipbox.png"));
		table.add(pane);
		table.padRight(20);
		table.setPosition(600, 115);
		table.setSize(386, 215);
		table.getCell(pane).width(table.getWidth()).height(table.getHeight()-20);
		stage.addActor(table);
		generateHero(currentSelectHero);
	}
	
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		Hero hero=HeroControler.heros.get(currentSelectHero);
		sb.begin();
		heroImage.draw(sb, step==3?1:step);
		FontUtil.draw(sb, hero.name, 22, Color.WHITE, 220, 486, 1000);
		FontUtil.draw(sb,hero.maxhp+"", 20, blue, 465, 437, 1000);
		FontUtil.draw(sb,hero.maxmp+"", 20, blue, 465, 397, 1000);
		FontUtil.draw(sb,hero.attack+"", 20, blue, 605, 437, 1000);
		FontUtil.draw(sb,hero.magicAttack+"", 20, blue, 605, 397, 1000);
		FontUtil.draw(sb,hero.defense+"", 20, blue, 744, 437, 1000);
		FontUtil.draw(sb,hero.magicDefense+"", 20, blue, 744, 397, 1000);
		FontUtil.draw(sb,hero.speed+"", 20, blue, 884, 437, 1000);
		FontUtil.draw(sb,hero.hit+"", 20, blue, 884, 397, 1000);
		FontUtil.draw(sb,hero.level+"", 30, blue, 150, 497, 1000);
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
		
	}

	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode)
			this.disposed=true;
	}

	public void onkeyUp(int keyCode) {
		
	}

	public void dispose() {
		stage.dispose();
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
		heroImage=HeroImage.generateImage(HeroControler.heros.get(index).images, 300, 375);
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
