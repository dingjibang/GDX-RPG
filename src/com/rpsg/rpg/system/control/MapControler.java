package com.rpsg.rpg.system.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.game.hero.Flandre;
import com.rpsg.rpg.game.hero.Marisa;
import com.rpsg.rpg.game.hero.Reimu;
import com.rpsg.rpg.game.hero.Yuuka;
import com.rpsg.rpg.game.items.equipment.Sunshade;
import com.rpsg.rpg.game.items.equipment.TestEquip;
import com.rpsg.rpg.game.items.medicine.YaoWan;
import com.rpsg.rpg.object.rpgobj.IRPGObject;
import com.rpsg.rpg.object.rpgobj.NPC;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.ThreadPool;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.utils.game.Logger;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;

public class MapControler {
	
	public static int MAP_MAX_OUT_X=300;
	public static int MAP_MAX_OUT_Y=200;
	public static List<IRPGObject> drawlist=new ArrayList<IRPGObject>();
	public static NPC npc;
	public static void init(GameView gv){
		//初始化角色
		HeroControler.initControler();
		if(gv.global.heros.isEmpty()){
			HeroControler.newHero(Marisa.class);
			HeroControler.addHero(Marisa.class);
			HeroControler.newHero(Reimu.class);
			HeroControler.addHero(Reimu.class);
			HeroControler.newHero(Flandre.class);
			HeroControler.addHero(Flandre.class);
			HeroControler.newHero(Yuuka.class);
			HeroControler.addHero(Yuuka.class);
			gv.global.getItems("equipment").add(new TestEquip());
			gv.global.getItems("equipment").add(new Sunshade());
			gv.global.getItems("medicine").add(new YaoWan());
		}
		HeroControler.initHeros(gv.stage);
		
		//设置抗锯齿
		if(Setting.DISPLAY_ANTI_ALIASING)
			gv.map.getTileSets().forEach((s)->s.forEach((tile)->tile.getTextureRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear)));
		
		//获取灯光
		for(int i=0;i<gv.map.getLayers().getCount();i++){
			MapLayer m = gv.map.getLayers().get(i);
			for(MapObject obj:m.getObjects()){
				if (obj.getProperties().get("type").equals("LIGHT")){
					PointLight pl= new PointLight(gv.ray,20);
					pl.setDistance(Float.parseFloat((String)obj.getProperties().get("STRENGTH")));
					pl.setPosition((int)(((RectangleMapObject)obj).getRectangle().getX()+24),
								(int)(((RectangleMapObject)obj).getRectangle().getY()+((RectangleMapObject)obj).getRectangle().getHeight())+24);
				}
			}
		}
		
		//生成NPC
		if(gv.global.npcs.isEmpty()){
			List<MapLayer> removeList=new ArrayList<MapLayer>();
			for(int i=0;i<gv.map.getLayers().getCount();i++){
				TiledMapTileLayer bot=(TiledMapTileLayer) gv.map.getLayers().get(0);
				 MapLayer m = gv.map.getLayers().get(i);
				if(m.getObjects().getCount()!=0)
					removeList.add(m);
				for(MapObject obj:m.getObjects()){
					if(obj.getProperties().get("type").equals("NPC")){
						try {
							npc=(NPC)Class.forName("com.rpsg.rpg.game.object."+obj.getName()).getConstructor(String.class,Integer.class,Integer.class)
								.newInstance(
									obj.getProperties().get("IMAGE")+".png",
									(int)(((RectangleMapObject)obj).getRectangle().getWidth()),
									(int)(((RectangleMapObject)obj).getRectangle().getHeight())
								);
							npc.init();
							npc.generatePosition(((int)(((RectangleMapObject)obj).getRectangle().getX())/48),
									 (int)(bot.getHeight()-2-((RectangleMapObject)obj).getRectangle().getY()/48),
									 i-removeList.size());
							npc.params=GameUtil.parseMapProperties(obj.getProperties());
							Logger.info("NPC生成成功["+npc.position+","+npc.mapx+":"+npc.mapy+"]");
							gv.stage.addActor(npc);
							ThreadPool.pool.add(npc.threadPool);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				HeroControler.generatePosition(gv.global.x,gv.global.y,gv.global.z);
			}
			for(MapLayer l:removeList)
				gv.map.getLayers().remove(l);
		}else{
			List<MapLayer> removeList=new ArrayList<MapLayer>();
			for(int i=0;i<gv.map.getLayers().getCount();i++){
				MapLayer m=gv.map.getLayers().get(i);
				if(m.getObjects().getCount()!=0)
					removeList.add(m);
			}
			for(MapLayer l:removeList)
				gv.map.getLayers().remove(l);
			for(NPC n:gv.global.npcs){
				n.scripts=new HashMap<String, Class<? extends Script>>();
				n.threadPool=new LinkedList<Script>();
				n.init();
				gv.stage.addActor(n);
				ThreadPool.pool.add(n.threadPool);
				n.images=NPC.generateImages(n.imgPath, n.bodyWidth, n.bodyHeight);
				npc=n;
			}
		}
		Logger.info("地图模块已全部加载完成。");
	}
	
	public synchronized static void draw(GameView gv){
		int size=gv.map.getLayers().getCount();
		for(int i=0;i<size;i++){
			drawlist.clear();
			gv.render.setView(gv.camera);
			gv.render.render(new int[]{i});
			SpriteBatch sb=(SpriteBatch) gv.stage.getBatch();
			sb.begin();
			sb.setProjectionMatrix(gv.camera.combined);
			for(Actor a:gv.stage.getActors())
				if(a instanceof IRPGObject){
					IRPGObject c = (IRPGObject)a;
					if(c.layer==i)
						drawlist.add(c);
				}
			Collections.sort(drawlist);
			for(IRPGObject ir:drawlist){
				ir.draw(sb, 1f);
			}
			sb.end();
		}
//		System.out.println(HeroControler.getHeadHero().getX()+" "+HeroControler.getHeadHero().getY()+" "+gv.camera.position.x+" "+gv.camera.position.y+" "+HeroControler.getHeadHero().mapx+" "+HeroControler.getHeadHero().mapy);
	}
	
	public static void logic(GameView gv){
	}
	
	public static void dispose(){
		HeroControler.dispose();
		for(Actor a:GameViews.gameview.stage.getActors())
			if(a instanceof IRPGObject)
				((IRPGObject)a).dispose();
		GameViews.gameview.stage.getActors().clear();
	}
	
	public static List<NPC> getNPCs(){
		List<NPC> list=new ArrayList<NPC>();
		for(Actor a:GameViews.gameview.stage.getActors())
			if(a instanceof NPC)
				list.add((NPC)a);
		return list;
	}
}
