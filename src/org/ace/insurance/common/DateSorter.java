package org.ace.insurance.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Zaw Than Oo
 * @Use The <code>RegNoSorter</code> class is used to sort the entities which
 *      have custom registration number format.
 */
public class DateSorter<T extends IDateSorter> {
	private List<T> sortedList;

	public DateSorter(List<T> entitylist) {
		if (entitylist != null && !entitylist.isEmpty()) {
			sortedList = new ArrayList<T>();
			Collections.sort(entitylist, new Comparator<T>() {
				public int compare(T obj1, T obj2) {
					Date date1 = obj1.getSortingDate();
					Date date2 = obj2.getSortingDate();
					return date1.compareTo(date2);
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
}