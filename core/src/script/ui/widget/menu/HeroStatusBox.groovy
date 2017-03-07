package script.ui.widget.menu

import static com.rpsg.rpg.util.UIUtil.*

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.object.hero.Hero
import com.rpsg.rpg.object.prop.PropKey;
import com.rpsg.rpg.ui.widget.AsyncLoadImage
import com.rpsg.rpg.ui.widget.Image;

class HeroStatusBox extends Table {
	Hero hero
	
	HeroStatusBox(Hero hero){
		this.hero = hero
		
		top().left()
		
		setWidth 610
		
		//hp
		def group1 = $(new Group())
		$("base").size(610, 40).color("333333").a(0.9f) to group1
		$(Path.IMAGE_MENU_STATUS + "hp.png").position(26, 7) to group1
		$("生命", 22).size(45, 32).position(61, 3).center() to group1
		$(new ProgressBar("8a251c", 270, 30, hero.target.get(PropKey.hp), hero.target.get(PropKey.maxhp))).position(133, 5) to group1
		$("base").size(195, 40).a(0.1f).x(415) to group1
		$(hero.target.get(PropKey.hp) + "/" + hero.target.get(PropKey.maxhp), 22).size(195, 32).center().position(415, 3) to group1
		add(group1.get()).size(610, 40).padBottom(4).colspan(4).row()
		
		//mp
		def group2 = $(new Group())
		$("base").size(610, 40).color("333333").a(0.9f) to group2
		$(Path.IMAGE_MENU_STATUS + "mp.png").position(24, 7) to group2
		$("妖力", 22).size(45, 32).position(61, 3).center() to group2
		$(new ProgressBar("505ca3", 270, 30, hero.target.get(PropKey.mp), hero.target.get(PropKey.maxmp))).position(133, 5) to group2
		$("base").size(195, 40).a(0.1f).x(415) to group2
		$(hero.target.get(PropKey.mp) + "/" + hero.target.get(PropKey.maxmp), 22).size(195, 32).center().position(415, 3) to group2
		add(group2.get()).size(610, 40).padBottom(4).colspan(4).row()
		
		//properties
		add(new BackgroundableLabel("333333", 0.9f, "ffffff", 0.1f, "物理攻击", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("333333", 0.9f, hero.target.get(PropKey.patk), 22)).size(165, 40)
		add(new BackgroundableLabel("333333", 0.9f, "ffffff", 0.1f, "物理防御", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("333333", 0.9f, hero.target.get(PropKey.pdef), 22)).size(165, 40)
		row()
		
		add(new BackgroundableLabel("444444", 0.9f, "ffffff", 0.1f, "魔法攻击", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("444444", 0.9f, hero.target.get(PropKey.matk), 22)).size(165, 40)
		add(new BackgroundableLabel("444444", 0.9f, "ffffff", 0.1f, "魔法防御", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("444444", 0.9f, hero.target.get(PropKey.mdef), 22)).size(165, 40)
		row()
		
		add(new BackgroundableLabel("333333", 0.9f, "ffffff", 0.1f, "命中", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("333333", 0.9f, hero.target.get(PropKey.hit), 22)).size(165, 40)
		add(new BackgroundableLabel("333333", 0.9f, "ffffff", 0.1f, "闪避", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("333333", 0.9f, hero.target.get(PropKey.evasion), 22)).size(165, 40)
		row()
		
		add(new BackgroundableLabel("444444", 0.9f, "ffffff", 0.1f, "暴击率", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("444444", 0.9f, hero.target.get(PropKey.criticalRate), 22)).size(165, 40)
		add(new BackgroundableLabel("444444", 0.9f, "ffffff", 0.1f, "暴击伤害", 22)).size(140, 40).space(0).left()
		add(new BackgroundableLabel("444444", 0.9f, hero.target.get(PropKey.critical), 22)).size(165, 40)
		row()
		
	}
	
}
