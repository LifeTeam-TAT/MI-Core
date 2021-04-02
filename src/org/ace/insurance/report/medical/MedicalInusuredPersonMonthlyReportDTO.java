package org.ace.insurance.report.medical;

import java.util.Date;

import org.ace.insurance.report.medical.view.MedicalInsuredPersonMonthlyReportView;

public class MedicalInusuredPersonMonthlyReportDTO {
	private String id;
	private String branchid;
	private Date policyStartDate;
	private String policyNo;
	private String insuredName;
	private String nrc;
	private String occupation;
	private int age;
	private String address;
	private String gender;
	private String fullIdNo;
	private Date dateofBirth;

	private int unit;
	private int basicPlusUnit;
	private int addOnUnit;

	private double premium;
	private double basicPlusPremium;
	private double addOnPremium;

	private String disease;
	private String branch;

	public MedicalInusuredPersonMonthlyReportDTO() {
	}

	public MedicalInusuredPersonMonthlyReportDTO(MedicalInsuredPersonMonthlyReportView view) {
		this.policyStartDate = view.getPolicyStartDate();
		this.policyNo = view.getPolicyNo();
		this.insuredName = view.getInsuredName();
		this.nrc = view.getNrc();
		this.id = view.getId();
		this.branchid = view.getBranchid();
		this.occupation = view.getOccupation();
		this.age = view.getAge();
		this.address = view.getAddress();
		this.gender = view.getGender();
		this.dateofBirth = view.getDateofBirth();
		this.disease = view.getDisease();
		this.unit = view.getUnit();
		this.basicPlusUnit = view.getBasicPlusUnit();
		this.addOnUnit = view.getAddOnUnit();
		this.premium = view.getPremium();
		this.basicPlusPremium = view.getBasicPlusPremium();
		this.addOnPremium = view.getAddOnPremium();
		this.branch = view.getBranch();
	}

	public int getTotalUnit() {
		return unit + basicPlusUnit + addOnUnit;
	}

	public double getTotalPremium() {
		return premium + basicPlusPremium + addOnPremium;
	}

	public String getBranchid() {
		return branchid;
	}

	public void setBranchid(String branchid) {
		this.branchid = branchid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullIdNo() {
		return fullIdNo;
	}

	public void setFullIdNo(String fullIdNo) {
		this.fullIdNo = fullIdNo;
	}

	public Date getPolicyStartDate() {
		return policyStartDate;
	}

	public void setPolicyStartDate(Date policyStartDate) {
		this.policyStartDate = policyStartDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getNrc() {
		return nrc;
	}

	public void setNrc(String nrc) {
		this.nrc = nrc;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
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

	public double getAddOnPremium() {
		return addOnPremium;
	}

	public void setAddOnPremium(double addOnPremium) {
		this.addOnPremium = addOnPremium;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

}
