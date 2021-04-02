package org.ace.insurance.web.manage.life.billcollection;

import java.util.Calendar;
import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.system.common.paymenttype.PaymentType;
import org.ace.insurance.web.common.SaleChannelType;

public class BillCollectionDTO implements ISorter {

	private static final long serialVersionUID = -7589756160225172326L;

	private Date startDate;
	private Date endDate;
	private Date coverageDate;
	private Date paidUpDate;
	private PaymentType paymentType;
	private String agentName;

	private String policyId;
	private String policyNo;
	private String insuredName;
	private String idNo;
	private String customerAddress;
	private String remark;
	private String customerName;

	private SaleChannelType saleChannelType;

	private int keyCount;
	private int paymentTimes;
	private int lastPaymentTerm;
	private double rate;

	private double sumInsured;
	private double basicTermPremium;
	private double addOnTermPremium;
	private double totalNcbTermPremium;
	private double termSpecialDiscount;
	private double loanInterest;
	private double renewalInterest;
	private double serviceCharges;
	private double refund;
	private double paidUpAmount;
	private double realPaidUpAmount;
	private double receivedPremium;
	private double addOnPremium;
	private double ncbPremium;
	private double discountPercent;
	private PolicyReferenceType referenceType;
	private double extraAmount;

	public BillCollectionDTO() {
	}

	/* Life, Medical, Fire Bill Collection */
	public BillCollectionDTO(String policyId, String policyNo, String initialId, Name insuredName, String orgName, String idNo, Date coverageDate, Date endDate,
			PaymentType paymentType, int lastPaymentTerm, double sumInsured, double basicTermPremium, double addOnTermPremium, double totalNcbTermPremium, double discountPercent) {
		this.policyId = policyId;
		this.policyNo = policyNo;
		this.insuredName = initialId != null ? initialId + insuredName.getFullName() : orgName;
		this.idNo = idNo;
		this.paymentTimes = 1;
		this.startDate = coverageDate;
		this.endDate = endDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(coverageDate);
		calendar.add(Calendar.MONTH, paymentType.getMonth() * paymentTimes);
		this.coverageDate = calendar.getTime();
		this.paymentType = paymentType;
		this.lastPaymentTerm = lastPaymentTerm + 1;
		this.sumInsured = sumInsured;
		this.basicTermPremium = basicTermPremium;
		this.addOnTermPremium = addOnTermPremium;
		this.totalNcbTermPremium = totalNcbTermPremium;
		this.discountPercent = discountPercent;
		this.termSpecialDiscount = Utils.getPercentOf(discountPercent, basicTermPremium);
	}

	/* Paid Up Bill Collection */
	public BillCollectionDTO(String policyId, String policyNo, String insuredName, String idNo, Date startDate, Date endDate, PaymentType paymentType, int paymentTimes,
			int lastPaymentTerm, double basicTermPremium, double loanInterest, double renewalInterest, double serviceCharges, double paidUpAmount, double realPaidUpAmount,
			double receivedPremium, Date paidUpDate) {
		this.policyId = policyId;
		this.policyNo = policyNo;
		this.insuredName = insuredName;
		this.idNo = idNo;
		this.startDate = endDate;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.MONTH, paymentType.getMonth() * paymentTimes);
		this.endDate = calendar.getTime();
		this.paymentType = paymentType;
		this.paymentTimes = paymentTimes;
		this.lastPaymentTerm = lastPaymentTerm;
		this.basicTermPremium = basicTermPremium;
		this.loanInterest = loanInterest;
		this.renewalInterest = renewalInterest;
		this.serviceCharges = serviceCharges;
		this.paidUpAmount = paidUpAmount;
		this.realPaidUpAmount = realPaidUpAmount;
		this.receivedPremium = receivedPremium;
		this.paidUpDate = paidUpDate;
	}

	public double getPaidUpAmount() {
		return paidUpAmount;
	}

	public void setPaidUpAmount(double paidUpAmount) {
		this.paidUpAmount = paidUpAmount;
	}

	public double getRealPaidUpAmount() {
		return realPaidUpAmount;
	}

	public Date getPaidUpDate() {
		return paidUpDate;
	}

	public void setPaidUpDate(Date paidUpDate) {
		this.paidUpDate = paidUpDate;
	}

	public void setRealPaidUpAmount(double realPaidUpAmount) {
		this.realPaidUpAmount = realPaidUpAmount;
	}

	public double getReceivedPremium() {
		return receivedPremium;
	}

	public void setReceivedPremium(double receivedPremium) {
		this.receivedPremium = receivedPremium;
	}

	public double getAddOnPremium() {
		return addOnPremium;
	}

	public void setAddOnPremium(double addOnPremium) {
		this.addOnPremium = addOnPremium;
	}

	public double getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(double discountPercent) {
		this.discountPercent = discountPercent;
	}

	public double getNcbPremium() {
		return ncbPremium;
	}

	public void setNcbPremium(double ncbPremium) {
		this.ncbPremium = ncbPremium;
	}

	public int getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(int keyCount) {
		this.keyCount = keyCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCoverageDate() {
		return coverageDate;
	}

	public void setCoverageDate(Date coverageDate) {
		this.coverageDate = coverageDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public int getPaymentTimes() {
		return paymentTimes;
	}

	public void setPaymentTimes(int paymentTimes) {
		this.paymentTimes = paymentTimes;
	}

	public int getLastPaymentTerm() {
		return lastPaymentTerm;
	}

	public void setLastPaymentTerm(int lastPaymentTerm) {
		this.lastPaymentTerm = lastPaymentTerm;
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

	public double getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public double getRefund() {
		return refund;
	}

	public void setRefund(double refund) {
		this.refund = refund;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public double getTotalAmount() {
		return (getTotalTermPremium() * paymentTimes) + serviceCharges + loanInterest + renewalInterest - refund - extraAmount;
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

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public double getAddOnTermPremium() {
		return addOnTermPremium;
	}

	public void setAddOnTermPremium(double addOnTermPremium) {
		this.addOnTermPremium = addOnTermPremium;
	}

	public double getTotalNcbTermPremium() {
		return totalNcbTermPremium;
	}

	public void setTotalNcbTermPremium(double totalNcbTermPremium) {
		this.totalNcbTermPremium = totalNcbTermPremium;
	}

	public double getTermSpecialDiscount() {
		return termSpecialDiscount;
	}

	public void setTermSpecialDiscount(double termSpecialDiscount) {
		this.termSpecialDiscount = termSpecialDiscount;
	}

	public double getTotalTermPremium() {
		return (basicTermPremium + addOnTermPremium) - (totalNcbTermPremium + termSpecialDiscount);
	}
	/*
	 * public double getTotalAmount() { return (getTotalTermPremium() *
	 * paymentTimes) + serviceCharges + loanInterest + renewalInterest - refund;
	 * }
	 */

	public double getTotalPaidUpAmount() {
		return realPaidUpAmount - serviceCharges;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

	/**
	 * @return the extraAmount
	 */
	public double getExtraAmount() {
		return extraAmount;
	}

	/**
	 * @param extraAmount
	 *            the extraAmount to set
	 */
	public void setExtraAmount(double extraAmount) {
		this.extraAmount = extraAmount;
	}

}
