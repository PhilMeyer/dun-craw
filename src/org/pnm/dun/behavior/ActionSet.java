package org.pnm.dun.behavior;

public class ActionSet {

	public boolean usedMove;
	public boolean usedStandard;
	
	public void useMove() {
		if(usedMove){
			usedStandard = true;
		}
		else{
			usedMove = true;
		}
	}

	public void useStandard() {
		usedStandard = true;
	}
	
}
