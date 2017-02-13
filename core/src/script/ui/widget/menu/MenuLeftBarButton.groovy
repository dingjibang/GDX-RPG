package script.ui.widget.menu

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.ui.view.View
import com.rpsg.rpg.ui.widget.Image
import com.rpsg.rpg.ui.widget.Label

import script.ui.view.MenuableView

import static com.rpsg.rpg.util.UIUtil.*

class MenuLeftBarButton extends Group{

	Class<MenuableView> to

	String en, zh
	TypedGdxQuery<Image> icon, bg, pbg
	TypedGdxQuery<Label> enl, zhl
	boolean checked = false

	def onclick

	MenuLeftBarButton(Map param) {
		param.each {this."${it.key}" = it.value}

		bg = $("base").click(this.&click).size(210, 100).color("242424").a(0.9f) to this
		pbg = $(Path.IMAGE_MENU_GLOBAL + "btn_bar_checked.png").visible(false).disableTouch().y(-10) to this
		icon = $(Path.IMAGE_MENU_GLOBAL + "icon_" + en.toLowerCase() + ".png").position(31, 25).disableTouch() to this

		enl = $(en, 20).a(0.7f).size(120, 49).center().position(75, 5).disableTouch() to this
		zhl = $(zh, 34).size(70, 49).center().position(101, 40).disableTouch() to this

		setSize bg.width(), bg.height()
	}

	void setChecked(checked) {

		pbg.visible checked

		addAction Actions.after(Actions.moveBy(checked ? 10 : (this.checked ? -10 : 0), 0, 0.1f, Interpolation.pow4Out))

		this.checked = checked
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
