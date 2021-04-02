package org.ace.insurance.report.medical;

import java.util.Date;

import org.ace.insurance.common.WorkflowTask;
import org.ace.insurance.report.medical.view.HealthProposalReportView;

public class HealthProposalReportDTO {
	
	private String proposalNo;
	private Date dateOfProposed;
	private String customerName;
	private String nrcNo;
	private String fatherName;
	private String addressAndPhoneNo;
	private int basicUnit;
	private int additionalUnit;
	private int option1Unit;
	private int option2Unit;
	private double premium;
	private String agentNameAndCodeNo;
	private WorkflowTask workFlowTask;
	private String responsiblePerson;
	private String remark;
	private String branch;
	
	
	public HealthProposalReportDTO(HealthProposalReportView view) {
		this.proposalNo = view.getProposalNo();
		this.dateOfProposed = view.getDateOfProposed();
		this.customerName = view.getCustomerName();
		this.nrcNo = view.getNrcNo();
		this.fatherName = view.getFatherName();
		this.addressAndPhoneNo = view.getAddressAndPhoneNo();
		this.basicUnit = view.getBasicUnit();
		this.additionalUnit = view.getAdditionalUnit();
		this.option1Unit = view.getOption1Unit();
		this.option2Unit = view.getOption2Unit();
		this.premium = view.getPremium();
		this.agentNameAndCodeNo = view.getAgentNameAndCodeNo();
		this.workFlowTask = view.getWorkFlowTask();
		this.responsiblePerson = view.getResponsiblePerson();
		this.remark = view.getRemark();
		this.branch = view.getBranch();
	}


	public String getProposalNo() {
		return proposalNo;
	}


	public Date getDateOfProposed() {
		return dateOfProposed;
	}


	public String getCustomerName() {
		return customerName;
	}


	public String getNrcNo() {
		return nrcNo;
	}


	public String getFatherName() {
		return fatherName;
	}


	public String getAddressAndPhoneNo() {
		return addressAndPhoneNo;
	}


	public int getBasicUnit() {
		return basicUnit;
	}


	public int getAdditionalUnit() {
		return additionalUnit;
	}


	public int getOption1Unit() {
		return option1Unit;
	}


	public int getOption2Unit() {
		return option2Unit;
	}


	public double getPremium() {
		return premium;
	}


	public String getAgentNameAndCodeNo() {
		return agentNameAndCodeNo;
	}


	public WorkflowTask getWorkFlowTask() {
		return workFlowTask;
	}


	public String getResponsiblePerson() {
		return responsiblePerson;
	}


	public String getRemark() {
		return remark;
	}


	public String getBranch() {
		return branch;
	}
	
}
