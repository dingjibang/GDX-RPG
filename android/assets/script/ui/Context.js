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

var Path = com.rpsg.rpg.core.Path;

var Button = com.rpsg.rpg.ui.Button;

var print = obj => java.lang.System.out.println(obj);

/**
 * fast util
 *
 * $("base") return a base texture and GDX-Query it.
 * $("imagePath") return a texture and GDX-Query it.
 * $(javaList, iterator) to each element.
 * $("JSViewName", initParam) return custom JS View and Gdx-Query it.
 * $(else) Gdx-Query it.
 */
var $ = (obj, param2) => {
	if(typeof obj == "function")
		return new java.lang.Runnable({run: obj});

	if(typeof obj == "string"){
		if(obj == "base")
			return new com.rpsg.gdxQuery.GdxQuery(UI.base());

		if(typeof param2 == "object"){
			var widget = com.rpsg.rpg.ui.JSWidget.of("widget/" + obj);
			widget.create(param2);
			return new com.rpsg.gdxQuery.GdxQuery(widget);
		}

		if(typeof param2 == "number")
			return Res.text.getLabel(obj, param2).query();

		return new com.rpsg.gdxQuery.GdxQuery(Res.get(obj));
	}

	if(typeof param2 == "function" && obj instanceof java.util.List){
		for(var i = 0; i < obj.size(); i++)
			param2(i, obj.get(i));
		return;
	}

	if(typeof param2 == "function"){
		for(var i = 0; i < obj.size; i++)
			param2(i, obj.get(i));
		return;
	}

	return new com.rpsg.gdxQuery.GdxQuery(obj);
};

var STAGE_WIDTH = Game.STAGE_WIDTH;
var STAGE_HEIGHT = Game.STAGE_HEIGHT;
