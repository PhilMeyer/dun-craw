package org.pnm.dun.engine;

import org.pnm.dun.unit.HeroFactory;
import org.pnm.dun.unit.MonsterFactory;
import org.pnm.dun.unit.Unit;
import org.pnm.dun.unit.Weapon;

public class AttackEngine {

	public int resolve(int atkRoll, Unit a, Weapon weapon, int atkMods, int damMods, Unit d) {
		boolean hit = hit(a, d, weapon, atkRoll, atkMods);
		int calcDam = 0;
		if (hit) {
			calcDam = calculateDamage(a, d, weapon, damMods);
			if (calcDam > 0) {
				d.hp -= calcDam;
				RulesEngine.log("{0} takes {1} damage.  HP={2}/{3}.", d, calcDam, d.hp, d.mhp);
				if (d.hp <= 0) {
					RulesEngine.log("{0} is killed.", d);
				}
			}
			else {
				RulesEngine.log("No damage done.");
			}
		}
		return calcDam;
	}

	public int calculateDamage(Unit a, Unit d, Weapon w, int damMods) {
		int damRoll = Die.d(w.dice).roll();
		int calcDam = w.pow + damRoll - d.arm + damMods;
		if (calcDam < 0) {
			calcDam = 0;
		}
		RulesEngine.log("P+S[{0}] vs ARM[{1}]:  {2}({5}d6) + {0} + {4} - {1} = {3}", w.pow, d.arm, damRoll, calcDam,
				damMods, w.dice);
		if(damRoll == 12 && calcDam < 1){
			calcDam = 1;
		}
		return calcDam;
	}

	public boolean hit(Unit a, Unit d, Weapon wep, int roll, int mods) {
		if(roll == 12){
			return true;
		}
		int modAtk = wep.acc + mods;
		int totalAtk = modAtk + roll;
		boolean hit = d.def <= totalAtk;
		String hitMissString = (hit) ? "Hit!" : "Miss.";
		RulesEngine.log("ATK[{0}] vs DEF[{1}]:  {2}(2d6) + {3} + {4} = {5}. {6}", modAtk, d.def, roll, wep.acc, mods,
				totalAtk, hitMissString);
		return hit;
	}

	public static void main(String[] args) {
		AttackEngine sim = new AttackEngine();
		Unit attacker = HeroFactory.siege();
		Unit defender = MonsterFactory.fennblade();
	}

}
