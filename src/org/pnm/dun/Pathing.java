package org.pnm.dun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pnm.dun.model.Unit;
import org.pnm.dun.util.GeometryUtil;
import org.pnm.dun.util.Location;

public class Pathing {

	Environment e;
	List<Location> shortest;
	

	public Pathing(Environment e) {
		this.e = e;
	}

	public List<Location> getShortestPath(Unit actor, Unit target) {
		Location locA = e.locations.get(actor);
		Location locT = e.locations.get(target);
		List<Location> firstStep = new ArrayList<>();
		firstStep.add(locA);
		getNextStep(firstStep, locT);
		return shortest;
	}

	private void getNextStep(List<Location> path, Location target) {
		Location current = path.get(path.size() - 1);
		//System.out.println("Getting next step in path: "+path);
		double distance = GeometryUtil.distance(current, target);
		boolean hopeless = shortest != null && path.size() >= shortest.size();
		if(distance > 20 || hopeless){
			return; // give up
		}
		if (distance < 2) {
			if(shortest == null || path.size() < shortest.size()){
				System.out.println("Found a path: "+path);
				shortest = path;
			}
		} else {
			CloserComparator comparator = new CloserComparator(target);
			List<Location> adjacentSquares = e.getLegalAdjacent(current);
			Collections.sort(adjacentSquares, comparator);
			//System.out.println("Adjacent Squares: "+adjacentSquares);
			for (Location adjacentSquare : adjacentSquares) {
				if (!path.contains(adjacentSquare)) {
					List<Location> newPath = new ArrayList<>();
					newPath.addAll(path);
					newPath.add(adjacentSquare);
					getNextStep(newPath, target);
				}
			}
		}
	}
	
	
	

}
