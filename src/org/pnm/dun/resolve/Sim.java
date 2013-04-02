package org.pnm.dun.resolve;

import java.text.MessageFormat;

import org.pnm.dun.model.Unit;
import org.pnm.dun.model.UnitFactory;

public class Sim {

	public static Die d2 = new Die(2, 6);
	
	AtkResolveStrategy atkStrategy = new StandardAtkStrategy();
	DamResolveStrategy damStrategy = new StandardDamStrategy();


	public int resolve(AtkResolveStrategy atkStrat, DamResolveStrategy damStrat, Unit a, Unit d, int pow) {
		boolean hit = atkStrat.hit(a, d);
		int calcDam = 0;
		if (hit) {
			calcDam = damStrat.resolve(a, d, pow);
			if(calcDam > 0){
				log("Unit is killed.");
			}
			else{
				log("No damage done.");
			}
		}
		return calcDam;
	}
	
	public int resolve(Unit a, Unit d, int pow) {
		return resolve(atkStrategy, damStrategy, a, d, pow);
	}

	public static void log(String format, Object... args) {
		System.out.println(MessageFormat.format(format, args));
	}
	
	public static void main(String[] args) {
		Sim sim = new Sim();
		Unit attacker = UnitFactory.malg();
		Unit defender = UnitFactory.grunt();
		sim.combo(attacker, defender, attacker.getDefaultWepPow());
		sim.combined(attacker, defender, attacker.getDefaultWepPow());
		sim.pen(attacker, defender);
	}
	
	public void combo(Unit attacker, Unit defender, int pow){
		resolve(attacker, defender, pow);
		resolve(attacker, defender, pow);
	}
	
	public void combined(Unit attacker, Unit defender, int pow){
		resolve(attacker, defender, pow+pow);
	}
	
	public void pen(Unit attacker, Unit defender){
		DamResolveStrategy penStrategy = new PenDamStrategy();
		resolve(atkStrategy, penStrategy, attacker, defender, -1);
	}

}
