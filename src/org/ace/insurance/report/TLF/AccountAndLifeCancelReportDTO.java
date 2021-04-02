package org.ace.insurance.report.TLF;

import java.util.Date;

import org.ace.insurance.report.TLF.view.AccountAndLifeDeptCancelReportView;

public class AccountAndLifeCancelReportDTO {

	private String tlfNo;
	private String productName;
	private String salePointName;
	private String paymentChannel;
	private double accountPremium;
	private double homeAmount;
	private boolean lifeDeptStatus;
	private boolean accountStatus;
	private boolean paymentComplete;
	private Date paymentDate;
	private Date accountReceiptDate;
	private Date lifeDeptPaymentDate;
	private boolean paymentCompleteProcess;

	public AccountAndLifeCancelReportDTO() {
	}

	public AccountAndLifeCancelReportDTO(AccountAndLifeDeptCancelReportView view) {
		this.tlfNo = view.getTLFNo();
		this.productName = view.getProductName();
		this.salePointName = view.getSalePointName();
		this.paymentChannel = view.getPaymentChannel();
		this.accountReceiptDate = view.getAccountReceiptDate();
		this.accountStatus = view.isAccountStatus();
		this.homeAmount = view.getAmount();
		this.lifeDeptPaymentDate = view.getPaymentDate();
		this.lifeDeptStatus = view.isLifeDeptStatus();
		this.accountPremium = view.getAccountPremium();

	}

	public String getTlfNo() {
		return tlfNo;
	}

	public String getProductName() {
		return productName;
	}

	public String getSalePointName() {
		return salePointName;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public boolean isAccountStatus() {
		return accountStatus;
	}

	public double getAccountPremium() {
		return accountPremium;
	}

	public Date getAccountReceiptDate() {
		return accountReceiptDate;
	}

	public Date getLifeDeptPaymentDate() {
		return lifeDeptPaymentDate;
	}

	public boolean isLifeDeptStatus() {
		return lifeDeptStatus;
	}

	public double getHomeAmount() {
		return homeAmount;
	}

	public boolean isPaymentComplete() {
		return paymentComplete;
	}

	public boolean isPaymentCompleteProcess() {
		this.paymentCompleteProcess = (paymentComplete && !lifeDeptStatus);
		return paymentCompleteProcess;
	}

}
