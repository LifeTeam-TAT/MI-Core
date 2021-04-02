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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFECLAIMBENEFICIARYPERSON)
@TableGenerator(name = "LIFECLAIMBENEFICIARYPERSON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIMBENEFICIARYPERSON_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class LifeClaimBeneficiaryPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECLAIMBENEFICIARYPERSON_GEN")
	private String id;

	private String beneficiaryNo;

	private String name;

	private String nrc;

	private float percentage;

	private double hospitalizedAmount;

	private double deathClaimAmount;

	private double disabilityAmount;

	private String beneficiaryRole;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICY_INSUREDPERSON_ID", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICY_BENEFICIARY_ID", referencedColumnName = "ID")
	private PolicyInsuredPersonBeneficiaries beneficiaryPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationShip;

	@Version
	private int version;

	@Embedded
	private UserRecorder recorder;

	public LifeClaimBeneficiaryPerson() {

	}

	public String getId() {
		return id;
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public String getName() {
		return name;
	}

	public String getNrc() {
		return nrc;
	}

	public float getPercentage() {
		return percentage;
	}

	public double getHospitalizedAmount() {
		return hospitalizedAmount;
	}

	public double getDeathClaimAmount() {
		return deathClaimAmount;
	}

	public double getDisabilityAmount() {
		return disabilityAmount;
	}

	public String getBeneficiaryRole() {
		return beneficiaryRole;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public PolicyInsuredPersonBeneficiaries getBeneficiaryPerson() {
		return beneficiaryPerson;
	}

	public RelationShip getRelationShip() {
		return relationShip;
	}

	public int getVersion() {
		return version;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public void setHospitalizedAmount(double hospitalizedAmount) {
		this.hospitalizedAmount = hospitalizedAmount;
	}

	public void setDeathClaimAmount(double deathClaimAmount) {
		this.deathClaimAmount = deathClaimAmount;
	}

	public void setDisabilityAmount(double disabilityAmount) {
		this.disabilityAmount = disabilityAmount;
	}

	public void setBeneficiaryRole(String beneficiaryRole) {
		this.beneficiaryRole = beneficiaryRole;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public void setBeneficiaryPerson(PolicyInsuredPersonBeneficiaries beneficiaryPerson) {
		this.beneficiaryPerson = beneficiaryPerson;
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((beneficiaryPerson == null) ? 0 : beneficiaryPerson.hashCode());
		result = prime * result + ((beneficiaryRole == null) ? 0 : beneficiaryRole.hashCode());
		long temp;
		temp = Double.doubleToLongBits(deathClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(disabilityAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(hospitalizedAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((nrc == null) ? 0 : nrc.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		result = prime * result + ((relationShip == null) ? 0 : relationShip.hashCode());
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
		LifeClaimBeneficiaryPerson other = (LifeClaimBeneficiaryPerson) obj;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (beneficiaryPerson == null) {
			if (other.beneficiaryPerson != null)
				return false;
		} else if (!beneficiaryPerson.equals(other.beneficiaryPerson))
			return false;
		if (beneficiaryRole == null) {
			if (other.beneficiaryRole != null)
				return false;
		} else if (!beneficiaryRole.equals(other.beneficiaryRole))
			return false;
		if (Double.doubleToLongBits(deathClaimAmount) != Double.doubleToLongBits(other.deathClaimAmount))
			return false;
		if (Double.doubleToLongBits(disabilityAmount) != Double.doubleToLongBits(other.disabilityAmount))
			return false;
		if (Double.doubleToLongBits(hospitalizedAmount) != Double.doubleToLongBits(other.hospitalizedAmount))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nrc == null) {
			if (other.nrc != null)
				return false;
		} else if (!nrc.equals(other.nrc))
			return false;
		if (Float.floatToIntBits(percentage) != Float.floatToIntBits(other.percentage))
			return false;
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (relationShip == null) {
			if (other.relationShip != null)
				return false;
		} else if (!relationShip.equals(other.relationShip))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
