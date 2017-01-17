//#include GdxQuery.js
var Game = com.rpsg.rpg.core.Game;
var UI = com.rpsg.rpg.core.UI;
var Color = com.badlogic.gdx.graphics.Color;
var menu = Game.view.menu; 
var Keys = com.badlogic.gdx.Input.Keys;
var Res = com.rpsg.rpg.core.Res;

var scene2d = com.badlogic.gdx.scenes.scene2d;
var Group = scene2d.Group;
var Actions = scene2d.actions.Actions;
var Interpolation = com.badlogic.gdx.math.Interpolation;

var print = obj => java.lang.System.out.println(obj);

var $ = obj => {
	if(obj instanceof Function)
		return new java.lang.Runnable({run: obj});
	
	if(obj instanceof String){
		if(obj === "base")
			return $(UI.base());
		return $(Res.get(obj));
	}
	
	return new com.rpsg.gdxQuery.GdxQuery(obj);
}; 
