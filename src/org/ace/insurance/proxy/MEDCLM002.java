package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class MEDCLM002 implements ISorter {
	private String id;
	private String claimRequestId;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private int unit;

	public MEDCLM002() {
		super();
	}

	public MEDCLM002(String id, String claimRequestId, String customerName, Date submittedDate, Date pendingSince, int unit) {
		super();
		this.id = id;
		this.claimRequestId = claimRequestId;
		this.customerName = customerName;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public Date getPendingSince() {
		return pendingSince;
	}

	// public String getUnit() {
	// return new DefaultProcessor().getName(unit).toUpperCase();
	// }
	public int getUnit() {
		return unit;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setClaimRequestId(String claimRequestId) {
		this.claimRequestId = claimRequestId;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public void setPendingSince(Date pendingSince) {
		this.pendingSince = pendingSince;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	@Override
	public String getRegistrationNo() {
		return claimRequestId;
	}

}
