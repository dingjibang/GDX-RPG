package script.ui.view;

import com.rpsg.rpg.core.Game;
import com.rpsg.rpg.core.Res;
import com.rpsg.rpg.object.graphics.Preinitializable;
import com.rpsg.rpg.ui.view.View;
import com.rpsg.rpg.util.Timer;

/**
 * 快速View
 */
public abstract class UIView extends View implements Preinitializable {
	private static final String[] emptyStringArrays = new String[]{};

	public UIView() {
		this.stage = Game.stage();
	}

	public void toPreinit(){
		String[] preInits = preInit();
		if(preInits != emptyStringArrays && preInits.length != 0)
			Timer.then(() -> Res.preInit(preInits));
	}

	public String[] preInit(){
		return emptyStringArrays;
	}

	public void act() {
		stage.act();
	}

	public void draw() {
		stage.draw();
	}
}
