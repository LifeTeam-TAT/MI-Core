package org.ace.insurance.renewal;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class RenewalNotification implements ISorter {
	private static final long serialVersionUID = 1L;
	private String policyId;
	private String policyNo;
	private String customerName;
	private Date endDate;
	private int days;

	public RenewalNotification(String policyId, String policyNo, String customerName, Date endDate, int days) {
		this.policyId = policyId;
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.endDate = endDate;
		this.days = days;
	}

	public String getPolicyId() {
		return policyId;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getDays() {
		return days;
	}

	@Override
	public String getRegistrationNo() {
		return policyNo;
	}
}
