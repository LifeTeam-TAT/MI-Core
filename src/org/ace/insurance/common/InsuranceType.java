/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.common;

public enum InsuranceType {

	LIFE("Life"),

	STUDENT_LIFE("Student Life"),

	HEALTH("Health"),

	PERSON_TRAVEL("Preson Travel"),

	FARMER("Farmer"),

	MOTOR("MOTOR"),

	CASH_IN_SAFE("CASH_IN_SAFE"),

	FIRE("FIRE"),

	SHORT_ENDOWMENT_LIFE("Short Endowment Life"),

	PUBLIC_TERM_LIFE("Public Term Life"),

	SPORTMAN("Sport Man"),

	SPORTMANABROAD("Sport Man Abroad");

	private String label;

	private InsuranceType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}