package org.ace.insurance.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "IdConditionType")
@XmlEnum
public enum IdConditionType {
	A("A"), C("C"), D("D"), E("E"), F("F"), G("G"), N("N"), P("P"), TH("TH"), YA("YA") ;

	private String label;

	private IdConditionType(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}
