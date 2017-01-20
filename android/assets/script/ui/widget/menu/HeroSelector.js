/**
 * 角色选择器（菜单）
 * @include Context
 * @extends com.badlogic.gdx.scenes.scene2d.Group
 */

var walkTexture = [];
var walkImg = $("base").disableTouch();
var outer = $("base").disableTouch().a(0).size(110, 132).position(-2, -2);
var onclick = null;

var hero = null;

this.create = function(_hero) {
	hero = _hero;

	outer.to(this);

	//bg
	$("base").size(106, 128).color(hero.color).to(this).click($(() => {
		if(onclick != null)
			onclick();
	}));

	//hero walk sprite
	var texture = hero.sprite.getTexture();
	for(var i = 0; i < 3; i++){
		var region = new com.badlogic.gdx.graphics.g2d.TextureRegion(texture);
		//干他吗的弱智JS，see http://www-archive.mozilla.org/js/liveconnect/lc3_method_overloading.html
		region["setRegion(int,int,int,int)"](i * 48, 192, 48, 64);
		walkTexture.push(new com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable(region));
	}
	walkImg.to(this).a(.95).size(48, 64).scale(2).position(5, 1).get().setDrawable(walkTexture[0]);
	walkImg.nearest();


	//properties
	var PropKey = com.rpsg.rpg.object.prop.PropKey;
	//hp
	$("base").size(106, 5).color("e0e0e0").position(0, 5).to(this).disableTouch();
	$("base").height(5).color("e84e40").position(0, 5).to(this).width(hero.target.get(PropKey.hp) / hero.target.get(PropKey.maxhp) * 106).disableTouch();

	//mp
	$("base").size(106, 5).color("e0e0e0").position(0, 0).to(this).disableTouch();
	$("base").height(5).color("3b81db").position(0, 0).to(this).width(hero.target.get(PropKey.mp) / hero.target.get(PropKey.maxmp) * 106).disableTouch();

	//level
	$("base").size(106, 23).to(this).y(10).disableTouch();
	$("" + hero.target.get(PropKey.level), 16).size(106, 23).center().color("000000").to(this).y(10).disableTouch();

}

var timer = 27, currentWalk = 0;
this.draw = function(batch, alpha) {
	if(timer-- < 0){
		timer = 27;
		if(++currentWalk == 4) currentWalk = 0;
		walkImg.get().setDrawable(walkTexture[currentWalk == 3 ? 1 : currentWalk]);
		walkImg.nearest();
	}

	this.super$draw(batch, alpha);
}


this.select = function(){
	outer.a(1).action(Actions.forever(Actions.sequence(Actions.fadeIn(0.3), Actions.fadeOut(0.3))));
};

this.unselect = function () {
	outer.a(0).cleanActions();
}

this.click = function(callback) {
	if(callback)
		onclick = callback;
	else if(onclick)
		onclick();

	return this;
}

this.hero = function() {
	return hero;
}