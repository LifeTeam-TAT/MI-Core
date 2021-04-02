package org.ace.insurance.product;

import java.io.Serializable;

import org.ace.insurance.common.ProductGroupType;

public class PROGRP003 implements Serializable {
	private static final long serialVersionUID = 403570860946827514L;
	private String id;
	private String name;
	private ProductGroupType groupType;
	private String policyNoPrefix;
	private String proposalNoPrefix;
	private double fleetDiscount;
	private double underSession13;
	private double underSession25;
	private double maxSumInsured;

	public PROGRP003(String id, String name, ProductGroupType groupType, String policyNoPrefix, String proposalNoPrefix, double fleetDiscount, double underSession13,
			double underSession25, double maxSumInsured) {
		this.id = id;
		this.name = name;
		this.groupType = groupType;
		this.policyNoPrefix = policyNoPrefix;
		this.proposalNoPrefix = proposalNoPrefix;
		this.fleetDiscount = fleetDiscount;
		this.underSession13 = underSession13;
		this.underSession25 = underSession25;
		this.maxSumInsured = maxSumInsured;
	}

	public PROGRP003(ProductGroup productGroup) {
		this.id = productGroup.getId();
		this.name = productGroup.getName();
		this.groupType = productGroup.getGroupType();
		this.policyNoPrefix = productGroup.getPolicyNoPrefix();
		this.proposalNoPrefix = productGroup.getProposalNoPrefix();
		this.fleetDiscount = productGroup.getFleetDiscount();
		this.underSession13 = productGroup.getUnderSession13();
		this.underSession25 = productGroup.getUnderSession25();
		this.maxSumInsured = productGroup.getMaxSumInsured();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ProductGroupType getGroupType() {
		return groupType;
	}

	public String getPolicyNoPrefix() {
		return policyNoPrefix;
	}

	public String getProposalNoPrefix() {
		return proposalNoPrefix;
	}

	public double getFleetDiscount() {
		return fleetDiscount;
	}

	public double getUnderSession13() {
		return underSession13;
	}

	public double getUnderSession25() {
		return underSession25;
	}

	public double getMaxSumInsured() {
		return maxSumInsured;
	}

}
