package org.pnm.dun.engine;

import java.util.List;

import org.pnm.dun.env.Environment;
import org.pnm.dun.unit.ActiveType;
import org.pnm.dun.unit.PassiveType;
import org.pnm.dun.unit.Unit;
import org.pnm.dun.unit.Weapon;
import org.pnm.dun.util.CollectionUtil;

public class RulesEngine {

	private Environment e;
	private AttackEngine attackEngine;
	public boolean testMode;

	public RulesEngine(Environment e) {
		this.e = e;
		this.attackEngine = new AttackEngine();
	}

	public int resolve(Unit a, Unit t, ActiveType type) {
		AttackEngine.log("{0} using {1} on {2}.", a, type, t);
		switch (type) {
		case BASIC:
			return basicAttack(a, t);
		case POWER:
			return power(a, t);
		case HEAL:
			return heal(a, t);
		case CLEAVE:
			return cleave(a, t);
		case TWIN_STRIKE:
			int dam = 0;
			dam += resolve(a, -1, 0, t);
			dam += resolve(a, -1, 0, t);
			return dam;
		}
		return 0;
	}

	private int power(Unit a, Unit t) {
		Weapon w = a.getDefaultWeapon();
		Weapon pWep = new Weapon("pw", w.pow, w.acc);
		pWep.dice = 3;
		return resolve(a, -1, 0, pWep, t);
	}

	private int cleave(Unit a, Unit d) {
		List<Unit> adjEn = e.getAdjacentEnemies(a);
		List<Unit> adjAll = e.getAdjacentAllies(d);
		List<Unit> intersect = CollectionUtil.intersect(adjEn, adjAll);
		intersect.add(d);
		if (!intersect.isEmpty()) {
			Unit[] targets = new Unit[intersect.size()];
			for (int i = 0; i < targets.length; i++) {
				targets[i] = intersect.get(i);
			}
			return resolve(a, -1, -1, targets);
		}
		return 0;
	}

	private int heal(Unit a, Unit d) {
		int pow = Die.d1.roll();
		d.hp += pow;
		return pow;
	}

	public int basicAttack(Unit a, Unit d) {
		return resolve(a, 0, 0, d);
	}

	public int resolve(Unit a, int atkMod, int damMod, Unit... targs) {
		return resolve(a,atkMod, damMod, a.getDefaultWeapon(), targs);
	}

	public int resolve(Unit a, int atkMod, int damMod, Weapon w, Unit... targs) {
		int atkRoll = Die.d2.roll();
		int damDone = 0;
		for (Unit d : targs) {
			boolean hasDefensiveAura = hasDefensiveAura(d);
			damMod = damMod + (hasDefensiveAura ? -1 : 0);
			damDone += attackEngine.resolve(atkRoll, a, w, atkMod, damMod, d);
			if (d.hp <= 0 && !testMode) {
				e.remove(d);
			}
		}
		return damDone;
	}

	private boolean hasDefensiveAura(Unit d) {
		for (Unit u : e.getAdjacent(d)) {
			if (u.side == d.side && d.hasPassive(PassiveType.AURA)) {
				return true;
			}
		}
		return false;
	}

}
