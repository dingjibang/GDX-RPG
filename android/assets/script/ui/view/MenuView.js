/**
 * GDX-RPG 菜单视图
 */
//#include view/context.js

this.create = function(){
	this.stage = Game.stage();
	
	$(UI.base()).size(1000, 500).position(100, 100).color(Color.RED).appendTo(this.stage).click(new java.lang.Runnable(function(){
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

