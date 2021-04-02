package org.ace.insurance.life.endorsement;

import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class LifeBeneficiary {

	private String beneficiaryNo;
	private float percentage;
	private BeneficiaryEndorseStatus status;
	private String initialId;
	private String idNo;
	private int age;
	private String insuredPersonCodeNo;
	private Gender gender;
	private IdType idType;
	private ResidentAddress residentAddress;
	private Name name;
	private RelationShip relationship;

	public LifeBeneficiary() {
	}

	public LifeBeneficiary(InsuredPersonBeneficiaries beneficiary) {
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.percentage = beneficiary.getPercentage();
		this.insuredPersonCodeNo = beneficiary.getProposalInsuredPerson().getInsPersonCodeNo();
		this.initialId = beneficiary.getInitialId();
		this.idNo = beneficiary.getIdNo();
		this.age = beneficiary.getAge();
		this.name = beneficiary.getName();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.relationship = beneficiary.getRelationship();
		this.gender = beneficiary.getGender();
	}

	public LifeBeneficiary(PolicyInsuredPersonBeneficiaries beneficiary) {
		this.beneficiaryNo = beneficiary.getBeneficiaryNo();
		this.percentage = beneficiary.getPercentage();
		this.insuredPersonCodeNo = beneficiary.getPolicyInsuredPerson().getInsPersonCodeNo();
		this.initialId = beneficiary.getInitialId();
		this.idNo = beneficiary.getIdNo();
		this.age = beneficiary.getAge();
		this.name = beneficiary.getName();
		this.idType = beneficiary.getIdType();
		this.residentAddress = beneficiary.getResidentAddress();
		this.relationship = beneficiary.getRelationship();
		this.gender = beneficiary.getGender();
	}

	public String getBeneficiaryNo() {
		return beneficiaryNo;
	}

	public void setBeneficiaryNo(String beneficiaryNo) {
		this.beneficiaryNo = beneficiaryNo;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public BeneficiaryEndorseStatus getStatus() {
		return status;
	}

	public void setStatus(BeneficiaryEndorseStatus status) {
		this.status = status;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getIdNo() {
		if (idNo == null) {
			return "";
		}
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

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LifeBeneficiary) {
			LifeBeneficiary toCompare = (LifeBeneficiary) obj;
			return this.beneficiaryNo.equals(toCompare.getBeneficiaryNo());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
