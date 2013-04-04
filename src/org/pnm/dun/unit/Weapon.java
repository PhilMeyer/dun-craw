package org.pnm.dun.unit;

public class Weapon {
	public final int pow;
	public final int acc;
	public final int rng;
	String name;
	public int dice = 2;

	public Weapon(String name, int pow, int acc) {
		this(name, pow,acc,0);
	}

	public Weapon(String name,int pow, int acc, int rng) {
		this.rng = rng;
		this.pow = pow;
		this.acc = acc;
		this.name = name;
	}
}
