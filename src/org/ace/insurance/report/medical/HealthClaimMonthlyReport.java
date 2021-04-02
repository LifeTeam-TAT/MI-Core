package org.ace.insurance.report.medical;

import java.util.Date;


import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.ISorter;

public class HealthClaimMonthlyReport implements ISorter {
	private static final long serialVersionUID = 288430480510991981L;
	
	private String id;
	private Date activedPolicyStartDate;
	private String policyNo;
	private String branchId;
	private String insuredPersonName;
	private Gender gender;
	private Date dateOfBirth;
	private int age;
	private Date injuredDate;
	private double totalClaimAmount;
	private String curedHospital1;
	private String curedHospital2;
	private String diseaseName;
	private int noOfHospitalizationDay;
	private String beneficiaryName;
	private String relationship;
	private String operationName1;
	private String operationName2;
	private Date deathDate;
	private int basicUnit;
	private double basicClaimAmount;
	private int basicPlusUnit;
	private double basicPlusClaimAmount;
	private int addOn1Unit;
	private double addOn1ClaimAmount;
	private int addOn2Unit;
	private double addOn2ClaimAmount;
	private String salePersonName;
	private CustomerType customerType;
	private String salePersonType;
	private double commission;
	
	

	public HealthClaimMonthlyReport(String id, Date activedPolicyStartDate, String policyNo, String branchId,
			String insuredPersonName, Gender gender, Date dateOfBirth, int age, Date injuredDate,
			double totalClaimAmount, String curedHospital1, String curedHospital2, String diseaseName,
			int noOfHospitalizationDay, String beneficiaryName, String relationship, String operationName1,
			String operationName2, Date deathDate, int basicUnit, double basicClaimAmount, int basicPlusUnit,
			double basicPlusClaimAmount, int addOn1Unit, double addOn1ClaimAmount, int addOn2Unit,
			double addOn2ClaimAmount, String salePersonName, CustomerType customerType, String salePersonType,
			double commission) {
		this.id = id;
		this.activedPolicyStartDate = activedPolicyStartDate;
		this.policyNo = policyNo;
		this.branchId = branchId;
		this.insuredPersonName = insuredPersonName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.age = age;
		this.injuredDate = injuredDate;
		this.totalClaimAmount = totalClaimAmount;
		this.curedHospital1 = curedHospital1;
		this.curedHospital2 = curedHospital2;
		this.diseaseName = diseaseName;
		this.noOfHospitalizationDay = noOfHospitalizationDay;
		this.beneficiaryName = beneficiaryName;
		this.relationship = relationship;
		this.operationName1 = operationName1;
		this.operationName2 = operationName2;
		this.deathDate = deathDate;
		this.basicUnit = basicUnit;
		this.basicClaimAmount = basicClaimAmount;
		this.basicPlusUnit = basicPlusUnit;
		this.basicPlusClaimAmount = basicPlusClaimAmount;
		this.addOn1Unit = addOn1Unit;
		this.addOn1ClaimAmount = addOn1ClaimAmount;
		this.addOn2Unit = addOn2Unit;
		this.addOn2ClaimAmount = addOn2ClaimAmount;
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

	public Date getInjuredDate() {
		return injuredDate;
	}

	public void setInjuredDate(Date injuredDate) {
		this.injuredDate = injuredDate;
	}

	public double getTotalClaimAmount() {
		return totalClaimAmount;
	}

	public void setTotalClaimAmount(double totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
	}

	public String getCuredHospital1() {
		return curedHospital1;
	}

	public void setCuredHospital1(String curedHospital1) {
		this.curedHospital1 = curedHospital1;
	}

	public String getCuredHospital2() {
		return curedHospital2;
	}

	public void setCuredHospital2(String curedHospital2) {
		this.curedHospital2 = curedHospital2;
	}

	public String getDiseaseName() {
		return diseaseName;
	}

	public void setDiseaseName(String diseaseName) {
		this.diseaseName = diseaseName;
	}

	public int getNoOfHospitalizationDay() {
		return noOfHospitalizationDay;
	}

	public void setNoOfHospitalizationDay(int noOfHospitalizationDay) {
		this.noOfHospitalizationDay = noOfHospitalizationDay;
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

	public String getOperationName1() {
		return operationName1;
	}

	public void setOperationName1(String operationName1) {
		this.operationName1 = operationName1;
	}

	public String getOperationName2() {
		return operationName2;
	}

	public void setOperationName2(String operationName2) {
		this.operationName2 = operationName2;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public int getBasicUnit() {
		return basicUnit;
	}

	public void setBasicUnit(int basicUnit) {
		this.basicUnit = basicUnit;
	}

	public double getBasicClaimAmount() {
		return basicClaimAmount;
	}

	public void setBasicClaimAmount(double basicClaimAmount) {
		this.basicClaimAmount = basicClaimAmount;
	}

	public int getBasicPlusUnit() {
		return basicPlusUnit;
	}

	public void setBasicPlusUnit(int basicPlusUnit) {
		this.basicPlusUnit = basicPlusUnit;
	}

	public double getBasicPlusClaimAmount() {
		return basicPlusClaimAmount;
	}

	public void setBasicPlusClaimAmount(double basicPlusClaimAmount) {
		this.basicPlusClaimAmount = basicPlusClaimAmount;
	}

	public int getAddOn1Unit() {
		return addOn1Unit;
	}

	public void setAddOn1Unit(int addOn1Unit) {
		this.addOn1Unit = addOn1Unit;
	}

	public double getAddOn1ClaimAmount() {
		return addOn1ClaimAmount;
	}

	public void setAddOn1ClaimAmount(double addOn1ClaimAmount) {
		this.addOn1ClaimAmount = addOn1ClaimAmount;
	}

	public int getAddOn2Unit() {
		return addOn2Unit;
	}

	public void setAddOn2Unit(int addOn2Unit) {
		this.addOn2Unit = addOn2Unit;
	}

	public double getAddOn2ClaimAmount() {
		return addOn2ClaimAmount;
	}

	public void setAddOn2ClaimAmount(double addOn2ClaimAmount) {
		this.addOn2ClaimAmount = addOn2ClaimAmount;
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
