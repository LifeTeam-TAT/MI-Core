package org.ace.insurance.life.policyEditHistory;

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
import javax.persistence.Version;

import org.ace.insurance.common.EndorsementStatus;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.IdType;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.claim.ClaimStatus;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.life.policy.PolicyInsuredPersonAddon;
import org.ace.insurance.life.policy.PolicyInsuredPersonAttachment;
import org.ace.insurance.life.policy.PolicyInsuredPersonBeneficiaries;
import org.ace.insurance.life.policy.PolicyInsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.ClassificationOfHealth;
import org.ace.insurance.life.proposal.InsuredPersonAddon;
import org.ace.insurance.life.proposal.InsuredPersonAttachment;
import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
import org.ace.insurance.life.proposal.InsuredPersonKeyFactorValue;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.occupation.Occupation;
import org.ace.insurance.system.common.riskyOccupation.RiskyOccupation;
import org.ace.insurance.system.common.typesOfSport.TypesOfSport;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFEPOLICYINSUREDPERSONEDITHISTORY)
@TableGenerator(name = "LPOLINSURPERSON_EDITHISTORY_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LPOLINSURPERSON_EDITHISTORY_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "PolicyInsuredPersonEditHistory.findAll", query = "SELECT s FROM PolicyInsuredPersonEditHistory s "),
		@NamedQuery(name = "PolicyInsuredPersonEditHistory.findById", query = "SELECT s FROM PolicyInsuredPersonEditHistory s WHERE s.id = :id") })
@EntityListeners(IDInterceptor.class)
public class PolicyInsuredPersonEditHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LPOLINSURPERSON_EDITHISTORY_GEN")
	private String id;

	@Column(name = "AGE")
	private int age;
	private double sumInsured;
	private double premium;
	private double basicTermPremium;
	private double addOnTermPremium;
	private double endorsementNetBasicPremium;
	private double endorsementNetAddonPremium;
	private double interest;
	@Column(name = "INPERSONCODENO")
	private String inPersonCodeNo;
	@Column(name = "INPERSONGROUPCODENO")
	private String inPersonGroupCodeNo;
	private String initialId;
	private String idNo;
	private String fatherName;

	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;

	@Enumerated(EnumType.STRING)
	private EndorsementStatus endorsementStatus;

	@Enumerated(EnumType.STRING)
	private ClaimStatus claimStatus;

	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Enumerated(value = EnumType.STRING)
	private IdType idType;

	@Enumerated(value = EnumType.STRING)
	private ClassificationOfHealth clsOfHealth;

	@Embedded
	private ResidentAddress residentAddress;

	@Embedded
	private Name name;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OCCUPATIONID", referencedColumnName = "ID")
	private Occupation occupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RISKYOCCUPATIONID", referencedColumnName = "ID")
	private RiskyOccupation riskyOccupation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TYPESOFSPORTID", referencedColumnName = "ID")
	private TypesOfSport typesOfSport;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFERENCENUMBER", referencedColumnName = "ID")
	private PolicyInsuredPerson policyInsuredPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LIFEPOLICYID", referencedColumnName = "ID")
	private LifePolicyEditHistory lifePolicy;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonAttachmentEditHistory> attachmentList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonAddonEditHistory> policyInsuredPersonAddOnList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonKeyFactorValueEditHistory> policyInsuredPersonkeyFactorValueList;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "policyInsuredPerson", orphanRemoval = true)
	private List<PolicyInsuredPersonBeneficiariesEditHistory> policyInsuredPersonBeneficiariesList;
	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public PolicyInsuredPersonEditHistory() {
	}

	public PolicyInsuredPersonEditHistory(ProposalInsuredPerson insuredPerson) {
		this.dateOfBirth = insuredPerson.getDateOfBirth();
		this.clsOfHealth = insuredPerson.getClsOfHealth();
		this.premium = insuredPerson.getProposedPremium();
		this.sumInsured = insuredPerson.getProposedSumInsured();
		this.product = insuredPerson.getProduct();
		this.endorsementNetAddonPremium = insuredPerson.getEndorsementNetAddonPremium();
		this.endorsementNetBasicPremium = insuredPerson.getEndorsementNetBasicPremium();
		this.interest = insuredPerson.getInterest();
		this.endorsementStatus = insuredPerson.getEndorsementStatus();
		this.inPersonCodeNo = insuredPerson.getInsPersonCodeNo();
		this.initialId = insuredPerson.getInitialId();
		this.idNo = insuredPerson.getIdNo();
		this.idType = insuredPerson.getIdType();
		this.name = insuredPerson.getName();
		this.gender = insuredPerson.getGender();
		this.residentAddress = insuredPerson.getResidentAddress();
		this.occupation = insuredPerson.getOccupation();
		this.fatherName = insuredPerson.getFatherName();
		this.age = insuredPerson.getAgeForNextYear();
		this.riskyOccupation = insuredPerson.getRiskyOccupation();

		for (InsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new PolicyInsuredPersonAttachmentEditHistory(attachment));
		}
		for (InsuredPersonAddon addOn : insuredPerson.getInsuredPersonAddOnList()) {
			addInsuredPersonAddOn(new PolicyInsuredPersonAddonEditHistory(addOn));
		}
		for (InsuredPersonKeyFactorValue keyFactorValue : insuredPerson.getKeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValueEditHistory(keyFactorValue));
		}
		for (InsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiariesEditHistory(insuredPersonBeneficiaries));
		}
	}

	public PolicyInsuredPersonEditHistory(PolicyInsuredPerson insuredPerson) {
		this.dateOfBirth = insuredPerson.getDateOfBirth();
		this.clsOfHealth = insuredPerson.getClsOfHealth();
		this.premium = insuredPerson.getPremium();
		this.sumInsured = insuredPerson.getSumInsured();
		this.product = insuredPerson.getProduct();
		this.basicTermPremium = insuredPerson.getBasicTermPremium();
		this.endorsementNetAddonPremium = insuredPerson.getEndorsementNetAddonPremium();
		this.endorsementNetBasicPremium = insuredPerson.getEndorsementNetBasicPremium();
		this.interest = insuredPerson.getInterest();
		this.endorsementStatus = insuredPerson.getEndorsementStatus();
		this.inPersonCodeNo = insuredPerson.getInsPersonCodeNo();
		this.policyInsuredPerson = insuredPerson;
		this.initialId = insuredPerson.getInitialId();
		this.idNo = insuredPerson.getIdNo();
		this.idType = insuredPerson.getIdType();
		this.name = insuredPerson.getName();
		this.gender = insuredPerson.getGender();
		this.residentAddress = insuredPerson.getResidentAddress();
		this.occupation = insuredPerson.getOccupation();
		this.riskyOccupation = insuredPerson.getRiskyOccupation();
		this.fatherName = insuredPerson.getFatherName();

		for (PolicyInsuredPersonAttachment attachment : insuredPerson.getAttachmentList()) {
			addAttachment(new PolicyInsuredPersonAttachmentEditHistory(attachment));
		}
		for (PolicyInsuredPersonAddon addOn : insuredPerson.getPolicyInsuredPersonAddOnList()) {
			addInsuredPersonAddOn(new PolicyInsuredPersonAddonEditHistory(addOn));
		}
		for (PolicyInsuredPersonKeyFactorValue keyFactorValue : insuredPerson.getPolicyInsuredPersonkeyFactorValueList()) {
			addPolicyInsuredPersonKeyFactorValue(new PolicyInsuredPersonKeyFactorValueEditHistory(keyFactorValue));
		}
		for (PolicyInsuredPersonBeneficiaries insuredPersonBeneficiaries : insuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
			addInsuredPersonBeneficiaries(new PolicyInsuredPersonBeneficiariesEditHistory(insuredPersonBeneficiaries));
		}
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

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
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

	public EndorsementStatus getEndorsementStatus() {
		return endorsementStatus;
	}

	public void setEndorsementStatus(EndorsementStatus endorsementStatus) {
		this.endorsementStatus = endorsementStatus;
	}

	public String getInPersonCodeNo() {
		return inPersonCodeNo;
	}

	public void setInPersonCodeNo(String inPersonCodeNo) {
		this.inPersonCodeNo = inPersonCodeNo;
	}

	public double getInterest() {
		return interest;
	}

	public void setInterest(double interest) {
		this.interest = interest;
	}

	public double getEndorsementNetAddonPremium() {
		return endorsementNetAddonPremium;
	}

	public void setEndorsementNetAddonPremium(double endorsementNetAddonPremium) {
		this.endorsementNetAddonPremium = endorsementNetAddonPremium;
	}

	public double getEndorsementNetBasicPremium() {
		return endorsementNetBasicPremium;
	}

	public void setEndorsementNetBasicPremium(double endorsementNetBasicPremium) {
		this.endorsementNetBasicPremium = endorsementNetBasicPremium;
	}

	public PolicyInsuredPerson getPolicyInsuredPerson() {
		return policyInsuredPerson;
	}

	public void setPolicyInsuredPerson(PolicyInsuredPerson policyInsuredPerson) {
		this.policyInsuredPerson = policyInsuredPerson;
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

	public List<PolicyInsuredPersonAttachmentEditHistory> getAttachmentList() {
		if (this.attachmentList == null) {
			this.attachmentList = new ArrayList<PolicyInsuredPersonAttachmentEditHistory>();
		}
		return this.attachmentList;
	}

	public void setAttachmentList(List<PolicyInsuredPersonAttachmentEditHistory> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public LifePolicyEditHistory getLifePolicy() {
		return lifePolicy;
	}

	public void setLifePolicy(LifePolicyEditHistory lifePolicy) {
		this.lifePolicy = lifePolicy;
	}

	public List<PolicyInsuredPersonAddonEditHistory> getPolicyInsuredPersonAddOnList() {
		return policyInsuredPersonAddOnList;
	}

	public void setPolicyInsuredPersonAddOnList(List<PolicyInsuredPersonAddonEditHistory> policyInsuredPersonAddOnList) {
		this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
	}

	public List<PolicyInsuredPersonKeyFactorValueEditHistory> getPolicyInsuredPersonkeyFactorValueList() {
		if (policyInsuredPersonkeyFactorValueList == null) {
			policyInsuredPersonkeyFactorValueList = new ArrayList<PolicyInsuredPersonKeyFactorValueEditHistory>();
		}
		return policyInsuredPersonkeyFactorValueList;
	}

	public void setPolicyInsuredPersonkeyFactorValueList(List<PolicyInsuredPersonKeyFactorValueEditHistory> policyInsuredPersonkeyFactorValueList) {
		this.policyInsuredPersonkeyFactorValueList = policyInsuredPersonkeyFactorValueList;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public TypesOfSport getTypesOfSport() {
		return typesOfSport;
	}

	public void setTypesOfSport(TypesOfSport typesOfSport) {
		this.typesOfSport = typesOfSport;
	}

	public String getInPersonGroupCodeNo() {
		return inPersonGroupCodeNo;
	}

	public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
		this.inPersonGroupCodeNo = inPersonGroupCodeNo;
	}

	public double getAddOnPremium() {
		double premium = 0.0;
		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
			for (PolicyInsuredPersonAddonEditHistory pia : policyInsuredPersonAddOnList) {
				premium = premium + pia.getPremium();
			}
		}
		return premium;
	}

	public double getTotalPermium() {
		return premium + getAddOnPremium();
	}

	public double getAddOnSumInsure() {
		double premium = 0.0;
		if (policyInsuredPersonAddOnList != null && !policyInsuredPersonAddOnList.isEmpty()) {
			for (PolicyInsuredPersonAddonEditHistory pia : policyInsuredPersonAddOnList) {
				premium = premium + pia.getSumInsured();
			}
		}
		return premium;
	}

	public List<PolicyInsuredPersonBeneficiariesEditHistory> getPolicyInsuredPersonBeneficiariesList() {
		if (this.policyInsuredPersonBeneficiariesList == null) {
			this.policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiariesEditHistory>();
		}
		return this.policyInsuredPersonBeneficiariesList;
	}

	public void setPolicyInsuredPersonBeneficiariesList(List<PolicyInsuredPersonBeneficiariesEditHistory> policyInsuredPersonBeneficiariesList) {
		this.policyInsuredPersonBeneficiariesList = policyInsuredPersonBeneficiariesList;
	}

	public void addAttachment(PolicyInsuredPersonAttachmentEditHistory attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<PolicyInsuredPersonAttachmentEditHistory>();
		}
		attachment.setPolicyInsuredPerson(this);
		attachmentList.add(attachment);
	}

	public void addInsuredPersonAddOn(PolicyInsuredPersonAddonEditHistory policyInsuredPersonAddOn) {
		if (policyInsuredPersonAddOnList == null) {
			policyInsuredPersonAddOnList = new ArrayList<PolicyInsuredPersonAddonEditHistory>();
		}
		policyInsuredPersonAddOn.setPolicyInsuredPersonInfo(this);
		policyInsuredPersonAddOnList.add(policyInsuredPersonAddOn);
	}

	public void addPolicyInsuredPersonKeyFactorValue(PolicyInsuredPersonKeyFactorValueEditHistory keyFactorValue) {
		if (policyInsuredPersonkeyFactorValueList == null) {
			policyInsuredPersonkeyFactorValueList = new ArrayList<PolicyInsuredPersonKeyFactorValueEditHistory>();
		}
		keyFactorValue.setPolicyInsuredPersonInfo(this);
		policyInsuredPersonkeyFactorValueList.add(keyFactorValue);

	}

	public void addInsuredPersonBeneficiaries(PolicyInsuredPersonBeneficiariesEditHistory policyInsuredPersonBeneficiaries) {
		if (policyInsuredPersonBeneficiariesList == null) {
			policyInsuredPersonBeneficiariesList = new ArrayList<PolicyInsuredPersonBeneficiariesEditHistory>();
		}
		policyInsuredPersonBeneficiaries.setPolicyInsuredPerson(this);
		policyInsuredPersonBeneficiariesList.add(policyInsuredPersonBeneficiaries);
	}

	public ClassificationOfHealth getClsOfHealth() {
		return clsOfHealth;
	}

	public void setClsOfHealth(ClassificationOfHealth clsOfHealth) {
		this.clsOfHealth = clsOfHealth;
	}

	public void setPaymentTimes(int paymentTimes) {
		// do nothing
	}

	public RiskyOccupation getRiskyOccupation() {
		return riskyOccupation;
	}

	public void setRiskyOccupation(RiskyOccupation riskyOccupation) {
		this.riskyOccupation = riskyOccupation;
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
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(addOnTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + age;
		temp = Double.doubleToLongBits(basicTermPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((idNo == null) ? 0 : idNo.hashCode());
		result = prime * result + ((idType == null) ? 0 : idType.hashCode());
		result = prime * result + ((inPersonCodeNo == null) ? 0 : inPersonCodeNo.hashCode());
		result = prime * result + ((inPersonGroupCodeNo == null) ? 0 : inPersonGroupCodeNo.hashCode());
		result = prime * result + ((initialId == null) ? 0 : initialId.hashCode());
		temp = Double.doubleToLongBits(interest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((policyInsuredPerson == null) ? 0 : policyInsuredPerson.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((residentAddress == null) ? 0 : residentAddress.hashCode());
		result = prime * result + ((riskyOccupation == null) ? 0 : riskyOccupation.hashCode());
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((typesOfSport == null) ? 0 : typesOfSport.hashCode());
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
		PolicyInsuredPersonEditHistory other = (PolicyInsuredPersonEditHistory) obj;
		if (Double.doubleToLongBits(addOnTermPremium) != Double.doubleToLongBits(other.addOnTermPremium))
			return false;
		if (age != other.age)
			return false;
		if (Double.doubleToLongBits(basicTermPremium) != Double.doubleToLongBits(other.basicTermPremium))
			return false;
		if (claimStatus != other.claimStatus)
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
		if (inPersonCodeNo == null) {
			if (other.inPersonCodeNo != null)
				return false;
		} else if (!inPersonCodeNo.equals(other.inPersonCodeNo))
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
		if (Double.doubleToLongBits(interest) != Double.doubleToLongBits(other.interest))
			return false;
		if (lifePolicy == null) {
			if (other.lifePolicy != null)
				return false;
		} else if (!lifePolicy.equals(other.lifePolicy))
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
		if (policyInsuredPerson == null) {
			if (other.policyInsuredPerson != null)
				return false;
		} else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
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
		if (riskyOccupation == null) {
			if (other.riskyOccupation != null)
				return false;
		} else if (!riskyOccupation.equals(other.riskyOccupation))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		if (typesOfSport == null) {
			if (other.typesOfSport != null)
				return false;
		} else if (!typesOfSport.equals(other.typesOfSport))
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
