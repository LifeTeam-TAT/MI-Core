package org.ace.insurance.web.common;

import java.util.Map;
import java.util.Set;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

public class CartesianChartModelUtil {

	public static CartesianChartModel getCartesianChartModel(Map<String, Number> map) {
		CartesianChartModel categoryModel = new CartesianChartModel();
		ChartSeries series = new ChartSeries();
		series.setLabel("Total");
		Set<String> keys = map.keySet();
		for (String key : keys) {
			series.set(key, map.get(key));
		}
		categoryModel.addSeries(series);
		return categoryModel;
	}
}
