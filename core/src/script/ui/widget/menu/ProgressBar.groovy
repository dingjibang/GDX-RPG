package script.ui.widget.menu

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.ui.widget.Image

import static com.rpsg.rpg.util.UIUtil.*
/**
 * 你懂的进度条控件
 */
class ProgressBar extends Group{

	String innerColor
	int padding

	int progress, max

	TypedGdxQuery<Image> outer, inner, inner2

	ProgressBar(String color, int width, int height, int progress, int max, int padding = 2) {
		this.padding = padding
		this.innerColor = color

		setSize width, height

		outer = $("base").size(width, height) to this
		inner = $("base").position(padding, padding).color(Color.valueOf(color)).height((float)(getHeight() - padding * 2)) to this
		inner2 = $("base").a(0.1).position(padding, (float)(getHeight() / 2)).height((float)(getHeight() / 2 - padding)) to this

		this.max = max
		this.progress = progress
		this.progress progress
	}

	ProgressBar progress(int progress){
		this.progress = progress
		float width = progress / max * (getWidth() - padding * 2)

		inner.stopActions().action(Actions.sizeTo(width, (float)(getHeight() - padding * 2), 0.6f, Interpolation.pow4Out))
		inner2.stopActions().action(Actions.sizeTo(width, (float)(getHeight() / 2 - padding), 0.6f, Interpolation.pow4Out))
		this
	}

}
