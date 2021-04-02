package org.ace.insurance.proxy;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.interfaces.IDataModel;

public class LIF001 implements Serializable, ISorter, IDataModel {
	private static final long serialVersionUID = 1L;
	private String id;
	private String proposalNo;
	private String salePointName;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private double sumInsured;
	private int unit;

	public LIF001() {
	}

	public LIF001(String id, String proposalNo, String salePointName, String intialId, Name cusName, String orgName, Date submittedDate, Date pendingSince, double sumInsured,
			int unit) {
		this.id = id;
		this.proposalNo = proposalNo;
		this.salePointName = salePointName;
		this.customerName = cusName != null ? intialId + " " + cusName.getFullName() : orgName;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.sumInsured = sumInsured;
		this.unit = unit;

	}

	public String getId() {
		return id;
	}

	public String getProposalNo() {
		return proposalNo;
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

	public double getSumInsured() {
		return sumInsured;
	}

	public int getUnit() {
		return unit;
	}

	public String getSalePointName() {
		return salePointName;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}
}
