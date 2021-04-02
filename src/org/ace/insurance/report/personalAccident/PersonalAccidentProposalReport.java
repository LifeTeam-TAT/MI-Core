package org.ace.insurance.report.personalAccident;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.MaritalStatus;

public class PersonalAccidentProposalReport implements ISorter {
	private static final long serialVersionUID = 288430480510991981L;

	private String id;
	private String proposalNo;
	private String insuredPersonName;
	private String addressAndPhoneNo;
	private String ageAndDateOfBirth;
	private MaritalStatus maritalStatus;
	private String occupation;
	private String agentNameAndAgentCode;
	private Date activedProposalStartDate;
	private Date activedProposalEndDate;
	private Double sumInsured;
	private Double premium;
	private String cashReceiptNoAndPaymentDate;
	private String remark;
	
	
	
	public PersonalAccidentProposalReport() {
		
	}


	public PersonalAccidentProposalReport(String id, String proposalNo, String insuredPersonName,
			String addressAndPhoneNo, String ageAndDateOfBirth, MaritalStatus maritalStatus, String occupation,
			String agentNameAndAgentCode, Date activedProposalStartDate, Date activedProposalEndDate, Double sumInsured,
			Double premium, String cashReceiptNoAndPaymentDate, String remark) {
		this.id = id;
		this.proposalNo = proposalNo;
		this.insuredPersonName = insuredPersonName;
		this.addressAndPhoneNo = addressAndPhoneNo;
		this.ageAndDateOfBirth = ageAndDateOfBirth;
		this.maritalStatus = maritalStatus;
		this.occupation = occupation;
		this.agentNameAndAgentCode = agentNameAndAgentCode;
		this.activedProposalStartDate = activedProposalStartDate;
		this.activedProposalEndDate = activedProposalEndDate;
		this.sumInsured = sumInsured;
		this.premium = premium;
		this.cashReceiptNoAndPaymentDate = cashReceiptNoAndPaymentDate;
		this.remark = remark;
	}


	public String getId() {
		return id;
	}


	public String getProposalNo() {
		return proposalNo;
	}


	public String getInsuredPersonName() {
		return insuredPersonName;
	}


	public String getAddressAndPhoneNo() {
		return addressAndPhoneNo;
	}


	public String getAgeAndDateOfBirth() {
		return ageAndDateOfBirth;
	}


	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}


	public String getOccupation() {
		return occupation;
	}


	public String getAgentNameAndAgentCode() {
		return agentNameAndAgentCode;
	}


	public Date getActivedProposalStartDate() {
		return activedProposalStartDate;
	}


	public Date getActivedProposalEndDate() {
		return activedProposalEndDate;
	}


	public Double getSumInsured() {
		return sumInsured;
	}


	public Double getPremium() {
		return premium;
	}

	public String getCashReceiptNoAndPaymentDate() {
		return cashReceiptNoAndPaymentDate;
	}


	public String getRemark() {
		return remark;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}
	
	
}
