package org.ace.insurance.common;

public enum ReceiptNoCriteriaItems {
	RECEIPTNO("RECEIPTNO");
	private String label;

	private ReceiptNoCriteriaItems(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
