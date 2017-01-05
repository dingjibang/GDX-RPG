/**
 * GDX-RPG 地图常用上下文
 */
var Game = com.rpsg.rpg.core.Game;

function _parseToJS(jsonArray) {
	var obj = {};
	for(var i = 0; i < jsonArray.size; i++){
		var json = jsonArray.get(i);
		obj[json.getString("id")] = json;
	}
	return obj;
}

var MSG = _parseToJS(Game.prop.get("msg"));
