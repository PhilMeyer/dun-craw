package org.pnm.dun.behavior;

import org.pnm.dun.env.Environment;
import org.pnm.dun.unit.Unit;

public interface Strategy {

	Action getAction(Environment e, Unit u, ActionSet actionSet);

}
