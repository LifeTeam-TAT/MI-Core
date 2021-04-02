package org.ace.insurance.web.manage.report.medical;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthDailyIncomeReportCriteria {

	private Date stratDate;
	private Date endDate;
	private String branchId;
	private List<String> productIdList;

	public HealthDailyIncomeReportCriteria() {
		super();
	}

	public HealthDailyIncomeReportCriteria(Date stratDate, Date endDate, String branchId, List<String> productIdList) {
		super();
		this.stratDate = stratDate;
		this.endDate = endDate;
		this.branchId = branchId;
		this.productIdList = productIdList;
	}

	public Date getStartDate() {
		return stratDate;
	}

	public void setStartDate(Date strarDate) {
		this.stratDate = strarDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Date getStratDate() {
		return stratDate;
	}

	public void setStratDate(Date stratDate) {
		this.stratDate = stratDate;
	}

	public List<String> getProductIdList() {
		if (productIdList == null) {
			return productIdList = new ArrayList<String>();
		} else {
			return productIdList;
		}

	}

	public void setProductIdList(List<String> productIdList) {
		this.productIdList = productIdList;
	}

}
