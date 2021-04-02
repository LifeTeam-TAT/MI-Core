package org.ace.insurance.report.medical.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_MEDICAL_INSUREDPERSON_MONTHLY_REPORT)
@ReadOnly
public class MedicalInsuredPersonMonthlyReportView {
	@Id
	private String id;

	@Column(name = "BRANCHID")
	private String branchid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ACTIVEDPOLICYSTARTDATE")
	private Date policyStartDate;

	@Column(name = "POLICYNO")
	private String policyNo;

	@Column(name = "INSUREDPERSONNAME")
	private String insuredName;

	@Column(name = "IDNO")
	private String nrc;

	@Column(name = "OCCUPATION")
	private String occupation;

	@Column(name = "AGE")
	private int age;

	@Column(name = "RESIDENTADDRESS")
	private String address;

	private String gender;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEOFBIRTH")
	private Date dateofBirth;

	@Column(name = "CODE")
	private String disease;

	@Column(name = "BRANCH")
	private String branch;

	private int unit;
	private int basicPlusUnit;
	private int addOnUnit;

	private double premium;
	private double basicPlusPremium;
	private double addOnPremium;

	public MedicalInsuredPersonMonthlyReportView() {

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

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public double getAddOnPremium() {
		return addOnPremium;
	}

	public void setAddOnPremium(double addOnPremium) {
		this.addOnPremium = addOnPremium;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(addOnPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + addOnUnit;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + age;
		temp = Double.doubleToLongBits(basicPlusPremium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + basicPlusUnit;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((branchid == null) ? 0 : branchid.hashCode());
		result = prime * result + ((dateofBirth == null) ? 0 : dateofBirth.hashCode());
		result = prime * result + ((disease == null) ? 0 : disease.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuredName == null) ? 0 : insuredName.hashCode());
		result = prime * result + ((nrc == null) ? 0 : nrc.hashCode());
		result = prime * result + ((occupation == null) ? 0 : occupation.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((policyStartDate == null) ? 0 : policyStartDate.hashCode());
		temp = Double.doubleToLongBits(premium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		MedicalInsuredPersonMonthlyReportView other = (MedicalInsuredPersonMonthlyReportView) obj;
		if (Double.doubleToLongBits(addOnPremium) != Double.doubleToLongBits(other.addOnPremium))
			return false;
		if (addOnUnit != other.addOnUnit)
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (age != other.age)
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
		if (branchid == null) {
			if (other.branchid != null)
				return false;
		} else if (!branchid.equals(other.branchid))
			return false;
		if (dateofBirth == null) {
			if (other.dateofBirth != null)
				return false;
		} else if (!dateofBirth.equals(other.dateofBirth))
			return false;
		if (disease == null) {
			if (other.disease != null)
				return false;
		} else if (!disease.equals(other.disease))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuredName == null) {
			if (other.insuredName != null)
				return false;
		} else if (!insuredName.equals(other.insuredName))
			return false;
		if (nrc == null) {
			if (other.nrc != null)
				return false;
		} else if (!nrc.equals(other.nrc))
			return false;
		if (occupation == null) {
			if (other.occupation != null)
				return false;
		} else if (!occupation.equals(other.occupation))
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
		if (unit != other.unit)
			return false;
		return true;
	}

}
