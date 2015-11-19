package com.rpsg.rpg.system.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
/*import com.rpsg.rpg.game.hero.Arisu;
import com.rpsg.rpg.game.hero.Flandre;
import com.rpsg.rpg.game.hero.Marisa;
import com.rpsg.rpg.game.hero.Reimu;
import com.rpsg.rpg.game.hero.Yuuka;*/
import com.rpsg.rpg.object.rpg.CollideType;
import com.rpsg.rpg.object.rpg.NPC;
import com.rpsg.rpg.object.rpg.PublicNPC;
import com.rpsg.rpg.object.rpg.RPGObject;
import com.rpsg.rpg.object.script.Script;
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
		removeAllPath();
		
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
				}
			}
		}
		
		//生成NPC
		layer=new MapLayers();
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
		
		int skip = 0;
		for(int i=0;i<size;i++){
			
			if(RPG.maps.map.getLayers().get(i).getObjects().getCount()!=0){
				skip++;
				continue;
			}
			
			drawlist.clear();
			gv.render.setView(gv.camera);
			gv.render.render(new int[]{i});

			for(Actor a:gv.stage.getActors()){
				if(a instanceof RPGObject){
					RPGObject c = (RPGObject)a;
					if(c.layer==i-skip)
						drawlist.add(c);
				}
			}
			
			Collections.sort(drawlist);
			sb.begin();
			for(RPGObject ir:drawlist){
				ir.draw(sb, 1f);
			}
			
			
			sb.end();
		}
		
		//draw map path
		
		sb.begin();
		
		for(Image path:points)
			path.actAndDraw(sb);
		if(lastPoint!=null)
			lastPoint.actAndDraw(sb);
		sb.end();
	}
	
	private List<Image> points = new ArrayList<>();
	private Image lastPoint;
	
	public void putPath(List<Image> points){
		this.points=points;
	}
	
	public void removePath(){
		if(!points.isEmpty()){
			lastPoint=points.get(0);
			points.remove(0);
			lastPoint.addAction(Actions.fadeOut(0.2f));
		}
	}
	
	public void removeAllPath(){
		points.clear();
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
