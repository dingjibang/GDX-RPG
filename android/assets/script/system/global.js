/**
*	GDX-RPG 引擎 游戏初始化脚本
**/

var RPG = com.rpsg.rpg.core.RPG;
var item = RPG.ctrl.item;

//初始地图
//map = "test/cgloop.tmx";

//map = "test/cafe.tmx";x=21;y=1;z=1;
//map = "test/inner.tmx";x=7;y=18;z=3;
map = "test/rkoroom.tmx";x=3;y=4;z=3;
//初始坐标
//x = 1;
//y = 1;
//z = 1;

//初始天气
weather = com.rpsg.rpg.utils.display.WeatherUtil.WEATHER_NO;

//初始携带
gold = 120;

//初始化道具
item.put(1);
item.put(2);
item.put(3);
item.put(4);
item.put(6);

item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);

item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);
item.put(1);

//HERO INIT
var heroController = com.rpsg.rpg.system.controller.HeroController;
var allHeroJsFileNames = new Array("1.grd","2.grd","3.grd","4.grd");
for(var i = 0; i < allHeroJsFileNames.length; i++)
{
	var filename = allHeroJsFileNames[i];
	var hero = heroController.newHero(filename);
	if(hero != null)
	{
		heros.add(hero);
	}
}

