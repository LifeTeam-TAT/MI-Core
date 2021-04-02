/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.common;

public enum AddOnType {
	BASED_ON_SUMINSU("Percentage of Sum Insured"), 
	FIXED("Fixed Amount"), 
	BASED_ON_PREMIUN("Percentage of Premium"),
	BASED_ON_BASE_PREMIUM("According to Base Premium"),
	BASED_ON_ADDON_SUMINSU("Percentage of Additional Cover Sum Insred"),
	PER_UNIT("Per Unit");

	private String label;

	private AddOnType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}