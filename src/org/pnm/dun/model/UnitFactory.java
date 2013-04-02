package org.pnm.dun.model;


public class UnitFactory {


	public static Unit grunt(){
		Unit unit = new Unit("Grunt", "test.png", 7, 5, 5, 11, 13, 4);
		unit.addWep(new Weapon("Club",4,3));
		return unit;
	}
	
	public static Unit will(){
		Unit unit = new Unit("Will", "test.png", 12, 5, 5, 10, 18, 7);
		unit.addWep(new Weapon("Longsword",4,5));
		unit.addActive(ActiveType.HEAL);
		unit.addPassive(PassiveType.AURA);
		return unit;
	}
	
	public static Unit malg(){
		Unit unit = new Unit("Malgor", "test.png", 15, 5, 7, 13, 13, 4);
		unit.setDamResist(1);
		unit.addWep(new Weapon("Greataxe",5,3));
		unit.addActive(ActiveType.CLEAVE);
		unit.addPassive(PassiveType.TOUGH);
		return unit;
	}
	
	public static Unit cass(){
		Unit unit = new Unit("Cassie", "test.png", 8, 4, 1, 15, 8, 11);
		unit.addActive(new Active(ActiveType.CHARM,1,10));
		unit.addPassive(new Passive(PassiveType.DAZING_TOUCH));
		unit.addWep(new Weapon("Touch",0,0));
		return unit;
	}
	
	public static Unit fel(){
		Unit unit = new Unit("Felicity", "test.png", 10, 6, 4, 13, 13, 6);
		unit.addWep(new Weapon("Longbow",3,5,10));
		unit.addWep(new Weapon("Shortsword",2,6));
		unit.addActive(ActiveType.CALL_SHOT);
		unit.addPassive(PassiveType.EVADE);
		return unit;
	}
	
	public static Unit cat(){
		Unit unit = new Unit("Cat", "test.png", 10, 7, 5, 14, 10, 5);
		unit.addWep(new Weapon("Claw",4,4));
		unit.addWep(new Weapon("Claw",4,4));
		unit.addActive(ActiveType.TWIN_STRIKE);
		unit.addPassive(PassiveType.DODGE);
		return unit;
	}
	
}
