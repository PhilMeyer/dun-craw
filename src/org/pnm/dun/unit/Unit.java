package org.pnm.dun.unit;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class Unit{

	public final int def, arm, spd, res;
	public final int mhp;
	public int hp = 1;
	public int base = 1;
	String name;
	
	List<Ability> abilities = new ArrayList<>();
	List<Active> actives = new ArrayList<>();
	List<Passive> passives = new ArrayList<>();
	List<Weapon> weapons = new ArrayList<>();
	public final String imagePath;
	
	public static enum Side{PLAYER, MONSTER}
	
	public Side side = Side.PLAYER;
	
	public Unit(String name, String imagePath, int mhp, int spd, int def, int arm, int res){
		this.name = name;
		this.spd = spd;
		this.def = def;
		this.arm = arm;
		this.res = res;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public String toString(){
		
		return name;
	}
	
	public String toVerboseString(){
		String format = "{0}  [{1}/{8}] SPD={2} POW={3} ACC={4} DEF={5} ARM={6} RES={7}";
		Weapon w = getDefaultWeapon();
		String stats = MessageFormat.format(format, name, mhp, spd, w.pow, w.acc, def, arm, res, hp);
		StringBuilder sb = new StringBuilder(stats);
		sb.append("\n\t");
		for(Active active : actives){
			sb.append(active);
		}
		sb.append("\n\t");
		for(Passive passive : passives){
			sb.append(passive);
		}
		return sb.toString();
	}

	public boolean hasPassive(PassiveType type) {
		for(Passive p : passives){
			if(p.type == type){
				return true;
			}
		}
		return false;
	}

	public Side getSide() {
		return side;
	}

	public void setSide(Side side) {
		this.side = side;
	}

	public Side getEnemySide() {
		return (side == Side.MONSTER) ? Side.PLAYER : Side.MONSTER;
	}
}
