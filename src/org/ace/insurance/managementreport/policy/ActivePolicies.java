package org.ace.insurance.managementreport.policy;

import java.util.Map;

public class ActivePolicies {
	private Map<String, Number> pieChartMap;
	
	public ActivePolicies (Map<String, Number> pieChartMap) {
		this.pieChartMap = pieChartMap;
	}

	public ActivePolicies() {
		// TODO Auto-generated constructor stub
	}

	public Map<String, Number> getPieChartMap() {
		return pieChartMap;
	}

	public void setPieChartMap(Map<String, Number> pieChartMap) {
		this.pieChartMap = pieChartMap;
	}
}
