package org.ace.insurance.life.claim;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.interfaces.IEntity;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.java.component.idgen.service.IDInterceptor;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */

@Entity
@Table(name = TableName.LIFECLAIM_AMOUNT)
@TableGenerator(name = "LIFECLAIMAMOUNT_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIMAMOUNT_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "ClaimAmount.findAll", query = "SELECT c FROM LifeClaimAmount c "),
		@NamedQuery(name = "ClaimAmount.findById", query = "SELECT c FROM LifeClaimAmount c WHERE c.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifeClaimAmount implements Serializable, IEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECLAIMAMOUNT_GEN")
	private String id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYINSUREDPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;

	private String claimRole;

	private double claimAmount;
	private double netAmount;
	private double loanAmount;
	private double loanInterest;
	private double renewelAmount;
	private double renewelInterest;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeClaimAmount() {

	}

	public LifeClaimAmount(PolicyInsuredPerson policyInsuredPerson, String claimRole, double claimAmount, double netAmount, double loanAmount, double loanInterest,
			double renewelAmount, double renewelInterest) {
		this.policyInsuredPerson = policyInsuredPerson;
		this.claimRole = claimRole;
		this.claimAmount = claimAmount;
		this.netAmount = netAmount;
		this.loanAmount = loanAmount;
		this.loanInterest = loanInterest;
		this.renewelAmount = renewelAmount;
		this.renewelInterest = renewelInterest;
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

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public String getClaimRole() {
		return claimRole;
	}

	public void setClaimRole(String claimRole) {
		this.claimRole = claimRole;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(claimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimRole == null) ? 0 : claimRole.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(loanAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(loanInterest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(netAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
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
		LifeClaimAmount other = (LifeClaimAmount) obj;
		if (Double.doubleToLongBits(claimAmount) != Double.doubleToLongBits(other.claimAmount))
			return false;
		if (claimRole == null) {
			if (other.claimRole != null)
				return false;
		} else if (!claimRole.equals(other.claimRole))
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
		if (Double.doubleToLongBits(netAmount) != Double.doubleToLongBits(other.netAmount))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
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
