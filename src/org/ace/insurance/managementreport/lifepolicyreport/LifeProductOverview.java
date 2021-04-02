package org.ace.insurance.managementreport.lifepolicyreport;

import java.util.Map;

public class LifeProductOverview {
    private Map<String, Number> chartMap;
	
	public LifeProductOverview (Map<String, Number> pieChartMap) {
		this.chartMap = pieChartMap;
	}

	public Map<String, Number> getPieChartMap() {
		return chartMap;
	}

	public void setPieChartMap(Map<String, Number> pieChartMap) {
		this.chartMap = pieChartMap;
	}

}
