package org.ace.insurance.report.life;

/**
 * @author NNH
 */
import java.util.List;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;

public class GroupLifeProposalReport implements ISorter {
	
	private String proposalNo;
	private String inPersonGroupCodeNo;
	private String agentNameAndCode;
	private String insuredpersonName;
	private String addressAndPhoneNo;
	private double sumInsured;
	private double basicPremium;
	private String branch;
	public double subTotal;
	public double getSubTotal() {
		return subTotal;
	}

	

	private List<InsuredPersonBeneficiaries> insuredPersonBeneficiariesList;

	public GroupLifeProposalReport() {

	}
	
	public GroupLifeProposalReport(String proposalNo,
			String inPersonGroupCodeNo, String agentNameAndCode,
			String insuredpersonName, String addressAndPhoneNo, double sumInsured,
			double basicPremium, String branch,
			List<InsuredPersonBeneficiaries> insuredPersonBeneficiariesList) {
		
		this.proposalNo = proposalNo;
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
		this.agentNameAndCode = agentNameAndCode;
		this.insuredpersonName = insuredpersonName;
		this.addressAndPhoneNo = addressAndPhoneNo;
		this.sumInsured = sumInsured;
		this.basicPremium = basicPremium;
		this.branch = branch;
		this.insuredPersonBeneficiariesList = insuredPersonBeneficiariesList;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

	
	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getInPersonGroupCodeNo() {
		return inPersonGroupCodeNo;
	}

	public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public String getAgentNameAndCode() {
		return agentNameAndCode;
	}

	public void setAgentNameAndCode(String agentNameAndCode) {
		this.agentNameAndCode = agentNameAndCode;
	}


	public String getInsuredpersonName() {
		return insuredpersonName;
	}

	public void setInsuredpersonName(String insuredpersonName) {
		this.insuredpersonName = insuredpersonName;
	}

	public String getAddressAndPhoneNo() {
		return addressAndPhoneNo;
	}

	public void setAddressAndPhoneNo(String addressAndPhoneNo) {
		this.addressAndPhoneNo = addressAndPhoneNo;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getBasicPremium() {
		return basicPremium;
	}

	public void setBasicPremium(double basicPremium) {
		this.basicPremium = basicPremium;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public List<InsuredPersonBeneficiaries> getInsuredPersonBeneficiariesList() {
		return insuredPersonBeneficiariesList;
	}

	public void setInsuredPersonBeneficiariesList(
			List<InsuredPersonBeneficiaries> insuredPersonBeneficiariesList) {
		this.insuredPersonBeneficiariesList = insuredPersonBeneficiariesList;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}
}
