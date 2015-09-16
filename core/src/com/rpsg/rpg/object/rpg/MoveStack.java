package com.rpsg.rpg.object.rpg;

import java.io.Serializable;

public class MoveStack implements Serializable{
	private static final long serialVersionUID = 1L;
	public int face;
	public int step;
	
	public MoveStack(int face,int step){
		this.face=face;
		this.step=step;
	}
	
	/**
	 * 返回[x1,y1]<b>相对于</b>[x2,y2]的面向
	 */
	public static int calcFace(int x1,int y1,int x2,int y2){
		if(x1==x2)
			if(y1>y2)
				return RPGObject.FACE_D;
			else
				return RPGObject.FACE_U;
		else
			if(x1>x2)
				return RPGObject.FACE_L;
			else
				return RPGObject.FACE_R;
	}
}
