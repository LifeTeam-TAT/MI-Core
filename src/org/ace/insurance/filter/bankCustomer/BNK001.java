package org.ace.insurance.filter.bankCustomer;

import java.io.Serializable;

import org.ace.insurance.system.common.bank.Bank;

public class BNK001 implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String acode;
	private String description;

	public BNK001(String id, String name, String description, String code) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.acode = code;
	}

	public BNK001(Bank bank) {
		this.id = bank.getId();
		this.acode = bank.getAcode();
		this.description = bank.getDescription();
		this.name = bank.getName();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAcode() {
		return acode;
	}

	public String getDescription() {
		return description;
	}

}
