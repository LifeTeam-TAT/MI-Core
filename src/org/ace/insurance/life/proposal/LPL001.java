package org.ace.insurance.life.proposal;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.web.common.SaleChannelType;

public class LPL001 implements ISorter {
	private static final long serialVersionUID = 1L;
	private String proposalId;
	private String proposalNo;
	private String agent;
	private String customerName;
	private String branch;
	private double totalPremium;
	private double totalSuminsured;
	private String paymentType;
	private Date submittedDate;
	private SaleChannelType saleChannelType;
	private String saleBank;
	private int unit;
	private String salePoint;

	public LPL001(String id, String proposalNo, String customerName, String agent, String branch, double totalPremium, double totalSuminsured, String paymentType,
			Date submittedDate, SaleChannelType saleChannelType, String saleBank, int unit, String salePoint) {
		this.proposalId = id;
		this.proposalNo = proposalNo;
		this.customerName = customerName;
		this.agent = agent;
		this.branch = branch;
		this.totalPremium = totalPremium;
		this.totalSuminsured = totalSuminsured;
		this.paymentType = paymentType;
		this.submittedDate = submittedDate;
		this.saleChannelType = saleChannelType;
		this.saleBank = saleBank;
		this.unit = unit;
		this.salePoint = salePoint;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public double getTotalSuminsured() {
		return totalSuminsured;
	}

	public void setTotalSuminsured(double totalSuminsured) {
		this.totalSuminsured = totalSuminsured;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public String getSaleBank() {
		return saleBank;
	}

	public void setSaleBank(String saleBank) {
		this.saleBank = saleBank;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public String getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(String salePoint) {
		this.salePoint = salePoint;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}

}
