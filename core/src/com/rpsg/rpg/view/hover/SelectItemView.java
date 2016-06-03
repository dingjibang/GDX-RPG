package com.rpsg.rpg.view.hover;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Icon;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.ImageButton;
import com.rpsg.rpg.system.ui.ImageList;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;

@SuppressWarnings("unchecked")
public class SelectItemView extends HoverView{

	Group inner,description,outer;
	ImageList ilist;
	String currentFilter = Item.class.getSimpleName();
	ImageButton takeButton;
	Image take=Res.get(Setting.IMAGE_MENU_ITEM+"but_use.png"),off=Res.get(Setting.IMAGE_MENU_EQUIP+"but_off.png").a(.3f),throwImg=Res.get(Setting.IMAGE_MENU_EQUIP+"but_remove.png").a(.3f);
	
	Hero hero;
	CustomRunnable<Item> callback;
	
	public void init() {
		hero = (Hero)param.get("hero");
		callback = (CustomRunnable<Item>)param.get("callback");
		
		$.add(Res.get(Setting.UI_BASE_IMG)).appendTo(stage).setColor(.3f,.3f,.3f,.5f).setSize(GameUtil.stage_width, GameUtil.stage_height).click(()->disposed = true);
		$.add(Res.get(Setting.UI_BASE_IMG)).appendTo(stage).setColor(.25f,.25f,.25f,1f).setPosition(100, 10).setSize(835,550);
		outer = $.add(new Group()).appendTo(stage).setX(-100).setY(20).getItem(Group.class);
		
		$.add(Res.get("使用物品", 45)).appendTo(outer).setPosition(240,477);
		
		ImageButton exit=new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"file_exit.png"),Res.getDrawable(Setting.IMAGE_MENU_SYSTEM+"file_exit_active.png"));
		exit.setPosition(862, 525);
		exit.addAction(Actions.moveTo(862, 500, 0.1f));
		exit.addListener(new InputListener() {
			public void touchUp(InputEvent event, float x, float y, int pointer, int b) {
				disposed = true;
			}
			
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int b) {
				return true;
			}
		});
		stage.addActor(exit);
		
		CheckBoxStyle cstyle=new CheckBoxStyle();
		cstyle.checkboxOff=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"info.png");
		cstyle.checkboxOn=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"info_p.png");// help button press
		cstyle.font = Res.font.get(20);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(755,282).setColor(0,0,0,.55f).setPosition(240,178).appendTo(outer);
		
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(155,155).setColor(0,0,0,.55f).setPosition(240,12).appendTo(outer);
		$.add(Res.get(Setting.UI_BASE_IMG)).setSize(597,103).setColor(0,0,0,.55f).setPosition(398,64).appendTo(outer);
		
		$.add(takeButton = new ImageButton(Res.getDrawable(Setting.IMAGE_MENU_GLOBAL+"button.png"),Setting.UI_BUTTON).setFg(take)).appendTo(outer).setSize(597,49).setPosition(398, 12).getCell().prefSize(597,49);
		
		
		inner = (Group) $.add(new Group()).appendTo(outer).getItem();
		
		description = (Group) $.add(new Group()).appendTo(outer).getItem();
		
		float oldTop = 0f;
		if(ilist!=null)
			oldTop = ilist.getScrollPercentY();
		inner.clear();
		description.clear();
		
		
		ilist=((ImageList) $.add(new ImageList(getItems(currentFilter))).setSize(735, 266).setPosition(248, 185).appendTo(inner).getItem());
		
		ilist.generate().onChange(t->{
			description.clear();
			
			if(true){
				takeButton.onClick(()->{
					callback.run((Item)t.getItem());
					disposed = true;
				});
			}
			
			Label name;
			$.add(name = new Label(t.getItem().name,30)).setPosition(410, 130).setColor(Color.valueOf("ff6600")).appendTo(description);
			$.add(new Label(("("+"拥有"+t.getItem().count+"个")+")",16).position((int) (name.getX()+name.getWidth()+15), 130)).appendTo(description).setColor(Color.LIGHT_GRAY);
			ScrollPane pane = new ScrollPane(new Label(t.getItem().description,17).warp(true).markup(true));
			pane.setupOverscroll(20, 200, 200);
			pane.getStyle().vScroll=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbar.png");
			pane.getStyle().vScrollKnob=Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"mini_scrollbarin.png");
			pane.setFadeScrollBars(false);
			pane.layout();
			$.add(pane).setSize(578, 60).setPosition(410, 68).appendTo(description);
			$.add(new Image(t)).setPosition(246,18).setSize(143,143).appendTo(description);
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
	
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.ESCAPE)
			disposed = true;
		return super.keyDown(keycode);
	}
}
