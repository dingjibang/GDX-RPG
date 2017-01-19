/**
 * GDX-RPG 菜单视图
 * @include Context
 */

var archive = Game.archive.get();

this.create = function(){

	stage = this.stage = Game.stage();

	// stage.setDebugAll(true);

	$("base").size(1280, 720).color("333333").a(0).to(stage).fadeTo(.4, .3);

	var group = $(new Group()).to(stage).x(-800).action(Actions.moveTo(0, 0, .5, Interpolation.pow4Out));

	$("base").size(540, 720).color("333333").a(0.9).to(group);
	$(new Button(Path.IMAGE_MENU_GLOBAL + "btn_left.png")).position(23, 634).to(group).click($(() => menu.hide()));
	$("菜单", 45).position(116, 611).size(91, 94).center().to(group);
	$("游戏已进行 " + archive.time(), 23).right().position(230, 602).size(284, 48).to(group);

	$("base").size(490, 4).position(24, 589).to(group);

	var heros = archive.party.current();

	var heroBox = [];
	$(heros, (idx, hero) => {
		var box = $("HeroSelector", hero);
		heroBox.push(box.to(group).position(24 + idx * 128, 437));
		//box on click
		box.get().click(() => {
			for(var i in heroBox)
				heroBox[i].get().unselect();
			box.get().select();
		});
	});

	heroBox[0].get().click(null);


	$(group.children().list(), (idx, dom) => {
		var x = dom.getX();
		dom.setX(dom.getX() - idx * 50);
		dom.addAction(Actions.moveBy(50 * idx, 0, .9, Interpolation.pow4Out));
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
	return this.super$keyDown(code);
};