package org.ace.insurance.common;

public class SaleManCriteria {
	public String criteriaValue;
	public SaleManCriteriaItems saleManCriteriaItems;
	
	public SaleManCriteria() {
	}
	
	public SaleManCriteria(String criteriaValue, SaleManCriteriaItems saleManCriteriaItems) {
		this.criteriaValue = criteriaValue;
		this.saleManCriteriaItems = saleManCriteriaItems;
	}


	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public SaleManCriteriaItems getSaleManCriteriaItems() {
		return saleManCriteriaItems;
	}

	public void setSaleManCriteriaItems(SaleManCriteriaItems saleManCriteriaItems) {
		this.saleManCriteriaItems = saleManCriteriaItems;
	}

}
