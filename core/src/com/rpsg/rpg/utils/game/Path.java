package com.rpsg.rpg.utils.game;

public class Path {	
	public static int X,Y;
	public static int goalX,goalY;
	
	
	public static void click(int x,int y){
		goalX = (int) Math.ceil((float)x/48f);
		goalY = (int) Math.ceil((float)y/48f);
		System.out.println("["+goalX+","+goalY+"]");
		
	}

}
