package script.ui.view

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.rpsg.rpg.core.Game
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.object.hero.Hero
import com.rpsg.rpg.object.prop.PropKey
import com.rpsg.rpg.ui.widget.Button
import script.ui.widget.menu.HeroStatusBox
import script.ui.widget.menu.ProgressBar

import static com.rpsg.rpg.util.UIUtil.*
/**
 * GDX-RPG 人物状态菜单
 */
class StatusView extends MenuableView{

	void create() {
		generate()
	}
	
	void generate() {
		stage.clear()
		
		Hero hero = parent.currentHero.get().hero
		
		stage.debugAll = false
		
		def table = new Table().padTop(70).left().top()
		
		//		hero name & button****************
		def group1 = $(new Group()).size(650, 110)
		$("base").size(610, 50).color("333333").a(0.9f).position(43, 2) to group1
		$(new Button(Path.IMAGE_MENU_GLOBAL + "triangle_l.png")).y(20).click {
			keyDown(Keys.LEFT)
		} to group1
		$(new Button(Path.IMAGE_MENU_GLOBAL + "triangle_r.png")).y(20).x(624).click {
			keyDown(Keys.RIGHT)
		} to group1
		$(hero.name, 61).center().size(610, 100).position(43, 12) to group1
		$(hero.jname, 28).center().size(610, 41).a(0.4f).position(43, -2) to group1
		
		table.add(group1.get()).padBottom(50).row()
		//end 	hero name & button****************
		
		
		//		level info****************
		def group2 = $(new Group()).size(610, 109)
		$("base").size(610, 109).color("333333").a(0.9f) to group2
		$(hero.target.get(PropKey.level), 50).size(95, 73).center().position(25, 36) to group2
		$(hero.tag, 25).size(95, 44).center().position(25, 8).a(0.7f) to group2
		$(hero.target.get(PropKey.exp) + "/" + hero.target.get(PropKey.nextExp), 20).size(289, 30).position(141, 62) to group2
		int level = hero.target.get(PropKey.level), max = Game.prop.get("system").getInt("maxLevel")
		$("下一等级 " + (level == max ?: level + 1), 20).size(137, 30).right().position(430, 62) to group2
		$(new ProgressBar(hero.color, 438, 44, hero.target.get(PropKey.exp), hero.target.get(PropKey.nextExp))).position(132, 17) to group2
		table.add(group2.get()).padBottom(50).padLeft(45).row()
		//end 	level info****************
		
		
		//		hero properties****************
		table.add(new HeroStatusBox(hero)).padBottom(50).padLeft(45).row()
		//end 	hero properties****************
		
		table.layout()
		
		$(new ScrollPane(table)).position(320, 0).size(960, 720) to stage
	}
	
	void draw() {
		super.draw();
	}
	
	void onResume() {
		parent.fgLabel.get().show()
	}
	
	void onRemove() {
		parent.fgLabel.get().hide()
	}

	boolean keyDown(int code) {
		if(code == Keys.RIGHT || code == Keys.DOWN){
			parent.next()
			generate()
		}

		if(code == Keys.LEFT || code == Keys.UP){
			parent.prev()
			generate()
		}

		return super.keyDown(code)
	}
}