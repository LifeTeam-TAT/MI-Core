package org.ace.insurance.report.common;

import java.util.Date;

import org.ace.insurance.common.KeyFactorIDConfig;
import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.system.common.branch.Branch;

public class SummaryReportCriteria {
	private String reportType;
	private int year;
	private int month;
	private Date startDate;
	private Date endDate;
	private LifeProductType lifeProductType;
	private boolean continuePolicy;
	private Branch branch;

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
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

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
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

	public LifeProductType getLifeProductType() {
		return lifeProductType;
	}

	public void setLifeProductType(LifeProductType lifeProductType) {
		this.lifeProductType = lifeProductType;
	}

	public String getProductId() {
		switch (lifeProductType) {
			case PUBLIC_LIFE: {
				return KeyFactorIDConfig.getPublicLifeId();
			}
			case GROUP_LIFE: {
				return KeyFactorIDConfig.getGroupLifeId();
			}
			case SNAKE_BITE: {
				return KeyFactorIDConfig.getSnakeBikeId();
			}
			case SPORT_MAN: {
				return KeyFactorIDConfig.getSportManId();
			}
		}
		return null;
	}

	public String getMonthString() {
		return Utils.getMonthString(month);
	}

	public boolean isContinuePolicy() {
		return continuePolicy;
	}

	public void setContinuePolicy(boolean continuePolicy) {
		this.continuePolicy = continuePolicy;
	}
}
