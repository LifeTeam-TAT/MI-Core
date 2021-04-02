package org.ace.insurance.web.manage.life.claim;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
public class LifeClaimDischargeFormDTO {
	private String policyNo = "";
	private String refundNo = "";
	private Date presentDate;
	private String idNo = "";
	private String fatherName = "";
	private double claimAmount = 0;
	private double loanAmount = 0;
	private double loanInterest = 0;
	private double renewelAmount = 0;
	private double renewelInterest = 0;
	private double netClaimAmount = 0;
	private double serviceCharges = 0;
	private double sumInsured = 0;
	private Date commenmanceDate;
	private Date maturityDate;
	private String beneficiaryName = "";
	private String customerName = "";
	private String insuredPersonName = "";
	private String address = "";
	private SimpleDateFormat format;

	public LifeClaimDischargeFormDTO() {
		format = new SimpleDateFormat("dd-MM-yyyy");
		this.presentDate = new Date();
		// this.presentDate = format.format(new Date());
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public Date getPresentDate() {
		return presentDate;
	}

	public void setPresentDate(Date presentDate) {
		this.presentDate = presentDate;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getRenewelAmount() {
		return renewelAmount;
	}

	public void setRenewelAmount(double renewelAmount) {
		this.renewelAmount = renewelAmount;
	}

	public double getRenewelInterest() {
		return renewelInterest;
	}

	public void setRenewelInterest(double renewelInterest) {
		this.renewelInterest = renewelInterest;
	}

	public double getNetClaimAmount() {
		return netClaimAmount;
	}

	public void setNetClaimAmount(double netClaimAmount) {
		this.netClaimAmount = netClaimAmount;
	}

	public double getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		// this.commenmanceDate = format.format(commenmanceDate);
		this.commenmanceDate = commenmanceDate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		// this.maturityDate = format.format(maturityDate);
		this.maturityDate = maturityDate;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
}
