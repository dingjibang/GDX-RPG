package com.rpsg.rpg.system.ui;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Disposable;
import com.rpsg.gdxQuery.$;
import com.rpsg.rpg.core.RPG;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.system.base.Res;

public class HeroSelectBox extends Group implements Disposable{
	
	private Hero hero;
	private boolean forAllHeros = false;
	private ItemDeadable deadable;
	
	ParticleEffect particle;
	int particleTime = 0;
	
	public HeroSelectBox(int width, int height, boolean forAllHeros,ItemDeadable deadable) {
		size(width, height);
		sizeChanged();
		this.forAllHeros = forAllHeros;
		this.deadable = deadable;
		particle = new ParticleEffect();
		particle.load(Gdx.files.internal(Setting.PARTICLE + "addp.p"), Gdx.files.internal(Setting.PARTICLE));
		
		generate();
	}
	
	@Override
	public void act(float delta) {
		if(particleTime>=0){
			particleTime--;
			if(particle.isComplete())
				particle.reset();
		}else{
			particle.allowCompletion();
		}
		super.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		particle.draw(batch,Gdx.graphics.getDeltaTime());
	}
	
	public void dispose(){
		particle.dispose();
	}
	
	public HeroSelectBox generate(){
		clear();
		
		addActor(Res.get(Setting.UI_BASE_IMG).size((int)getWidth(),(int)getHeight()).a(.15f));
		
		int margin = 10;
		int w = (int) ((getWidth()-margin*3)/2);
		int h = (int) ((getHeight()-margin*3)/2);
		
		List<Hero> heros = RPG.ctrl.hero.currentHeros;
		generateHeroBox(margin, h+margin*2, w, h,heros.size()>0?heros.get(0):null);
		generateHeroBox(w+margin*2, h+margin*2, w, h,heros.size()>1?heros.get(1):null);
		generateHeroBox(margin, margin, w, h,heros.size()>2?heros.get(2):null);
		generateHeroBox(w+margin*2, margin, w, h,heros.size()>3?heros.get(3):null);
		
		if(forAllHeros){
			addActor(Res.get(Setting.UI_BASE_IMG).size((int)getWidth(),(int)getHeight()).color(Color.valueOf("33333399")));
			addActor(new Label("全体使用的",40).align(0, 0).width((int)getWidth()).height((int)getHeight()));
		}
		
		return this;
	}
	
	
	private HeroSelectBox generateHeroBox(int x,int y,int w,int h,final Hero hero){
		addActor(Res.get(Setting.UI_BASE_IMG).size(w,h).a(.15f).position(x, y).object(hero==null?"nullHero":hero).onClick(new Runnable() {
			public void run() {
				set(hero);
			}
		}));
		
		if(hero!=null){
			addActor(new HeroImage(hero,0).position(x+10, y+10));
			addActor(new Label(hero.name,20).position(x+65, y+53));
			addActor(Res.get(Setting.UI_BASE_IMG).size(141, 42).position(x+65, y+8).disableTouch());
			addActor(Res.get(Setting.UI_BASE_PRO).size((int)((float)hero.target.getProp("hp")/(float)hero.target.getProp("maxhp")*137), 18).position(x+67, y+30).color(Color.valueOf("c33737")).disableTouch());
			addActor(Res.get(Setting.UI_BASE_PRO).size((int)((float)hero.target.getProp("mp")/(float)hero.target.getProp("maxmp")*137), 18).position(x+67, y+10).color(Color.valueOf("3762c3")).disableTouch());
			addActor(new Label(hero.target.getProp("hp")+"/"+hero.target.getProp("maxhp"),14).width(130).align(x+67, y+30).color(Color.LIGHT_GRAY));
			addActor(new Label(hero.target.getProp("mp")+"/"+hero.target.getProp("maxmp"),14).width(130).align(x+67, y+10).color(Color.LIGHT_GRAY));
			
			if((deadable == ItemDeadable.yes && !hero.target.isDead()) || (deadable == ItemDeadable.no && hero.target.isDead())){
				addActor(Res.get(Setting.UI_BASE_IMG).size(w,h).color(Color.BLACK).a(.85f).position(x, y));
				addActor(new Label("无法使用",22).position(x+63, y+30));
			}
		}
		
	
		$.add(this).children().find(Label.class).setTouchable(Touchable.disabled);
		return this;
	}
	
	public HeroSelectBox position(int x,int y){
		setPosition(x, y);
		return this;
	}
	
	public Hero get(){
		return hero;
	}
	
	public HeroSelectBox set(Hero hero){
		if(hero==null)
			return this;
		this.hero = hero;
		$.add(this).children().findUserObjectInstanceOf(String.class,Hero.class).setAlpha(.15f).cleanActions().getFather().find(hero).setAlpha(.4f).addAction(Actions.forever(Actions.sequence(Actions.alpha(.2f,.2f),Actions.alpha(.5f,.2f))));
		return this;
	}

	public HeroSelectBox size(int width, int height) {
		setSize(width, height);
		return this;
	}

	public void animate() {
		Vector2 vec2;
		if(get()==null)
			return;
		vec2 = $.add(this).children().find(get()).getPosition().cpy();
		particle.setPosition(vec2.x+getX()+35, vec2.y+getY()+10);
		particleTime = 40;
//		particle.start();
	}
}
