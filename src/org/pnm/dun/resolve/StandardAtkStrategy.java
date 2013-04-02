package org.pnm.dun.resolve;

import org.pnm.dun.model.Unit;
import org.pnm.dun.model.Weapon;


public class StandardAtkStrategy implements AtkResolveStrategy{

	
	@Override
	public boolean hit(Unit a, Unit d) {
		int atkRoll = Sim.d2.roll();
		Weapon wep = a.getDefaultWeapon();
		int totalAtk = wep.acc + atkRoll;
		boolean hit = d.def <= totalAtk;
		String hitMissString = (hit) ? "Hit!" : "Miss.";
		Sim.log("ATK[{1}] vs DEF[{4}]:  {0}(2d6) + {1} = {2}. {3}", atkRoll, wep.acc, totalAtk, hitMissString, d.def);
		return hit;
	}

}
