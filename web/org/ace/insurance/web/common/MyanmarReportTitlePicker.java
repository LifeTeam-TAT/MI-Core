package org.ace.insurance.web.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyanmarReportTitlePicker {

	private static String YEAR_MM_TEXT = "YEAR_MM_TEXT";
	private static String FOR_MONTH_MM_TEXT = "FOR_MONTH_MM_TEXT";
	private static String TRAVEL_INCOME_MM_TEXT = "TRAVEL_INCOME_MM_TEXT";
	private static String LOCAL_TRAVEL_MM_TITLE = "LOCAL_TRAVEL_MM_TITLE";
	private static String OVERSEA_TRAVEL_MM_TITLE = "OVERSEA_TRAVEL_MM_TITLE";

	private static Properties reportTitle;

	static {
		try {
			reportTitle = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("/REPORT_TITLE_MM.properties");
			reportTitle.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load REPORT_TITLE_MM.properties");
		}
	}

	public static String getYearMyanmarText() {
		return reportTitle.getProperty(YEAR_MM_TEXT);
	}

	public static String getForMonthMyanmarText() {
		return reportTitle.getProperty(FOR_MONTH_MM_TEXT);
	}

	public static String getTravelIncomeMyanmarText() {
		return reportTitle.getProperty(TRAVEL_INCOME_MM_TEXT);
	}

	public static String getLocalTravelMyanmarText() {
		return reportTitle.getProperty(LOCAL_TRAVEL_MM_TITLE);
	}

	public static String getOverseaTravelMyanmarText() {
		return reportTitle.getProperty(OVERSEA_TRAVEL_MM_TITLE);
	}
}
