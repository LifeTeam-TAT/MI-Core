package org.ace.insurance.report.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class AgentJobConfigLoader {
	private static Properties idConfig;
	static {
		try {
			idConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("/agent-job-config.properties");
			idConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load agent-job-config.properties");
		}
	}

	public static List<String> getKeys() {
		List<String> result = new ArrayList<String>();
		for (Object key : idConfig.keySet()) {
			result.add((String) key);
		}
		Collections.sort(result);
		return result;
	}

	public static List<String> getJobNames(String key) {
		String value = idConfig.getProperty(key);
		String[] arr = value.split(",");
		return new ArrayList<String>(Arrays.asList(arr));
	}
}