package com.rpsg.rpg.utils.display;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.rpsg.rpg.system.ui.Image;
import com.rpsg.rpg.system.ui.Toast;
import com.rpsg.rpg.utils.game.GameUtil;
import com.rpsg.rpg.view.GameViews;

public class Toasts {
	private Stage stage;

	static{
		Toast.init();
	}
	
	public Toasts() {
		stage = new Stage(new ScalingViewport(Scaling.stretch, GameUtil.stage_width, GameUtil.stage_height, new OrthographicCamera()), GameViews.batch);
	}

	public void draw() {
		//generate the toast in main loop thread.
		for(Actor actor : stage.getActors()){
			if(actor instanceof Toast){
				Toast toast = (Toast)actor;
				if(!toast.generated)
					toast.generate();
			}
		}
		
		stage.act();
		stage.getBatch().end();
		stage.draw();
		stage.getBatch().begin();
	}

	public void add(String msg, Color color) {
		stage.addActor(new Toast(msg, color));
	}

	public void add(String msg) {
		stage.addActor(new Toast(msg));
	}

	public void add(String msg, Color color, int fontSize) {
		stage.addActor(new Toast(msg, color, fontSize));
	}
	
	public void add(String msg,Color color,int fontSize,boolean animate,Image icon){
		stage.addActor(new Toast(msg, color, fontSize, animate, icon));
	}

}
