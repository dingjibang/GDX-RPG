package script.ui.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.rpsg.gdxQuery.GdxQuery
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.*
import com.rpsg.rpg.ui.widget.Button
import com.rpsg.rpg.view.UIView
import groovy.transform.CompileStatic
import script.ui.widget.menu.HeroFGLabel
import script.ui.widget.menu.HeroSelector
import script.ui.widget.menu.MenuLeftBarButton

import java.lang.reflect.Type

import static com.rpsg.rpg.util.UIUtil.*

/**
 * GDX-RPG 菜单视图
 */
class MenuView extends UIView{

	def archive = Game.archive.get()
	def heroBox = []
	def currentHero = null
	GdxQuery bar, group1
	TypedGdxQuery<HeroFGLabel> fgLabel

	TypedGdxQuery label, exit, hr

	def buttons = []

	boolean menu = false

	void create() {
		$("base").size(1280, 720).color("333333").a(0).fadeTo(0.4f, 0.3f) to stage

		bar = q("base").size(540, 720).color("333333cc").x(-800).a(0.8f).action(Actions.moveTo(0, 0, 0.5f, Interpolation.pow4Out)) to stage

		group1 = q(new Group()).x(-800).action(Actions.moveTo(0, 0, 0.5f, Interpolation.pow4Out)) to stage

		exit = $(new Button(Path.IMAGE_MENU_GLOBAL + "btn_left.png")).position(23, 634).click({keyDown Input.Keys.ESCAPE}) to group1
		label = $("菜单", 45).position(116, 621).size(91, 94).center() to group1
		
		$("游戏已进行 ${archive.time()}", 23).right().position(230, 617).size(284, 48) to group1

		hr = $("base").size(490, 4).position(24, 604) to group1

		def heros = archive.party.current()
		fgLabel = $ new HeroFGLabel() to stage

		heros.eachWithIndex {hero, idx ->
			def box = $ new HeroSelector(hero)
			heroBox += box.position(24 + idx * 128, 437) to group1

			box.get().click({
				heroBox.each {it.get().unselect()}
				box.get().select()
				currentHero = box

				fgLabel.get().setHero(box.get().hero())
			})
		}
		heroBox[MathUtils.random(0, heroBox.size() - 1)].get().click()

		$(Path.IMAGE_MENU_GLOBAL + "icon_position.png").position(48, 360) to group1
		$("${Game.view.map.name()} [ ${archive.position.x}, ${archive.position.y} ]", 32).size(394, 48).position(120, 361) to group1

		$(Path.IMAGE_MENU_GLOBAL + "icon_gold.png").position(53, 298) to group1
		$("${archive.gold} 金币", 32).size(394, 48).position(120, 298) to group1

		$(Path.IMAGE_MENU_GLOBAL + "icon_flag.png").position(47, 233) to group1
		$("当前无任务", 32).size(394, 48).position(120, 233) to group1//TODO

		//button1
		def button1 = $(UI.button()).size(216, 100).position(32, 110).to(group1).get()
		button1.addActor $(Path.IMAGE_MENU_GLOBAL + "icon_save.png").position(13, 15).get()
		button1.addActor $("存档", 36).position(113, 38).size(75, 52).get()
		button1.addActor $("SAVE", 22).position(120, 3).size(58, 52).get()

		def button2 = $(UI.button()).size(216, 100).position(287, 110).to(group1).get()
		button2.addActor $(Path.IMAGE_MENU_GLOBAL + "icon_load.png").position(13, 15).get()
		button2.addActor $("读档", 36).position(113, 38).size(75, 52).get()
		button2.addActor $("LOAD", 22).position(120, 3).size(58, 52).get()

		def button3 = $(UI.button()).size(471, 50).position(32, 31).to(group1).click(this.&toMenu).get()
		button3.addActor $(Path.IMAGE_MENU_GLOBAL + "icon_more.png").position(209, 3).get()


		group1.children().list().eachWithIndex {dom, idx ->
			dom.x = dom.x - idx * 40
			dom.addAction(Actions.moveBy(40 * idx, 0, 0.9f, Interpolation.pow4Out))
		}

		buttons << new MenuLeftBarButton(zh: "状态", en: "STATUS")
	}

	@CompileStatic
	void toMenu() {
		if(menu) return
		menu = true
		fgLabel.get().toMenu()
		group1.cleanActions().action(Actions.sequence(Actions.parallel(Actions.moveBy(-600, 0, 0.4f, Interpolation.pow4Out), Actions.fadeOut(0.1f)), Actions.removeActor()))
		bar.cleanActions().action(Actions.moveBy(-310, 0, 0.4f, Interpolation.pow4Out))

		def group2 = $ new Group() to stage
		$(exit, label, hr) into group2
		hr.action(Actions.sizeTo(183, 4, 0.3f, Interpolation.pow4Out))
	}

	boolean keyDown(int code) {
		if(code == Input.Keys.ESCAPE)
			Game.view.menu.hide()

		if(code == Input.Keys.RIGHT || code == Input.Keys.DOWN){
			if(heroBox.indexOf(currentHero) == heroBox.size() - 1)
				currentHero = heroBox[0]
			else
				currentHero = heroBox[heroBox.indexOf(currentHero) + 1]
			currentHero.get().click()
		}

		if(code == Input.Keys.LEFT || code == Input.Keys.UP){
			if(heroBox.indexOf(currentHero) == 0)
				currentHero = heroBox[heroBox.size() - 1]
			else
				currentHero = heroBox[heroBox.indexOf(currentHero) - 1]

			currentHero.get().click()
		}

		return super.keyDown(code)
	}

}