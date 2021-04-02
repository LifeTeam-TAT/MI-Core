package org.ace.insurance.web.manage.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.ace.insurance.system.common.bmiChart.BMIChart;
import org.ace.insurance.system.common.bmiChart.service.interfaces.IBMIService;
import org.ace.java.component.SystemException;
import org.ace.java.web.common.BaseBean;
import org.ace.java.web.common.MessageId;

@ViewScoped
@ManagedBean(name = "ManageBMIChartActionBean")
public class ManageBMIChartActionBean extends BaseBean {

	@ManagedProperty(value="#{BMIService}")
	private IBMIService bmiService;

	public void setBmiService(IBMIService bmiService) {
		this.bmiService = bmiService;
	}
	
	private BMIChart bmiChart;
	private boolean isNew;
	private List<BMIChart> bmiChartList;
	
	@PostConstruct
	public void init(){
		loadList();
	}

	public void createNewInstance() {
		bmiChart=new BMIChart();
		isNew=true;
		bmiChartList=new ArrayList<>();
	}

	public void loadList() {
		createNewInstance();
		bmiChartList=bmiService.findAllBMIChart();
	}
	
	public void insertBMIChart(){
		try {
			bmiChart.setHeight(bmiChart.getFeets()*12+bmiChart.getInches());
			bmiService.insertBMIChart(bmiChart);
			addInfoMessage(null, MessageId.INSERT_SUCCESS, "BMI Chart");
			loadList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}
	
	public void updateBMIChart() {
		try {
			bmiChart.setHeight(bmiChart.getFeets()*12+bmiChart.getInches());
			bmiService.updateBMIChart(bmiChart);
			addInfoMessage(null, MessageId.UPDATE_SUCCESS, "BMI Chart");
			loadList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void deleteBMIChart() {
		try {
			bmiService.deleteBMIChart(bmiChart);
			addInfoMessage(null, MessageId.DELETE_SUCCESS, "BMI Chart");
			loadList();
		} catch (SystemException ex) {
			handelSysException(ex);
		}
	}

	public void prepareUpdateBMIChart(BMIChart bmiChart) {
		bmiChart.setFeets(bmiChart.getHeight()/12);
		bmiChart.setInches(bmiChart.getHeight()%12);
		this.bmiChart = bmiChart;
		isNew = false;
	}

	public BMIChart getBmiChart() {
		return bmiChart;
	}

	public void setBmiChart(BMIChart bmiChart) {
		this.bmiChart = bmiChart;
	}

	public boolean getIsNew() {
		return isNew;
	}

	public List<BMIChart> getBmiChartList() {
		return bmiChartList;
	}
	
	
}
