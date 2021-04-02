package org.ace.insurance.report.TLF.view;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_FNI_SALEPOINTINCOME)
@ReadOnly
public class SalePointIncomeReportView {
	@Id
	private String id;
	private String coaId;
	private String tlfNo;
	private String narration;
	private String salePointName;
	private String salePointId;
	private String paymentChannel;
	private String policyNo;
	private String accountType;
	private double cashDebit;
	private double cashCredit;
	private double transferDebit;
	private double transferCredit;
	private boolean agentTransaction;
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public SalePointIncomeReportView() {
	}

	public String getTlfNo() {
		return tlfNo;
	}

	public String getId() {
		return id;
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

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}

	public String getSalePointId() {
		return salePointId;
	}

	public void setSalePointId(String salePointId) {
		this.salePointId = salePointId;
	}

	public String getAccountType() {
		return accountType;
	}

}
