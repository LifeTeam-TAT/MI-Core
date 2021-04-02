package org.ace.insurance.life.paidUp;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.common.PaidUpClaimStatus;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.java.component.idgen.service.IDInterceptor;
import org.joda.time.Period;

@Entity
@Table(name = TableName.LIFEPAIDUPPROPOSAL)
@TableGenerator(name = "LIFEPAIDUPPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPAIDUPPROPOSAL_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifePaidUpProposal.findAll", query = "SELECT p FROM LifePaidUpProposal p "),
		@NamedQuery(name = "LifePaidUpProposal.findByProposalNo", query = "SELECT p FROM LifePaidUpProposal p WHERE p.proposalNo = :proposalNo"),
		@NamedQuery(name = "LifePaidUpProposal.findByPolicyNo", query = "SELECT p FROM LifePaidUpProposal p WHERE p.policyNo = :policyNo") })
@EntityListeners(IDInterceptor.class)
public class LifePaidUpProposal implements Serializable {
	private static final long serialVersionUID = 6267827803794249138L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPAIDUPPROPOSAL_GEN")
	private String id;

	private String proposalNo;
	private String policyNo;
	private double sumInsured;
	private double receivedPremium;
	private double paidUpAmount;
	private double reqAmount;
	private double realPaidUpAmount;
	private double realPremium;
	private double serviceCharges;
	private boolean isApproved;
	private boolean complete;
	private String rejectedReason;

	@Enumerated(EnumType.STRING)
	private PaidUpClaimStatus claimStatus;

	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyEndDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYID", referencedColumnName = "ID")
	private LifePolicy lifePolicy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMPRODUCTID", referencedColumnName = "ID")
	private ClaimProduct claimProduct;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public LifePaidUpProposal() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getReceivedPremium() {
		return receivedPremium;
	}

	public void setReceivedPremium(double receivedPremium) {
		this.receivedPremium = receivedPremium;
	}

	public double getPaidUpAmount() {
		return paidUpAmount;
	}

	public void setPaidUpAmount(double paidUpAmount) {
		this.paidUpAmount = paidUpAmount;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public LifePolicy getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicy lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public ClaimProduct getClaimProduct() {
		return claimProduct;
	}

	public void setClaimProduct(ClaimProduct claimProduct) {
		this.claimProduct = claimProduct;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public double getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public PaidUpClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(PaidUpClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public double getReqAmount() {
		return reqAmount;
	}

	public void setReqAmount(double reqAmount) {
		this.reqAmount = reqAmount;
	}

	public double getRealPremium() {
		return realPremium;
	}

	public void setRealPremium(double realPremium) {
		this.realPremium = realPremium;
	}

	public double getRealPaidUpAmount() {
		return realPaidUpAmount;
	}

	public void setRealPaidUpAmount(double realPaidUpAmount) {
		this.realPaidUpAmount = realPaidUpAmount;
	}

	public int getPolicyPeriod() {
		int periodMonth = lifePolicy.getPeriodMonth();
		return periodMonth / 12;
	}

	public int getPaymentYear() {
		Period period = Utils.getPeriod(lifePolicy.getActivedPolicyStartDate(), lifePolicy.getCoverageDate(), false, true);
		int paymentYear = period.getYears();
		int paymentMonth = period.getMonths();
		if (paymentMonth >= 6) {
			paymentYear = paymentYear + 1;
		}
		return paymentYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((claimProduct == null) ? 0 : claimProduct.hashCode());
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isApproved ? 1231 : 1237);
		result = prime * result + (complete ? 1231 : 1237);
		result = prime * result + ((lifePolicy == null) ? 0 : lifePolicy.hashCode());
		long temp;
		temp = Double.doubleToLongBits(paidUpAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		temp = Double.doubleToLongBits(realPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(receivedPremium);
		temp = Double.doubleToLongBits(realPaidUpAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((rejectedReason == null) ? 0 : rejectedReason.hashCode());
		temp = Double.doubleToLongBits(reqAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(serviceCharges);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
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
		LifePaidUpProposal other = (LifePaidUpProposal) obj;
		if (activedPolicyEndDate == null) {
			if (other.activedPolicyEndDate != null)
				return false;
		} else if (!activedPolicyEndDate.equals(other.activedPolicyEndDate))
			return false;
		if (activedPolicyStartDate == null) {
			if (other.activedPolicyStartDate != null)
				return false;
		} else if (!activedPolicyStartDate.equals(other.activedPolicyStartDate))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (claimProduct == null) {
			if (other.claimProduct != null)
				return false;
		} else if (!claimProduct.equals(other.claimProduct))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isApproved != other.isApproved)
			return false;
		if (complete != other.complete)
			return false;
		if (lifePolicy == null) {
			if (other.lifePolicy != null)
				return false;
		} else if (!lifePolicy.equals(other.lifePolicy))
			return false;
		if (Double.doubleToLongBits(paidUpAmount) != Double.doubleToLongBits(other.paidUpAmount))
			return false;
		if (Double.doubleToLongBits(realPaidUpAmount) != Double.doubleToLongBits(other.realPaidUpAmount))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (proposalNo == null) {
			if (other.proposalNo != null)
				return false;
		} else if (!proposalNo.equals(other.proposalNo))
			return false;
		if (Double.doubleToLongBits(realPremium) != Double.doubleToLongBits(other.realPremium))
			return false;
		if (Double.doubleToLongBits(receivedPremium) != Double.doubleToLongBits(other.receivedPremium))
			return false;
		if (rejectedReason == null) {
			if (other.rejectedReason != null)
				return false;
		} else if (!rejectedReason.equals(other.rejectedReason))
			return false;
		if (Double.doubleToLongBits(reqAmount) != Double.doubleToLongBits(other.reqAmount))
			return false;
		if (Double.doubleToLongBits(serviceCharges) != Double.doubleToLongBits(other.serviceCharges))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
