package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import org.ace.insurance.common.HospitalCase;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFECLAIMPROPOSAL)
@TableGenerator(name = "LIFECLAIMPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIMPROPOSAL_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeClaimProposal.updateCompleteStatus", query = "UPDATE LifeClaimProposal m SET m.complete = :complete WHERE m.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifeClaimProposal implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECLAIMPROPOSAL_GEN")
	private String id;

	private String notificationNo;
	private String claimProposalNo;
	private double totalClaimAmont;
	private boolean complete;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicy lifePolicy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson claimPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEPOINTID", referencedColumnName = "ID")
	private SalesPoints salePoint;

	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date occuranceDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date reportDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYCLAIMID", referencedColumnName = "ID")
	private LifePolicyClaim lifePolicyClaim;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "LIFECLAIMPROPOSALID", referencedColumnName = "ID")
	private List<LifeClaimBeneficiaryPerson> beneficiaryList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "LIFECLAIMPROPOSALID", referencedColumnName = "ID")
	private List<LifeClaimProposalAttachment> attachmentList;

	private String pyaee;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSPITALID", referencedColumnName = "ID")
	private Hospital hospital;

	@Enumerated(EnumType.STRING)
	private HospitalCase hospitalCase;

	private String occurancePlace;

	private double remainPremium;

	@Temporal(TemporalType.TIMESTAMP)
	private Date informDate;
	
	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public LifeClaimProposal() {

	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public String getOccurancePlace() {
		return occurancePlace;
	}

	public void setOccurancePlace(String occurancePlace) {
		this.occurancePlace = occurancePlace;
	}

	public String getId() {
		return id;
	}

	public String getNotificationNo() {
		return notificationNo;
	}

	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}

	public String getClaimProposalNo() {
		return claimProposalNo;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public PolicyInsuredPerson getClaimPerson() {
		return claimPerson;
	}

	public Branch getBranch() {
		return branch;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public Date getOccuranceDate() {
		return occuranceDate;
	}

	public LifePolicyClaim getLifePolicyClaim() {
		return lifePolicyClaim;
	}

	public void setLifePolicyClaim(LifePolicyClaim lifePolicyClaim) {
		this.lifePolicyClaim = lifePolicyClaim;
	}

	public List<LifeClaimBeneficiaryPerson> getBeneficiaryList() {
		return beneficiaryList;
	}

	public List<LifeClaimProposalAttachment> getAttachmentList() {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<LifeClaimProposalAttachment>();
		}
		return attachmentList;
	}

	public int getVersion() {
		return version;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setClaimProposalNo(String claimProposalNo) {
		this.claimProposalNo = claimProposalNo;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public void setClaimPerson(PolicyInsuredPerson claimPerson) {
		this.claimPerson = claimPerson;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setOccuranceDate(Date occuranceDate) {
		this.occuranceDate = occuranceDate;
	}

	public void setBeneficiaryList(List<LifeClaimBeneficiaryPerson> beneficiaryList) {
		this.beneficiaryList = beneficiaryList;
	}

	public void addBeneficiary(LifeClaimBeneficiaryPerson beneficiary) {
		if (beneficiaryList == null) {
			beneficiaryList = new ArrayList<LifeClaimBeneficiaryPerson>();
		}
		beneficiaryList.add(beneficiary);
	}

	public void setAttachmentList(List<LifeClaimProposalAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addAttachment(LifeClaimProposalAttachment attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<LifeClaimProposalAttachment>();
		}
		attachmentList.add(attachment);
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public double getTotalClaimAmont() {
		return totalClaimAmont;
	}

	public void setTotalClaimAmont(double totalClaimAmont) {
		this.totalClaimAmont = totalClaimAmont;
	}

	public SalesPoints getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalesPoints salePoint) {
		this.salePoint = salePoint;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getPyaee() {
		return pyaee;
	}

	public void setPyaee(String pyaee) {
		this.pyaee = pyaee;
	}
	
	

	public double getHospitalClaimAmount() {
		LifeHospitalizedClaim h = null;
		if (lifePolicyClaim instanceof LifeHospitalizedClaim) {
			h = (LifeHospitalizedClaim) lifePolicyClaim;
		}
		return h != null ? h.getHospitalizedAmount() : 0.00;
	}

	public double getDeathClaimAmount() {
		LifeDeathClaim h = null;
		if (lifePolicyClaim instanceof LifeDeathClaim) {
			h = (LifeDeathClaim) lifePolicyClaim;
		}
		return h != null ? h.getDeathClaimAmount() : 0.00;
	}

	public double getRemainPremium() {
		return remainPremium;
	}

	public void setRemainPremium(double remainPremium) {
		this.remainPremium = remainPremium;
	}

	public HospitalCase getHospitalCase() {
		return hospitalCase;
	}

	public void setHospitalCase(HospitalCase hospitalCase) {
		this.hospitalCase = hospitalCase;
	}
	
	
	

	public Date getInformDate() {
		return informDate;
	}

	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((claimPerson == null) ? 0 : claimPerson.hashCode());
		result = prime * result + ((claimProposalNo == null) ? 0 : claimProposalNo.hashCode());
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lifePolicy == null) ? 0 : lifePolicy.hashCode());
		result = prime * result + ((lifePolicyClaim == null) ? 0 : lifePolicyClaim.hashCode());
		result = prime * result + ((notificationNo == null) ? 0 : notificationNo.hashCode());
		result = prime * result + ((occuranceDate == null) ? 0 : occuranceDate.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((pyaee == null) ? 0 : pyaee.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((reportDate == null) ? 0 : reportDate.hashCode());
		result = prime * result + ((salePoint == null) ? 0 : salePoint.hashCode());
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalClaimAmont);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		LifeClaimProposal other = (LifeClaimProposal) obj;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (claimPerson == null) {
			if (other.claimPerson != null)
				return false;
		} else if (!claimPerson.equals(other.claimPerson))
			return false;
		if (claimProposalNo == null) {
			if (other.claimProposalNo != null)
				return false;
		} else if (!claimProposalNo.equals(other.claimProposalNo))
			return false;
		if (complete != other.complete)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lifePolicy == null) {
			if (other.lifePolicy != null)
				return false;
		} else if (!lifePolicy.equals(other.lifePolicy))
			return false;
		if (lifePolicyClaim == null) {
			if (other.lifePolicyClaim != null)
				return false;
		} else if (!lifePolicyClaim.equals(other.lifePolicyClaim))
			return false;
		if (notificationNo == null) {
			if (other.notificationNo != null)
				return false;
		} else if (!notificationNo.equals(other.notificationNo))
			return false;
		if (occuranceDate == null) {
			if (other.occuranceDate != null)
				return false;
		} else if (!occuranceDate.equals(other.occuranceDate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (pyaee == null) {
			if (other.pyaee != null)
				return false;
		} else if (!pyaee.equals(other.pyaee))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (reportDate == null) {
			if (other.reportDate != null)
				return false;
		} else if (!reportDate.equals(other.reportDate))
			return false;
		if (salePoint == null) {
			if (other.salePoint != null)
				return false;
		} else if (!salePoint.equals(other.salePoint))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (Double.doubleToLongBits(totalClaimAmont) != Double.doubleToLongBits(other.totalClaimAmont))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
