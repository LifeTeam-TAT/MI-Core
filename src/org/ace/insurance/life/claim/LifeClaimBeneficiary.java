package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IEntity;
import org.ace.insurance.payment.Payment;
import org.ace.java.component.idgen.service.IDInterceptor;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Entity
@Table(name = TableName.LIFECLAIM_BENEFICIARY)
@TableGenerator(name = "LIFECLAIMBENEFICIARY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIMBENEFICIARY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ClaimBeneficiary.findAll", query = "SELECT c FROM LifeClaimBeneficiary c "),
		@NamedQuery(name = "ClaimBeneficiary.findById", query = "SELECT c FROM LifeClaimBeneficiary c WHERE c.id = :id"),
		@NamedQuery(name = "ClaimBeneficiary.findByRefundNo", query = "SELECT c FROM LifeClaimBeneficiary c WHERE c.refundNo = :refundNo") })
@EntityListeners(IDInterceptor.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "BENEFICIARYROLE", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = LifeClaimBeneficiaryRole.BENEFICIARY)
public abstract class LifeClaimBeneficiary implements Serializable, IEntity {

	private static final long serialVersionUID = -5163024273733485323L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECLAIMBENEFICIARY_GEN")
	private String id;

	@Column(name = "BENEFICIARYROLE", insertable = false, updatable = false)
	private String beneficiaryRole;

	@Column(name = "REFUNDNO")
	private String refundNo;

	@Column(name = "CLAIMAMOUNT")
	private double claimAmount;

	@Column(name = "DEFICITPREMIUM")
	private double deficitPremium;

	@Column(name = "LOANAMOUNT")
	private double loanAmount;

	@Column(name = "LOANINTEREST")
	private double loanInterest;

	@Column(name = "RENEWELAMOUNT")
	private double renewelAmount;

	@Column(name = "RENEWELINTEREST")
	private double renewelInterest;

	@Column(name = "APPROVED")
	private boolean approved;

	@Column(name = "NEEDSOMEDOCUMENT")
	private boolean needSomeDocument;

	@Column(name = "REJECTEDREASON")
	private String rejectedReason;

	@Enumerated(value = EnumType.STRING)
	@Column(name = "CLAIMSTATUS")
	ClaimStatus claimStatus;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PAYMENTID", referencedColumnName = "ID")
	private Payment payment;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public LifeClaimBeneficiary() {

	}

	public LifeClaimBeneficiary(Date paymentDate, double claimAmount, double loanAmount, double loanInterest, double renewelAmount, double renewInterest, double netClaimAmount,
			Payment payment) {
		this.claimAmount = claimAmount;
		this.loanAmount = loanAmount;
		this.loanInterest = loanInterest;
		this.renewelAmount = renewelAmount;
		this.renewelInterest = renewInterest;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getBeneficiaryRole() {
		return beneficiaryRole;
	}

	public void setBeneficiaryRole(String beneficiaryRole) {
		this.beneficiaryRole = beneficiaryRole;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getDeficitPremium() {
		return deficitPremium;
	}

	public void setDeficitPremium(double deficitPremium) {
		this.deficitPremium = deficitPremium;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}

	public double getRenewelAmount() {
		return renewelAmount;
	}

	public void setRenewelAmount(double renewelAmount) {
		this.renewelAmount = renewelAmount;
	}

	public double getRenewelInterest() {
		return renewelInterest;
	}

	public void setRenewelInterest(double renewelInterest) {
		this.renewelInterest = renewelInterest;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isClaimInsuredPersonBeneficiary() {
		return LifeClaimBeneficiaryRole.INSUREDPERSONBENEFICIARY.equals(beneficiaryRole);
	}

	public boolean isClaimInsuredPerson() {
		return LifeClaimBeneficiaryRole.INSUREDPERSON.equals(beneficiaryRole);
	}

	public boolean isSuccessor() {
		return LifeClaimBeneficiaryRole.SUCCESSOR.equals(beneficiaryRole);
	}

	public boolean isDeathPerson() {
		return LifeClaimBeneficiaryRole.DEATHPERSON.equals(beneficiaryRole);
	}

	public boolean isDisabilityPerson() {
		return LifeClaimBeneficiaryRole.DISABILITYPERSON.equals(beneficiaryRole);
	}

	public LifeClaimInsuredPersonBeneficiary castClaimInsuredPersonBeneficiary() {
		return (LifeClaimInsuredPersonBeneficiary) this;
	}

	public LifeClaimSuccessor castLifeClaimSuccessor() {
		return (LifeClaimSuccessor) this;
	}

	public LifeClaimDeathPerson castLifeClaimDeathPerson() {
		return (LifeClaimDeathPerson) this;
	}

	public LifeClaimDisabilityPerson castLifeDisabilityPerson() {
		return (LifeClaimDisabilityPerson) this;
	}

	public String getRejectedReason() {
		return rejectedReason;
	}

	public void setRejectedReason(String rejectedReason) {
		this.rejectedReason = rejectedReason;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isNeedSomeDocument() {
		return needSomeDocument;
	}

	public void setNeedSomeDocument(boolean needSomeDocument) {
		this.needSomeDocument = needSomeDocument;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public void update(LifeClaimBeneficiary claimBeneficiary) {
		this.renewelAmount = claimBeneficiary.getRenewelAmount();
		this.renewelInterest = claimBeneficiary.getRenewelInterest();
		this.claimAmount = claimBeneficiary.getClaimAmount();
		this.loanAmount = claimBeneficiary.getLoanAmount();
	}

	public boolean isNew() {
		return this.id == null;
	}

	abstract public String getFullName();

	abstract public String getFullResidentAddress();

	abstract public String getIdNo();

	abstract public String getFatherName();

	public double getNetClaimAmount() {
		return (claimAmount - (loanAmount + loanInterest + renewelAmount + renewelInterest));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + ((beneficiaryRole == null) ? 0 : beneficiaryRole.hashCode());
		long temp;
		temp = Double.doubleToLongBits(claimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		temp = Double.doubleToLongBits(deficitPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(loanAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(loanInterest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (needSomeDocument ? 1231 : 1237);
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((refundNo == null) ? 0 : refundNo.hashCode());
		result = prime * result + ((rejectedReason == null) ? 0 : rejectedReason.hashCode());
		temp = Double.doubleToLongBits(renewelAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(renewelInterest);
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
		LifeClaimBeneficiary other = (LifeClaimBeneficiary) obj;
		if (approved != other.approved)
			return false;
		if (beneficiaryRole == null) {
			if (other.beneficiaryRole != null)
				return false;
		} else if (!beneficiaryRole.equals(other.beneficiaryRole))
			return false;
		if (Double.doubleToLongBits(claimAmount) != Double.doubleToLongBits(other.claimAmount))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (Double.doubleToLongBits(deficitPremium) != Double.doubleToLongBits(other.deficitPremium))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(loanAmount) != Double.doubleToLongBits(other.loanAmount))
			return false;
		if (Double.doubleToLongBits(loanInterest) != Double.doubleToLongBits(other.loanInterest))
			return false;
		if (needSomeDocument != other.needSomeDocument)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (refundNo == null) {
			if (other.refundNo != null)
				return false;
		} else if (!refundNo.equals(other.refundNo))
			return false;
		if (rejectedReason == null) {
			if (other.rejectedReason != null)
				return false;
		} else if (!rejectedReason.equals(other.rejectedReason))
			return false;
		if (Double.doubleToLongBits(renewelAmount) != Double.doubleToLongBits(other.renewelAmount))
			return false;
		if (Double.doubleToLongBits(renewelInterest) != Double.doubleToLongBits(other.renewelInterest))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
