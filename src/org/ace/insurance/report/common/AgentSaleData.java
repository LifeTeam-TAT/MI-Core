package org.ace.insurance.report.common;

public class AgentSaleData {
	private String insuranceType;
	private long noOfpolicy;
	private double premium;

	public AgentSaleData() {
	}

	public AgentSaleData(String insuranceType, long noOfpolicy, double premium) {
		this.insuranceType = insuranceType;
		this.noOfpolicy = noOfpolicy;
		this.premium = premium;
	}

	public String getInsuranceType() {
		return insuranceType;
	}

	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}

	public long getNoOfpolicy() {
		return noOfpolicy;
	}

	public void setNoOfpolicy(long noOfpolicy) {
		this.noOfpolicy = noOfpolicy;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

}
