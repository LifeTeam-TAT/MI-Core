package org.ace.insurance.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
	private String result;
	private String intFormat = "#,###,###";
	private String doubleFormat = "#,###,###.00";
	private String currencyFormat = "#,###,###.00";
	private String dateFormat = "dd-MM-yyy hh:mm a";
	
	public String formatIntValue(int value) {
		result = new DecimalFormat(intFormat).format(value);
		return result;
	}
	
	public String formatDoubleValue(double value) {
		result = new DecimalFormat(doubleFormat).format(value);
		return result;
	}
	
	public String formatCurrencyValue(double value) {
		result = new DecimalFormat(currencyFormat).format(value);
		return result;
	}

	public String formatCurrencyValue(long value) {
		result = new DecimalFormat(currencyFormat).format(value);
		return result;
	}
	
	public String formatStringDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		result = format.format(date);
		return result;
	}

	@Override
	public String toString() {
		return result;
	}
}
