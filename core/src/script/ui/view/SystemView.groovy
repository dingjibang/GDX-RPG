package script.ui.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Scaling
import com.rpsg.rpg.core.Game
import com.rpsg.rpg.core.Log
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.core.Res
import com.rpsg.rpg.core.UI

import static com.rpsg.rpg.util.UIUtil.$

/**
 * GDX-RPG 系统配置视图
 */
class SystemView extends MenuableView{
	void create() {
//		stage.debugAll = true;

		def table = new Table().padTop(70).left().top()

		/**游戏状态 start*/
		def group1 = new Table().top().left()

		group1.addActor(UI.base().query().size(870	, 180).color(Color.BLACK).a(0.3f).y(-17).get())
		$("游戏档案", 60) to group1
		group1.add($("Save & Load", 20).get()).padTop(-15).right().row()
		group1.add(Res.sync(Path.IMAGE_MENU_SYSTEM + "split.png")).padTop(15).padBottom(40).row()

		def screenshot = new Image(parent.screenshot)

		screenshot.height = 250 / screenshot.width * screenshot.height
		screenshot.width = 250
		screenshot.scaleY = -1
		screenshot.originY = screenshot.height / 2

		def inset = new Table().left().top()
		inset.add(screenshot).prefSize(screenshot.width, screenshot.height).left().padLeft(20)

		def inset2 = new Table().left().top()
		inset2.add($("${Game.view.map.name()} [ ${parent.archive.position.x}, ${parent.archive.position.y} ]", 32).get()).left().padBottom(10).row()
		inset2.add($("游戏已进行 ${parent.archive.time()}", 22).get()).left().padBottom(29).row()


		def buttons = new Table().left().top()

		def saveButton = $(UI.button()).get()
		saveButton.addActor $("保存游戏", 22).width(170).y(14).center().get()
		buttons.add(saveButton).height(50).prefSize(170, 50).left().padRight(16)
		$(saveButton).click({
			Log.i("clicked!!")
		})

		def loadButton = $(UI.button()).get()
		loadButton.addActor $("读取游戏", 22).width(170).y(14).center().get()
		buttons.add(loadButton).height(50).prefSize(170, 50).left().padRight(16)

		def backButton = $(UI.button()).get()
		backButton.addActor $("返回菜单", 22).width(170).y(14).center().get()
		buttons.add(backButton).height(50).prefSize(170, 50).left()

		inset2.add(buttons).left()
		inset.add(inset2).left().padLeft(30)

		group1.add(inset).left().row()

		table.add(group1).padBottom(50).row()
		/**游戏状态 end*/



		/**画面设置*/
		def group2 = new Table().top().left().padTop(70)

		$("画面设置", 60) to group2
		group2.add($("Graphics", 20).get()).padTop(-15).right().row()
		group2.add(Res.sync(Path.IMAGE_MENU_SYSTEM + "split.png")).padTop(15).padBottom(40).row()

		table.add(group2).padBottom(50).row()
		/**画面设置 end*/




		$(new ScrollPane(table)).position(320, 0).size(960, 720) to stage
	}
	
	void draw() {
		super.draw();
	}
}