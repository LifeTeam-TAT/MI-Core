package org.ace.insurance.payment;

public class AccountPayment {
	private String acccountName;
	private Payment payment;
	
	public AccountPayment(String acccountName, Payment payment) {
		this.acccountName = acccountName;
		this.payment = payment;
	}

	public String getAcccountName() {
		return acccountName;
	}
	
	public void setAcccountName(String acccountName) {
		this.acccountName = acccountName;
	}
	
	public Payment getPayment() {
		return payment;
	}
	
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
