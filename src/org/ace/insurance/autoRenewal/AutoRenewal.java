package org.ace.insurance.autoRenewal;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.InsuranceType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.UserRecorder;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.AUTORENEWAL)
@TableGenerator(name = "AUTORENEWAL_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "AUTORENEWAL_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "AutoRenewal.updateStatus", query = "UPDATE AutoRenewal ar SET ar.status = :status WHERE ar.id = :id"),
		@NamedQuery(name = "AutoRenewal.findAll", query = "SELECT ar FROM AutoRenewal ar "),
		@NamedQuery(name = "AutoRenewal.findAllActiveInstance", query = "SELECT ar FROM AutoRenewal ar WHERE ar.status = :status") })
@EntityListeners(IDInterceptor.class)
public class AutoRenewal {
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "AUTORENEWAL_GEN")
	private String id;

	private String autoRenewalNo;
	private String policyId;
	private String policyNo;
	private String BranchId;
	private String customerName;
	private String customerNrc;

	@Enumerated(EnumType.STRING)
	private InsuranceType insuranceType;

	@Enumerated(EnumType.STRING)
	private AutoRenewalStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date activedPolicyEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduleEndDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date renewalDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date cancelDate;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public AutoRenewal() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getAutoRenewalNo() {
		return autoRenewalNo;
	}

	public void setAutoRenewalNo(String autoRenewalNo) {
		this.autoRenewalNo = autoRenewalNo;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getBranchId() {
		return BranchId;
	}

	public void setBranchId(String branchId) {
		BranchId = branchId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerNrc() {
		return customerNrc;
	}

	public void setCustomerNrc(String customerNrc) {
		this.customerNrc = customerNrc;
	}

	public InsuranceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(InsuranceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public AutoRenewalStatus getStatus() {
		return status;
	}

	public void setStatus(AutoRenewalStatus status) {
		this.status = status;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public Date getActivedPolicyEndDate() {
		return activedPolicyEndDate;
	}

	public void setActivedPolicyEndDate(Date activedPolicyEndDate) {
		this.activedPolicyEndDate = activedPolicyEndDate;
	}

	public Date getScheduleStartDate() {
		return scheduleStartDate;
	}

	public void setScheduleStartDate(Date scheduleStartDate) {
		this.scheduleStartDate = scheduleStartDate;
	}

	public Date getScheduleEndDate() {
		return scheduleEndDate;
	}

	public void setScheduleEndDate(Date scheduleEndDate) {
		this.scheduleEndDate = scheduleEndDate;
	}

	public Date getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
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
		result = prime * result + ((BranchId == null) ? 0 : BranchId.hashCode());
		result = prime * result + ((activedPolicyEndDate == null) ? 0 : activedPolicyEndDate.hashCode());
		result = prime * result + ((activedPolicyStartDate == null) ? 0 : activedPolicyStartDate.hashCode());
		result = prime * result + ((autoRenewalNo == null) ? 0 : autoRenewalNo.hashCode());
		result = prime * result + ((cancelDate == null) ? 0 : cancelDate.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((customerNrc == null) ? 0 : customerNrc.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((insuranceType == null) ? 0 : insuranceType.hashCode());
		result = prime * result + ((policyId == null) ? 0 : policyId.hashCode());
		result = prime * result + ((policyNo == null) ? 0 : policyNo.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((renewalDate == null) ? 0 : renewalDate.hashCode());
		result = prime * result + ((scheduleEndDate == null) ? 0 : scheduleEndDate.hashCode());
		result = prime * result + ((scheduleStartDate == null) ? 0 : scheduleStartDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		AutoRenewal other = (AutoRenewal) obj;
		if (BranchId == null) {
			if (other.BranchId != null)
				return false;
		} else if (!BranchId.equals(other.BranchId))
			return false;
		if (activedPolicyEndDate == null) {
			if (other.activedPolicyEndDate != null)
				return false;
		} else if (!activedPolicyEndDate.equals(other.activedPolicyEndDate))
			return false;
		if (activedPolicyStartDate == null) {
			if (other.activedPolicyStartDate != null)
				return false;
		} else if (!activedPolicyStartDate.equals(other.activedPolicyStartDate))
			return false;
		if (autoRenewalNo == null) {
			if (other.autoRenewalNo != null)
				return false;
		} else if (!autoRenewalNo.equals(other.autoRenewalNo))
			return false;
		if (cancelDate == null) {
			if (other.cancelDate != null)
				return false;
		} else if (!cancelDate.equals(other.cancelDate))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerNrc == null) {
			if (other.customerNrc != null)
				return false;
		} else if (!customerNrc.equals(other.customerNrc))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (insuranceType != other.insuranceType)
			return false;
		if (policyId == null) {
			if (other.policyId != null)
				return false;
		} else if (!policyId.equals(other.policyId))
			return false;
		if (policyNo == null) {
			if (other.policyNo != null)
				return false;
		} else if (!policyNo.equals(other.policyNo))
			return false;
		if (recorder == null) {
			if (other.recorder != null)
				return false;
		} else if (!recorder.equals(other.recorder))
			return false;
		if (renewalDate == null) {
			if (other.renewalDate != null)
				return false;
		} else if (!renewalDate.equals(other.renewalDate))
			return false;
		if (scheduleEndDate == null) {
			if (other.scheduleEndDate != null)
				return false;
		} else if (!scheduleEndDate.equals(other.scheduleEndDate))
			return false;
		if (scheduleStartDate == null) {
			if (other.scheduleStartDate != null)
				return false;
		} else if (!scheduleStartDate.equals(other.scheduleStartDate))
			return false;
		if (status != other.status)
			return false;
		if (version != other.version)
			return false;
		return true;
	}

}
