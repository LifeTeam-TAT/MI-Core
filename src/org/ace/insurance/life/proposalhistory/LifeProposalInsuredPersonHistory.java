package org.ace.insurance.life.proposalhistory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.proposal.ClassificationOfHealth;
import org.ace.insurance.life.proposal.InsuredPersonAddon;
import org.ace.insurance.life.proposal.InsuredPersonAttachment;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.insurance.web.manage.life.proposal.InsuredPersonInfoDTO;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEPROPOSAL_INSURED_PERSON_HISTORY)
@TableGenerator(name = "LIFEPROPOSAL_INSURED_PERSON_HISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFEPROPOSAL_INSURED_PERSON_HISTORY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "LifeProposalInsuredPersonHistory.findAll", query = "SELECT s FROM LifeProposalInsuredPersonHistory s "),
		@NamedQuery(name = "LifeProposalInsuredPersonHistory.findById", query = "SELECT s FROM LifeProposalInsuredPersonHistory s WHERE s.id = :id") })
@EntityListeners(IDInterceptor.class)
public class LifeProposalInsuredPersonHistory {

	private boolean approved;
	private boolean needMedicalCheckup;

	@Column(name = "AGE")
	private int age;
	private double proposedSumInsured;
	private double basicPremium;
	private double approvedSumInsured;
	private double basicTermPremium;
	private double addOnTermPremium;
	private double endorsementNetBasicPremium;
	private double endorsementNetAddonPremium;
	private double interest;
	private int weight;
	private int height;
	private double premiumRate;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFEPROPOSAL_INSURED_PERSON_HISTORY_GEN")
	private String id;

	private String rejectReason;

	@Column(name = "INPERSONCODENO")
	private String insPersonCodeNo;

	@Column(name = "INPERSONGROUPCODENO")
	private String inPersonGroupCodeNo;
	private String initialId;
	private String idNo;
	private String fatherName;

	@Transient
	private Boolean isPaidPremiumForPaidup;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Enumerated(EnumType.STRING)
	private EndorsementStatus endorsementStatus;

	@Enumerated(value = EnumType.STRING)
	private ClassificationOfHealth clsOfHealth;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPESOFSPORTID", referencedColumnName = "ID")
	private TypesOfSport typesOfSport;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPROPOSAL_HISTORY_ID", referencedColumnName = "ID")
	private LifeProposalHistory lifeProposalHistory;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposalInsuredPersonHistory", orphanRemoval = true)
	private List<LifeProposalInsuredPersonAttachmentHistory> lifeProposalInsuredPersonAttachmentHistoryList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposalInsuredPersonHistory", orphanRemoval = true)
	private List<LifeProposalInsuredPersonAddonHistory> lifeProposalInsuredPersonAddonHistoryList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposalInsuredPersonHistory", orphanRemoval = true)
	private List<LifeProposalInsuredPersonKeyFactorValueHistory> lifeProposalInsuredPersonKeyFactorValueHistoryList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "lifeProposalInsuredPersonHistory", orphanRemoval = true)
	private List<LifeProposalInsuredPersonBeneficiariesHistory> lifeProposalInsuredPersonBeneficiariesHistoryList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public LifeProposalInsuredPersonHistory() {

	}

	public LifeProposalInsuredPersonHistory(InsuredPersonInfoDTO dto) {
		this.approved = dto.isApprove();
		this.needMedicalCheckup = dto.isNeedMedicalCheckup();
		this.age = dto.getAgeForNextYear();
		this.basicPremium = dto.getPremium();
		this.proposedSumInsured = dto.getSumInsuredInfo();
		this.approvedSumInsured = dto.getApprovedSumInsured();
		this.basicTermPremium = dto.getBasicTermPremium();
		this.addOnTermPremium = dto.getAddOnTermPremium();
		this.endorsementNetAddonPremium = dto.getEndorsementAddonPremium();
		this.endorsementNetBasicPremium = dto.getEndorsementBasicPremium();
		this.interest = dto.getInterest();
		this.weight = dto.getWeight();
		this.height = dto.getHeight();
		this.premiumRate = dto.getPremiumRate();
		this.rejectReason = dto.getRejectReason();
		this.insPersonCodeNo = dto.getInsPersonCodeNo();
		this.inPersonGroupCodeNo = dto.getInPersonGroupCodeNo();
		this.initialId = dto.getInitialId();
		this.idNo = dto.getIdNo();
		this.fatherName = dto.getFatherName();
		this.dateOfBirth = dto.getDateOfBirth();
		this.endorsementStatus = dto.getEndorsementStatus();
		this.clsOfHealth = dto.getClassificationOfHealth();
		this.gender = dto.getGender();
		this.idType = dto.getIdType();
		this.residentAddress = dto.getResidentAddress();
		this.name = dto.getName();
		this.product = dto.getProduct();
		this.typesOfSport = dto.getTypesOfSport();
		this.occupation = dto.getOccupation();
		// override
		this.customer = dto.getCustomer();
	}

	public LifeProposalInsuredPersonHistory(Date dateOfBirth, double proposedSumInsured, Product product, LifeProposalHistory lifeproposalHistory, int periodMonth, Date startDate,
			Date endDate, double basicPremium, String initialId, String idNo, IdType idType, Name name, Gender gender, ResidentAddress residentAddress, Occupation occupation,
			String fatherName, double endorsementNetBasicPremium, double endorsementNetAddonPremium, double interest, int weight, int height, double premiumRate,
			EndorsementStatus status, String insPersonCodeNo, Boolean isPaidPremiumForPaidup, Customer customer, int age, String inPersonGroupCodeNo) {
		this.dateOfBirth = dateOfBirth;
		this.proposedSumInsured = proposedSumInsured;
		this.product = product;
		this.lifeProposalHistory = lifeproposalHistory;
		this.customer = customer;
		this.basicPremium = basicPremium;
		this.initialId = initialId;
		this.idNo = idNo;
		this.idType = idType;
		this.name = name;
		this.residentAddress = residentAddress;
		this.gender = gender;
		this.occupation = occupation;
		this.fatherName = fatherName;
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
		this.interest = interest;
		this.weight = weight;
		this.height = height;
		this.premiumRate = premiumRate;
		this.endorsementStatus = status;
		this.insPersonCodeNo = insPersonCodeNo;
		this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
		this.age = age;
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public LifeProposalInsuredPersonHistory(Date dateOfBirth, double proposedSumInsured, Product product, LifeProposalHistory lifeproposalHistory, int periodMonth, Date startDate,
			Date endDate, double basicPremium, double endorsementNetBasicPremium, double endorsementNetAddonPremium, double interest, int weight, int height, double premiumRate,
			EndorsementStatus status, String insPersonCodeNo, String inPersonGroupCodeNo) {
		this.dateOfBirth = dateOfBirth;
		this.proposedSumInsured = proposedSumInsured;
		this.product = product;
		this.lifeProposalHistory = lifeproposalHistory;
		this.basicPremium = basicPremium;
		this.endorsementStatus = status;
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
		this.insPersonCodeNo = insPersonCodeNo;
		this.interest = interest;
		this.weight = weight;
		this.height = height;
		this.premiumRate = premiumRate;
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;

	}

	public LifeProposalInsuredPersonHistory(PolicyInsuredPerson policyInsuredPerson) {
		this.clsOfHealth = policyInsuredPerson.getClsOfHealth();
		this.dateOfBirth = policyInsuredPerson.getDateOfBirth();
		this.product = policyInsuredPerson.getProduct();
		this.proposedSumInsured = policyInsuredPerson.getSumInsured();
		this.basicPremium = policyInsuredPerson.getPremium();
		this.approvedSumInsured = policyInsuredPerson.getSumInsured();
		this.insPersonCodeNo = policyInsuredPerson.getInsPersonCodeNo();
		this.initialId = policyInsuredPerson.getInitialId();
		this.idNo = policyInsuredPerson.getIdNo();
		this.idType = policyInsuredPerson.getIdType();
		this.name = policyInsuredPerson.getName();
		this.residentAddress = policyInsuredPerson.getResidentAddress();
		this.gender = policyInsuredPerson.getGender();
		this.occupation = policyInsuredPerson.getOccupation();
		this.fatherName = policyInsuredPerson.getFatherName();
		this.customer = policyInsuredPerson.getCustomer();
		this.age = policyInsuredPerson.getAge();
		this.inPersonGroupCodeNo = policyInsuredPerson.getInPersonGroupCodeNo();
	}

	public LifeProposalInsuredPersonHistory(ProposalInsuredPerson insuredPerson) {
		this.clsOfHealth = insuredPerson.getClsOfHealth();
		this.dateOfBirth = insuredPerson.getDateOfBirth();
		this.product = insuredPerson.getProduct();
		this.proposedSumInsured = insuredPerson.getProposedSumInsured();
		this.basicPremium = insuredPerson.getProposedPremium();
		this.approvedSumInsured = insuredPerson.getApprovedSumInsured();
		this.insPersonCodeNo = insuredPerson.getInsPersonCodeNo();
		this.initialId = insuredPerson.getInitialId();
		this.idNo = insuredPerson.getIdNo();
		this.idType = insuredPerson.getIdType();
		this.name = insuredPerson.getName();
		this.residentAddress = insuredPerson.getResidentAddress();
		this.gender = insuredPerson.getGender();
		this.occupation = insuredPerson.getOccupation();
		this.fatherName = insuredPerson.getFatherName();
		this.customer = insuredPerson.getCustomer();
		this.age = insuredPerson.getAge();
		this.inPersonGroupCodeNo = insuredPerson.getInPersonGroupCodeNo();

		for (InsuredPersonKeyFactorValue lifeKFValue : insuredPerson.getKeyFactorValueList()) {
			addLifeProposalKeyFactorValueHistory(new LifeProposalInsuredPersonKeyFactorValueHistory(lifeKFValue));
		}
		for (InsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getInsuredPersonBeneficiariesList()) {
			addLifeProposalInsuredPersonBeneficiaries(new LifeProposalInsuredPersonBeneficiariesHistory(insuredPersonBeneficiaries));
		}
		for (InsuredPersonAddon addon : insuredPerson.getInsuredPersonAddOnList()) {
			addLifeProposalInsuredPersonAddonHistory(new LifeProposalInsuredPersonAddonHistory(addon));
		}
		for (InsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addLifeProposalInsuredPersonAttachmentHistory(new LifeProposalInsuredPersonAttachmentHistory(attachment));
		}
	}

	public LifeProposalInsuredPersonHistory(Customer customer) {
		this.name = customer.getName();
		this.initialId = customer.getInitialId();
		this.fatherName = customer.getFatherName();
		this.idNo = customer.getIdNo();
		this.idType = customer.getIdType();
		this.dateOfBirth = customer.getDateOfBirth();
		this.residentAddress = customer.getResidentAddress();
		this.gender = customer.getGender();
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public double getProposedSumInsured() {
		return proposedSumInsured;
	}

	public void setProposedSumInsured(double proposedSumInsured) {
		this.proposedSumInsured = proposedSumInsured;
	}

	public double getBasicPremium() {
		return basicPremium;
	}

	public void setBasicPremium(double basicPremium) {
		this.basicPremium = basicPremium;
	}

	public double getApprovedSumInsured() {
		return approvedSumInsured;
	}

	public void setApprovedSumInsured(double approvedSumInsured) {
		this.approvedSumInsured = approvedSumInsured;
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

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		if (approved) {
			needMedicalCheckup = false;
			rejectReason = null;
			approvedSumInsured = 0.0;
		}
		this.approved = approved;
	}

	public boolean isNeedMedicalCheckup() {
		return needMedicalCheckup;
	}

	public void setNeedMedicalCheckup(boolean needMedicalCheckup) {
		this.needMedicalCheckup = needMedicalCheckup;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public List<LifeProposalInsuredPersonAttachmentHistory> getLifeProposalInsuredPersonAttachmentHistoryList() {
		if (this.lifeProposalInsuredPersonAttachmentHistoryList == null) {
			this.lifeProposalInsuredPersonAttachmentHistoryList = new ArrayList<LifeProposalInsuredPersonAttachmentHistory>();
		}
		return this.lifeProposalInsuredPersonAttachmentHistoryList;
	}

	public void setLifeProposalInsuredPersonAttachmentHistory(List<LifeProposalInsuredPersonAttachmentHistory> lifeProposalInsuredAttachmentHistoryList) {
		this.lifeProposalInsuredPersonAttachmentHistoryList = lifeProposalInsuredAttachmentHistoryList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LifeProposalHistory getLifeProposalHistory() {
		return lifeProposalHistory;
	}

	public void setLifeProposalHistory(LifeProposalHistory lifeProposalHistory) {
		this.lifeProposalHistory = lifeProposalHistory;
	}

	public List<LifeProposalInsuredPersonAddonHistory> getLifeProposalInsuredPersonAddonHistoryList() {
		if (lifeProposalInsuredPersonAddonHistoryList == null) {
			lifeProposalInsuredPersonAddonHistoryList = new ArrayList<LifeProposalInsuredPersonAddonHistory>();
		}
		return lifeProposalInsuredPersonAddonHistoryList;
	}

	public void setLifeProposalInsuredPersonAddonHistoryList(List<LifeProposalInsuredPersonAddonHistory> lifeProposalInsuredPersonAddonHistoryList) {
		this.lifeProposalInsuredPersonAddonHistoryList = lifeProposalInsuredPersonAddonHistoryList;
	}

	public List<LifeProposalInsuredPersonKeyFactorValueHistory> getLifeProposalInsuredPersonKeyFactorValueHistoryList() {
		if (lifeProposalInsuredPersonKeyFactorValueHistoryList == null) {
			lifeProposalInsuredPersonKeyFactorValueHistoryList = new ArrayList<LifeProposalInsuredPersonKeyFactorValueHistory>();
		}
		return lifeProposalInsuredPersonKeyFactorValueHistoryList;
	}

	public void setLifeProposalInsuredPersonKeyFactorValueHistoryList(List<LifeProposalInsuredPersonKeyFactorValueHistory> lifeProposalInsuredPersonKeyFactorValueHistoryList) {
		this.lifeProposalInsuredPersonKeyFactorValueHistoryList = lifeProposalInsuredPersonKeyFactorValueHistoryList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<LifeProposalInsuredPersonBeneficiariesHistory> getLifeProposalInsuredPersonBeneficiariesHistoryList() {
		if (this.lifeProposalInsuredPersonBeneficiariesHistoryList == null) {
			this.lifeProposalInsuredPersonBeneficiariesHistoryList = new ArrayList<LifeProposalInsuredPersonBeneficiariesHistory>();
		}
		return this.lifeProposalInsuredPersonBeneficiariesHistoryList;
	}

	public void setLifeProposalInsuredPersonBeneficiariesHistoryList(List<LifeProposalInsuredPersonBeneficiariesHistory> lifeProposalInsuredPersonBeneficiariesHistoryList) {
		this.lifeProposalInsuredPersonBeneficiariesHistoryList = lifeProposalInsuredPersonBeneficiariesHistoryList;
	}

	public void addLifeProposalKeyFactorValueHistory(LifeProposalInsuredPersonKeyFactorValueHistory lifeProposalInsuredPersonKeyFactorValueHistory) {
		lifeProposalInsuredPersonKeyFactorValueHistory.setLifeProposalInsuredPersonHistory(this);
		getLifeProposalInsuredPersonKeyFactorValueHistoryList().add(lifeProposalInsuredPersonKeyFactorValueHistory);
	}

	public void addLifeProposalInsuredPersonAddonHistory(LifeProposalInsuredPersonAddonHistory lifeProposalInsuredPersonAddonHistory) {
		lifeProposalInsuredPersonAddonHistory.setLifeProposalInsuredPersonHistory(this);
		getLifeProposalInsuredPersonAddonHistoryList().add(lifeProposalInsuredPersonAddonHistory);
	}

	public void addLifeProposalInsuredPersonAttachmentHistory(LifeProposalInsuredPersonAttachmentHistory lifeProposalInsuredPersonAttachmentHistory) {
		lifeProposalInsuredPersonAttachmentHistory.setLifeProposalInsuredPersonHistory(this);
		getLifeProposalInsuredPersonAttachmentHistoryList().add(lifeProposalInsuredPersonAttachmentHistory);
	}

	public void addLifeProposalInsuredPersonBeneficiaries(LifeProposalInsuredPersonBeneficiariesHistory lifeProposalInsuredPersonBeneficiariesHistory) {
		lifeProposalInsuredPersonBeneficiariesHistory.setLifeProposalInsuredPersonHistory(this);
		getLifeProposalInsuredPersonBeneficiariesHistoryList().add(lifeProposalInsuredPersonBeneficiariesHistory);
	}

	public ClassificationOfHealth getClsOfHealth() {
		return clsOfHealth;
	}

	public void setClsOfHealth(ClassificationOfHealth clsOfHealth) {
		this.clsOfHealth = clsOfHealth;
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

	public boolean isValidAttachment() {
		if (lifeProposalInsuredPersonAttachmentHistoryList == null || lifeProposalInsuredPersonAttachmentHistoryList.isEmpty()) {
			return false;
		}
		return true;
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

	public String getFullAddress() {
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

	public Occupation getOccupation() {
		return occupation;
	}

	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public EndorsementStatus getEndorsementStatus() {
		return endorsementStatus;
	}

	public void setEndorsementStatus(EndorsementStatus endorsementStatus) {
		this.endorsementStatus = endorsementStatus;
	}

	public double getEndorsementNetBasicPremium() {
		return endorsementNetBasicPremium;
	}

	public void setEndorsementNetBasicPremium(double endorsementNetBasicPremium) {
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
	}

	public double getEndorsementNetAddonPremium() {
		return endorsementNetAddonPremium;
	}

	public void setEndorsementNetAddonPremium(double endorsementNetAddonPremium) {
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getPremiumRate() {
		return premiumRate;
	}

	public void setPremiumRate(double premiumRate) {
		this.premiumRate = premiumRate;
	}

	public String getInsPersonCodeNo() {
		return insPersonCodeNo;
	}

	public void setInsPersonCodeNo(String insPersonCodeNo) {
		this.insPersonCodeNo = insPersonCodeNo;
	}

	public Boolean getIsPaidPremiumForPaidup() {
		return isPaidPremiumForPaidup;
	}

	public void setIsPaidPremiumForPaidup(Boolean isPaidPremiumForPaidup) {
		this.isPaidPremiumForPaidup = isPaidPremiumForPaidup;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getInPersonGroupCodeNo() {
		return inPersonGroupCodeNo;
	}

	public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public double getPremiumForOneThousandKyat() {
		return ((basicPremium * product.getBasedAmount()) / proposedSumInsured);
	}

	public TypesOfSport getTypesOfSport() {
		return typesOfSport;
	}

	public void setTypesOfSport(TypesOfSport typesOfSport) {
		this.typesOfSport = typesOfSport;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(addOnTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + age;
		result = prime * result + (approved ? 1231 : 1237);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(approvedSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(basicPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(basicTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((clsOfHealth == null) ? 0 : clsOfHealth.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		temp = Double.doubleToLongBits(endorsementNetAddonPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(endorsementNetBasicPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((endorsementStatus == null) ? 0 : endorsementStatus.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + height;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		result = prime * result + ((insPersonCodeNo == null) ? 0 : insPersonCodeNo.hashCode());
		temp = Double.doubleToLongBits(interest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((isPaidPremiumForPaidup == null) ? 0 : isPaidPremiumForPaidup.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (needMedicalCheckup ? 1231 : 1237);
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		temp = Double.doubleToLongBits(premiumRate);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		temp = Double.doubleToLongBits(proposedSumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((rejectReason == null) ? 0 : rejectReason.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((typesOfSport == null) ? 0 : typesOfSport.hashCode());
		result = prime * result + version;
		result = prime * result + weight;
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
		LifeProposalInsuredPersonHistory other = (LifeProposalInsuredPersonHistory) obj;
		if (Double.doubleToLongBits(addOnTermPremium) != Double.doubleToLongBits(other.addOnTermPremium))
			return false;
		if (age != other.age)
			return false;
		if (approved != other.approved)
			return false;
		if (Double.doubleToLongBits(approvedSumInsured) != Double.doubleToLongBits(other.approvedSumInsured))
			return false;
		if (Double.doubleToLongBits(basicPremium) != Double.doubleToLongBits(other.basicPremium))
			return false;
		if (Double.doubleToLongBits(basicTermPremium) != Double.doubleToLongBits(other.basicTermPremium))
			return false;
		if (clsOfHealth != other.clsOfHealth)
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (Double.doubleToLongBits(endorsementNetAddonPremium) != Double.doubleToLongBits(other.endorsementNetAddonPremium))
			return false;
		if (Double.doubleToLongBits(endorsementNetBasicPremium) != Double.doubleToLongBits(other.endorsementNetBasicPremium))
			return false;
		if (endorsementStatus != other.endorsementStatus)
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (gender != other.gender)
			return false;
		if (height != other.height)
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
		if (inPersonGroupCodeNo == null) {
			if (other.inPersonGroupCodeNo != null)
				return false;
		} else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
			return false;
		if (initialId == null) {
			if (other.initialId != null)
				return false;
		} else if (!initialId.equals(other.initialId))
			return false;
		if (insPersonCodeNo == null) {
			if (other.insPersonCodeNo != null)
				return false;
		} else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
			return false;
		if (Double.doubleToLongBits(interest) != Double.doubleToLongBits(other.interest))
			return false;
		if (isPaidPremiumForPaidup == null) {
			if (other.isPaidPremiumForPaidup != null)
				return false;
		} else if (!isPaidPremiumForPaidup.equals(other.isPaidPremiumForPaidup))
			return false;
		if (lifeProposalHistory == null) {
			if (other.lifeProposalHistory != null)
				return false;
		} else if (!lifeProposalHistory.equals(other.lifeProposalHistory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (needMedicalCheckup != other.needMedicalCheckup)
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
			return false;
		if (Double.doubleToLongBits(premiumRate) != Double.doubleToLongBits(other.premiumRate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (Double.doubleToLongBits(proposedSumInsured) != Double.doubleToLongBits(other.proposedSumInsured))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (rejectReason == null) {
			if (other.rejectReason != null)
				return false;
		} else if (!rejectReason.equals(other.rejectReason))
			return false;
		if (residentAddress == null) {
			if (other.residentAddress != null)
				return false;
		} else if (!residentAddress.equals(other.residentAddress))
			return false;
		if (typesOfSport == null) {
			if (other.typesOfSport != null)
				return false;
		} else if (!typesOfSport.equals(other.typesOfSport))
			return false;
		if (version != other.version)
			return false;
		if (weight != other.weight)
			return false;
		return true;
	}

}
