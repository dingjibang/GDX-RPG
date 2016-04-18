package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.rpsg.rpg.system.ui.Animation;
import com.rpsg.rpg.view.GameViews;

public class MapAnimationsManager {
	
	public Animation add(int id){
		Animation a = new Animation(id);
		GameViews.gameview.stage.addActor(a);
		return a;
	}
	
	public boolean remove(Animation animation){
		return GameViews.gameview.stage.getActors().removeValue(animation, true);
	}
	
	public void removeAll(){
		Array<Actor> removeList = new Array<>();
		for(Actor actor : GameViews.gameview.stage.getActors())
			if(actor instanceof Animation)
				removeList.add(actor);
		
		GameViews.gameview.stage.getActors().removeAll(removeList, true);
	}
	
}
