package script.ui.view

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable
import com.rpsg.rpg.core.*

import static com.rpsg.rpg.util.UIUtil.*

/**
 * GDX-RPG Toast视图
 */
class ToastView extends UIView{
	
	public String text
	
	public static final float PADDING_HEIGHT = 10
	public static final float PADDING_WIDTH = 60
	public static final int FONT_SIZE = 18

	public static final int MARGIN_BOTTOM = 60
	
	public float paddingHeight = PADDING_HEIGHT
	public float paddingWidth = PADDING_WIDTH
	public int fontSize = FONT_SIZE

	public static NinePatchDrawable UI_TOAST
	static{
		UI_TOAST = new NinePatchDrawable(UI.get9(Path.IMAGE_GLOBAL + "toast_box.png",2,2,1,1,5,5,5,5))
	}


	void create() {
		buuleable true

		def table = new Table()
		table.background = UI_TOAST

		table.add($(text, fontSize).center().get()).pad(paddingHeight, paddingWidth, paddingHeight, paddingWidth).row()

		$(new Table().bottom().padBottom(MARGIN_BOTTOM))
			.width(Game.width())
			.a(0)
			.into(table)
			.action(Actions.sequence(
				Actions.fadeIn(0.3f),
				Actions.delay((float)(1.5f + text.length() * 0.15f)),
				Actions.fadeOut(0.3f),
				Actions.run(this.&remove)
			))
			.to(stage)
	}
	

}