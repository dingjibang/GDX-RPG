/**
*	GDX-RPG 引擎 游戏初始化脚本
**/

var RPG = com.rpsg.rpg.core.RPG;
var item = RPG.ctrl.item;
var GameDate = com.rpsg.rpg.utils.game.GameDate;
var Weather = com.rpsg.rpg.object.base.Weather;

//初始地图
map = "test/cafe.tmx";
x=-10;
y=-10;
z=1;

map = "11/outside1.tmx";x=15;y=58;z=3;

//初始时间
date.time = GameDate.Time.DAY;

//初始天气
weather = Weather.no;

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
RPG.ctrl.hero.add(1);
RPG.ctrl.hero.add(6);
RPG.ctrl.hero.add(7);

var flandre = RPG.ctrl.hero.add(2);
currentHeros.remove(flandre);

var Association = com.rpsg.rpg.object.base.Association;
var Global = com.rpsg.rpg.object.base.Global;

Global.baseLinkSpellCard = Association.readSkill(1);
