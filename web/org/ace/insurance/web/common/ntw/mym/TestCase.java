package org.ace.insurance.web.common.ntw.mym;

import java.text.DecimalFormat;

public class TestCase {
	public static void main(String[] args) {
		double value = 690000;
		AbstractMynNumConvertor convertor = new DefaultConvertor();
		System.out.println(convertor.getNameWithDecimal(value));
		String dString = new DecimalFormat("#").format(value);
		System.out.println(dString.length());
	}
}
