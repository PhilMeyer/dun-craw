package org.pnm.dun.behavior;

import java.util.List;

import org.pnm.dun.env.Environment;
import org.pnm.dun.unit.Unit;

public class RangedBasic implements Strategy{

	@Override
	public Action getAction(Environment e, Unit u, ActionSet set){
		List<Unit> adjacentEnemies = e.getAdjacentEnemies(u);
		//System.out.println("Adjacent enemies of "+u+" are "+adjacentEnemies);
		boolean hasAdjacentEnemy = !adjacentEnemies.isEmpty();
		if(hasAdjacentEnemy && set.hasMove()){
			return new ShiftAction(u,e.getSafeSquare(u));
		}
		
		if(!hasAdjacentEnemy && set.hasStandard()){
			List<Unit> nearestEnemies = e.getNearestEnemies(u);
			if(!nearestEnemies.isEmpty()){
				Unit bestTarget = null;
				int bestMod = Integer.MAX_VALUE;
				for(Unit enemy : nearestEnemies){
					int tMod = e.getRangedPenalties(u, enemy);
					if(bestTarget == null || tMod > bestMod){
						bestMod = tMod;
						bestTarget = enemy;
					}
				}
				return new AttackAction(u,bestTarget);
			}
		}
		return null;
	}
	
}