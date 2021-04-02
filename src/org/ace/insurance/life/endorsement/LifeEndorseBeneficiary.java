package org.ace.insurance.life.endorsement;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEENDORSEBENEFICIARY)
@TableGenerator(name = "LIFEENDORSEBENEFICIARY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEENDORSEBENEFICIARY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeEndorseBeneficiary.findAll", query = "SELECT leb FROM LifeEndorseBeneficiary leb "),
		@NamedQuery(name = "LifeEndorseBeneficiary.findById", query = "SELECT leb FROM LifeEndorseBeneficiary leb WHERE leb.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifeEndorseBeneficiary implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEENDORSEBENEFICIARY_GEN")
	private String id;

	private String beneficiaryNo;
	private String initialId;
	private String idNo;
	private int age;
	private float percentage;
	private String insuredPersonCodeNo;

	@Enumerated(value = EnumType.STRING)
	private BeneficiaryEndorseStatus beneficiaryEndorseStatus;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
	private RelationShip relationship;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEENDORSECHAGEID", referencedColumnName = "ID")
	private LifeEndorseChange lifeEndorseChange;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeEndorseBeneficiary() {
	}

	public LifeEndorseBeneficiary(String beneficiaryNo, float percentage, BeneficiaryEndorseStatus status, String insuredPersonCodeNo, String initialId, String idNo, int age,
			Name name, IdType idType, ResidentAddress residentAddress, RelationShip relationship, Gender gender) {

		this.beneficiaryNo = beneficiaryNo;
		this.percentage = percentage;
		this.beneficiaryEndorseStatus = status;
		this.insuredPersonCodeNo = insuredPersonCodeNo;
		this.initialId = initialId;
		this.idNo = idNo;
		this.idNo = idNo;
		this.age = age;
		this.name = name;
		this.idType = idType;
		this.residentAddress = residentAddress;
		this.relationship = relationship;
		this.gender = gender;

	}

	public LifeEndorseBeneficiary(LifeBeneficiary beneficiary) {
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryEndorseStatus = beneficiary.getStatus();
		this.insuredPersonCodeNo = beneficiary.getInsuredPersonCodeNo();
		this.initialId = beneficiary.getInitialId();
		this.idNo = beneficiary.getIdNo();
		this.age = beneficiary.getAge();
		this.name = beneficiary.getName();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.relationship = beneficiary.getRelationship();
		this.gender = beneficiary.getGender();
	}

	public LifeEndorseBeneficiary(LifeBeneficiary beneficiary, BeneficiaryEndorseStatus status) {
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.percentage = beneficiary.getPercentage();
		this.beneficiaryEndorseStatus = status;
		this.insuredPersonCodeNo = beneficiary.getInsuredPersonCodeNo();
		this.initialId = beneficiary.getInitialId();
		this.idNo = beneficiary.getIdNo();
		this.age = beneficiary.getAge();
		this.name = beneficiary.getName();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.relationship = beneficiary.getRelationship();
		this.gender = beneficiary.getGender();

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

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public String getInsuredPersonCodeNo() {
		return insuredPersonCodeNo;
	}

	public void setInsuredPersonCodeNo(String insuredPersonCodeNo) {
		this.insuredPersonCodeNo = insuredPersonCodeNo;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public ResidentAddress getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public RelationShip getRelationship() {
		return relationship;
	}

	public void setRelationship(RelationShip relationship) {
		this.relationship = relationship;
	}

	public LifeEndorseChange getLifeEndorseChange() {
		return lifeEndorseChange;
	}

	public void setLifeEndorseChange(LifeEndorseChange lifeEndorseChange) {
		this.lifeEndorseChange = lifeEndorseChange;
	}

	public BeneficiaryEndorseStatus getBeneficiaryEndorseStatus() {
		return beneficiaryEndorseStatus;
	}

	public void setStatus(BeneficiaryEndorseStatus beneficiaryEndorseStatus) {
		this.beneficiaryEndorseStatus = beneficiaryEndorseStatus;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId;
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + " " + name.getFirstName();
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + " " + name.getMiddleName();
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + " " + name.getLastName();
			}
		}
		return result.replaceAll("\\s+", " ");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((beneficiaryEndorseStatus == null) ? 0 : beneficiaryEndorseStatus.hashCode());
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((insuredPersonCodeNo == null) ? 0 : insuredPersonCodeNo.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
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
		LifeEndorseBeneficiary other = (LifeEndorseBeneficiary) obj;
		if (age != other.age)
			return false;
		if (beneficiaryEndorseStatus != other.beneficiaryEndorseStatus)
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		if (idType != other.idType)
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (insuredPersonCodeNo == null) {
			if (other.insuredPersonCodeNo != null)
				return false;
		} else if (!insuredPersonCodeNo.equals(other.insuredPersonCodeNo))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(percentage) != Float.floatToIntBits(other.percentage))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
