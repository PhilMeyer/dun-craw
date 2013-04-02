package org.pnm.dun.resolve;

import org.pnm.dun.model.Unit;

public interface DamResolveStrategy {

	int resolve(Unit a, Unit b, int pow);
	
}
