package script.ui.widget.menu

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.ui.view.View
import com.rpsg.rpg.ui.widget.Image
import com.rpsg.rpg.ui.widget.Label
import groovy.transform.CompileStatic

import static com.rpsg.rpg.util.UIUtil.*

class MenuLeftBarButton extends Group{

	Class<View> to

	String en, zh
	TypedGdxQuery<Image> icon, bg, pbg
	TypedGdxQuery<Label> enl, zhl
	boolean checked = false

	def onclick

	@CompileStatic
	MenuLeftBarButton() {
		icon = $(Path.IMAGE_MENU_GLOBAL + en.toLowerCase() + ".png")
		bg = $("base").size(210, 100).color("242424").a(0.9f) to this
		pbg = $(Path.IMAGE_MENU_GLOBAL + "btn_bar_checked.png").visible(false).disableTouch() to this

		enl = $(en, 20).color("636363").size(120, 49).center().position(95, 5) to this
		zhl = $(zh, 34).size(70, 49).center().position(101, 40) to this
	}

	void setChecked(checked) {
		this.checked = checked

		pbg.visible = checked

		clearActions()
		addAction Actions.moveTo(checked ? 30 : 0, 0, 0.3f, Interpolation.pow4Out)
	}

	MenuLeftBarButton click(callback){
		onclick = callback
		this
	}

	MenuLeftBarButton click() {
		onclick?.call this
		this
	}

}
