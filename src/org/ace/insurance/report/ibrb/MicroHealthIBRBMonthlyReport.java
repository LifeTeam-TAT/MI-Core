package org.ace.insurance.report.ibrb;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.ace.insurance.common.CustomerType;
import org.ace.insurance.common.TableName;
import org.ace.insurance.web.common.SaleChannelType;
import org.eclipse.persistence.annotations.ReadOnly;

@Entity
@Table(name = TableName.VWT_FNI_MICRO_HEALTH_IBRB_MONTHLY_REPORT)
@ReadOnly
public class MicroHealthIBRBMonthlyReport {
	@Id
	private String id;
	private String insuredPersonName;
	private String branchId;
	private String salePointId;
	private Date paymentDate;
	private String receiptNo;
	private Date activedPolicyStartDate;
	private String policyNo;
	private String gender;
	private int age;
	private String occupation;
	private String residentAddress;
	private String province;
	private String township;
	private String paymentType;
	@Enumerated(EnumType.STRING)
	private CustomerType customerType;
	private double totalPremium;
	private String benefInfo;
	private int basicUnit;
	private String agentName;
	private double agentcommission;
	private String liscenseno;
	private String fromTermtoTerm;
	private String fromDatetoDate;
	private String salesPointName;
	
	@Enumerated(EnumType.STRING)
	private SaleChannelType saleChannelType;

	public MicroHealthIBRBMonthlyReport() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInsuredPersonName() {
		return insuredPersonName;
	}

	public void setInsuredPersonName(String insuredPersonName) {
		this.insuredPersonName = insuredPersonName;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getSalePointId() {
		return salePointId;
	}

	public void setSalePointId(String salePointId) {
		this.salePointId = salePointId;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getActivedPolicyStartDate() {
		return activedPolicyStartDate;
	}

	public void setActivedPolicyStartDate(Date activedPolicyStartDate) {
		this.activedPolicyStartDate = activedPolicyStartDate;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getResidentAddress() {
		return residentAddress;
	}

	public void setResidentAddress(String residentAddress) {
		this.residentAddress = residentAddress;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTownship() {
		return township;
	}

	public void setTownship(String township) {
		this.township = township;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	public String getBenefInfo() {
		return benefInfo;
	}

	public void setBenefInfo(String benefInfo) {
		this.benefInfo = benefInfo;
	}

	public int getBasicUnit() {
		return basicUnit;
	}

	public void setBasicUnit(int basicUnit) {
		this.basicUnit = basicUnit;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public double getAgentcommission() {
		return agentcommission;
	}

	public void setAgentcommission(double agentcommission) {
		this.agentcommission = agentcommission;
	}

	public String getLiscenseno() {
		return liscenseno;
	}

	public void setLiscenseno(String liscenseno) {
		this.liscenseno = liscenseno;
	}

	public String getFromTermtoTerm() {
		return fromTermtoTerm;
	}

	public void setFromTermtoTerm(String fromTermtoTerm) {
		this.fromTermtoTerm = fromTermtoTerm;
	}

	public String getFromDatetoDate() {
		return fromDatetoDate;
	}

	public void setFromDatetoDate(String fromDatetoDate) {
		this.fromDatetoDate = fromDatetoDate;
	}

	public String getSalesPointName() {
		return salesPointName;
	}

	public void setSalesPointName(String salesPointName) {
		this.salesPointName = salesPointName;
	}

	public SaleChannelType getSaleChannelType() {
		return saleChannelType;
	}

	public void setSaleChannelType(SaleChannelType saleChannelType) {
		this.saleChannelType = saleChannelType;
	}
	

}
