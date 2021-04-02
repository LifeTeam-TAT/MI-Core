package org.ace.insurance.report.life;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class LifeDailyPremiumIncomeReportDTO {
	private String policyId;
	private String policyNo;
	private String productId;
	private String receiptNo;
	private String insuredPersonName;
	// private List<PolicyInsuredPerson> policyInsuredPersonList;
	private String organizationName;
	private String agentName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String branchId;
	private String branchName;
	private double netPremium;
	private String toTerm;
	private String paymentType;
	private String remark;
	private int personsCount;

	public LifeDailyPremiumIncomeReportDTO() {
	}

	public LifeDailyPremiumIncomeReportDTO(String policyNo, String cashReceiptNo, String insuredPersonName, String organizationName, Date paymentDate, String branchId,
			String branchName, double netPremium, String toTerm, String paymentType, int personsCount) {
		if (insuredPersonName == null) {
			this.insuredPersonName = organizationName;
		} else {
			this.insuredPersonName = insuredPersonName;
		}

		this.policyNo = policyNo;
		// this.productId = productId;
		this.receiptNo = cashReceiptNo;
		this.branchName = branchName;
		this.paymentDate = paymentDate;
		this.branchId = branchId;
		this.branchName = branchName;
		this.netPremium = netPremium;
		this.toTerm = toTerm;
		this.paymentType = paymentType;
		this.personsCount = personsCount;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public double getNetPremium() {
		return netPremium;
	}

	public void setNetPremium(double netPremium) {
		this.netPremium = netPremium;
	}

	public String getToTerm() {
		return toTerm;
	}

	public void setToTerm(String toTerm) {
		this.toTerm = toTerm;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getPersonsCount() {
		return personsCount;
	}

	public void setPersonsCount(int personsCount) {
		this.personsCount = personsCount;
	}

}
