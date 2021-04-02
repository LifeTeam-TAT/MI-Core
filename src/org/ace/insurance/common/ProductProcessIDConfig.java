package org.ace.insurance.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ace.java.component.SystemException;

public class ProductProcessIDConfig {
	private static String MEDICAL_PROPOSAL = "MEDICAL_PROPOSAL";
	private static String PROPOSAL_PROCESS_ID = "PROPOSAL_PROCESS_ID";
	private static String MEDICAL_PRODUCT_ID = "MEDICAL_PRODUCT_ID";

	private static String HOSPITALIZATION_CLAIM = "HOSPITALIZATION_CLAIM";
	private static String DEATH_CLAIM = "DEATH_CLAIM";
	private static String OPERATION_CLAIM = "OPERATION_CLAIM";
	private static String MEDICATION_CLAIM = "MEDICATION_CLAIM";

	private static Properties idConfig;

	static {
		try {
			idConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("product_process-id-config.properties");
			idConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load product_process-id-config.properties");
		}
	}

	public static String getMedicalProposalId() {
		return idConfig.getProperty(MEDICAL_PROPOSAL);
	}

	public static String getProposalProcessId() {
		return idConfig.getProperty(PROPOSAL_PROCESS_ID);
	}

	public static String getMedicalProductId() {
		return idConfig.getProperty(MEDICAL_PRODUCT_ID);
	}

	public static String getHospitalizationClaimId() {
		return idConfig.getProperty(HOSPITALIZATION_CLAIM);
	}

	public static String getDeathClaimId() {
		return idConfig.getProperty(DEATH_CLAIM);
	}

	public static String getOperationClaimId() {
		return idConfig.getProperty(OPERATION_CLAIM);
	}

	public static String getMedicationClaimId() {
		return idConfig.getProperty(MEDICATION_CLAIM);
	}

}
