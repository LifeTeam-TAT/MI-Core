package org.ace.insurance.web.manage.claimProduct;

import java.util.List;

import org.ace.insurance.claimproduct.ClaimProductRateKeyFactor;

public class ClaimProductRateDto implements Comparable<ClaimProductRateDto> {
	private String tempId;
	private String claimProductRateId;
	private double rate;
	private List<ClaimProductRateKeyFactor> claimProductRateKeyFactors;

	public ClaimProductRateDto() {
		tempId = System.currentTimeMillis() + "";
	}

	public ClaimProductRateDto(String claimProductRateId, double rate, List<ClaimProductRateKeyFactor> claimProductRateKeyFactors) {
		this.claimProductRateId = claimProductRateId;
		this.rate = rate;
		this.claimProductRateKeyFactors = claimProductRateKeyFactors;
	}

	public String getTempId() {
		return tempId;
	}

	public void setTempId(String tempId) {
		this.tempId = tempId;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getClaimProductRateId() {
		return claimProductRateId;
	}

	public void setClaimProductRateId(String claimProductRateId) {
		this.claimProductRateId = claimProductRateId;
	}

	public List<ClaimProductRateKeyFactor> getClaimProductRateKeyFactors() {
		return claimProductRateKeyFactors;
	}

	public void setClaimProductRateKeyFactors(List<ClaimProductRateKeyFactor> claimProductRateKeyFactors) {
		this.claimProductRateKeyFactors = claimProductRateKeyFactors;
	}

	@Override
	public int compareTo(ClaimProductRateDto o) {
		if (this.getRate() > o.getRate()) {
			return 1;
		} else {
			return -1;
		}
	}
}
