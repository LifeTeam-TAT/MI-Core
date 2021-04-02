package org.ace.insurance.proxy;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.life.policy.PolicyInsuredPerson;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.PaymentChannel;

public class MEDFEES001 implements ISorter, Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String invoiceNo;
	private PaymentChannel paymentChannel;
	private String bank;
	private String bankAccountNo;
	private double medicalFees;
	private String claimNo;
	private String policyNo;
	private String insuredPersonName;
	private Date invoiceDate;
	private String hospitalName;
	private Product product;
	private String sanctionNo;

	public MEDFEES001() {

	}

	public MEDFEES001(String id, String invoiceNo, String bankAccountNo, PaymentChannel paymentChannel, String bank, double medicalFees, String claimNo, String policyNo,
			PolicyInsuredPerson person) {
		this.id = id;
		this.invoiceNo = invoiceNo;
		this.bankAccountNo = bankAccountNo;
		this.paymentChannel = paymentChannel;
		this.bank = bank;
		this.medicalFees = medicalFees;
		this.claimNo = claimNo;
		this.policyNo = policyNo;
		this.insuredPersonName = person.getFullName();
	}

	public MEDFEES001(String id, String hospitalName, String invoiceNo, Date invoiceDate, String bankAccountNo, PaymentChannel paymentChannel, String bank, double medicalFees,
			Product product) {
		this.id = id;
		this.hospitalName = hospitalName;
		this.invoiceDate = invoiceDate;
		this.invoiceNo = invoiceNo;
		this.bankAccountNo = bankAccountNo;
		this.paymentChannel = paymentChannel;
		this.bank = bank;
		this.medicalFees = medicalFees;
		this.product = product;
	}

	public MEDFEES001(String id, String hospitalName, String invoiceNo, Date invoiceDate, String bankAccountNo, PaymentChannel paymentChannel, String bank, double medicalFees,
			String sanctionNo, String claimNo) {
		this.id = id;
		this.hospitalName = hospitalName;
		this.invoiceDate = invoiceDate;
		this.invoiceNo = invoiceNo;
		this.bankAccountNo = bankAccountNo;
		this.paymentChannel = paymentChannel;
		this.bank = bank;
		this.medicalFees = medicalFees;
		this.sanctionNo = sanctionNo;
		this.claimNo = claimNo;
	}

	public String getId() {
		return id;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public PaymentChannel getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(PaymentChannel paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}

	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public double getMedicalFees() {
		return medicalFees;
	}

	public void setMedicalFees(double medicalFees) {
		this.medicalFees = medicalFees;
	}

	public String getClaimNo() {
		return claimNo;
	}

	public void setClaimNo(String claimNo) {
		this.claimNo = claimNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getRegistrationNo() {
		return invoiceNo;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getSanctionNo() {
		return sanctionNo;
	}

	public void setSanctionNo(String sanctionNo) {
		this.sanctionNo = sanctionNo;
	}

}
