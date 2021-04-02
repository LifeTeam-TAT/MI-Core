package org.ace.insurance.life.paidUp;

import org.ace.insurance.life.policy.LifePolicy;

public class LifePaidUpProposalDTO {
	private String proposalNo;
	private double sumInsured;
	private double receivedPremium;
	private double paidUpAmount;
	private double reqAmount;
	private double realPremium;
	private double serviceCharges;
	private boolean isApproved;
	private LifePolicy lifePolicy;
}
