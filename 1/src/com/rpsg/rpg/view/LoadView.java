package com.rpsg.rpg.view;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.utils.display.ScreenUtil;
import com.rpsg.rpg.utils.game.GameUtil;

public class LoadView extends View {
	private Sprite f_load;
	private int[] hbx,hby,hbs,hba;
	private Sprite[] hb;
	private float[] hbr;
	private boolean[] hbab;
	private int floada=100;
	private boolean floadb=true;
	private Image bluredbg;
	@Override
	public void init() {
		System.out.println("init");
		f_load= new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f_load.png")));
		hbx=new int[10];hby=new int[10];hbs=new int[10];hba=new int[10];hbr=new float[10];hbab=new boolean[10];hb=new Sprite[10];
		for (int i = 0; i <hbx.length; i++) {
			switch(new Random().nextInt(6) + 1){
			case 1:hb[i] = new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f1.png")));break;
			case 2:hb[i] = new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f2.png")));break;
			case 3:hb[i] = new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f3.png")));break;
			case 4:hb[i] = new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f4.png")));break;
			case 5:hb[i] = new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f5.png")));break;
			case 6:hb[i] = new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f6.png")));break;
			}
			hbx[i]=0;hby[i]=40;hbs[i]=0;hba[i]=0;hbab[i]=true;
		}
		f_load.setSize(200, 25);
		f_load.setPosition(GameUtil.screen_width-f_load.getWidth()-70, 30);
		reinit();
	}
	
	@Override
	public void dispose() {
		
	}
	
	@Override
	public void draw(SpriteBatch batch) {
//		Gdx.gl.glClearColor(0, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(bluredbg!=null){
			bluredbg.act(Gdx.graphics.getDeltaTime());
			bluredbg.draw(batch);
		}
		f_load.draw(batch);
		for(int i=0;i<hbx.length;i++){
			hb[i].draw(batch);
		}
	}

	@Override
	public void logic() {
		if(floadb) floada-=2; else floada+=2;
		if((floada==2 && floadb)||(floada==150 && !floadb)) floadb=!floadb;
		f_load.setColor(1,1,1,floada);
		for(int i=0;i<hbx.length;i++){
			if(hbab[i]){
				hbx[i]=GameUtil.screen_width-new Random().nextInt(120)-40;
				hby[i]=new Random().nextInt(80)+10;
				hbr[i]=0;
				hbab[i]=false;
				hbs[i]=new Random().nextInt(7)+2;
				hba[i]=255;
			}
			if(hby[i]>-50){
				hbx[i]-=hbs[i];
				hby[i]-=hbs[i];
				hbr[i]+=1/hbs[i];
				hba[i]-=hbs[i];
			}else
				hbab[i]=true;
			hb[i].setX(hbx[i]);hb[i].setY(hby[i]);hb[i].setRotation(hbr[i]);hb[i].setColor(1,1,1,hba[i]);
		}
	}

	@Override
	public void onkeyTyped(char character) {
		
	}

	@Override
	public void onkeyDown(int keyCode) {
		
	}

	@Override
	public void onkeyUp(int keyCode) {
		
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	public void reinit() {
		if(bluredbg!=null){
			bluredbg.dispose();
			bluredbg=null;
		}
		Pixmap pbg=ScreenUtil.getScreenshot(0, 0, GameUtil.screen_width, GameUtil.screen_height, false);
		bluredbg= new Image(new TextureRegion(new Texture(pbg),0,GameUtil.screen_height,GameUtil.screen_width,-GameUtil.screen_height));
		bluredbg.setColor(new Color(0.7f,0.7f,0.7f,1));
		pbg.dispose();
	}

}
