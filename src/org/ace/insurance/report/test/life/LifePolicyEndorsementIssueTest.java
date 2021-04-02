package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Utils;
import org.ace.insurance.life.policy.EndorsementLifePolicyPrint;
import org.ace.insurance.life.policy.LifePolicy;
import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
import org.ace.insurance.life.policyHistory.LifePolicyHistory;
import org.ace.insurance.life.policyHistory.service.interfaces.ILifePolicyHistoryService;
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

public class LifePolicyEndorsementIssueTest {
	private static Logger logger = LogManager.getLogger(LifePolicyEndorsementIssueTest.class);
	private static ILifePolicyService lifePolicyService;
	private static ILifePolicyHistoryService lifePolicyHistoryService;

	@BeforeClass
	public static void init() {
		logger.info("lifePolicyTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");
		lifePolicyHistoryService = (ILifePolicyHistoryService) factory.getBean("LifePolicyHistoryService");
		logger.info("lifePolicyTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifePolicyEndorsementIssueTest.class.getName());
	}

	// @Test
	public void test() {
		try {
			LifePolicy lifePolicy = lifePolicyService.findLifePolicyById("ISLIF009001000000333109012014");
			List<EndorsementLifePolicyPrint> endorsementPolicyPrintList = lifePolicyService.findEndorsementPolicyPrintByLifePolicyNo(lifePolicy.getPolicyNo());
			List<LifePolicyHistory> list = lifePolicyHistoryService.findLifePolicyHistoryByPolicyNo(lifePolicy.getPolicyNo());
			LifePolicyHistory lifePolicyHistory = list.get(0);
			EndorsementLifePolicyPrint endorsementPolicyPrint = endorsementPolicyPrintList.get(0);
			Map paramMap = new HashMap();
			paramMap.put("proposalNo", lifePolicy.getLifeProposal().getProposalNo());
			paramMap.put("extraRegulation", endorsementPolicyPrint.getExtraRegulation());
			paramMap.put("currentDate", Utils.getDateFormatString(new Date()));
			paramMap.put("submittedDate", Utils.getDateFormatString(lifePolicy.getLifeProposal().getSubmittedDate()));
			paramMap.put("commenmanceDate", Utils.getDateFormatString(lifePolicyHistory.getCommenmanceDate()));
			paramMap.put("policyNo", lifePolicyHistory.getPolicyNo());
			paramMap.put("customerName", lifePolicyHistory.getCustomerName());
			paramMap.put("customerAddress", lifePolicyHistory.getCustomerAddress());
			paramMap.put("endorsementDescription", endorsementPolicyPrint.getEndorsementDescription());
			paramMap.put("endorseChange", "Period");
			paramMap.put("endorseChangeDetail", "Preriod From 20 years to 15 years");
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyEndorsementIssue.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			if (jprint.getPages().size() > 1) {
				jprint.removePage(1);
			}
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/Life.pdf");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void prepareTest() {
		try {
			Map paramMap = new HashMap();
			paramMap.put("proposalNo", "proposalNo");
			paramMap.put("extraRegulation", "extraRegulation");
			paramMap.put("currentDate", "currentDate");
			paramMap.put("submittedDate", "submittedDate");
			paramMap.put("commenmanceDate", "commenmanceDate");
			paramMap.put("policyNo", "policyNo");
			paramMap.put("customerName", "customerName");
			paramMap.put("customerAddress", "customerAddress");
			paramMap.put("endorsementDescription", "endorsementDescription");
			paramMap.put("endorseChange", "endorseChange");
			paramMap.put("endorseChangeDetail", "endorseChangeDetail");
			paramMap.put("propertyAddress", "propertyAddress");
			InputStream inputStream = new FileInputStream("report-template/life/lifePolicyEndorsementIssue.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			if (jprint.getPages().size() > 1) {
				jprint.removePage(1);
			}
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/Life.pdf");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
