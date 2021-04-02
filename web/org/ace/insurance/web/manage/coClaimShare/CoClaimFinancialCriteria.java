package org.ace.insurance.web.manage.coClaimShare;

import java.util.Date;

import org.ace.insurance.common.CoinsuranceType;
import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;

public class CoClaimFinancialCriteria {

	private InsuranceType insuranceType;
	private CoinsuranceType coinsuranceType;
	private CoinsuranceCompany coinsuranceCompany;
	private String claimNo;
	private String policyNo;
	private Date startDate;
	private Date endDate;

	public CoClaimFinancialCriteria() {

	}

	public CoClaimFinancialCriteria(InsuranceType insuranceType, CoinsuranceType coinsuranceType, CoinsuranceCompany coinsuranceCompany, String claimNo, String policyNo,
			Date startDate, Date endDate) {
		super();
		this.insuranceType = insuranceType;
		this.coinsuranceType = coinsuranceType;
		this.coinsuranceCompany = coinsuranceCompany;
		this.claimNo = claimNo;
		this.policyNo = policyNo;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public CoinsuranceType getCoinsuranceType() {
		return coinsuranceType;
	}

	public void setCoinsuranceType(CoinsuranceType coinsuranceType) {
		this.coinsuranceType = coinsuranceType;
	}

	public CoinsuranceCompany getCoinsuranceCompany() {
		return coinsuranceCompany;
	}

	public void setCoinsuranceCompany(CoinsuranceCompany coinsuranceCompany) {
		this.coinsuranceCompany = coinsuranceCompany;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

}
