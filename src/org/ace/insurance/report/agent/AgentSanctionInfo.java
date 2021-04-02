package org.ace.insurance.report.agent;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.PolicyReferenceType;

public class AgentSanctionInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String policyNo;
	private String receiptNo;
	private String agentName;
	private String licenseNo;
	private String customerName;
	private double premium;
	private double comission;
	private String currencyCode;
	private PolicyReferenceType referenceType;
	private String sanctionNo;
	private Date sanctionDate;
	private Date paymentDate;

	public AgentSanctionInfo() {
	}

	public AgentSanctionInfo(String id, String policyNo, String receiptNo, String agentSalutaiton, Name agentName, String licenseNo, String customerSalutaiton, Name customerName,
			String orgName, double premium, double comission, String currencyCode, PolicyReferenceType referenceType, String sanctionNo, Date sanctionDate, Date paymentDate) {
		this.id = id;
		this.policyNo = policyNo;
		this.receiptNo = receiptNo;
		this.agentName = agentSalutaiton + agentName.getFullName();
		this.licenseNo = licenseNo;
		this.customerName = customerName != null ? customerSalutaiton + " " + customerName.getFullName() : orgName;
		this.premium = premium;
		this.comission = comission;
		this.currencyCode = currencyCode;
		this.referenceType = referenceType;
		this.sanctionNo = sanctionNo;
		this.sanctionDate = sanctionDate;
		this.paymentDate = paymentDate;
	}

	public String getId() {
		return id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public double getPremium() {
		return premium;
	}

	public double getComission() {
		return comission;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

	public void setSanctionDate(Date sanctionDate) {
		this.sanctionDate = sanctionDate;
	}

}
