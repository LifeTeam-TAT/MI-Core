package org.ace.insurance.report.life.report;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.life.view.LifeMonthlyReportView;
import org.ace.insurance.report.life.view.LifeRenewalMonthlyReportView;
import org.joda.time.DateTime;
import org.joda.time.Period;

public class LifeMonthlyReport implements ISorter {
	private String policyNo;
	private String customerName;
	private String customerAddress;
	private String cashReceiptAndPaymentDate;
	private int age;
	private double sumInsured;
	private double premium;
	private int commission;
	private String periodOfMonth;
	private String paymentType;
	private String agentNameWithLiscenceNo;
	private int noOfInsu;

	public LifeMonthlyReport(LifeMonthlyReportView view) {
		this.policyNo = view.getPolicyNo();
		if (view.getCustomerName() == null) {
			this.customerName = view.getOrganizationName();
		} else {
			this.customerName = view.getCustomerName();
		}
		if (view.getCustomerAddress() == null) {
			this.customerAddress = view.getOrganizationAddress();
		} else {
			this.customerAddress = view.getCustomerAddress();
		}

		this.cashReceiptAndPaymentDate = view.getReceiptNo() + " \n (" + Utils.getDateFormatString(view.getPaymentDate()) + ")";
		this.age = view.getAge();
		this.sumInsured = view.getSumInsured();
		this.premium = view.getPremium();
		this.commission = view.getPercentage();
		this.periodOfMonth = view.getPeriodOfMonth();
		this.paymentType = view.getPaymentTypeName();
		this.agentNameWithLiscenceNo = view.getAgentNameAndCodeNo();
		this.noOfInsu = view.getNoOfInsu();
	}

	public LifeMonthlyReport(LifeRenewalMonthlyReportView view) {
		this.policyNo = view.getPolicyNo();
		if (view.getCustomerName() == null) {
			this.customerName = view.getOrganizationName();
		} else {
			this.customerName = view.getCustomerName();
		}
		if (view.getCustomerAddress() == null) {
			this.customerAddress = view.getOrganizationAddress();
		} else {
			this.customerAddress = view.getCustomerAddress();
		}

		this.cashReceiptAndPaymentDate = view.getReceiptNo() + " \n (" + Utils.getDateFormatString(view.getPaymentDate()) + ")";
		this.age = view.getAge();
		this.sumInsured = view.getSumInsured();
		this.premium = view.getPremium();
		this.commission = view.getPercentage();
		this.periodOfMonth = view.getPeriodOfMonth();
		this.paymentType = view.getPaymentTypeName();
		this.agentNameWithLiscenceNo = view.getAgentNameAndCodeNo();
		this.noOfInsu = view.getNoOfInsu();
	}

	public String getCurrentAge(Date dob) {
		DateTime startDate = new DateTime(dob);
		DateTime endDate = new DateTime(new Date());
		Period period = new Period(startDate, endDate);
		StringBuffer result = new StringBuffer();
		result.append(period.getYears() + 1 + "");
		return result.toString();
	}

	public int getNoOfInsu() {
		return noOfInsu;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public String getCashReceiptAndPaymentDate() {
		return cashReceiptAndPaymentDate;
	}

	public double getAge() {
		return age;
	}

	public double getSumInsured() {
		return sumInsured;
	}

	public double getPremium() {
		return premium;
	}

	public int getCommission() {
		return commission;
	}

	public String getPeriodOfMonth() {
		return periodOfMonth;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getAgentNameWithLiscenceNo() {
		return agentNameWithLiscenceNo;
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

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}

}
