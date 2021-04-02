package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Utils;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.report.life.LifePremiumLedgerReport;
import org.ace.insurance.report.life.LifePremiumLedgerReport.PremiumLedgerInfo;
import org.ace.insurance.report.life.service.interfaces.ILifePremiumLedgerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class LifePremiumLedgerReportTest {
	private static Logger logger = LogManager.getLogger(LifePremiumLedgerReportTest.class);
	private static ILifePremiumLedgerService lifePremiumLedgerService;

	@BeforeClass
	public static void init() {
		logger.info("LifePremiumLedgerReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePremiumLedgerService = (ILifePremiumLedgerService) factory.getBean("LifePremiumLedgerService");
		logger.info("LifePremiumLedgerReportTest instance has been loaded.");
	}

	@AfterClass
	public static void finished() {
		logger.info("LifePremiumLedgerReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifePremiumLedgerReportTest.class.getName());
	}

	// @Test
	public void report() throws Exception {
		LifePremiumLedgerReport ledgerReport = lifePremiumLedgerService.findLifePremiumLedgerReport("ISLIF009HO000000014105082013");
		InputStream inputStream = new FileInputStream("report-template/life/lifePremiumLedgerReport.jrxml");
		Map paramMap = new HashMap();
		String firstComission = "";
		String renewalComission = "";
		String agent = "";
		LifePolicy lifePolicy = ledgerReport.getLifePolicy();
		if (lifePolicy.getAgent() != null) {
			firstComission = Utils.getCurrencyFormatString(Double.valueOf(lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getFirstCommission()));
			renewalComission = Utils.getCurrencyFormatString(Double.valueOf(lifePolicy.getPolicyInsuredPersonList().get(0).getProduct().getRenewalCommission()));
			agent = lifePolicy.getAgent().getFullName();
		}
		paramMap.put("policyNo", lifePolicy.getPolicyNo());
		paramMap.put("sumInsured", lifePolicy.getTotalSumInsured());
		paramMap.put("agent", agent);
		paramMap.put("firstComission", firstComission);
		paramMap.put("renewalComission", renewalComission);
		paramMap.put("paymentType", lifePolicy.getPaymentType().getName());
		paramMap.put("basicPremium", lifePolicy.getTotalPremium());
		paramMap.put("basicTermPremium", lifePolicy.getTotalBasicTermPremium());
		paramMap.put("customerNameAndAddress", lifePolicy.getCustomerName() + " / " + lifePolicy.getCustomerAddress());
		paramMap.put("surveyDate", ledgerReport.getSurveyDate());
		paramMap.put("startDate", lifePolicy.getActivedPolicyStartDate());
		paramMap.put("lastPaymentDate", lifePolicy.getLastPaymentDate());
		paramMap.put("endDate", lifePolicy.getActivedPolicyEndDate());
		paramMap.put("paymentInfos", new JRBeanCollectionDataSource(ledgerReport.getPremiumLedgerInfoList()));
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/lifePremiumLedgerReport.pdf");
	}

	@Test
	public void prepareReport() throws Exception {

		InputStream inputStream = new FileInputStream("report-template/life/lifePremiumLedgerReport.jrxml");
		Map paramMap = new HashMap();

		List<PremiumLedgerInfo> premiumLedgerInfoList = new ArrayList<PremiumLedgerInfo>();
		LifePremiumLedgerReport lifePremiumLedgerReport = new LifePremiumLedgerReport();
		PremiumLedgerInfo premiumLedgerInfo = lifePremiumLedgerReport.new PremiumLedgerInfo();
		premiumLedgerInfo.setReceiptDate(new Date());
		premiumLedgerInfo.setReceiptNo("receiptNo");
		premiumLedgerInfo.setDueDate(new Date());
		premiumLedgerInfoList.add(premiumLedgerInfo);

		paramMap.put("policyNo", "policyNo");
		paramMap.put("sumInsured", 100.0);
		paramMap.put("agent", "agent");
		paramMap.put("firstComission", "1000.0");
		paramMap.put("renewalComission", "100.0");
		paramMap.put("paymentType", "paymentType");
		paramMap.put("basicPremium", 1000.0);
		paramMap.put("basicTermPremium", 1000.0);
		paramMap.put("customerNameAndAddress", "customerNameAndAddress");
		paramMap.put("surveyDate", new Date());
		paramMap.put("startDate", new Date());
		paramMap.put("lastPaymentDate", new Date());
		paramMap.put("endDate", new Date());
		paramMap.put("paymentInfos", new JRBeanCollectionDataSource(premiumLedgerInfoList));
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/lifePremiumLedgerReport.pdf");
	}

}
