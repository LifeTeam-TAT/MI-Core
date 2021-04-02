package org.ace.insurance.life.surrender;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.web.common.KeyFactorChecker;
import org.ace.java.component.idgen.service.IDInterceptor;

/***************************************************************************************
 * @author PPA
 * @Date 2016-03-03
 * @Version 1.0
 * @Purpose This class serves as the Data Entity Object For Life Surrender
 *          Proposal
 * 
 ***************************************************************************************/

@Entity
@Table(name = TableName.LIFESURRENDERPROPOSAL)
@TableGenerator(name = "LIFESURRENDERPROPOSAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFESURRENDERPROPOSAL_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeSurrenderProposal.findAll", query = "SELECT s FROM LifeSurrenderProposal s "),
		@NamedQuery(name = "LifeSurrenderProposal.findByProposalNo", query = "SELECT s FROM LifeSurrenderProposal s WHERE s.proposalNo = :proposalNo") })
@EntityListeners(IDInterceptor.class)
public class LifeSurrenderProposal implements Serializable {

	private static final long serialVersionUID = 8774597711733653403L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFESURRENDERPROPOSAL_GEN")
	private String id;

	private String proposalNo;
	private String policyNo;
	private double sumInsured;
	private double receivedPremium;
	private double surrenderAmount;

	@Temporal(TemporalType.TIMESTAMP)
	private Date submittedDate;

	private double lifePremium;
	private boolean isApproved;
	private String rejectedReason;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POLICYID", referencedColumnName = "ID")
	private LifePolicy lifePolicy;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMPRODUCTID", referencedColumnName = "ID")
	private ClaimProduct claimProduct;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "PROPOSALID", referencedColumnName = "ID")
	private List<LifeSurrenderKeyFactor> lifeSurrenderKeyFactors;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public LifeSurrenderProposal() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public double getSurrenderAmount() {
		return surrenderAmount;
	}

	public void setSurrenderAmount(double surrenderAmount) {
		this.surrenderAmount = surrenderAmount;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public double getLifePremium() {
		return lifePremium;
	}

	public void setLifePremium(double lifePremium) {
		this.lifePremium = lifePremium;
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

	public List<LifeSurrenderKeyFactor> getLifeSurrenderKeyFactors() {
		return lifeSurrenderKeyFactors;
	}

	public void setLifeSurrenderKeyFactors(List<LifeSurrenderKeyFactor> lifeSurrenderKeyFactors) {
		this.lifeSurrenderKeyFactors = lifeSurrenderKeyFactors;
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

	public double totalReceivedPremium() {
		return receivedPremium + lifePremium;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getPolicyPeriod() {
		String result = null;
		for (LifeSurrenderKeyFactor keyFactor : lifeSurrenderKeyFactors) {
			if (KeyFactorChecker.isPolicyPeriod(keyFactor.getKeyFactor())) {
				result = keyFactor.getValue();
				break;
			}
		}
		return result;
	}

	public String getPersonAge() {
		String result = null;
		for (LifeSurrenderKeyFactor keyFactor : lifeSurrenderKeyFactors) {
			if (KeyFactorChecker.isMedicalAge(keyFactor.getKeyFactor())) {
				result = keyFactor.getValue();
				break;
			}
		}
		return result;
	}

	public String getPaymentYear() {
		String result = null;
		for (LifeSurrenderKeyFactor keyFactor : lifeSurrenderKeyFactors) {
			if (KeyFactorChecker.isPaymentYear(keyFactor.getKeyFactor())) {
				result = keyFactor.getValue();
				break;
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((claimProduct == null) ? 0 : claimProduct.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isApproved ? 1231 : 1237);
		result = prime * result + ((lifePolicy == null) ? 0 : lifePolicy.hashCode());
		long temp;
		temp = Double.doubleToLongBits(lifePremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		temp = Double.doubleToLongBits(receivedPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((rejectedReason == null) ? 0 : rejectedReason.hashCode());
		result = prime * result + ((submittedDate == null) ? 0 : submittedDate.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(surrenderAmount);
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
		LifeSurrenderProposal other = (LifeSurrenderProposal) obj;
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
		if (lifePolicy == null) {
			if (other.lifePolicy != null)
				return false;
		} else if (!lifePolicy.equals(other.lifePolicy))
			return false;
		if (Double.doubleToLongBits(lifePremium) != Double.doubleToLongBits(other.lifePremium))
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
		if (Double.doubleToLongBits(receivedPremium) != Double.doubleToLongBits(other.receivedPremium))
			return false;
		if (rejectedReason == null) {
			if (other.rejectedReason != null)
				return false;
		} else if (!rejectedReason.equals(other.rejectedReason))
			return false;
		if (submittedDate == null) {
			if (other.submittedDate != null)
				return false;
		} else if (!submittedDate.equals(other.submittedDate))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (Double.doubleToLongBits(surrenderAmount) != Double.doubleToLongBits(other.surrenderAmount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
