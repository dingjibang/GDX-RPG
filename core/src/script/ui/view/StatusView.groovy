package script.ui.view

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.rpsg.rpg.core.Game
import com.rpsg.rpg.core.Log;
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.core.Res
import com.rpsg.rpg.core.UI;
import com.rpsg.rpg.object.hero.Hero
import com.rpsg.rpg.object.item.Equipment
import com.rpsg.rpg.object.prop.PropKey
import com.rpsg.rpg.object.prop.PropType;
import com.rpsg.rpg.ui.widget.Button
import com.rpsg.rpg.ui.widget.LabelImageCheckbox

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
		$("base#333333").size(610, 50).a(0.9f).position(43, 2) to group1
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
		$("base#333333").size(610, 109).a(0.9f) to group2
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
		
		
		//		有效率****************
		def group3 = new Table().top().left()
		
		def group31 = new Group()
		$("base#c33737").size(610, 40).a(0.9f) to group31
		$("属性有效率", 22).size(140, 40).center().x(227) to group31
		
		def help = $(new Group()).y(0).a(0);
		$("base#000000").a(0.8f).size(610, 40 * PropType.values().length) to help
				
		$(new LabelImageCheckbox("帮助", 22, 
			Res.sync(Path.IMAGE_MENU_STATUS + "triangle_d.png"), 
			Res.sync(Path.IMAGE_MENU_STATUS + "triangle_u.png"), 
			$("base#811c1c").get(), 
			$("base#912424").get()
		).click({
			def flag = !!help.userObject();
			help.cleanActions().action(flag ? Actions.fadeOut(0.3f, Interpolation.pow4Out) : Actions.fadeIn(0.3f, Interpolation.pow4Out)).userObject(!flag);
		})).size(133, 40).x(610 - 133) to group31
	
		group3.add(group31).size(610, 40).row()
		
		PropType.values() eachWithIndex {val, idx ->
			def group32 = new Group()
			$("base#333333").a(0.9f).size(610, 40) to group32
			if(idx % 2 != 0) $("base").a(0.1f).size(610, 40) to group32
			$("base").a(0.1f).size(177, 40) to group32
			
			
			def icon = Res.sync(Path.IMAGE_MENU_STATUS + val.name() + ".png").query() to group32
			def offsetx = 30 / 2 - icon.width() / 2, offsety = 40 / 2 - icon.height() / 2
			icon.position(63 + (int)offsetx, (int)offsety)
			
			$(val.description(), 22).size(45, 40).x(96) to group32
			
			def v = hero.target.get(PropKey.valueOf(val.name()));
			
			
			float r = v >= 100 ? 1 : (v < 0 ? 0 : v) / 100
			float g = v <= 100 ? 1 : (200 - (v > 200 ? 200 : v)) / 100
			float b = v >= 100 ? (200 - (v > 200 ? 200 : v)) / 100 : (v < 0 ? 0 : v) / 100
			
			Log.i "v:${v}, r:${r}, g:${g}, b:${b}"
			
			$(v + "%", 22).center().size(433, 40).x(176).color(r, g, b) to group32
			
			group3.add(group32).size(610, 40).row()
		}
		group3.addActor help.get()
		group3.layout()
		table.add(group3).padBottom(50).padLeft(45).row()
		//end 	有效率****************
		
		//start equipment
		def equiplist = hero.target.getEquipments()
		Equipment.Parts.values() eachWithIndex {val, idx ->
			def group4 = $(new Group()).size(611, 100).get()
			$("base#333333").a(0.9f).size(611, 100) to group4
			if(idx %2 == 0) $("base").a(0.1f).size(611, 100) to group4
			def equip = equiplist.get(val)
			$(equip ? equip.name : "无装备", 26).size(480, 40).position(117, 50) to group4
			$(equip ? equip.description : "", 20).size(480, 40).position(117, 10).get().overflow(true).query() to group4
			$(equip ? equip.getIcon() : UI.base().query().a(0)).size(100, 100) to group4
			table.add(group4).padLeft(45).row()
		}
		//end   equipment

		table.add().padBottom(50)
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