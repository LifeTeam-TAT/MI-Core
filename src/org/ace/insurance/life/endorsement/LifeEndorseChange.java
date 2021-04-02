package org.ace.insurance.life.endorsement;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.Utils;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEENDORSECHANGE)
@TableGenerator(name = "LIFEENDORSECHANGE_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEENDORSECHANGE_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeEndorseChange.findAll", query = "SELECT lec FROM LifeEndorseChange lec "),
		@NamedQuery(name = "LifeEndorseChange.findById", query = "SELECT lec FROM LifeEndorseChange lec WHERE lec.id = :id") })
@EntityListeners(IDInterceptor.class)

public class LifeEndorseChange implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEENDORSECHANGE_GEN")
	private String id;

	private String oldValue;
	private String newValue;
	private double oldPremium;
	private double newPremium;
	private double endorsePremium;
	private double interest;
	private double paidUpValue;
	private int coverTime;
	private double refundAmount;

	@Enumerated(EnumType.STRING)
	LifeEndorseItem lifeEndorseItem;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeEndorseChange", orphanRemoval = true)
	private List<LifeEndorseBeneficiary> lifeEndorseBeneficiaryInfoList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEENDORSEINSUREDPERSONID", referencedColumnName = "ID")
	private LifeEndorseInsuredPerson lifeEndorseInsuredPerson;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeEndorseChange() {
	}

	public LifeEndorseChange(LifeEndorseItem lifeEndorseItem) {
		this.lifeEndorseItem = lifeEndorseItem;
	}

	public LifeEndorseChange(LifeEndorseItem lifeEndorseItem, String oldValue, String newValue) {
		this.lifeEndorseItem = lifeEndorseItem;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public LifeEndorseChange(LifeEndorseItem lifeEndorseItem, double oldPremium, double newPremium) {
		this.lifeEndorseItem = lifeEndorseItem;
		this.oldPremium = oldPremium;
		this.newPremium = newPremium;
	}

	public LifeEndorseChange(LifeEndorseItem lifeEndorseItem, String oldValue, String newValue, double oldPremium, double newPremium) {
		this.lifeEndorseItem = lifeEndorseItem;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.oldPremium = oldPremium;
		this.newPremium = newPremium;
	}

	/******************************************************
	 * getter / setter
	 **********************************************************/

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

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public double getOldPremium() {
		return oldPremium;
	}

	public void setOldPremium(double oldPremium) {
		this.oldPremium = oldPremium;
	}

	public double getNewPremium() {
		return newPremium;
	}

	public void setNewPremium(double newPremium) {
		this.newPremium = newPremium;
	}

	public double getEndorsePremium() {
		return endorsePremium;
	}

	public void setEndorsePremium(double endorsePremium) {
		this.endorsePremium = endorsePremium;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getPaidUpValue() {
		return paidUpValue;
	}

	public void setPaidUpValue(double paidUpValue) {
		this.paidUpValue = paidUpValue;
	}

	public int getCoverTime() {
		return coverTime;
	}

	public void setCoverTime(int coverTime) {
		this.coverTime = coverTime;
	}

	public double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public LifeEndorseItem getLifeEndorseItem() {
		return lifeEndorseItem;
	}

	public void setLifeEndorseItem(LifeEndorseItem lifeEndorseItem) {
		this.lifeEndorseItem = lifeEndorseItem;
	}

	public LifeEndorseInsuredPerson getLifeEndorseInsuredPerson() {
		return lifeEndorseInsuredPerson;
	}

	public void setLifeEndorseInsuredPerson(LifeEndorseInsuredPerson lifeEndorseInsuredPerson) {
		this.lifeEndorseInsuredPerson = lifeEndorseInsuredPerson;
	}

	public List<LifeEndorseBeneficiary> getLifeEndorseBeneficiaryInfoList() {
		if (lifeEndorseBeneficiaryInfoList == null) {
			lifeEndorseBeneficiaryInfoList = new ArrayList<LifeEndorseBeneficiary>();
		}
		return lifeEndorseBeneficiaryInfoList;
	}

	public void setLifeEndorseBeneficiaryInfoList(List<LifeEndorseBeneficiary> lifeEndorseBeneficiaryInfoList) {
		this.lifeEndorseBeneficiaryInfoList = lifeEndorseBeneficiaryInfoList;
	}

	public void addLifeEndorseBeneficiary(LifeEndorseBeneficiary lifeEndorseBeneficiary) {
		lifeEndorseBeneficiary.setLifeEndorseChange(this);
		getLifeEndorseBeneficiaryInfoList().add(lifeEndorseBeneficiary);
	}

	public String getNewValueString() {
		double value = 0;
		String stringValue = null;
		try {
			if (newValue != null) {
				value = Double.parseDouble(newValue);
				stringValue = Utils.formattedCurrency(value);
			}

		} catch (NumberFormatException e) {
			stringValue = newValue;
		}
		return stringValue;
	}

	public String getOldValueString() {
		double value = 0;
		String stringValue = null;
		try {
			if (oldValue != null) {
				value = Double.parseDouble(oldValue);
				stringValue = Utils.formattedCurrency(value);
			}
		} catch (NumberFormatException e) {
			stringValue = oldValue;
		}
		return stringValue;
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
		result = prime * result + coverTime;
		long temp;
		temp = Double.doubleToLongBits(endorsePremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		temp = Double.doubleToLongBits(interest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((lifeEndorseItem == null) ? 0 : lifeEndorseItem.hashCode());
		temp = Double.doubleToLongBits(newPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((newValue == null) ? 0 : newValue.hashCode());
		temp = Double.doubleToLongBits(oldPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((oldValue == null) ? 0 : oldValue.hashCode());
		temp = Double.doubleToLongBits(paidUpValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		temp = Double.doubleToLongBits(refundAmount);
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
		LifeEndorseChange other = (LifeEndorseChange) obj;
		if (coverTime != other.coverTime)
			return false;
		if (Double.doubleToLongBits(endorsePremium) != Double.doubleToLongBits(other.endorsePremium))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(interest) != Double.doubleToLongBits(other.interest))
			return false;
		if (lifeEndorseItem != other.lifeEndorseItem)
			return false;
		if (Double.doubleToLongBits(newPremium) != Double.doubleToLongBits(other.newPremium))
			return false;
		if (newValue == null) {
			if (other.newValue != null)
				return false;
		} else if (!newValue.equals(other.newValue))
			return false;
		if (Double.doubleToLongBits(oldPremium) != Double.doubleToLongBits(other.oldPremium))
			return false;
		if (oldValue == null) {
			if (other.oldValue != null)
				return false;
		} else if (!oldValue.equals(other.oldValue))
			return false;
		if (Double.doubleToLongBits(paidUpValue) != Double.doubleToLongBits(other.paidUpValue))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (Double.doubleToLongBits(refundAmount) != Double.doubleToLongBits(other.refundAmount))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
