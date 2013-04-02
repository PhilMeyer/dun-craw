package org.pnm.dun.util;

import java.text.MessageFormat;

public class Location {
	public final int x, y;
	
	public Location(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Location(double x, double y) {
		this((int)Math.round(x), (int)Math.round(y));
	}

	public String toString(){
		String format = "({0},{1})";
		return MessageFormat.format(format, x, y);
	}
	
	public static void main(String[] args) {
		System.out.println(new Location(0, 14));
	}
	
}
