package org.ace.insurance.report.agent;

import java.util.Date;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.report.agent.view.AgentCommissionDetailReportView;

public class AgentCommissionDetailReport {

	public String agentCode;
	public String liscenseNo;
	public String agentName;
	public String phoneNo;
	public String address;
	public String businessType;
	public String policyNo;
	public double commission;
	public PolicyReferenceType insuranceType;
	public String remark;
	public Date invoiceDate;
	public String invoiceNo;
	public String receiptNo;
	
	public AgentCommissionDetailReport() {}
	
	public AgentCommissionDetailReport(String agentCode, String liscenseNo, String agentName,String phNo, String address, String policyNo,
										double commission, PolicyReferenceType insuranceType, String remark, Date invoiceDate, String invoiceNo, String receiptNo) {
		this.agentCode = agentCode;
		this.liscenseNo = liscenseNo;
		this.agentName = agentName;
		this.phoneNo = phNo;
		this.address = address;
		//this.businessType = businessType;
		this.commission = commission;
		this.insuranceType = insuranceType;
		this.remark = remark;
		this.policyNo = policyNo;
		this.invoiceDate = invoiceDate;
		this.invoiceNo = invoiceNo;
		this.receiptNo = receiptNo;
	}
	
	public AgentCommissionDetailReport(AgentCommissionDetailReportView view) {
		this.agentCode = view.getAgentCode();
		this.liscenseNo = view.getLiscenseNo();
		this.agentName = view.getAgentName();
		this.phoneNo = view.getPhone();
		this.address = view.getAgentAddress();
		this.commission = view.getCommission();
		this.insuranceType = view.getInsuranceType();
		this.remark = view.getRemark();
		this.policyNo = view.getPolicyNo();
		this.invoiceDate = view.getInvoiceDate();
		this.invoiceNo = view.getInvoiceNo();
		this.receiptNo = view.getReceiptNo();
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public PolicyReferenceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(PolicyReferenceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	
	
//	public String getPolicyNoFromDB() {
//		String policyNo = null;
//		//PolicyReferenceType rType = insuranceType;
//		if(insuranceType.equals(PolicyReferenceType.MOTOR_POLICY)) {
//			MotorPolicy mPolicy = mPolicyService.findMotorPolicyById(tempPolicy);
//			policyNo = mPolicy.getPolicyNo();
//		} else if(insuranceType.equals(PolicyReferenceType.FIRE_POLICY)) {
//			FirePolicy fPolicy = fPolicyService.findFirePolicyById(tempPolicy);
//			policyNo = fPolicy.getPolicyNo();
//		} else if(insuranceType.equals(PolicyReferenceType.LIFE_POLICY)) {
//			LifePolicy lPolicy = new LifePolicy();
//			lPolicy = lPolicyService.findLifePolicyById(tempPolicy);
//			policyNo = lPolicy.getPolicyNo();
//		} 
//		return policyNo;
//	}

}