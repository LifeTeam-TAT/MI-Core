package org.ace.insurance.report.life;

import java.util.Date;

import org.ace.insurance.common.ProposalType;
import org.ace.insurance.system.common.branch.Branch;

public class LifePremiumPaymentCriteria {
	private Date startDate;
	private Date endDate;
	private Branch branch;
	private ProposalType proposalType;
	
	public LifePremiumPaymentCriteria(){}

	public LifePremiumPaymentCriteria(Date startDate, Date endDate, Branch branch, ProposalType proposalType) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.branch = branch;
		this.proposalType = proposalType;
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

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}
	

}
