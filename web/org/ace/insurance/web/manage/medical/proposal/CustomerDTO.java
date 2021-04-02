package org.ace.insurance.web.manage.medical.proposal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.ace.insurance.common.ContentInfo;
import org.ace.insurance.common.FamilyInfo;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.HealthType;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.MaritalStatus;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.OfficeAddress;
import org.ace.insurance.common.PassportType;
import org.ace.insurance.common.PermanentAddress;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.medical.proposal.CustomerInfoStatus;
import org.ace.insurance.system.common.bankBranch.BankBranch;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.country.Country;
import org.ace.insurance.system.common.industry.Industry;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.qualification.Qualification;
import org.ace.insurance.system.common.religion.Religion;
import org.ace.insurance.web.common.CommonDTO;

public class CustomerDTO extends CommonDTO {
	private String initialId;
	private String fatherName;
	private String idNo;
	private double height;
	private double weight;
	private String placeOfBirth;
	private Date dateOfBirth;
	private String labourNo;
	private String birthMark;
	private String salary;
	private int closedPolicy;
	private int activePolicy;
	private String bankAccountNo;
	private Gender gender;
	private IdType idType;
	private MaritalStatus maritalStatus;
	private OfficeAddress officeAddress;
	private PermanentAddress permanentAddress;
	private ResidentAddress residentAddress;
	private ContentInfo contentInfo;
	private Name name;
	private String stateCode;
	private String townshipCode;
	private String idConditionType;
	private List<FamilyInfo> familyInfo;
	private Branch branch;
	private Qualification qualification;
	private BankBranch bankBranch;
	private Religion religion;
	private Occupation occupation;
	private String fullIdNo;
	private boolean guardian;
	private Industry industry;
	private Country country;
	private List<CustomerInfoStatus> customerInfoStatusList;
	private PassportType pType;
	private HealthType healthType;

	public CustomerDTO(String initialId, String fatherName, String idNo, double height, double weight, String placeOfBirth, Date dateOfBirth, String labourNo, String birthMark,
			String salary, int closedPolicy, int activePolicy, String bankAccountNo, Gender gender, IdType idType, MaritalStatus maritalStatus, OfficeAddress officeAddress,
			PermanentAddress permanentAddress, ResidentAddress residentAddress, ContentInfo contentInfo, Name name, String stateCode, String townshipCode, String idConditionType,
			List<FamilyInfo> familyInfo, Branch branch, Qualification qualification, BankBranch bankBranch, Religion religion, Occupation occupation, String fullIdNo,
			Industry industry, Country country, List<CustomerInfoStatus> customerInfoStatusList, PassportType pType) {
		super();
		this.initialId = initialId;
		this.fatherName = fatherName;
		this.idNo = idNo;
		this.height = height;
		this.weight = weight;
		this.placeOfBirth = placeOfBirth;
		this.dateOfBirth = dateOfBirth;
		this.labourNo = labourNo;
		this.birthMark = birthMark;
		this.salary = salary;
		this.closedPolicy = closedPolicy;
		this.activePolicy = activePolicy;
		this.bankAccountNo = bankAccountNo;
		this.gender = gender;
		this.idType = idType;
		this.maritalStatus = maritalStatus;
		this.officeAddress = officeAddress;
		this.permanentAddress = permanentAddress;
		this.residentAddress = residentAddress;
		this.contentInfo = contentInfo;
		this.name = name;
		this.stateCode = stateCode;
		this.townshipCode = townshipCode;
		this.idConditionType = idConditionType;
		this.familyInfo = familyInfo;
		this.branch = branch;
		this.qualification = qualification;
		this.bankBranch = bankBranch;
		this.religion = religion;
		this.occupation = occupation;
		this.fullIdNo = fullIdNo;
		this.industry = industry;
		this.country = country;
		this.customerInfoStatusList = customerInfoStatusList;
		this.pType = pType;
	}

	public CustomerDTO(CustomerDTO customerDTO) {
		super(customerDTO.isExistsEntity(), customerDTO.getVersion(), customerDTO.getId(), customerDTO.getRecorder());
		this.initialId = customerDTO.getInitialId();
		this.fatherName = customerDTO.getFatherName();
		this.idNo = customerDTO.getIdNo();
		this.height = customerDTO.getHeight();
		this.weight = customerDTO.getWeight();
		this.placeOfBirth = customerDTO.getPlaceOfBirth();
		this.dateOfBirth = customerDTO.getDateOfBirth();
		this.labourNo = customerDTO.getLabourNo();
		this.birthMark = customerDTO.getBirthMark();
		this.salary = customerDTO.getSalary();
		this.closedPolicy = customerDTO.getClosedPolicy();
		this.activePolicy = customerDTO.getActivePolicy();
		this.bankAccountNo = customerDTO.getBankAccountNo();
		this.gender = customerDTO.getGender();
		this.idType = customerDTO.getIdType();
		this.maritalStatus = customerDTO.getMaritalStatus();
		this.officeAddress = customerDTO.getOfficeAddress();
		this.permanentAddress = customerDTO.getPermanentAddress();
		this.residentAddress = new ResidentAddress(customerDTO.getResidentAddress());
		this.contentInfo = customerDTO.getContentInfo();
		this.name = new Name(customerDTO.getName());
		this.stateCode = customerDTO.getStateCode();
		this.townshipCode = customerDTO.getTownshipCode();
		this.idConditionType = customerDTO.getIdConditionType();
		this.familyInfo = customerDTO.getFamilyInfo();
		this.branch = customerDTO.getBranch();
		this.qualification = customerDTO.getQualification();
		this.bankBranch = customerDTO.getBankBranch();
		this.religion = customerDTO.getReligion();
		this.occupation = customerDTO.getOccupation();
		this.fullIdNo = customerDTO.getFullIdNo();
		this.industry = customerDTO.getIndustry();
		this.country = customerDTO.getCountry();
		this.customerInfoStatusList = customerDTO.getCustomerInfoStatusList();
		this.pType = customerDTO.getpType();
	}

	public CustomerDTO() {
		occupation = new Occupation();
		country = new Country();
		contentInfo = new ContentInfo();
		residentAddress = new ResidentAddress();
	}

	public String getFullIdNo() {
		if (!IdType.STILL_APPLYING.equals(idType)) {
			return fullIdNo;
		} else {
			return IdType.STILL_APPLYING.getLabel();
		}

	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public String getIdConditionType() {
		return idConditionType;
	}

	public void setIdConditionType(String idConditionType) {
		this.idConditionType = idConditionType;
	}

	public String getStateCode() {
		return stateCode;
	}

	public String getTownshipCode() {
		return townshipCode;
	}

	public List<CustomerInfoStatus> getCustomerInfoStatusList() {
		return customerInfoStatusList;
	}

	public void setCustomerInfoStatusList(List<CustomerInfoStatus> customerInfoStatusList) {
		this.customerInfoStatusList = customerInfoStatusList;
	}

	public String getInitialId() {
		return initialId;
	}

	public void setInitialId(String initialId) {
		this.initialId = initialId;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getLabourNo() {
		return labourNo;
	}

	public void setLabourNo(String labourNo) {
		this.labourNo = labourNo;
	}

	public String getBirthMark() {
		return birthMark;
	}

	public void setBirthMark(String birthMark) {
		this.birthMark = birthMark;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public int getClosedPolicy() {
		return closedPolicy;
	}

	public void setClosedPolicy(int closedPolicy) {
		this.closedPolicy = closedPolicy;
	}

	public int getActivePolicy() {
		return activePolicy;
	}

	public void setActivePolicy(int activePolicy) {
		this.activePolicy = activePolicy;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
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

	public MaritalStatus getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(MaritalStatus maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public OfficeAddress getOfficeAddress() {
		if (officeAddress == null) {
			officeAddress = new OfficeAddress();
		}
		return officeAddress;
	}

	public void setOfficeAddress(OfficeAddress officeAddress) {
		this.officeAddress = officeAddress;
	}

	public PermanentAddress getPermanentAddress() {
		if (permanentAddress == null) {
			permanentAddress = new PermanentAddress();
		}
		return permanentAddress;
	}

	public void setPermanentAddress(PermanentAddress permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public ResidentAddress getResidentAddress() {
		if (residentAddress == null) {
			residentAddress = new ResidentAddress();
		}
		return residentAddress;
	}

	public void setResidentAddress(ResidentAddress residentAddress) {
		this.residentAddress = residentAddress;
	}

	public ContentInfo getContentInfo() {
		if (contentInfo == null) {
			contentInfo = new ContentInfo();
		}
		return contentInfo;
	}

	public void setContentInfo(ContentInfo contentInfo) {
		this.contentInfo = contentInfo;
	}

	public Name getName() {
		if (name == null) {
			name = new Name();
		}
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public List<FamilyInfo> getFamilyInfo() {
		if (familyInfo == null) {
			familyInfo = new ArrayList<FamilyInfo>();
		}
		return familyInfo;
	}

	public String getFullAddress() {
		// for claim
		// this.residentAddress=new ResidentAddress();
		String result = "";
		if (residentAddress != null) {
			if (residentAddress.getResidentAddress() != null && !residentAddress.getResidentAddress().isEmpty()) {
				result = result + residentAddress.getResidentAddress();
			}
			if (residentAddress.getTownship() != null && !residentAddress.getTownship().getFullTownShip().isEmpty()) {
				result = result + ", " + residentAddress.getTownship().getFullTownShip();
			}
		}
		return result;
	}

	public void setFamilyInfo(List<FamilyInfo> familyInfo) {
		this.familyInfo = familyInfo;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public BankBranch getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(BankBranch bankBranch) {
		this.bankBranch = bankBranch;
	}

	public Religion getReligion() {
		return religion;
	}

	public void setReligion(Religion religion) {
		this.religion = religion;
	}

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public boolean isGuardian() {
		return guardian;
	}

	public void setGuardian(boolean guardian) {
		this.guardian = guardian;
	}

	public void addCustomerInfoStatus(CustomerInfoStatus customerInfoStatus) {
		if (customerInfoStatusList == null) {
			customerInfoStatusList = new ArrayList<CustomerInfoStatus>();
		}

		if (!customerInfoStatusList.contains(customerInfoStatus)) {
			customerInfoStatusList.add(customerInfoStatus);
		}
	}

	public int getAgeForNextYear() {
		if (dateOfBirth != null) {
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
		return 0;
	}

	public String getFullName() {
		String result = "";
		if (name != null) {
			if (initialId != null && !initialId.isEmpty()) {
				result = initialId + " ";
			}
			if (name.getFirstName() != null && !name.getFirstName().isEmpty()) {
				result = result + name.getFirstName() + " ";
			}
			if (name.getMiddleName() != null && !name.getMiddleName().isEmpty()) {
				result = result + name.getMiddleName() + " ";
			}
			if (name.getLastName() != null && !name.getLastName().isEmpty()) {
				result = result + name.getLastName();
			}
		}
		return result;
	}

	public void addFamilyInfo(FamilyInfo familyInfo) {
		getFamilyInfo().add(familyInfo);
	}

	public PassportType getpType() {
		return pType;
	}

	public void setpType(PassportType pType) {
		this.pType = pType;
	}

	public HealthType getHealthType() {
		return healthType;
	}

	public void setHealthType(HealthType healthType) {
		this.healthType = healthType;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public void setTownshipCode(String townshipCode) {
		this.townshipCode = townshipCode;
	}

	public void loadTransientIdNo() {
		if (idType.equals(IdType.NRCNO) && fullIdNo != null && !fullIdNo.isEmpty()) {
			StringTokenizer token = new StringTokenizer(fullIdNo, "/()");
			stateCode = token.nextToken();
			townshipCode = token.nextToken();
			idConditionType = token.nextToken();
			idNo = token.nextToken();
			fullIdNo = stateCode.equals("null") ? "" : fullIdNo;
		} else if (idType.equals(IdType.FRCNO) || idType.equals(IdType.PASSPORTNO)) {
			idNo = fullIdNo == null ? "" : fullIdNo;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + activePolicy;
		result = prime * result + ((bankAccountNo == null) ? 0 : bankAccountNo.hashCode());
		result = prime * result + ((bankBranch == null) ? 0 : bankBranch.hashCode());
		result = prime * result + ((birthMark == null) ? 0 : birthMark.hashCode());
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + closedPolicy;
		result = prime * result + ((contentInfo == null) ? 0 : contentInfo.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((customerInfoStatusList == null) ? 0 : customerInfoStatusList.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((familyInfo == null) ? 0 : familyInfo.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((fullIdNo == null) ? 0 : fullIdNo.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + (guardian ? 1231 : 1237);
		result = prime * result + ((healthType == null) ? 0 : healthType.hashCode());
		long temp;
		temp = Double.doubleToLongBits(height);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((idConditionType == null) ? 0 : idConditionType.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((industry == null) ? 0 : industry.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((labourNo == null) ? 0 : labourNo.hashCode());
		result = prime * result + ((maritalStatus == null) ? 0 : maritalStatus.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((officeAddress == null) ? 0 : officeAddress.hashCode());
		result = prime * result + ((pType == null) ? 0 : pType.hashCode());
		result = prime * result + ((permanentAddress == null) ? 0 : permanentAddress.hashCode());
		result = prime * result + ((placeOfBirth == null) ? 0 : placeOfBirth.hashCode());
		result = prime * result + ((qualification == null) ? 0 : qualification.hashCode());
		result = prime * result + ((religion == null) ? 0 : religion.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((salary == null) ? 0 : salary.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((townshipCode == null) ? 0 : townshipCode.hashCode());
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		CustomerDTO other = (CustomerDTO) obj;
		if (activePolicy != other.activePolicy)
			return false;
		if (bankAccountNo == null) {
			if (other.bankAccountNo != null)
				return false;
		} else if (!bankAccountNo.equals(other.bankAccountNo))
			return false;
		if (bankBranch == null) {
			if (other.bankBranch != null)
				return false;
		} else if (!bankBranch.equals(other.bankBranch))
			return false;
		if (birthMark == null) {
			if (other.birthMark != null)
				return false;
		} else if (!birthMark.equals(other.birthMark))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (closedPolicy != other.closedPolicy)
			return false;
		if (contentInfo == null) {
			if (other.contentInfo != null)
				return false;
		} else if (!contentInfo.equals(other.contentInfo))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (customerInfoStatusList == null) {
			if (other.customerInfoStatusList != null)
				return false;
		} else if (!customerInfoStatusList.equals(other.customerInfoStatusList))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (familyInfo == null) {
			if (other.familyInfo != null)
				return false;
		} else if (!familyInfo.equals(other.familyInfo))
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
		if (guardian != other.guardian)
			return false;
		if (healthType != other.healthType)
			return false;
		if (Double.doubleToLongBits(height) != Double.doubleToLongBits(other.height))
			return false;
		if (idConditionType != other.idConditionType)
			return false;
		if (idNo == null) {
			if (other.idNo != null)
				return false;
		} else if (!idNo.equals(other.idNo))
			return false;
		if (idType != other.idType)
			return false;
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry))
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (labourNo == null) {
			if (other.labourNo != null)
				return false;
		} else if (!labourNo.equals(other.labourNo))
			return false;
		if (maritalStatus != other.maritalStatus)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (officeAddress == null) {
			if (other.officeAddress != null)
				return false;
		} else if (!officeAddress.equals(other.officeAddress))
			return false;
		if (pType != other.pType)
			return false;
		if (permanentAddress == null) {
			if (other.permanentAddress != null)
				return false;
		} else if (!permanentAddress.equals(other.permanentAddress))
			return false;
		if (placeOfBirth == null) {
			if (other.placeOfBirth != null)
				return false;
		} else if (!placeOfBirth.equals(other.placeOfBirth))
			return false;
		if (qualification == null) {
			if (other.qualification != null)
				return false;
		} else if (!qualification.equals(other.qualification))
			return false;
		if (religion == null) {
			if (other.religion != null)
				return false;
		} else if (!religion.equals(other.religion))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (salary == null) {
			if (other.salary != null)
				return false;
		} else if (!salary.equals(other.salary))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (townshipCode == null) {
			if (other.townshipCode != null)
				return false;
		} else if (!townshipCode.equals(other.townshipCode))
			return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

}