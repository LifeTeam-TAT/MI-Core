package org.ace.insurance.filter.life;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.system.common.paymenttype.PaymentType;

public class LPLC001 implements ISorter {
	private String id;
	private String policyNo;
	private String proposalNo;
	private String agent;
	private String customer;
	private String branch;
	private double premium;
	private double sumInsured;
	private PaymentType paymentType;
	private Date submittedDate;

	public LPLC001(String id, String policyNo, String proposalNo,  String agent, String customer, String branch, double premium, double sumInsured,
			PaymentType paymentType, Date submittedDate) {
		this.id = id;
		this.policyNo = policyNo;
		this.proposalNo = proposalNo;
		this.agent = agent;
		this.customer = customer;
		this.branch = branch;
		this.premium = premium;
		this.sumInsured = sumInsured;
		this.paymentType = paymentType;
		this.submittedDate = submittedDate;
	}

	public String getId() {
		return id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public String getAgent() {
		return agent;
	}

	public String getCustomer() {
		return customer;
	}

	public String getBranch() {
		return branch;
	}

	public double getPremium() {
		return premium;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public String getRegistrationNo() {
		return policyNo;
	}
}
