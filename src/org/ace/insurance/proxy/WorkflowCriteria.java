package org.ace.insurance.proxy;

import java.io.Serializable;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.User;

public class WorkflowCriteria implements Serializable {
	private static final long serialVersionUID = 1L;
	private ReferenceType referenceType;
	private WorkflowTask workflowTask;
	private User responsiblePerson;
	private TransactionType transactionType;
	private String branchId;

	/* used from workFlowChanger */
	public WorkflowCriteria(ReferenceType referenceType, WorkflowTask workflowTask, String branchId) {
		this.referenceType = referenceType;
		this.workflowTask = workflowTask;
		this.branchId = branchId;
	}

	public WorkflowCriteria(ReferenceType referenceType, WorkflowTask workflowTask, TransactionType workFlowProposalType, User responsiblePerson) {
		this.referenceType = referenceType;
		this.workflowTask = workflowTask;
		this.responsiblePerson = responsiblePerson;
		this.transactionType = workFlowProposalType;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public WorkflowTask getWorkflowTask() {
		return workflowTask;
	}

	public User getResponsiblePerson() {
		return responsiblePerson;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
}
