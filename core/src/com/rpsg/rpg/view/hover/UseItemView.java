package com.rpsg.rpg.view.hover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.BaseItem;
import com.rpsg.rpg.object.base.items.Item;
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
		if(item instanceof Item)
			forAll = ((Item)item).range == Item.ItemRange.all;
		
		group.addActor(box = new HeroSelectBox(460,200,forAll).position(430, 240-120));
		
		Group itemInfoGroup = new Group();
		itemInfoGroup.addActor(Res.get(Setting.UI_BASE_IMG).size(460, 120).position(430, 280).a(.2f));
		itemInfoGroup.addActor(new Image(icon).x(455).y(300).scale(.4f));
		itemInfoGroup.addActor(Res.get(item.name, 38).position(595, 340).width(325).overflow(true).color(Color.valueOf("ff6600")));
		itemInfoGroup.addActor(count = Res.get("持有 "+item.count+" 个", 22).position(595, 310).width(330).overflow(true));
		
		TextButtonStyle tstyle = new TextButtonStyle();
		tstyle.down = Setting.UI_BUTTON;
		tstyle.up = Res.getDrawable(Setting.IMAGE_MENU_NEW_EQUIP+"throwbut.png");
		tstyle.font = Res.font.get(22);
		
		final TextButton button = new TextButton("使用",tstyle);
		
		$.add(button.onClick(new Runnable() {
		public void run() {
			if(box.get()!=null){
				current = box.get();
				item.user = current;
				RPG.ctrl.item.use(item);
				int _count = item.count;
				count.setText("持有 "+_count+" 个");
				box.generate();
				box.set(current).animate();
				if(_count<=0){
					button.setText("关闭");
					button.onClick(new Runnable() {
						public void run() {
							UseItemView.this.disposed = true;
							((Runnable)param.get("callback")).run();
						}
					});
				}
			}
		}
	})).appendTo(group).setSize(454,55).setPosition(435,40).getCell().prefSize(454,55);
		
		itemInfoGroup.setY(50);
		
		group.addActor(itemInfoGroup);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		box.dispose();
	}
}
	