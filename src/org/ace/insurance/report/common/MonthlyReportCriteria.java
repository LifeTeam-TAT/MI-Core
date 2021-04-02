package org.ace.insurance.report.common;

import org.ace.insurance.common.ProposalType;
import org.ace.insurance.product.Product;
import org.ace.insurance.system.common.branch.Branch;

public class MonthlyReportCriteria {
	private int year;
	private int month;
	private Branch branch;
	private ProposalType proposalType;
	private Product product;

	public MonthlyReportCriteria() {

	}

	public MonthlyReportCriteria(int year, int month, Branch branch, ProposalType proposalType) {
		this.year = year;
		this.month = month;
		this.branch = branch;
		this.proposalType = proposalType;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public ProposalType getProposalType() {
		return proposalType;
	}

	public void setProposalType(ProposalType proposalType) {
		this.proposalType = proposalType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
