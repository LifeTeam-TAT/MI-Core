package org.ace.insurance.managementreport.customer;

import java.util.Map;

public class CustomerReport {
	private Map<String, Number> pieChartMap;

	public CustomerReport() {
	}

	public CustomerReport(Map<String, Number> pieChartMap) {
		this.pieChartMap = pieChartMap;
	}

	public Map<String, Number> getPieChartMap() {
		return pieChartMap;
	}

	public void setPieChartMap(Map<String, Number> pieChartMap) {
		this.pieChartMap = pieChartMap;
	}

}
