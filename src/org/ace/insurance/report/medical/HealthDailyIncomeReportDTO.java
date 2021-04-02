package org.ace.insurance.report.medical;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.report.medical.view.Claim.MedicalClaimInitialReportView;
import org.ace.insurance.report.medical.view.Claim.MedicalClaimPaymentReportView;

public class HealthDailyIncomeReportDTO {
	private String policyNo;
	private String cashReceiptNo;
	private String customerName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private int noOfInsurance;
	private double premium;
	private String branch;

	private String claimRequestId;
	private String nrc;
	private String fatherName;
	private String policyInsuredPersonName;
	private double disabilityAmount;
	private double hospitalizedAmount;
	private double operationAmount;
	private double deathAmount;
	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;
	private String branchId;

	public HealthDailyIncomeReportDTO() {
	}

	public HealthDailyIncomeReportDTO(MedicalClaimInitialReportView view) {
		this.policyNo = view.getPolicyNo();
		this.customerName = view.getCustomerName();
		this.branch = view.getBranch();
		this.claimRequestId = view.getClaimRequestId();
		this.nrc = view.getNrc();
		this.fatherName = view.getFatherName();
		this.policyInsuredPersonName = view.getPolicyInsuredPersonName();
		this.disabilityAmount = view.getDisabilityAmount();
		this.hospitalizedAmount = view.getHospitalizedAmount();
		this.operationAmount = view.getOperationAmount();
		this.deathAmount = view.getDeathAmount();
		this.submittedDate = view.getSubmittedDate();
		this.branchId = view.getBranchId();
	}

	public HealthDailyIncomeReportDTO(MedicalClaimPaymentReportView view) {
		this.policyNo = view.getPolicyNo();
		this.customerName = view.getCustomerName();
		this.branch = view.getBranch();
		this.claimRequestId = view.getClaimRequestId();
		this.nrc = view.getNrc();
		this.fatherName = view.getFatherName();
		this.policyInsuredPersonName = view.getPolicyInsuredPersonName();
		this.disabilityAmount = view.getDisabilityAmount();
		this.hospitalizedAmount = view.getHospitalizedAmount();
		this.operationAmount = view.getOperationAmount();
		this.deathAmount = view.getDeathAmount();
		this.submittedDate = view.getPaymentDate();
		this.cashReceiptNo = view.getReceiptNo();
		this.branchId = view.getBranchId();
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getNoOfInsurance() {
		return noOfInsurance;
	}

	public void setNoOfInsurance(int noOfInsurance) {
		this.noOfInsurance = noOfInsurance;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public void setClaimRequestId(String claimRequestId) {
		this.claimRequestId = claimRequestId;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getPolicyInsuredPersonName() {
		return policyInsuredPersonName;
	}

	public void setPolicyInsuredPersonName(String policyInsuredPersonName) {
		this.policyInsuredPersonName = policyInsuredPersonName;
	}

	public double getDisabilityAmount() {
		return disabilityAmount;
	}

	public void setDisabilityAmount(double disabilityAmount) {
		this.disabilityAmount = disabilityAmount;
	}

	public double getHospitalizedAmount() {
		return hospitalizedAmount;
	}

	public void setHospitalizedAmount(double hospitalizedAmount) {
		this.hospitalizedAmount = hospitalizedAmount;
	}

	public double getOperationAmount() {
		return operationAmount;
	}

	public void setOperationAmount(double operationAmount) {
		this.operationAmount = operationAmount;
	}

	public double getDeathAmount() {
		return deathAmount;
	}

	public double getTotalClaimAmount() {
		return hospitalizedAmount + deathAmount + operationAmount + disabilityAmount;
	}

	public void setDeathAmount(double deathAmount) {
		this.deathAmount = deathAmount;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}
