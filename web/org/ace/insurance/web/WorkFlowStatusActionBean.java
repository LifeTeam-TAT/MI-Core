package org.ace.insurance.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.insurance.common.WorkFlowType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.FinancialReport.ReportItem;
import org.ace.insurance.report.common.SummaryReportType;
import org.ace.insurance.report.common.service.interfaces.IFinancialReportService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;

@ViewScoped
@ManagedBean(name = "WorkFlowStatusActionBean")
public class WorkFlowStatusActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{FinancialReportService}")
	private IFinancialReportService financialReportService;

	public void setFinancialReportService(IFinancialReportService financialReportService) {
		this.financialReportService = financialReportService;
	}

	private List<String> reportItemTypeList;
	private WorkFlowType workFlowType;
	private WorkflowTask workFlowTask;

	@PostConstruct
	public void init() {
	}

	public SummaryReportType[] getSummeryReportTypes() {
		return SummaryReportType.values();
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesContext context = getFacesContext();
		NavigationHandler navHandler = context.getApplication().getNavigationHandler();
		String itemType = reportItemTypeList.get(event.getSeriesIndex());
		if (itemType == "MOTOR") {
			workFlowType = WorkFlowType.MOTOR;
		} else if (itemType == "FIRE") {
			workFlowType = WorkFlowType.FIRE;
		} else if (itemType == "LIFE") {
			workFlowType = WorkFlowType.LIFE;
		}
		navHandler.handleNavigation(context, null, "workFlowDetailsStatus" + "?faces-redirect=true");
		int index = event.getItemIndex();
		System.out.println("SelectedIndx : " + index);
		switch (index) {
			case 0: {
				workFlowTask = WorkflowTask.SURVEY;
			}
				break;
			case 1: {
				workFlowTask = WorkflowTask.APPROVAL;
			}
				break;
			case 2: {
				workFlowTask = WorkflowTask.INFORM;
			}
				break;
			case 3: {
				workFlowTask = WorkflowTask.CONFIRMATION;
			}
				break;
			case 4: {
				workFlowTask = WorkflowTask.PAYMENT;
			}
				break;
			case 5: {
				workFlowTask = WorkflowTask.ISSUING;
			}
				break;
		}
	}

	public CartesianChartModel getCategoryModel() {
		FinancialReport summeryReport = financialReportService.findAllWorkFlowStatus();
		reportItemTypeList = new ArrayList<String>();
		CartesianChartModel categoryModel = new CartesianChartModel();
		Map<String, List<ReportItem>> summeryReportMap = summeryReport.getReportItemMap();
		for (String reportItemType : summeryReportMap.keySet()) {
			reportItemTypeList.add(reportItemType);
			ChartSeries chartSeries = new ChartSeries();
			chartSeries.setLabel(reportItemType.toString());
			for (ReportItem item : summeryReportMap.get(reportItemType)) {
				chartSeries.set(item.getXSeries(), item.getYSeries());
			}
			categoryModel.addSeries(chartSeries);
		}
		return categoryModel;
	}
}
