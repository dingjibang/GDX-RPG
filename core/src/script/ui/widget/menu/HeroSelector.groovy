package script.ui.widget.menu

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.rpsg.rpg.object.hero.Hero
import com.rpsg.rpg.object.prop.PropKey

import static com.rpsg.rpg.util.UIUtil.*

class HeroSelector extends Group {
	def walkTexture = [];
	def walkImg = $("base").disableTouch();
	def outer = $("base").disableTouch().a(0).size(110, 132).position(-2, -2);
	def onclick = null;

	Hero hero

	HeroSelector(_hero){
		this.hero = _hero

		outer.to this

		$("base").size(106, 128).color(hero.color).click({
			if(onclick != null)
				onclick()
		}) to this

		def texture = hero.sprite.getTexture()

		(0..2).each {
			walkTexture += new TextureRegionDrawable(new TextureRegion(texture, it * 48, 192, 48, 64))
		}

		walkImg.a(0.95).size(48, 64).scale(2).position(5, 1).get().setDrawable(walkTexture[0])
		walkImg.nearest() to this


		$("base").size(106, 5).color("e0e0e0").position(0, 5).disableTouch() to this
		$("base").height(5).color("e84e40").position(0, 5).width(hero.target.get(PropKey.hp) / hero.target.get(PropKey.maxhp) * 106).disableTouch() to this

		//mp
		$("base").size(106, 5).color("e0e0e0").position(0, 0).disableTouch() to this
		$("base").height(5).color("3b81db").position(0, 0).width(hero.target.get(PropKey.mp) / hero.target.get(PropKey.maxmp) * 106).disableTouch() to this

		//level
		$("base").size(106, 23).y(10).disableTouch() to this
		$("" + hero.target.get(PropKey.level), 16).size(106, 23).center().color("000000").y(10).disableTouch() to this
	}

	def timer = 27, currentWalk = 0;
	void draw(Batch batch, float parentAlpha) {
		if(timer-- < 0){
			timer = 27
			if(++currentWalk == 4) currentWalk = 0;
			walkImg.get().setDrawable(walkTexture[currentWalk == 3 ? 1 : currentWalk])
			walkImg.nearest()
		}
		super.draw(batch, parentAlpha)
	}

	void select() {
		outer.a(1).action(Actions.forever(Actions.sequence(Actions.fadeIn(0.3), Actions.fadeOut(0.3))))
	}

	void unselect() {
		outer.a(0).cleanActions()
	}

	void click(callback) {
		onclick = callback
	}

	void click() {
		if(onclick)
			onclick()
	}

	Hero hero() {
		return hero
	}

}
