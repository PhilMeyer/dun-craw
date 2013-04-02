package org.pnm.dun.resolve;

import org.pnm.dun.model.Unit;

public class PenDamStrategy implements DamResolveStrategy {

	@Override
	public int resolve(Unit a, Unit b, int pow) {
		return 1;
	}

}
