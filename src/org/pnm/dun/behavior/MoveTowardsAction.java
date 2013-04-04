package org.pnm.dun.behavior;

import org.pnm.dun.unit.Unit;

public class MoveTowardsAction extends Action {

	public final Unit target;

	public MoveTowardsAction(Unit actor, Unit target) {
		super(actor);
		this.target = target;
	}

}
