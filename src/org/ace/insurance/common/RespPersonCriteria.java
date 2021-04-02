package org.ace.insurance.common;

public class RespPersonCriteria {
	public String criteriaValue;
	public RespPersonCriteriaItems respPersonCriteria;
	
	public RespPersonCriteria() {
		
	}

	public RespPersonCriteria(String criteriaValue, RespPersonCriteriaItems respPersonCriteria) {
		this.criteriaValue = criteriaValue;
		this.respPersonCriteria = respPersonCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public RespPersonCriteriaItems getRespPersonCriteria() {
		return respPersonCriteria;
	}

	public void setRespPersonCriteria(RespPersonCriteriaItems respPersonCriteria) {
		this.respPersonCriteria = respPersonCriteria;
	}

}
