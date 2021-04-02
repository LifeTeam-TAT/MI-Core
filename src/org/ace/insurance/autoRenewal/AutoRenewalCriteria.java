package org.ace.insurance.autoRenewal;

import java.util.Date;

import org.ace.insurance.common.InsuranceType;

public class AutoRenewalCriteria {
	private InsuranceType insuranceType;
	private Date policyEndDate;
	private String policyNo;

	public AutoRenewalCriteria() {
		super();
	}

	public AutoRenewalCriteria(InsuranceType insuranceType, Date policyEndDate, String policyNo) {
		super();
		this.insuranceType = insuranceType;
		this.policyEndDate = policyEndDate;
		this.policyNo = policyNo;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public Date getPolicyEndDate() {
		return policyEndDate;
	}

	public void setPolicyEndDate(Date policyEndDate) {
		this.policyEndDate = policyEndDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

}
