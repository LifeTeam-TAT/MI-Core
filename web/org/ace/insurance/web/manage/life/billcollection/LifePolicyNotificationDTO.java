package org.ace.insurance.web.manage.life.billcollection;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.interfaces.IDataModel;

public class LifePolicyNotificationDTO implements ISorter, IDataModel {
	private static final long serialVersionUID = 1L;
	private String id;
	private String policyNo;
	private String customerName;
	private String idNo;
	private String paymentType;
	private int paymentTerm;
	private double basicTermPremium;
	private double loanInterest;
	private double renewalInterest;
	private double refund;
	private double totalAmout;
	private Date activedPolicyStartDate;
	private Date activedPolicyEndDate;
	private Date coverageDate;

	public LifePolicyNotificationDTO() {
	}

	public LifePolicyNotificationDTO(String id, String policyNo, String customerName, String idNo, String paymentType, int paymentTerm, double basicTermPremium,
			Date activedPolicyStartDate, Date activedPolicyEndDate, Date coverageDate) {
		this.id = id;
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.idNo = idNo;
		this.paymentType = paymentType;
		this.paymentTerm = paymentTerm;
		this.basicTermPremium = basicTermPremium;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.activedPolicyEndDate = activedPolicyEndDate;
		this.coverageDate = coverageDate;
	}

	public LifePolicyNotificationDTO(String id, String policyNo, String customerName, String idNo, String paymentType, int paymentTerm, double basicTermPremium,
			Date activedPolicyStartDate, Date activedPolicyEndDate, Date coverageDate, double refund) {
		this.id = id;
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.idNo = idNo;
		this.paymentType = paymentType;
		this.paymentTerm = paymentTerm;
		this.basicTermPremium = basicTermPremium;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.activedPolicyEndDate = activedPolicyEndDate;
		this.coverageDate = coverageDate;
		this.refund = refund;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public int getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(int paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public double getBasicTermPremium() {
		return basicTermPremium;
	}

	public void setBasicTermPremium(double basicTermPremium) {
		this.basicTermPremium = basicTermPremium;
	}

	public double getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}

	public double getRenewalInterest() {
		return renewalInterest;
	}

	public void setRenewalInterest(double renewalInterest) {
		this.renewalInterest = renewalInterest;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public double getTotalAmout() {
		return totalAmout;
	}

	public void setTotalAmout(double totalAmout) {
		this.totalAmout = basicTermPremium - refund;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public Date getCoverageDate() {
		return coverageDate;
	}

	public void setCoverageDate(Date coverageDate) {
		this.coverageDate = coverageDate;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

	@Override
	public String getId() {
		return policyNo;
	}

}
