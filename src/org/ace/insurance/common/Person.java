package org.ace.insurance.common;

public class Person implements IReceiptNoSorter {
	private String name;
	private String recNo;

	public Person(String name, String recNo) {
		this.name = name;
		this.recNo = recNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProposalNo() {
		return recNo;
	}

	public void setRecNo(String recNo) {
		this.recNo = recNo;
	}

	// @Override
	// public String getRegistrationNo() {
	// return proposalNo;
	// }

	@Override
	public String getReceiptNo() {
		return recNo;
	}
}
