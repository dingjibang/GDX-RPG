/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.rpsg.rpg.system.ui;



import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ArraySelection;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectSet;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Equipment;
import com.rpsg.rpg.object.base.items.Item;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.utils.display.FontUtil;

/**
 * GDX-RPG List组件
 * 使用FontUtil技术，无需缓存字体，可以在List插入图片。
 */
public class List<T> extends Widget implements Cullable {
	private ListStyle style;
	private final Array<T> items = new Array<T>();
	final ArraySelection<T> selection = new ArraySelection<T>(items);
	private Rectangle cullingArea;
	private float prefWidth, prefHeight;
	private float itemHeight;
	private float textOffsetX, textOffsetY;

	public List (Skin skin) {
		this(skin.get(ListStyle.class));
	}

	public List (Skin skin, String styleName) {
		this(skin.get(styleName, ListStyle.class));
	}
	public List (ListStyle style) {
		selection.setActor(this);
		selection.setRequired(true);
		List<T> that=this;
		setStyle(style);
		setSize(getPrefWidth(), getPrefHeight());
		addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if (pointer == 0 && button != 0) return false;
				if (selection.isDisabled()) return false;
				List.this.touchDown(y);
				getStage().setKeyboardFocus(that);
				return true;
			}
			public boolean keyDown (InputEvent event, int keycode) {
				if(keycode==Keys.DOWN && getSelectedIndex()<getItems().size-1){
					setSelectedIndex(getSelectedIndex()+1);
					if(getParent() instanceof ScrollPane){
						ScrollPane pane=(ScrollPane)getParent();
						pane.setScrollY(pane.getScrollY()+getItemHeight());
					}
				}
				if(keycode==Keys.UP && getSelectedIndex()>0){
					setSelectedIndex(getSelectedIndex()-1);
					if(getParent() instanceof ScrollPane){
						ScrollPane pane=(ScrollPane)getParent();
						pane.setScrollY(pane.getScrollY()-getItemHeight());
					}					
				}
				if(dbclick!=null && (keycode==Keys.ENTER || keycode==Keys.Z)){
					dbclick.run();
				}
				return true;
			}
			public boolean keyUp (InputEvent event, int keycode) {
				return true;
			}
		});
	}

	public void setStyle (ListStyle style) {
		if (style == null) throw new IllegalArgumentException("style cannot be null.");
		this.style = style;
		invalidateHierarchy();
	}
	
	int lastIndex=-1;
	int delaydbClickTime=0;
	
	void touchDown (float y) {
		if (items.size == 0) return;
		float height = getHeight();
		if (style.background != null) {
			height -= style.background.getTopHeight() + style.background.getBottomHeight();
			y -= style.background.getBottomHeight();
		}
		int index = (int)((height - y) / itemHeight);
		index = Math.max(0, index);
		index = Math.min(items.size - 1, index);
		selection.choose(items.get(index));
		if(lastIndex==index && delaydbClickTime<30){
			if(dbclick!=null)
				dbclick.run();
		}else
			delaydbClickTime=0;
		lastIndex=index;
		if(click!=null)
			click.run();
	}
	Runnable dbclick,click;
	public List<T> onDBClick(Runnable run){
		dbclick=run;
		return this;
	}
	
	public List<T> onClick(Runnable run){
		click=run;
		return this;
	}
	
	public void layout () {
		final BitmapFont font = style.font;
		final Drawable selectedDrawable = style.selection;
		if(itemHeight==0){
			itemHeight = font.getCapHeight() - font.getDescent() * 2;
			itemHeight += selectedDrawable.getTopHeight() + selectedDrawable.getBottomHeight();
		}

		textOffsetX = selectedDrawable.getLeftWidth();
		textOffsetY = selectedDrawable.getTopHeight() - font.getDescent();

		prefWidth = 0;
		for (int i = 0; i < items.size; i++) {
			TextBounds bounds = font.getBounds(items.get(i).toString());
			prefWidth = Math.max(bounds.width, prefWidth);
		}
		prefWidth += selectedDrawable.getLeftWidth() + selectedDrawable.getRightWidth();
		prefHeight = items.size * itemHeight;

		Drawable background = style.background;
		if (background != null) {
			prefWidth += background.getLeftWidth() + background.getRightWidth();
			prefHeight += background.getTopHeight() + background.getBottomHeight();
		}
	}

	@Override
	public void draw (Batch batch, float parentAlpha) {
		delaydbClickTime++;
		validate();

		BitmapFont font = style.font;
		Drawable selectedDrawable = style.selection;
		Color fontColorSelected = style.fontColorSelected;
		Color fontColorUnselected = style.fontColorUnselected;

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		float x = getX(), y = getY(), width = getWidth(), height = getHeight();
		float itemY = height;

		Drawable background = style.background;
		if (background != null) {
			background.draw(batch, x, y, width, height);
			float leftWidth = background.getLeftWidth();
			x += leftWidth;
			itemY -= background.getTopHeight();
			width -= leftWidth + background.getRightWidth();
		}

		font.setColor(fontColorUnselected.r, fontColorUnselected.g, fontColorUnselected.b, fontColorUnselected.a * parentAlpha);
		for (int i = 0; i < items.size; i++) {
			if (cullingArea == null || (itemY - itemHeight <= cullingArea.y + cullingArea.height && itemY >= cullingArea.y)) {
				T item = items.get(i);
				boolean selected = selection.contains(item);
				if (selected) {
					selectedDrawable.draw(batch, x+20, y + itemY - itemHeight, width-10, itemHeight);
					font.setColor(fontColorSelected.r, fontColorSelected.g, fontColorSelected.b, fontColorSelected.a * parentAlpha);
				}
				FontUtil.draw(((SpriteBatch)batch), item.toString(), 20,selected?blue:Color.WHITE, (int)(x + textOffsetX + 20), (int)(y + itemY - textOffsetY)+1-padTop, 500);
				int offset=0;
				if(item instanceof Equipment){
					if(!((Equipment)item).throwable){
						Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"throwable.png").draw(batch, x+width-61+offset, y+itemY - itemHeight+6+padTop, 61, 17);
						offset=-70;
					}
					if(((Equipment)item).onlyFor!=null){
						Res.getDrawable(Setting.GAME_RES_IMAGE_MENU_EQUIP+"only.png").draw(batch, x+width-61+offset, y+itemY - itemHeight+6+padTop, 61, 17);
						offset=-70;
					}
				}
				if(item instanceof Item && ((Item)item).count!=0)
					FontUtil.draw((SpriteBatch)batch, ""+((Item)item).count, 20, (selected?blue:Color.WHITE), (int)(x+width-61+offset),(int)(y + itemY - textOffsetY)+1-padTop, 100,-3,0);
				if (selected) {
					font.setColor(fontColorUnselected.r, fontColorUnselected.g, fontColorUnselected.b, fontColorUnselected.a
						* parentAlpha);
				}
			} else if (itemY < cullingArea.y) {
				break;
			}
			itemY -= itemHeight;
		}
		
	}
	Color blue=new Color(80f/255f,111f/255f,187f/255f,1);
	public ArraySelection<T> getSelection () {
		return selection;
	}

	/** Returns the first selected item, or null. */
	public T getSelected () {
		return selection.first();
	}

	/** Sets the selection to only the passed item, if it is a possible choice. */
	public void setSelected (T item) {
		if (items.contains(item, false))
			selection.set(item);
		else if (selection.getRequired() && items.size > 0)
			selection.set(items.first());
		else
			selection.clear();
	}

	/** @return The index of the first selected item. The top item has an index of 0. Nothing selected has an index of -1. */
	public int getSelectedIndex () {
		ObjectSet<T> selected = selection.items();
		return selected.size == 0 ? -1 : items.indexOf(selected.first(), false);
	}

	/** Sets the selection to only the selected index. */
	public void setSelectedIndex (int index) {
		if (index < -1 || index >= items.size)
			throw new IllegalArgumentException("index must be >= -1 and < " + items.size + ": " + index);
		if (index == -1) {
			selection.clear();
		} else {
			selection.set(items.get(index));
		}
	}

	public void setItems (@SuppressWarnings("unchecked") T... newItems) {
		if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
		float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

		items.clear();
		items.addAll(newItems);
		selection.validate();

		invalidate();
		if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight()) invalidateHierarchy();
	}

	/** Sets the current items, clearing the selection if it is no longer valid. If a selection is
	 * {@link ArraySelection#getRequired()}, the first item is selected. */
	public void setItems (Array<T> newItems) {
		if (newItems == null) throw new IllegalArgumentException("newItems cannot be null.");
		float oldPrefWidth = getPrefWidth(), oldPrefHeight = getPrefHeight();

		items.clear();
		items.addAll(newItems);
		selection.validate();

		invalidate();
		if (oldPrefWidth != getPrefWidth() || oldPrefHeight != getPrefHeight()) invalidateHierarchy();
	}

	public void clearItems () {
		if (items.size == 0) return;
		items.clear();
		selection.clear();
		invalidateHierarchy();
	}

	public Array<T> getItems () {
		return items;
	}

	public float getItemHeight () {
		return itemHeight;
	}
	
	public void setItemHeight (float itemHeight) {
		this.itemHeight=itemHeight;
	}
	public int padTop=0;

	public float getPrefWidth () {
		validate();
		return prefWidth;
	}

	public float getPrefHeight () {
		validate();
		return prefHeight;
	}

	public void setCullingArea (Rectangle cullingArea) {
		this.cullingArea = cullingArea;
	}

}
