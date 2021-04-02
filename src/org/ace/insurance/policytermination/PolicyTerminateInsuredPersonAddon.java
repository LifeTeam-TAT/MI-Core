// package org.ace.insurance.policytermination;
//
// import javax.persistence.Embedded;
// import javax.persistence.Entity;
// import javax.persistence.EntityListeners;
// import javax.persistence.FetchType;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.JoinColumn;
// import javax.persistence.ManyToOne;
// import javax.persistence.OneToOne;
// import javax.persistence.Table;
// import javax.persistence.TableGenerator;
// import javax.persistence.Transient;
// import javax.persistence.Version;
//
// import org.ace.insurance.common.TableName;
// import org.ace.insurance.common.UserRecorder;
// import org.ace.insurance.life.policy.PolicyInsuredPersonAddon;
// import org.ace.insurance.life.proposal.InsuredPersonAddon;
// import org.ace.insurance.system.common.addon.AddOn;
// import org.ace.insurance.web.manage.life.proposal.InsuredPersonAddOnDTO;
// import org.ace.java.component.idgen.service.IDInterceptor;
//
// @Entity
// @Table(name = TableName.LIFEPOLICYTERMINATEINSUREDPERSONADDON)
// @TableGenerator(name = "LPOLICYTERMINATENSUREDPERSONADDON_GEN", table =
// "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL",
// pkColumnValue = "LPOLICYTERMINATENSUREDPERSONADDON_GEN", allocationSize = 10)
// @EntityListeners(IDInterceptor.class)
// public class PolicyTerminateInsuredPersonAddon {
//
// @Id
// @GeneratedValue(strategy = GenerationType.TABLE, generator =
// "LPOLICYTERMINATENSUREDPERSONADDON_GEN")
// private String id;
//
// private double premium;
// private double sumInsured;
// private double premiumRate;
//
// @OneToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "PRODUCTADDONID", referencedColumnName = "ID")
// private AddOn addOn;
//
// @ManyToOne(fetch = FetchType.LAZY)
// @JoinColumn(name = "LIFEPOLICYTERMINATEINSUREDPERSONID", referencedColumnName
// = "ID")
// private PolicyTerminateInsuredPerson policyInsuredPerson;
// @Embedded
// private UserRecorder recorder;
// @Version
// private int version;
//
// @Transient
// private boolean include;
//
// public PolicyTerminateInsuredPersonAddon() {
// }
//
// public PolicyTerminateInsuredPersonAddon(InsuredPersonAddon addOn) {
// this.premium = addOn.getProposedPremium();
// this.sumInsured = addOn.getProposedSumInsured();
// this.premiumRate = addOn.getPremiumRate();
// this.addOn = addOn.getAddOn();
// }
//
// public PolicyTerminateInsuredPersonAddon(PolicyInsuredPersonAddon addOn) {
// this.premium = addOn.getPremium();
// this.sumInsured = addOn.getSumInsured();
// this.premiumRate = addOn.getPremiumRate();
// this.addOn = addOn.getAddOn();
// }
//
// public PolicyTerminateInsuredPersonAddon(AddOn addOn, double addOnSumInsured)
// {
// this.addOn = addOn;
// this.sumInsured = addOnSumInsured;
// }
//
// public PolicyTerminateInsuredPersonAddon(AddOn addOn) {
// this.addOn = addOn;
// this.include = addOn.isCompulsory();
// }
//
// public PolicyTerminateInsuredPersonAddon(InsuredPersonAddOnDTO dto) {
// this.sumInsured = dto.getAddOnSumInsured();
// this.addOn = dto.getAddOn();
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
// public void overwriteId(String id) {
// this.id = id;
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
// public double getPremium() {
// return premium;
// }
//
// public void setPremium(double premium) {
// this.premium = premium;
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
// public double getPremiumRate() {
// return premiumRate;
// }
//
// public void setPremiumRate(double premiumRate) {
// this.premiumRate = premiumRate;
// }
//
// public AddOn getAddOn() {
// return addOn;
// }
//
// public void setAddOn(AddOn addOn) {
// this.addOn = addOn;
// }
//
// public PolicyTerminateInsuredPerson getPolicyInsuredPerson() {
// return policyInsuredPerson;
// }
//
// public void setPolicyInsuredPerson(PolicyTerminateInsuredPerson
// policyInsuredPerson) {
// this.policyInsuredPerson = policyInsuredPerson;
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
// public boolean isInclude() {
// return include;
// }
//
// public void setInclude(boolean include) {
// this.include = include;
// }
//
// @Override
// public int hashCode() {
// final int prime = 31;
// int result = 1;
// result = prime * result + ((addOn == null) ? 0 : addOn.hashCode());
// result = prime * result + ((id == null) ? 0 : id.hashCode());
// result = prime * result + ((policyInsuredPerson == null) ? 0 :
// policyInsuredPerson.hashCode());
// long temp;
// temp = Double.doubleToLongBits(premium);
// result = prime * result + (int) (temp ^ (temp >>> 32));
// temp = Double.doubleToLongBits(premiumRate);
// result = prime * result + (int) (temp ^ (temp >>> 32));
// result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
// temp = Double.doubleToLongBits(sumInsured);
// result = prime * result + (int) (temp ^ (temp >>> 32));
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
// PolicyTerminateInsuredPersonAddon other = (PolicyTerminateInsuredPersonAddon)
// obj;
// if (addOn == null) {
// if (other.addOn != null)
// return false;
// } else if (!addOn.equals(other.addOn))
// return false;
// if (id == null) {
// if (other.id != null)
// return false;
// } else if (!id.equals(other.id))
// return false;
// if (policyInsuredPerson == null) {
// if (other.policyInsuredPerson != null)
// return false;
// } else if (!policyInsuredPerson.equals(other.policyInsuredPerson))
// return false;
// if (Double.doubleToLongBits(premium) !=
// Double.doubleToLongBits(other.premium))
// return false;
// if (Double.doubleToLongBits(premiumRate) !=
// Double.doubleToLongBits(other.premiumRate))
// return false;
// if (recorder == null) {
// if (other.recorder != null)
// return false;
// } else if (!recorder.equals(other.recorder))
// return false;
// if (Double.doubleToLongBits(sumInsured) !=
// Double.doubleToLongBits(other.sumInsured))
// return false;
// if (version != other.version)
// return false;
// return true;
// }
//
// }
