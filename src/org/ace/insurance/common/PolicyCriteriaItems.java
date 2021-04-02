package org.ace.insurance.common;

public enum PolicyCriteriaItems {
	POLICYNO("Policy No"), 
	CUSTOMERNAME("Customer Name"),
	ORGANIZATIONNAME("Organization Name"),
	BANKCUSTOMER("Bank Customer"), 
	REGISTRATION_NO("Registration No"), 
	INSUREDPERSON_NAME("Insuredperson Name"), 
	NRC_NO("NRCNO");
	
	private String label;

	private PolicyCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
