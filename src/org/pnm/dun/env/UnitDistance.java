package org.pnm.dun.env;

import org.pnm.dun.unit.Unit;

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


//public List<Unit> getNearestEnemies(Unit u) {
//	List<Unit> enemiesByDistance = new ArrayList<>();
//	List<UnitDistance> temp = new ArrayList<>();
//	for (Entry<Unit, Location> entry : locations.entrySet()) {
//		Unit o = entry.getKey();
//		if (o.side != u.side) {
//			double nearestDistance = Double.MAX_VALUE;
//			for (Location uOcc : occupations.get(u)) {
//				for (Location oOcc : occupations.get(o)) {
//					double distance = GeometryUtil.distance(uOcc, oOcc);
//					if (distance < nearestDistance) {
//						nearestDistance = distance;
//					}
//				}
//			}
//			temp.add(new UnitDistance(o, nearestDistance));
//		}
//	}
//
//	Collections.sort(temp);
//	for(UnitDistance ud : temp){
//		enemiesByDistance.add(ud.u);
//	}
//	return enemiesByDistance;
//}