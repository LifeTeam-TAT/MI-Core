package org.ace.insurance.life.endorsement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEENDORSEINFO)
@TableGenerator(name = "LIFEENDORSEINFO_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEENDORSEINFO_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeEndorseInfo.findAll", query = "SELECT le FROM LifeEndorseInfo le "),
		@NamedQuery(name = "LifeEndorseInfo.findById", query = "SELECT le FROM LifeEndorseInfo le WHERE le.id = :id"),
		@NamedQuery(name = "LifeEndorseInfo.findBySourcePolicyReferenceNo", query = "SELECT le FROM LifeEndorseInfo le WHERE le.sourcePolicyReferenceNo = :sourcePolicyReferenceNo"),
		@NamedQuery(name = "LifeEndorseInfo.findByEndorsePolicyReferenceNo", query = "SELECT le FROM LifeEndorseInfo le WHERE le.endorsePolicyReferenceNo = :endorsePolicyReferenceNo") })
@EntityListeners(IDInterceptor.class)
public class LifeEndorseInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEENDORSEINFO_GEN")
	private String id;

	private String lifePolicyNo;
	private String sourcePolicyReferenceNo;
	private String endorsePolicyReferenceNo;

	@Temporal(TemporalType.TIMESTAMP)
	private Date commenmanceDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEPOLICYENDDATE")
	private Date activePolicyEndDate;

	@Column(name = "ENDORSEMENTDAET")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endorsementDate;

	private int passedMonth;
	private String productId;
	private int monthsOfPaymentType;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeEndorseInfo", orphanRemoval = true)
	private List<LifeEndorseInsuredPerson> lifeEndorseInsuredPersonInfoList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeEndorseInfo() {
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

	public String getLifePolicyNo() {
		return lifePolicyNo;
	}

	public void setLifePolicyNo(String lifePolicyNo) {
		this.lifePolicyNo = lifePolicyNo;
	}

	public String getSourcePolicyReferenceNo() {
		return sourcePolicyReferenceNo;
	}

	public void setSourcePolicyReferenceNo(String sourcePolicyReferenceNo) {
		this.sourcePolicyReferenceNo = sourcePolicyReferenceNo;
	}

	public String getEndorsePolicyReferenceNo() {
		return endorsePolicyReferenceNo;
	}

	public void setEndorsePolicyReferenceNo(String endorsePolicyReferenceNo) {
		this.endorsePolicyReferenceNo = endorsePolicyReferenceNo;
	}

	public Date getCommenmanceDate() {
		return commenmanceDate;
	}

	public void setCommenmanceDate(Date commenmanceDate) {
		this.commenmanceDate = commenmanceDate;
	}

	public Date getActivePolicyEndDate() {
		return activePolicyEndDate;
	}

	public void setActivePolicyEndDate(Date activePolicyEndDate) {
		this.activePolicyEndDate = activePolicyEndDate;
	}

	public Date getEndorsementDate() {
		return endorsementDate;
	}

	public void setEndorsementDate(Date endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	public int getPassedMonth() {
		return passedMonth;
	}

	public void setPassedMonth(int passedMonth) {
		this.passedMonth = passedMonth;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public int getMonthsOfPaymentType() {
		return monthsOfPaymentType;
	}

	public void setMonthsOfPaymentType(int monthsOfPaymentType) {
		this.monthsOfPaymentType = monthsOfPaymentType;
	}

	public List<LifeEndorseInsuredPerson> getLifeEndorseInsuredPersonInfoList() {
		if (lifeEndorseInsuredPersonInfoList == null) {
			lifeEndorseInsuredPersonInfoList = new ArrayList<LifeEndorseInsuredPerson>();
		}
		return lifeEndorseInsuredPersonInfoList;
	}

	public void setLifeEndorseInsuredPersonInfoList(List<LifeEndorseInsuredPerson> lifeEndorseInsuredPersonInfoList) {
		this.lifeEndorseInsuredPersonInfoList = lifeEndorseInsuredPersonInfoList;
	}

	public void addLifeEndorseInsuredPerson(LifeEndorseInsuredPerson lifeEndorseInsuredPerson) {
		lifeEndorseInsuredPerson.setLifeEndorseInfo(this);
		getLifeEndorseInsuredPersonInfoList().add(lifeEndorseInsuredPerson);
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getBasicEndorsePremium() {
		double endorsePremium = 0;
		for (LifeEndorseInsuredPerson insuredPerson : getLifeEndorseInsuredPersonInfoList()) {
			endorsePremium = endorsePremium + insuredPerson.getEndorsePremium();
		}
		return endorsePremium;
	}

	public double getInterest() {
		double interest = 0;
		for (LifeEndorseInsuredPerson insuredPerson : getLifeEndorseInsuredPersonInfoList()) {
			interest = interest + insuredPerson.getEndorseInterest();
		}
		return interest;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activePolicyEndDate == null) ? 0 : activePolicyEndDate.hashCode());
		result = prime * result + ((commenmanceDate == null) ? 0 : commenmanceDate.hashCode());
		result = prime * result + ((endorsePolicyReferenceNo == null) ? 0 : endorsePolicyReferenceNo.hashCode());
		result = prime * result + ((endorsementDate == null) ? 0 : endorsementDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lifePolicyNo == null) ? 0 : lifePolicyNo.hashCode());
		result = prime * result + monthsOfPaymentType;
		result = prime * result + passedMonth;
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((productId == null) ? 0 : productId.hashCode());
		result = prime * result + ((sourcePolicyReferenceNo == null) ? 0 : sourcePolicyReferenceNo.hashCode());
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
		LifeEndorseInfo other = (LifeEndorseInfo) obj;
		if (activePolicyEndDate == null) {
			if (other.activePolicyEndDate != null)
				return false;
		} else if (!activePolicyEndDate.equals(other.activePolicyEndDate))
			return false;
		if (commenmanceDate == null) {
			if (other.commenmanceDate != null)
				return false;
		} else if (!commenmanceDate.equals(other.commenmanceDate))
			return false;
		if (endorsePolicyReferenceNo == null) {
			if (other.endorsePolicyReferenceNo != null)
				return false;
		} else if (!endorsePolicyReferenceNo.equals(other.endorsePolicyReferenceNo))
			return false;
		if (endorsementDate == null) {
			if (other.endorsementDate != null)
				return false;
		} else if (!endorsementDate.equals(other.endorsementDate))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lifePolicyNo == null) {
			if (other.lifePolicyNo != null)
				return false;
		} else if (!lifePolicyNo.equals(other.lifePolicyNo))
			return false;
		if (monthsOfPaymentType != other.monthsOfPaymentType)
			return false;
		if (passedMonth != other.passedMonth)
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (sourcePolicyReferenceNo == null) {
			if (other.sourcePolicyReferenceNo != null)
				return false;
		} else if (!sourcePolicyReferenceNo.equals(other.sourcePolicyReferenceNo))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

	/******************************************************
	 * Override Method
	 **********************************************************/

}
