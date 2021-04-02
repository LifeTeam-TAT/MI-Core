package org.ace.insurance.common;

public class UserCriteria {
	public String criteriaValue;
	public UserCriteriaItems userCriteria;

	public UserCriteria() {

	}

	public UserCriteria(String criteriaValue, UserCriteriaItems userCriteria) {
		this.criteriaValue = criteriaValue;
		this.userCriteria = userCriteria;
	}

	public String getCriteriaValue() {
		return criteriaValue;
	}

	public void setCriteriaValue(String criteriaValue) {
		this.criteriaValue = criteriaValue;
	}

	public UserCriteriaItems getUserCriteria() {
		return userCriteria;
	}

	public void setUserCriteria(UserCriteriaItems userCriteria) {
		this.userCriteria = userCriteria;
	}
}
