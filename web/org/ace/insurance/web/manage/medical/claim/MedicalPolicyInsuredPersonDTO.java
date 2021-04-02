package org.ace.insurance.web.manage.medical.claim;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonAddOn;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonAttachment;
import org.ace.insurance.medical.policy.MedicalPolicyKeyFactorValue;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.relationship.RelationShip;
import org.ace.insurance.web.common.CommonDTO;
import org.ace.insurance.web.manage.medical.proposal.CustomerDTO;
import org.ace.insurance.web.manage.medical.proposal.PolicyGuardianDTO;

public class MedicalPolicyInsuredPersonDTO extends CommonDTO {

	private boolean exitsEntity;
	private boolean death;
	private String id;
	private int age;
	private int totalHosDays;
	private double basicTermPremium;
	private double addOnTermPremium;
	private double premium;
	private Date dateOfBirth;
	private int unit;
	private ClaimStatus claimStatus;
	private Product product;
	private CustomerDTO customerDTO;
	private PolicyGuardianDTO guardianDTO;
	private MedicalPolicyDTO medicalPolicyDTO;
	private List<MedicalPolicyInsuredPersonAttachment> attachmentList;
	private List<MedicalPolicyInsuredPersonAddOn> policyInsuredPersonAddOnList;
	private List<MedicalPolicyKeyFactorValue> policyInsuredPersonkeyFactorValueList;
	private List<MedicalPolicyInsuredPersonBeneficiaryDTO> policyInsuredPersonBeneficiariesDtoList;
	private List<MedicalPolicyInsuredPersonAddOn> addOnList;
	private int version;
	private String tempId;
	private RelationShip relationShip;
	private PolicyGuardianDTO policyGuardianDTO;
	private String insuredPersonfullName;
	private String fatherName;
	private String occupation;
	private String fullIdNo;
	private String beneficiaryNames;
	private String beneRelationships;
	private String beneFullIdNos;
	private double sumInsured;
	private double termPremium;
	private int addOnUnit1;
	private int addOnUnit2;

	public MedicalPolicyInsuredPersonDTO() {
		tempId = System.nanoTime() + "";
	}

	public MedicalPolicyInsuredPersonDTO(MedicalPolicyInsuredPersonDTO medicalPolicyInsuredPersonDTO) {
		this.tempId = medicalPolicyInsuredPersonDTO.getTempId();
		this.id = medicalPolicyInsuredPersonDTO.getId();
		this.age = medicalPolicyInsuredPersonDTO.getAge();
		this.basicTermPremium = medicalPolicyInsuredPersonDTO.getBasicTermPremium();
		this.addOnTermPremium = medicalPolicyInsuredPersonDTO.getAddOnTermPremium();
		this.premium = medicalPolicyInsuredPersonDTO.getPremium();
		this.dateOfBirth = medicalPolicyInsuredPersonDTO.getDateOfBirth();
		this.unit = medicalPolicyInsuredPersonDTO.getUnit();
		this.claimStatus = medicalPolicyInsuredPersonDTO.getClaimStatus();
		this.product = medicalPolicyInsuredPersonDTO.getProduct();
		this.customerDTO = medicalPolicyInsuredPersonDTO.getCustomerDTO();
		this.attachmentList = medicalPolicyInsuredPersonDTO.getAttachmentList();
		this.policyInsuredPersonAddOnList = medicalPolicyInsuredPersonDTO.getPolicyInsuredPersonAddOnList();
		this.policyInsuredPersonkeyFactorValueList = medicalPolicyInsuredPersonDTO.getPolicyInsuredPersonkeyFactorValueList();
		this.policyInsuredPersonBeneficiariesDtoList = medicalPolicyInsuredPersonDTO.getPolicyInsuredPersonBeneficiariesDtoList();
		this.version = medicalPolicyInsuredPersonDTO.getVersion();
		this.guardianDTO = medicalPolicyInsuredPersonDTO.getGuardianDTO();
		this.exitsEntity = medicalPolicyInsuredPersonDTO.isExitsEntity();
		this.death = medicalPolicyInsuredPersonDTO.isDeath();
		this.relationShip = medicalPolicyInsuredPersonDTO.getRelationShip();
		this.policyGuardianDTO = medicalPolicyInsuredPersonDTO.getGuardianDTO();
	}

	public int getTotalHosDays() {
		return totalHosDays;
	}

	public void setTotalHosDays(int totalHosDays) {
		this.totalHosDays = totalHosDays;
	}

	public MedicalPolicyDTO getMedicalPolicyDTO() {
		return medicalPolicyDTO;
	}

	public void setMedicalPolicyDTO(MedicalPolicyDTO medicalPolicyDTO) {
		this.medicalPolicyDTO = medicalPolicyDTO;
	}

	public PolicyGuardianDTO getGuardianDTO() {
		return guardianDTO;
	}

	public void setGuardianDTO(PolicyGuardianDTO guardianDTO) {
		this.guardianDTO = guardianDTO;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getTermPremium() {
		return termPremium;
	}

	public void setTermPremium(double termPremium) {
		this.termPremium = termPremium;
	}

	public boolean isDeath() {
		return death;
	}

	public void setDeath(boolean death) {
		this.death = death;
	}

	public boolean isExitsEntity() {
		return exitsEntity;
	}

	public void setExitsEntity(boolean exitsEntity) {
		this.exitsEntity = exitsEntity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getBasicTermPremium() {
		return basicTermPremium;
	}

	public void setBasicTermPremium(double basicTermPremium) {
		this.basicTermPremium = basicTermPremium;
	}

	public double getAddOnTermPremium() {
		return addOnTermPremium;
	}

	public void setAddOnTermPremium(double addOnTermPremium) {
		this.addOnTermPremium = addOnTermPremium;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public Product getProduct() {
		return product;
	}

	public String getBeneficiaryNames() {
		return beneficiaryNames;
	}

	public void setBeneficiaryNames(String beneficiaryNames) {
		this.beneficiaryNames = beneficiaryNames;
	}

	public String getBeneRelationships() {
		return beneRelationships;
	}

	public void setBeneRelationships(String beneRelationships) {
		this.beneRelationships = beneRelationships;
	}

	public String getBeneFullIdNos() {
		return beneFullIdNos;
	}

	public void setBeneFullIdNos(String beneFullIdNos) {
		this.beneFullIdNos = beneFullIdNos;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getInsuredPersonfullName() {
		return insuredPersonfullName;
	}

	public void setInsuredPersonfullName(String insuredPersonfullName) {
		this.insuredPersonfullName = insuredPersonfullName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public List<MedicalPolicyInsuredPersonAttachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<MedicalPolicyInsuredPersonAttachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<MedicalPolicyInsuredPersonAddOn> getPolicyInsuredPersonAddOnList() {
		return policyInsuredPersonAddOnList;
	}

	public void setPolicyInsuredPersonAddOnList(List<MedicalPolicyInsuredPersonAddOn> policyInsuredPersonAddOnList) {
		this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
	}

	public List<MedicalPolicyKeyFactorValue> getPolicyInsuredPersonkeyFactorValueList() {
		return policyInsuredPersonkeyFactorValueList;
	}

	public void setPolicyInsuredPersonkeyFactorValueList(List<MedicalPolicyKeyFactorValue> policyInsuredPersonkeyFactorValueList) {
		this.policyInsuredPersonkeyFactorValueList = policyInsuredPersonkeyFactorValueList;
	}

	public List<MedicalPolicyInsuredPersonBeneficiaryDTO> getPolicyInsuredPersonBeneficiariesDtoList() {
		return policyInsuredPersonBeneficiariesDtoList;
	}

	public void setPolicyInsuredPersonBeneficiariesDtoList(List<MedicalPolicyInsuredPersonBeneficiaryDTO> policyInsuredPersonBeneficiariesDtoList) {
		this.policyInsuredPersonBeneficiariesDtoList = policyInsuredPersonBeneficiariesDtoList;
	}

	public List<MedicalPolicyInsuredPersonAddOn> getAddOnList() {
		if (addOnList == null) {
			addOnList = new ArrayList<MedicalPolicyInsuredPersonAddOn>();
		}
		return addOnList;
	}

	public void setAddOnList(List<MedicalPolicyInsuredPersonAddOn> addOnList) {
		this.addOnList = addOnList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public RelationShip getRelationShip() {
		return relationShip;
	}

	public void setRelationShip(RelationShip relationShip) {
		this.relationShip = relationShip;
	}

	public PolicyGuardianDTO getPolicyGuardianDTO() {
		return policyGuardianDTO;
	}

	public void setPolicyGuardianDTO(PolicyGuardianDTO policyGuardianDTO) {
		this.policyGuardianDTO = policyGuardianDTO;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public void addMedicalPolicyInsuredPersonAttachment(MedicalPolicyInsuredPersonAttachment medicalPolicyInsuredPersonAttachment) {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<MedicalPolicyInsuredPersonAttachment>();
		}
		this.attachmentList.add(medicalPolicyInsuredPersonAttachment);
	}

	public void addAddOn(MedicalPolicyInsuredPersonAddOn addOn) {
		if (this.addOnList == null) {
			this.addOnList = new ArrayList<MedicalPolicyInsuredPersonAddOn>();
		}
		this.addOnList.add(addOn);
	}

	public void addMedicalPolicyInsuredPersonBeneficiaryDTO(MedicalPolicyInsuredPersonBeneficiaryDTO medicalPolicyInsuredPersonBeneficiaryDTO) {
		if (policyInsuredPersonBeneficiariesDtoList == null) {
			this.policyInsuredPersonBeneficiariesDtoList = new ArrayList<MedicalPolicyInsuredPersonBeneficiaryDTO>();
		}
		this.policyInsuredPersonBeneficiariesDtoList.add(medicalPolicyInsuredPersonBeneficiaryDTO);
	}

	public String getFullName() {
		if (this.customerDTO != null) {
			return this.customerDTO.getFullName();
		} else {
			return "";
		}

	}

	public String getIdNo() {
		if (this.customerDTO != null) {
			return this.customerDTO.getIdNo();
		} else {
			return "";
		}

	}

	public ResidentAddress getResidentAddress() {
		if (this.customerDTO != null) {
			return this.customerDTO.getResidentAddress();
		} else {
			return null;
		}
	}

	public int getAddOnUnit1() {
		return addOnUnit1;
	}

	public void setAddOnUnit1(int addOnUnit1) {
		this.addOnUnit1 = addOnUnit1;
	}

	public int getAddOnUnit2() {
		return addOnUnit2;
	}

	public void setAddOnUnit2(int addOnUnit2) {
		this.addOnUnit2 = addOnUnit2;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((addOnList == null) ? 0 : addOnList.hashCode());
		long temp;
		temp = Double.doubleToLongBits(addOnTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + age;
		temp = Double.doubleToLongBits(basicTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((customerDTO == null) ? 0 : customerDTO.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + (death ? 1231 : 1237);
		result = prime * result + (exitsEntity ? 1231 : 1237);
		result = prime * result + ((guardianDTO == null) ? 0 : guardianDTO.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicalPolicyDTO == null) ? 0 : medicalPolicyDTO.hashCode());
		result = prime * result + ((policyGuardianDTO == null) ? 0 : policyGuardianDTO.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((relationShip == null) ? 0 : relationShip.hashCode());
		result = prime * result + ((tempId == null) ? 0 : tempId.hashCode());
		result = prime * result + totalHosDays;
		result = prime * result + unit;
		result = prime * result + version;
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
		MedicalPolicyInsuredPersonDTO other = (MedicalPolicyInsuredPersonDTO) obj;
		if (addOnList == null) {
			if (other.addOnList != null)
				return false;
		} else if (!addOnList.equals(other.addOnList))
			return false;
		if (Double.doubleToLongBits(addOnTermPremium) != Double.doubleToLongBits(other.addOnTermPremium))
			return false;
		if (age != other.age)
			return false;
		if (Double.doubleToLongBits(basicTermPremium) != Double.doubleToLongBits(other.basicTermPremium))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (customerDTO == null) {
			if (other.customerDTO != null)
				return false;
		} else if (!customerDTO.equals(other.customerDTO))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (death != other.death)
			return false;
		if (exitsEntity != other.exitsEntity)
			return false;
		if (guardianDTO == null) {
			if (other.guardianDTO != null)
				return false;
		} else if (!guardianDTO.equals(other.guardianDTO))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicalPolicyDTO == null) {
			if (other.medicalPolicyDTO != null)
				return false;
		} else if (!medicalPolicyDTO.equals(other.medicalPolicyDTO))
			return false;
		if (policyGuardianDTO == null) {
			if (other.policyGuardianDTO != null)
				return false;
		} else if (!policyGuardianDTO.equals(other.policyGuardianDTO))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (relationShip == null) {
			if (other.relationShip != null)
				return false;
		} else if (!relationShip.equals(other.relationShip))
			return false;
		if (tempId == null) {
			if (other.tempId != null)
				return false;
		} else if (!tempId.equals(other.tempId))
			return false;
		if (totalHosDays != other.totalHosDays)
			return false;
		if (unit != other.unit)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
