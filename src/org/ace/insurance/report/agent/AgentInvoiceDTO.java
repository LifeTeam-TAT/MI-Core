package org.ace.insurance.report.agent;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.Name;

public class AgentInvoiceDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String invoiceNo;
	private Date invoiceDate;
	private String agentName;
	private String licenseNo;
	private double premium;
	private double comission;
	private String currencyCode;
	private String branchId;

	public AgentInvoiceDTO() {

	}

	public AgentInvoiceDTO(String invoiceNo, String salutation, Name name, String licenseNo, double premium, double comission, String currencyCode, Date invoiceDate,
			String branchId) {
		this.invoiceNo = invoiceNo;
		this.agentName = salutation + name.getFullName();
		this.licenseNo = licenseNo;
		this.premium = premium;
		this.comission = comission;
		this.currencyCode = currencyCode;
		this.invoiceDate = invoiceDate;
		this.branchId = branchId;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getLicenseNo() {
		return licenseNo;
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

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public String getBranchId() {
		return branchId;
	}

}
