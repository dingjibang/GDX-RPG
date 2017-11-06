package script.ui.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.rpsg.rpg.core.*
import script.ui.widget.menu.CheckBox

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
			Game.toast.showToast("test123测试测试", 20);
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

		def gray = Color.valueOf("cccccc")

		$("图形设置", 60) to group2
		group2.add($("Graphics", 20).get()).padTop(-15).right().row()
		group2.add(Res.sync(Path.IMAGE_MENU_SYSTEM + "split.png")).padTop(15).padBottom(40).row()

		com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle cstyle = new com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle();
		cstyle.font = Res.text.get(24)
		cstyle.checkboxOn = Res.getDrawable(Path.IMAGE_GLOBAL + "check_on.png")
		cstyle.checkboxOff = Res.getDrawable(Path.IMAGE_GLOBAL + "check_off.png")

		group2.add(new CheckBox("开启平滑纹理", cstyle).padText(20).checked(Game.setting.filter).click({_this -> Game.setting.filter = _this.checked})).left().padLeft(40).row()
		group2.add($("使用线性纹理过滤，图片放大缩小将减少锯齿撕裂感\n重启游戏后生效", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		group2.add(new CheckBox("开启高级画质", cstyle).padText(20).checked(Game.setting.enablePost).click({_this -> Game.setting.enablePost = _this.checked})).left().padLeft(40).padTop(25).row()
		group2.add($("开启后，对画面将进行一系列后期处理，将稍微影响性能", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		group2.add(new CheckBox("适配屏幕尺寸", cstyle).padText(20).checked(Game.setting.fitScaling).click({_this -> Game.setting.fitScaling = _this.checked})).left().padLeft(40).padTop(25).row()
		group2.add($("无论如何都以16:9的游戏尺寸显示\n重启游戏后生效", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		group2.add(new CheckBox("高清字体", cstyle).padText(20).checked(Game.setting.hdFont).click({_this -> Game.setting.hdFont = _this.checked})).left().padLeft(40).padTop(25).row()
		group2.add($("使用更清晰的字体纹理，在高分辨率显示屏/手机上效果更好，但会消耗额外的内存\n重启游戏后生效", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		group2.add(new CheckBox("显示FPS(游戏帧数)", cstyle).padText(20).checked(Game.setting.showFPS).click({_this -> Game.setting.showFPS = _this.checked})).left().padLeft(40).padTop(25).row()
		group2.add($("你是强迫症么？", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		table.add(group2).padBottom(50).row()
		/**画面设置 end*/



		/**性能设置*/
		def group3 = new Table().top().left().padTop(70)


		$("性能设置", 60) to group3
		group3.add($("Performance", 20).get()).padTop(-15).right().row()
		group3.add(Res.sync(Path.IMAGE_MENU_SYSTEM + "split.png")).padTop(15).padBottom(40).row()

		group3.add(new CheckBox("使用实验性质的文字渲染引擎", cstyle).padText(20).checked(Game.setting.newTextRender).click({_this -> Game.setting.newTextRender = _this.checked})).left().padLeft(40).row()
		group3.add($("新的文字渲染引擎理论可以提高游戏性能，但是会消耗额外的内存\n重启游戏后生效", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		group3.add(new CheckBox("缓存游戏资源", cstyle).padText(20).checked(Game.setting.cache).click({_this -> Game.setting.cache = _this.checked})).left().padLeft(40).padTop(25).row()
		group3.add($("缓存游戏中的资源，将提高加载速度，但会消耗额外的内存\n重启游戏后生效", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		table.add(group3).padBottom(50).row()
		/**画面设置 end*/


		/**开发者选项*/
		def group4 = new Table().top().left().padTop(70)


		$("开发者选项", 60) to group4
		group4.add($("Developer", 20).get()).padTop(-15).right().row()
		group4.add(Res.sync(Path.IMAGE_MENU_SYSTEM + "split.png")).padTop(15).padBottom(40).row()

		group4.add(new CheckBox("开启界面调试", cstyle).padText(20).checked(Game.setting.uiDebug).click({_this -> Game.setting.uiDebug = _this.checked})).left().padLeft(40).row()
		group4.add($("Enable Stage Debug(uiDebug)", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		group4.add(new CheckBox("遇到致命错误仍然继续游戏", cstyle).padText(20).checked(Game.setting.onErrorResumeNext).click({_this -> Game.setting.onErrorResumeNext = _this.checked})).left().padLeft(40).padTop(25).row()
		group4.add($("开启后将忽略错误继续进行游戏，开启将极度降低游戏性能(onErrorResumeNext)", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		group4.add(new CheckBox("启用控制台", cstyle).padText(20).checked(Game.setting.console).click({_this -> Game.setting.console = _this.checked})).left().padLeft(40).padTop(25).row()
		group4.add($("使用控制台查看日志，或对游戏内容更改(Console)", 20).color(gray).get()).padLeft(100).padTop(10).left().row()

		table.add(group4).padBottom(50).row()
		/**画面设置 end*/




		$(new ScrollPane(table)).position(320, 0).size(960, 720) to stage
	}
	
	void draw() {
		super.draw();
	}

	void onRemove(){
		Game.setting.save()
	}


}