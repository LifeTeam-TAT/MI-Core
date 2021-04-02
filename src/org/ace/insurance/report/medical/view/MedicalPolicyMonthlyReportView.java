package org.ace.insurance.report.medical.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.MEDICALPOLICYMONTHLYREPORT)
@ReadOnly
public class MedicalPolicyMonthlyReportView {
	@Id
	private String id;
	private String policyNo;
	private String proposalNo;
	private String cashReceiptNo;
	@Temporal(TemporalType.TIMESTAMP)
	private Date policyStartDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String customerName;
	private String nrcNo;
	private String customerAddress;
	private String fatherName;
	private String phoneNo;
	private String branchId;
	private String branch;
	private int unit;
	private int basicPlusUnit;
	private int addOnUnit;
	private double premium;
	private double basicPlusPremium;
	private double addOnUnitPremium;
	private String salePersonName;
	private String typeOfSalePerson;
	private double commission;

	public MedicalPolicyMonthlyReportView() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getNrcNo() {
		return nrcNo;
	}

	public void setNrcNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
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

	public int getAddOnUnit() {
		return addOnUnit;
	}

	public void setAddOnUnit(int addOnUnit) {
		this.addOnUnit = addOnUnit;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public double getBasicPlusPremium() {
		return basicPlusPremium;
	}

	public void setBasicPlusPremium(double basicPlusPremium) {
		this.basicPlusPremium = basicPlusPremium;
	}

	public double getAddOnUnitPremium() {
		return addOnUnitPremium;
	}

	public void setAddOnUnitPremium(double addOnUnitPremium) {
		this.addOnUnitPremium = addOnUnitPremium;
	}

	public String getSalePersonName() {
		return salePersonName;
	}

	public void setSalePersonName(String salePersonName) {
		this.salePersonName = salePersonName;
	}

	public String getTypeOfSalePerson() {
		return typeOfSalePerson;
	}

	public void setTypeOfSalePerson(String typeOfSalePerson) {
		this.typeOfSalePerson = typeOfSalePerson;
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
		result = prime * result + addOnUnit;
		long temp;
		temp = Double.doubleToLongBits(addOnUnitPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(basicPlusPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + basicPlusUnit;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((cashReceiptNo == null) ? 0 : cashReceiptNo.hashCode());
		temp = Double.doubleToLongBits(commission);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((customerAddress == null) ? 0 : customerAddress.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((fatherName == null) ? 0 : fatherName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nrcNo == null) ? 0 : nrcNo.hashCode());
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
		result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyStartDate == null) ? 0 : policyStartDate.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((proposalNo == null) ? 0 : proposalNo.hashCode());
		result = prime * result + ((salePersonName == null) ? 0 : salePersonName.hashCode());
		result = prime * result + ((typeOfSalePerson == null) ? 0 : typeOfSalePerson.hashCode());
		result = prime * result + unit;
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
		MedicalPolicyMonthlyReportView other = (MedicalPolicyMonthlyReportView) obj;
		if (addOnUnit != other.addOnUnit)
			return false;
		if (Double.doubleToLongBits(addOnUnitPremium) != Double.doubleToLongBits(other.addOnUnitPremium))
			return false;
		if (Double.doubleToLongBits(basicPlusPremium) != Double.doubleToLongBits(other.basicPlusPremium))
			return false;
		if (basicPlusUnit != other.basicPlusUnit)
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (cashReceiptNo == null) {
			if (other.cashReceiptNo != null)
				return false;
		} else if (!cashReceiptNo.equals(other.cashReceiptNo))
			return false;
		if (Double.doubleToLongBits(commission) != Double.doubleToLongBits(other.commission))
			return false;
		if (customerAddress == null) {
			if (other.customerAddress != null)
				return false;
		} else if (!customerAddress.equals(other.customerAddress))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (fatherName == null) {
			if (other.fatherName != null)
				return false;
		} else if (!fatherName.equals(other.fatherName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nrcNo == null) {
			if (other.nrcNo != null)
				return false;
		} else if (!nrcNo.equals(other.nrcNo))
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		if (phoneNo == null) {
			if (other.phoneNo != null)
				return false;
		} else if (!phoneNo.equals(other.phoneNo))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (policyStartDate == null) {
			if (other.policyStartDate != null)
				return false;
		} else if (!policyStartDate.equals(other.policyStartDate))
			return false;
		if (Double.doubleToLongBits(premium) != Double.doubleToLongBits(other.premium))
			return false;
		if (proposalNo == null) {
			if (other.proposalNo != null)
				return false;
		} else if (!proposalNo.equals(other.proposalNo))
			return false;
		if (salePersonName == null) {
			if (other.salePersonName != null)
				return false;
		} else if (!salePersonName.equals(other.salePersonName))
			return false;
		if (typeOfSalePerson == null) {
			if (other.typeOfSalePerson != null)
				return false;
		} else if (!typeOfSalePerson.equals(other.typeOfSalePerson))
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}

}
