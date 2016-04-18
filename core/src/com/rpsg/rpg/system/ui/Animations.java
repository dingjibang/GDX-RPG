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
	
	public Animations play(BattleResult result){
		if(result.animateId <= 0) return this;
		
		for(Target target : result.targetList){
			if(target.parentEnemy != null){
				Enemy enemy = target.parentEnemy;
				EnemyBox box = battleView.enemyGroup.getBox(enemy);
				if(box != null)
					$.add(new Animation(result.animateId).generate()).setPosition(box.getX(), box.getY()).appendTo(this);
			}else if(target.parentHero != null){
				Hero hero = target.parentHero;
				HeroStatusBox box = battleView.heroGroup.getBox(hero);
				if(box != null)
					$.add(new Animation(result.animateId).generate()).setPosition(box.getX(), box.getY()).appendTo(this);
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
