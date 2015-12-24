package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.rpg.Collide;
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.rpg.PublicNPC;
import com.rpsg.rpg.object.rpg.RPGObject;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.Light;
import com.rpsg.rpg.system.ui.Animation;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;

public class MapLoader {
	
	public int mapWidth,mapHeight;
	
	public List<RPGObject> drawlist=new ArrayList<>();
	private List<Light> lights = new ArrayList<>();
	private Map<TiledMapTileLayer,int[][]> m_MapDataCache;
	public MapLayers layer;
	public void load(GameView gv){
		clearLights();
		
		RPG.ctrl.hero.initHeros(gv.stage);
		
		//设置抗锯齿
		if(Setting.persistence.antiAliasing)
			for (TiledMapTileSet ms : RPG.maps.map.getTileSets()) {
				for (TiledMapTile tile : ms) {
					tile.getTextureRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
				}
			}

		//获取灯光
		for(int i=0;i<RPG.maps.map.getLayers().getCount();i++){
			MapLayer m = RPG.maps.map.getLayers().get(i);
			for(MapObject obj:m.getObjects()){
				if (obj.getProperties().get("type")!=null && obj.getProperties().get("type").equals("LIGHT")){
					PointLight pl= new PointLight(gv.ray,20);
					int h = obj.getProperties().get("height",Float.class).intValue();
					pl.setDistance(Float.parseFloat((String)obj.getProperties().get("STRENGTH")));
					pl.setPosition((int)(((TiledMapTileMapObject)obj).getX()+24),(int)(((TiledMapTileMapObject)obj).getY()+h+24));
					Object id = obj.getProperties().get("ID");
					addLight(id==null?null:Integer.valueOf(id.toString()), pl);
				}
			}
		}
		
		//生成NPC
		layer=new MapLayers();
		m_MapDataCache = new HashMap<TiledMapTileLayer,int[][]>();
		if(gv.global.npcs.isEmpty()){
			int remove = 0;
			for(int i=0;i<RPG.maps.map.getLayers().getCount();i++){
				TiledMapTileLayer baseLayer=(TiledMapTileLayer) RPG.maps.map.getLayers().get(0);
				 MapLayer m = RPG.maps.map.getLayers().get(i);
				if(m.getObjects().getCount()==0){
					layer.add(m);
				}else{
					remove++;
				}
				for(MapObject obj:m.getObjects()){
					if(obj.getProperties().get("type")!=null && obj.getProperties().get("type").equals("NPC") && obj instanceof TiledMapTileMapObject){
						TiledMapTileMapObject tobj = (TiledMapTileMapObject)obj;
						int w = tobj.getProperties().get("width",Float.class).intValue();
						int h = tobj.getProperties().get("height",Float.class).intValue();
						int x = tobj.getProperties().get("x",Float.class).intValue();
						int y = tobj.getProperties().get("y",Float.class).intValue();
						try {
							NPC npc;
							if(!obj.getName().equals("PUBLIC"))
							npc=(NPC)Class.forName("com.rpsg.rpg.game.object."+obj.getName()).getConstructor(String.class,Integer.class,Integer.class)
								.newInstance(obj.getProperties().get("IMAGE")+".png",w,h);
							else{
								String imgPath=(String) obj.getProperties().get("IMAGE");
								imgPath=imgPath==null?"empty":imgPath;
								npc=new PublicNPC((String) obj.getProperties().get("ID"),imgPath+".png",w,h);
							}
							npc.params=GameUtil.parseMapProperties(obj.getProperties());
							npc.init();
							
							if(obj.getProperties().get("ABSOLUTE")!=null && obj.getProperties().get("ABSOLUTE").equals("true"))
								npc.generateAbsolutePosition(x,y,i-remove);
							else
								npc.generatePosition(x/48, (int)(baseLayer.getHeight()-y/48-1),i-remove);//TODO FIX IT 
							
							if(obj.getProperties().get("SHADOW")!=null && obj.getProperties().get("SHADOW").equals("true"))
								npc.drawShadow=true;
							if(obj.getProperties().get("LOCK")!=null && obj.getProperties().get("LOCK").equals("true"))
								GameViews.gameview.renderable=false;
							
							Logger.info("NPC生成成功["+npc.position+","+npc.mapx+":"+npc.mapy+":"+npc.layer+"]");
							gv.stage.addActor(npc);
							RPG.ctrl.thread.pool.add(npc.threadPool);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			RPG.ctrl.hero.generatePosition(gv.global.x,gv.global.y+2,gv.global.z);
			
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

		//设置通用常量
		mapWidth= (int) (((TiledMapTileLayer) layer.get(0)).getWidth() * ((TiledMapTileLayer) layer.get(0)).getTileWidth());
		mapHeight= (int) (((TiledMapTileLayer) layer.get(0)).getHeight() * ((TiledMapTileLayer) layer.get(0)).getTileHeight());
		Logger.info("地图模块已全部加载完成。");
		
	}
	
	public int[][] getMapData(int zIndex)
	{
		
		TiledMapTileLayer tileLayer = (TiledMapTileLayer)layer.get(zIndex);
		return getMapData(tileLayer);
	}
	
	public int[][] getMapData(TiledMapTileLayer tileLayer)
	{
		if(m_MapDataCache.containsKey(tileLayer))
		{
			return	m_MapDataCache.get(tileLayer);
		}
		int height = tileLayer.getHeight();
		int width = tileLayer.getWidth();
		int[][] mapData = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = height - 1; j >= 0; j--) {
				if (Collide.getID(tileLayer, i, j) == 0) {
					mapData[i][j] = 1;
				} else {
					mapData[i][j] = 0;
				}
			}
		}
		m_MapDataCache.put(tileLayer, mapData);
		return mapData;
	}
	
	public void clearLights(){
		lights.clear();
	}
	
	public Light getLight(int id){
		for(Light l : lights){
			if(l.id!=null && id == l.id)
				return l;
		}
		return null;
	}
	
	public void addLight(Integer id,box2dLight.Light light){
		lights.add(new Light(id,light));
	}
	
	/**
	 * FIXME nmb，能把灯光销毁，但是gameview.ray里还存着这个灯光，然后就导致出现神绮的bug。<br>
	 * 目前解决办法只能是灯光和地图统一销毁，而这个removeLight方法就是假的，只是把灯光大小调到了0让他不显示。
	 */
	public void removeLight(int id){
		for(Light l : lights)
			if(l.id !=null && l.id == id)
				l.light.setDistance(0);
	}
	
	public int addLight(Integer id,int x,int y,int distance){
		PointLight pl = new PointLight(GameViews.gameview.ray, 20);
		pl.setPosition(x,y);
		pl.setDistance(distance);
		addLight(id, pl);
		return id;
	}
	
	public synchronized void draw(GameView gv){
		int size=RPG.maps.map.getLayers().getCount();
		SpriteBatch sb=(SpriteBatch) gv.stage.getBatch();
		
		sb.setProjectionMatrix(gv.camera.combined);
		
		int skip = 0;
		for(int i=0;i<size;i++){
			
			if(RPG.maps.map.getLayers().get(i).getObjects().getCount()!=0){
				skip++;
				continue;
			}
			
			drawlist.clear();
			gv.render.setView(gv.camera);
			gv.render.render(new int[]{i});
			
			Array<Actor> removeList1 = new Array<>();
			
			for(Actor a:gv.stage.getActors()){
				if(a instanceof RPGObject){
					RPGObject c = (RPGObject)a;
					if(c instanceof Animation){
						if(((Animation)c).remove)
							removeList1.add(c);
					}
					if(c.layer==i-skip && !(!RPG.ctrl.hero.show && c instanceof Hero && c != RPG.ctrl.hero.getHeadHero()))
						drawlist.add(c);
				}
			}
			
			gv.stage.getActors().removeAll(removeList1, true);
			
			Collections.sort(drawlist);
			sb.begin();
			for(RPGObject ir:drawlist){
				ir.draw(sb, 1f);
			}
			sb.end();
		}
		
		//draw map path
		
		sb.begin();
		
		if(lastPoint!=null)
			lastPoint.actAndDraw(sb);
		sb.end();
	}
	
	private Image lastPoint;
	
	public void removePath(){
		if(lastPoint!=null)
			lastPoint.addAction(Actions.fadeOut(0.2f));
	}
	
	
	public void logic(GameView gv){
	}
	
	public void dispose(){
		m_MapDataCache.clear();
		m_MapDataCache = null;
		GameViews.gameview.stage.getActors().clear();
	}
	
	public ArrayList<NPC> getNPCs(){
		ArrayList<NPC> list=new ArrayList<NPC>();
		for(Actor a:GameViews.gameview.stage.getActors())
			if(a instanceof NPC)
				list.add((NPC)a);
		return list;
	}

	public void putPath(Image end) {
		lastPoint = end;
	}
}
