package org.ace.insurance.web.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConstantsUtil {
	private static String CUSTOMER_MAX_NUMBER = "CUSTOMER_MAX_NUMBER";

	private static Properties idConfig;
	static {
		try {
			idConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("/constants_util.properties");
			idConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load keyfactor-id-config.properties");
		}
	}

	public static int CUSTOMER_MAX_NUMBER() {
		return Integer.parseInt(idConfig.getProperty(CUSTOMER_MAX_NUMBER));
	}

}
