package org.ace.insurance.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;
@XmlType(name = "paymentReferenceType")
@XmlEnum
public enum PaymentReferenceType {

		UNDERWRITING("UNDERWRITING"), CLAIM("CLAIM");

		private String label;

		private PaymentReferenceType(String label) {
			this.label = label;
		}

		public String getLabel() {
			return label;
		}
}
