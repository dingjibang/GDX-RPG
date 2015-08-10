eval(""+load('global.js'));
npc.walkSpeed=1;
if(typeof(speed)=="undefined" || speed==null)
	var speed = 60;
if(typeof(maxLength)=="undefined" || maxLength==null)
	var maxLength = 3;
var sleepMaxTime=speed,sleepTime=0,count=-1,maxCount=-1;
var maxWalkLength=maxLength;
var bo2;
if(typeof(bounds)!="undefined" && bounds!=null)
	bo2==new Vector();
var _step = parseInt(Math.random()*maxWalkLength);
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
