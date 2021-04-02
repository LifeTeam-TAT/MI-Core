package org.ace.insurance.report.medical.view.Claim;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_MEDICALCLAIM_PAYMENTREPROT_VIEW)
@ReadOnly
public class MedicalClaimPaymentReportView {
	@Id
	private String claimRequestId;
	private String customerId;
	private String customerName;
	private String nrc;
	private String fatherName;
	private String policyInsuredPersonName;
	private String policyNo;
	private double disabilityAmount;
	private double hospitalizedAmount;
	private double operationAmount;
	private double deathAmount;
	private String receiptNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String branchId;
	private String branch;

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public void setClaimRequestId(String claimRequestId) {
		this.claimRequestId = claimRequestId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

	public void setDeathAmount(double deathAmount) {
		this.deathAmount = deathAmount;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

}
