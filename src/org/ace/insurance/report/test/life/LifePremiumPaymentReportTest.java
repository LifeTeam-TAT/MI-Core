package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.report.life.LifeDailyIncomeReportDTO;
import org.ace.insurance.report.life.LifePremiumPaymentReport;
import org.ace.insurance.report.life.service.interfaces.ILifePremiumPaymentReportService;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class LifePremiumPaymentReportTest {
	private static Logger logger = LogManager.getLogger(LifeDailyIncomeReportDTO.class);
	private static ILifePremiumPaymentReportService lifePremiumPaymentReportService;

	@BeforeClass
	public static void init() {
		logger.info("LifeDailyIncomeReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePremiumPaymentReportService = (ILifePremiumPaymentReportService) factory.getBean("LifePremiumPaymentReportService");
		logger.info("LifeDailyIncomeReportTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifePremiumPaymentReportTest.class.getName());
	}

	@Test
	public void prepareTest() {
		try {
			List<LifePremiumPaymentReport> reportList = new ArrayList<LifePremiumPaymentReport>();
			LifePremiumPaymentReport lifePremiumPaymentReport = new LifePremiumPaymentReport();
			lifePremiumPaymentReport.setCashReceiptNo("cashReceiveNo");
			lifePremiumPaymentReport.setCustomerName("customer");
			lifePremiumPaymentReport.setPolicyNo("policyNo");
			lifePremiumPaymentReport.setIncome(1000.0);
			lifePremiumPaymentReport.setSumInsured(1000.0);
			reportList.add(lifePremiumPaymentReport);
			Map paramMap = new HashMap();
			paramMap.put("grandTotalPremium", 10000.0);
			paramMap.put("lastIndex", true);
			paramMap.put("grandTotalSI", 10000.0);
			paramMap.put("grandTotalIncome", 10000.0);
			paramMap.put("branch", "branch");
			paramMap.put("TableDataSource", new JRBeanCollectionDataSource(reportList));
			InputStream inputStream = new FileInputStream("report-template/life/lifePremiumPaymentReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifePremiumPaymentReport.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
