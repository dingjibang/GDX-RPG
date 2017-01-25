package com.rpsg.rpg.ui.widget;

import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.rpsg.rpg.core.Res;

/**
 * GDX-RPG 按钮
 */
public class Button extends com.badlogic.gdx.scenes.scene2d.ui.Button{
	/**
	 * 快速创建一个按钮，按钮的文件命名为xxx.png & xxx_p.png，其中第一个为默认样式，第二个为按下去时候的样式，第二个不需要传但是要遵守文件命名格式
	 */
	public Button(String buttonPath){
		super(Res.getDrawable(buttonPath), Res.getDrawable(buttonPath.replaceAll("\\.png", "_p.png")));
	}

	public Button(Drawable up, Drawable down){
		super(up, down);
	}
}
