package org.ace.insurance.common;

import java.util.Date;

import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;

public class NotificationCriteria {
	private String reportType;
	private ReferenceType product;
	private Branch branch;
	private SalesPoints salePoint;
	private int year;
	private Date startDate;
	private Date endDate;
	private String policyNo;
	private MonthType month;

	public NotificationCriteria() {
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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

	public MonthType getMonth() {
		return month;
	}

	public void setMonth(MonthType month) {
		this.month = month;
	}

	public ReferenceType getProduct() {
		return product;
	}

	public void setProduct(ReferenceType product) {
		this.product = product;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public SalesPoints getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalesPoints salePoint) {
		this.salePoint = salePoint;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

}
