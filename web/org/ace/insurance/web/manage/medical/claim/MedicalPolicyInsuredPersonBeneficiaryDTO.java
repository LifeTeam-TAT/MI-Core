package org.ace.insurance.web.manage.medical.claim;

import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import org.ace.insurance.common.ContentInfo;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.web.common.CommonDTO;

public class MedicalPolicyInsuredPersonBeneficiaryDTO extends CommonDTO {
	private String tempId;
	private int age;
	private Date dateOfBirth;
	private float percentage;
	private String beneficiaryNo;
	private String initialId;
	private String fatherName;
	private String idNo;
	private Gender gender;
	private IdType idType;
	private ClaimStatus claimStatus;
	private ResidentAddress residentAddress;
	private Name name;
	private RelationShip relationship;
	private Date deathDate;
	private String deathReason;
	private boolean death;
	private boolean exit;
	private String stateCode;
	private String townshipCode;
	private String idConditionType;
	private String fullIdNo;
	private ContentInfo contentInfo;

	public MedicalPolicyInsuredPersonBeneficiaryDTO() {
		tempId = System.nanoTime() + "";
	}

	// Form Data
	private boolean claimBeneficiary;

	public MedicalPolicyInsuredPersonBeneficiaryDTO(MedicalPolicyInsuredPersonBeneficiaryDTO medicalPolicyInsuredPersonBeneficiaryDTO) {
		super(medicalPolicyInsuredPersonBeneficiaryDTO.isExistsEntity(), medicalPolicyInsuredPersonBeneficiaryDTO.getVersion(), medicalPolicyInsuredPersonBeneficiaryDTO.getId(),
				medicalPolicyInsuredPersonBeneficiaryDTO.getRecorder());
		this.age = medicalPolicyInsuredPersonBeneficiaryDTO.getAge();
		this.dateOfBirth = medicalPolicyInsuredPersonBeneficiaryDTO.getDateOfBirth();
		this.percentage = medicalPolicyInsuredPersonBeneficiaryDTO.getPercentage();
		this.beneficiaryNo = medicalPolicyInsuredPersonBeneficiaryDTO.getBeneficiaryNo();
		this.initialId = medicalPolicyInsuredPersonBeneficiaryDTO.getInitialId();
		this.fatherName = medicalPolicyInsuredPersonBeneficiaryDTO.getFatherName();
		this.idNo = medicalPolicyInsuredPersonBeneficiaryDTO.getIdNo();
		this.gender = medicalPolicyInsuredPersonBeneficiaryDTO.getGender();
		this.idType = medicalPolicyInsuredPersonBeneficiaryDTO.getIdType();
		this.claimStatus = medicalPolicyInsuredPersonBeneficiaryDTO.getClaimStatus();
		this.residentAddress = medicalPolicyInsuredPersonBeneficiaryDTO.getResidentAddress();
		this.name = medicalPolicyInsuredPersonBeneficiaryDTO.getName();
		this.relationship = medicalPolicyInsuredPersonBeneficiaryDTO.getRelationship();
		this.deathDate = medicalPolicyInsuredPersonBeneficiaryDTO.getDeathDate();
		this.deathReason = medicalPolicyInsuredPersonBeneficiaryDTO.getDeathReason();
		this.death = medicalPolicyInsuredPersonBeneficiaryDTO.isDeath();
		this.stateCode = medicalPolicyInsuredPersonBeneficiaryDTO.getStateCode();
		this.townshipCode = medicalPolicyInsuredPersonBeneficiaryDTO.getTownshipCode();
		this.idConditionType = medicalPolicyInsuredPersonBeneficiaryDTO.getIdConditionType();
		this.fullIdNo = medicalPolicyInsuredPersonBeneficiaryDTO.getFullIdNo();
		this.contentInfo = medicalPolicyInsuredPersonBeneficiaryDTO.getContentInfo();
	}

	public MedicalPolicyInsuredPersonBeneficiaryDTO(boolean exitsEntity, int version, int age, Date dateOfBirth, float percentage, String beneficiaryNo, String initialId,
			String fatherName, String idNo, String id, Gender gender, IdType idType, ClaimStatus claimStatus, ResidentAddress residentAddress, Name name, RelationShip relationship,
			Date deathDate, String deathReason, boolean death, boolean exit, boolean claimBeneficiary, UserRecorder recorder) {
		super(exitsEntity, version, id, recorder);
		this.age = age;
		this.dateOfBirth = dateOfBirth;
		this.percentage = percentage;
		this.beneficiaryNo = beneficiaryNo;
		this.initialId = initialId;
		this.fatherName = fatherName;
		this.idNo = idNo;
		this.gender = gender;
		this.idType = idType;
		this.claimStatus = claimStatus;
		this.residentAddress = residentAddress;
		this.name = name;
		this.relationship = relationship;
		this.deathDate = deathDate;
		this.deathReason = deathReason;
		this.death = death;
		this.exit = exit;
		this.claimBeneficiary = claimBeneficiary;
	}

	public int getAgeForNextYear() {
		Calendar cal_1 = Calendar.getInstance();
		int currentYear = cal_1.get(Calendar.YEAR);
		Calendar cal_2 = Calendar.getInstance();
		cal_2.setTime(dateOfBirth);
		cal_2.set(Calendar.YEAR, currentYear);
		if (new Date().after(cal_2.getTime())) {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR) + 1;
			return year_2 - year_1;
		} else {
			Calendar cal_3 = Calendar.getInstance();
			cal_3.setTime(dateOfBirth);
			int year_1 = cal_3.get(Calendar.YEAR);
			int year_2 = cal_1.get(Calendar.YEAR);
			return year_2 - year_1;
		}
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getTownshipCode() {
		return townshipCode;
	}

	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	public String getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(String idConditionType) {
		this.idConditionType = idConditionType;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public String setFullIdNo() {
		if (idType.equals(IdType.NRCNO)) {
			fullIdNo = stateCode + "/" + townshipCode + "(" + idConditionType + ")" + idNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			fullIdNo = idNo;
		}
		return fullIdNo;
	}

	public void loadTransientIdNo() {
		if (idType.equals(IdType.NRCNO) && fullIdNo != null) {
			StringTokenizer token = new StringTokenizer(fullIdNo, "/()");
			stateCode = token.nextToken();
			townshipCode = token.nextToken();
			idConditionType = token.nextToken();
			idNo = token.nextToken();
			fullIdNo = stateCode.equals("null") ? "" : fullIdNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			idNo = (fullIdNo == null) ? "" : fullIdNo;
		}
	}

	public boolean isExit() {
		return exit;
	}

	public void setExit(boolean exit) {
		this.exit = exit;
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

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
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

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public String getDeathReason() {
		return deathReason;
	}

	public void setDeathReason(String deathReason) {
		this.deathReason = deathReason;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public boolean isClaimBeneficiary() {
		return claimBeneficiary;
	}

	public void setClaimBeneficiary(boolean claimBeneficiary) {
		this.claimBeneficiary = claimBeneficiary;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public ContentInfo getContentInfo() {
		return contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId.trim();
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
		return result;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + age;
		result = prime * result + ((beneficiaryNo == null) ? 0 : beneficiaryNo.hashCode());
		result = prime * result + (claimBeneficiary ? 1231 : 1237);
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result + ((deathReason == null) ? 0 : deathReason.hashCode());
		result = prime * result + (exit ? 1231 : 1237);
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((fullIdNo == null) ? 0 : fullIdNo.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((idConditionType == null) ? 0 : idConditionType.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(percentage);
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((tempId == null) ? 0 : tempId.hashCode());
		result = prime * result + ((townshipCode == null) ? 0 : townshipCode.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalPolicyInsuredPersonBeneficiaryDTO other = (MedicalPolicyInsuredPersonBeneficiaryDTO) obj;
		if (age != other.age)
			return false;
		if (beneficiaryNo == null) {
			if (other.beneficiaryNo != null)
				return false;
		} else if (!beneficiaryNo.equals(other.beneficiaryNo))
			return false;
		if (claimBeneficiary != other.claimBeneficiary)
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (death != other.death)
			return false;
		if (deathDate == null) {
			if (other.deathDate != null)
				return false;
		} else if (!deathDate.equals(other.deathDate))
			return false;
		if (deathReason == null) {
			if (other.deathReason != null)
				return false;
		} else if (!deathReason.equals(other.deathReason))
			return false;
		if (exit != other.exit)
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (fullIdNo == null) {
			if (other.fullIdNo != null)
				return false;
		} else if (!fullIdNo.equals(other.fullIdNo))
			return false;
		if (gender != other.gender)
			return false;
		if (idConditionType == null) {
			if (other.idConditionType != null)
				return false;
		} else if (!idConditionType.equals(other.idConditionType))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(percentage) != Float.floatToIntBits(other.percentage))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (tempId == null) {
			if (other.tempId != null)
				return false;
		} else if (!tempId.equals(other.tempId))
			return false;
		if (townshipCode == null) {
			if (other.townshipCode != null)
				return false;
		} else if (!townshipCode.equals(other.townshipCode))
			return false;
		return true;
	}

}
