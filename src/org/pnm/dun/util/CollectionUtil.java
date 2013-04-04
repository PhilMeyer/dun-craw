package org.pnm.dun.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {

	public static <T> List<T> intersect(List<T> l1, List<T> l2) {
		List<T> list = new ArrayList<T>();
		for(T t : l1){
			if(l2.contains(t)){
				list.add(t);
			}			
		}
		return list;
	}

}
