package org.pnm.dun.behavior;

import org.pnm.dun.unit.Unit;
import org.pnm.dun.util.Location;

public class ShiftAction extends Action {

	Location location;
	
	public ShiftAction(Unit actor, Location location) {
		super(actor);
		this.location = location;
	}

}
