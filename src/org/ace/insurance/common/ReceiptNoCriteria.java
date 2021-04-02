package org.ace.insurance.common;


public class ReceiptNoCriteria {
	public String criteriaValue;
	public ReceiptNoCriteriaItems receiptNoCriteria;
	public String product;
	
	public ReceiptNoCriteria() {
		
	}

	public ReceiptNoCriteria(String criteriaValue, ReceiptNoCriteriaItems receiptNoCriteria) {
		this.criteriaValue = criteriaValue;
		this.receiptNoCriteria = receiptNoCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}
	
	public ReceiptNoCriteriaItems getReceiptNoCriteria() {
		return receiptNoCriteria;
	}

	public void setReceiptNoCriteria(ReceiptNoCriteriaItems receiptNoCriteria) {
		this.receiptNoCriteria = receiptNoCriteria;
	}

	public ReceiptNoCriteriaItems[] getReceiptNoCriteriaItemList() {
		return ReceiptNoCriteriaItems.values();
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

}
