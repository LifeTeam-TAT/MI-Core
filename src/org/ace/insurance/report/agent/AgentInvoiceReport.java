package org.ace.insurance.report.agent;

/**
 * @author NNH
 * @since 1.0.0
 * @date 2014/Feb/18
 */
import java.util.Date;
import java.util.List;

import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.payment.AgentCommission;

public class AgentInvoiceReport {

	private String invoiceNo;
	private Date invoiceDate;
	private String agentName;
	private String liscenseNo;
	private String agentCodeNo;
	private PolicyReferenceType referenceType;
	private double commissionAmount;
	private List<AgentCommission> agentCommissions;
	public String filePath;

	public AgentInvoiceReport() {
	}

	public AgentInvoiceReport(String invoiceNo, Date invoiceDate, String agentName, String liscenseNo, PolicyReferenceType referenceType, double commissionAmount,
			List<AgentCommission> agentCommissions) {
		this.invoiceNo = invoiceNo;
		this.invoiceDate = invoiceDate;
		this.agentName = agentName;
		this.liscenseNo = liscenseNo;
		this.referenceType = referenceType;
		this.commissionAmount = commissionAmount;
		this.agentCommissions = agentCommissions;
	}

	public AgentInvoiceReport(List<AgentCommission> agentCommissions) {
		this.invoiceNo = agentCommissions.get(0).getInvoiceNo();
		this.invoiceDate = agentCommissions.get(0).getInvoiceDate();
		this.agentName = agentCommissions.get(0).getAgent().getFullName();
		this.liscenseNo = agentCommissions.get(0).getAgent().getLiscenseNo();
		this.referenceType = agentCommissions.get(0).getReferenceType();
		this.agentCodeNo = agentCommissions.get(0).getAgent().getCodeNo();
		for (AgentCommission ac : agentCommissions) {
			commissionAmount = commissionAmount + ac.getCommission();
		}
		this.agentCommissions = agentCommissions;
	}

	public List<AgentCommission> getAgentCommissions() {
		return agentCommissions;
	}

	public void setAgentCommissions(List<AgentCommission> agentCommissions) {
		this.agentCommissions = agentCommissions;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public PolicyReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(PolicyReferenceType referenceType) {
		this.referenceType = referenceType;
	}

	public double getCommissionAmount() {
		return commissionAmount;
	}

	public void setCommissionAmount(double commissionAmount) {
		this.commissionAmount = commissionAmount;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAgentCodeNo() {
		return agentCodeNo;
	}

	public void setAgentCodeNo(String agentCodeNo) {
		this.agentCodeNo = agentCodeNo;
	}

}
