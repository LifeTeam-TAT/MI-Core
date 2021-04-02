package org.ace.insurance.report.TLF;

import java.util.Date;

public class SalePointIncomeReportDTO {

	private String tlfNo;
	private String coaId;
	private String narration;
	private String salePointId;
	private String salePointName;
	private String paymentChannel;
	private Date createdDate;
	private double cashDebit;
	private double cashCredit;
	private double transferDebit;
	private double transferCredit;
	private String policyNo;
	private boolean agentTransaction;
	private String accountType;

	public SalePointIncomeReportDTO() {

	}

	public SalePointIncomeReportDTO(String tlfNo, String coaId, String narration, String salePointName, String salePointId, String paymentChannel, Date createdDate,
			double cashDebit, double cashCredit, double transferDebit, double transferCredit, String policyNo, boolean agentTransaction, String accountType) {
		super();
		this.tlfNo = tlfNo;
		this.coaId = coaId;
		this.narration = narration;
		this.salePointName = salePointName;
		this.salePointId = salePointId;
		this.paymentChannel = paymentChannel;
		this.createdDate = createdDate;
		this.cashDebit = cashDebit;
		this.cashCredit = cashCredit;
		this.transferDebit = transferDebit;
		this.transferCredit = transferCredit;
		this.policyNo = policyNo;
		this.agentTransaction = agentTransaction;
		this.accountType = accountType;
	}

	public String getTlfNo() {
		return tlfNo;
	}

	public void setTlfNo(String tlfNo) {
		this.tlfNo = tlfNo;
	}

	public String getCoaId() {
		return coaId;
	}

	public void setCoaId(String coaId) {
		this.coaId = coaId;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getSalePointName() {
		return salePointName;
	}

	public void setSalePointName(String salePointName) {
		this.salePointName = salePointName;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public double getCashDebit() {
		return cashDebit;
	}

	public void setCashDebit(double cashDebit) {
		this.cashDebit = cashDebit;
	}

	public double getCashCredit() {
		return cashCredit;
	}

	public void setCashCredit(double cashCredit) {
		this.cashCredit = cashCredit;
	}

	public double getTransferDebit() {
		return transferDebit;
	}

	public void setTransferDebit(double transferDebit) {
		this.transferDebit = transferDebit;
	}

	public double getTransferCredit() {
		return transferCredit;
	}

	public void setTransferCredit(double transferCredit) {
		this.transferCredit = transferCredit;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public boolean isAgentTransaction() {
		return agentTransaction;
	}

	public void setAgentTransaction(boolean agentTransaction) {
		this.agentTransaction = agentTransaction;
	}

	public String getSalePointId() {
		return salePointId;
	}

	public void setSalePointId(String salePointId) {
		this.salePointId = salePointId;
	}

}
