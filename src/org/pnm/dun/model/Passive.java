package org.pnm.dun.model;

public class Passive {

	PassiveType type;
	final int value, rng;
	
	public Passive(PassiveType type){
		this(type,0,0);
	}

	public Passive(PassiveType type, int value){
		this(type,value,0);
	}
	public Passive(PassiveType type, int value, int rng){
		this.type = type;
		this.value = value;
		this.rng = rng;
	}

	
	public String toString(){
		return type.toString();
	}
}
