package org.ace.insurance.common;

public class ExpressCriteria {
	public String criteriaValue;
	public ExpressCriteriaItems expressCriteria;

	public ExpressCriteria() {

	}

	public ExpressCriteria(String criteriaValue, ExpressCriteriaItems expressCriteria) {
		this.criteriaValue = criteriaValue;
		this.expressCriteria = expressCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public ExpressCriteriaItems getExpressCriteria() {
		return expressCriteria;
	}

	public void setExpressCriteria(ExpressCriteriaItems expressCriteria) {
		this.expressCriteria = expressCriteria;
	}
}
