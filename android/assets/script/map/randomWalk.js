if(typeof(speed)=="undefined" || speed==null)
	var speed = 22;
if(typeof(maxLength)=="undefined" || maxLength==null)
	var maxLength = 3;
var sleepMaxTime=speed,sleepTime=0,count=-1,maxCount=-1;
var maxWalkLength=maxLength;
var bo2;
if(typeof(bounds)!="undefined" && bounds!=null)
	bo2==new Vector();
var step = parseInt(Math.random()*maxWalkLength);
var face = 0;

if(typeof(point)=="undefined" || point==null){
	face = parseInt(Math.random()*4);
	if(face == 3)
		face=RPGObject.FACE_D;
	else if(face == 2)
		face=RPGObject.FACE_U;
	else if(face == 1)
		face=RPGObject.FACE_L;
	else if(face == 0) 
		face=RPGObject.FACE_R;
}else if(point.x!= -1 && point.y!= -1){
	face = NPC.getFaceByPoint(point.x, point.y);
}else{
	face = NPC.getFaceByPoint(Hero.mapx,Hero.mapy);
}

if(bo2!=null){
	if(face==RPGObject.FACE_D){
		if(bo2.y+step<bounds.y){
			bo2.y+=step;
		}else{
			if(bo2.y<bounds.y) step=bounds.y-step; else step=0;
			bo2.y=bounds.y;
		}
	}else if(face==RPGObject.FACE_U){
		if(bo2.y-step>-bounds.y){
			bo2.y-=step;
		}else{
			if(bo2.y>-bounds.y) step=Math.abs( Math.abs(bounds.y)-step); else step=0;
			bo2.y=-bounds.y;
		}
	}else if(face==RPGObject.FACE_R){
		if(bo2.x+step<bounds.x){
			bo2.x+=step;
		}else{
			if(bo2.x<bounds.x) step=bounds.x-step; else step=0;
			bo2.x=bounds.x;
		}
	}else if(face==RPGObject.FACE_L){
		if(bo2.x-step>-bounds.x){
			bo2.x-=step;
		}else{
			if(bo2.x>-bounds.x) step=Math.abs(Math.abs(bounds.x)-step); else step=0;
			bo2.x=-bounds.x;
		}
	}
}

faceTo(step>0?face:RPGObject.getReverseFace(face));
move(step>0?step:0);
wait(speed);
end();
