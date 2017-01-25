package script.ui.widget.menu

import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Scaling
import com.rpsg.gdxQuery.TypedGdxQuery
import com.rpsg.rpg.core.Path
import com.rpsg.rpg.object.hero.Hero
import com.rpsg.rpg.ui.widget.AsyncLoadImage

import static com.rpsg.rpg.util.UIUtil.*

class HeroFGLabel extends Group {
	def fg,fgs
	def bg
	TypedGdxQuery<Label> label, jlabel

	def menu = false

	HeroFGLabel(){
		fgs = $(new AsyncLoadImage()).color(0.03f, 0.03f, 0.03f, 0.3f).position(1600, 0).size(573, 687).disableTouch().scaleX(-1) to this
		fgs.get().setScaling(Scaling.fit)

		fg = $(new AsyncLoadImage()).position(1500, 0).size(573, 687).disableTouch().scaleX(-1) to this
		fg.get().setScaling(Scaling.fit)

		bg = $("base").color("222222").a(0.8f).size(466, 90).position(1844, 100) to this

		label = $("", 39).size(466, 90).center().position(1844, 105) to this
		jlabel = $("", 31).size(396, 65).right().a(0.2f).position(1844, 100) to this
	}

	void setHero(Hero hero){
		//generate fg
		fg.x(1500).cleanActions().action(Actions.moveBy(menu ? -620 : -750, 0,0.8f, Interpolation.pow4Out)).get().setDrawableAsync(Path.IMAGE_FG + hero.fg + "/Normal.png")
		fgs.x(1530).cleanActions().action(Actions.moveBy(menu ? -620 : -750, 0,0.9f, Interpolation.pow4Out)).get().setDrawableAsync(Path.IMAGE_FG + hero.fg + "/Normal.png")
		if(!menu){
			bg.x(1844).cleanActions().action(Actions.moveBy(-1100, 0, 1, Interpolation.pow4Out))
			label.x(1844).cleanActions().action(Actions.moveBy(-1100, 0, 1.08f, Interpolation.pow4Out)).text(hero.name)
			jlabel.x(1844).cleanActions().action(Actions.moveBy(-1100, 0, 1.13f, Interpolation.pow4Out)).text(hero.jname)
		}

		$(this).a(0).cleanActions().action(Actions.fadeIn(0.5f))
	}

	void toMenu() {
		menu = true
		$(label, bg, jlabel).cleanActions().list().each({
			it.addAction(Actions.sequence(Actions.parallel(Actions.moveBy(500, 0, 0.4f, Interpolation.pow4Out), Actions.fadeOut(0.2f)), Actions.removeActor()))
		})

		$(fg, fgs).cleanActions().list().each({it.addAction(Actions.moveBy(130, 0, 0.4f, Interpolation.pow4Out))})
	}

	void hide() {
		$(fg, fgs).cleanActions().list().each({it.addAction(Actions.moveBy(800, 0, 0.4f, Interpolation.pow4Out))})
	}

	void show() {
		setHero(hero);
	}

}
