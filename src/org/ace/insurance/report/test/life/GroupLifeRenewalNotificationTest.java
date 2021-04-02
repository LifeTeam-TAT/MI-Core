package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ace.insurance.common.Utils;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class GroupLifeRenewalNotificationTest {
	private static Logger logger = LogManager.getLogger(GroupLifeIssueReportTest.class);
	private static ILifePolicyService lifePolicyService;
	private static IPaymentService paymentService;

	@BeforeClass
	public static void init() {
		logger.info("GroupLifeIssueReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");
		paymentService = (IPaymentService) factory.getBean("PaymentService");
		logger.info("GroupLifeIssueReportTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(GroupLifeIssueReportTest.class.getName());
	}

	@Test
	public void test() {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyByPolicyNo("GGLE/1310/0000002894/HO");
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("policyNo", lifePolicy.getPolicyNo());
			paramMap.put("customerName", lifePolicy.getCustomerName());
			paramMap.put("totalSumInsured", Utils.getCurrencyFormatString(lifePolicy.getTotalSumInsured()));
			paramMap.put("totalInsuredPerson", lifePolicy.getInsuredPersonInfo().size());
			paramMap.put("periodMonth", lifePolicy.getPeriodMonth());
			paramMap.put("startDate", Utils.getDateFormatString(lifePolicy.getActivedPolicyStartDate()));
			paramMap.put("endDate", Utils.getDateFormatString(lifePolicy.getActivedPolicyEndDate()));
			paramMap.put("currentDate", new Date());
			InputStream inputStream = new FileInputStream("report-template/life/GroupLifeRenewalNotificationLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/test.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
