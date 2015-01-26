package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.tip.EmptyEquip;
import com.rpsg.rpg.object.base.items.tip.TipEquip;
import com.rpsg.rpg.object.rpgobj.Hero;
import com.rpsg.rpg.system.base.HeroImage;
import com.rpsg.rpg.system.base.IView;
import com.rpsg.rpg.system.base.Image;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.control.HeroControler;
import com.rpsg.rpg.utils.display.AlertUtil;
import com.rpsg.rpg.utils.display.FontUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.ItemUtil;
import com.rpsg.rpg.view.GameViews;

public class EquipView extends IView{
	Stage stage;
	List<HeroImage> heros=new ArrayList<HeroImage>();
	Image map;
	
	int currentSelectHero=0;
	int currentSelectEquip=0;
	
	com.rpsg.rpg.system.base.List<Equipment> elist;
	com.rpsg.rpg.system.base.List<EQuipSelect> sellist;
	com.rpsg.rpg.system.base.List<ListItem> olist;
	
	Runnable cancel;
	
	Equipment equip=new TipEquip();
	Texture up,down;
	
	public void init() {
		
		
		up=new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_MENU_EQUIP+"add.png"));
		down=new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_MENU_EQUIP+"sub.png"));
		
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
		
		Image bot=Res.get(Setting.GAME_RES_IMAGE_MENU_EQUIP+"botbar.png");
		bot.setColor(1,1,1,0);
		bot.addAction(Actions.fadeIn(0.2f));
		bot.setPosition(130, 13);
		stage.addActor(bot);
		
		ListStyle style=new ListStyle();
		style.font=FontUtil.generateFont(" ".toCharArray()[0], 22);
		style.selection=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"equipsel.png");
		style.fontColorSelected=blue;
		elist=new com.rpsg.rpg.system.base.List<Equipment>(style);
		elist.onClick(()->{
			equip=elist.getSelected();
		});
		ScrollPane pane=new ScrollPane(elist);
		pane.getStyle().vScroll=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"scrollbar.png");
		pane.getStyle().vScrollKnob=Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"scrollbarin.png");
		pane.setForceScroll(false, true);
		pane.layout();
		Table table=new Table();
		table.setBackground(Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"equipbox.png"));
		table.add(pane);
		table.padRight(20);
		table.setPosition(600, 120);
		table.setSize(386, 215);
		table.getCell(pane).width(table.getWidth()).height(table.getHeight()-20);
		
		table.setColor(1,1,1,0);
		table.addAction(Actions.fadeIn(0.2f));
		stage.addActor(table);
		
		sellist=new com.rpsg.rpg.system.base.List<EQuipSelect>(style);
		sellist.setSize(173,200);
		sellist.setPosition(184, 16);
		sellist.setItemHeight(40);
		sellist.padTop=7;
		sellist.layout();
		sellist.onClick(()->{
			gengrateEList();
		}).onDBClick(()->{
			gengrateEList();
		});
		
		
		stage.addActor(sellist);
		
		generateHero(currentSelectHero);
		gengrateEList();
		
		Actor mask=new Actor();
		mask.setWidth(GameUtil.screen_width);
		mask.setHeight(GameUtil.screen_height);
		mask.addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return false;
			}
		});
		stage.addActor(mask);
		
		Image msg=Res.get(Setting.GAME_RES_IMAGE_MENU_EQUIP+"equipmsgbox.png");
		msg.setPosition(380, 140);
		stage.addActor(msg);
		olist=new com.rpsg.rpg.system.base.List<ListItem>(style);
		olist.getItems().add(new ListItem("装备").setRunnable(()->{
			ItemUtil.useEquip(HeroControler.heros.get(currentSelectHero), equip);
			gengrateEList();
		}));
		olist.getItems().add(new ListItem("丢弃").setRunnable(()->{
			if(equip.throwable){
				ItemUtil.throwItem("equipment",equip);
				AlertUtil.add("丢弃成功。", AlertUtil.Yellow);
				gengrateEList();
			}
		}));
		cancel=()->{
			olist.setVisible(false);
			msg.setVisible(false);
			mask.setVisible(false);
			stage.setKeyboardFocus(null);
		};
		cancel.run();
		olist.getItems().add(new ListItem("取消"));
		olist.onDBClick(()->{
			olist.getSelected().run();
			cancel.run();
		});
		olist.setPosition(380, 100);
		olist.setSize(211, 200);
		stage.addActor(olist);
		
		elist.onDBClick(()->{
			if(!elist.getSelected().name.equals(EmptyEquip.gloname)){
				olist.setVisible(true);
				olist.setSelectedIndex(0);
				msg.setVisible(true);
				mask.setVisible(true);
				stage.setKeyboardFocus(olist);
			}else{
				if(sellist.getSelected()!=null){
					ItemUtil.takeOffEquip(HeroControler.heros.get(currentSelectHero), sellist.getSelected().type);
					gengrateEList();
				}
			}
		});
		
	}
	
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	Color green=new Color(106f/255f,186f/255f,49f/255f,1);
	Color red=new Color(206f/255f,88f/255f,88f/255f,1);
	public void draw(SpriteBatch batch) {
		stage.draw();
		SpriteBatch sb=(SpriteBatch) stage.getBatch();
		Hero hero=HeroControler.heros.get(currentSelectHero);
		sb.begin();
		heroImage.draw(sb, step==3?1:step);
		FontUtil.draw(sb, hero.name, 22, Color.WHITE, 220, 486, 1000);
		FontUtil.draw(sb,hero.prop.get("maxhp")+"", 20, blue, 465, 437, 1000);
		FontUtil.draw(sb,hero.prop.get("maxmp")+"", 20, blue, 465, 397, 1000);
		FontUtil.draw(sb,hero.prop.get("attack")+"", 20, blue, 605, 437, 1000);
		FontUtil.draw(sb,hero.prop.get("magicAttack")+"", 20, blue, 605, 397, 1000);
		FontUtil.draw(sb,hero.prop.get("defense")+"", 20, blue, 744, 437, 1000);
		FontUtil.draw(sb,hero.prop.get("magicDefense")+"", 20, blue, 744, 397, 1000);
		FontUtil.draw(sb,hero.prop.get("speed")+"", 20, blue, 884, 437, 1000);
		FontUtil.draw(sb,hero.prop.get("hit")+"", 20, blue, 884, 397, 1000);
		FontUtil.draw(sb,hero.prop.get("level")+"", 30, blue, 154+60/2-FontUtil.getTextWidth(hero.prop.get("level")+"", 30), 497, 1000);
		if(equip!=null){
			FontUtil.draw(sb,equip.statusName, 20, Color.WHITE, 400, 96, 1000);
			FontUtil.draw(sb,equip.illustration, 17, blue, 390, 65, 540);
			
			if(!equip.disable){
				drawChange(sb,"maxhp",515,434);
				drawChange(sb,"maxmp",515,394);
				drawChange(sb,"attack",653,434);
				drawChange(sb,"magicAttack",653,394);
				drawChange(sb,"defense",792,434);
				drawChange(sb,"magicDefense",792,394);
				drawChange(sb,"speed",932,434);
				drawChange(sb,"hit",932,394);
			}
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
	
	private int getDef(String prop){
		Hero hero=HeroControler.heros.get(currentSelectHero);
		if(hero.getEquipValue(equip.equipType, prop)!=0)
			return hero.prop.get(prop)-hero.getEquipValue(equip.equipType, prop)+equip.prop.get(prop);
		else
			return equip.prop.get(prop);
	}
	
	private void drawChange(SpriteBatch sb,String prop,int x,int y){
		Integer def=getDef(prop);
		if(def!=0){
			FontUtil.draw(sb, def>0?"+"+def:def+"",12,def>0?green:red, x-FontUtil.getTextWidth(def>0?"+"+def:def+"", 12, -3)+13, y-1, 100, -6, 0);
			sb.draw(def>0?up:down, x+20, y-12);
		}
	}
	
	public void onkeyTyped(char character) {
		stage.keyTyped(character);
	}

	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode)
			if(!olist.isVisible())
				this.disposed=true;
			else
				cancel.run();
		stage.keyDown(keyCode);
	}

	public void onkeyUp(int keyCode) {
		stage.keyUp(keyCode);
	}

	public void dispose() {
		up.dispose();
		down.dispose();
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
			gengrateEList();
		}
	}
	
	public void prevHero(){
		if(currentSelectHero!=0){
			currentSelectHero--;
			generateHero(currentSelectHero);
			gengrateEList();
		}
	}

	@Override
	public boolean scrolled(int amount) {
		return stage.scrolled(amount);
	}
	
	private void gengrateEList(){
		currentSelectEquip=sellist.getSelectedIndex();
		sellist.clearItems();
		Array<EQuipSelect> item = sellist.getItems();
		Hero hero=HeroControler.heros.get(currentSelectHero);
		item.add(new EQuipSelect(Equipment.EQUIP_WEAPON,hero.getEquipName("weapon"),"武器"));
		item.add(new EQuipSelect(Equipment.EQUIP_CLOTHES,hero.getEquipName("clothes"),"衣服"));
		item.add(new EQuipSelect(Equipment.EQUIP_SHOES,hero.getEquipName("shoes"),"鞋子"));
		item.add(new EQuipSelect(Equipment.EQUIP_ORNAMENT1,hero.getEquipName("ornament1"),"装饰"));
		item.add(new EQuipSelect(Equipment.EQUIP_ORNAMENT2,hero.getEquipName("ornament2"),"装饰"));
		sellist.setSelectedIndex(currentSelectEquip);
		elist.clearItems();
		Array<Equipment> eitem=elist.getItems();
		eitem.add(new EmptyEquip());
		if(currentSelectEquip!=-1){
			GameViews.global.getItems(Equipment.class).forEach((e)->{
				if((e.equipType.equals(item.get(currentSelectEquip).type)) && (e.onlyFor==null || e.onlyFor==hero.getClass()))
					eitem.add(e);
			});
			equip=new TipEquip("提示", sellist.getSelected().tip);
		}
			
	}
	
	class EQuipSelect{
		String type;
		String name;
		String tip;
		public EQuipSelect(String type, String name, String tip) {
			this.type = type;
			this.name = name;
			this.tip = tip;
		}

		public String toString(){
			return name;
		}
	}
}
