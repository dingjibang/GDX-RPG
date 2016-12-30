package com.rpsg.rpg.core;

import java.util.ArrayList;
import java.util.List;

import java8.util.Optional;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.rpg.util.Stream;
import com.rpsg.rpg.view.LogoView;
import com.rpsg.rpg.view.View;

/**
 * GDX-RPG 游戏入口
 * 
 * <p>本类为游戏的入口，</p>
 */
public class Views implements ApplicationListener {
	
	public static SpriteBatch batch;
	
	/**缓存已创建的view*/
	static List<View> views = new ArrayList<>();
	/**当前所显示的view**/
	static View currentView = null;
	
	/**当游戏被创建*/
	public void create() {
		//创建资源管理器
		Res.init();
		//初始化上下文
		RPG.init();
		//创建全局画笔
		batch = new SpriteBatch();
		
		//创建LOGO界面
		setView(LogoView.class);
		
	}


	public void render() {
		
	}
	
	/**
	 * 切换到某个{@link View}下
	 */
	public static void setView(Class<? extends View> clz){
		Optional<View> view = Stream.of(views).filter(v -> v.getClass().equals(clz)).findAny();
		if(view.isPresent()){
			currentView = view.get();
		}else{
			try {
				currentView = clz.newInstance();
				currentView.create();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**获取当前显示的{@link View}*/
	public static View getView(){
		return currentView;
	}
	
	public void resize(int width, int height) {}

	public void pause() {}

	public void resume() {}

	public void dispose() {}
	
}
