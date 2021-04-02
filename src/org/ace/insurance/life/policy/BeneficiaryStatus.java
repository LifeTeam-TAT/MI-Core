/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 *
 *
 ***************************************************************************************/
package org.ace.insurance.life.policy;

public enum BeneficiaryStatus {
	NONE("NONE"),
	DISCHARGED("DISCHARGED"),
	INHERITED("INHERITED"),
	REJECTED("REJECTED");

	private String label;

	private BeneficiaryStatus(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}