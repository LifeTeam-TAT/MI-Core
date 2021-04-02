package org.ace.insurance.payment;

public class PaymentOrderCashReceiptDTO {
	private String cashReceiptNo;
	
	private String suspendAccountCode;
	private String suspendAccountName;
	private double suspendDbAmount;
	
	private String premiumIncomeAccountCode;
	private String premiumIncomeAccountName;
	private double premiumIncomeCrAmount;
	
	public PaymentOrderCashReceiptDTO() {		
	}
	
	public PaymentOrderCashReceiptDTO(String cashReceiptNo, String suspendAccountCode,
			String suspendAccountName, double suspendDbAmount, String premiumIncomeAccountCode,
			String premiumIncomeAccountName, double premiumIncomeCrAmount) {
		super();
		this.cashReceiptNo = cashReceiptNo;
		this.suspendAccountCode = suspendAccountCode;
		this.suspendAccountName = suspendAccountName;
		this.suspendDbAmount = suspendDbAmount;
		this.premiumIncomeAccountCode = premiumIncomeAccountCode;
		this.premiumIncomeAccountName = premiumIncomeAccountName;
		this.premiumIncomeCrAmount = premiumIncomeCrAmount;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}

	public String getSuspendAccountCode() {
		return suspendAccountCode;
	}

	public void setSuspendAccountCode(String suspendAccountCode) {
		this.suspendAccountCode = suspendAccountCode;
	}

	public String getSuspendAccountName() {
		return suspendAccountName;
	}

	public void setSuspendAccountName(String suspendAccountName) {
		this.suspendAccountName = suspendAccountName;
	}

	public double getSuspendDbAmount() {
		return suspendDbAmount;
	}

	public void setSuspendDbAmount(double suspendDbAmount) {
		this.suspendDbAmount = suspendDbAmount;
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
	
}
