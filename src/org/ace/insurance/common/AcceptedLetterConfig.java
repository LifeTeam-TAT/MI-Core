package org.ace.insurance.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ace.java.component.SystemException;

public class AcceptedLetterConfig {
	private static String ACCEPTED_CASE1 = "ACCEPTED_CASE1";
	private static String ACCEPTED_CASE2 = "ACCEPTED_CASE2";
	private static String AGENT_INFO1 = "AGENT_INFO1";
	private static String AGENT_INFO2 = "AGENT_INFO2";
	private static String REJECTED_CASE1 = "REJECTED_CASE1";
	private static String REJECTED_CASE2 = "REJECTED_CASE2";
	private static String REJECTED_CASE3 = "REJECTED_CASE3";
	private static String REJECTED_CASE4 = "REJECTED_CASE4";

	private static Properties initClaimConfig;

	static {
		try {
			initClaimConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("acceptedletter.properties");
			initClaimConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "acceptedletter.properties");
		}
	}

	public static String getACCEPTED_CASE1() {
		return initClaimConfig.getProperty(ACCEPTED_CASE1);
	}

	public static String getAGENT_INFO1() {
		return initClaimConfig.getProperty(AGENT_INFO1);
	}

	public static String getACCEPTED_CASE2() {
		return initClaimConfig.getProperty(ACCEPTED_CASE2);
	}

	public static String getAGENT_INFO2() {
		return initClaimConfig.getProperty(AGENT_INFO2);
	}

	public static String getREJECTED_CASE1() {
		return initClaimConfig.getProperty(REJECTED_CASE1);
	}

	public static String getREJECTED_CASE2() {
		return initClaimConfig.getProperty(REJECTED_CASE2);
	}

	public static String getREJECTED_CASE3() {
		return initClaimConfig.getProperty(REJECTED_CASE3);
	}

	public static String getREJECTED_CASE4() {
		return initClaimConfig.getProperty(REJECTED_CASE4);
	}

}
