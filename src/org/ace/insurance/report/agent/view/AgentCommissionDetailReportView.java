package org.ace.insurance.report.agent.view;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VW_REPORT_AGENT_COMMISSION_DETAIL)
@ReadOnly
public class AgentCommissionDetailReportView {

	@Id
	@Column(name = "POLICYID")
	private String policyId;

	@Column(name = "AGENTID")
	private String agentId;

	@Column(name = "POLICYNO")
	private String policyNo;

	@Column(name = "AGENTCODE")
	private String agentCode;

	@Column(name = "LISCENSENO")
	private String liscenseNo;

	@Column(name = "AGENTNAME")
	private String agentName;

	@Column(name = "AGENTADDRESS")
	private String agentAddress;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "INSURANCETYPE")
	@Enumerated(EnumType.STRING)
	private PolicyReferenceType insuranceType;

	@Column(name = "INVOICENO")
	private String invoiceNo;

	@Column(name = "RECEIPTNO")
	private String receiptNo;

	@Column(name = "COMMISSION")
	private double commission;

	@Column(name = "INVOICEDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date invoiceDate;

	@Column(name = "COMMISSIONSTARTDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date commissionDate;

	@Column(name = "STATUS")
	private boolean status;

	@Column(name = "ISPAID")
	private boolean isPaid;

	@Column(name = "REMARK")
	private String remark;

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

	public String getAgentAddress() {
		return agentAddress;
	}

	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public PolicyReferenceType getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(PolicyReferenceType insuranceType) {
		this.insuranceType = insuranceType;
	}

	public String getInvoiceNo() {
		return invoiceNo;
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

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getCommissionDate() {
		return commissionDate;
	}

	public void setCommissionDate(Date commissionDate) {
		this.commissionDate = commissionDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
