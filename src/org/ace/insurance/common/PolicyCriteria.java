package org.ace.insurance.common;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.ace.insurance.system.common.branch.BRA001;

public class PolicyCriteria {
	private String criteriaValue;
	private PolicyCriteriaItems policyCriteria;
	private String product;
	private Date fromDate;
	private Date toDate;
	private ReferenceType referenceType;
	private boolean isEndorse;
	private BRA001 branch;

	public PolicyCriteria() {

	}

	public PolicyCriteria(String criteriaValue, PolicyCriteriaItems policyCriteria, ReferenceType referenceType) {
		this.criteriaValue = criteriaValue;
		this.policyCriteria = policyCriteria;
		this.referenceType = referenceType;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public PolicyCriteriaItems getPolicyCriteria() {
		return policyCriteria;
	}

	public void setPolicyCriteria(PolicyCriteriaItems policyCriteria) {
		this.policyCriteria = policyCriteria;
	}

	public List<PolicyCriteriaItems> getPolicyCriteriaItemList() {
		return Arrays.asList(PolicyCriteriaItems.POLICYNO, PolicyCriteriaItems.CUSTOMERNAME, PolicyCriteriaItems.ORGANIZATIONNAME);
	}

	public List<PolicyCriteriaItems> getMotorPolicyCriteriaItemList() {
		return Arrays.asList(PolicyCriteriaItems.POLICYNO, PolicyCriteriaItems.REGISTRATION_NO, PolicyCriteriaItems.CUSTOMERNAME, PolicyCriteriaItems.ORGANIZATIONNAME);
	}

	public List<PolicyCriteriaItems> getLifePolicyCriteriaItemList() {
		return Arrays.asList(PolicyCriteriaItems.POLICYNO, PolicyCriteriaItems.CUSTOMERNAME, PolicyCriteriaItems.ORGANIZATIONNAME, PolicyCriteriaItems.INSUREDPERSON_NAME,
				PolicyCriteriaItems.NRC_NO);
	}

	public List<String> getLifeProductList() {
		return Arrays.asList("PUBLIC LIFE", "GROUP LIFE", "PERSONAL ACCIDENT", "SHORT TERM ENDOWMENT LIFE ");
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public boolean isEndorse() {
		return isEndorse;
	}

	public void setEndorse(boolean isEndorse) {
		this.isEndorse = isEndorse;
	}

	public BRA001 getBranch() {
		return branch;
	}

	public void setBranch(BRA001 branch) {
		this.branch = branch;
	}

}
