package org.ace.insurance.system.common.bankBranch;

import java.io.Serializable;

public class BKB001 implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String bank;
	private String branchCode;
	private double discountRate;

	public BKB001() {
	}

	public BKB001(String id, String name, String bank, String branchCode, double discountRate) {
		this.id = id;
		this.name = name;
		this.bank = bank;
		this.branchCode = branchCode;
		this.discountRate = discountRate;
	}

	public BKB001(String id, String name, String bank, String branchCode) {
		this.id = id;
		this.name = name;
		this.bank = bank;
		this.branchCode = branchCode;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBank() {
		return bank;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public double getDiscountRate() {
		return discountRate;
	}
}
