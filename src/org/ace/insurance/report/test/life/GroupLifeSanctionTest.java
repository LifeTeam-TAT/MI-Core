package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

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

public class GroupLifeSanctionTest {
	private static Logger logger = LogManager.getLogger(GroupLifeSanctionTest.class);
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
		org.junit.runner.JUnitCore.main(GroupLifeSanctionTest.class.getName());
	}

	// @Test
	public void test() {
		try {
			LifeProposal lifeProposal = lifeProposalService.findLifeProposalById("ISLIF001001000000365102012014");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<Double, Integer> ratePairs = new HashMap<Double, Integer>();
			for (ProposalInsuredPerson insuredPerson : lifeProposal.getProposalInsuredPersonList()) {
				double premium = insuredPerson.getProposedPremium();
				if (ratePairs.containsKey(premium)) {
					ratePairs.put(premium, ratePairs.get(premium) + 1);
				} else {
					ratePairs.put(premium, 1);
				}
			}
			paramMap.put("insuredName", lifeProposal.getCustomerName());
			paramMap.put("totalNoOfPerson", lifeProposal.getProposalInsuredPersonList().size());
			paramMap.put("totalSI", lifeProposal.getTotalSumInsured());
			paramMap.put("totalPremium", lifeProposal.getTotalPremium());
			paramMap.put("staffName", "userName");
			paramMap.put("personPremiumRate", ratePairs.entrySet());
			InputStream inputStream = new FileInputStream("report-template/life/groupLifeSanction.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/GroupLifeSanction.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void prepareTest() {
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			Map<Double, Integer> ratePairs = new HashMap<Double, Integer>();
			ratePairs.put(100.0, 1);
			paramMap.put("insuredName", "customerName");
			paramMap.put("totalNoOfPerson", 1);
			paramMap.put("totalSI", 1000.0);
			paramMap.put("totalPremium", 1000.0);
			paramMap.put("staffName", "userName");
			paramMap.put("personPremiumRate", ratePairs.entrySet());
			InputStream inputStream = new FileInputStream("report-template/life/groupLifeSanction.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/GroupLifeSanction.pdf");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
