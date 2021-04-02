package org.ace.insurance.payment;

import java.util.Date;

public class AgentPaymentCashReceiptDTO {
	private String cashReceiptNo;
	
	private String agentExpenseAcountCode;
	private String agentExpenseAccountName;
	private double agentExpenseDbAmount;
	
	private String agentPayableAccountCode;
	private String agentPayableAccountName;
	private double agentPayableCrAmount;
	
	private Date confirmDate;
	
	public AgentPaymentCashReceiptDTO() {
		
	}
	
	public AgentPaymentCashReceiptDTO(String cashReceiptNo, String agentExpenseAcountCode,
			String agentExpenseAccountName, double agentExpenseDbAmount, String agentPayableAccountCode,
			String agentPayableAccountName, double agentPayableCrAmount, Date confirmDate) {
		super();
		this.cashReceiptNo = cashReceiptNo;
		this.agentExpenseAcountCode = agentExpenseAcountCode;
		this.agentExpenseAccountName = agentExpenseAccountName;
		this.agentExpenseDbAmount = agentExpenseDbAmount;
		this.agentPayableAccountCode = agentPayableAccountCode;
		this.agentPayableAccountName = agentPayableAccountName;
		this.agentPayableCrAmount = agentPayableCrAmount;
		this.confirmDate = confirmDate;
	}

	public String getCashReceiptNo() {
		return cashReceiptNo;
	}

	public void setCashReceiptNo(String cashReceiptNo) {
		this.cashReceiptNo = cashReceiptNo;
	}

	public String getAgentExpenseAcountCode() {
		return agentExpenseAcountCode;
	}

	public void setAgentExpenseAcountCode(String agentExpenseAcountCode) {
		this.agentExpenseAcountCode = agentExpenseAcountCode;
	}

	public String getAgentExpenseAccountName() {
		return agentExpenseAccountName;
	}

	public void setAgentExpenseAccountName(String agentExpenseAccountName) {
		this.agentExpenseAccountName = agentExpenseAccountName;
	}

	public double getAgentExpenseDbAmount() {
		return agentExpenseDbAmount;
	}

	public void setAgentExpenseDbAmount(double agentExpenseDbAmount) {
		this.agentExpenseDbAmount = agentExpenseDbAmount;
	}

	public String getAgentPayableAccountCode() {
		return agentPayableAccountCode;
	}

	public void setAgentPayableAccountCode(String agentPayableAccountCode) {
		this.agentPayableAccountCode = agentPayableAccountCode;
	}

	public String getAgentPayableAccountName() {
		return agentPayableAccountName;
	}

	public void setAgentPayableAccountName(String agentPayableAccountName) {
		this.agentPayableAccountName = agentPayableAccountName;
	}

	public double getAgentPayableCrAmount() {
		return agentPayableCrAmount;
	}

	public void setAgentPayableCrAmount(double agentPayableCrAmount) {
		this.agentPayableCrAmount = agentPayableCrAmount;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}
	
}
