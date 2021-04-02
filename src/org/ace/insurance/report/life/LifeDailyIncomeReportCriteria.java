package org.ace.insurance.report.life;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LifeDailyIncomeReportCriteria {
	private Date startDate;
	private Date endDate;
	private String branchId;
	private List<String> productIdList;

	public LifeDailyIncomeReportCriteria() {
	}

	public LifeDailyIncomeReportCriteria(Date startDate, Date endDate, String branch, List<String> productIdList) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.branchId = branch;
		this.productIdList = productIdList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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
