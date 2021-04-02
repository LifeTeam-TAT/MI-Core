package org.ace.insurance.web.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ace.insurance.common.ErrorCode;
import org.ace.java.component.SystemException;

public class InitialClaimConfig {

	private static String AGENT_NAME_AND_NO = "AGENT_NAME_AND_NO";
	private static String POLICY_NO = "POLICY_NO";
	private static String UNIT = "UNIT";
	private static String INSUREDPERSON_NAME = "INSUREDPERSON_NAME";
	private static String FATHER_NAME = "FATHER_NAME";
	private static String NRC = "NRC";
	private static String OCCUPATION = "OCCUPATION";
	private static String ADDRESS = "ADDRESS";
	private static String PHONE_NO = "PHONE_NO";
	private static String AGENT_COMMI_NAME = "AGENT_COMMI_NAME";

	private static Properties initClaimConfig;

	static {
		try {
			initClaimConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("initial_claim.properties");
			initClaimConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load initial_claim.properties");
		}
	}

	public static String getAgentNameAndNo() {
		return initClaimConfig.getProperty(AGENT_NAME_AND_NO);
	}

	public static String getPolicyNo() {
		return initClaimConfig.getProperty(POLICY_NO);
	}

	public static String getUnit() {
		return initClaimConfig.getProperty(UNIT);
	}

	public static String getInsuredPersonName() {
		return initClaimConfig.getProperty(INSUREDPERSON_NAME);
	}

	public static String getFatherName() {
		return initClaimConfig.getProperty(FATHER_NAME);
	}

	public static String getNrc() {
		return initClaimConfig.getProperty(NRC);
	}

	public static String getOccupation() {
		return initClaimConfig.getProperty(OCCUPATION);
	}

	public static String getAddress() {
		return initClaimConfig.getProperty(ADDRESS);
	}

	public static String getPhoneNo() {
		return initClaimConfig.getProperty(PHONE_NO);
	}

	public static String getAgentCommiName() {
		return initClaimConfig.getProperty(AGENT_COMMI_NAME);
	}
}
