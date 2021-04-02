package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.Utils;

public class TRA001 implements ISorter {
	private static final long serialVersionUID = 1L;

	private String id;
	private String proposalNo;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private double totalUnit;
	private double totalPremium;

	public TRA001() {
	}

	public TRA001(String id, String proposalNo, String initialId, Name cutomerName, String organizationName, Date submittedDate, double totalUnit, double totalPremium,
			Date pendingSince) {
		super();
		this.id = id;
		this.proposalNo = proposalNo;
		this.customerName = initialId != null && !initialId.isEmpty() && cutomerName != null ? Utils.getCompleteName(initialId, cutomerName) : organizationName;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.totalUnit = totalUnit;
		this.totalPremium = totalPremium;
	}

	public TRA001(String id, String proposalNo, String customerName, Date submittedDate, Date pendingSince, int totalUnit, double totalPremium) {
		super();
		this.id = id;
		this.proposalNo = proposalNo;
		this.customerName = customerName;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.totalUnit = totalUnit;
		this.totalPremium = totalPremium;
	}

	public TRA001(String id, String proposalNo, Date submittedDate, Date pendingSince, double totalUnit) {
		this.id = id;
		this.proposalNo = proposalNo;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.totalUnit = totalUnit;
	}

	public TRA001(String id, String proposalNo, Date submittedDate, Date pendingSince) {
		this.id = id;
		this.proposalNo = proposalNo;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
	}

	public String getId() {
		return id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public Date getPendingSince() {
		return pendingSince;
	}

	/**
	 * @return the totalUnit
	 */
	public double getTotalUnit() {
		return totalUnit;
	}

	public double getTotalPremium() {
		return totalPremium;
	}

	public void setTotalPremium(double totalPremium) {
		this.totalPremium = totalPremium;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}
}
