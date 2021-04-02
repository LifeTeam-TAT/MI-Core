package org.ace.insurance.web.common;

import java.util.Comparator;

import org.ace.insurance.report.customer.CustomerInformationReport;
import org.primefaces.model.SortOrder;

public class LazySorter implements Comparator<Object> {
	private String sortField;

	private SortOrder sortOrder;

	public LazySorter(String sortField, SortOrder sortOrder) {
		this.sortField = sortField;
		this.sortOrder = sortOrder;
	}

	public int compare(Object customer1, Object customer2) {
		try {
			Object value1 = CustomerInformationReport.class.getField(this.sortField).get(customer1);
			Object value2 = CustomerInformationReport.class.getField(this.sortField).get(customer2);
			int value = ((Comparable) value1).compareTo(value2);

			return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}
