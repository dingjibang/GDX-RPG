package com.rpsg.rpg.utils.display;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.Alert;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;

public class AlertUtil {
	
	public static Image Yellow,Green,Red;
	
	public static NinePatch box;
	
	public List<Alert> list=new ArrayList<Alert>();
	
	public static Stage stage = new Stage();
	
	public AlertUtil() {
		Yellow=new Image(Setting.IMAGE_GLOBAL+"t.png");
		Green=new Image(Setting.IMAGE_GLOBAL+"o.png");
		Red=new Image(Setting.IMAGE_GLOBAL+"x.png");
		box=GameUtil.processNinePatchFile(Setting.IMAGE_GLOBAL+"box.9.png");
		Logger.info("提示模块载入完成。");
	}
	
	public void add(String alert,Image type){
		list.add(new Alert(new Image(type), alert));
	}
	
	public void add(String alert,Image type,int fontsize){
		list.add(new Alert(new Image(type), alert, fontsize));
	}
	
	public void draw(SpriteBatch sb){
		List<Alert> removeList=new ArrayList<>();
		for (Alert a:list)
			if(a.dispose)
				removeList.add(a);
		list.removeAll(removeList);
		for(int i=0;i<list.size();i++){
			list.get(i).draw(sb, i*60,stage);
		}
		stage.draw();
		sb.setColor(1,1,1,1);
	}
	
}
