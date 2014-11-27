package com.rpsg.rpg.object;


public class Collide {
	public boolean left;
	public boolean right;
	public boolean top;
	public boolean bottom;
	
	
	public void testCollide(int x,int y,int[][] t){
			left=!(x==0 || t[y][x-1]!=0);
			right=!(x==t[0].length-1 || t[y][x+1]!=0);
			top=!(y==0 || t[y-1][x]!=0);
			bottom=!(y==t.length-1 || t[y+1][x]!=0);
	}


	@Override
	public String toString() {
		return "Collide [bottom=" + bottom + ", left=" + left + ", right=" + right + ", top=" + top + "]";
	}
}
