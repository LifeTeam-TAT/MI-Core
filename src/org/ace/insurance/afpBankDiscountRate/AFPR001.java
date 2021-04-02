package org.ace.insurance.afpBankDiscountRate;

import java.io.Serializable;

public class AFPR001 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String productGroup;
	private String bank;
	private double discountRate;

	public AFPR001(String id, String productGroup, String bank, double discountRate) {
		this.id = id;
		this.productGroup = productGroup;
		this.bank = bank;
		this.discountRate = discountRate;
	}

	public AFPR001(AFPBankDiscountRate rate) {
		this.id = rate.getId();
		this.productGroup = rate.getProductGroup().getName();
		this.bank = rate.getBank().getName();
		this.discountRate = rate.getDiscountRate();
	}

	public String getId() {
		return id;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public String getBank() {
		return bank;
	}

	public double getDiscountRate() {
		return discountRate;
	}

}
