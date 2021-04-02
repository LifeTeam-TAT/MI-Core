package org.ace.insurance.report.common;

import java.util.Date;

import org.ace.insurance.common.WorkFlowType;

public class WorkFlowStatusReport {
	private String reqUser;
	private String respUser;
	private String proposalNo;
	private WorkFlowType workFlowType;
	private Date pendingSinceDate;
	//pending 
	public WorkFlowStatusReport(String reqUser, String respUser, String proposalNo, WorkFlowType workFlowType, Date pendingSinceDate) {
		this.reqUser = reqUser;
		this.respUser = respUser;
		this.proposalNo = proposalNo;
		this.workFlowType = workFlowType;
		this.pendingSinceDate = pendingSinceDate;
	}
	public String getReqUser() {
		return reqUser;
	}
	public String getRespUser() {
		return respUser;
	}
	public String getProposalNo() {
		return proposalNo;
	}
	public WorkFlowType getWorkFlowType() {
		return workFlowType;
	}
	public Date getPendingSinceDate() {
		return pendingSinceDate;
	}
}
