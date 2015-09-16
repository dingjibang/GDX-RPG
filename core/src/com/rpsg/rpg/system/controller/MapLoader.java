package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.hero.Arisu;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.rpg.PublicNPC;
import com.rpsg.rpg.object.rpg.RPGObject;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;

public class MapLoader {
	
	public int mapWidth,mapHeight;
	
	public List<RPGObject> drawlist=new ArrayList<RPGObject>();
	public MapLayers layer ;
	public void load(GameView gv){
		//初始化角色 TODO 放到script.js里
		RPG.ctrl.hero.initControler();
		if(gv.global.first){
			gv.global.first=false;
			RPG.ctrl.hero.newHero(Arisu.class);
			RPG.ctrl.hero.addHero(Arisu.class);
//			RPG.ctrl.hero.newHero(Marisa.class);
//			RPG.ctrl.hero.addHero(Marisa.class);
//			RPG.ctrl.hero.newHero(Reimu.class);
//			RPG.ctrl.hero.addHero(Reimu.class);
//			RPG.ctrl.hero.newHero(Yuuka.class);
//			RPG.ctrl.hero.addHero(Yuuka.class);
//			RPG.ctrl.hero.newHero(Flandre.class);
		}
		RPG.ctrl.hero.initHeros(gv.stage);
		
		//设置抗锯齿
		if(Setting.persistence.antiAliasing)
			for (TiledMapTileSet ms : RPG.maps.map.getTileSets()) {
				for (TiledMapTile tile : ms) {
					tile.getTextureRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				}
			}
		;

		//获取灯光
		for(int i=0;i<RPG.maps.map.getLayers().getCount();i++){
			MapLayer m = RPG.maps.map.getLayers().get(i);
			for(MapObject obj:m.getObjects()){
				if (obj.getProperties().get("type")!=null && obj.getProperties().get("type").equals("LIGHT")){
					PointLight pl= new PointLight(gv.ray,20);
					pl.setDistance(Float.parseFloat((String)obj.getProperties().get("STRENGTH")));
					pl.setPosition((int)(((RectangleMapObject)obj).getRectangle().getX()+24),
								(int)(((RectangleMapObject)obj).getRectangle().getY()+((RectangleMapObject)obj).getRectangle().getHeight())+24);
				}
			}
		}
		
		//生成NPC
		layer=new MapLayers();
		if(gv.global.npcs.isEmpty()){
			List<MapLayer> removeList=new ArrayList<MapLayer>();
			for(int i=0;i<RPG.maps.map.getLayers().getCount();i++){
				TiledMapTileLayer bot=(TiledMapTileLayer) RPG.maps.map.getLayers().get(0);
				 MapLayer m = RPG.maps.map.getLayers().get(i);
				if(m.getObjects().getCount()!=0)
					removeList.add(m);
				for(MapObject obj:m.getObjects()){
					if(obj.getProperties().get("type")!=null && obj.getProperties().get("type").equals("NPC")){
						try {
							NPC npc;
							if(!obj.getName().equals("PUBLIC"))
							npc=(NPC)Class.forName("com.rpsg.rpg.game.object."+obj.getName()).getConstructor(String.class,Integer.class,Integer.class)
								.newInstance(
									obj.getProperties().get("IMAGE")+".png",
									(int)(((RectangleMapObject)obj).getRectangle().getWidth()),
									(int)(((RectangleMapObject)obj).getRectangle().getHeight())
								);
							else{
								String imgPath=(String) obj.getProperties().get("IMAGE");
								imgPath=imgPath==null?"empty":imgPath;
								npc=new PublicNPC((String) obj.getProperties().get("ID"),imgPath+".png",(int)(((RectangleMapObject)obj).getRectangle().getWidth()),(int)(((RectangleMapObject)obj).getRectangle().getHeight()));
							}
							npc.params=GameUtil.parseMapProperties(obj.getProperties());
							npc.init();
							if(obj.getProperties().get("ABSOLUTE")!=null && obj.getProperties().get("ABSOLUTE").equals("true"))
								npc.generateAbsolutePosition(((int)(((RectangleMapObject)obj).getRectangle().getX())),
										 (int)(((RectangleMapObject)obj).getRectangle().getY()),
										 i-removeList.size());
							else
								npc.generatePosition(((int)(((RectangleMapObject)obj).getRectangle().getX())/48),
									 (int)(bot.getHeight()-2-((RectangleMapObject)obj).getRectangle().getY()/48),
									 i-removeList.size());
							if(obj.getProperties().get("SHADOW")!=null && obj.getProperties().get("SHADOW").equals("true"))
								npc.drawShadow=true;
							if(obj.getProperties().get("LOCK")!=null && obj.getProperties().get("LOCK").equals("true"))
								GameViews.gameview.renderAble=false;
							Logger.info("NPC生成成功["+npc.position+","+npc.mapx+":"+npc.mapy+":"+npc.layer+"]");
							gv.stage.addActor(npc);
							RPG.ctrl.thread.pool.add(npc.threadPool);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			for(MapLayer lay:RPG.maps.map.getLayers()){
				boolean inc=false;
				for(MapLayer re:removeList)
					if(re==lay)
						inc=true;
				if(!inc)
					layer.add(lay);
			}
				
			RPG.ctrl.hero.generatePosition(gv.global.x,gv.global.y,gv.global.z);
		}else{
			List<MapLayer> removeList=new ArrayList<MapLayer>();
			for(int i=0;i<RPG.maps.map.getLayers().getCount();i++){
				MapLayer m=RPG.maps.map.getLayers().get(i);
				if(m.getObjects().getCount()!=0)
					removeList.add(m);
			}
			
			for(MapLayer lay:RPG.maps.map.getLayers()){
				boolean inc=false;
				for(MapLayer re:removeList)
					if(re==lay)
						inc=true;
				if(!inc)
					layer.add(lay);
			}

			@SuppressWarnings("unchecked")
			ArrayList<NPC> npcs=(ArrayList<NPC>)gv.global.npcs.clone();
			for(NPC n:npcs){
				n.scripts=new HashMap<CollideType, String>();
				n.threadPool=new LinkedList<Script>();
				n.init();
				gv.stage.addActor(n);
				RPG.ctrl.thread.pool.add(n.threadPool);
				n.images=NPC.generateImages(n.imgPath, n.bodyWidth, n.bodyHeight);
			}
		}
		gv.global.npcs.clear();
		
		
		//生成远景图
		RPG.maps.distant=new Distant(RPG.maps.map.getProperties().get("distant"));

		headHeroPointLight=new PointLight(gv.ray,200);
		headHeroPointLight.setDistance(400);
		headHeroPointLight.setSoft(true);
		
		//设置通用常量
		mapWidth= (int) (((TiledMapTileLayer) layer.get(0)).getWidth() * ((TiledMapTileLayer) layer.get(0)).getTileWidth());
		mapHeight= (int) (((TiledMapTileLayer) layer.get(0)).getHeight() * ((TiledMapTileLayer) layer.get(0)).getTileHeight());

		Logger.info("地图模块已全部加载完成。");
		
	}

	PointLight headHeroPointLight;
	
	
	public synchronized void draw(GameView gv){
		int size=RPG.maps.map.getLayers().getCount();
		SpriteBatch sb=(SpriteBatch) gv.stage.getBatch();
		
		headHeroPointLight.setPosition(RPG.ctrl.hero.getHeadHero().getX()+24,RPG.ctrl.hero.getHeadHero().getY());
		sb.setProjectionMatrix(gv.camera.combined);
		for(int i=0;i<size;i++){
			if(RPG.maps.map.getLayers().get(i).getObjects().getCount()!=0)
				continue;
			drawlist.clear();
			gv.render.setView(gv.camera);
			gv.render.render(new int[]{i});

			for(Actor a:gv.stage.getActors())
				if(a instanceof RPGObject){
					RPGObject c = (RPGObject)a;
					if(c.layer==i)
						drawlist.add(c);
				}
			Collections.sort(drawlist);
			sb.begin();
			for(RPGObject ir:drawlist){
				ir.draw(sb, 1f);
			}
			
			
			sb.end();
		}
		sb.begin();
		//draw map path
		if(path!=null){
			path.actAndDraw(sb);
		}
		sb.end();
	}
	
	private Image path;
	public void putPath(int mapx,int mapy){
		path=Res.get(Setting.IMAGE_GLOBAL+"path.png").color(Color.RED);
		$.add(path).setPosition(mapx*48,mapy*48).addAction(Actions.forever(Actions.sequence(Actions.fadeIn(0.1f),Actions.fadeOut(0.1f))));
	}
	
	public void logic(GameView gv){
	}
	
	public void dispose(){
		GameViews.gameview.stage.getActors().clear();
	}
	
	public ArrayList<NPC> getNPCs(){
		ArrayList<NPC> list=new ArrayList<NPC>();
		for(Actor a:GameViews.gameview.stage.getActors())
			if(a instanceof NPC)
				list.add((NPC)a);
		return list;
	}
}
