package org.ace.insurance.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DTOSorter<T extends IDTOSorter> {
	private List<T> sortedList;

	public DTOSorter(List<T> entitylist) {
		if (entitylist != null && !entitylist.isEmpty()) {
			sortedList = new ArrayList<T>();
			Collections.sort(entitylist, new Comparator<T>() {
				@Override
				public int compare(IDTOSorter o1, IDTOSorter o2) {
					Long l1, l2;
					try {
						l1 = Long.parseLong(o1.getTempId());
						l2 = Long.parseLong(o2.getTempId());
					} catch (NumberFormatException e) {
						try {
							l1 = Long.parseLong(o1.getTempId().substring(11, 20));
							l2 = Long.parseLong(o2.getTempId().substring(11, 20));
						} catch (StringIndexOutOfBoundsException exp) {
							l1 = Long.parseLong(o1.getTempId().substring(11));
							l2 = Long.parseLong(o2.getTempId().substring(11));
						}
					}
					if (l1 > l2) {
						return 1;
					} else if (l1 < l2) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			for (T t : entitylist) {
				sortedList.add(t);
			}
		}
	}

	public List<T> getSortedList() {
		return sortedList;
	}

	/* Example Usage */
	public static void main(String[] args) {
	}
}
