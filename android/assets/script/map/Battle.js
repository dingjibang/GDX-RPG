eval(""+load('global.js'));


/** 
 *	###怪物移动脚本###
 * 
 * 
 * 
 * 	##使用方法##
 * 
 * 	在tiled指定一个NPC后，将其的AUTO_SCRIPT属性设置为此文件的文件名（Battle.js）
 * 
 * 
 * 
 *  ##参数##
 * 
 *	#_point : Vector2
 *	(point这个词为引擎关键字(执行器指针，参见script.java)，被占用的，所以使用_point)
 *	_point 是一个vector2(vector2 = {x:Number,y:Number}) 变量
 *	当_point是空值(undefined)的时候，NPC会随机移动
 *  当_point = {x:-1,y:-1} 时，NPC会朝向玩家缓慢移动（但注意的是，也会偶尔转身往别的地方走，而不是死死的面向玩家）
 *  否则，当_point的x与y的值为任意数字时，NPC会在这个(x,y)坐标附近移动
 *  #accelerate1Length : Number
 * 	加速模式1的距离，默认5
 *  #accelerate2Length : Number
 * 	加速模式2的距离，默认3
 *  #defaultWalkSpeed : Number
 * 	默认行走速度，指默认NPC行走速度，默认2
 *  #accelerate1WalkSpeed : Number
 * 	加速模式1行走速度，指默认NPC行走速度，默认2
 *  #accelerate2WalkSpeed : Number
 * 	加速模式2行走速度，指默认NPC行走速度，默认3
 *  #speed : Number
 * 	默认行走等待速度，指脚本执行完毕后，下一次重新执行的延时，越高行走越慢，默认6（0.1秒）
 *  #accelerate1Speed : Number
 * 	加速模式1行走等待速度，指脚本执行完毕后，下一次重新执行的延时，越高行走越慢，默认2（0.033秒）
 *  #accelerate2Speed : Number
 * 	加速模式2行走等待速度，指脚本执行完毕后，下一次重新执行的延时，越高行走越慢，默认2（0.033秒）
 *
 * 	#maxLength : Number
 * 	 行走最大可能长度，比如每次行走最大走1步，默认为1。
 * 
 *  #bounds : Vector2
 * 	根据x和y，制定一个矩形区域，NPC会在这个矩形内行走而不会越界，越界就会减速并掉头，他的优先级是最高的，并不会屌前面的参数	
 * 
 */
var CollideType = com.rpsg.rpg.object.rpg.CollideType;

/* Default Parameter Check Start*/
if(typeof(defaultWalkSpeed)=="undefined" || defaultWalkSpeed==null)
	var defaultWalkSpeed = 2;

if(typeof(accelerate1WalkSpeed)=="undefined" || accelerate1WalkSpeed==null)
	var accelerate1WalkSpeed = 4;
	
if(typeof(accelerate2WalkSpeed)=="undefined" || accelerate2WalkSpeed==null)
	var accelerate2WalkSpeed = 8;

if(typeof(speed)=="undefined" || speed==null)
	var speed = 6;

if(typeof(accelerate1Speed)=="undefined" || accelerate1Speed==null)
	var accelerate1Speed = 0;
	
if(typeof(accelerate2Speed)=="undefined" || accelerate2Speed==null)
	var accelerate2Speed = 0;

if(typeof(accelerate1Length)=="undefined" || accelerate1Length==null)
	var accelerate1Length = 5;

if(typeof(accelerate2Length)=="undefined" || accelerate2Length==null)
	var accelerate2Length = 3;

if(typeof(maxLength)=="undefined" || maxLength==null)
	var maxLength = 1;
	
//战斗
var toBattle = function(){
	battle({enemy:1});npc.remove();
	removeSelf();
	end();
};

/* Default Parameter Check End*/


var rpgObject=com.rpsg.rpg.object.rpg.RPGObject;
var isBattle = false;

for(var i = 0;i<Heros.size();i++){
	var _Hero = Heros.get(i);
	
	var HeroX,HeroY,npcDistance;
	var mode = 0;
	HeroX = _Hero.mapx;
	HeroY = _Hero.mapy;
	
	var dx = Math.abs(parseInt(npc.mapx-HeroX));
	var dy = Math.abs(parseInt(npc.mapy-HeroY));
	npcDistance = Math.max(dx,dy);
	
	if(((npcDistance ==1 && dx != dy) && // near checked
	(npc.currentImageNo==1 || npc.currentImageNo==4 || npc.currentImageNo==7 || npc.currentImageNo ==10) && // static Image NO. checked
	parseInt(npc.getCurrentFace()) == parseInt(npc.getFaceByPoint(HeroX,HeroY)) //Check if the face is to Hero 
		|| npcDistance == 0)){	
		toBattle();
		isBattle = true;
	}
}


if(!isBattle){
	if(npcDistance <= accelerate2Length)
	{
		mode = 2;
	}
	else if(npcDistance <= accelerate1Length)
	{
		mode = 1;
	}

	switch(mode)
	{
		case 1:
		speed = accelerate1Speed;
		npc.walkSpeed = accelerate1WalkSpeed;
		_point = {x:-1,y:-1};
		break;
		case 2:
		speed = accelerate2Speed;
		npc.walkSpeed=accelerate2WalkSpeed;
		_point = {x:-1,y:-1};
		if(npcDistance == 0)
		{
			_step = 0;
		}
		break;
		case 0:
		default: 
		npc.walkSpeed=defaultWalkSpeed;
		break;
	}
	eval("" + load("randomWalk.js"));
}




/*if(!npc.scripts.containsKey(CollideType.near))
{
	var battleScriptStr ="var rpgObject=com.rpsg.rpg.object.rpg.RPGObject;"+
	"var dx,dy;"+
	"dx=Math.abs(npc.mapx-Hero.mapx);dy=Math.abs(npc.mapy-Hero.mapy);"+
	"if((dx==1||dy==1) &&(dx!=dy))"+
	"if(npc.currentImageNo==1 || npc.currentImageNo==4 || npc.currentImageNo==7 || npc.currentImageNo ==10)"+
	"if(parseInt(npc.getCurrentFace()) == parseInt(npc.getFaceByPoint(Hero.mapx,Hero.mapy)))"+
	"{battle({enemy:1});npc.remove();}"+
	"removeSelf();end();";
	npc.scripts.put(CollideType.near,battleScriptStr);
	npc.scripts.put(CollideType.face,battleScriptStr);
}*/

