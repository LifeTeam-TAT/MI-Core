package org.ace.insurance.system.common.reinsuranceRatio;

import org.ace.insurance.system.common.coinsurancecompany.CoinsuranceCompany;

public class ReinsuranceRationDTO {
	private CoinsuranceCompany coinsuranceCompany;
	private double commission;
	private double minSumInsured;
	private double maxSumInsured;

	public ReinsuranceRationDTO() {

	}

	public ReinsuranceRationDTO(CoinsuranceCompany coinsuranceCompany, double commission, double minSumInsured, double maxSumInsured) {
		this.coinsuranceCompany = coinsuranceCompany;
		this.commission = commission;
		this.minSumInsured = minSumInsured;
		this.maxSumInsured = maxSumInsured;
	}

	public CoinsuranceCompany getCoinsuranceCompany() {
		return coinsuranceCompany;
	}

	public void setCoinsuranceCompany(CoinsuranceCompany coinsuranceCompany) {
		this.coinsuranceCompany = coinsuranceCompany;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}

	public double getMinSumInsured() {
		return minSumInsured;
	}

	public void setMinSumInsured(double minSumInsured) {
		this.minSumInsured = minSumInsured;
	}

	public double getMaxSumInsured() {
		return maxSumInsured;
	}

	public void setMaxSumInsured(double maxSumInsured) {
		this.maxSumInsured = maxSumInsured;
	}

}
