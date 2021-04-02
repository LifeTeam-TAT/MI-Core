package org.ace.insurance.claimproduct.testcase;

import java.util.HashMap;
import java.util.Map;

import org.ace.insurance.claimproduct.ClaimProduct;
import org.ace.insurance.claimproduct.service.interfaces.IClaimProductService;
import org.ace.insurance.common.KeyFactorType;
import org.ace.insurance.system.common.keyfactor.KeyFactor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClaimProductServiceTest {

	private static Logger logger = LogManager.getLogger(ClaimProductServiceTest.class);
	private static IClaimProductService claimProductService;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void init() {
		logger.info("ClaimProductServiceTestCase is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		claimProductService = (IClaimProductService) factory.getBean("ClaimProductService");
		logger.info("ClaimProductServiceTestCase instance has been loaded.");

	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(ClaimProductServiceTest.class.getName());
	}

	@AfterClass
	public static void finished() {
		logger.info("ClaimProductServiceTestCase has been finished.........................................");
	}

	@Test
	public void findPremiumRate() {
		Map<KeyFactor, String> keyFactorMap = new HashMap<KeyFactor, String>();
		ClaimProduct claimProduct = claimProductService.findClaimProductById("ISCPD001001000000000216032016");

		KeyFactor surrenderAgeKF = new KeyFactor();
		surrenderAgeKF.setId("ISSYS013001000000295708032016");
		surrenderAgeKF.setKeyFactorType(KeyFactorType.FROM_TO);

		KeyFactor paymentYearKF = new KeyFactor();
		paymentYearKF.setId("ISSYS013001000000295808032016");
		paymentYearKF.setKeyFactorType(KeyFactorType.FIXED);

		KeyFactor policyPeriodKF = new KeyFactor();
		policyPeriodKF.setId("ISSYS013001000000295908032016");
		policyPeriodKF.setKeyFactorType(KeyFactorType.FIXED);

		keyFactorMap.put(surrenderAgeKF, "29");
		keyFactorMap.put(paymentYearKF, "2");
		keyFactorMap.put(policyPeriodKF, "11");

		double surrenderAmount = claimProductService.findClaimProductRate(keyFactorMap, claimProduct, 10000000);
		logger.debug(" Surrender Amount : " + surrenderAmount);
	}
}
