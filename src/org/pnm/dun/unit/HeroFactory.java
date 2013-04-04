package org.pnm.dun.unit;


public class HeroFactory {

	
	/*
	 * Support/Healer, decent in melee.
	 */
	public static Unit will(){
		Unit unit = new Unit("Will", "P_U_KnightsErrant", 12, 5, 10, 17, 7); 
		// P_U_KnightsErrant
		// C_U_PrecursorKnightOfficer
		unit.addWep(new Weapon("Longsword",9,5));
		unit.addActive(ActiveType.HEAL);
		unit.addPassive(PassiveType.AURA);
		return unit;
	}
	
	/*
	 * Slow but steady.  	
	 * Send after high armor.
	 */
	public static Unit siege(){
		Unit unit = new Unit("Siege", "C_W_Siege", 15, 5, 12, 14, 4);
		unit.setDamResist(1);
		unit.addWep(new Weapon("Warhammer",10,5));
		unit.addActive(ActiveType.SLAM);
		unit.addActive(ActiveType.POWER);
		unit.addPassive(PassiveType.ALERT);
		return unit;
	}
	
	/*
	 * Tough and deadly.  	
	 * Good at clearing minions.
	 */
	public static Unit fang(){
		Unit unit = new Unit("Fang", "O_S_WhiteMane", 15, 5, 12, 14, 4);
		unit.setDamResist(1);
		unit.base=2;
		unit.addWep(new Weapon("Greataxe",11,4));
		unit.addActive(ActiveType.CLEAVE);
		unit.addPassive(PassiveType.TOUGH);
		return unit;
	}
	
	public static Unit kaya(){
		Unit unit = new Unit("Kaya", "O_W_Kaya", 8, 4, 15, 8, 11);
		unit.addActive(new Active(ActiveType.CHARM,1,10));
		unit.addPassive(new Passive(PassiveType.PARALYZING_STRIKE));
		unit.addWep(new Weapon("Touch",0,0));
		return unit;
	}
	
	public static Unit zaal(){
		Unit unit = new Unit("Zaal", "S_W_Zaal", 8, 4, 15, 8, 11);
		unit.addActive(new Active(ActiveType.FIREBALL,2,10));
		unit.addPassive(new Passive(PassiveType.CRIPPLING_TOUCH));
		unit.addWep(new Weapon("Staff",4,4));
		return unit;
	}
	
	
	public static Unit dhov(){
		Unit unit = new Unit("Dhovanu", "L_U_Striders", 10, 6, 13, 13, 6);
		unit.addWep(new Weapon("Longbow",3,8,10));
		unit.addActive(ActiveType.CALL_SHOT);
		unit.addPassive(PassiveType.EVADE);
		return unit;
	}
	
	public static Unit hawk(){
		Unit unit = new Unit("Hawk", "M_S_Hawk", 10, 7, 15, 10, 5);
		unit.addWep(new Weapon("Shortsword",8,7));
		unit.addWep(new Weapon("Shortsword",8,7));
		unit.addActive(ActiveType.TWIN_STRIKE);
		unit.addPassive(PassiveType.MOBILITY);
		return unit;
	}
	
}
