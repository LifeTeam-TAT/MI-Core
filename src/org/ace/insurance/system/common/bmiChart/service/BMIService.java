package org.ace.insurance.system.common.bmiChart.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Query;

import org.ace.insurance.system.common.bmiChart.BMIChart;
import org.ace.insurance.system.common.bmiChart.persistence.interfaces.IBMIDAO;
import org.ace.insurance.system.common.bmiChart.service.interfaces.IBMIService;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.ace.java.component.service.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value="BMIService")
public class BMIService extends BaseService implements IBMIService{

	@Resource(name="BMIDAO")
	private IBMIDAO bmiDAO;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<BMIChart> findAllBMIChart() {
		List<BMIChart> bmiChartList;
		try {
			bmiChartList=bmiDAO.findAllBMIChart();
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Failed to find all BMI Chart", e);
		}
		return bmiChartList;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertBMIChart(BMIChart bmiChart) {
		try {
			bmiDAO.insertBMIChart(bmiChart);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to insert BMI Chart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateBMIChart(BMIChart bmiChart) {
		try {
			bmiDAO.updateBMIChart(bmiChart);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to update BMI Chart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteBMIChart(BMIChart bmiChart) {
		try {
			bmiDAO.deleteBMIChart(bmiChart);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to delete BMI Chart", e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public int findPoundByAgeAndHeight(int age, int height) {
		int bmiWeight;
		try {
			bmiWeight=bmiDAO.findPoundByAgeAndHeight(age,height);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find Pound By Age And Height", e);
		}
		return bmiWeight;
	}

}
