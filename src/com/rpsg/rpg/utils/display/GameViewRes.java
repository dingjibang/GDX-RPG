package com.rpsg.rpg.utils.display;


import box2dLight.RayHandler;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.rpsg.rpg.system.base.TmxMapLoader;

public class GameViewRes {
	public static Stage stage = new Stage();
	public static OrthographicCamera camera = (OrthographicCamera) stage.getCamera();
	public static RayHandler ray = new RayHandler(null);
	public static World world ;
	public static TmxMapLoader mapLoader=new TmxMapLoader(); 
	public static AssetManager ma=new AssetManager();
	
	static{
		ma.setLoader(TiledMap.class, GameViewRes.mapLoader);
	}
}
