package org.ace.insurance.web.manage.report.common;

import java.io.IOException;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;

import org.ace.insurance.common.MonthNames;
import org.ace.insurance.report.common.ProposalPremiumSummaryReport;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.common.service.interfaces.IProposalPremiumSummaryReportService;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.web.common.BaseBean;

@ViewScoped
@ManagedBean(name = "ProposalPremiunSummaryReportActionBean")
public class ProposalPremiunSummaryReportActionBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{ProposalPremiumSummaryReportService}")
	private IProposalPremiumSummaryReportService proposalPremiumSummaryReportService;

	public void setProposalPremiumSummaryReportService(IProposalPremiumSummaryReportService proposalPremiumSummaryReportService) {
		this.proposalPremiumSummaryReportService = proposalPremiumSummaryReportService;
	}

	private SummaryReportCriteria criteria;
	private List<ProposalPremiumSummaryReport> summaryReportList;
	private boolean showWeekly;
	private boolean showYearly;
	private boolean showMonthly;
	private final String reportName = "ProposalPremiumSummaryReport";
	private final String pdfDirPath = "/pdf-report/" + reportName + "/" + System.currentTimeMillis() + "/";
	private final String dirPath = getSystemPath() + pdfDirPath;
	private final String fileName = reportName + ".pdf";

	@PostConstruct
	public void init() {
		showWeekly = true;
		summaryReportList = new ArrayList<ProposalPremiumSummaryReport>();
		reset();
	}

	public SummaryReportCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(SummaryReportCriteria criteria) {
		this.criteria = criteria;
	}

	public boolean isShowWeekly() {
		return showWeekly;
	}

	public void setShowWeekly(boolean showWeekly) {
		this.showWeekly = showWeekly;
	}

	public boolean isShowYearly() {
		return showYearly;
	}

	public void setShowYearly(boolean showYearly) {
		this.showYearly = showYearly;
	}

	public boolean isShowMonthly() {
		return showMonthly;
	}

	public void setShowMonthly(boolean showMonthly) {
		this.showMonthly = showMonthly;
	}

	public void reset() {
		criteria = new SummaryReportCriteria();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -7);
		criteria.setStartDate(cal.getTime());
		Date endDate = new Date();
		criteria.setEndDate(endDate);
		cal.setTime(endDate);
		criteria.setYear(cal.get(Calendar.YEAR));
		criteria.setReportType("Weekly Report");
		summaryReportList = proposalPremiumSummaryReportService.findProposalPremiumSummaryReport(criteria);
	}

	public void search() {
		if (validation()) {
			summaryReportList = proposalPremiumSummaryReportService.findProposalPremiumSummaryReport(criteria);
		}
	}

	public List<ProposalPremiumSummaryReport> getSummaryReportList() {
		return summaryReportList;
	}

	public List<String> getReportTypeList() {
		List<String> result = new ArrayList<String>();
		result.add("Weekly Report");
		result.add("Monthly Report");
		result.add("Yearly Report");
		return result;
	}

	public void criteriaChangeOrgEvent(AjaxBehaviorEvent event) {
		showWeekly = false;
		showMonthly = false;
		showYearly = false;
		if (criteria.getReportType().equalsIgnoreCase("Monthly Report")) {
			showMonthly = true;
		}
		if (criteria.getReportType().equalsIgnoreCase("Weekly Report")) {
			showWeekly = true;
		}
		if (criteria.getReportType().equalsIgnoreCase("Yearly Report")) {
			showYearly = true;
		}
	}

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

	public MonthNames[] getMonthSelectItemList() {
		return MonthNames.values();
	}

	private boolean validation() {
		boolean valid = true;
		String formID = "summaryForm";
		if (criteria.getReportType().equalsIgnoreCase("Weekly Report")) {
			if (criteria.getStartDate() == null) {
				addErrorMessage(formID + ":startDate", UIInput.REQUIRED_MESSAGE_ID);
				valid = false;
			}
		}
		return valid;
	}

	public void generateReport() {
		try {
			FileHandler.forceMakeDirectory(dirPath);
			// premiumPaymentReportService.generateLifePremiumPaymentReport(getPremiumPaymentList(),
			// dirPath + fileName);
			proposalPremiumSummaryReportService.generateProposalPremiumSummaryReport(getSummaryReportList(), dirPath + fileName, criteria);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getStream() {
		String fileFullPath = pdfDirPath + fileName;
		return fileFullPath;
	}

	//
	// public String getStream()throws IOException {
	// FileHandler.forceMakeDirectory(ctxPath + reportDir);
	// stream = reportDir + "/" + reportFileName;
	// proposalPremiumSummaryReportService.generateProposalPremiumSummaryReport(getSummaryReportList(),
	// ctxPath + stream, criteria);
	// return stream;
	// }
}
