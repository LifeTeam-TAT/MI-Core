package org.ace.insurance.report.TLF;

public class AccountManualReceiptDTO {

	private String accountName;
	private double amount;
	private String createdDate;

	public AccountManualReceiptDTO() {
	}

	public AccountManualReceiptDTO(String accountName, double amount, String createdDate) {
		this.accountName = accountName;
		this.amount = amount;
		this.createdDate = createdDate;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

}
