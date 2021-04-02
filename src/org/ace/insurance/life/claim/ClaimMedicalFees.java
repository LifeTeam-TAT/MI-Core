package org.ace.insurance.life.claim;

import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.HospitalCase;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.currency.Currency;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.hospital.Hospital;
import org.ace.insurance.system.common.organization.Organization;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.CLAIMMEDICALFEES)
@TableGenerator(name = "CLAIMMEDICALFEES_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "CLAIMMEDICALFEES_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class ClaimMedicalFees {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CLAIMMEDICALFEES_GEN")
	private String id;
	private String invoiceNo;
	private String sanctionNo;
	private String referenceNo;
	private String receiptNo;
	private String policyNo;
	private String claimNo;

	private double amount;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUR", referencedColumnName = "ID")
	private Currency currency;

	@Temporal(TemporalType.DATE)
	private Date medicalFeesStartDate;

	@Temporal(TemporalType.DATE)
	private Date invoiceDate;

	@Temporal(TemporalType.DATE)
	private Date sanctionDate;

	private boolean status;

	@Column(name = "REFERENCETYPE")
	@Enumerated(value = EnumType.STRING)
	private PolicyReferenceType referenceType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "ID")
	private Customer customer;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZATIONID", referencedColumnName = "ID")
	private Organization organization;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson claimPerson;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "HOSPITALID", referencedColumnName = "ID")
	private Hospital hospital;

	@Enumerated(EnumType.STRING)
	private HospitalCase hospitalCase;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date informDate;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public PolicyInsuredPerson getClaimPerson() {
		return claimPerson;
	}

	public void setClaimPerson(PolicyInsuredPerson claimPerson) {
		this.claimPerson = claimPerson;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

	public Date getMedicalFeesStartDate() {
		return medicalFeesStartDate;
	}

	public void setMedicalFeesStartDate(Date medicalFeesStartDate) {
		this.medicalFeesStartDate = medicalFeesStartDate;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public HospitalCase getHospitalCase() {
		return hospitalCase;
	}

	public void setHospitalCase(HospitalCase hospitalCase) {
		this.hospitalCase = hospitalCase;
	}

	
	public Date getInformDate() {
		return informDate;
	}

	public void setInformDate(Date informDate) {
		this.informDate = informDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((claimNo == null) ? 0 : claimNo.hashCode());
		result = prime * result + ((claimPerson == null) ? 0 : claimPerson.hashCode());
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((hospital == null) ? 0 : hospital.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((invoiceDate == null) ? 0 : invoiceDate.hashCode());
		result = prime * result + ((invoiceNo == null) ? 0 : invoiceNo.hashCode());
		result = prime * result + ((medicalFeesStartDate == null) ? 0 : medicalFeesStartDate.hashCode());
		result = prime * result + ((organization == null) ? 0 : organization.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((receiptNo == null) ? 0 : receiptNo.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
		result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
		result = prime * result + ((sanctionDate == null) ? 0 : sanctionDate.hashCode());
		result = prime * result + ((sanctionNo == null) ? 0 : sanctionNo.hashCode());
		result = prime * result + (status ? 1231 : 1237);
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
		ClaimMedicalFees other = (ClaimMedicalFees) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (claimNo == null) {
			if (other.claimNo != null)
				return false;
		} else if (!claimNo.equals(other.claimNo))
			return false;
		if (claimPerson == null) {
			if (other.claimPerson != null)
				return false;
		} else if (!claimPerson.equals(other.claimPerson))
			return false;
		if (currency == null) {
			if (other.currency != null)
				return false;
		} else if (!currency.equals(other.currency))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (hospital == null) {
			if (other.hospital != null)
				return false;
		} else if (!hospital.equals(other.hospital))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (invoiceDate == null) {
			if (other.invoiceDate != null)
				return false;
		} else if (!invoiceDate.equals(other.invoiceDate))
			return false;
		if (invoiceNo == null) {
			if (other.invoiceNo != null)
				return false;
		} else if (!invoiceNo.equals(other.invoiceNo))
			return false;
		if (medicalFeesStartDate == null) {
			if (other.medicalFeesStartDate != null)
				return false;
		} else if (!medicalFeesStartDate.equals(other.medicalFeesStartDate))
			return false;
		if (organization == null) {
			if (other.organization != null)
				return false;
		} else if (!organization.equals(other.organization))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (receiptNo == null) {
			if (other.receiptNo != null)
				return false;
		} else if (!receiptNo.equals(other.receiptNo))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (referenceNo == null) {
			if (other.referenceNo != null)
				return false;
		} else if (!referenceNo.equals(other.referenceNo))
			return false;
		if (referenceType != other.referenceType)
			return false;
		if (sanctionDate == null) {
			if (other.sanctionDate != null)
				return false;
		} else if (!sanctionDate.equals(other.sanctionDate))
			return false;
		if (sanctionNo == null) {
			if (other.sanctionNo != null)
				return false;
		} else if (!sanctionNo.equals(other.sanctionNo))
			return false;
		if (status != other.status)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
