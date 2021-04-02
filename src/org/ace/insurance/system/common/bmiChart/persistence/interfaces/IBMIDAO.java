package org.ace.insurance.system.common.bmiChart.persistence.interfaces;

import java.util.List;

import org.ace.insurance.system.common.bmiChart.BMIChart;

public interface IBMIDAO {

	List<BMIChart> findAllBMIChart();

	void insertBMIChart(BMIChart bmiChart);

	void updateBMIChart(BMIChart bmiChart);

	void deleteBMIChart(BMIChart bmiChart);

	int findPoundByAgeAndHeight(int age, int height);

}
