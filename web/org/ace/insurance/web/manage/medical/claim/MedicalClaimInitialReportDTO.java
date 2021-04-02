package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ClaimInitialReporter;
import org.ace.insurance.medical.claim.ClaimInitialReportICD;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.web.common.CommonDTO;

public class MedicalClaimInitialReportDTO extends CommonDTO {
	private Date reportDate;
	private Date hospitalizedStartDate;
	private String CauseofHospitalized;
	private String claimReportNo;
	private Agent agent;
	private Customer referral;
	private String policyNo;
	private MedicalPolicyInsuredPerson policyInsuredPerson;
	private Hospital medicalPlace;
	private ClaimInitialReporter claimInitialReporter;
	private List<ClaimInitialReportICD> claimInitialReportICDList;
	private ClaimStatus claimStatus;

	public MedicalClaimInitialReportDTO() {

	}

	public MedicalPolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(MedicalPolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Customer getReferral() {
		return referral;
	}

	public void setReferral(Customer referral) {
		this.referral = referral;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Hospital getMedicalPlace() {
		return medicalPlace;
	}

	public void setMedicalPlace(Hospital medicalPlace) {
		this.medicalPlace = medicalPlace;
	}

	public Date getHospitalizedStartDate() {
		return hospitalizedStartDate;
	}

	public void setHospitalizedStartDate(Date hospitalizedStartDate) {
		this.hospitalizedStartDate = hospitalizedStartDate;
	}

	public String getCauseofHospitalized() {
		return CauseofHospitalized;
	}

	public void setCauseofHospitalized(String causeofHospitalized) {
		CauseofHospitalized = causeofHospitalized;
	}

	public String getClaimReportNo() {
		return claimReportNo;
	}

	public void setClaimReportNo(String claimReportNo) {
		this.claimReportNo = claimReportNo;
	}

	public ClaimInitialReporter getClaimInitialReporter() {
		if (claimInitialReporter == null) {
			claimInitialReporter = new ClaimInitialReporter();
		}
		return claimInitialReporter;
	}

	public void setClaimInitialReporter(ClaimInitialReporter claimInitialReporter) {
		this.claimInitialReporter = claimInitialReporter;
	}

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		}
		return "";
	}

	public List<ClaimInitialReportICD> getClaimInitialReportICDList() {
		if (claimInitialReportICDList == null) {
			claimInitialReportICDList = new ArrayList<ClaimInitialReportICD>();
		}

		return claimInitialReportICDList;
	}

	public void setClaimInitialReportICDList(List<ClaimInitialReportICD> claimInitialReportICDList) {
		this.claimInitialReportICDList = claimInitialReportICDList;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((CauseofHospitalized == null) ? 0 : CauseofHospitalized.hashCode());
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((claimInitialReportICDList == null) ? 0 : claimInitialReportICDList.hashCode());
		result = prime * result + ((claimInitialReporter == null) ? 0 : claimInitialReporter.hashCode());
		result = prime * result + ((claimReportNo == null) ? 0 : claimReportNo.hashCode());
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((hospitalizedStartDate == null) ? 0 : hospitalizedStartDate.hashCode());
		result = prime * result + ((medicalPlace == null) ? 0 : medicalPlace.hashCode());
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalClaimInitialReportDTO other = (MedicalClaimInitialReportDTO) obj;
		if (CauseofHospitalized == null) {
			if (other.CauseofHospitalized != null)
				return false;
		} else if (!CauseofHospitalized.equals(other.CauseofHospitalized))
			return false;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (claimInitialReportICDList == null) {
			if (other.claimInitialReportICDList != null)
				return false;
		} else if (!claimInitialReportICDList.equals(other.claimInitialReportICDList))
			return false;
		if (claimInitialReporter == null) {
			if (other.claimInitialReporter != null)
				return false;
		} else if (!claimInitialReporter.equals(other.claimInitialReporter))
			return false;
		if (claimReportNo == null) {
			if (other.claimReportNo != null)
				return false;
		} else if (!claimReportNo.equals(other.claimReportNo))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (hospitalizedStartDate == null) {
			if (other.hospitalizedStartDate != null)
				return false;
		} else if (!hospitalizedStartDate.equals(other.hospitalizedStartDate))
			return false;
		if (medicalPlace == null) {
			if (other.medicalPlace != null)
				return false;
		} else if (!medicalPlace.equals(other.medicalPlace))
			return false;
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (referral == null) {
			if (other.referral != null)
				return false;
		} else if (!referral.equals(other.referral))
			return false;
		if (reportDate == null) {
			if (other.reportDate != null)
				return false;
		} else if (!reportDate.equals(other.reportDate))
			return false;
		return true;
	}

}
