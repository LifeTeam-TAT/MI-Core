package org.ace.insurance.life.claim;

import java.io.Serializable;
import java.util.Date;

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

import org.ace.insurance.common.ClaimInitialReporter;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.medical.claim.ClaimStatus;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.salesPoints.SalesPoints;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.LIFECLAIMNOTIFICATION)
@TableGenerator(name = "LIFECLAIMNOTIFICATION_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "LIFECLAIMNOTIFICATION_GEN", allocationSize = 10)
@EntityListeners(IDInterceptor.class)
public class LifeClaimNotification implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "LIFECLAIMNOTIFICATION_GEN")
	private String id;

	private String notificationNo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTID", referencedColumnName = "ID")
	private Product product;
	private String lifePolicyNo;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMPERSONID", referencedColumnName = "ID")
	private PolicyInsuredPerson claimPerson;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BRANCHID", referencedColumnName = "ID")
	private Branch branch;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SALEPOINTID", referencedColumnName = "ID")
	private SalesPoints salePoint;
	@Temporal(TemporalType.DATE)
	private Date reportedDate;
	@Temporal(TemporalType.DATE)
	private Date occuranceDate;
	@Enumerated(value = EnumType.STRING)
	private ClaimStatus claimStatus;
	

	@Embedded
	private ClaimInitialReporter claimInitialReporter;

	@Embedded
	private UserRecorder recorder;
	@Version
	private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNotificationNo() {
		return notificationNo;
	}

	public void setNotificationNo(String notificationNo) {
		this.notificationNo = notificationNo;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getLifePolicyNo() {
		return lifePolicyNo;
	}

	public void setLifePolicyNo(String lifePolicyNo) {
		this.lifePolicyNo = lifePolicyNo;
	}

	public PolicyInsuredPerson getClaimPerson() {
		return claimPerson;
	}

	public void setClaimPerson(PolicyInsuredPerson claimPerson) {
		this.claimPerson = claimPerson;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public SalesPoints getSalePoint() {
		return salePoint;
	}

	public void setSalePoint(SalesPoints salePoint) {
		this.salePoint = salePoint;
	}

	public Date getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}

	public Date getOccuranceDate() {
		return occuranceDate;
	}

	public void setOccuranceDate(Date occuranceDate) {
		this.occuranceDate = occuranceDate;
	}

	public ClaimStatus getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(ClaimStatus claimStatus) {
		this.claimStatus = claimStatus;
	}

	public ClaimInitialReporter getClaimInitialReporter() {
		if (claimInitialReporter == null) {
			claimInitialReporter = new ClaimInitialReporter();
		}
		return claimInitialReporter;
	}

	public void setClaimInitialReporter(ClaimInitialReporter claimInitialReporter) {
		this.claimInitialReporter = claimInitialReporter;
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

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branch == null) ? 0 : branch.hashCode());
		result = prime * result + ((claimInitialReporter == null) ? 0 : claimInitialReporter.hashCode());
		result = prime * result + ((claimPerson == null) ? 0 : claimPerson.hashCode());
		result = prime * result + ((claimStatus == null) ? 0 : claimStatus.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lifePolicyNo == null) ? 0 : lifePolicyNo.hashCode());
		result = prime * result + ((notificationNo == null) ? 0 : notificationNo.hashCode());
		result = prime * result + ((occuranceDate == null) ? 0 : occuranceDate.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((reportedDate == null) ? 0 : reportedDate.hashCode());
		result = prime * result + ((salePoint == null) ? 0 : salePoint.hashCode());
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
		LifeClaimNotification other = (LifeClaimNotification) obj;
		if (branch == null) {
			if (other.branch != null)
				return false;
		} else if (!branch.equals(other.branch))
			return false;
		if (claimInitialReporter == null) {
			if (other.claimInitialReporter != null)
				return false;
		} else if (!claimInitialReporter.equals(other.claimInitialReporter))
			return false;
		if (claimPerson == null) {
			if (other.claimPerson != null)
				return false;
		} else if (!claimPerson.equals(other.claimPerson))
			return false;
		if (claimStatus != other.claimStatus)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lifePolicyNo == null) {
			if (other.lifePolicyNo != null)
				return false;
		} else if (!lifePolicyNo.equals(other.lifePolicyNo))
			return false;
		if (notificationNo == null) {
			if (other.notificationNo != null)
				return false;
		} else if (!notificationNo.equals(other.notificationNo))
			return false;
		if (occuranceDate == null) {
			if (other.occuranceDate != null)
				return false;
		} else if (!occuranceDate.equals(other.occuranceDate))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (reportedDate == null) {
			if (other.reportedDate != null)
				return false;
		} else if (!reportedDate.equals(other.reportedDate))
			return false;
		if (salePoint == null) {
			if (other.salePoint != null)
				return false;
		} else if (!salePoint.equals(other.salePoint))
			return false;
		if (version != other.version)
			return false;
		return true;
	}



}
