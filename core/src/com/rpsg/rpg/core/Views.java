package com.rpsg.rpg.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.ui.view.ParameterizableView;
import com.rpsg.rpg.ui.view.View;
import com.rpsg.rpg.util.Timer;
import com.rpsg.rpg.view.LoadView;
import com.rpsg.rpg.view.LogoView;

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
		Log.i("=====================================================");
		Log.i(" _____ _______   __     _____  _____   _____"); 
		Log.i(" / ____|  __ \\ \\ / /    |  __ \\|  __ \\ / ____|");
		Log.i("| |  __| |  | \\ V /_____| |__) | |__) | |  __ ");
		Log.i("| | |_ | |  | |> <______|  _  /|  ___/| | |_ |");
		Log.i("| |__| | |__| / . \\     | | \\ \\| |    | |__| |");
		Log.i(" \\_____|_____/_/ \\_\\    |_|  \\_\\_|     \\_____| ");
		Log.i("");
		Log.i("=====================================================");
		Log.i(">>> Initialization <<<");
	        
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
		
		Log.i(">>> Completed <<<");
		Log.i("=====================================================");
		Log.i("");
		
		//创建载入动画
		loadView = new LoadView();
		loadView.create();
		
		//创建LOGO界面
		addView(LogoView.class);
		
	}

	/**游戏主循环*/
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
		
		//延时运行工具
		Timer.act();
		
		//依次遍历view
		//创建views的快照进行遍历
		for(int i = views.size() - 1; i >= 0; i--){
			View view = views.get(i);
			view.act();
			view.draw();
		}
		
		loadView.act();
		loadView.draw();
	}
	
	public static <T extends View> T addView(Class<T> clz) {
		return addView(clz, null);
	}
	
	/**增加一个{@link View}到控制器里*/
	public static <T extends View> T addView(Class<T> clz, Map<String, Object> param){
		try {
			T view = null;
			if(view instanceof ParameterizableView)
				view = clz.getDeclaredConstructor(Map.class).newInstance(param);
			else
				view = clz.newInstance();
			
			view.create();
			
			addView(view);
			
			return view;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void addView(View view) {
		insertViews.add(0, view);
		Log.i("Views << " + view.toString());
	}
	
	public void resize(int width, int height) {}

	public void pause() {}

	public void resume() {}

	public void dispose() {}
	
}
