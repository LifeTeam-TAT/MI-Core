package org.ace.insurance.report.life;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.report.life.view.LifeProposalView;

public class LifeProposalReport implements ISorter {
	private static final long serialVersionUID = 1L;

	public String porposalId;
	public String porposalNo;
	public Date inspectionDate;
	public String customerName;
	public String fatherName;
	public String agentNameAndCode;
	public String addressAndPhoneNo;
	public double sumInsured;
	public double oneYearPremium;
	public double premium;
	public String branch;
	public String workflow;
	public String responsiblePerson;
	public String remark;

	public LifeProposalReport() {

	}

	public LifeProposalReport(LifeProposalView view) {
		this.porposalId = view.getId();
		this.inspectionDate = view.getDateOfProposed();
		this.porposalNo = view.getProposalNo();
		if (view.getCustomerName() != null) {
			this.customerName = view.getCustomerName();
		} else {
			this.customerName = view.getOrganizationName();
		}

		this.fatherName = view.getFatherName();
		this.agentNameAndCode = view.getAgentNameAndCodeNo();
		if (view.getCustomerName() != null) {
			this.addressAndPhoneNo = view.getCustomerAddressAndPhoneNumber();
		} else {
			this.addressAndPhoneNo = view.getOrganizationAddressAndPhoneNumber();
		}

		this.sumInsured = view.getSumInsured();
		this.oneYearPremium = view.getOneYearPremium();
		this.premium = view.getPremium();
		this.branch = view.getBranchName();
		this.workflow = view.getWorkflowStatus();
		this.responsiblePerson = view.getResponsiblePerson();

	}

	public String getPorposalId() {
		return porposalId;
	}

	public void setPorposalId(String porposalId) {
		this.porposalId = porposalId;
	}

	public String getPorposalNo() {
		return porposalNo;
	}

	public void setPorposalNo(String porposalNo) {
		this.porposalNo = porposalNo;
	}

	public Date getInspectionDate() {
		return inspectionDate;
	}

	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getAgentNameAndCode() {
		return agentNameAndCode;
	}

	public void setAgentNameAndCode(String agentNameAndCode) {
		this.agentNameAndCode = agentNameAndCode;
	}

	public String getAddressAndPhoneNo() {
		return addressAndPhoneNo;
	}

	public void setAddressAndPhoneNo(String addressAndPhoneNo) {
		this.addressAndPhoneNo = addressAndPhoneNo;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public double getOneYearPremium() {
		return oneYearPremium;
	}

	public void setOneYearPremium(double oneYearPremium) {
		this.oneYearPremium = oneYearPremium;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getWorkflow() {
		return workflow;
	}

	public void setWorkflow(String workflow) {
		this.workflow = workflow;
	}

	public String getResponsiblePerson() {
		return responsiblePerson;
	}

	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String getRegistrationNo() {
		return porposalNo;
	}
}
