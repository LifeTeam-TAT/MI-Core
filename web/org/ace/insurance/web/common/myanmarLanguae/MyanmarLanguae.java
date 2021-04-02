package org.ace.insurance.web.common.myanmarLanguae;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.ace.insurance.product.Product;
import org.ace.insurance.web.common.KeyFactorChecker;

public class MyanmarLanguae {
	private static Properties myanConfig;
	private static String STUDENT_LIFE_LABEL_1 = "STUDENT_LIFE_LABEL_1";
	private static String PUBLIC_LIFE_LABEL_1 = "PUBLIC_LIFE_LABEL_1";
	private static String SHORT_TERM_LIFE_LABEL_1 = "SHORT_TERM_LIFE_LABEL_1";
	private static String GOVERNMENT_ENDOWNMENT_LIFE_LABEL1 = "GOVERNMENT_ENDOWNMENT_LIFE_LABEL1";
	private static String GROUP_LIFE_LABEL_1 = "GROUP_LIFE_LABEL_1";
	private static String SNAKE_BITE_LABEL_1 = "SNAKE_BITE_LABEL_1";
	private static String PERSONAL_ACCIDENT_LABEL_1 = "PERSONAL_ACCIDENT_LABEL_1";
	private static String FARMER_LABEL_1 = "FARMER_LABEL_1";
	private static String HEALTH_LABEL = "HEALTH_LABEL";
	private static String CRITICAL_ILLNESS_LABEL = "CRITICAL_ILLNESS_LABEL";
	private static String MICROHEALTH_LABEL = "MICROHEALTH_LABEL";
	static {
		try {
			myanConfig = new Properties();
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream in = classLoader.getResourceAsStream("/LANGUAGE_en.properties");
			myanConfig.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load LANGUAGE_en.properties");
		}
	}

	public static String getMyanmarLanguaeString(String lable) {
		return myanConfig.getProperty(lable);
	}

	public static String getProductNameMyanmarLanguage(Product product) {
		String productName = "";
		if (KeyFactorChecker.isStudentLife(product.getId())) {
			productName = myanConfig.getProperty(STUDENT_LIFE_LABEL_1);
		}
		if (KeyFactorChecker.isPublicLife(product)) {
			productName = myanConfig.getProperty(PUBLIC_LIFE_LABEL_1);
		}
		if (KeyFactorChecker.isShortTermEndowment(product.getId())) {
			productName = myanConfig.getProperty(SHORT_TERM_LIFE_LABEL_1);
		}
		if (KeyFactorChecker.isGovernmentShortTermEndowment(product.getId())) {
			productName = myanConfig.getProperty(GOVERNMENT_ENDOWNMENT_LIFE_LABEL1);
		}
		if (KeyFactorChecker.isGroupLife(product)) {
			productName = myanConfig.getProperty(GROUP_LIFE_LABEL_1);
		}
		if (KeyFactorChecker.isPersonalAccident(product) || (KeyFactorChecker.isPersonalAccidentUSD(product))) {
			productName = myanConfig.getProperty(PERSONAL_ACCIDENT_LABEL_1);
		}
		if (KeyFactorChecker.isFarmer(product)) {
			productName = myanConfig.getProperty(FARMER_LABEL_1);
		}
		if (KeyFactorChecker.isHealth(product.getId()) || KeyFactorChecker.isGroupHealth(product.getId())) {
			productName = myanConfig.getProperty(HEALTH_LABEL);
		}
		if (KeyFactorChecker.isCriticalIllness(product.getId()) || KeyFactorChecker.isGroupCriticalIllness(product.getId())) {
			productName = myanConfig.getProperty(CRITICAL_ILLNESS_LABEL);
		}

		if (KeyFactorChecker.isSnakeBite(product.getId())) {
			productName = myanConfig.getProperty(SNAKE_BITE_LABEL_1);
		}

		if (KeyFactorChecker.isMicroHealth(product.getId())) {
			productName = myanConfig.getProperty(MICROHEALTH_LABEL);
		}
		return productName;
	}

}
