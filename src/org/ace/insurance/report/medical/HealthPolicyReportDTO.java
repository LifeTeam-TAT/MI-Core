package org.ace.insurance.report.medical;

import java.util.Date;

import org.ace.insurance.report.medical.view.HealthPolicyReportView;

public class HealthPolicyReportDTO {
	private String policyNo;
	private String proposalNo;
	private String receiptNo;
	private Date commencementDate;
	private String customerName;
	private String address;
	private String phoneNoAndEmailAddress;
	private int basicUnit;
	private int additionalUnit;
	private int option1Unit;
	private int option2Unit;
	private double premium;
	private String premiumMode;
	private String agentNameAndCodeNo;
	private String remarks;
	private String branch;

	public HealthPolicyReportDTO(HealthPolicyReportView view) {
		this.policyNo = view.getPolicyNo();
		this.proposalNo = view.getProposalNo();
		this.receiptNo = view.getReceiptNo();
		this.commencementDate = view.getCommencementDate();
		this.customerName = view.getCustomerName();
		this.address = view.getAddress();
		this.phoneNoAndEmailAddress = view.getPhoneNoAndEmailAddress();
		this.basicUnit = view.getBasicUnit();
		this.additionalUnit = view.getAdditionalUnit();
		this.option1Unit = view.getOption1Unit();
		this.option2Unit = view.getOption2Unit();
		this.premium = view.getPremium();
		this.premiumMode = view.getPremiumMode();
		this.agentNameAndCodeNo = view.getAgentNameAndCodeNo();
		this.remarks = view.getRemarks();
		this.branch = view.getBranch();
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public Date getCommencementDate() {
		return commencementDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAddress() {
		return address;
	}

	public String getPhoneNoAndEmailAddress() {
		return phoneNoAndEmailAddress;
	}

	public int getBasicUnit() {
		return basicUnit;
	}

	public int getAdditionalUnit() {
		return additionalUnit;
	}

	public int getOption1Unit() {
		return option1Unit;
	}

	public int getOption2Unit() {
		return option2Unit;
	}

	public double getPremium() {
		return premium;
	}

	public String getPremiumMode() {
		return premiumMode;
	}

	public String getAgentNameAndCodeNo() {
		return agentNameAndCodeNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getBranch() {
		return branch;
	}

}
