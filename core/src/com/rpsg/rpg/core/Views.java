package com.rpsg.rpg.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.view.LoadView;
import com.rpsg.rpg.view.LogoView;
import com.rpsg.rpg.view.View;

/**
 * GDX-RPG 游戏入口
 * 
 * <p>本类为游戏的入口，</p>
 */
public class Views implements ApplicationListener {
	
	/**画笔*/
	public static SpriteBatch batch;
	/**输入监听器*/
	public static Input input;
	/**当前所显示的view*/
	public static List<View> views = new ArrayList<>();
	/**缓存的view，将在下一帧加入到{@link #views}里*/
	private static List<View> insertViews = new ArrayList<>();
	
	/**载入视图，当有资源被载入时，该视图将被绘制*/
	public static LoadView loadView;
	
	/**当游戏被创建*/
	public void create() {
		//创建资源管理器
		Res.init();
		//初始化上下文
		Game.init();
		//创建UI工具
		UI.init();
		//创建全局画笔
		batch = new SpriteBatch();
		//创建输入监听器
		Gdx.input.setInputProcessor(input = new Input(views));
		//创建载入动画
		loadView = new LoadView();
		loadView.create();
		
		//创建LOGO界面
		addView(LogoView.class);
		
	}


	public void render() {
		//设置OpenGL清屏颜色
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//查找views里是否有需要被删除的元素
		$.removeIf(views, View::removeable);
		
		//如果insertViews有内容，则加入到views里
		if(!insertViews.isEmpty()){
			for(View view : insertViews)
				views.add(0, view);
			insertViews.clear();
		}
		
		//依次遍历view
		//创建views的快照进行遍历
		for(View view : views){
			view.act();
			view.draw();
		}
		
		loadView.act();
		loadView.draw();
	}
	
	/**
	 * 增加一个{@link View}到控制器里
	 */
	public static void addView(Class<? extends View> clz){
		try {
			View view = clz.newInstance();
			view.create();
			
			insertViews.add(0, view);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void resize(int width, int height) {}

	public void pause() {}

	public void resume() {}

	public void dispose() {}
	
}
