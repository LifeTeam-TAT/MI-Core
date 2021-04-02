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
@Table(name = TableName.ACCOUNTANDLIFEDEPTCANCELREPORT_VIEW)
@ReadOnly
public class AccountAndLifeDeptCancelReportView {

	@Id
	private String id;
	private String TLFNo;
	private String productName;
	private String salePointName;
	private String paymentChannel;
	private String productId;
	private String salePointId;
	private double amount;
	private double accountPremium;
	private boolean lifeDeptStatus;
	private boolean accountStatus;
	private boolean paymentComplete;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date confirmDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date accountReceiptDate;

	public AccountAndLifeDeptCancelReportView() {
	}

	public String getId() {
		return id;
	}

	public String getTLFNo() {
		return TLFNo;
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

	public String getProductId() {
		return productId;
	}

	public String getSalePointId() {
		return salePointId;
	}

	public double getAmount() {
		return amount;
	}

	public boolean isLifeDeptStatus() {
		return lifeDeptStatus;
	}

	public boolean isAccountStatus() {
		return accountStatus;
	}

	public boolean isPaymentComplete() {
		return paymentComplete;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public Date getAccountReceiptDate() {
		return accountReceiptDate;
	}

	public double getAccountPremium() {
		return accountPremium;
	}

}
