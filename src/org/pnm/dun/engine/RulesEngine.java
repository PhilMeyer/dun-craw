package org.pnm.dun.engine;

import java.text.MessageFormat;
import java.util.List;

import org.pnm.dun.env.Environment;
import org.pnm.dun.unit.ActiveType;
import org.pnm.dun.unit.PassiveType;
import org.pnm.dun.unit.Unit;
import org.pnm.dun.unit.Weapon;
import org.pnm.dun.util.CollectionUtil;
import org.pnm.dun.util.Location;

public class RulesEngine {

	private Environment e;
	private AttackEngine attackEngine;
	public static boolean testMode;

	public RulesEngine(Environment e) {
		this.e = e;
		this.attackEngine = new AttackEngine();
	}

	public int resolve(Unit a, Unit t, ActiveType type) {
		log("{0} using {1} on {2}.", a, type, t);
		switch (type) {
		case OPPORTUNITY_ATTACK:
			return oa(a, t);
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
			dam += resolve(-1, 0, a, t);
			dam += resolve(-1, 0, a, t);
			return dam;
		}
		return 0;
	}

	public int resolve(Unit a, Location loc, ActiveType type) {
		log("{0} using {1} at {2}.", a, type, loc);
		switch (type) {
		case FIREBALL:
			return resolveAreaAttack(a, loc);
		}
		return 0;
	}

	private int resolveAreaAttack(Unit a, Location l) {
		int atkRoll = Die._2d6.roll();
		List<Location> adjacent = e.getAdjacent(l);
		for (Location loc : adjacent) {
			Unit t = e.getUnitAt(loc);
			if (t != null) {
				Weapon wep = a.getDefaultWeapon();
				boolean hit = attackEngine.hit(a, t, wep, atkRoll, 0);
				if (hit) {
					int dam = wep.pow;
					if (t.hasPassive(PassiveType.RESISTANCE)) {
						dam -= 2;
					}
					t.hp -= dam;
				}
			}
		}
		return 0;
	}

	private int oa(Unit a, Unit t) {
		int aMod = t.hasPassive(PassiveType.MOBILITY) ? 2 : 0;
		return resolve(aMod, 0, a, t);
	}

	private int power(Unit a, Unit t) {
		Weapon w = a.getDefaultWeapon();
		Weapon pWep = new Weapon("pw", w.pow, w.acc);
		pWep.dice = 3;
		return resolve(-1, 0, pWep, a, t);
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
			return resolve(-1, -1, a, targets);
		}
		return 0;
	}

	private int heal(Unit a, Unit d) {
		int pow = Die._1d6.roll();
		d.hp += pow;
		return pow;
	}

	public int basicAttack(Unit a, Unit d) {
		return resolve(0, 0, a, d);
	}

	public int resolve(int atkMod, int damMod, Unit a, Unit... targs) {
		return resolve(atkMod, damMod, a.getDefaultWeapon(), a, targs);
	}

	public int resolve(int atkMod, int damMod, Weapon w, Unit a, Unit... targs) {
		int atkRoll = Die._2d6.roll();
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

	public static void log(String format, Object... args) {
		if (!testMode)
			System.out.println(MessageFormat.format(format, args));
	}

}
