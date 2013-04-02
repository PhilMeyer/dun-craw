package org.pnm.dun.util;

import java.text.MessageFormat;

public class Location {
	public final double x, y;
	
	public Location(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public String toString(){
		String format = "({0,number,0.0},{1,number,0.0})";
		return MessageFormat.format(format, x, y);
	}
	
	public static void main(String[] args) {
		System.out.println(new Location(0.0, 14.598));
	}

	public int intX() {
		return Math.round((float)x);
	}

	public int intY() {
		return Math.round((float)y);
	}
	
}
