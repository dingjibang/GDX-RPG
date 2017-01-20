package com.rpsg.rpg.view.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Align;
import com.rpsg.gdxQuery.GdxQuery;
import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Path;
import com.rpsg.rpg.ui.widget.AsyncLoadImage;

/**
 * GDX-RPG 立绘显示器
 */
public class FG {
	
	public static final int left = 0, right = 1;
	GdxQuery query = new GdxQuery();
	
	public FG() {
		AsyncLoadImage left = new AsyncLoadImage();//left
		left.setScale(.40f);
		query.add(left);
		
		AsyncLoadImage right = new AsyncLoadImage();//right
		right.setScale(-.40f, .40f);
		right.setX(Game.STAGE_WIDTH);
		query.add(right);
		
		query.origin(Align.bottomLeft).alpha(0);
	}
	
	private AsyncLoadImage get(Integer position) {
		return (AsyncLoadImage)query.get(position);
	}
	
	public void draw(Batch batch) {
		query.act();
		//为节省开销，透明时不画图
		if(!query.isTransparent()){
			batch.begin();
			query.draw(batch);
			batch.end();
		}
	}
	
	public void show(int position, String fgPath, Action withAction) {
		AsyncLoadImage img = get(position);
		img.setDrawableAsync(Path.IMAGE_FG + fgPath);
		img.clearActions();
		img.getColor().a = .3f;
		img.addAction(Actions.fadeIn(.3f));
		if(withAction != null)
			img.addAction(withAction);
	}
	
	public void hide(Integer position) {
		if(position == null){
			query.cleanActions().each(a -> a.addAction(Actions.fadeOut(.3f)));
		}else{
			AsyncLoadImage img = get(position);
			img.clearActions();
			img.addAction(Actions.fadeOut(.5f));
		}
	}
}
