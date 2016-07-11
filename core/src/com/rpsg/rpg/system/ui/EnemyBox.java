package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.core.Setting;
import com.rpsg.rpg.object.base.items.Item.ItemDeadable;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.system.base.Res;

public class EnemyBox extends Group {

	Image image;
	public Enemy enemy;
	Label name,hp;
	Progress hpbar;
	Image selectBox = new Image(Setting.UI_BUTTON);

	public EnemyBox(Enemy enemy) {
		this.enemy = enemy;
		int _hp = Integer.valueOf(enemy.target.getProp("hp")),_maxhp = Integer.valueOf(enemy.target.getProp("maxhp"));
		
		Group fg = new Group();
		fg.addActor(image = Res.getNP(enemy.imgPath));
		fg.addActor(hp = Res.get(enemy.name+"\n"+_hp + " / " + _maxhp, 18).align(Align.center));
		hp.position((int)(getWidth()/2 - hp.getWidth()/2), (int)getHeight()+40);
		
		fg.addActor(Res.base().size(20, 20).position(getWidth()/2 - 130/2 - 10, getHeight()+15).color(enemy.color));
		fg.addActor(hpbar = new Progress(Res.getNP(Setting.IMAGE_BATTLE+"bg_00001_00001.png").scale(.5f), Res.getNP(Setting.IMAGE_BATTLE+"hp_00001_00001.png").scale(.5f), 
				Res.getNP(Setting.IMAGE_BATTLE+"cache_00001_00001.png").scale(.5f), 0, _maxhp)); 
		hpbar.setPosition(getWidth()/2 - 130/2 + 10, getHeight()+20);
		
		$.add(fg).addAction(Actions.forever(Actions.sequence(Actions.moveBy(0,10,3f,Interpolation.pow2),Actions.moveBy(0, -10,3)))).appendTo(this);
		
		$.add(selectBox).appendTo(fg).setVisible(false);
		
	}
	
	@Override
	public void act(float delta) {
		hpbar.value(enemy.target.getProp("hp")); 
		int _hp = Integer.valueOf(enemy.target.getProp("hp")),_maxhp = Integer.valueOf(enemy.target.getProp("maxhp"));
		hp.text(enemy.name+"\n"+_hp + " / " + _maxhp);
		super.act(delta);
	}
	
	public EnemyBox position(float x,float y){
		setPosition(x, y);
		return this;
	}
	
	@Override
	public float getWidth() {
		return image.getWidth();
	}
	
	@Override
	public float getHeight() {
		return image.getHeight();
	}
	
	public void select(CustomRunnable<EnemyBox> callback, ItemDeadable deadable){
		boolean isDead = enemy.target.isDead();
		if(deadable == ItemDeadable.all || (isDead && deadable == ItemDeadable.yes) || (!isDead && deadable == ItemDeadable.no))
		$.add(selectBox).show().click(()->{
			callback.run(EnemyBox.this);
		});
	}
	
	public void stopSelect(){
		$.add(selectBox).hide();
		selectBox.clearListeners();
	}
	
	
}
