package org.ace.insurance.report.agent;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.Name;

public class AgentSanctionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sanctionNo;
	private Date sanctionDate;
	private String agentName;
	private String licenseNo;
	private double premium;
	private double comission;
	private String currencyCode;
	private String branchId;

	public AgentSanctionDTO() {

	}

	public AgentSanctionDTO(String sanctionNo, String salutation, Name name, String licenseNo, double premium, double comission, String currencyCode, Date sanctionDate,
			String branchId) {
		this.sanctionNo = sanctionNo;
		this.agentName = salutation + name.getFullName();
		this.licenseNo = licenseNo;
		this.premium = premium;
		this.comission = comission;
		this.currencyCode = currencyCode;
		this.sanctionDate = sanctionDate;
		this.branchId = branchId;
	}

	public String getSanctionNo() {
		return sanctionNo;
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

	public Date getSanctionDate() {
		return sanctionDate;
	}

	public String getBranchId() {
		return branchId;
	}

}
