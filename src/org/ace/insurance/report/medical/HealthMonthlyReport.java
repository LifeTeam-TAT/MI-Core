package org.ace.insurance.report.medical;

import java.util.Date;
import java.util.List;

import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.ISorter;
import org.ace.insurance.medical.policy.MedicalPolicy;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPerson;
import org.ace.insurance.medical.policy.MedicalPolicyInsuredPersonBeneficiaries;

public class HealthMonthlyReport implements ISorter {
	private static final long serialVersionUID = 288430480510991981L;
	
	private String id;
	private Date activedPolicyStartDate;
	private String policyNo;
	private String branchId;
	private String insuredPersonName;
	private Gender gender;
	private Date dateOfBirth;
	private int age;
	private String fullIdNo;
	private String occupation;
	private String address;
	private String paymentType;
	private Double premium;
	private String receiptNo;
	private Date paymentDate;
	private String beneficiaryName;
	private String relationship;
	private int unit;
	private int basicPlusUnit;
	private int addOn1;
	private int addOn2;
	private String salePersonName;
	private CustomerType customerType;
	private String salePersonType;
	private double commission;
	
	public HealthMonthlyReport() {
		
	}

	public HealthMonthlyReport(String id, Date activedPolicyStartDate, String policyNo, String branchId,
			String insuredPersonName, Gender gender, Date dateOfBirth, int age, String fullIdNo, String occupation,
			String address, String paymentType, Double premium, String receiptNo, Date paymentDate,
			String beneficiaryName, String relationship, int unit, int basicPlusUnit, int addOn1, int addOn2,
			String salePersonName, CustomerType customerType, String salePersonType, double commission) {
		this.id = id;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.policyNo = policyNo;
		this.branchId = branchId;
		this.insuredPersonName = insuredPersonName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.fullIdNo = fullIdNo;
		this.occupation = occupation;
		this.address = address;
		this.paymentType = paymentType;
		this.premium = premium;
		this.receiptNo = receiptNo;
		this.paymentDate = paymentDate;
		this.beneficiaryName = beneficiaryName;
		this.relationship = relationship;
		this.unit = unit;
		this.basicPlusUnit = basicPlusUnit;
		this.addOn1 = addOn1;
		this.addOn2 = addOn2;
		this.salePersonName = salePersonName;
		this.customerType = customerType;
		this.salePersonType = salePersonType;
		this.commission = commission;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Double getPremium() {
		return premium;
	}

	public void setPremium(Double premium) {
		this.premium = premium;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public int getBasicPlusUnit() {
		return basicPlusUnit;
	}

	public void setBasicPlusUnit(int basicPlusUnit) {
		this.basicPlusUnit = basicPlusUnit;
	}

	public int getAddOn1() {
		return addOn1;
	}

	public void setAddOn1(int addOn1) {
		this.addOn1 = addOn1;
	}

	public int getAddOn2() {
		return addOn2;
	}

	public void setAddOn2(int addOn2) {
		this.addOn2 = addOn2;
	}

	public String getSalePersonName() {
		return salePersonName;
	}

	public void setSalePersonName(String salePersonName) {
		this.salePersonName = salePersonName;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public String getSalePersonType() {
		return salePersonType;
	}

	public void setSalePersonType(String salePersonType) {
		this.salePersonType = salePersonType;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
	
}
