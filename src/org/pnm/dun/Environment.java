package org.pnm.dun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pnm.dun.model.Unit;
import org.pnm.dun.util.GeometryUtil;
import org.pnm.dun.util.Location;

public class Environment {

	Map<Unit, Location> locations = new HashMap<>();
	Map<Unit, List<Location>> occupations = new HashMap<>();
	Map<Unit, Integer> orientations = new HashMap<>();
	

	public Set<Entry<Unit, Location>> getUnitEntries() {
		Set<Entry<Unit, Location>> set = new HashSet<>();
		set.addAll(locations.entrySet());
		return set;
	}

	public Unit getUnitAt(int x, int y) {
		Location test = new Location(x, y);
		//System.out.println("Getting unit at: " + test);
		for (Entry<Unit, List<Location>> entry : occupations.entrySet()) {
			List<Location> tempList = entry.getValue();
			for (Location temp : tempList) {
				if (temp.equals(test)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

	public Location getLocation(Unit selected) {
		return locations.get(selected);

	}

	public void place(Unit unit, Location location) {
		place(unit, location, 0);
	}

	public void place(Unit unit, Location location, Integer orientation) {
		locations.put(unit, location);
		List<Location> occupationList = new ArrayList<>();
		occupationList.add(location);
		if (unit.base == 3) {
			occupationList.add(new Location(location.x, location.y + 1));
			occupationList.add(new Location(location.x + 1, location.y));
			occupationList.add(new Location(location.x + 1, location.y + 1));
		}
		occupations.put(unit, occupationList);
		orientations.put(unit, orientation);
	}

	public Integer getOrientation(Unit unit) {
		Integer o = orientations.get(unit);
		if(o == null){
			o = Integer.valueOf(0);
		}
		return o;
	}

	public List<Unit> getAdjacentEnemies(Unit u) {
		List<Unit> adjacentEnemies = new ArrayList<>();
		List<Location> list = occupations.get(u);
		if(list == null){
			System.out.println(u);
			throw new IllegalArgumentException();
		}
		for (Location uSquare : list) {
			for (Entry<Unit, List<Location>> entry : occupations.entrySet()) {
				Unit o = entry.getKey();
				if (o.side != u.side) {
					List<Location> occupiedSquares = entry.getValue();
					for (Location occupiedSquare : occupiedSquares) {
						double distance = GeometryUtil.distance(occupiedSquare, uSquare);
						if (distance < 2 && !adjacentEnemies.contains(o)) {
							adjacentEnemies.add(o);
						}
					}
				}
			}
		}
		return adjacentEnemies;
	}

	public List<Unit> getNearestEnemies(Unit u) {
		List<Unit> enemiesByDistance = new ArrayList<>();
		List<UnitDistance> temp = new ArrayList<>();
		for (Entry<Unit, Location> entry : locations.entrySet()) {
			Unit o = entry.getKey();
			if (o.side != u.side) {
				double nearestDistance = Double.MAX_VALUE;
				for (Location uOcc : occupations.get(u)) {
					for (Location oOcc : occupations.get(o)) {
						double distance = GeometryUtil.distance(uOcc, oOcc);
						if (distance < nearestDistance) {
							nearestDistance = distance;
						}
					}
				}
				temp.add(new UnitDistance(o, nearestDistance));
			}
		}

		Collections.sort(temp);
		for(UnitDistance ud : temp){
			enemiesByDistance.add(ud.u);
		}
		return enemiesByDistance;
	}

	class UnitDistance implements Comparable<UnitDistance> {
		Unit u;
		double d;

		public UnitDistance(Unit u, double d) {
			this.u = u;
			this.d = d;
		}

		@Override
		public int compareTo(UnitDistance o) {
			return Double.valueOf(d).compareTo(o.d);
		}

		public String toString() {
			return u + " - " + d;
		}
	}

	public void remove(Unit u) {
		locations.remove(u);
		occupations.remove(u);
		orientations.remove(u);
	}

	public List<Location> getShortestPath(Unit actor, Unit target) {
		Pathing pathing = new Pathing(this);
		return pathing.getShortestPath(actor, target);
	}

	private void pruneOccupied(List<Location> adjacent) {
		Iterator<Location> iterator = adjacent.iterator();
		while(iterator.hasNext()){
			Location loc = iterator.next();
			if(getUnitAt(loc.x, loc.y) != null){
				iterator.remove();
			}
		}
	}

	public List<Location> getLegalAdjacent(Location loc) {
		List<Location> adjacent = new ArrayList<>();
		adjacent.add(new Location(loc.x-1, loc.y));
		adjacent.add(new Location(loc.x+1, loc.y));
		adjacent.add(new Location(loc.x, loc.y-1));
		adjacent.add(new Location(loc.x, loc.y+1));
		adjacent.add(new Location(loc.x-1, loc.y-1));
		adjacent.add(new Location(loc.x+1, loc.y+1));
		pruneOccupied(adjacent);
		//pruneOccupied(adjacent);
		return adjacent;
	}

//	public Location getStep(Unit actor, Unit target) {
//		Location start = locations.get(actor);
//		List<Location> adjacent = new ArrayList<>();
//		adjacent.add(new Location());
//		
//		
//		return null;
//	}

}
