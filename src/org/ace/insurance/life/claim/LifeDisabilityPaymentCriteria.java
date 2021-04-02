package org.ace.insurance.life.claim;

import java.util.Date;

public class LifeDisabilityPaymentCriteria {
	private String claimProposalNo;
	private Date startDate;
	private Date endDate;

	public LifeDisabilityPaymentCriteria() {
	}

	public String getClaimProposalNo() {
		return claimProposalNo;
	}

	public void setClaimProposalNo(String claimProposalNo) {
		this.claimProposalNo = claimProposalNo;
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

}
