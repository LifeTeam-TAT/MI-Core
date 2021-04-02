package org.ace.java.component.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesManager {

	public static final String DATASOURCE_CONFIG = "DataSource-Config.properties";

	public static void main(String[] arg) {
		Properties properties = new Properties();
		try {
			InputStream stream = PropertiesManager.class.getClassLoader().getResourceAsStream("");
			System.out.println(stream);
			properties.load(stream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties getProperties(String propertiesName) {
		Properties properties = new Properties();
		try {
			properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
}
