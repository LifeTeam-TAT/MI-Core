package org.ace.insurance.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ace.java.component.SystemException;

public class UploadFileConfig {
	private static Properties uploadFileConfig;

	private static final String UPLOAD_PATH_HOME = "UPLOAD_PATH_HOME";

	static {
		try {
			uploadFileConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("upload_file_config.properties");
			uploadFileConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to load upload_file_config.properties.");
		}
	}

	public static String getUploadFilePathHome() {
		return uploadFileConfig.getProperty(UPLOAD_PATH_HOME);
	}

}
