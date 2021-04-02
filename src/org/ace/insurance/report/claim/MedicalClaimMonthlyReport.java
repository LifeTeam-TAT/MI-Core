package org.ace.insurance.report.claim;

import java.util.Date;

public class MedicalClaimMonthlyReport {
	private String id;
	private String policyNo;
	private Date submittedDate;
	private Double claimAmount;
	private String claimReferenceno;
	private String insuredPersonName;
	private String receiptNo;
	private Date paymentDate;

	public MedicalClaimMonthlyReport() {
	}

	public MedicalClaimMonthlyReport(Date submittedDate, String claimRequestNo, String insuredPersonName, String policyNo, String receiptNo, Date paymentDate, Double claimAmount) {
		this.submittedDate = submittedDate;
		this.claimReferenceno = claimRequestNo;
		this.paymentDate = paymentDate;
		this.policyNo = policyNo;
		this.receiptNo = receiptNo;
		this.insuredPersonName = insuredPersonName;
		this.claimAmount = claimAmount;
	}

	public String getId() {
		return id;
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

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(Double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getClaimReferenceno() {
		return claimReferenceno;
	}

	public void setClaimReferenceno(String claimReferenceno) {
		this.claimReferenceno = claimReferenceno;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
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
