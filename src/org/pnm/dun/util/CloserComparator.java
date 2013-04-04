package org.pnm.dun.util;

import java.util.Comparator;


public class CloserComparator implements Comparator<Location> {

	Location target;
	
	public CloserComparator(Location target){
		this.target = target;
	}
	
	@Override
	public int compare(Location l1, Location l2) {
		Double d1 = GeometryUtil.distance(l1, target);
		Double d2 = GeometryUtil.distance(l2, target);
		return d1.compareTo(d2);
	}

}
