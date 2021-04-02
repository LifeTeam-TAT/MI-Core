package org.ace.insurance.report.publicLife.view;

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
@Table(name = TableName.VWT_FNI_ENDOWNMENTLIFEMONTHLYREPORTVIEW)
public class PublicLifeMonthlyReportView {

	@Id
	private String id;
	private int age;
	private String receiptNo;
	private double amount;
	private double commission;
	private double sumInsured;
	private String policyNo;
	private String insuredPersonName;
	private String policyTerm;
	private String paymentType;
	private String agentName;
	private String liscenseNo;
	private String salesPointsId;
	private String branchId;
	private String residentAddress;
	private String districtName;
	private String provinceName;
	private String salePointsName;
	private Date activePolicyStartDate;
	private Date activePolicyEndDate;
	private String fromTermToTerm;

	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;

	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;
	
	public PublicLifeMonthlyReportView() {
	}

	public String getId() {
		return id;
	}

	public int getAge() {
		return age;
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

	public String getPolicyTerm() {
		return policyTerm;
	}

	public String getPaymentType() {
		return paymentType;
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

	public Date getPaymentDate() {
		return paymentDate;
	}

	
	public Date getActivePolicyStartDate() {
		return activePolicyStartDate;
	}

	public void setActivePolicyStartDate(Date activePolicyStartDate) {
		this.activePolicyStartDate = activePolicyStartDate;
	}

	public Date getActivePolicyEndDate() {
		return activePolicyEndDate;
	}

	public void setActivePolicyEndDate(Date activePolicyEndDate) {
		this.activePolicyEndDate = activePolicyEndDate;
	}

	

	public String getFromTermToTerm() {
		return fromTermToTerm;
	}

	public void setFromTermToTerm(String fromTermToTerm) {
		this.fromTermToTerm = fromTermToTerm;
	}

	public String getSalePointsName() {
		return salePointsName;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

}
