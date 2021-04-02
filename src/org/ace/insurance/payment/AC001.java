package org.ace.insurance.payment;

public class AC001 {
	private String customerName;
	private String policyNo;
	private String challanNo;
	private double commission;
	
	public AC001(){
		
	}
	
	public AC001(String customerName,String policyNo,String challanNo,double commission){
		this.customerName = customerName;
		this.policyNo = policyNo;
		this.challanNo = challanNo;
		this.commission = commission;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public double getCommission() {
		return commission;
	}

	public void setCommission(double commission) {
		this.commission = commission;
	}
}
