package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.proposal.LifeProposal;
import org.ace.insurance.life.proposal.ProposalInsuredPerson;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
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

public class PublicLifeSanctionTest {
	private static Logger logger = LogManager.getLogger(LifePolicy.class);
	private static ILifeProposalService lifeProposalService;

	@BeforeClass
	public static void init() {
		logger.info("PublicLifeSanctionTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeProposalService = (ILifeProposalService) factory.getBean("LifeProposalService");
		logger.info("PublicLifeSanctionTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(PublicLifeSanctionTest.class.getName());
	}

	// @Test
	public void test() {
		try {
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById("ISLIF001HO000000000114062013");
			ProposalInsuredPerson insuredPerson = lifeProposal.getProposalInsuredPersonList().get(0);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("proposalNo", lifeProposal.getProposalNo());
			paramMap.put("customerName", insuredPerson.getFullName());
			paramMap.put("age", insuredPerson.getAge());
			paramMap.put("sumInsured", lifeProposal.getSumInsured());
			paramMap.put("period", lifeProposal.getPeriodMonth());
			paramMap.put("medicalInfo", insuredPerson.getClsOfHealth().getLabel());
			paramMap.put("premiumForOneThousandKyat", insuredPerson.getPremiumForOneThousandKyat());
			double totalTermPremim = lifeProposal.getTotalTermPremium();
			double kyat = Math.floor(totalTermPremim);
			double pyar = (totalTermPremim - kyat) * 100;
			DecimalFormat formatter1 = new DecimalFormat("#,###");
			String termPremiumKyat = formatter1.format(kyat);
			DecimalFormat formatter2 = new DecimalFormat("00");
			String termPremiumPyar = formatter2.format(pyar);

			int month = lifeProposal.getPaymentType().getMonth();
			switch (month) {
				case 12: {
					paramMap.put("paymentYear", "1");
					paramMap.put("paymentMonth", "");
					paramMap.put("oneYearPaymentKyat", termPremiumKyat);
					paramMap.put("oneYearPaymentPyar", termPremiumPyar);
					paramMap.put("sixMonthPaymentKyat", "");
					paramMap.put("sixMonthPaymentPyar", "");
					paramMap.put("threeMonthPaymentKyat", "");
					paramMap.put("threeMonthPaymentPyar", "");
				}
					break;
				case 6: {
					paramMap.put("paymentYear", "0");
					paramMap.put("paymentMonth", "6");
					paramMap.put("oneYearPaymentKyat", "");
					paramMap.put("oneYearPaymentPyar", "");
					paramMap.put("sixMonthPaymentKyat", termPremiumKyat);
					paramMap.put("sixMonthPaymentPyar", termPremiumPyar);
					paramMap.put("threeMonthPaymentKyat", "");
					paramMap.put("threeMonthPaymentPyar", "");
				}
					break;
				case 3: {
					paramMap.put("paymentYear", "");
					paramMap.put("paymentMonth", "3");
					paramMap.put("oneYearPaymentKyat", "");
					paramMap.put("oneYearPaymentPyar", "");
					paramMap.put("sixMonthPaymentKyat", "");
					paramMap.put("sixMonthPaymentPyar", "");
					paramMap.put("threeMonthPaymentKyat", termPremiumKyat);
					paramMap.put("threeMonthPaymentPyar", termPremiumPyar);
				}
					break;
			}
			paramMap.put("premiumKyat", termPremiumKyat);
			paramMap.put("premiumPyar", termPremiumPyar);
			InputStream inputStream = new FileInputStream("report-template/life/publicLifeSanction.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/PublicLifeSanction.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void prepareTest() {
		try {

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("proposalNo", "proposalNo");
			paramMap.put("customerName", "customerName");
			paramMap.put("age", 60);
			paramMap.put("sumInsured", 1000.0);
			paramMap.put("period", 2);
			paramMap.put("medicalInfo", "FirstClass");
			paramMap.put("premiumForOneThousandKyat", 1000.0);
			double totalTermPremim = 1000.0;
			double kyat = Math.floor(totalTermPremim);
			double pyar = (totalTermPremim - kyat) * 100;
			DecimalFormat formatter1 = new DecimalFormat("#,###");
			String termPremiumKyat = formatter1.format(kyat);
			DecimalFormat formatter2 = new DecimalFormat("00");
			String termPremiumPyar = formatter2.format(pyar);

			int month = 3;
			switch (month) {
				case 12: {
					paramMap.put("paymentYear", "1");
					paramMap.put("paymentMonth", "");
					paramMap.put("oneYearPaymentKyat", termPremiumKyat);
					paramMap.put("oneYearPaymentPyar", termPremiumPyar);
					paramMap.put("sixMonthPaymentKyat", "");
					paramMap.put("sixMonthPaymentPyar", "");
					paramMap.put("threeMonthPaymentKyat", "");
					paramMap.put("threeMonthPaymentPyar", "");
				}
					break;
				case 6: {
					paramMap.put("paymentYear", "0");
					paramMap.put("paymentMonth", "6");
					paramMap.put("oneYearPaymentKyat", "");
					paramMap.put("oneYearPaymentPyar", "");
					paramMap.put("sixMonthPaymentKyat", termPremiumKyat);
					paramMap.put("sixMonthPaymentPyar", termPremiumPyar);
					paramMap.put("threeMonthPaymentKyat", "");
					paramMap.put("threeMonthPaymentPyar", "");
				}
					break;
				case 3: {
					paramMap.put("paymentYear", "");
					paramMap.put("paymentMonth", "3");
					paramMap.put("oneYearPaymentKyat", "");
					paramMap.put("oneYearPaymentPyar", "");
					paramMap.put("sixMonthPaymentKyat", "");
					paramMap.put("sixMonthPaymentPyar", "");
					paramMap.put("threeMonthPaymentKyat", termPremiumKyat);
					paramMap.put("threeMonthPaymentPyar", termPremiumPyar);
				}
					break;
			}
			paramMap.put("premiumKyat", termPremiumKyat);
			paramMap.put("premiumPyar", termPremiumPyar);
			InputStream inputStream = new FileInputStream("report-template/life/publicLifeSanction.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/PublicLifeSanction.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
