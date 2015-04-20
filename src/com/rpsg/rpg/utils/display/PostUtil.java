package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.bitfire.postprocessing.PostProcessor;
import com.bitfire.postprocessing.effects.Bloom;
import com.rpsg.rpg.system.ui.Label;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameViews;

public class PostUtil {
	
	static Stage stage;
	static Label name,y,m,d,yy,mm,day,map,money,jname,level,next;
	
	public static void init(){
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		stage.addActor(name=new Label("雾雨魔理沙", 42).setWidth(1000));
		stage.addActor(y=new Label("2015", 42).setWidth(1000));
		stage.addActor(m=new Label("3", 42).setWidth(1000));
		stage.addActor(d=new Label("10", 42).setWidth(1000));
		stage.addActor(yy=new Label("年", 42).setWidth(1000));
		stage.addActor(mm=new Label("月", 24).setWidth(1000));
		stage.addActor(day=new Label("白天", 42).setWidth(1000));
		stage.addActor(map=new Label("测试地图", 32).setWidth(1000));
		stage.addActor(money=new Label("10511", 42).setWidth(1000));
		stage.addActor(jname=new Label("kirisame marisa", 20).setWidth(1000));
		stage.addActor(level=new Label("LV 22", 42).setWidth(1000));
		stage.addActor(next=new Label("NEXT 330 Exp", 42).setWidth(1000));
		Logger.info("特效创建成功。");
	}
	
	public static void draw(boolean display,PostProcessor post,int height){
		FrameBuffer buffer = null;
		if(display)
			buffer=new FrameBuffer(Format.RGB565, 1024, 576, true);
		Bloom bloom=GameViews.bloom;
		bloom.setBaseIntesity(0);
		bloom.setBloomIntesity(0.75f);
		bloom.setBloomSaturation(0.8f);
		bloom.setThreshold(0.1f);
		
		if(display)
			post.render(buffer,Bloom.class);
		
		bloom.setBaseIntesity(1.2f);
		bloom.setBaseSaturation(1f);
		bloom.setBloomIntesity(0.7f);
		bloom.setBloomSaturation(1.2f);
		bloom.setThreshold(0.3f);
		if(display){
			stage.getBatch().begin();
			stage.getBatch().draw(new TextureRegion(buffer.getColorBufferTexture(),73,height,878,-height),73,0);
			stage.getBatch().end();
		}
		
		name.setPos(107, 132);
		jname.setPos(130, 100).setPad(-4).setColor(1,1,1,0.2f);
		level.setPos(210,105).setPad(-15).setColor(1,1,1,0.6f);
		next.setPos(110,77).setPad(-15).setColor(1,1,1,0.3f);
		y.setPos(360,115).setPad(-13);
		day.setPos(470,135).setColor(1,1,1,0.3f);
		yy.setPos(420,80).setColor(1,1,1,0.25f);
		m.setPos(475,90).setPad(-13);
		mm.setPos(505,75).setColor(1,1,1,0.2f);
		d.setPos(525,90).setPad(-13);
		map.setPos(445,40).setColor(1,1,1,0.7f);
		money.setPos(150, 40).setPad(-13);
		
		stage.act();
		stage.draw();
		
		if(display)
			buffer.dispose();
		
		if(display){
			
		}
	}
}
