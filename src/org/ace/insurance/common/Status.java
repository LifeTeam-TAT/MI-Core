package org.ace.insurance.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "status")
@XmlEnum
public enum Status {
	APPLYING("Applying"), APPROVED("Approved"), REJECTED("Rejected"), PAID("Paid"), ISSUED("Issued");

	private String label;

	private Status(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
