package org.ace.insurance.proxy;

import java.util.Date;

import org.ace.insurance.common.ISorter;

public class MEDCLM001 implements ISorter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String refundNo;
	private String beneficiaryName;
	private Date pendingDate;
	private double claimAmount;
	private String unit;

	public MEDCLM001() {
	}

	public MEDCLM001(String id, String refundNo, String beneficiaryName, Date pendingDate, double claimAmount, String unit) {
		this.id = id;
		this.refundNo = refundNo;
		this.beneficiaryName = beneficiaryName;
		this.pendingDate = pendingDate;
		this.claimAmount = claimAmount;
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public String getRefundNo() {
		return refundNo;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public Date getPendingDate() {
		return pendingDate;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public String getUnit() {
		return unit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getRegistrationNo() {
		return refundNo;
	}

}
