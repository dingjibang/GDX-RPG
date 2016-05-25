package com.rpsg.rpg.system.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameView;

public class Distant {
	Image distant;
	float w,h,sw,sh;
	float bgScale=1.05f;
	public Distant(Object disName){
		if(disName==null)
			distant=null;
		else{
			TiledMapTileLayer layer=(TiledMapTileLayer)RPG.maps.loader.layer.get(0);
			float width=(layer.getWidth()*layer.getTileWidth());
			float height=(layer.getHeight()*layer.getTileHeight());
			distant=Res.get(Setting.IMAGE_BACKGROUND+disName);
			distant.setWidth(w=(width+GameUtil.stage_width));
			distant.setHeight(h=height+GameUtil.stage_height);
			distant.setScale(bgScale);
			distant.setX(-(GameUtil.stage_width/2));
			distant.setY(-(GameUtil.stage_height/2));
			sw=bgScale*w-w;
			sh=bgScale*h-h;
		}
	}
	
	public void draw(SpriteBatch batch,GameView gv){
		OrthographicCamera camera=gv.camera;
		if(distant!=null){
			batch.begin();
			TiledMapTileLayer layer=(TiledMapTileLayer)RPG.maps.map.getLayers().get(0);
			float width=(layer.getWidth()*layer.getTileWidth());
			float height=(layer.getHeight()*layer.getTileHeight());
			float x=camera.view.getTranslation(new Vector3()).x;
			float y=camera.view.getTranslation(new Vector3()).y;
			distant.setX(-(GameUtil.stage_width/2)+(x/width)*sw);
			distant.setY(-(GameUtil.stage_height/2)+(y/height)*sh);
			distant.draw(batch);
			batch.flush();
			batch.end();
		}
	}
}
