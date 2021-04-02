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
@Table(name = TableName.DAILYINCOMEREPORT_VIEW)
@ReadOnly
public class DailyIncomeReportView {

	@Id
	private String id;
	private String policyNo;
	private String productName;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String paymentChannel;
	private String receiptNo;
	private double homeAmount;
	private String salePointName;
	private String salePointId;
	private String productId;

	public DailyIncomeReportView() {

	}

	public String getId() {
		return id;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getProductName() {
		return productName;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public double getHomeAmount() {
		return homeAmount;
	}

	public String getSalePointName() {
		return salePointName;
	}

	public String getSalePointId() {
		return salePointId;
	}

	public String getProductId() {
		return productId;
	}

}
