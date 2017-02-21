package script.ui.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.AddAction
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.rpsg.gdxQuery.GdxQuery
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.*
import com.rpsg.rpg.ui.widget.Button

import script.ui.widget.menu.HeroFGLabel
import script.ui.widget.menu.HeroSelector
import script.ui.widget.menu.MenuLeftBarButton

import static com.rpsg.rpg.util.UIUtil.*

/**
 * GDX-RPG 菜单视图
 */
class MenuView extends UIView{

	def archive = Game.archive.get()
	def heroBox = []
	def currentHero = null
	GdxQuery bg, group1
	TypedGdxQuery<HeroFGLabel> fgLabel

	TypedGdxQuery label, exit, hr

	List<MenuLeftBarButton> buttons = []

	MenuLeftBarButton current

	boolean menu = false
	
	List<MenuableView> openedViewlist = []

	void create() {

//		stage.debugAll = true

		$("base").size(Game.STAGE_WIDTH, Game.STAGE_HEIGHT).color("333333").a(0).fadeTo(0.4f, 0.3f) to stage

		bg = q("base").size(540, 720).color("333333cc").x(-800).a(0.8f).action(Actions.moveTo(0, 0, 0.5f, Interpolation.pow4Out)) to stage

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

			box.get().click {
				heroBox.each {it.get().unselect()}
				box.get().select()
				currentHero = box

				fgLabel.get().setHero(box.get().hero())
			}
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
			dom.addAction Actions.moveBy(40 * idx, 0, 0.9f, Interpolation.pow4Out)
		}

		buttons << new MenuLeftBarButton(zh: "状态", en: "STATUS", to: StatusView.class)
		buttons << new MenuLeftBarButton(zh: "装备", en: "EQUIPMENT", to: EquipmentView.class)
		buttons << new MenuLeftBarButton(zh: "物品", en: "ITEM", to: null)
		buttons << new MenuLeftBarButton(zh: "符卡", en: "SPELLCARD", to: null)
		buttons << new MenuLeftBarButton(zh: "战术", en: "TACTICS", to: null)
		buttons << new MenuLeftBarButton(zh: "记录", en: "NOTE", to: null)
		buttons << new MenuLeftBarButton(zh: "系统", en: "SYSTEM", to: null)
	}

	void toMenu() {
		if(menu) return
		menu = true
		fgLabel.get().toMenu()
		group1.stopActions().action(Actions.sequence(Actions.parallel(Actions.moveTo(-600, 0, 0.4f, Interpolation.pow4Out), Actions.fadeOut(0.1f)), Actions.removeActor()))
		bg.stopActions().action(Actions.moveTo(-310, 0, 0.4f, Interpolation.pow4Out))

		def group2 = $ new Group() to stage
		$(exit, label, hr) into group2
		hr.stopActions().action(Actions.after(Actions.sizeTo(183, 4, 0.3f, Interpolation.pow4Out)))

		def table = $ new Table().center().left().padBottom(20)
		
		buttons.each {
			it.click {self ->
				if(current == self) return

				buttons.each {it.checked = false}
				self.checked = true
				
				//set view
				def viewClass = self.to
				if(!viewClass) return
				current = self
				
				MenuableView view = null
				openedViewlist.each {
					if(it.class.equals(viewClass))
						view = it
				}
				if(!view){
					view = viewClass.newInstance()
					openedViewlist += view
					view.parent = thisObject
					view.create()
				}
				
				Views.find(MenuableView.class)?.remove()	
				Views.addView view
				view.onResume()
			}

			table.cell(it).row()
		}
		
		stage.addAction Actions.delay(0.15f, Actions.run({buttons[0].click()}))
		
		table.eachCells{c -> c.padTB(5)}.layout()

		$(new ScrollPane(table.get())).size(247, 578).position(-280, 0).a(0).fadeIn(0.2f).action(Actions.moveBy(300, 0, 0.35f, Interpolation.pow4Out)) to stage

	}
	
	void draw() {
		super.draw();
	}
	
	void onRemove() {
		Views.find(MenuableView.class)?.remove()
		openedViewlist.clear()
	}
	
	void prev() {
		if(heroBox.indexOf(currentHero) == 0)
			currentHero = heroBox[heroBox.size() - 1]
		else
			currentHero = heroBox[heroBox.indexOf(currentHero) - 1]

		currentHero.get().click()
	}
	
	void next() {
		if(heroBox.indexOf(currentHero) == heroBox.size() - 1)
			currentHero = heroBox[0]
		else
			currentHero = heroBox[heroBox.indexOf(currentHero) + 1]
		currentHero.get().click()
	}

	boolean keyDown(int code) {
		if(code == Input.Keys.ESCAPE)
			Game.view.menu.hide()

		if(code == Input.Keys.RIGHT || code == Input.Keys.DOWN)
			next()

		if(code == Input.Keys.LEFT || code == Input.Keys.UP)
			prev()

		return super.keyDown(code)
	}

}