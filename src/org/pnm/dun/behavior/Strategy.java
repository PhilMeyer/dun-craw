package org.pnm.dun.behavior;

import org.pnm.dun.Environment;
import org.pnm.dun.model.Unit;

public interface Strategy {

	Action getAction(Environment e, Unit u);

}
