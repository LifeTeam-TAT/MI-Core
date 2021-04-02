package org.ace.insurance.life.policyEditHistory;

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
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policy.PolicyInsuredPersonAddon;
import org.ace.insurance.life.proposal.InsuredPersonAddon;
import org.ace.insurance.system.common.addon.AddOn;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSONADDONEDITHISTORY)
@TableGenerator(name = "LPOLINSUREDPERSONADDON_EDITHISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSUREDPERSONADDON_EDITHISTORY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "PolicyInsuredPersonAddonEditHistory.findAll", query = "SELECT p FROM PolicyInsuredPersonAddonEditHistory p "),
		@NamedQuery(name = "PolicyInsuredPersonAddonEditHistory.findById", query = "SELECT p FROM PolicyInsuredPersonAddonEditHistory p WHERE p.id = :id") })
@EntityListeners(IDInterceptor.class)
public class PolicyInsuredPersonAddonEditHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSUREDPERSONADDON_EDITHISTORY_GEN")
	private String id;

	private double premium;
	private double sumInsured;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTADDONID", referencedColumnName = "ID")
	private AddOn addOn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYINSUREDPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPersonEditHistory policyInsuredPerson;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public PolicyInsuredPersonAddonEditHistory() {
	}

	public PolicyInsuredPersonAddonEditHistory(InsuredPersonAddon addOn) {
		this.premium = addOn.getProposedPremium();
		this.sumInsured = addOn.getProposedSumInsured();
		this.addOn = addOn.getAddOn();
	}

	public PolicyInsuredPersonAddonEditHistory(PolicyInsuredPersonAddon addOn) {
		this.premium = addOn.getPremium();
		this.sumInsured = addOn.getSumInsured();
		this.addOn = addOn.getAddOn();
	}

	public PolicyInsuredPersonAddonEditHistory(AddOn addOn, int addOnSumInsured) {
		this.addOn = addOn;
		this.sumInsured = addOnSumInsured;
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

	public AddOn getAddOn() {
		return addOn;
	}

	public void setAddOn(AddOn addOn) {
		this.addOn = addOn;
	}

	public PolicyInsuredPersonEditHistory getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPersonEditHistory policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public PolicyInsuredPersonEditHistory getPolicyInsuredPersonInfo() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPersonInfo(PolicyInsuredPersonEditHistory policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		long temp;
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		PolicyInsuredPersonAddonEditHistory other = (PolicyInsuredPersonAddonEditHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
