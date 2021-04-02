package org.ace.insurance.web.util;

import java.util.Date;

import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.common.TransactionType;
import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.user.User;

public class UserChangerCriteria {
	private Date startDate;
	private Date endDate;
	private String referenceNo;
	private WorkflowTask workflowTask;	
	private User responsibleUser;
	private ReferenceType referenceType;
	private TransactionType transactionType;

	public UserChangerCriteria() {
	}

	public UserChangerCriteria(Date startDate, Date endDate, String referenceNo, WorkflowTask workflowTask, TransactionType transactionType, String responsiblePerson,
			ReferenceType referenceType) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.referenceNo = referenceNo;
		this.workflowTask = workflowTask;
		this.transactionType = transactionType;
		this.referenceType = referenceType;
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getProposalNo() {
		return referenceNo;
	}

	public WorkflowTask getWorkflowTask() {
		return workflowTask;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public void setWorkflowTask(WorkflowTask workflowTask) {
		this.workflowTask = workflowTask;
	}

	

	public String getReferenceNo() {
		return referenceNo;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public User getResponsibleUser() {
		return responsibleUser;
	}

	public void setResponsibleUser(User responsibleUser) {
		this.responsibleUser = responsibleUser;
	}

}
