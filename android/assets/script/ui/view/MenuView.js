/**
 * GDX-RPG 菜单视图
 */
//#include Context.js

this.create = function(){
	this.stage = Game.stage();
	
	$("base").fillParent().color("333333").a(0).to(stage).fadeTo(.3);
	
	var group = $(new Group()).to(this.stage).x(-800).action(Actions.moveTo(0, 0, .5, Interpolation.pow4Out));
	$("base").size(540, 720).color("333333").a(0.9).to(group).click($(() => {
		print("asd");
	}));
	
};

var time = 180;

this.draw = function(){
	this.stage.draw();
	if(time-- <= 0)
		menu.hide();
};

this.act = function(){
	this.stage.act();
};

this.keyDown = function(code){
	if(code == Keys.ESCAPE)
		menu.hide();
	return true;
};