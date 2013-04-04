package org.pnm.dun.engine;

import java.util.Random;

public class Die {

	public static final Die d3 = new Die(3, 6);
	public static final Die d2 = new Die(2, 6);
	public static final Die d1 = new Die(1, 6);
	
	int numSides;
	int numDie;
	
	public Die(){
		this(6);
	}
	
	public Die(int numSides){
		this(numSides,1);
	}

	public Die(int numDie, int numSides){
		this.numDie = numDie;
		this.numSides = numSides;
	}
	
	public int roll(){
		int total = 0;
		Random r = new Random();
		for(int i = 0; i < numDie; i++){
			total += 1 + r.nextInt(numSides);
		}
		return total;
	}
	
	
	public static void main(String[] args){
		Die d = new Die(2,6);
		boolean rolledTwo = false;
		boolean rolledTwelve = false;
		int total = 0;
		int runs = 1000000;
		for(int i = 0; i < runs; i++){
			int roll = d.roll();
			total += roll;
			if(roll == 2){
				rolledTwo = true;
			}
			if(roll == 12){
				rolledTwelve = true;
			}
			//System.out.println(roll);
			if(roll > 12 || roll < 2){
				throw new IllegalStateException("Invalid roll: "+roll);
			}
		}
		System.out.println((double)total/runs);
		System.out.println(rolledTwo);
		System.out.println(rolledTwelve);
	}

	public static Die d(int n) {
		switch(n){
			case 1:	return d1;
			case 2:	return d2;
			case 3:	return d3;
		}
		return new Die(n);
	}
	
}
