package com.rpsg.rpg.system.control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.rpsg.rpg.game.hero.Flandre;
import com.rpsg.rpg.game.hero.Marisa;
import com.rpsg.rpg.game.hero.Reimu;
import com.rpsg.rpg.game.hero.Yuuka;
import com.rpsg.rpg.object.rpgobj.IRPGObject;
import com.rpsg.rpg.object.rpgobj.NPC;
import com.rpsg.rpg.object.script.Script;
import com.rpsg.rpg.system.base.ThreadPool;
import com.rpsg.rpg.utils.display.ColorUtil;
import com.rpsg.rpg.utils.display.FG;
import com.rpsg.rpg.utils.display.Msg;
import com.rpsg.rpg.view.GameView;
import com.rpsg.rpg.view.GameViews;

public class MapControler {
	
	public static int MAP_MAX_OUT_X=300;
	public static int MAP_MAX_OUT_Y=200;
	public static List<IRPGObject> drawlist=new ArrayList<IRPGObject>();
	public static NPC npc;
	public static void init(GameView gv){
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
			HeroControler.generatePosition(10,10,1);
		}
		HeroControler.initHeros(gv.stage);
		ColorUtil.currentColor=new Color(gv.global.mapColor);
		if(gv.global.npcs.isEmpty()){
			List<MapLayer> removeList=new ArrayList<MapLayer>();
			for(int i=0;i<gv.map.getLayers().getCount();i++){
				MapLayer m=gv.map.getLayers().get(i);
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
							npc.generatePosition((int)((int)(((RectangleMapObject)obj).getRectangle().getX())/48),
									(int)((int)(((RectangleMapObject)obj).getRectangle().getY())/48), i-1);
							System.out.println(npc.position+","+npc.mapx+":"+npc.mapy);
							gv.stage.addActor(npc);
							ThreadPool.pool.add(npc.threadPool);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				if(m.getObjects().getCount()!=0)
					removeList.add(m);
			}
			for(MapLayer l:removeList){
				gv.map.getLayers().remove(l);
			}
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
	}
	
	public synchronized static void draw(SpriteBatch batch,GameView gv){
		int size=gv.map.getLayers().getCount();
		for(int i=0;i<size;i++){
			drawlist.clear();
			batch.end();
			gv.render.setView(gv.camera);
			gv.render.getBatch().setColor(ColorUtil.currentColor);
			gv.render.render(new int[]{i});
			batch.begin();
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
				ir.setColor(ColorUtil.currentColor);
				ir.draw(sb, 1f);
			}
			sb.end();
		}
		FG.draw(batch);
		Msg.draw(batch);
//		System.out.println(hero.getX()+" "+hero.getY()+" "+gv.camera.position.x+" "+gv.camera.position.y+" "+hero.mapx+" "+hero.mapy);
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
