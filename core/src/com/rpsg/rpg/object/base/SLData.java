package com.rpsg.rpg.object.base;

import java.io.Serializable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.io.Files;
import com.rpsg.rpg.system.base.Res;
import com.rpsg.rpg.system.ui.HoverView;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.TextButton;
import com.rpsg.rpg.view.hover.LoadView;
import com.rpsg.rpg.view.hover.SaveView;

public class SLData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	public transient Image thumbnail;
	public int level;
	public String mapName;
	public String saveDate;
	public String gameDate;
	public String heroName;
	public static void generate(final int id,int i, final Stage stage, final HoverView sv) {
		final Image im=Res.get(Setting.IMAGE_MENU_SYSTEM+"savebl.png").position(i>1?44+(i-2)*483:44+i*483, i>1?270:114);
		im.setUserObject(new exMask());
		stage.addActor(im);
		SLData slData = null;
		im.onClick(new Runnable() {
			@Override
			public void run() {
				for (Actor actor : stage.getActors())
					if (actor.getUserObject() != null && actor.getUserObject().getClass().equals(exMask.class))
						((Image) actor).setColor(1, 1, 1, 1);
				im.color(Color.valueOf("FF0000"));
				if (sv instanceof SaveView)
					((SaveView) sv).currentSelect = id;
				else
					((LoadView) sv).currentSelect = id;
			}
		});
		im.onDblClick(new Runnable() {
			@Override
			public void run() {
				for (Actor e : stage.getActors())
					if (e.getName() != null && e.getName().equals("mask"))
						((TextButton) e).click();
			}
		});
		String fileName = Setting.PERSISTENCE+(id+"_sld.dat");
		if(!Gdx.files.local(fileName).exists()){
			slData = new SLData();
			String path=Setting.IMAGE_MENU_SYSTEM+"ea.png";
			slData.thumbnail=Res.get(path);
			stage.addActor($.add(Res.font.getLabel("空位置",26)).setUserObject(new Object()).setWidth(200).setPosition(i>1?350+(i-2)*483:350+i*483, i>1?317:161).getItem());
		}else{
			slData = (SLData) Files.load(fileName);
			Res.dispose("save/"+id+".png");
			Gdx.files.local("save").mkdirs();
			Texture txt = new Texture(Gdx.files.local("save/" + id + ".png"));
			slData.thumbnail=new Image(new TextureRegion(txt,0,txt.getHeight(),txt.getWidth(),-txt.getHeight()));
			stage.addActor($.add(Res.font.getLabel("LV "+slData.level,30)).setUserObject(new Object()).setWidth(200).setPosition(i>1?280+(i-2)*483:280+i*483, i>1?358:202).setTouchable(null).getItem());
			stage.addActor($.add(Res.font.getLabel(slData.mapName+","+slData.heroName,18)).setUserObject(new Object()).setWidth(200).setPosition(i>1?280+(i-2)*483:280+i*483, i>1?332:176).setTouchable(null).getItem());
			stage.addActor($.add(Res.font.getLabel(slData.gameDate,18)).setUserObject(new Object()).setWidth(200).setPosition(i>1?280+(i-2)*483:280+i*483, i>1?308:151).setTouchable(null).getItem());
			stage.addActor($.add(Res.font.getLabel("保存于"+slData.saveDate,14)).setUserObject(new Object()).setWidth(200).setPosition(i>1?280+(i-2)*483:280+i*483, i>1?286:130).setTouchable(null).setColor(Color.LIGHT_GRAY).getItem());
		}
		slData.thumbnail.setUserObject(new Object());
		slData.thumbnail.setSize(207,112);
		stage.addActor(slData.thumbnail.disableTouch().position(i>1?54+(i-2)*483:54+i*483, i>1?277:121));
		stage.addActor(Res.get(Setting.IMAGE_MENU_SYSTEM+"/saveno.png").position(i>1?54+(i-2)*483:54+i*483, i>1?277:121).disableTouch());
		stage.addActor($.add(Res.font.getLabel(id,14).width(50)).setUserObject(new Object()).setPosition(i>1?60+(i-2)*483:60+i*483, i>1?281:126).setAlign(Align.center).setColor(Color.ORANGE).setTouchable(null).getItem());
	}
	
	public static class exMask{}
}
