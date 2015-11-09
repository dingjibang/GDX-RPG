package com.rpsg.rpg.utils.display;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Alert;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;

public class AlertUtil {
	
	public static Image Yellow,Green,Red;
	
	public static NinePatch box;
	
	private List<Alert> list=new ArrayList<Alert>();
	
	private final static Stage stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
	
	public AlertUtil() {
		Yellow=new Image(Setting.IMAGE_GLOBAL+"t.png");
		Green=new Image(Setting.IMAGE_GLOBAL+"o.png");
		Red=new Image(Setting.IMAGE_GLOBAL+"x.png");
		box=GameUtil.processNinePatchFile(Setting.IMAGE_GLOBAL+"box.9.png");
		Logger.info("提示模块载入完成。");
	}
	
	public void add(String alert,Image type){
		add(alert,type,20);
	}
	
	public void add(String alert,Image type,int fontsize){
		Alert a = new Alert(new Image(type), alert, fontsize);
		list.add(a);
		stage.addActor(a);
	}
	
	public void draw(){
		List<Alert> removeList=new ArrayList<>();
		for (Alert a:list){
			if(a.dispose){
				removeList.add(a);
				stage.getActors().removeIndex(stage.getActors().indexOf(a, true));//这是什么傻逼API，remove一个元素有这么复杂？？！，笑死
			}
		}
		list.removeAll(removeList);
		
		stage.act();
		
		for(int i=0;i<list.size();i++)
			list.get(i).update(i*60);
		
		stage.draw();
	}
	
}
