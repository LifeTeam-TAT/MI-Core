package org.ace.insurance.report.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinancialReport {
	public class ReportItem {
		private String xSeries;
		private Double ySeries;
		
		public ReportItem(String xSeries, Double ySeries) {
			this.xSeries = xSeries;
			this.ySeries = ySeries;
		}

		public String getXSeries() {
			return xSeries;
		}

		public Double getYSeries() {
			return ySeries;
		}
	}
	
	private Map<String, List<ReportItem>> reportItemMap;

	public FinancialReport (){
		reportItemMap = new HashMap<String, List<ReportItem>>();
	}
	

	public Map<String, List<ReportItem>> getReportItemMap() {
		return reportItemMap;
	}

	public void addReportItem(String type, String xSeries, Double ySeries) {
		 List<ReportItem> itemList = reportItemMap.get(type);
		 if(itemList == null) {
			 itemList = new ArrayList<ReportItem>();
			 reportItemMap.put(type, itemList);
		 }
		 itemList.add(new ReportItem(xSeries, ySeries));
	}
}
