package org.ace.insurance.web.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

/**
 * Dummy implementation of LazyDataModel that uses a list to mimic a real data
 * source like a database.
 */
public class LazyDataModelUtil<T> extends LazyDataModel<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1792371435530976490L;
	private List<T> datasource = Collections.emptyList();

	private List<SortMeta> sortMetas = Collections.emptyList();

	private List<SortMeta> defaultSortMetas = Collections.emptyList();

	Map<String, Object> filters = Collections.emptyMap();

	public void setDefaultSortMetas(List<SortMeta> defaultSortMetas) {
		this.defaultSortMetas = defaultSortMetas;
	}

	public List<SortMeta> getDefaultSortMetas() {
		return defaultSortMetas;
	}

	public LazyDataModelUtil(List<T> datasource) {
		this.datasource = datasource;
	}

	private List<SortMeta> addDefaultSortMetas(List<SortMeta> source, List<SortMeta> target) {
		int index = 0;
		if (target == null)
			target = new ArrayList<SortMeta>();
		// TODO
		// the problem here is if I allowed the user's sort
		// the report will as many the footer as the branch changes

		// if I allowed default sort ,
		// user won't be able to see what they want
		for (SortMeta sourceSM : source) {
			SortMeta temp = sourceSM;
			for (SortMeta targetSM : target) {
				if (targetSM.getSortField() != null && targetSM.getSortField().equals(sourceSM.getSortField())) {
					temp = new SortMeta();
					temp.setSortFunction(targetSM.getSortFunction());
					temp.setSortField(targetSM.getSortField());
					temp.setSortOrder(targetSM.getSortOrder());
					target.remove(targetSM);
					break;
				}
			}
			target.add(index, temp);
			index++;
		}

		// outerloop: for (SortMeta sourceSM : source) {
		// for (SortMeta targetSM : target) {
		// if (targetSM.getSortField() != null &&
		// targetSM.getSortField().equals(sourceSM.getSortField())) {
		// continue outerloop;
		// }
		// }
		// target.add(sourceSM);
		// index++;
		// }

		return target;
	}

	public List<T> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String, Object> filters) {
		List<T> data = new ArrayList<T>();

		if (!defaultSortMetas.isEmpty()) {
			multiSortMeta = addDefaultSortMetas(this.defaultSortMetas, multiSortMeta);
		}

		this.sortMetas = multiSortMeta;
		this.filters = filters;

		// filter
		if (datasource != null) {
			for (T temp : datasource) {
				boolean match = true;

				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						Field field = temp.getClass().getDeclaredField(filterProperty);
						field.setAccessible(true);
						String fieldValue = String.valueOf(field.get(temp)).toLowerCase();

						if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
							match = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
						e.printStackTrace();
					}
				}

				if (match) {
					data.add(temp);
				}
			}
		}

		// sort
		if (multiSortMeta != null && !multiSortMeta.isEmpty()) {
			Collections.sort(data, new LazyMultiSorter(multiSortMeta));
		}

		// rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}

	public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		List<T> data = new ArrayList<T>();
		if (sortOrder != null && sortField != null) {
			SortMeta sortMeta = new SortMeta();
			sortMeta.setSortField(sortField);
			sortMeta.setSortOrder(sortOrder);
			this.sortMetas = Arrays.asList(sortMeta);
		}
		if (!defaultSortMetas.isEmpty()) {
			this.sortMetas = addDefaultSortMetas(this.defaultSortMetas, this.sortMetas);
		}
		this.filters = filters;

		// filter
		if (datasource != null) {
			for (T temp : datasource) {
				boolean match = true;

				for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
					try {
						String filterProperty = it.next();
						Object filterValue = filters.get(filterProperty);
						Field field = temp.getClass().getDeclaredField(filterProperty);
						field.setAccessible(true);
						String fieldValue = String.valueOf(field.get(temp)).toLowerCase();

						if (filterValue == null || fieldValue.startsWith(filterValue.toString())) {
							match = true;
						} else {
							match = false;
							break;
						}
					} catch (Exception e) {
						match = false;
						e.printStackTrace();
					}
				}

				if (match) {
					data.add(temp);
				}
			}
		}

		// sort
		if (sortMetas != null && !sortMetas.isEmpty()) {
			Collections.sort(data, new LazyMultiSorter(sortMetas));
		}

		// rowCount
		int dataSize = data.size();
		this.setRowCount(dataSize);

		// paginate
		if (dataSize > pageSize) {
			try {
				return data.subList(first, first + pageSize);
			} catch (IndexOutOfBoundsException e) {
				return data.subList(first, first + (dataSize % pageSize));
			}
		} else {
			return data;
		}
	}

	// start with 0 and end with size to get full list without pagination
	// and filter/sort by saved filter/sort
	public List<T> getSortedList() {
		return load(0, datasource.size(), sortMetas, filters);
	}

}
