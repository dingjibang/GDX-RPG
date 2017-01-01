package com.rpsg.rpg.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 *	GDX-RPG 游戏工具类 
 */
public class Game {
	public static final int STAGE_WIDTH = 1280, STAGE_HEIGHT = 720;
	
	public static int width(){
		return Gdx.graphics.getWidth();
	}
	
	public static int height(){
		return Gdx.graphics.getHeight();
	}
	
	/**
	 * 获取一个默认{@link Stage}，他将和{@link Views#batch}共用一个画笔。<br>
	 * 要注意的是，这个画笔的{@link com.badlogic.gdx.graphics.g2d.Batch#setTransformMatrix(com.badlogic.gdx.math.Matrix4) transform}被改变的话，将可能导致接下来绘制的东西坐标出现异常。
	 */
	public static Stage stage(){
		return new Stage(new ScalingViewport(Scaling.stretch, Game.STAGE_WIDTH, Game.STAGE_HEIGHT, new OrthographicCamera()), Views.batch);
	}
}
