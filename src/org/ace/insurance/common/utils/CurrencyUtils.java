package org.ace.insurance.common.utils;

import org.ace.insurance.system.common.currency.Currency;

public class CurrencyUtils {
	/**
	 * Return true if Currency is US Dollar, false otherwise.
	 * 
	 * @param currency
	 * @return true if Currency is US Dollar, false otherwise.
	 */
	public static boolean isUSD(Currency currency) {
		return currency.getCurrencyCode().equalsIgnoreCase("USD") ? true : false;
	}

	/**
	 * Return true if Currency is SG Dollar, false otherwise.
	 * 
	 * @param currency
	 * @return true if Currency is SG Dollar, false otherwise.
	 */
	public static boolean isSGD(Currency currency) {
		return currency.getCurrencyCode().equalsIgnoreCase("SGD") ? true : false;
	}

	/**
	 * Return true if Currency is Myanmar Kyat, false otherwise.
	 * 
	 * @param currency
	 * @return true if Currency is Myanmar Kyat, false otherwise.
	 */

	public static boolean isKyat(Currency currency) {
		return currency.getCurrencyCode().equalsIgnoreCase("KYT") ? true : false;
	}


	/**
	 * Return currency format string
	 * 
	 * @param currency
	 * @return currency format string
	 */
	public static String getCurrencyFormat(Currency currency) {
		String currencyFormat = "";
		if (currency.getCurrencyCode().equalsIgnoreCase("USD")) {
			currencyFormat = "¤ #,##0.00";
		} else if (currency.getCurrencyCode().equalsIgnoreCase("KYT")) {
			currencyFormat = "#,##0.00";
		}

		return currencyFormat;
	}

	/**
	 * Return Currency Code if Currency is not null, 'KYT' otherwise.
	 * 
	 * @param currency
	 * @return Currency Code if Currency is not null, 'KYT' otherwise.
	 */
	public static String getCurrencyCode(Currency currency) {
		return currency == null ? "KYT" : currency.getCurrencyCode();
	}

}
