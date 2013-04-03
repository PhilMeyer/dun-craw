package org.pnm.dun.behavior;

import java.util.List;

import org.pnm.dun.Environment;
import org.pnm.dun.model.Unit;

public class MeleeBasic implements Strategy{

	@Override
	public Action getAction(Environment e, Unit u){
		
		List<Unit> adjacentEnemies = e.getAdjacentEnemies(u);
		//System.out.println("Adjacent enemies of "+u+" are "+adjacentEnemies);
		if(!adjacentEnemies.isEmpty()){
			return new AttackAction(u,adjacentEnemies.get(0));
		}
		List<Unit> nearestEnemies = e.getNearestEnemies(u);
		if(!nearestEnemies.isEmpty()){
			return new MoveTowardsAction(u,nearestEnemies.get(0));
		}
		return null;
	}
	
	
}
