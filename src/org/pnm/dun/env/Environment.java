package org.pnm.dun.env;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pnm.dun.engine.AttackEngine;
import org.pnm.dun.engine.RulesEngine;
import org.pnm.dun.unit.Unit;
import org.pnm.dun.unit.Unit.Side;
import org.pnm.dun.unit.Weapon;
import org.pnm.dun.util.GeometryUtil;
import org.pnm.dun.util.Location;

public class Environment {

	private static final double SQRT2 = Math.sqrt(2);

	Map<Unit, Location> locations = new HashMap<>();
	Map<Unit, List<Location>> occupations = new HashMap<>();
	Map<Unit, Integer> orientations = new HashMap<>();
	private Unit actor;

	public Set<Entry<Unit, Location>> getUnitEntries() {
		Set<Entry<Unit, Location>> set = new HashSet<>();
		set.addAll(locations.entrySet());
		return set;
	}

	public Unit getUnitAt(int x, int y) {
		Location test = new Location(x, y);
		// System.out.println("Getting unit at: " + test);
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
		place(unit, location, null);
	}

	public void place(Unit unit, Location location, Integer orientation) {
		Integer currentOrientation = orientations.get(unit);
		if (orientation == null) {
			orientation = (currentOrientation != null) ? currentOrientation : 0;
		}
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
		if (o == null) {
			o = Integer.valueOf(0);
		}
		return o;
	}

	public List<Unit> getAdjacentUnits(Unit u, Side side) {
		List<Unit> adjacentUnits = new ArrayList<>();
		List<Location> list = occupations.get(u);
		if (list == null) {
			System.out.println(u);
			throw new IllegalArgumentException();
		}
		for (Location uSquare : list) {
			for (Entry<Unit, List<Location>> entry : occupations.entrySet()) {
				Unit o = entry.getKey();
				if (o.side == side && !o.equals(u)) {
					List<Location> occupiedSquares = entry.getValue();
					for (Location occupiedSquare : occupiedSquares) {
						double distance = GeometryUtil.distance(occupiedSquare, uSquare);
						if (distance < 2 && !adjacentUnits.contains(o)) {
							adjacentUnits.add(o);
						}
					}
				}
			}
		}
		return adjacentUnits;
	}

	public List<Unit> getAdjacentAllies(Unit u) {
		return getAdjacentUnits(u, u.side);
	}

	public List<Unit> getAdjacentEnemies(Unit u) {
		return getAdjacentUnits(u, u.getEnemySide());
	}

	public List<Unit> getNearestEnemies(Unit u) {
		List<UnitDistance> distances = new ArrayList<>();
		for (Entry<Unit, Location> entry : locations.entrySet()) {
			Unit o = entry.getKey();
			if (o.side != u.side) {
				Pathing pathing = new Pathing(this, u, o);
				List<Location> shortestPath = pathing.getShortestPath();
				if (shortestPath != null) {
					distances.add(new UnitDistance(u, shortestPath.size()));
				}
			}
		}
		List<Unit> enemiesByDistance = new ArrayList<>();
		Collections.sort(distances);
		for (UnitDistance ud : distances) {
			enemiesByDistance.add(ud.u);
		}
		return enemiesByDistance;
	}

	public void remove(Unit u) {
		locations.remove(u);
		occupations.remove(u);
		orientations.remove(u);
	}

	public List<Location> getShortestPath(Unit actor, Unit target) {
		Pathing pathing = new Pathing(this, actor, target);
		return pathing.getShortestPath();
	}

	// private void pruneOccupied(List<Location> adjacent) {
	// Iterator<Location> iterator = adjacent.iterator();
	// while(iterator.hasNext()){
	// Location loc = iterator.next();
	// if(getUnitAt(loc.x, loc.y) != null){
	// iterator.remove();
	// }
	// }
	// }

	// public List<Location> getLegalAdjacent(Location loc) {
	// List<Location> adjacent = new ArrayList<>();
	// adjacent.add(new Location(loc.x-1, loc.y));
	// adjacent.add(new Location(loc.x+1, loc.y));
	// adjacent.add(new Location(loc.x, loc.y-1));
	// adjacent.add(new Location(loc.x, loc.y+1));
	// adjacent.add(new Location(loc.x-1, loc.y-1));
	// adjacent.add(new Location(loc.x+1, loc.y+1));
	// pruneOccupied(adjacent);
	// //pruneOccupied(adjacent);
	// return adjacent;
	// }

	public void orient(Unit u, int angle) {
		// System.out.println("Orienting "+u+" at "+angle);
		orientations.put(u, angle);
	}

	public void setCurrentlyActing(Unit actor) {
		this.actor = actor;
	}

	public Unit getActor() {
		return actor;
	}

	public List<Location> getAdjacent(Location loc) {
		List<Location> adjacent = new ArrayList<>();
		adjacent.add(new Location(loc.x - 1, loc.y));
		adjacent.add(new Location(loc.x + 1, loc.y));
		adjacent.add(new Location(loc.x, loc.y - 1));
		adjacent.add(new Location(loc.x, loc.y + 1));
		adjacent.add(new Location(loc.x - 1, loc.y - 1));
		adjacent.add(new Location(loc.x + 1, loc.y + 1));
		adjacent.add(new Location(loc.x + 1, loc.y - 1));
		adjacent.add(new Location(loc.x - 1, loc.y + 1));
		return adjacent;
	}

	public boolean isStillAlive(Unit u) {
		return locations.get(u) != null;
	}

	public int getRangedPenalties(Unit a, Unit d) {
		Location aLoc = getLocation(a);
		Location dLoc = getLocation(d);
		Weapon wep = a.getDefaultWeapon();
		double distance = GeometryUtil.distance(aLoc, dLoc);
		int rangeMod = 0;
		if (distance > SQRT2) {
			rangeMod = (int) Math.floor(distance / wep.rng);
			RulesEngine.log("Range is {0} giving mod of -{1}.", distance, rangeMod);
		}
		return 0;
	}

	public Location getSafeSquare(Unit u) {
		return null;
	}

	public Set<Unit> getUnits() {
		return locations.keySet();
	}

	public List<Unit> getAdjacent(Unit u) {
		List<Unit> list = new ArrayList<>();
		List<Location> adjacentLoc = getAdjacent(locations.get(u));
		for (Location loc : adjacentLoc) {
			Unit adj = getUnitAt(loc);
			if (adj != null) {
				list.add(adj);
			}
		}
		return list;
	}

	public Unit getUnitAt(Location loc) {
		return getUnitAt(loc.x, loc.y);
	}

	public void place(Unit u, int x, int y) {
		place(u, new Location(x, y));
	}

}
