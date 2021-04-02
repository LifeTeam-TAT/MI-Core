package org.ace.insurance.report.medical;

import java.util.Date;

import org.ace.insurance.report.medical.view.MedicalPolicyMonthlyReportView;

public class MedicalPolicyMonthlyReportDTO {
	private String policyNo;
	private String proposalNo;
	private String cashReceiptNo;
	private Date policyStartDate;
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

	public MedicalPolicyMonthlyReportDTO() {
	}

	public MedicalPolicyMonthlyReportDTO(MedicalPolicyMonthlyReportView view) {
		super();
		this.policyNo = view.getPolicyNo();
		this.proposalNo = view.getProposalNo();
		this.cashReceiptNo = view.getCashReceiptNo();
		this.policyStartDate = view.getPolicyStartDate();
		this.paymentDate = view.getPaymentDate();
		this.customerName = view.getCustomerName();
		this.nrcNo = view.getNrcNo();
		this.customerAddress = view.getCustomerAddress();
		this.fatherName = view.getFatherName();
		this.phoneNo = view.getPhoneNo();
		this.branchId = view.getBranchId();
		this.branch = view.getBranch();
		this.unit = view.getUnit();
		this.basicPlusUnit = view.getBasicPlusUnit();
		this.addOnUnit = view.getAddOnUnit();
		this.premium = view.getPremium();
		this.basicPlusPremium = view.getBasicPlusPremium();
		this.addOnUnitPremium = view.getAddOnUnitPremium();
		this.salePersonName = view.getSalePersonName();
		this.typeOfSalePerson = view.getTypeOfSalePerson();
		this.commission = view.getCommission();
	}

	public int getTotalUnit() {
		return this.unit + this.basicPlusUnit + this.addOnUnit;
	}

	public double getTotalPremium() {
		return premium + basicPlusPremium + addOnUnitPremium;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

}
