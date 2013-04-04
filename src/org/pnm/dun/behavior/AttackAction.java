package org.pnm.dun.behavior;

import org.pnm.dun.unit.ActiveType;
import org.pnm.dun.unit.Unit;

public class AttackAction extends Action {

	public final Unit target;
	
	public final ActiveType type;

	public AttackAction(Unit actor, Unit target, ActiveType type) {
		super(actor);
		this.target = target;
		this.type = type;
	}
	
	public AttackAction(Unit actor, Unit target) {
		this(actor,target,ActiveType.BASIC);
	}

}
