package org.ace.insurance.web.common.ntw.mym;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class AbstractMynNumConvertor {
	protected static Properties mymNumberConfig;
	static {
		try {
			mymNumberConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("/mym-number.properties");
			mymNumberConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load mym-number.properties");
		}
	}
	protected final String SEPARATOR = " ";
	protected final int NO_VALUE = -1;
	protected final String KYAT = mymNumberConfig.getProperty(MymNumConstant.KYAT);
	protected final String PYA = mymNumberConfig.getProperty(MymNumConstant.PYA);
	protected final String DOLLAR = mymNumberConfig.getProperty(MymNumConstant.DOLLAR);
	protected final String CENT = mymNumberConfig.getProperty(MymNumConstant.CENT);
	protected final String AND = mymNumberConfig.getProperty(MymNumConstant.AND);
	protected final String MINUS = mymNumberConfig.getProperty(MymNumConstant.MINUS);
	protected final String CURPOSTFIX = mymNumberConfig.getProperty(MymNumConstant.CURPOSTFIX);

	protected List<Integer> getDigits(long value) {
		ArrayList<Integer> digits = new ArrayList<Integer>();
		if (value == 0) {
			digits.add(0);
		} else {
			while (value > 0) {
				digits.add(0, (int) value % 10);
				value /= 10;
			}
		}
		return digits;
	}

	public String getName(long value) {
		return getName(Long.toString(value));
	}

	public String getName(double value) {
		String dString = new DecimalFormat("#").format(value);
		return getName(dString);
	}

	public String getNameWithDecimal(double amount) {
		String value = new DecimalFormat("##.00").format(amount);
		long totalPya = 0;
		long totalKyat = 0;
		boolean negative = false;
		if (value.startsWith("-")) {
			negative = true;
			value = value.substring(1);
		}
		int decimals = value.indexOf(".");
		if (0 <= decimals) {
			String pya = value.substring(decimals + 1);
			if (pya.startsWith("0") && pya.length() > 1) {
				pya = pya.substring(1);
				totalPya = Long.parseLong(pya);
				totalPya = totalPya >= 5 ? 1 : 0;
			} else
				totalPya = Long.parseLong(pya);
			if (!value.substring(0, decimals).isEmpty())
				totalKyat = Long.parseLong(value.substring(0, decimals));
		}

		String totalAmountKyat = totalKyat > 0 ? getName(totalKyat) : "";
		String totalAmountPya = totalPya > 0 ? getName(totalPya) : "";
		totalAmountKyat = totalKyat > 0 ? KYAT.concat(SEPARATOR).concat(totalAmountKyat).concat(SEPARATOR).concat(CURPOSTFIX) : "";
		totalAmountPya = totalPya > 0 ? totalAmountPya.concat(PYA.concat(SEPARATOR)) : "";

		if (negative) {
			if (totalKyat > 0 && totalPya > 0) {
				return MINUS.concat(SEPARATOR).concat(totalAmountKyat).concat(AND).concat(SEPARATOR).concat(totalAmountPya);
			} else
				return MINUS.concat(SEPARATOR).concat(totalAmountKyat).concat(totalAmountPya);
		} else {
			if (totalKyat > 0 && totalPya > 0) {
				return totalAmountKyat.concat(AND).concat(SEPARATOR).concat(totalAmountPya);
			} else
				return totalAmountKyat.concat(totalAmountPya);
		}
	}

	public String getNameWithDollarDecimal(double amount) {
		String value = new DecimalFormat("##.00").format(amount);
		long totalPya = 0;
		long totalKyat = 0;
		boolean negative = false;
		if (value.startsWith("-")) {
			negative = true;
			value = value.substring(1);
		}
		int decimals = value.indexOf(".");
		if (0 <= decimals) {
			String pya = value.substring(decimals + 1);
			if (pya.startsWith("0") && pya.length() > 1) {
				pya = pya.substring(1);
				totalPya = Long.parseLong(pya);
				totalPya = totalPya >= 5 ? 1 : 0;
			} else
				totalPya = Long.parseLong(pya);
			if (!value.substring(0, decimals).isEmpty())
				totalKyat = Long.parseLong(value.substring(0, decimals));
		}

		String totalAmountKyat = totalKyat > 0 ? getName(totalKyat) : "";
		String totalAmountPya = totalPya > 0 ? getName(totalPya) : "";
		totalAmountKyat = totalKyat > 0 ? DOLLAR.concat(SEPARATOR).concat(totalAmountKyat).concat(SEPARATOR).concat(CURPOSTFIX) : "";
		totalAmountPya = totalPya > 0 ? totalAmountPya.concat(CENT.concat(SEPARATOR)) : "";

		if (negative) {
			if (totalKyat > 0 && totalPya > 0) {
				return MINUS.concat(SEPARATOR).concat(totalAmountKyat).concat(AND).concat(SEPARATOR).concat(totalAmountPya);
			} else
				return MINUS.concat(SEPARATOR).concat(totalAmountKyat).concat(totalAmountPya);
		} else {
			if (totalKyat > 0 && totalPya > 0) {
				return totalAmountKyat.concat(AND).concat(SEPARATOR).concat(totalAmountPya);
			} else
				return totalAmountKyat.concat(totalAmountPya);
		}
	}

	abstract public String getName(String value);

	public String getConcateDollorAndStampFee(String usdPremiumInword, String stampFeeInword) {
		return usdPremiumInword.concat(AND).concat(SEPARATOR).concat(stampFeeInword);
	}
}
