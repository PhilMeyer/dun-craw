package org.pnm.dun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.pnm.dun.model.Unit;
import org.pnm.dun.util.GeometryUtil;
import org.pnm.dun.util.Location;

public class Pathing {

	Environment e;
	List<Location> shortest;

	Map<Location, Integer> marks = new HashMap<>();

	Location start;
	Location end;

	public Pathing(Environment e, Unit actor, Unit target) {
		this.e = e;
		start = e.locations.get(actor);
		end = e.locations.get(target);
	}

	public List<Location> getShortestPath() {
		markGrid();
		//dumpMap();
		List<Location> path = backTrack();
		Collections.reverse(path);
		path.remove(0);
		path.remove(path.size()-1);
		System.out.println("Shortest path between: " + start + " and " + end + " is " + path);
		return path;
	}

	private void markGrid() {
		int steps = 0;
		marks.put(start, steps);
		List<Location> markedInPreviousStep = new ArrayList<>();
		markedInPreviousStep.add(start);
		boolean markedOne = true;
		boolean found = false;
		while (markedOne && !found) {
			//System.out.println(steps);
			//System.out.println(marks.size());
			steps++;
			markedOne = false;
			List<Location> markedThisStep = new ArrayList<>();
			for (Location loc : markedInPreviousStep) {
				List<Location> adjacentSquares = e.getAdjacent(loc);
				for (Location adj : adjacentSquares) {
					if(adj.equals(end)){
						found = true;
						marks.put(adj, steps);
						break;
					}
					Unit unitAt = e.getUnitAt(adj.x, adj.y);
					if(unitAt == null){
						if (!marks.containsKey(adj)) {
							marks.put(adj, steps);
							markedThisStep.add(adj);
							markedOne = true;
							// System.out.println("Marked "+adj+" with "+steps);
						}
					}
				}
			}
			markedInPreviousStep = markedThisStep;
		}
	}

	private List<Location> backTrack() {
		int prevMark = marks.get(end);
		List<Location> path = new ArrayList<>();
		path.add(end);
		boolean backAtStart = false;
		while (!backAtStart) {
			List<Location> adjacentSquares = e.getAdjacent(path.get(path.size()-1));
			for (Location adj : adjacentSquares) {
				Integer mark = marks.get(adj);
				if (adj.equals(start)) {
					backAtStart = true;
					path.add(adj);
					break;
				}
				//System.out.println("Mark for "+adj+" is "+mark);
				if (mark != null && mark < prevMark) {
					prevMark = mark;
					path.add(adj);
					break;
				}

			}
		}
		return path;
	}

	private void dumpMap() {
		for(int y = 0; y < 14; y++){
			for(int x = 0; x < 14; x++){
				Integer mark = marks.get(new Location(x,y));
				String s = mark == null ? " X" : String.valueOf(mark);
				if(mark != null && mark <= 9){
					s = " "+s;
				}
				System.out.print(" "+s);
			}
			System.out.println();
		}
	}

	// private void nextStep(List<Location> path, Location point, int step) {
	// marked.add(point);
	// List<Location> newPath = new ArrayList<>();
	// newPath.add(point);
	// List<Location> adjacentSquares = e.getLegalAdjacent(start);
	// for(Location adjacent : adjacentSquares){
	// if(!paths.containsKey(adjacent)){
	// paths.put(adjacent, path);
	// }
	// }
	// for(Location adjacent : adjacentSquares){
	// if(!marked.contains(adjacent)){
	// nextStep(newPath, adjacent, step+1);
	// }
	// }
	// }

	// List<Location> firstStep = new ArrayList<>();
	// firstStep.add(locA);
	// getNextStep(firstStep, locT);

	@SuppressWarnings("unused")
	private void getNextStep(List<Location> path, Location target) {
		Location current = path.get(path.size() - 1);
		System.out.println("                 Getting next step in path: " + path);
		double distance = GeometryUtil.distance(current, target);
		boolean hopeless = shortest != null && path.size() >= shortest.size();
		if (distance > 20 || hopeless) {
			return; // give up
		}
		if (distance < 2) {
			if (shortest == null || path.size() < shortest.size()) {
				System.out.println("*************************  Found a path: " + path);
				shortest = path;
			}
		} else {
			CloserComparator comparator = new CloserComparator(target);
			List<Location> adjacentSquares = e.getLegalAdjacent(current);
			Collections.sort(adjacentSquares, comparator);
			// System.out.println("Adjacent Squares: "+adjacentSquares);
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
