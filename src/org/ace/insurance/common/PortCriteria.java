package org.ace.insurance.common;

public class PortCriteria {
	public String criteriaValue;
	public PortCriteriaItems portCriteria;

	public PortCriteria() {

	}

	public PortCriteria(String criteriaValue, PortCriteriaItems portCriteria) {
		this.criteriaValue = criteriaValue;
		this.portCriteria = portCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public PortCriteriaItems getPortCriteria() {
		return portCriteria;
	}

	public void setPortCriteria(PortCriteriaItems portCriteria) {
		this.portCriteria = portCriteria;
	}

}
