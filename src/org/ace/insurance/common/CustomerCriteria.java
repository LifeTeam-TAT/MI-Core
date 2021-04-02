package org.ace.insurance.common;

import java.util.Arrays;
import java.util.List;

public class CustomerCriteria {
	public String criteriaValue;
	public CustomerCriteriaItems customerCriteria;

	public CustomerCriteria() {

	}

	public CustomerCriteria(String criteriaValue, CustomerCriteriaItems customerCriteria) {
		this.criteriaValue = criteriaValue;
		this.customerCriteria = customerCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public CustomerCriteriaItems getCustomerCriteria() {
		return customerCriteria;
	}

	public void setCustomerCriteria(CustomerCriteriaItems customerCriteria) {
		this.customerCriteria = customerCriteria;
	}

	public List<CustomerCriteriaItems> getCustomerCriteriaItemsList() {
		return Arrays.asList(CustomerCriteriaItems.values());
	}

}
