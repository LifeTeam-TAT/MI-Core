package org.ace.insurance.medical.claim;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.claim.Attachment;
import org.ace.insurance.common.TableName;
import org.ace.insurance.system.common.icd10.ICD10;
import org.ace.insurance.system.common.operation.Operation;

@Entity
@Table(name = TableName.OPERATIONCLAIM)
@DiscriminatorValue(value = MedicalClaimRole.OPERATION_CLAIM)
@NamedQueries(value = { @NamedQuery(name = "OperationClaim.findAll", query = "SELECT o FROM OperationClaim o "),
		@NamedQuery(name = "OperationClaim.findById", query = "SELECT o FROM OperationClaim o WHERE o.id = :id") })
public class OperationClaim extends MedicalClaim implements Serializable {

	private static final long serialVersionUID = 2975395072326798697L;

	@Column(name = "OPERATIONDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date operationDate;

	@Column(name = "OPERATIONFEE")
	private double operationFee;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATION_ID", referencedColumnName = "ID")
	private Operation operation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OPERATIONREASONID", referencedColumnName = "ID")
	private ICD10 operationReason;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "HOLDERID", referencedColumnName = "ID")
	private List<Attachment> attachmentList;

	@Column(name = "REMARK")
	private String operationRemark;

	public OperationClaim() {

	}

	public double getOperationFee() {
		return operationFee;
	}

	public void setOperationFee(double operationFee) {
		this.operationFee = operationFee;
	}

	public String getOperationRemark() {
		return operationRemark;
	}

	public void setOperationRemark(String operationRemark) {
		this.operationRemark = operationRemark;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public ICD10 getOperationReason() {
		return operationReason;
	}

	public void setOperationReason(ICD10 operationReason) {
		this.operationReason = operationReason;
	}

	public List<Attachment> getAttachmentList() {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public void addAttachment(Attachment attachment) {
		if (attachmentList == null) {
			attachmentList = new ArrayList<Attachment>();
		}
		attachmentList.add(attachment);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((operationDate == null) ? 0 : operationDate.hashCode());
		long temp;
		temp = Double.doubleToLongBits(operationFee);
		result = prime * result + ((operationReason == null) ? 0 : operationReason.hashCode());
		result = prime * result + ((operationRemark == null) ? 0 : operationRemark.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationClaim other = (OperationClaim) obj;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (operationDate == null) {
			if (other.operationDate != null)
				return false;
		} else if (!operationDate.equals(other.operationDate))
			return false;
		if (Double.doubleToLongBits(operationFee) != Double.doubleToLongBits(other.operationFee))
			return false;

		if (operationReason == null) {
			if (other.operationReason != null)
				return false;
		} else if (!operationReason.equals(other.operationReason))
			return false;
		if (operationRemark == null) {
			if (other.operationRemark != null)
				return false;
		} else if (!operationRemark.equals(other.operationRemark))
			return false;
		return true;
	}
}
