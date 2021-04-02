package org.ace.insurance.payment;

import java.util.ArrayList;
import java.util.List;

import org.ace.insurance.life.claim.ClaimMedicalFees;

public class TlfData {

	private String receivableDr;
	private String salePointName;
	/*
	 * This fields are required. Use to generate Tlf for premium.
	 */
	private String premiumCodeDr;
	private String premiumCodeCr;
	private String claimCodeDr;
	private String claimCodeCr;
	/*
	 * This fields are mandatory required. Use to generate Tlf for premium by
	 * cheque.
	 */
	private String chequeCodeCr;
	private String claimChequeCodeCr;
	/*
	 * This fields are mandatory required if co-insurance case. Use to generate
	 * Tlf for co-insurance.
	 */
	private String coCodeDr;
	private String coCodeCr;
	/*
	 * This fields are mandatory required if co-insurance case. Use to generate
	 * Tlf for co-insurance.
	 */
	private String agentCodeDr;
	private String agentCodeCr;
	/*
	 * This fields are optional. Use to generate Tlf for service charges.
	 */
	private String servicesCodeDr;
	private String servicesCodeCr;
	/*
	 * This fields are optional. Use to generate Tlf for stamp fee.
	 */
	private String stampCodeDr;
	private String stampCodeCr;

	private String medicalFeesCodeDr;
	private String medicalFeesCodeCr;

	/* Optional. Use to generate Tlf for co commission. */
	private String coCommCodeDr;
	private String coCommCodeCr;

	/* Mandatory . Use to generate Tlf for Claim Outstanding */
	private String claimOutstCodeDr;
	private String claimOutstCodeCr;

	/* This fields are only use for Travel proposal (Express) */
	private String fromDate;
	private String toDate;
	private int noOfExpresss;
	private int noOfPassengers;
	/*
	 * This two field are optional required base on product cover type ( Unit /
	 * sumInsured).
	 */
	private int unit;
	private double sumInsured;

	/*
	 * This field are used to stored policy holder information. May be
	 * organization or customer.
	 */
	private String customerId;
	private String customerName;

	private boolean isRenewal;
	private Payment payment;
	private List<AgentCommission> agentCommissionList;
	private List<ClaimMedicalFees> claimMedicalFeesList;

	public TlfData() {
	}

	public String getPremiumCodeDr() {
		return premiumCodeDr;
	}

	public void setPremiumCodeDr(String premiumCodeDr) {
		this.premiumCodeDr = premiumCodeDr;
	}

	public String getPremiumCodeCr() {
		return premiumCodeCr;
	}

	public void setPremiumCodeCr(String premiumCodeCr) {
		this.premiumCodeCr = premiumCodeCr;
	}

	public String getChequeCodeCr() {
		return chequeCodeCr;
	}

	public void setChequeCodeCr(String chequeCodeCr) {
		this.chequeCodeCr = chequeCodeCr;
	}

	public String getCoCodeDr() {
		return coCodeDr;
	}

	public void setCoCodeDr(String coCodeDr) {
		this.coCodeDr = coCodeDr;
	}

	public String getCoCodeCr() {
		return coCodeCr;
	}

	public void setCoCodeCr(String coCodeCr) {
		this.coCodeCr = coCodeCr;
	}

	public String getAgentCodeDr() {
		return agentCodeDr;
	}

	public void setAgentCodeDr(String agentCodeDr) {
		this.agentCodeDr = agentCodeDr;
	}

	public String getAgentCodeCr() {
		return agentCodeCr;
	}

	public void setAgentCodeCr(String agentCodeCr) {
		this.agentCodeCr = agentCodeCr;
	}

	public String getServicesCodeDr() {
		return servicesCodeDr;
	}

	public void setServicesCodeDr(String servicesCodeDr) {
		this.servicesCodeDr = servicesCodeDr;
	}

	public String getServicesCodeCr() {
		return servicesCodeCr;
	}

	public void setServicesCodeCr(String servicesCodeCr) {
		this.servicesCodeCr = servicesCodeCr;
	}

	public String getStampCodeDr() {
		return stampCodeDr;
	}

	public void setStampCodeDr(String stampCodeDr) {
		this.stampCodeDr = stampCodeDr;
	}

	public String getMedicalFeesCodeDr() {
		return medicalFeesCodeDr;
	}

	public void setMedicalFeesCodeDr(String medicalFeesCodeDr) {
		this.medicalFeesCodeDr = medicalFeesCodeDr;
	}

	public String getMedicalFeesCodeCr() {
		return medicalFeesCodeCr;
	}

	public void setMedicalFeesCodeCr(String medicalFeesCodeCr) {
		this.medicalFeesCodeCr = medicalFeesCodeCr;
	}

	public String getStampCodeCr() {
		return stampCodeCr;
	}

	public void setStampCodeCr(String stampCodeCr) {
		this.stampCodeCr = stampCodeCr;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getNoOfExpresss() {
		return noOfExpresss;
	}

	public void setNoOfExpresss(int noOfExpresss) {
		this.noOfExpresss = noOfExpresss;
	}

	public int getNoOfPassengers() {
		return noOfPassengers;
	}

	public void setNoOfPassengers(int noOfPassengers) {
		this.noOfPassengers = noOfPassengers;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public boolean isRenewal() {
		return isRenewal;
	}

	public void setRenewal(boolean isRenewal) {
		this.isRenewal = isRenewal;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public List<AgentCommission> getAgentCommissionList() {
		return agentCommissionList;
	}

	public void setAgentCommissionList(List<AgentCommission> agentCommissionList) {
		this.agentCommissionList = agentCommissionList;
	}

	public double getPremium() {
		if (isRenewal)
			return payment.getRenewalNetPremium();
		else
			return payment.getNetPremium();
	}

	public String getCoCommCodeCr() {
		return coCommCodeCr;
	}

	public void setCoCommCodeCr(String coCommCodeCr) {
		this.coCommCodeCr = coCommCodeCr;
	}

	public String getCoCommCodeDr() {
		return coCommCodeDr;
	}

	public void setCoCommCodeDr(String coCommCodeDr) {
		this.coCommCodeDr = coCommCodeDr;
	}

	public String getClaimOutstCodeDr() {
		return claimOutstCodeDr;
	}

	public void setClaimOutstCodeDr(String claimOutstCodeDr) {
		this.claimOutstCodeDr = claimOutstCodeDr;
	}

	public String getClaimOutstCodeCr() {
		return claimOutstCodeCr;
	}

	public void setClaimOutstCodeCr(String claimOutstCodeCr) {
		this.claimOutstCodeCr = claimOutstCodeCr;
	}

	public String getReceivableDr() {
		return receivableDr;
	}

	public void setReceivableDr(String receivableDr) {
		this.receivableDr = receivableDr;
	}

	public String getSalePointName() {
		return salePointName;
	}

	public void setSalePointName(String salePointName) {
		this.salePointName = salePointName;
	}

	public String getClaimCodeDr() {
		return claimCodeDr;
	}

	public void setClaimCodeDr(String claimCodeDr) {
		this.claimCodeDr = claimCodeDr;
	}

	public String getClaimCodeCr() {
		return claimCodeCr;
	}

	public void setClaimCodeCr(String claimCodeCr) {
		this.claimCodeCr = claimCodeCr;
	}

	public String getClaimChequeCodeCr() {
		return claimChequeCodeCr;
	}

	public void setClaimChequeCodeCr(String claimChequeCodeCr) {
		this.claimChequeCodeCr = claimChequeCodeCr;
	}

	public List<ClaimMedicalFees> getClaimMedicalFeesList() {
		if (claimMedicalFeesList == null) {
			claimMedicalFeesList = new ArrayList<ClaimMedicalFees>();
		}
		return claimMedicalFeesList;
	}

	public void setClaimMedicalFeesList(List<ClaimMedicalFees> claimMedicalFeesList) {
		this.claimMedicalFeesList = claimMedicalFeesList;
	}

}
