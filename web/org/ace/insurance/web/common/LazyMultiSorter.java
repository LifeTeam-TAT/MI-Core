package org.ace.insurance.web.common;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

public class LazyMultiSorter implements Comparator<Object> {
	private List<SortMeta> multiSortMeta;

	public LazyMultiSorter(List<SortMeta> multiSortMeta) {
		this.multiSortMeta = multiSortMeta;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int compare(Object obj1, Object obj2) {
		try {
			for (SortMeta sort : multiSortMeta) {
				if (sort.getSortField() != null) {
					Field f1 = obj1.getClass().getDeclaredField(sort.getSortField());
					f1.setAccessible(true);
					Object value1 = f1.get(obj1);

					Field f2 = obj2.getClass().getDeclaredField(sort.getSortField());
					f2.setAccessible(true);
					Object value2 = f2.get(obj2);
					int value = ((Comparable) value1).compareTo(value2);
					if (value != 0) {
						return SortOrder.ASCENDING.equals(sort.getSortOrder()) ? value : -1 * value;
					}
				}
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
