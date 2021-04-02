package org.ace.insurance.report.life;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.life.policy.LifePolicy;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class LifePolicyMonthlyReport implements ISorter {
	private String policyId;
	private String customerName;
	private String age;
	private String policyNo;
	private String address;
	private double sumInsure;
	private String period;
	private double premium;
	private String paymentType;
	private double agentCommission;
	private String receiptNo;
	private String agentName;
	private int numberOfInsuredPerson;
	private String product;
	private String codeNo;
	private Date paymentDate;
	private Date dob;

	public LifePolicyMonthlyReport() {
		super();
	}

	public LifePolicyMonthlyReport(LifePolicy lifePolicy, LifeProductType product, String receiptNo, Date dob, Date paymentDate) {
		this.policyId = lifePolicy.getId();
		this.policyNo = lifePolicy.getPolicyNo();
		this.address = lifePolicy.getCustomerAddress();
		this.sumInsure = lifePolicy.getTotalSumInsured();
		this.premium = lifePolicy.getTotalBasicTermPremium();
		this.paymentDate = paymentDate;
		this.receiptNo = receiptNo;
		this.dob = dob;

		if (lifePolicy.getCustomer() != null) {
			this.customerName = lifePolicy.getCustomer().getFullName();
		} else {
			this.customerName = lifePolicy.getOrganization().getName();
		}

		if (lifePolicy.getAgent() != null) {
			this.agentName = lifePolicy.getAgent() == null ? "-" : lifePolicy.getAgent().getFullName();
			this.codeNo = lifePolicy.getAgent() == null ? "-" : lifePolicy.getAgent().getLiscenseNo();
			this.agentCommission = calAgentCommission(lifePolicy.getCommenmanceDate());
		}

		if (product == LifeProductType.PUBLIC_LIFE) {
			// this is old source, age is calculated. wrong concept
			// getAge(lifePolicy.getPolicyInsuredPersonList().get(0).getDateOfBirth());
			// this is new source, age is retrieve from DB
			// lifePolicy.getPolicyInsuredPersonList().get(0).getAge();
			this.age = String.valueOf(lifePolicy.getPolicyInsuredPersonList().get(0).getAge());
			this.period = getPeriod(lifePolicy.getPeriodMonth());
			this.paymentType = changeMonthType(lifePolicy.getPaymentType().getMonth());
		}

		if (product == LifeProductType.GROUP_LIFE) {
			this.numberOfInsuredPerson = lifePolicy.getPolicyInsuredPersonList().size();
		}

	}

	public double calAgentCommission(Date policyStartDate) {
		double result = 0.0;
		DateTime startDate = new DateTime(policyStartDate);
		DateTime endDate = new DateTime(new Date());
		Period period = new Period(startDate, endDate);
		if (period.getYears() > 0) {
			result = 5;
		} else {
			result = 20;
		}
		return result;
	}

	public String changeMonthType(int months) {
		StringBuffer result = new StringBuffer();
		int year = months / 12;
		if (year > 0) {
			result.append(year + " Year ");
		}
		int month = months % 12;
		if (month > 0) {
			result.append(month + " Months");
		}
		return result.toString();
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getCodeNo() {
		if (codeNo == null) {
			codeNo = "-";
		}
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getPeriod(int period) {
		StringBuffer result = new StringBuffer();
		int year = period / 12;
		result.append(year + " Y ");
		int month = period % 12;
		if (month > 0) {
			result.append(month + " M ");
		}
		return result.toString();
	}

	public String getCurrentAge(Date dob) {
		DateTime startDate = new DateTime(dob);
		DateTime endDate = new DateTime(new Date());
		Period period = new Period(startDate, endDate);
		StringBuffer result = new StringBuffer();
		result.append(period.getYears() + 1 + "");
		return result.toString();
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getSumInsure() {
		return sumInsure;
	}

	public void setSumInsure(double sumInsure) {
		this.sumInsure = sumInsure;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public double getPremium() {
		return premium;
	}

	public void setPremium(double premium) {
		this.premium = premium;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public double getAgentCommission() {
		return agentCommission;
	}

	public void setAgentCommission(double agentCommission) {
		this.agentCommission = agentCommission;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getAgentName() {
		if (agentName == null) {
			agentName = "-";
		}
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public int getNumberOfInsuredPerson() {
		return numberOfInsuredPerson;
	}

	public void setNumberOfInsuredPerson(int numberOfInsuredPerson) {
		this.numberOfInsuredPerson = numberOfInsuredPerson;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
}
