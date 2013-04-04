package org.pnm.dun.model;


public class MonsterFactory {


	public static Unit fennblade(){
		Unit unit = new Monster("Grunt", "T_U_FennOfficer", 7, 5, 11, 13, 4);
		unit.addWep(new Weapon("Club",4,3));
		return unit;
	}
	
	public static Unit grunt(){
		Unit unit = new Monster("Grunt", "T_U_KrielWarrior", 7, 4, 12, 13, 4);
		unit.addWep(new Weapon("Club",9,4));
		return unit;
	}
	
	public static Unit mulg(){
		Unit unit = new Monster("Mulg", "T_B_Mulg", 10, 7, 14, 10, 5);
		unit.base=3;
		unit.addWep(new Weapon("D",3,5));
		return unit;
	}
	
	
	
}
