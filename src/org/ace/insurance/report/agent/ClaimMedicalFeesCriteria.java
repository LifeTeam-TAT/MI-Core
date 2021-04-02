package org.ace.insurance.report.agent;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.PolicyReferenceType;

public class ClaimMedicalFeesCriteria {
	private List<PolicyReferenceType> referenceTypeList;
	private Date startDate;
	private Date endDate;
	private String branchId;
	private String claimNo;
	private String currencyCode;
	private String policyNo;
	private String sanctionNo;
	private String hospitalId;
	private Date medicalreportDate;
	private Date informDate;

	public ClaimMedicalFeesCriteria() {
	}

	public List<PolicyReferenceType> getReferenceTypeList() {
		return referenceTypeList;
	}

	public void setReferenceTypeList(List<PolicyReferenceType> referenceTypeList) {
		this.referenceTypeList = referenceTypeList;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public String getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(String hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Date getMedicalreportDate() {
		return medicalreportDate;
	}

	public void setMedicalreportDate(Date medicalreportDate) {
		this.medicalreportDate = medicalreportDate;
	}

	public Date getInformDate() {
		return informDate;
	}

	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}

}
