package script.ui.view

import static com.rpsg.rpg.util.UIUtil.*

import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.object.hero.Hero
import com.rpsg.rpg.object.prop.PropKey
import com.rpsg.rpg.ui.widget.Button
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table



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
		
		stage.debugAll = true
		
		def table = new Table().padTop(70).left().top()
		
		//		hero name & button****************
		def group1 = $(new Group()).size(650, 110)
		$("base").size(610, 50).color("333333").a(0.9f).position(43, 2) to group1
		$(new Button(Path.IMAGE_MENU_GLOBAL + "triangle_l.png")).y(20).click {
			parent.prev()
			generate()
		} to group1
		$(new Button(Path.IMAGE_MENU_GLOBAL + "triangle_r.png")).y(20).x(624).click {
			parent.next()
			generate()
		} to group1
		$(hero.name, 61).center().size(610, 100).position(43, 12) to group1
		$(hero.jname, 28).center().size(610, 41).a(0.4f).position(43, -2) to group1
		
		table.add(group1.get()).padBottom(50).row()
		//end 	hero name & button****************
		
		
		//		level info****************
		def group2 = $(new Group()).size(610, 109)
		$("base").size(610, 109).color("333333").a(0.9f) to group2
		$(hero.target.get(PropKey.level), 50).size(95, 73).center().position(25, 36) to group2
		table.add(group2.get()).padBottom(50).padLeft(45).row()
		//end 	level info****************
		 
		
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

}