package org.ace.insurance.proxy;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.Name;
import org.ace.insurance.system.common.PaymentChannel;

public class AGT001 implements ISorter, Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String agent;
	private String licenseNo;
	private String invoiceNo;
	private double premium;
	private double commission;
	private Date invoiceDate;
	private PaymentChannel paymentChannel;
	private String bank;
	private String bankAccountNo;

	public AGT001() {

	}

	public AGT001(String id, String salutation, Name agentName, String licenseNo, String invoiceNo, double premium, double commission, Date invoiceDate, String bankAccountNo,
			PaymentChannel paymentChannel, String bank) {
		this.id = id;
		this.agent = salutation + " " + agentName.getFullName();
		this.licenseNo = licenseNo;
		this.invoiceNo = invoiceNo;
		this.premium = premium;
		this.commission = commission;
		this.invoiceDate = invoiceDate;
		this.bankAccountNo = bankAccountNo;
		this.paymentChannel = paymentChannel;
		this.bank = bank;
	}

	public String getId() {
		return id;
	}

	public String getAgent() {
		return agent;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public double getPremium() {
		return premium;
	}

	public double getCommission() {
		return commission;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public String getBank() {
		return bank;
	}

	public String getLicenseNo() {
		return licenseNo;
	}

	@Override
	public String getRegistrationNo() {
		return invoiceNo;
	}

}
