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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Scaling;

/** A button with a child {@link Image} to display an image. This is useful when the button must be larger than the image and the
 * image centered on the button. If the image is the size of the button, a {@link Button} without any children can be used, where
 * the {@link ButtonStyle#up}, {@link ButtonStyle#down}, and {@link ButtonStyle#checked} nine patches define
 * the image.
 * @author Nathan Sweet */
public class ImageButton extends Button {
	private final Image image;
	private ImageButtonStyle style;
	Runnable run;
	
	public ImageButton (Skin skin) {
		this(skin.get(ImageButtonStyle.class));
	}

	public ImageButton (Skin skin, String styleName) {
		this(skin.get(styleName, ImageButtonStyle.class));
	}
	
	public ImageButton pos(int x,int y){
		setPosition(x, y);
		return this;
	}

	public ImageButton (ImageButtonStyle style) {
		super(style);
		image = new Image();
//		image.setScaling(Scaling.fit);
		add(image);
		setStyle(style);
		setSize(getPrefWidth(), getPrefHeight());
		addListener(new InputListener(){
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				if(run!=null)
					run.run();
				return true;
			}
		});
	}
	
	public ImageButton onClick(Runnable run){
		this.run=run;
		return this;
	}
	
	public ImageButton click(){
		if(this.run!=null)
			this.run.run();
		return this;
	}
	
	public ImageButton (Drawable imageUp) {
		this(new ImageButtonStyle(null, null, null, imageUp, null, null));
	}

	public ImageButton (Drawable imageUp, Drawable imageDown) {
		this(new ImageButtonStyle(null, null, null, imageUp, imageDown, null));
	}

	public ImageButton (Drawable imageUp, Drawable imageDown, Drawable imageChecked) {
		this(new ImageButtonStyle(null, null, null, imageUp, imageDown, imageChecked));
	}
	
	Image fg;
	public ImageButton setFg(Image fg){
		this.fg=fg;
		return this;
	}

	public void setStyle (ButtonStyle style) {
		if (!(style instanceof ImageButtonStyle)) throw new IllegalArgumentException("style must be an ImageButtonStyle.");
		super.setStyle(style);
		this.style = (ImageButtonStyle)style;
		if (image != null) updateImage();
	}

	public ImageButtonStyle getStyle () {
		return style;
	}

	private void updateImage () {
		Drawable drawable = null;
		if (isDisabled() && style.imageDisabled != null)
			drawable = style.imageDisabled;
		else if (isPressed() && style.imageDown != null)
			drawable = style.imageDown;
		else if (isChecked() && style.imageChecked != null)
			drawable = (style.imageCheckedOver != null && isOver()) ? style.imageCheckedOver : style.imageChecked;
		else if (isOver() && style.imageOver != null)
			drawable = style.imageOver;
		else if (style.imageUp != null) //
			drawable = style.imageUp;
		image.setDrawable(drawable);
	}

	public void draw (Batch batch, float parentAlpha) {
		updateImage();
		super.draw(batch, parentAlpha);
		if(fg!=null){
			fg.setX(getX()+(getPrefWidth()/2-fg.getWidth()/2));
			fg.setY(getY()+(getHeight()/2-fg.getHeight()/2));
			fg.setColor(getColor());
			fg.draw(batch);
		}
	}

	public Image getImage () {
		return image;
	}

	public Cell<?> getImageCell () {
		return getCell(image);
	}

	/** The style for an image button, see {@link ImageButton}.
	 * @author Nathan Sweet */
	static public class ImageButtonStyle extends ButtonStyle {
		/** Optional. */
		public Drawable imageUp, imageDown, imageOver, imageChecked, imageCheckedOver, imageDisabled;

		public ImageButtonStyle () {
		}

		public ImageButtonStyle (Drawable up, Drawable down, Drawable checked, Drawable imageUp, Drawable imageDown,
			Drawable imageChecked) {
			super(up, down, checked);
			this.imageUp = imageUp;
			this.imageDown = imageDown;
			this.imageChecked = imageChecked;
		}

		public ImageButtonStyle (ImageButtonStyle style) {
			super(style);
			this.imageUp = style.imageUp;
			this.imageDown = style.imageDown;
			this.imageOver = style.imageOver;
			this.imageChecked = style.imageChecked;
			this.imageCheckedOver = style.imageCheckedOver;
			this.imageDisabled = style.imageDisabled;
		}

		public ImageButtonStyle (ButtonStyle style) {
			super(style);
		}
	}
}
