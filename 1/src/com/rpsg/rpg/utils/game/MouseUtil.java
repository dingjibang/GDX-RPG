package com.rpsg.rpg.utils.game;

import java.awt.AWTException;
import java.awt.Robot;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class MouseUtil {
	private static Robot robot;
	
	public static void init(){
		try {
			if(null==robot)
			robot=new Robot();
			Gdx.input.setCursorCatched(false);
			setHWCursorVisible(false);
			Logger.info("鼠标控制模块初始化成功。");
		} catch (AWTException e) {
			e.printStackTrace();
			Logger.error("鼠标控制模块初始化失败。", e);
		}
	}
	static org.lwjgl.input.Cursor emptyCursor;
	public static void setHWCursorVisible(boolean visible) {
		if (Gdx.app.getType() != ApplicationType.Desktop && Gdx.app instanceof LwjglApplication)
			return;
		if (emptyCursor == null) {
			if (Mouse.isCreated()) {
				int min = org.lwjgl.input.Cursor.getMinCursorSize();
				IntBuffer tmp = BufferUtils.createIntBuffer(min * min);
				try {
					emptyCursor = new org.lwjgl.input.Cursor(min, min, min / 2, min / 2, 1, tmp, null);
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
			}
		}
		if (Mouse.isInsideWindow())
			try {
				Mouse.setNativeCursor(visible ? null : emptyCursor);
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
	}
	
	public static void MoveTo(int x, int y) {
		try {
			robot.mouseMove(Display.getX()+x, Display.getY()-Display.getHeight()+y);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
