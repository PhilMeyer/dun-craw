package org.pnm.dun.behavior;

import org.pnm.dun.model.Unit;

public class Action {

	public final Unit actor;
	public final Unit target;
	
	public Action(Unit actor, Unit target) {
		this.actor = actor;
		this.target = target;
	}

}
