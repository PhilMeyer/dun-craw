package org.pnm.dun.resolve;

import java.text.MessageFormat;

import org.pnm.dun.model.MonsterFactory;
import org.pnm.dun.model.Unit;
import org.pnm.dun.model.HeroFactory;
import org.pnm.dun.model.Weapon;

public class Sim {

	public static Die d2 = new Die(2, 6);
	
	AtkResolveStrategy atkStrategy = new StandardAtkStrategy();
	DamResolveStrategy damStrategy = new StandardDamStrategy();

	public int resolve(AtkResolveStrategy atkStrat, DamResolveStrategy damStrat, Unit a, Unit d, Weapon weapon) {
		boolean hit = atkStrat.hit(a, d);
		int calcDam = 0;
		if (hit) {
			calcDam = damStrat.resolve(a, d, weapon.pow);
			if(calcDam > 0){
				log("Unit is killed.");
			}
			else{
				log("No damage done.");
			}
		}
		return calcDam;
	}
	
	public int resolve(Unit a, Unit d, Weapon weapon) {
		return resolve(atkStrategy, damStrategy, a, d, weapon);
	}

	public static void log(String format, Object... args) {
		System.out.println(MessageFormat.format(format, args));
	}
	
	public static void main(String[] args) {
		Sim sim = new Sim();
		Unit attacker = HeroFactory.siege();
		Unit defender = MonsterFactory.fennblade();
	}
	

}
