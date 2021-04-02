package org.ace.insurance.payment;

public class CoinsuranceCashReceiptDTO {
	private String cashReceiptNo;
	private String sundryAccountCode;
	private String sundryAccountName;
	private double sundryDrAmount;
	private double sundryCrAmount;
	
	private String agentCommissionAcountCode;
	private String agentCommissionAccountName;
	private double agentCommissionCrAmount;
	
	private String premiumIncomeAccountCode;
	private String premiumIncomeAccountName;
	private double premiumIncomeCrAmount;
	
	private double siAmount;
	
	private boolean hasCoinsurance;	
	
	public CoinsuranceCashReceiptDTO() {
		
	}
	
	public CoinsuranceCashReceiptDTO(String cashReceiptNo, String sundryAccountCode,
			String sundryAccountName, double sundryDrAmount,
			double sundryCrAmount, String agentCommissionAcountCode,
			String agentCommissionAccountName, double agentCommissionCrAmount,
			String premiumIncomeAccountCode, String premiumIncomeAccountName,
			double premiumIncomeCrAmount, double siAmount, boolean hasCoinsurance) {
		super();
		this.cashReceiptNo = cashReceiptNo;
		this.sundryAccountCode = sundryAccountCode;
		this.sundryAccountName = sundryAccountName;
		this.sundryDrAmount = sundryDrAmount;
		this.sundryCrAmount = sundryCrAmount;
		this.agentCommissionAcountCode = agentCommissionAcountCode;
		this.agentCommissionAccountName = agentCommissionAccountName;
		this.agentCommissionCrAmount = agentCommissionCrAmount;
		this.premiumIncomeAccountCode = premiumIncomeAccountCode;
		this.premiumIncomeAccountName = premiumIncomeAccountName;
		this.premiumIncomeCrAmount = premiumIncomeCrAmount;
		this.siAmount = siAmount;
		this.hasCoinsurance = hasCoinsurance;
	}

	public String getSundryAccountCode() {
		return sundryAccountCode;
	}

	public void setSundryAccountCode(String sundryAccountCode) {
		this.sundryAccountCode = sundryAccountCode;
	}

	public String getSundryAccountName() {
		return sundryAccountName;
	}

	public void setSundryAccountName(String sundryAccountName) {
		this.sundryAccountName = sundryAccountName;
	}

	public double getSundryDrAmount() {
		return sundryDrAmount;
	}

	public void setSundryDrAmount(double sundryDrAmount) {
		this.sundryDrAmount = sundryDrAmount;
	}

	public double getSundryCrAmount() {
		return sundryCrAmount;
	}

	public void setSundryCrAmount(double sundryCrAmount) {
		this.sundryCrAmount = sundryCrAmount;
	}

	public String getAgentCommissionAcountCode() {
		return agentCommissionAcountCode;
	}

	public void setAgentCommissionAcountCode(String agentCommissionAcountCode) {
		this.agentCommissionAcountCode = agentCommissionAcountCode;
	}

	public String getAgentCommissionAccountName() {
		return agentCommissionAccountName;
	}

	public void setAgentCommissionAccountName(String agentCommissionAccountName) {
		this.agentCommissionAccountName = agentCommissionAccountName;
	}

	public double getAgentCommissionCrAmount() {
		return agentCommissionCrAmount;
	}

	public void setAgentCommissionCrAmount(double agentCommissionCrAmount) {
		this.agentCommissionCrAmount = agentCommissionCrAmount;
	}

	public String getPremiumIncomeAccountCode() {
		return premiumIncomeAccountCode;
	}

	public void setPremiumIncomeAccountCode(String premiumIncomeAccountCode) {
		this.premiumIncomeAccountCode = premiumIncomeAccountCode;
	}

	public String getPremiumIncomeAccountName() {
		return premiumIncomeAccountName;
	}

	public void setPremiumIncomeAccountName(String premiumIncomeAccountName) {
		this.premiumIncomeAccountName = premiumIncomeAccountName;
	}

	public double getPremiumIncomeCrAmount() {
		return premiumIncomeCrAmount;
	}

	public void setPremiumIncomeCrAmount(double premiumIncomeCrAmount) {
		this.premiumIncomeCrAmount = premiumIncomeCrAmount;
	}

	public double getSiAmount() {
		return siAmount;
	}

	public void setSiAmount(double siAmount) {
		this.siAmount = siAmount;
	}

	public boolean isHasCoinsurance() {
		return hasCoinsurance;
	}

	public void setHasCoinsurance(boolean hasCoinsurance) {
		this.hasCoinsurance = hasCoinsurance;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}	
	
}
