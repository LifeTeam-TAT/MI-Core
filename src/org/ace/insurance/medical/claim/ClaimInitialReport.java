package org.ace.insurance.medical.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.ClaimInitialReporter;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;

import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CLAIM_INITIAL_REPORT)
@TableGenerator(name = "CLAIM_INITIAL_REPORT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CLAIM_INITIAL_REPORT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ClaimInitialReport.findAll", query = "SELECT r FROM ClaimInitialReport r Order by r.claimReportNo"),
		@NamedQuery(name = "ClaimInitialReport.findAllActiveClaim", query = "SELECT r FROM ClaimInitialReport r  Order by r.claimReportNo"),
		@NamedQuery(name = "ClaimInitialReport.findById", query = "SELECT r FROM ClaimInitialReport r WHERE r.id = :id"), })
@EntityListeners(IDInterceptor.class)
public class ClaimInitialReport implements Serializable {
	private static final long serialVersionUID = -6982490830051621004L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CLAIM_INITIAL_REPORT_GEN")
	private String id;

	private String claimReportNo;

	@Column(name = "CAUSEOFHOSPITALIZED")
	private String causeofHospitalized;

	private String policyNo;

	@Column(name = "REPORT_DATE")
	@Temporal(TemporalType.DATE)
	private Date reportDate;

	@Column(name = "HOSPITALIZED_START_DATE")
	@Temporal(TemporalType.DATE)
	private Date hospitalizedStartDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFERRALID", referencedColumnName = "ID")
	private Customer referral;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AGENTID", referencedColumnName = "ID")
	private Agent agent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYINSUREDPERSON", referencedColumnName = "ID")
	private MedicalPolicyInsuredPerson policyInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSP_PLACE_ID", referencedColumnName = "ID")
	private Hospital hospitalPlace;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CLAIMSTATUS")
	private ClaimStatus claimStatus;

	@Embedded
	private ClaimInitialReporter claimInitialReporter;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "claimInitialReport", orphanRemoval = true)
	private List<ClaimInitialReportICD> claimInitialReportICDList;

	public ClaimInitialReport() {

	}

	public MedicalPolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(MedicalPolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCauseofHospitalized() {
		return causeofHospitalized;
	}

	public void setCauseofHospitalized(String causeofHospitalized) {
		this.causeofHospitalized = causeofHospitalized;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public Date getHospitalizedStartDate() {
		return hospitalizedStartDate;
	}

	public void setHospitalizedStartDate(Date hospitalizedStartDate) {
		this.hospitalizedStartDate = hospitalizedStartDate;
	}

	public Customer getReferral() {
		return referral;
	}

	public void setReferral(Customer referral) {
		this.referral = referral;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
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

	public Hospital getMedicalPlace() {
		return hospitalPlace;
	}

	public void setMedicalPlace(Hospital hospitalPlace) {
		this.hospitalPlace = hospitalPlace;
	}

	public ClaimInitialReporter getClaimInitialReporter() {
		if (claimInitialReporter == null) {
			claimInitialReporter = new ClaimInitialReporter();
		}
		return claimInitialReporter;
	}

	public void addClaimInitialReportICD(ClaimInitialReportICD claimInitialReportICD) {
		if (!getClaimInitialReportICDList().contains(claimInitialReportICD)) {
			getClaimInitialReportICDList().add(claimInitialReportICD);
		}
	}

	public void setClaimInitialReporter(ClaimInitialReporter claimInitialReporter) {
		this.claimInitialReporter = claimInitialReporter;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getSalePersonName() {
		if (agent != null) {
			return agent.getFullName();
		} else if (referral != null) {
			return referral.getFullName();
		} else {
			return "";
		}
	}

	public String getClaimReportNo() {
		return claimReportNo;
	}

	public void setClaimReportNo(String claimReportNo) {
		this.claimReportNo = claimReportNo;
	}

	public boolean isApplied() {
		if (ClaimStatus.INITIAL_INFORM.equals(claimStatus)) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agent == null) ? 0 : agent.hashCode());
		result = prime * result + ((causeofHospitalized == null) ? 0 : causeofHospitalized.hashCode());
		result = prime * result + ((claimInitialReporter == null) ? 0 : claimInitialReporter.hashCode());
		result = prime * result + ((claimReportNo == null) ? 0 : claimReportNo.hashCode());
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((hospitalPlace == null) ? 0 : hospitalPlace.hashCode());
		result = prime * result + ((hospitalizedStartDate == null) ? 0 : hospitalizedStartDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((referral == null) ? 0 : referral.hashCode());
		result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
		result = prime * result + version;
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
		ClaimInitialReport other = (ClaimInitialReport) obj;
		if (agent == null) {
			if (other.agent != null)
				return false;
		} else if (!agent.equals(other.agent))
			return false;
		if (causeofHospitalized == null) {
			if (other.causeofHospitalized != null)
				return false;
		} else if (!causeofHospitalized.equals(other.causeofHospitalized))
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
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (hospitalPlace == null) {
			if (other.hospitalPlace != null)
				return false;
		} else if (!hospitalPlace.equals(other.hospitalPlace))
			return false;
		if (hospitalizedStartDate == null) {
			if (other.hospitalizedStartDate != null)
				return false;
		} else if (!hospitalizedStartDate.equals(other.hospitalizedStartDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (version != other.version)
			return false;
		return true;
	}

}
