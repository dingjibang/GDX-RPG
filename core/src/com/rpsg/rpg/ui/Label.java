package com.rpsg.rpg.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.core.Text;

/**
 * GDX-RPG UI Label<br>
 * 在原LibGDX Label的基础上，增加了自动换行、文本超长用“...”省略，以及自动纹理管理的功能。
 */
public class Label extends com.badlogic.gdx.scenes.scene2d.ui.Label{
	
	/**是否允许超过长度字尾用“...”显示*/
	private boolean overflow = false;
	/**是否允许超过长度自动换行，不能和{@link #overflow}同时为true*/
	private boolean warp = false;
	
	private CharSequence oldText = "", overflowd = "";
	
	/**允许多彩字体*/
	private boolean enableMarkup = false;
	
	private Integer maxWidth;

	public Label(Object text, LabelStyle style) {
		super(text.toString(), style);
		setTouchable(Touchable.disabled);
	}

	public Label(Object text, int fontsize) {
		super(text.toString(), genStyle(fontsize));
		setTouchable(Touchable.disabled);
	}

	/**是否启用多彩字体*/
	public Label markup(boolean enable) {
		this.enableMarkup = enable;
		return this;
	}

	private static LabelStyle genStyle(int fontSize) {
		LabelStyle style = new LabelStyle();
		style.font = Res.text.get(fontSize);
		return style;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (warp && maxWidth != null) {
			int width = Text.getTextWidth(getStyle().font, oldText.toString());
			if (width > maxWidth)
				setWidth(maxWidth);
		}
		if (overflow && !oldText.equals(getText().toString())) {
			oldText = getText().toString();// cpy
			if (Text.getTextWidth(getStyle().font, oldText.toString()) > getWidth())
				for (int i = 0; i < oldText.length(); i++) {
					CharSequence sub = oldText.subSequence(0, oldText.length() - i);
					int width = Text.getTextWidth(getStyle().font, sub.toString());
					if (width < getWidth()) {
						overflowd = (sub.length() <= 3 ? sub : sub.subSequence(0, sub.length() - 3) + "…");
						break;
					}
				}
			else
				overflowd = oldText;
		}

		getStyle().font.getData().markupEnabled = enableMarkup;

		if (overflow) {
			setText(overflowd);
			super.draw(batch, parentAlpha);
			setText(oldText);
		} else {
			super.draw(batch, parentAlpha);
		}
	}


	/**是否允许超长换行*/
	public Label warp(boolean warp) {
		setWrap(warp);
		this.warp = warp;
		super.setWrap(warp);
		return this;
	}

	/**是否允许超长用“...”表示*/
	public Label overflow(boolean hidden) {
		this.overflow = hidden;
		return this;
	}

	public boolean overflow() {
		return overflow;
	}

	public Label right() {
		setAlignment(Align.right);
		return this;
	}

	public Label center() {
		setAlignment(Align.center);
		return this;
	}

	public Label align(int align) {
		setAlignment(align);
		return this;
	}

	public Label left() {
		setAlignment(Align.left);
		return this;
	}

	public Label text(String text) {
		setText(text);
		return this;
	}

	public Label maxWidth(int i) {
		maxWidth = i;
		return this;
	}

	public GdxQuery query() {
		return new GdxQuery(this);
	}
}
