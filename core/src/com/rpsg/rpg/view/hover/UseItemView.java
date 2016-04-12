package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Music;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.base.items.Item.ItemRange;
import com.rpsg.rpg.object.base.items.Spellcard;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HeroSelectBox;
import com.rpsg.rpg.system.ui.Icon;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.system.ui.TextButton;

public class UseItemView extends SidebarView {

	HeroSelectBox box;
	Hero current;
	Label count;
	@Override
	public void init() {
		Icon icon = (Icon) param.get("item");
		final BaseItem item = icon.item;
		
		boolean forAll = false;
		
		final boolean sc = item instanceof Spellcard;
		ItemDeadable deadable = null;
		
		if(item instanceof Item){
			forAll = ((Item)item).range == Item.ItemRange.all;
			deadable = ((Item)item).deadable;
		}
		
		if(item instanceof Spellcard){
			forAll = ((Spellcard)item).range == Item.ItemRange.all;
			deadable = ((Spellcard)item).deadable;
		}
		
		group.addActor(box = new HeroSelectBox(460,200,forAll,deadable==null?ItemDeadable.no:deadable).position(430, 240-120));
		
		Group itemInfoGroup = new Group();
		itemInfoGroup.addActor(Res.get(Setting.UI_BASE_IMG).size(460, 100).position(430, 280).a(.2f));
		itemInfoGroup.addActor(new Image(icon).x(455).y(290).scale(.4f));
		itemInfoGroup.addActor(Res.get(item.name, 33).position(595, sc?313:328).width(325).overflow(true).color(Color.valueOf("ff6600")));
		if(!sc)
			itemInfoGroup.addActor(count = Res.get("持有 "+item.count+" 个", 20).position(595, 300).width(330).overflow(true));
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
		final TextButton button = new TextButton("使用",tstyle);
		
		$.add(button.onClick(()->{
			if(box.get()!=null || (item instanceof Item && ((Item)item).range == ItemRange.all) || (item instanceof Spellcard && ((Spellcard)item).range == ItemRange.all)){
				current = box.get();
				item.user = current;
				if(sc)
					((Spellcard)item).user2 = (Hero)param.get("user2");
				boolean success = RPG.ctrl.item.use(item);
				int _count = item.count;
				if(!sc)
					count.setText("持有 "+_count+" 个");
				box.generate();
				box.set(current);
				if(success){
					box.animate();
				}else{
					RPG.putMessage("使用失败", Color.RED);
					Music.playSE("err");
				}
				if(_count<=0){
					button.setText("关闭");
					button.onClick(() -> {
                        UseItemView.this.disposed = true;
                        ((Runnable)param.get("callback")).run();
                    });
				}
			}else{
				RPG.putMessage("请先选择使用者", Color.RED);
				Music.playSE("err");
				}
			}
	)).appendTo(group).setSize(454,55).setPosition(435,40).getCell().prefSize(454,55);
		
		itemInfoGroup.setY(60);
		
		group.addActor(itemInfoGroup);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		box.dispose();
	}
}
	