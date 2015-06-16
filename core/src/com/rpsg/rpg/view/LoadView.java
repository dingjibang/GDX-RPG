package com.rpsg.rpg.view;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.View;
import com.rpsg.rpg.utils.game.GameUtil;

public class LoadView extends View {
	private Sprite f_load;
	private int[] hbx,hby,hbs,hba;
	private Image[] hb;
	private float[] hbr;
	private boolean[] hbab;
	private int floada=100;
	private boolean floadb=true;
	private Image bg;
	private Stage stage;

    /**
     * 这个类是两年前写的了，懒的改了，很恶心就这样了，嗯~
     */
	@Override
	public void init() {
		stage=new Stage(new ScalingViewport(Scaling.stretch, GameUtil.screen_width, GameUtil.screen_height, new OrthographicCamera()));
		f_load= new Sprite(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f_load.png")));
		f_load.getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		bg= new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"bg.png")));
		bg.setPosition(0, 0);
		bg.setSize(GameUtil.screen_width,bg.getHeight());

		bg.setColor(1,1,1,0);
		hbx=new int[10];hby=new int[10];hbs=new int[10];hba=new int[10];hbr=new float[10];hbab=new boolean[10];hb=new Image[10];
		for (int i = 0; i <hbx.length; i++) {
			switch(new Random().nextInt(6) + 1){
			case 1:hb[i] = new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f1.png")));break;
			case 2:hb[i] = new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f2.png")));break;
			case 3:hb[i] = new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f3.png")));break;
			case 4:hb[i] = new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f4.png")));break;
			case 5:hb[i] = new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f5.png")));break;
			case 6:hb[i] = new Image(new Texture(Gdx.files.internal(Setting.GAME_RES_IMAGE_LOAD+"f6.png")));break;
			}
			hbx[i]=0;hby[i]=40;hbs[i]=0;hba[i]=0;hbab[i]=true;
		}
		f_load.setSize(200, 25);
		f_load.setPosition(GameUtil.screen_width - f_load.getWidth() - 70, 30);
        stage.addActor(bg);
		reinit();
	}
	
	@Override
	public void dispose() {
		
	}
	
	public void draw() {
        if(!(!flag && bg.getColor().a==0)) {
            SpriteBatch batch = (SpriteBatch) stage.getBatch();
            if (bg.getActions().size == 0) {
                bg.addAction(flag ? Actions.fadeIn(0.2f) : Actions.fadeOut(0.1f));
            } else {
                bg.clearActions();
                bg.addAction(flag ? Actions.fadeIn(0.2f) : Actions.fadeOut(0.1f));
            }
            stage.act();
            stage.draw();

            batch.begin();
            f_load.draw(batch);
            for (int i = 0; i < hbx.length; i++) {
                hb[i].draw(batch);
            }
            batch.end();
        }
	}

	@Override
	public void logic() {
		float globalAlpha=bg.getColor().a;
		
		if(floadb) floada-=2; else floada+=2;
		if((floada==2 && floadb)||(floada==150 && !floadb)) floadb=!floadb;
		f_load.setColor(1,1,1,globalAlpha);
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
			hb[i].setX(hbx[i]);hb[i].setY(hby[i]);hb[i].setRotation(hbr[i]);hb[i].setColor(1,1,1,globalAlpha*.5f);
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
	
	}

	public void draw(SpriteBatch batch) {
		this.draw();
	}
	
	boolean flag=false;
	public void start() {
		flag=true;
		bg.clearActions();
	}

	public void stop() {
		flag=false;
		bg.clearActions();
	}

}
