package org.ace.insurance.report.TLF.view;

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
@Table(name = TableName.FARMERMONTHLYINCOMEREPORT_VIEW)
@ReadOnly
public class FarmerMonthlyIncomeReportView {

	@Id
	private String id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	private String customerName;
	private String address;
	private String nrcNo;
	private double sumInsured;
	private double premium;
	private String agentName;
	private String liscenseNo;
	private double commission;
	private String branchName;
	private String branchId;
	private String salepointsId;
	private String salePointsName;
	private String fromTermToTerm;
	private String fromDateToDate;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;


	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}

	public String getId() {
		return id;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getAddress() {
		return address;
	}

	public String getNrcNo() {
		return nrcNo;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public String getAgentName() {
		return agentName;
	}

	public double getCommission() {
		return commission;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setNrcNoNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}

	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getLiscenseNo() {
		return liscenseNo;
	}

	public void setLiscenseNo(String liscenseNo) {
		this.liscenseNo = liscenseNo;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setNrcNo(String nrcNo) {
		this.nrcNo = nrcNo;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getSalepointsId() {
		return salepointsId;
	}

	public void setSalepointsId(String salepointsId) {
		this.salepointsId = salepointsId;
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

}
