/***************************************************************************************
 * @author <<Your Name>>
 * @Date 2013-02-11
 * @Version 1.0
 * @Purpose <<You have to write the comment the main purpose of this class>>
 * 
 *    
 ***************************************************************************************/
package org.ace.insurance.common;

public enum ReferenceValue {
	WALL("WALL"), 
	FLOOR("FLOOR"), 
	ROOF("ROOF"),
	OCCUPATION("OCCUPATION"),
	BUILDINGCLASS("BUILDINGCLASS");

	private String label;

	private ReferenceValue(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}