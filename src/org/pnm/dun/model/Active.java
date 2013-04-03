package org.pnm.dun.model;

public class Active {

	ActiveType type;
	final int value, rng;
	
	public Active(ActiveType type){
		this(type,0,0);
	}

	public Active(ActiveType type, int value){
		this(type,value,0);
	}
	public Active(ActiveType type, int value, int rng){
		this.type = type;
		this.value = value;
		this.rng = rng;
	}
	
	public String toString(){
		return type.toString();
	}
	
}
