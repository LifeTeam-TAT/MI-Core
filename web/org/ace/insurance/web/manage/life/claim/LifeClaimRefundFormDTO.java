package org.ace.insurance.web.manage.life.claim;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author T&D Infomation System Ltd
 * @since 1.0.0
 * @date 2013/07/16
 */
public class LifeClaimRefundFormDTO {
	private String claimRequestId = "";
	private String refundNo = "";
	private String presentDate = "";
	private double claimAmount = 0;
	private double loanAmount = 0;
	private double loanInterest = 0;
	private double renewelAmount = 0;
	private double renewelInterest = 0;
	private double netClaimAmount = 0;
	private double serviceCharges = 0;
	private String customerName = "";
	private String agentName = "";
	private String address = "";

	public LifeClaimRefundFormDTO() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		this.presentDate = format.format(new Date());
	}

	public String getClaimRequestId() {
		return claimRequestId;
	}

	public void setClaimRequestId(String claimRequestId) {
		this.claimRequestId = claimRequestId;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getLoanInterest() {
		return loanInterest;
	}

	public void setLoanInterest(double loanInterest) {
		this.loanInterest = loanInterest;
	}

	public double getRenewelAmount() {
		return renewelAmount;
	}

	public void setRenewelAmount(double renewelAmount) {
		this.renewelAmount = renewelAmount;
	}

	public double getRenewelInterest() {
		return renewelInterest;
	}

	public void setRenewelInterest(double renewelInterest) {
		this.renewelInterest = renewelInterest;
	}

	public double getNetClaimAmount() {
		return netClaimAmount;
	}

	public void setNetClaimAmount(double netClaimAmount) {
		this.netClaimAmount = netClaimAmount;
	}

	public double getServiceCharges() {
		return serviceCharges;
	}

	public void setServiceCharges(double serviceCharges) {
		this.serviceCharges = serviceCharges;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPresentDate() {
		return presentDate;
	}
}
