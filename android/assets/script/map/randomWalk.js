eval(""+load('global.js'));


/** 
 *	###随机移动脚本###
 * 
 * 
 * 
 * 	##使用方法##
 * 
 * 	在tiled指定一个NPC后，将其的AUTO_SCRIPT属性设置为此文件的文件名（randomWalk.js）
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
 * 
 *  #speed : Number
 * 	行走等待速度，指脚本执行完毕后，下一次重新执行的延时，越高行走越慢，默认60（1秒）
 *
 * 	#maxLength : Number
 * 	 行走最大可能长度，比如每次行走最大走3步，默认为3。
 *
 * 	#minLength : Number
 * 	 行走最小可能长度，比如每次行走最小走1步，默认为1。
 * 
 *  #bounds : Vector2
 * 	根据x和y，制定一个矩形区域，NPC会在这个矩形内行走而不会越界，越界就会减速并掉头，他的优先级是最高的，并不会屌前面的参数	
 * 
 */
 
if(typeof(defaultWalkSpeed)=="undefined" || defaultWalkSpeed==null)
	npc.walkSpeed=1;

if(typeof(speed)=="undefined" || speed==null)
	var speed = 60;
	
if(typeof(maxLength)=="undefined" || maxLength==null)
	var maxLength = 3;

if(typeof(minWalkLength)=="undefined" || minWalkLength==null)
	minWalkLength=1; 

var sleepMaxTime=speed,sleepTime=0,count=-1,maxCount=-1;
var maxWalkLength=maxLength;
var bo2;

if(typeof(bounds)!="undefined" && bounds!=null)
	bo2==new Vector();

if(typeof(_step)=="undefined" || _step==null)
	var _step = parseInt(Math.random()*(maxWalkLength-minWalkLength)+minWalkLength);

var face = 0;

if(typeof(_point)=="undefined" || _point==null){
	face = parseInt(Math.random()*4);
	if(face == 3)
		face=RPGObject.FACE_D;
	else if(face == 2)
		face=RPGObject.FACE_U;
	else if(face == 1)
		face=RPGObject.FACE_L;
	else if(face == 0) 
		face=RPGObject.FACE_R;
}else if(_point.x!= -1 && _point.y!= -1){
	face = npc.getFaceByPoint(_point.x, _point.y);
}else{
	face = npc.getFaceByPoint(Hero.mapx,Hero.mapy);
}

if(bo2!=null){
	if(face==RPGObject.FACE_D){
		if(bo2.y+_step<bounds.y){
			bo2.y+=_step;
		}else{
			if(bo2.y<bounds.y) _step=bounds.y-_step; else _step=0;
			bo2.y=bounds.y;
		}
	}else if(face==RPGObject.FACE_U){
		if(bo2.y-_step>-bounds.y){
			bo2.y-=_step;
		}else{
			if(bo2.y>-bounds.y) _step=Math.abs( Math.abs(bounds.y)-_step); else _step=0;
			bo2.y=-bounds.y;
		}
	}else if(face==RPGObject.FACE_R){
		if(bo2.x+_step<bounds.x){
			bo2.x+=_step;
		}else{
			if(bo2.x<bounds.x) _step=bounds.x-_step; else _step=0;
			bo2.x=bounds.x;
		}
	}else if(face==RPGObject.FACE_L){
		if(bo2.x-_step>-bounds.x){
			bo2.x-=_step;
		}else{
			if(bo2.x>-bounds.x) _step=Math.abs(Math.abs(bounds.x)-_step); else _step=0;
			bo2.x=-bounds.x;
		}
	}
}

faceTo(_step>0?face:RPGObject.getReverseFace(face));
move(_step>0?_step:0);
pause(speed);
end();
