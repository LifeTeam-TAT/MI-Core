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
@Table(name = TableName.LIFEENDORSEINSUREDPERSON)
@TableGenerator(name = "LIFEENDORSEINSUREDPERSON_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEENDORSEINSUREDPERSON_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeEndorseInsuredPerson.findAll", query = "SELECT lp FROM LifeEndorseInsuredPerson lp "),
		@NamedQuery(name = "LifeEndorseInsuredPerson.findById", query = "SELECT lp FROM LifeEndorseInsuredPerson lp WHERE lp.id = :id") })
@EntityListeners(IDInterceptor.class)

public class LifeEndorseInsuredPerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEENDORSEINSUREDPERSON_GEN")
	private String id;

	private String insuredPersonCodeNo;
	private int periodOfMonths;

	@Enumerated(EnumType.STRING)
	private InsuredPersonEndorseStatus insuredPersonEndorseStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEENDORSEINFOID", referencedColumnName = "ID")
	private LifeEndorseInfo lifeEndorseInfo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeEndorseInsuredPerson", orphanRemoval = true)
	private List<LifeEndorseChange> lifeEndorseChangeList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeEndorseInsuredPerson() {
	}

	public LifeEndorseInsuredPerson(String insuredPersonCodeNo, InsuredPersonEndorseStatus status) {

		this.insuredPersonCodeNo = insuredPersonCodeNo;
		this.insuredPersonEndorseStatus = status;
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

	public String getInsuredPersonCodeNo() {
		return insuredPersonCodeNo;
	}

	public void setInsuredPersonCodeNo(String insuredPersonCodeNo) {
		this.insuredPersonCodeNo = insuredPersonCodeNo;
	}

	public InsuredPersonEndorseStatus getInsuredPersonEndorseStatus() {
		return insuredPersonEndorseStatus;
	}

	public void setInsuredPersonEndorseStatus(InsuredPersonEndorseStatus insuredPersonEndorseStatus) {
		this.insuredPersonEndorseStatus = insuredPersonEndorseStatus;
	}

	public LifeEndorseInfo getLifeEndorseInfo() {
		return lifeEndorseInfo;
	}

	public void setLifeEndorseInfo(LifeEndorseInfo lifeEndorseInfo) {
		this.lifeEndorseInfo = lifeEndorseInfo;
	}

	public int getPeriodOfMonths() {
		return periodOfMonths;
	}

	public void setPeriodOfMonths(int periodOfMonths) {
		this.periodOfMonths = periodOfMonths;
	}

	public List<LifeEndorseChange> getLifeEndorseChangeList() {
		if (lifeEndorseChangeList == null) {
			lifeEndorseChangeList = new ArrayList<LifeEndorseChange>();
		}
		return lifeEndorseChangeList;
	}

	public void setLifeEndorseChangeList(List<LifeEndorseChange> lifeEndorseChangeList) {
		this.lifeEndorseChangeList = lifeEndorseChangeList;
	}

	public void addLifeEndorseChange(LifeEndorseChange lifeEndorseChange) {
		lifeEndorseChange.setLifeEndorseInsuredPerson(this);
		getLifeEndorseChangeList().add(lifeEndorseChange);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<LifeEndorseItem> getLifeEndorseItemList() {
		List<LifeEndorseItem> itemList = new ArrayList<LifeEndorseItem>();
		for (LifeEndorseChange lifeEndorseChange : getLifeEndorseChangeList()) {
			itemList.add(lifeEndorseChange.getLifeEndorseItem());
		}
		return itemList;
	}

	public LifeEndorseChange getLifeEndorseChangeByItem(LifeEndorseItem item) {
		for (LifeEndorseChange lifeEndorseChange : getLifeEndorseChangeList()) {
			if (lifeEndorseChange.getLifeEndorseItem() == item) {
				return lifeEndorseChange;
			}
		}
		return null;
	}

	public double getEndorsePremium() {
		double result = 0;
		double endorsePremium = 0;
		for (LifeEndorseChange lifeEndorseChange : getLifeEndorseChangeList()) {
			try {
				endorsePremium = (lifeEndorseChange.getEndorsePremium());
			} catch (NumberFormatException e) {
				continue;
			}
			result = result + endorsePremium;
		}
		return result;
	}

	public double getEndorseInterest() {
		double result = 0;
		double endorseInterest = 0;
		for (LifeEndorseChange lifeEndorseChange : getLifeEndorseChangeList()) {
			try {
				endorseInterest = (lifeEndorseChange.getInterest());
			} catch (NumberFormatException e) {
				continue;
			}
			result = result + endorseInterest;
		}
		return result;
	}

	public double getTotalEndorsePremium() {
		double premium = getEndorsePremium() + getEndorseInterest();
		premium = Utils.getTwoDecimalPoint(premium);
		return premium;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuredPersonCodeNo == null) ? 0 : insuredPersonCodeNo.hashCode());
		result = prime * result + ((insuredPersonEndorseStatus == null) ? 0 : insuredPersonEndorseStatus.hashCode());
		result = prime * result + periodOfMonths;
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
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
		LifeEndorseInsuredPerson other = (LifeEndorseInsuredPerson) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuredPersonCodeNo == null) {
			if (other.insuredPersonCodeNo != null)
				return false;
		} else if (!insuredPersonCodeNo.equals(other.insuredPersonCodeNo))
			return false;
		if (insuredPersonEndorseStatus != other.insuredPersonEndorseStatus)
			return false;
		if (periodOfMonths != other.periodOfMonths)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
