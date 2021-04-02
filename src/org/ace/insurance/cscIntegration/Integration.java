package org.ace.insurance.cscIntegration;

public class Integration {
	private String policyType;
	private String acePolicyNo;
	private String cscPolicyNo;
	private String aceCustomerName;
	private String cscCustomerKey;
	private String aceAgentName;
	private String cscAgentKey;
	private String branch;

	public Integration() {
	}

	public Integration(String policyType, String acePolicyNo, String cscPolicyNo, String aceCustomerName, String cscCustomerKey, String aceAgentName, String cscAgentKey,
			String branch) {
		super();
		this.policyType = policyType;
		this.acePolicyNo = acePolicyNo;
		this.cscPolicyNo = cscPolicyNo == null ? "" : cscPolicyNo;
		this.aceCustomerName = aceCustomerName;
		this.cscCustomerKey = cscCustomerKey == null ? "" : cscCustomerKey;
		this.aceAgentName = aceAgentName;
		this.cscAgentKey = cscAgentKey == null ? "" : cscAgentKey;
		this.branch = branch;
	}

	public String getPolicyType() {
		return policyType;
	}

	public String getAcePolicyNo() {
		return acePolicyNo;
	}

	public String getCscPolicyNo() {
		return cscPolicyNo;
	}

	public String getAceCustomerName() {
		return aceCustomerName;
	}

	public String getCscCustomerKey() {
		return cscCustomerKey;
	}

	public String getAceAgentName() {
		return aceAgentName;
	}

	public String getCscAgentKey() {
		return cscAgentKey;
	}

	public String getBranch() {
		return branch;
	}

}
