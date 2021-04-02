package org.ace.insurance.proxy;

import java.io.Serializable;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkflowTask;

public class WF001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private WorkflowTask workflowTask;
	private ReferenceType referenceType;
	private int count;
	private TransactionType transactionType;

	public WF001() {
	}

	public WF001(WorkflowTask workflowTask, ReferenceType referenceType) {
		this.workflowTask = workflowTask;
		this.referenceType = referenceType;
	}

	public WF001(WorkflowTask workflowTask, ReferenceType referenceType, long count, TransactionType transactionType) {
		this.workflowTask = workflowTask;
		this.referenceType = referenceType;
		this.count = (int) count;
		this.transactionType = transactionType;
	}

	public WorkflowTask getWorkflowTask() {
		return workflowTask;
	}

	public void setWorkflowTask(WorkflowTask workflowTask) {
		this.workflowTask = workflowTask;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

}
