package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.LifeProductType;
import org.ace.insurance.common.MonthType;
import org.ace.insurance.common.Utils;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.life.LifePolicyMonthlyReport;
import org.ace.insurance.report.life.report.LifeMonthlyReport;
import org.ace.insurance.report.life.service.interfaces.ILifePolicyMonthlyReportService;
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

public class LifePolicyMonthlyReportTest {
	private static Logger logger = LogManager.getLogger(LifePolicyMonthlyReportTest.class);
	private static ILifePolicyMonthlyReportService lifePolicyMonthlyReportService;

	@BeforeClass
	public static void init() {
		logger.info("LifeProposalReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePolicyMonthlyReportService = (ILifePolicyMonthlyReportService) factory.getBean("LifePolicyMonthlyReportService");
		logger.info("LifeProposalReportTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifePolicyMonthlyReportTest.class.getName());
	}

	// @Test
	public void test() {
		try {
			SummaryReportCriteria criteria = new SummaryReportCriteria();
			criteria.setStartDate(Utils.getDate("31-07-2013"));
			criteria.setEndDate(Utils.getDate("01-08-2013"));
			criteria.setMonth(0);
			criteria.setYear(2013);
			criteria.setLifeProductType(LifeProductType.PUBLIC_LIFE);
			List<LifeMonthlyReport> reports = lifePolicyMonthlyReportService.findLifePolicyMonthlyReportByCriteria(criteria);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("LifePolicyMonthlyReports", new JRBeanCollectionDataSource(reports));
			params.put("productFiltered", criteria.getLifeProductType());
			params.put("monthFiltered", criteria.getMonth());
			params.put("yearFiltered", criteria.getYear());
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyMonthlyReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, params, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifePolicyMonthlyReport.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void prepareTest() {
		try {
			List<LifePolicyMonthlyReport> reports = new ArrayList<LifePolicyMonthlyReport>();
			LifePolicyMonthlyReport lifePolicyMonthlyReport = new LifePolicyMonthlyReport();
			lifePolicyMonthlyReport.setCustomerName("customerName");
			lifePolicyMonthlyReport.setAge("age");
			lifePolicyMonthlyReport.setPolicyNo("policyNo");
			lifePolicyMonthlyReport.setAddress("address");
			lifePolicyMonthlyReport.setSumInsure(1000.0);
			lifePolicyMonthlyReport.setPeriod("period");
			lifePolicyMonthlyReport.setNumberOfInsuredPerson(1);
			lifePolicyMonthlyReport.setPremium(1000.0);
			lifePolicyMonthlyReport.setPaymentType("paymentType");
			lifePolicyMonthlyReport.setAgentCommission(1000.0);
			lifePolicyMonthlyReport.setReceiptNo("receiptNo");
			lifePolicyMonthlyReport.setAge("agentName");
			lifePolicyMonthlyReport.setPaymentDate(new Date());
			lifePolicyMonthlyReport.setCodeNo("codeNo");
			reports.add(lifePolicyMonthlyReport);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("LifePolicyMonthlyReports", new JRBeanCollectionDataSource(reports));
			params.put("productFiltered", LifeProductType.PUBLIC_LIFE);
			params.put("monthFiltered", MonthType.DEC);
			params.put("yearFiltered", 2014);
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyMonthlyReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, params, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifePolicyMonthlyReport.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
