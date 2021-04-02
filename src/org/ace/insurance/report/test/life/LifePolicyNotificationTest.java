package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.java.component.SystemException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class LifePolicyNotificationTest {
	private static Logger logger = LogManager.getLogger(LifePolicyNotificationTest.class);
	private static ILifePolicyService lifePolicyService;

	@BeforeClass
	public static void init() {
		logger.info("ReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");
		logger.info("ReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("ReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifePolicyNotificationTest.class.getName());
	}

	// @Test
	public void test() {
		LifePolicy lifePolicy = lifePolicyService.findLifePolicyById("ISLIF009HO000000000313062013");
		generateLifePolicyNotification(lifePolicy);
	}

	public static <T> void generateLifePolicyNotification(LifePolicy lifePolicy) {
		try {
			List jasperPrintList = new ArrayList();
			Map paramMap = new HashMap();
			paramMap.put("notificationDate", new Date());
			paramMap.put("insuredPersonName", lifePolicy.getInsuredPersonInfo().get(0).getFullName());
			paramMap.put("sumInsured", lifePolicy.getTotalSumInsured());
			paramMap.put("policyNo", lifePolicy.getPolicyNo());
			paramMap.put("basicTermPremium", lifePolicy.getTotalBasicTermPremium());
			paramMap.put("activePolicyEndDate", lifePolicy.getActivedPolicyEndDate());
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyNotification.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/LifePolicyNotification.pdf"));
			exporter.exportReport();
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate MotorCashReceipt", e);
		}
	}

	@Test
	public void prepareGenerateLifePolicyNotification() {
		try {
			List jasperPrintList = new ArrayList();
			Map paramMap = new HashMap();
			paramMap.put("notificationDate", new Date());
			paramMap.put("insuredPersonName", "insuredPersonName");
			paramMap.put("sumInsured", 100.0);
			paramMap.put("policyNo", "polic no");
			paramMap.put("basicTermPremium", 100.0);
			paramMap.put("activePolicyEndDate", new Date());
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyNotification.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/LifePolicyNotification.pdf"));
			exporter.exportReport();
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate MotorCashReceipt", e);
		}
	}

}
