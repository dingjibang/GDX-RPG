var System = java.lang.System;
var Game = com.rpsg.rpg.core.Game;
var UI = com.rpsg.rpg.core.UI;
var Color = com.badlogic.gdx.graphics.Color;
var menu = Game.view.menu; 

var print = function(obj){
	System.out.println(obj);
};

var $ = function(obj){
	return com.rpsg.gdxQuery.$.add(obj);
}; 
