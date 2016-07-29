package com.rpsg.rpg.view;



import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.bitfire.postprocessing.effects.Vignette;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Persistence;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.utils.display.GameViewRes;
import com.rpsg.rpg.utils.display.SelectUtil;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.utils.game.TimeUtil;
/**
 * GameViews 游戏核心入口类
 * @author dingjibang
 *
 */
public class GameViews implements ApplicationListener {
	public static SpriteBatch batch;//全局的画笔
	
	//这种写法很傻逼……用一个state标志来表明当前gameviews去draw()、logic()哪个窗口
	//TODO 有时间改进一下啊，可以用别的方法控制而不是很傻逼的switch case
	public static final int STATE_LOGO=0;
	public static final int STATE_LOAD=1;
	public static final int STATE_TITLE=2;
	public static final int STATE_GAME_LOAD=3;
	public static final int STATE_GAME=4;
	public static int state=0;
	
	//一些view，view是什么？是我做的一个类似stage的玩意……
	//init view
	public static LogoView logoview;//logo界面的view
	public static LoadView loadview;//载入界面的view（那个少女祈祷中）
	public static TitleView titleview;//标题画面的view（咱们现在没有标题画面，所以直接跳到gameview）
	public static GameView gameview;//游戏核心的view
	
	public static SelectUtil selectUtil;//这个是对话时候的选择框，放在这很傻逼（他应该隶属于gameview而不是gameviews，到时候会弄走的 TODO）
	
	public static PostProcessor post;//高清画质，不用管
	public static Bloom bloom;//高斯模糊，不用管
	public static Vignette vignette;
	
	public GameViews() {
		
	}

	@Override
	public void create() {
		//other
		batch = new SpriteBatch();//构建画笔
		
		if(!GameUtil.isDesktop)
			Setting.persistence = Persistence.read();//如果是安卓、ios的话，会先初始化libgdx引擎，才开始读取游戏设置，而如果是桌面版，会在main方法就读取游戏设置。
		Logger.info ("底层引擎初始化成功。");
		//start init
		
		Res.init();
		GameUtil.init();
		
		Setting.init();//设置一些复用的UI组件
		RPG.ctrl.task.init();//读取成就
		RPG.ctrl.index.init();//读取图鉴
		//input
		Gdx.input.setInputProcessor(RPG.input);//注册输入
		//view
		logoview = new LogoView();
		logoview.init();//注册logoview
		
		if(Setting.persistence.errorMessage!=null && Setting.persistence.errorMessage.length()!=0){//当游戏异常退出之前，会尝试把错误信息写到Setting.persistence.errorMessage这里然后保存，然后下一次启动游戏的时候，看看这个变量是不是空的，如果不是，就把上次的异常信息显示出来。
			RPG.putMessage(Setting.persistence.errorMessage, Color.RED);
			Setting.persistence.errorMessage="";
		}
		if(!GameUtil.isDesktop)
			RPG.putMessage("检测到您的游戏环境为手机/平板，已进行相应优化", Color.GREEN);//然而并没有优化hhh（有的）
		
		selectUtil=new SelectUtil();//TODO 试着把它移到gameview而不是gameviews。
		
		//高清画质一系列的东西，不用管
		post=new PostProcessor(false, true, true);
		bloom=new Bloom((int)(Gdx.graphics.getWidth() * 0.25f), (int)(Gdx.graphics.getHeight() * 0.25f));
		post.addEffect(bloom);
		
		vignette=new Vignette(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		vignette.setIntensity(0.5f);
		post.addEffect(vignette);
		
		Logger.info("Gdx-RPG引擎初始化成功。");
		
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		System.exit(0);
	}
	
	final static String command = "maUpdate";

	@Override
	public void render() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		TimeUtil.logic();
		RPG.ctrl.task.logic();
		switch(state){//根据state来让不同的view画图。
		case STATE_LOGO:{
			logoview.logic();
			if(logoview.played){
				logoview.dispose();
				loadview=new LoadView().init();
				state=STATE_LOAD;
				titleview = new TitleView().init();
			}
			logoview.draw(batch);
			break;
		}
		case STATE_LOAD:{
			loadview.logic();
			loadview.draw(batch);
			if (titleview.inited)
				state=STATE_TITLE;
			break;
		}
		case STATE_TITLE:{
			titleview.logic();
			titleview.draw(batch);
			break;
		}
		case STATE_GAME_LOAD:{
			loadview.logic();
			loadview.draw();
		}
		case STATE_GAME:{
			gameview.logic();
			gameview.draw(batch);
			if(!GameViewRes.ma.update() | !GameViewRes.ma2.update()){
				loadview.start(command);
			}else{
				loadview.stop(command);
			}
		}
		}
	
		RPG.popup.draw();//悬浮窗口，一些特殊的置顶窗口会使用。
		if(loadview != null){
			loadview.logic();
			loadview.draw();
		}
		GameUtil.drawFPS(batch);//绘制FPS
		RPG.toast.draw();//STEP一下提示工具包
		RPG.touch.draw();
		batch.end();
		
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
}
