package org.ace.insurance.report.medical.view.Claim;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.Gender;
import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.HEALTH_CLAIM_MONTHLY_REPORT)
@ReadOnly
public class HealthClaimMonthlyView {
	
	@Id
	private String id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyStartDate;
	
	private String policyNo;
	private String branchId;
	private String insuredPersonName;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfBirth;
	
	private int age;
	
	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Temporal(TemporalType.TIMESTAMP)
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
	
	@Enumerated(EnumType.STRING)
	private CustomerType customerType;
	
	private String salePersonType;
	private double commission;
	
	public HealthClaimMonthlyView() {
		
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(addOn1ClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + addOn1Unit;
		temp = Double.doubleToLongBits(addOn2ClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + addOn2Unit;
		result = prime * result + age;
		temp = Double.doubleToLongBits(basicClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(basicPlusClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + basicPlusUnit;
		result = prime * result + basicUnit;
		result = prime * result + ((beneficiaryName == null) ? 0 : beneficiaryName.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		temp = Double.doubleToLongBits(commission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((curedHospital1 == null) ? 0 : curedHospital1.hashCode());
		result = prime * result + ((curedHospital2 == null) ? 0 : curedHospital2.hashCode());
		result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
		result = prime * result + ((dateOfBirth == null) ? 0 : dateOfBirth.hashCode());
		result = prime * result + ((deathDate == null) ? 0 : deathDate.hashCode());
		result = prime * result + ((diseaseName == null) ? 0 : diseaseName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((injuredDate == null) ? 0 : injuredDate.hashCode());
		result = prime * result + ((insuredPersonName == null) ? 0 : insuredPersonName.hashCode());
		result = prime * result + noOfHospitalizationDay;
		result = prime * result + ((operationName1 == null) ? 0 : operationName1.hashCode());
		result = prime * result + ((operationName2 == null) ? 0 : operationName2.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((relationship == null) ? 0 : relationship.hashCode());
		result = prime * result + ((salePersonName == null) ? 0 : salePersonName.hashCode());
		result = prime * result + ((salePersonType == null) ? 0 : salePersonType.hashCode());
		temp = Double.doubleToLongBits(totalClaimAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		HealthClaimMonthlyView other = (HealthClaimMonthlyView) obj;
		if (activedPolicyStartDate == null) {
			if (other.activedPolicyStartDate != null)
				return false;
		} else if (!activedPolicyStartDate.equals(other.activedPolicyStartDate))
			return false;
		if (Double.doubleToLongBits(addOn1ClaimAmount) != Double.doubleToLongBits(other.addOn1ClaimAmount))
			return false;
		if (addOn1Unit != other.addOn1Unit)
			return false;
		if (Double.doubleToLongBits(addOn2ClaimAmount) != Double.doubleToLongBits(other.addOn2ClaimAmount))
			return false;
		if (addOn2Unit != other.addOn2Unit)
			return false;
		if (age != other.age)
			return false;
		if (Double.doubleToLongBits(basicClaimAmount) != Double.doubleToLongBits(other.basicClaimAmount))
			return false;
		if (Double.doubleToLongBits(basicPlusClaimAmount) != Double.doubleToLongBits(other.basicPlusClaimAmount))
			return false;
		if (basicPlusUnit != other.basicPlusUnit)
			return false;
		if (basicUnit != other.basicUnit)
			return false;
		if (beneficiaryName == null) {
			if (other.beneficiaryName != null)
				return false;
		} else if (!beneficiaryName.equals(other.beneficiaryName))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (Double.doubleToLongBits(commission) != Double.doubleToLongBits(other.commission))
			return false;
		if (curedHospital1 == null) {
			if (other.curedHospital1 != null)
				return false;
		} else if (!curedHospital1.equals(other.curedHospital1))
			return false;
		if (curedHospital2 == null) {
			if (other.curedHospital2 != null)
				return false;
		} else if (!curedHospital2.equals(other.curedHospital2))
			return false;
		if (customerType != other.customerType)
			return false;
		if (dateOfBirth == null) {
			if (other.dateOfBirth != null)
				return false;
		} else if (!dateOfBirth.equals(other.dateOfBirth))
			return false;
		if (deathDate == null) {
			if (other.deathDate != null)
				return false;
		} else if (!deathDate.equals(other.deathDate))
			return false;
		if (diseaseName == null) {
			if (other.diseaseName != null)
				return false;
		} else if (!diseaseName.equals(other.diseaseName))
			return false;
		if (gender != other.gender)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (injuredDate == null) {
			if (other.injuredDate != null)
				return false;
		} else if (!injuredDate.equals(other.injuredDate))
			return false;
		if (insuredPersonName == null) {
			if (other.insuredPersonName != null)
				return false;
		} else if (!insuredPersonName.equals(other.insuredPersonName))
			return false;
		if (noOfHospitalizationDay != other.noOfHospitalizationDay)
			return false;
		if (operationName1 == null) {
			if (other.operationName1 != null)
				return false;
		} else if (!operationName1.equals(other.operationName1))
			return false;
		if (operationName2 == null) {
			if (other.operationName2 != null)
				return false;
		} else if (!operationName2.equals(other.operationName2))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (relationship == null) {
			if (other.relationship != null)
				return false;
		} else if (!relationship.equals(other.relationship))
			return false;
		if (salePersonName == null) {
			if (other.salePersonName != null)
				return false;
		} else if (!salePersonName.equals(other.salePersonName))
			return false;
		if (salePersonType == null) {
			if (other.salePersonType != null)
				return false;
		} else if (!salePersonType.equals(other.salePersonType))
			return false;
		if (Double.doubleToLongBits(totalClaimAmount) != Double.doubleToLongBits(other.totalClaimAmount))
			return false;
		return true;
	}
	
	
	
}
