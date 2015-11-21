package com.rpsg.rpg.view.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.ItemType;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.DefaultIView;
import com.rpsg.rpg.system.ui.Icon;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.ImageList;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.hover.ThrowItemView;
import com.rpsg.rpg.view.hover.UseItemView;

public class ItemView extends DefaultIView{
	
	Group inner,description;
	ImageList ilist;
	String currentFilter = Item.class.getSimpleName();
	ImageButton takeButton,throwButton;
	Image take=Res.get(Setting.IMAGE_MENU_ITEM+"but_use.png"),off=Res.get(Setting.IMAGE_MENU_EQUIP+"but_off.png").a(.3f),throwImg=Res.get(Setting.IMAGE_MENU_EQUIP+"but_remove.png").a(.3f);
	public ItemView init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()),MenuView.stage.getBatch());
		
		//topBar
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(755,60).setColor(Color.valueOf("3d3d3d")).setPosition(240,486).appendTo(stage);
		
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"info.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"info_p.png");// help button press
		cstyle.font = Res.font.get(20);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(755,282).setColor(.2f,.2f,.2f,.85f).setPosition(240,178).appendTo(stage);
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(155,155).setColor(0,0,0,.55f).setPosition(240,12).appendTo(stage);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(597,103).setColor(0,0,0,.55f).setPosition(398,64).appendTo(stage);
		
		$.add(takeButton=new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(take)).appendTo(stage).setSize(297,49).setPosition(398, 12).getCell().prefSize(297,49);
		
		$.add(throwButton=new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(throwImg)).appendTo(stage).setSize(297,49).setPosition(698, 12).getCell().prefSize(297,49);
		
		inner = (Group) $.add(new Group()).appendTo(stage).getItem();
		description = (Group) $.add(new Group()).appendTo(stage).getItem();
		
		generate(true);
		
		stage.setDebugAll(!true || Setting.persistence.uiDebug);
		
		return this;
	}
	
	private void generate(boolean animate) {
		float oldTop = 0f;
		if(ilist!=null)
			oldTop = ilist.getScrollPercentY();
		inner.clear();
		description.clear();
		
		//generate topbar
		int offset = -14, pad = 119;
		for(final ItemType type:ItemType.values()){
			if(type.name().equalsIgnoreCase(currentFilter)){
				inner.addActor(new Image(Setting.UI_BASE_IMG).size(0, 73).color(Color.valueOf("8f171700")).position(135+(offset+=pad), 479).action(Actions.parallel(Actions.fadeIn(animate?.1f:0),Actions.sizeBy(160, 0,animate?.07f:0))));
				offset+=70;
				inner.addActor(new Image(Setting.IMAGE_MENU_ITEM+"i_"+type.name()+".png").position(98+offset, 500).a(0).action(Actions.fadeIn(animate?.1f:0)));
				$.add(new Label(type.value(),26)).setPosition(143+offset, 500).setColor(1,1,1,0).appendTo(inner).setAlpha(0).addAction(Actions.fadeIn(animate?.1f:0));
			}else{
				inner.addActor(new Image(Setting.UI_BASE_IMG).size(90, 60).color(Color.valueOf("3d3d3d")).position(135+(offset+=pad), 486).onClick(new Runnable(){
					public void run() {
						currentFilter = type.name();
						generate(true);
					}
				}));
				inner.addActor(new Image(Setting.IMAGE_MENU_ITEM+"i_"+type.name()+".png").position(140+offset+25, 500).disableTouch());
			}
		}
		
		ilist=((ImageList) $.add(new ImageList(getItems(currentFilter))).setSize(735, 266).setPosition(248, 185).appendTo(inner).getItem());
		
		ilist.generate().onChange(new CustomRunnable<Icon>() {
			public void run(Icon t) {
				
				description.clear();
				
				if(!t.enable || t.item instanceof Equipment){
					takeButton.setFg(take.a(.3f)).fgSelfColor(true).onClick(new Runnable(){public void run() {}});
				}else{
					if(t.current)
						takeButton.setFg(off.a(1)).onClick(new Runnable(){public void run() {
							RPG.ctrl.item.takeOff(parent.current, currentFilter);
							generate(false);
						}});
					else
						takeButton.setFg(take.a(1)).fgSelfColor(true).onClick(new Runnable(){public void run() {
							useEquip();
						}});
				}
				
				if(!t.item.throwable || t.current){
					throwButton.setFg(throwImg.a(.3f)).fgSelfColor(true).onClick(new Runnable(){public void run() {}});
				}else{
					throwButton.setFg(throwImg.a(1)).fgSelfColor(true).onClick(new Runnable(){public void run() {
						removeEquip();
					}});
				}
				
				Label name;
				$.add(name = new Label(t.item.name,30)).setPosition(410, 130).setColor(Color.valueOf("ff6600")).appendTo(description);
				$.add(new Label(("("+"拥有"+t.item.count+"个")+")",16).position((int) (name.getX()+name.getWidth()+15), 130)).appendTo(description).setColor(Color.LIGHT_GRAY);
				ScrollPane pane = new ScrollPane(new Label(t.item.description,17).warp(true).markup(true));
				pane.setupOverscroll(20, 200, 200);
				pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
				pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
				pane.setFadeScrollBars(false);
				pane.layout();
				$.add(pane).setSize(578, 60).setPosition(410, 68).appendTo(description);
				$.add(new Image(t)).setPosition(246,18).setSize(143,143).appendTo(description);
				
			}
		});
		
		ilist.setScrollPercentY(oldTop!=oldTop?0:oldTop);
	}
	
	private List<Icon> getItems(String type){
		List<BaseItem> baseItems = RPG.ctrl.item.search(type);
		
		List<Icon> io= new ArrayList<>();
		for(BaseItem e:baseItems){
			Icon obj=new Icon().generateIcon(e, true);
			io.add(obj);
		}
		
		return io;
	}
	
	public void draw(SpriteBatch batch) {
		stage.draw();
	}
	
	private void useEquip(){
		if(ilist.getCurrent()==null)
			return;
		BaseItem item = ilist.getCurrent().item;
		if(item!=null )
			RPG.popup.add(UseItemView.class,new HashMap<Object, Object>(){private static final long serialVersionUID = 1L;{
				put("title","使用物品");
				put("width",100);
				put("item",ilist.getCurrent());
				put("callback",new Runnable() {
					public void run() {
						generate(false);
					}
				});
			}});
	}
	
	@SuppressWarnings("serial")
	private void removeEquip(){
		if(ilist.getCurrent()!=null && !ilist.getCurrent().current && ilist.getCurrent().item!=null)
			
		RPG.popup.add(ThrowItemView.class,new HashMap<Object, Object>(){{
			put("title","丢弃物品");
			put("width",100);
			put("item",ilist.getCurrent());
			put("callback",new CustomRunnable<Integer>() {
				public void run(Integer t) {
					RPG.putMessage("成功丢弃道具 "+ilist.getCurrent().item.name+" "+t+" 个", Color.RED);
					RPG.ctrl.item.remove(ilist.getCurrent().item, t);
					generate(false);
				}
			});
		}});
		
	}

	public void logic() {
		stage.act();
	}
	
	public void onkeyDown(int keyCode) {
		if(Keys.ESCAPE==keyCode || Keys.X==keyCode)
			this.disposed=true;
		stage.keyDown(keyCode);
	}

}
