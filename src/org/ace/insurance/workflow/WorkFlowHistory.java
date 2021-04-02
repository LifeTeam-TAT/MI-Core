package org.ace.insurance.workflow;

import java.io.Serializable;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.UserRecorder;
import org.ace.insurance.common.WorkFlowDTO;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.User;
import org.ace.java.component.idgen.service.IDInterceptor;

@Entity
@Table(name = TableName.WORKFLOW_HIST)
@TableGenerator(name = "WORKFLOW_HIST_GEN", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "WORKFLOW_HIST_GEN", allocationSize = 10)
@NamedQueries(value = { @NamedQuery(name = "WorkFlowHistory.findAll", query = "SELECT a FROM WorkFlowHistory a "),
		@NamedQuery(name = "WorkFlowHistory.findById", query = "SELECT a FROM WorkFlowHistory a WHERE a.id = :id") })

@EntityListeners(IDInterceptor.class)
public class WorkFlowHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "WORKFLOW_HIST_GEN")
	private String id;
	private String branchId;
	private String referenceNo;
	private String remark;

	@Column(name = "WORKFLOWDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date workflowDate;

	@Enumerated(value = EnumType.STRING)
	private WorkflowTask workflowTask;

	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;

	@Enumerated(EnumType.STRING)
	private ReferenceType referenceType;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REQUESTORID", referencedColumnName = "ID")
	private User createdUser;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RESPONSIBLEUSERID", referencedColumnName = "ID")
	private User responsiblePerson;

	@Embedded
	private UserRecorder recorder;

	@Version
	private int version;

	public WorkFlowHistory() {
	}

	public WorkFlowHistory(WorkFlowDTO workFlowDTO) {
		this.referenceNo = workFlowDTO.getReferenceNo();
		this.branchId = workFlowDTO.getBranchId();
		this.remark = workFlowDTO.getRemark();
		this.workflowTask = workFlowDTO.getWorkflowTask();
		this.transactionType = workFlowDTO.getTransactionType();
		this.referenceType = workFlowDTO.getReferenceType();
		this.createdUser = workFlowDTO.getCreatedUser();
		this.responsiblePerson = workFlowDTO.getResponsiblePerson();
		this.workflowDate = new Date();
	}

	/* used from auto renewal */
	public WorkFlowHistory(String referenceNo, String branchId, String remark, WorkflowTask workflowTask, ReferenceType referenceType, User createdUser, User responsibleUser,
			Date todayDate) {
		this.referenceNo = referenceNo;
		this.branchId = branchId;
		this.remark = remark;
		this.workflowTask = workflowTask;
		this.referenceType = referenceType;
		this.createdUser = createdUser;
		this.responsiblePerson = responsibleUser;
		this.workflowDate = todayDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public UserRecorder getRecorder() {
		return recorder;
	}

	public void setRecorder(UserRecorder recorder) {
		this.recorder = recorder;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public WorkflowTask getWorkflowTask() {
		return workflowTask;
	}

	public void setWorkflowTask(WorkflowTask workflowTask) {
		this.workflowTask = workflowTask;
	}

	public User getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public User getResponsiblePerson() {
		return this.responsiblePerson;
	}

	public void setResponsiblePerson(User responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Date getWorkflowDate() {
		return workflowDate;
	}

	public void setWorkflowDate(Date workflowDate) {
		this.workflowDate = workflowDate;
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
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((createdUser == null) ? 0 : createdUser.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((recorder == null) ? 0 : recorder.hashCode());
		result = prime * result + ((referenceNo == null) ? 0 : referenceNo.hashCode());
		result = prime * result + ((referenceType == null) ? 0 : referenceType.hashCode());
		result = prime * result + ((remark == null) ? 0 : remark.hashCode());
		result = prime * result + ((responsiblePerson == null) ? 0 : responsiblePerson.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		result = prime * result + version;
		result = prime * result + ((workflowDate == null) ? 0 : workflowDate.hashCode());
		result = prime * result + ((workflowTask == null) ? 0 : workflowTask.hashCode());
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
		WorkFlowHistory other = (WorkFlowHistory) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (createdUser == null) {
			if (other.createdUser != null)
				return false;
		} else if (!createdUser.equals(other.createdUser))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		if (remark == null) {
			if (other.remark != null)
				return false;
		} else if (!remark.equals(other.remark))
			return false;
		if (responsiblePerson == null) {
			if (other.responsiblePerson != null)
				return false;
		} else if (!responsiblePerson.equals(other.responsiblePerson))
			return false;
		if (transactionType != other.transactionType)
			return false;
		if (version != other.version)
			return false;
		if (workflowDate == null) {
			if (other.workflowDate != null)
				return false;
		} else if (!workflowDate.equals(other.workflowDate))
			return false;
		if (workflowTask != other.workflowTask)
			return false;
		return true;
	}

}