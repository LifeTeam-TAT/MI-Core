package org.ace.insurance.proxy;

import java.io.Serializable;
import java.util.Date;

import org.ace.insurance.common.ISorter;
import org.ace.insurance.common.interfaces.IDataModel;

public class LSP001 implements Serializable, ISorter, IDataModel {

	private static final long serialVersionUID = 1L;
	private String id;
	private String proposalNo;
	private String policyNo;
	private String customerName;
	private Date submittedDate;
	private Date pendingSince;
	private double surrenderAmount;

	public LSP001() {

	}

	public LSP001(String id, String proposalNo, String policyNo, String customerName, Date submittedDate, Date pendingSince, double surrenderAmount) {
		super();
		this.id = id;
		this.proposalNo = proposalNo;
		this.policyNo = policyNo;
		this.customerName = customerName;
		this.submittedDate = submittedDate;
		this.pendingSince = pendingSince;
		this.surrenderAmount = surrenderAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProposalNo() {
		return proposalNo;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getPendingSince() {
		return pendingSince;
	}

	public void setPendingSince(Date pendingSince) {
		this.pendingSince = pendingSince;
	}

	public double getSurrenderAmount() {
		return surrenderAmount;
	}

	public void setSurrenderAmount(double surrenderAmount) {
		this.surrenderAmount = surrenderAmount;
	}

	@Override
	public String getRegistrationNo() {
		return proposalNo;
	}

}
