package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.report.life.LifePolicyReport;
import org.ace.insurance.report.life.LifePolicyReportCriteria;
import org.ace.insurance.report.life.service.interfaces.ILifePolicyReportService;
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

public class LifePolicyReportTest {
	private static Logger logger = LogManager.getLogger(LifePolicyReport.class);
	private static ILifePolicyReportService lifePolicyReportService;

	@BeforeClass
	public static void init() {
		logger.info("LifePolicyReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePolicyReportService = (ILifePolicyReportService) factory.getBean("LifePolicyReportService");
		logger.info("LifePolicyReportTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifePolicyReportTest.class.getName());
	}

	// @Test
	public void test() {
		try {
			LifePolicyReportCriteria criteria = new LifePolicyReportCriteria();
			// criteria.setStartDate(Utils.getDate("01-06-2013"));
			// criteria.setEndDate(Utils.getDate("15-06-2013"));
			List<LifePolicyReport> mpReportList = null;
			Map paramMap = new HashMap();
			paramMap.put("totalSumInsured", getTotalReportSumInsured(mpReportList));
			paramMap.put("totalPremium", getTotalReportPremiun(mpReportList));
			paramMap.put("TableDataSource", new JRBeanCollectionDataSource(mpReportList));
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifePolicyReport.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void prepareTest() {
		try {
			List<LifePolicyReport> mpReportList = new ArrayList<LifePolicyReport>();
			LifePolicyReport lifePolicyReport = new LifePolicyReport();
			lifePolicyReport.setPolicyNo("policyNo");
			lifePolicyReport.setProposalNo("proposalNo");
			// lifePolicyReport.setName("name");
			lifePolicyReport.setAddress("address");
			lifePolicyReport.setSumInsured(1000.0);
			lifePolicyReport.setPremium(1000.0);
			lifePolicyReport.setCashReceiptDate(new Date());
			lifePolicyReport.setReceiptNo("receiptNo");
			// lifePolicyReport.setRemark("remark");
			lifePolicyReport.setSubTotalSI(1000.0);
			lifePolicyReport.setSubTotalPremium(1000.0);
			mpReportList.add(lifePolicyReport);

			Map paramMap = new HashMap();
			paramMap.put("TableDataSource", new JRBeanCollectionDataSource(mpReportList));
			paramMap.put("grandTotalSI", 10000.0);
			paramMap.put("grandTotalPremium", 10000.0);
			paramMap.put("lastIndex", true);
			paramMap.put("branch", "branch");
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifePolicyReport.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private double getTotalReportSumInsured(List<LifePolicyReport> lifePolicyList) {
		double total = 0.0;
		for (LifePolicyReport lifePolicyReport : lifePolicyList) {
			total += lifePolicyReport.getSumInsured();
		}
		return total;

	}

	private double getTotalReportPremiun(List<LifePolicyReport> lifePolicyList) {
		double total = 0.0;
		for (LifePolicyReport lifePolicyReport : lifePolicyList) {
			total += lifePolicyReport.getPremium();
		}
		return total;

	}
}
