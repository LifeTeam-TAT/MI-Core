package org.ace.insurance.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ace.java.component.SystemException;

public class BranchSetting {
	private static String BRANCH_ID = "BRANCH_ID";

	private static Properties branchSetting;

	static {
		try {
			branchSetting = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("branch_setting.properties");
			branchSetting.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load branch_setting.properties");
		}
	}

	public static String getBranchId() {
		return branchSetting.getProperty("BRANCH_ID");
	}

}
