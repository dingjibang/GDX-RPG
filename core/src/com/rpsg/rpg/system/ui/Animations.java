package com.rpsg.rpg.system.ui;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.rpsg.gdxQuery.$;
import com.rpsg.gdxQuery.CustomRunnable;
import com.rpsg.rpg.object.base.items.BattleResult;
import com.rpsg.rpg.object.rpg.Enemy;
import com.rpsg.rpg.object.rpg.Hero;
import com.rpsg.rpg.object.rpg.Target;
import com.rpsg.rpg.view.BattleView;

public class Animations extends Group{
	
	BattleView battleView;
	public Animations(BattleView battleView) {
		this.battleView = battleView;
	}
	
	public Animations play(BattleResult result,Runnable callback){
		if(result.animateId <= 0) {
			callback.run();
			return this;
		}
		
		for(Target target : result.targetList){
			
			if(target.parentEnemy != null){
				Enemy enemy = target.parentEnemy;
				EnemyBox box = battleView.enemyGroup.getBox(enemy);
				if(box != null){
					Animation ani = null;
					$.add(ani = new Animation(result.animateId).generate().played(callback)).appendTo(this);
					ani.setPosition(box.getParent().getX() + box.getX() - ani.getWidth() / 2 + box.getWidth() / 2 , box.getParent().getY() + box.getY() - ani.getHeight() / 2 + box.getHeight() /2);
				}
			}else if(target.parentHero != null){
				Hero hero = target.parentHero;
				HeroStatusBox box = battleView.heroGroup.getBox(hero);
				if(box != null){
					Animation ani = null;
					$.add(ani = new Animation(result.animateId).generate().played(callback)).appendTo(this);
					ani.setPosition(box.getX() - ani.getWidth() / 2 + 85, box.getY() - ani.getHeight() / 2 + 70);
				}
			}
		}
		
		return this;
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		Array<Animation> list = new Array<>();
		
		$.add(this).children().each((CustomRunnable<Animation>)a->{
			if(a.finished())
				list.add(a);
		});
		
		getChildren().removeAll(list, false);
	}
}
