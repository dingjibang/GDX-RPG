/**
 * GDX-RPG 菜单视图
 * @include Context
 */

var archive = Game.archive.get();
var heroBox = [], currentHero = null;
var bar;

this.create = function(){

	stage = this.stage = Game.stage();

	// stage.setDebugAll(true);

	bar = $("base").size(1280, 720).color("333333").a(0).to(stage).fadeTo(.4, .3);

	var group = $(new Group()).to(stage).x(-800).action(Actions.moveTo(0, 0, .5, Interpolation.pow4Out));

	$("base").size(540, 720).color("333333").a(0.9).to(group);
	$(new Button(Path.IMAGE_MENU_GLOBAL + "btn_left.png")).position(23, 634).to(group).click($(() => menu.hide()));
	$("菜单", 45).position(116, 611).size(91, 94).center().to(group);
	$("游戏已进行 " + archive.time(), 23).right().position(230, 602).size(284, 48).to(group);

	$("base").size(490, 4).position(24, 589).to(group);

	var heros = archive.party.current();

	var fgLabel = $("menu.HeroFGLabel", {}).to(stage);

	$(heros, (idx, hero) => {
		var box = $("menu.HeroSelector", hero);
		heroBox.push(box.to(group).position(24 + idx * 128, 437));
		//box on click
		box.get().click(() => {
			for(var i in heroBox)
				heroBox[i].get().unselect();
			box.get().select();
			currentHero = box;

			fgLabel.get().setHero(box.get().hero());
		});
	});

	heroBox[random(0, heroBox.length - 1)].get().click(null);

	$(Path.IMAGE_MENU_GLOBAL + "icon_position.png").to(group).position(48, 360);
	$(Game.view.map.name() + " [" + archive.position.x + ", " + archive.position.y + "]", 32).to(group).size(394, 48).position(120, 361);

	$(Path.IMAGE_MENU_GLOBAL + "icon_gold.png").to(group).position(53, 298);
	$(archive.gold + " 金币", 32).to(group).size(394, 48).position(120, 298);

	$(Path.IMAGE_MENU_GLOBAL + "icon_flag.png").to(group).position(47, 233);
	$("当前无任务", 32).to(group).size(394, 48).position(120, 233);//TODO

	// $(new Button(Path.IMAGE_MENU_GLOBAL + "icon_save.png"))


	$(group.children().list(), (idx, dom) => {
		var x = dom.getX();
		dom.setX(dom.getX() - idx * 40);
		dom.addAction(Actions.moveBy(40 * idx, 0, .9, Interpolation.pow4Out));
	});


};

this.draw = function(){
	this.stage.draw();
};

this.act = function(){
	this.stage.act();
};

this.keyDown = function(code){
	if(code == Keys.ESCAPE)
		menu.hide();

	if(code == Keys.RIGHT || code == Keys.DOWN){
		if(heroBox.indexOf(currentHero) == heroBox.length - 1)
			currentHero = heroBox[0];
		else
			currentHero = heroBox[heroBox.indexOf(currentHero) + 1];

		currentHero.get().click(null);
	}

	if(code == Keys.LEFT || code == Keys.UP){
		if(heroBox.indexOf(currentHero) == 0)
			currentHero = heroBox[heroBox.length - 1];
		else
			currentHero = heroBox[heroBox.indexOf(currentHero) - 1];

		currentHero.get().click(null);
	}

	return this.super$keyDown(code);
};