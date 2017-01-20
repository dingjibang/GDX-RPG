/**
 * 角色标签（菜单）
 * @include Context
 * @extends com.badlogic.gdx.scenes.scene2d.Group
 */

var fg,fgs;
var bg;
var label, jlabel;

this.create = function(param) {
	fgs = $(new com.rpsg.rpg.ui.widget.AsyncLoadImage()).to(this).color(.03, .03, .03, .3).position(1600, 0).size(573, 687).disableTouch().scaleX(-1);
	fgs.get().setScaling(com.badlogic.gdx.utils.Scaling.fit);

	fg = $(new com.rpsg.rpg.ui.widget.AsyncLoadImage()).to(this).position(1500, 0).size(573, 687).disableTouch().scaleX(-1);
	fg.get().setScaling(com.badlogic.gdx.utils.Scaling.fit);

	bg = $("base").color("222222").a(.8).size(466, 90).position(1844, 100).to(this);

	label = $("", 39).size(466, 90).center().position(1844, 105).to(this);
	jlabel = $("", 31).size(396, 65).right().a(.2).position(1844, 100).to(this);
}


this.setHero = function(hero){
	//generate fg
	fg.x(1500).cleanActions().action(Actions.moveBy(-750, 0, .8, Interpolation.pow4Out)).get().setDrawableAsync(Path.IMAGE_FG + hero.fg + "/Normal.png");
	fgs.x(1530).cleanActions().action(Actions.moveBy(-750, 0, .9, Interpolation.pow4Out)).get().setDrawableAsync(Path.IMAGE_FG + hero.fg + "/Normal.png");
	bg.x(1844).cleanActions().action(Actions.moveBy(-1100, 0, 1, Interpolation.pow4Out));
	label.x(1844).cleanActions().action(Actions.moveBy(-1100, 0, 1.08, Interpolation.pow4Out)).get().setText(hero.name);
	jlabel.x(1844).cleanActions().action(Actions.moveBy(-1100, 0, 1.13, Interpolation.pow4Out)).get().setText(hero.jname);

	$(this).a(0).cleanActions().action(Actions.fadeIn(.5));
};
