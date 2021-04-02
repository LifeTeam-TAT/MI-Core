package org.ace.insurance.life.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.life.policy.PolicyInsuredPersonDTO;

public class LCL001 implements ISorter {
	private String id;
	private String policyId;
	private String policyNo;
	private String insuredPersonName;
	private String insuredPersonId;
	private String claimRole;
	private double sumInsured;
	private double claimAmount;
	private String claimProposalNo;
	private Date startDate;
	private Date endDate;
	private Date occuranceDate;
	private double claimPercentage;
	private List<PolicyInsuredPersonDTO> insuredPersonDTOList;
	private LifeProductType lifeProdutType;

	public LCL001(String id, String claimProposalNo, String policyNo, String claimRole, String insuredPerson, double sumInsured, Date occuranceDate, double claimAmount,
			double claimPercentage) {
		this.id = id;
		this.claimProposalNo = claimProposalNo;
		this.policyNo = policyNo;
		this.claimRole = claimRole;
		this.insuredPersonName = insuredPerson;
		this.sumInsured = sumInsured;
		this.claimAmount = claimAmount;
		this.occuranceDate = occuranceDate;
		this.claimPercentage = claimPercentage;
	}

	public LCL001() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public List<PolicyInsuredPersonDTO> getInsuredPersonDTOList() {
		if (insuredPersonDTOList == null) {
			insuredPersonDTOList = new ArrayList<PolicyInsuredPersonDTO>();
		}
		return insuredPersonDTOList;
	}

	public void setInsuredPersonDTOList(List<PolicyInsuredPersonDTO> insuredPersonDTOList) {
		this.insuredPersonDTOList = insuredPersonDTOList;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public String getClaimRole() {
		return claimRole;
	}

	public void setClaimRole(String claimRole) {
		this.claimRole = claimRole;
	}

	public String getInsuredPersonId() {
		return insuredPersonId;
	}

	public void setInsuredPersonId(String insuredPersonId) {
		this.insuredPersonId = insuredPersonId;
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

	public Date getOccuranceDate() {
		return occuranceDate;
	}

	public void setOccuranceDate(Date occuranceDate) {
		this.occuranceDate = occuranceDate;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getClaimProposalNo() {
		return claimProposalNo;
	}

	public void setClaimProposalNo(String claimProposalNo) {
		this.claimProposalNo = claimProposalNo;
	}

	public double getClaimPercentage() {
		return claimPercentage;
	}

	public void setClaimPercentage(double claimPercentage) {
		this.claimPercentage = claimPercentage;
	}

	public LifeProductType getLifeProdutType() {
		return lifeProdutType;
	}

	public void setLifeProdutType(LifeProductType lifeProdutType) {
		this.lifeProdutType = lifeProdutType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(claimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(claimPercentage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimProposalNo == null) ? 0 : claimProposalNo.hashCode());
		result = prime * result + ((claimRole == null) ? 0 : claimRole.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuredPersonId == null) ? 0 : insuredPersonId.hashCode());
		result = prime * result + ((insuredPersonName == null) ? 0 : insuredPersonName.hashCode());
		result = prime * result + ((occuranceDate == null) ? 0 : occuranceDate.hashCode());
		result = prime * result + ((policyId == null) ? 0 : policyId.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LCL001 other = (LCL001) obj;
		if (Double.doubleToLongBits(claimAmount) != Double.doubleToLongBits(other.claimAmount))
			return false;
		if (Double.doubleToLongBits(claimPercentage) != Double.doubleToLongBits(other.claimPercentage))
			return false;
		if (claimProposalNo == null) {
			if (other.claimProposalNo != null)
				return false;
		} else if (!claimProposalNo.equals(other.claimProposalNo))
			return false;
		if (claimRole == null) {
			if (other.claimRole != null)
				return false;
		} else if (!claimRole.equals(other.claimRole))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuredPersonId == null) {
			if (other.insuredPersonId != null)
				return false;
		} else if (!insuredPersonId.equals(other.insuredPersonId))
			return false;
		if (insuredPersonName == null) {
			if (other.insuredPersonName != null)
				return false;
		} else if (!insuredPersonName.equals(other.insuredPersonName))
			return false;
		if (occuranceDate == null) {
			if (other.occuranceDate != null)
				return false;
		} else if (!occuranceDate.equals(other.occuranceDate))
			return false;
		if (policyId == null) {
			if (other.policyId != null)
				return false;
		} else if (!policyId.equals(other.policyId))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		return true;
	}

}
