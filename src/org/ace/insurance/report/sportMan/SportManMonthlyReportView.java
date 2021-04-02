package org.ace.insurance.report.sportMan;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.ace.insurance.common.TableName;
import org.ace.insurance.web.common.SaleChannelType;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@ReadOnly
@Table(name = TableName.VWT_FNI_SPORTMANMONTHLYREPORTVIEW)
public class SportManMonthlyReportView {

	@Id
	private String id;
	private double amount;
	private double commission;
	private double sumInsured;
	private String receiptNo;
	private String policyNo;
	private String insuredPersonName;
	private String agentName;
	private String liscenseNo;
	private String salesPointsId;
	private String branchId;
	private String residentAddress;
	private String districtName;
	private String provinceName;
	private String typeOfSport;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	public SportManMonthlyReportView() {
	}

	public String getId() {
		return id;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public double getAmount() {
		return amount;
	}

	public double getCommission() {
		return commission;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public String getAgentName() {
		return agentName;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public String getSalesPointsId() {
		return salesPointsId;
	}

	public String getBranchId() {
		return branchId;
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public String getDistrictName() {
		return districtName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public String getTypeOfSport() {
		return typeOfSport;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getSalePointsName() {
		return salePointsName;
	}

	public String getFromTermToTerm() {
		return fromTermToTerm;
	}

	public String getFromDateToDate() {
		return fromDateToDate;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

}
