package org.ace.insurance.report.farmer;


import org.ace.insurance.common.ISorter;

public class FarmerMonthlyReport implements ISorter {
	private static final long serialVersionUID = 288430480510991981L;
	
	private String id;
	private String policyNo;
	private String insuredPersonName;
	private String address;
	private double sumInsured;
	private double premium;
	private double commission;
	private String cashReceiptNoAndPaymentDate;
	private String agentNameAndCode;

	
	
	
	
	public FarmerMonthlyReport(String id, String policyNo, String insuredPersonName, String address, double sumInsured,
			double premium, double commission, String cashReceiptNoAndPaymentDate, String agentNameAndCode) {
		this.id = id;
		this.policyNo = policyNo;
		this.insuredPersonName = insuredPersonName;
		this.address = address;
		this.sumInsured = sumInsured;
		this.premium = premium;
		this.commission = commission;
		this.cashReceiptNoAndPaymentDate = cashReceiptNoAndPaymentDate;
		this.agentNameAndCode = agentNameAndCode;
	}

	public String getId() {
		return id;
	}


	public String getPolicyNo() {
		return policyNo;
	}


	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	
	public String getAddress() {
		return address;
	}


	public double getSumInsured() {
		return sumInsured;
	}


	public double getPremium() {
		return premium;
	}


	public double getCommission() {
		return commission;
	}


	public String getCashReceiptNoAndPaymentDate() {
		return cashReceiptNoAndPaymentDate;
	}


	public String getAgentNameAndCode() {
		return agentNameAndCode;
	}


	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
	
}
