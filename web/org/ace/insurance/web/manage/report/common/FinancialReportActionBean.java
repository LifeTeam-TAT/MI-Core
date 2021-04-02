package org.ace.insurance.web.manage.report.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.NavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.FinancialReport;
import org.ace.insurance.report.common.FinancialReport.ReportItem;
import org.ace.insurance.report.common.FinancialReportCriteria;
import org.ace.insurance.report.common.SummaryReportType;
import org.ace.insurance.report.common.service.interfaces.IFinancialReportService;
import org.ace.java.web.common.BaseBean;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

@ViewScoped
@ManagedBean(name = "FinancialReportActionBean")
public class FinancialReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{FinancialReportService}")
	private IFinancialReportService financialReportService;

	public void setFinancialReportService(IFinancialReportService financialReportService) {
		this.financialReportService = financialReportService;
	}

	private CartesianChartModel linearModel;
	private CartesianChartModel categoryModel;
	private int year;
	private SummaryReportType summeryReportType;
	private List<String> reportItemTypeList;
	private Date startDate;
	private Date endDate;
	private PolicyReferenceType referenceType;

	@PostConstruct
	public void init() {
		year = Calendar.getInstance().get(Calendar.YEAR);
		summeryReportType = SummaryReportType.ALL_PROPOSAL;
		FinancialReport summeryReport = financialReportService.findFinancialReport(new FinancialReportCriteria(summeryReportType, year));
		populateDate(summeryReport);
	}

	public SummaryReportType[] getSummeryReportTypes() {
		return SummaryReportType.values();
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	public CartesianChartModel getCategoryModel() {
		return categoryModel;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public SummaryReportType getSummeryReportType() {
		return summeryReportType;
	}

	public void setSummeryReportType(SummaryReportType summeryReportType) {
		this.summeryReportType = summeryReportType;
	}

	public void itemSelect(ItemSelectEvent event) {
		FacesContext context = getFacesContext();
		NavigationHandler navHandler = context.getApplication().getNavigationHandler();
		String itemType = reportItemTypeList.get(event.getSeriesIndex());
		if (itemType == "LIFE") {
			if (summeryReportType.equals(SummaryReportType.LIFE_PROPOSAL) || summeryReportType.equals(SummaryReportType.ALL_PROPOSAL)) {
				navHandler.handleNavigation(context, null, "lifeProposalReport" + "?faces-redirect=true");
			} else if (summeryReportType.equals(SummaryReportType.LIFE_POLICY) || summeryReportType.equals(SummaryReportType.ALL_POLICY)) {
				navHandler.handleNavigation(context, null, "lifePolicyReport" + "?faces-redirect=true");
			} else if (summeryReportType.equals(SummaryReportType.LIFE_SUMINSURE) || summeryReportType.equals(SummaryReportType.ALL_SUMINSURE)) {
				navHandler.handleNavigation(context, null, "lifePolicyReport" + "?faces-redirect=true");
			} else if (summeryReportType.equals(SummaryReportType.LIFE_PREMIUM) || summeryReportType.equals(SummaryReportType.ALL_PREMIUN)) {
				navHandler.handleNavigation(context, null, "lifePremiumPaymentReport" + "?faces-redirect=true");
			} else if (summeryReportType.equals(SummaryReportType.LIFE_AGENT_COMMISSION) || summeryReportType.equals(SummaryReportType.ALL_COMMISSION)) {
				// FIXME CHECK REFTYPE
				referenceType = PolicyReferenceType.ENDOWNMENT_LIFE_POLICY;
				navHandler.handleNavigation(context, null, "agentCommissionDetailReport" + "?faces-redirect=true");
			}
		}
		startDate = Utils.getStartDateOfMonth(event.getItemIndex() + 3, year);
		endDate = Utils.getEndDateOfMonth(event.getItemIndex() + 3, year);
	}

	private void populateDate(FinancialReport summeryReport) {
		linearModel = new CartesianChartModel();
		categoryModel = new CartesianChartModel();
		reportItemTypeList = new ArrayList<String>();
		Map<String, List<ReportItem>> summeryReportMap = summeryReport.getReportItemMap();
		for (String reportItemType : summeryReportMap.keySet()) {
			reportItemTypeList.add(reportItemType);
			LineChartSeries lineChartSeries = new LineChartSeries();
			ChartSeries chartSeries = new ChartSeries();
			lineChartSeries.setLabel(reportItemType.toString());
			chartSeries.setLabel(reportItemType.toString());
			for (ReportItem item : summeryReportMap.get(reportItemType)) {
				System.out.println(item.getXSeries() + " : " + item.getYSeries());
				lineChartSeries.set(item.getXSeries(), item.getYSeries());
				chartSeries.set(item.getXSeries(), item.getYSeries());
			}
			linearModel.addSeries(lineChartSeries);
			categoryModel.addSeries(chartSeries);
		}
	}

	/*
	 * private boolean validCriteria() { String formID = "financialReportForm";
	 * int currnetYear = Calendar.getInstance().get(Calendar.YEAR); if(year >
	 * currnetYear) { addErrorMessage(formID + ":validationMessage",
	 * UIInput.REQUIRED_MESSAGE_ID); return false; } return true; }
	 */
	public Map<String, Integer> getYears() {
		SortedMap<String, Integer> years = new TreeMap<String, Integer>(Collections.reverseOrder());
		int endYear = Calendar.getInstance().get(Calendar.YEAR);
		for (int startYear = 1900; startYear <= endYear; startYear++) {
			int x = startYear + 1;
			String key = startYear + " - " + x;
			years.put(key, startYear);
		}
		return years;
	}

	public void search() {
		/*
		 * if(!validCriteria()) { return; }
		 */
		FinancialReport summeryReport = financialReportService.findFinancialReport(new FinancialReportCriteria(summeryReportType, year));
		populateDate(summeryReport);
	}

	public String getYSeriesName() {
		String result = null;
		switch (summeryReportType) {
			case ALL_PROPOSAL: {
				result = "Number Of Proposal";
			}
				break;

			case LIFE_PROPOSAL: {
				result = "Number Of Proposal";
			}
				break;
			case ALL_POLICY: {
				result = "Number Of Policy";
			}
				break;

			case LIFE_POLICY: {
				result = "Number Of Policy";
			}
				break;
			case ALL_PREMIUN: {
				result = "Premium Amount (Lakh)";
			}
				break;

			case LIFE_PREMIUM: {
				result = "Premium Amount (Lakh)";
			}
				break;
			case ALL_SUMINSURE: {
				result = "SumInsured Amount (Lakh)";
			}
				break;

			case LIFE_SUMINSURE: {
				result = "SumInsured Amount (Lakh)";
			}
				break;
			case ALL_COMMISSION: {
				result = "Agent Commission Amount (Lakh)";
			}
				break;

			case LIFE_AGENT_COMMISSION: {
				result = "Agent Commission Amount (Lakh)";
			}
				break;
		}
		return result;
	}

	public String getBarCharTitle() {
		String result = null;
		switch (summeryReportType) {
			case ALL_PROPOSAL: {
				result = "Proposal Bar Chart";
			}
				break;

			case LIFE_PROPOSAL: {
				result = "Life Proposal Bar Chart";
			}
				break;
			case ALL_POLICY: {
				result = "Policy Bar Chart";
			}
				break;

			case LIFE_POLICY: {
				result = "Life Policy Bar Chart";
			}
				break;
			case ALL_PREMIUN: {
				result = "Premium Bar Chart";
			}
				break;

			case LIFE_PREMIUM: {
				result = "Life Premium Bar Chart";
			}
				break;
			case ALL_SUMINSURE: {
				result = "SumInsured Bar Chart";
			}
				break;

			case LIFE_SUMINSURE: {
				result = "Life SumInsured Bar Chart";
			}
				break;
			case ALL_COMMISSION: {
				result = "Agent Commission Bar Chart";
			}
				break;

			case LIFE_AGENT_COMMISSION: {
				result = "LIfe Agent Commission Bar Char";
			}
				break;
		}
		return result;
	}

	public String getLineCharTitle() {
		String result = null;
		switch (summeryReportType) {
			case ALL_PROPOSAL: {
				result = "Proposal Line Chart";
			}
				break;

			case LIFE_PROPOSAL: {
				result = "Life Proposal Line Chart";
			}
				break;
			case ALL_POLICY: {
				result = "Policy Line Chart";
			}
				break;

			case LIFE_POLICY: {
				result = "Life Policy Line Chart";
			}
				break;
			case ALL_PREMIUN: {
				result = "Premium Line Chart";
			}
				break;

			case LIFE_PREMIUM: {
				result = "Life Premium Line Chart";
			}
				break;
			case ALL_SUMINSURE: {
				result = "SumInsured Line Chart";
			}
				break;

			case LIFE_SUMINSURE: {
				result = "Life SumInsured Line Chart";
			}
				break;
			case ALL_COMMISSION: {
				result = "Agent Commission Line Chart";
			}
				break;

			case LIFE_AGENT_COMMISSION: {
				result = "LIfe Agent Commission Line Char";
			}
				break;
		}
		return result;
	}
}
