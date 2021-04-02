// package org.ace.insurance.medicalpolicytermination;
//
// import java.io.Serializable;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
//
// import javax.persistence.CascadeType;
// import javax.persistence.Column;
// import javax.persistence.Embedded;
// import javax.persistence.Entity;
// import javax.persistence.EntityListeners;
// import javax.persistence.EnumType;
// import javax.persistence.Enumerated;
// import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.OneToMany;
// import javax.persistence.OneToOne;
// import javax.persistence.Table;
// import javax.persistence.TableGenerator;
// import javax.persistence.Temporal;
// import javax.persistence.TemporalType;
// import javax.persistence.Version;
//
// import org.ace.insurance.common.TableName;
// import org.ace.insurance.common.UserRecorder;
// import org.ace.insurance.common.interfaces.IInsuredItem;
// import org.ace.insurance.medical.claim.ClaimStatus;
// import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
// import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonAddOn;
// import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonAttachment;
// import
// org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonBeneficiaries;
// import org.ace.insurance.medical.policy.MedicalPolicyKeyFactorValue;
// import org.ace.insurance.medical.proposal.MedicalPersonHistoryRecord;
// import org.ace.insurance.product.Product;
// import org.ace.insurance.system.common.customer.Customer;
// import org.ace.insurance.system.common.occupation.Occupation;
// import org.ace.insurance.system.common.relationship.RelationShip;
// import org.ace.java.component.idgen.service.IDInterceptor;
//
// @Entity
// @Table(name = TableName.MEDPOLICY_INSUREDPERSON_TERMINATE)
// @EntityListeners(IDInterceptor.class)
// @TableGenerator(name = "MEDPOLICY_INSUREDPERSON_TERMINATE_GEN", table =
// "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL",
// pkColumnValue = "MEDPOLICY_INSUREDPERSON_TERMINATE_GEN", allocationSize = 10)
// public class MedicalPolicyInuredPersonTerminate implements IInsuredItem,
// Serializable {
// private static final long serialVersionUID = 1L;
//
// @Id
// @GeneratedValue(strategy = GenerationType.TABLE, generator =
// "MEDPOLICY_INSUREDPERSON_TERMINATE_GEN")
// private String id;
//
// private boolean actived;
// private boolean death;
//
// @Column(name = "HOSPITALDAYCOUNT")
// private int hosp_day_count;
// private int age;
// private int unit;
//
// private double sumInsured;
// private double premium;
//
// private double basicTermPremium;
// private double addonTermPremium;
//
// private double operationAmount;
// private double disabilityAmount;
//
// @Column(name = "INPERSONCODENO")
// private String insPersonCodeNo;
//
// @Column(name = "INPERSONGROUPCODENO")
// private String inPersonGroupCodeNo;
//
// @Temporal(TemporalType.DATE)
// private Date dateOfBirth;
//
// @Enumerated(EnumType.STRING)
// private ClaimStatus claimStatus;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "RELATIONSHIPID", referencedColumnName = "ID")
// private RelationShip relationship;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
// private Product product;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
// private Customer customer;
//
// @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
// @JoinColumn(name = "MEDICALPOLICYINSUREDPERSONGUARDIANID",
// referencedColumnName = "ID")
// private MedicalPolicyInsuredPersonGuardianTerminate guardian;
//
// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval =
// true)
// @JoinColumn(name = "MEDICALPOLICYINSUREDPERSONID", referencedColumnName =
// "ID")
// private List<MedicalPolicyInsuredPersonAttachmentTerminate> attachmentList;
//
// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval =
// true)
// @JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
// private List<MedicalPolicyInsuredPersonAddOnTerminate>
// policyInsuredPersonAddOnList;
//
// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval =
// true)
// @JoinColumn(name = "INSUREDPERSONID", referencedColumnName = "ID")
// private List<MedicalPersonHistoryRecordTerminate>
// medicalPersonHistoryRecordList;
//
// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval =
// true)
// @JoinColumn(name = "REFERENCEID", referencedColumnName = "ID")
// private List<MedicalPolicyKeyFactorValueTerminate> keyFactorValueList;
//
// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval =
// true)
// @JoinColumn(name = "POLICYINSUREDPERSONID", referencedColumnName = "ID")
// private List<MedicalPolicyInsuredPersonBeneficiariesTerminate>
// policyInsuredPersonBeneficiariesList;
//
// @Version
// private int version;
//
// @Embedded
// private UserRecorder recorder;
//
// public MedicalPolicyInuredPersonTerminate() {
// }
//
// public MedicalPolicyInuredPersonTerminate(MedicalPolicyInsuredPerson
// insuredPerson) {
// this.dateOfBirth = insuredPerson.getCustomer().getDateOfBirth();
// this.customer = insuredPerson.getCustomer();
// this.product = insuredPerson.getProduct();
// this.premium = insuredPerson.getPremium();
// this.relationship = insuredPerson.getRelationship();
// this.insPersonCodeNo = insuredPerson.getInsPersonCodeNo();
// this.inPersonGroupCodeNo = insuredPerson.getInPersonGroupCodeNo();
// this.age = insuredPerson.getAge();
// this.unit = insuredPerson.getUnit();
// this.sumInsured = insuredPerson.getSumInsured();
// this.basicTermPremium = insuredPerson.getBasicTermPremium();
// this.addonTermPremium = insuredPerson.getAddOnTermPremium();
// this.actived = insuredPerson.isActived();
// this.death = insuredPerson.isDeath();
// this.hosp_day_count = insuredPerson.getHosp_day_count();
// this.operationAmount = insuredPerson.getOperationAmount();
// this.disabilityAmount = insuredPerson.getDisabilityAmount();
//
// if (insuredPerson.getGuardian() != null) {
// this.guardian = new
// MedicalPolicyInsuredPersonGuardianTerminate(insuredPerson.getGuardian());
// }
// for (MedicalPolicyInsuredPersonAttachment attachment :
// insuredPerson.getAttachmentList()) {
// addAttachment(new MedicalPolicyInsuredPersonAttachmentTerminate(attachment));
// }
//
// for (MedicalPolicyKeyFactorValue keyFactorValue :
// insuredPerson.getKeyFactorValueList()) {
// addKeyFactorValue(new MedicalPolicyKeyFactorValueTerminate(keyFactorValue));
// }
//
// for (MedicalPolicyInsuredPersonBeneficiaries insuredPersonBeneficiaries :
// insuredPerson.getPolicyInsuredPersonBeneficiariesList()) {
// addInsuredPersonBeneficiaries(new
// MedicalPolicyInsuredPersonBeneficiariesTerminate(insuredPersonBeneficiaries));
// }
//
// for (MedicalPersonHistoryRecord record :
// insuredPerson.getMedicalPersonHistoryRecordList()) {
// addHistoryRecord(new MedicalPersonHistoryRecordTerminate(record));
// }
//
// for (MedicalPolicyInsuredPersonAddOn addOn :
// insuredPerson.getPolicyInsuredPersonAddOnList()) {
// addInsuredPersonAddOn(new MedicalPolicyInsuredPersonAddOnTerminate(addOn));
// }
// }
//
// public double getOperationAmount() {
// return operationAmount;
// }
//
// public void setOperationAmount(double operationAmount) {
// this.operationAmount = operationAmount;
// }
//
// public double getDisabilityAmount() {
// return disabilityAmount;
// }
//
// public void setDisabilityAmount(double disabilityAmount) {
// this.disabilityAmount = disabilityAmount;
// }
//
// public int getHosp_day_count() {
// return hosp_day_count;
// }
//
// public void setHosp_day_count(int hosp_day_count) {
// this.hosp_day_count = hosp_day_count;
// }
//
// public boolean isActived() {
// return actived;
// }
//
// public void setActived(boolean actived) {
// this.actived = actived;
// }
//
// public String getFullName() {
// return customer.getFullName();
// }
//
// public MedicalPolicyInsuredPersonGuardianTerminate getGuardian() {
// return guardian;
// }
//
// public void setGuardian(MedicalPolicyInsuredPersonGuardianTerminate guardian)
// {
// this.guardian = guardian;
// }
//
// public boolean isDeath() {
// return death;
// }
//
// public void setDeath(boolean death) {
// this.death = death;
// }
//
// public String getId() {
// return id;
// }
//
// public void setId(String id) {
// this.id = id;
// }
//
// public String getInsPersonCodeNo() {
// return insPersonCodeNo;
// }
//
// public void setInsPersonCodeNo(String insPersonCodeNo) {
// this.insPersonCodeNo = insPersonCodeNo;
// }
//
// public String getInPersonGroupCodeNo() {
// return inPersonGroupCodeNo;
// }
//
// public void setInPersonGroupCodeNo(String inPersonGroupCodeNo) {
// this.inPersonGroupCodeNo = inPersonGroupCodeNo;
// }
//
// public int getAge() {
// return age;
// }
//
// public void setAge(int age) {
// this.age = age;
// }
//
// public Date getDateOfBirth() {
// return dateOfBirth;
// }
//
// public void setDateOfBirth(Date dateOfBirth) {
// this.dateOfBirth = dateOfBirth;
// }
//
// public int getUnit() {
// return unit;
// }
//
// public void setUnit(int unit) {
// this.unit = unit;
// }
//
// public ClaimStatus getClaimStatus() {
// return claimStatus;
// }
//
// public void setClaimStatus(ClaimStatus claimStatus) {
// this.claimStatus = claimStatus;
// }
//
// public double getPremium() {
// return premium;
// }
//
// public void setPremium(double premium) {
// this.premium = premium;
// }
//
// public Product getProduct() {
// return product;
// }
//
// public void setProduct(Product product) {
// this.product = product;
// }
//
// public Customer getCustomer() {
// return customer;
// }
//
// public void setCustomer(Customer customer) {
// this.customer = customer;
// }
//
// public RelationShip getRelationship() {
// return relationship;
// }
//
// public void setRelationship(RelationShip relationship) {
// this.relationship = relationship;
// }
//
// public List<MedicalPolicyInsuredPersonAttachmentTerminate>
// getAttachmentList() {
// if (attachmentList == null) {
// attachmentList = new
// ArrayList<MedicalPolicyInsuredPersonAttachmentTerminate>();
// }
// return attachmentList;
// }
//
// public void
// setAttachmentList(List<MedicalPolicyInsuredPersonAttachmentTerminate>
// attachmentList) {
// this.attachmentList = attachmentList;
// }
//
// public List<MedicalPolicyInsuredPersonAddOnTerminate>
// getPolicyInsuredPersonAddOnList() {
// if (policyInsuredPersonAddOnList == null) {
// policyInsuredPersonAddOnList = new
// ArrayList<MedicalPolicyInsuredPersonAddOnTerminate>();
// }
// return policyInsuredPersonAddOnList;
// }
//
// public void
// setPolicyInsuredPersonAddOnList(List<MedicalPolicyInsuredPersonAddOnTerminate>
// policyInsuredPersonAddOnList) {
// this.policyInsuredPersonAddOnList = policyInsuredPersonAddOnList;
// }
//
// public List<MedicalPolicyKeyFactorValueTerminate> getKeyFactorValueList() {
// if (keyFactorValueList == null)
// keyFactorValueList = new ArrayList<MedicalPolicyKeyFactorValueTerminate>();
// return keyFactorValueList;
// }
//
// public void setKeyFactorValueList(List<MedicalPolicyKeyFactorValueTerminate>
// keyFactorValueList) {
// this.keyFactorValueList = keyFactorValueList;
// }
//
// public List<MedicalPolicyInsuredPersonBeneficiariesTerminate>
// getPolicyInsuredPersonBeneficiariesList() {
// if (this.policyInsuredPersonBeneficiariesList == null) {
// this.policyInsuredPersonBeneficiariesList = new
// ArrayList<MedicalPolicyInsuredPersonBeneficiariesTerminate>();
// }
// return this.policyInsuredPersonBeneficiariesList;
//
// }
//
// public void
// setPolicyInsuredPersonBeneficiariesList(List<MedicalPolicyInsuredPersonBeneficiariesTerminate>
// policyInsuredPersonBeneficiariesList) {
// this.policyInsuredPersonBeneficiariesList =
// policyInsuredPersonBeneficiariesList;
// }
//
// public int getVersion() {
// return version;
// }
//
// public void setVersion(int version) {
// this.version = version;
// }
//
// public List<MedicalPersonHistoryRecordTerminate>
// getMedicalPersonHistoryRecordList() {
// if (medicalPersonHistoryRecordList == null) {
// medicalPersonHistoryRecordList = new
// ArrayList<MedicalPersonHistoryRecordTerminate>();
// }
// return medicalPersonHistoryRecordList;
// }
//
// public void
// setMedicalPersonHistoryRecordList(List<MedicalPersonHistoryRecordTerminate>
// medicalPersonHistoryRecordList) {
// this.medicalPersonHistoryRecordList = medicalPersonHistoryRecordList;
// }
//
// public void addHistoryRecord(MedicalPersonHistoryRecordTerminate record) {
// getMedicalPersonHistoryRecordList().add(record);
// }
//
// public void addAttachment(MedicalPolicyInsuredPersonAttachmentTerminate
// attachment) {
// getAttachmentList().add(attachment);
// }
//
// public void addInsuredPersonAddOn(MedicalPolicyInsuredPersonAddOnTerminate
// policyInsuredPersonAddOn) {
// getPolicyInsuredPersonAddOnList().add(policyInsuredPersonAddOn);
// }
//
// public void addKeyFactorValue(MedicalPolicyKeyFactorValueTerminate
// keyFactorValue) {
// getKeyFactorValueList().add(keyFactorValue);
// }
//
// public void
// addInsuredPersonBeneficiaries(MedicalPolicyInsuredPersonBeneficiariesTerminate
// policyInsuredPersonBeneficiaries) {
// getPolicyInsuredPersonBeneficiariesList().add(policyInsuredPersonBeneficiaries);
// }
//
// public double getSumInsured() {
// return sumInsured;
// }
//
// public void setSumInsured(double sumInsured) {
// this.sumInsured = sumInsured;
// }
//
// public double getBasicTermPremium() {
// return basicTermPremium;
// }
//
// public void setBasicTermPremium(double basicTermPremium) {
// this.basicTermPremium = basicTermPremium;
// }
//
// public double getAddonTermPremium() {
// return addonTermPremium;
// }
//
// public void setAddonTermPremium(double addonTermPremium) {
// this.addonTermPremium = addonTermPremium;
// }
//
// public UserRecorder getRecorder() {
// return recorder;
// }
//
// public void setRecorder(UserRecorder recorder) {
// this.recorder = recorder;
// }
//
// /***************************
// * System Generated Method
// ***************************/
// public String getFatherName() {
// return this.customer.getFatherName();
// }
//
// public String getFullIdNo() {
// return this.customer.getFullIdNo();
// }
//
// public Occupation getOccupation() {
// return this.customer.getOccupation();
// }
//
// public String getFullAddress() {
// return this.customer.getFullAddress();
// }
//
// public String getGuardionName() {
// if (this.guardian != null) {
// return this.guardian.getCustomer().getFullName();
// } else {
// return "";
// }
// }
//
// public String getGuardionNRC() {
// if (this.guardian != null) {
// return this.guardian.getCustomer().getFullIdNo();
// } else {
// return "";
// }
// }
//
// public String getGuardionRelation() {
// if (this.guardian != null) {
// return this.guardian.getRelationship().getName();
// } else {
// return "";
// }
// }
//
// public int getTotalUnit() {
// int result = 0;
// for (MedicalPolicyInsuredPersonAddOnTerminate addOn :
// getPolicyInsuredPersonAddOnList()) {
// result += addOn.getUnit();
// }
// return result + getUnit();
// }
//
// public double getTotalPremium() {
// double result = getAddOnPremium() + getPremium();
// return result;
// }
//
// public double getAddOnPremium() {
// double premium = 0.0;
// for (MedicalPolicyInsuredPersonAddOnTerminate addOn :
// getPolicyInsuredPersonAddOnList()) {
// premium += addOn.getPremium();
// }
// return premium;
// }
//
// public double getTotalTermPremium() {
// return getBasicTermPremium() + getAddonTermPremium();
// }
//
// @Override
// public int hashCode() {
// final int prime = 31;
// int result = 1;
// result = prime * result + (actived ? 1231 : 1237);
// long temp;
// result = prime * result + age;
// temp = Double.doubleToLongBits(addonTermPremium);
// result = prime * result + (int) (temp ^ (temp >>> 32));
// temp = Double.doubleToLongBits(basicTermPremium);
// result = prime * result + (int) (temp ^ (temp >>> 32));
// result = prime * result + ((claimStatus == null) ? 0 :
// claimStatus.hashCode());
// result = prime * result + ((customer == null) ? 0 : customer.hashCode());
// result = prime * result + ((dateOfBirth == null) ? 0 :
// dateOfBirth.hashCode());
// result = prime * result + (death ? 1231 : 1237);
// temp = Double.doubleToLongBits(disabilityAmount);
// result = prime * result + (int) (temp ^ (temp >>> 32));
// result = prime * result + ((guardian == null) ? 0 : guardian.hashCode());
// result = prime * result + hosp_day_count;
// result = prime * result + ((id == null) ? 0 : id.hashCode());
// result = prime * result + ((inPersonGroupCodeNo == null) ? 0 :
// inPersonGroupCodeNo.hashCode());
// result = prime * result + ((insPersonCodeNo == null) ? 0 :
// insPersonCodeNo.hashCode());
// temp = Double.doubleToLongBits(operationAmount);
// result = prime * result + (int) (temp ^ (temp >>> 32));
// temp = Double.doubleToLongBits(premium);
// result = prime * result + (int) (temp ^ (temp >>> 32));
// result = prime * result + ((product == null) ? 0 : product.hashCode());
// result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
// result = prime * result + ((relationship == null) ? 0 :
// relationship.hashCode());
// result = prime * result + unit;
// result = prime * result + version;
// return result;
// }
//
// @Override
// public boolean equals(Object obj) {
// if (this == obj)
// return true;
// if (obj == null)
// return false;
// if (getClass() != obj.getClass())
// return false;
// MedicalPolicyInuredPersonTerminate other =
// (MedicalPolicyInuredPersonTerminate) obj;
// if (actived != other.actived)
// return false;
// if (age != other.age)
// return false;
// if (Double.doubleToLongBits(addonTermPremium) !=
// Double.doubleToLongBits(other.addonTermPremium))
// return false;
// if (Double.doubleToLongBits(basicTermPremium) !=
// Double.doubleToLongBits(other.basicTermPremium))
// return false;
// if (claimStatus != other.claimStatus)
// return false;
// if (customer == null) {
// if (other.customer != null)
// return false;
// } else if (!customer.equals(other.customer))
// return false;
// if (dateOfBirth == null) {
// if (other.dateOfBirth != null)
// return false;
// } else if (!dateOfBirth.equals(other.dateOfBirth))
// return false;
// if (death != other.death)
// return false;
// if (Double.doubleToLongBits(disabilityAmount) !=
// Double.doubleToLongBits(other.disabilityAmount))
// return false;
// if (guardian == null) {
// if (other.guardian != null)
// return false;
// } else if (!guardian.equals(other.guardian))
// return false;
// if (hosp_day_count != other.hosp_day_count)
// return false;
// if (id == null) {
// if (other.id != null)
// return false;
// } else if (!id.equals(other.id))
// return false;
// if (inPersonGroupCodeNo == null) {
// if (other.inPersonGroupCodeNo != null)
// return false;
// } else if (!inPersonGroupCodeNo.equals(other.inPersonGroupCodeNo))
// return false;
// if (insPersonCodeNo == null) {
// if (other.insPersonCodeNo != null)
// return false;
// } else if (!insPersonCodeNo.equals(other.insPersonCodeNo))
// return false;
// if (Double.doubleToLongBits(operationAmount) !=
// Double.doubleToLongBits(other.operationAmount))
// return false;
// if (Double.doubleToLongBits(premium) !=
// Double.doubleToLongBits(other.premium))
// return false;
// if (product == null) {
// if (other.product != null)
// return false;
// } else if (!product.equals(other.product))
// return false;
// if (recorder == null) {
// if (other.recorder != null)
// return false;
// } else if (!recorder.equals(other.recorder))
// return false;
// if (relationship == null) {
// if (other.relationship != null)
// return false;
// } else if (!relationship.equals(other.relationship))
// return false;
// if (unit != other.unit)
// return false;
// if (version != other.version)
// return false;
// return true;
// }
//
// @Override
// public double getAddOnSumInsure() {
// return 0;
// }
//
// @Override
// public double getAddOnTermPremium() {
// return addonTermPremium;
// }
// }
