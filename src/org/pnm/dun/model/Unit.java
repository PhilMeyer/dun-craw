package org.pnm.dun.model;

import java.util.ArrayList;
import java.util.List;

public class Unit{

	public final int str, def, arm, spd, cmd;
	int mhp = 1;
	int hp = 1;
	int dr;
	public int base = 1;
	String name;
	
	List<Ability> abilities = new ArrayList<>();
	List<Active> actives = new ArrayList<>();
	List<Passive> passives = new ArrayList<>();
	List<Weapon> weapons = new ArrayList<>();
	public final String imagePath;
	

	public Unit(String name, String imagePath, int mhp, int spd, int str, int def, int arm, int cmd){
		this.name = name;
		this.spd = spd;
		this.str = str;
		this.def = def;
		this.arm = arm;
		this.cmd = cmd;
		this.mhp = mhp;
		this.hp = mhp;
		this.imagePath = imagePath;
	}
	
	public Weapon getDefaultWeapon(){
		Weapon weapon = weapons.get(0);
		return weapon;
	}
	
	public int getDefaultWepPow(){
		Weapon weapon = weapons.get(0);
		int pow = 0;
		if(weapon != null){
			pow = weapon.pow;
		}
		return pow;
	}

	public void addWep(Weapon weapon) {
		weapons.add(weapon);
	}
	
	public String toString(){
		return name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDamResist(int dr) {
		this.dr = dr;
	}

	public void addPassive(PassiveType... toAdd) {
		for(PassiveType a : toAdd){
			this.passives.add(new Passive(a,1));
		}
	}

	public void addPassive(Passive... toAdd) {
		for(Passive a : toAdd){
			this.passives.add(a);
		}
	}

	public void addActive(ActiveType... toAdd) {
		for(ActiveType a : toAdd){
			this.actives.add(new Active(a,1));
		}
	}

	public void addActive(Active... toAdd) {
		for(Active a : toAdd){
			this.actives.add(a);
		}
	}
}
