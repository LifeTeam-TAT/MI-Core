package org.ace.insurance.life.policy;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policyHistory.PolicyInsuredPersonAddonHistory;
import org.ace.insurance.life.proposal.InsuredPersonAddon;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.insurance.web.manage.life.proposal.InsuredPersonAddOnDTO;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSONADDON)
@TableGenerator(name = "LPOLINSUREDPERSONADDON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSUREDPERSONADDON_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "PolicyInsuredPersonAddon.findAll", query = "SELECT p FROM PolicyInsuredPersonAddon p "),
		@NamedQuery(name = "PolicyInsuredPersonAddon.findById", query = "SELECT p FROM PolicyInsuredPersonAddon p WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class PolicyInsuredPersonAddon {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSUREDPERSONADDON_GEN")
	private String id;

	private double premium;
	private double sumInsured;
	private double premiumRate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTADDONID", referencedColumnName = "ID")
	private AddOn addOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYINSUREDPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	@Transient
	private boolean include;

	public PolicyInsuredPersonAddon() {
	}

	public PolicyInsuredPersonAddon(InsuredPersonAddon addOn) {
		this.premium = addOn.getProposedPremium();
		this.sumInsured = addOn.getProposedSumInsured();
		this.premiumRate = addOn.getPremiumRate();
		this.addOn = addOn.getAddOn();
	}

	public PolicyInsuredPersonAddon(PolicyInsuredPersonAddonHistory addOn) {
		this.premium = addOn.getPremium();
		this.sumInsured = addOn.getSumInsured();
		this.premiumRate = addOn.getPremiumRate();
		this.addOn = addOn.getAddOn();
	}

	public PolicyInsuredPersonAddon(AddOn addOn, double addOnSumInsured) {
		this.addOn = addOn;
		this.sumInsured = addOnSumInsured;
	}

	public PolicyInsuredPersonAddon(AddOn addOn) {
		this.addOn = addOn;
		this.include = addOn.isCompulsory();
	}

	public PolicyInsuredPersonAddon(InsuredPersonAddOnDTO dto) {
		this.sumInsured = dto.getAddOnSumInsured();
		this.addOn = dto.getAddOn();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void overwriteId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(boolean include) {
		this.include = include;
	}

	public PolicyInsuredPerson getPolicyInsuredPersonInfo() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPersonInfo(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(premiumRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
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
		PolicyInsuredPersonAddon other = (PolicyInsuredPersonAddon) obj;
		if (addOn == null) {
			if (other.addOn != null)
				return false;
		} else if (!addOn.equals(other.addOn))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (Double.doubleToLongBits(premiumRate) != Double.doubleToLongBits(other.premiumRate))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
