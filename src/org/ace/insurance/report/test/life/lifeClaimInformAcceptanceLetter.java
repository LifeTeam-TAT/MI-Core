package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.web.util.FileHandler;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class lifeClaimInformAcceptanceLetter {

	private static Logger logger = LogManager.getLogger(lifeClaimInformAcceptanceLetter.class);
	private static ILifeProposalService lifeProposalService;
	private static ILifeClaimService lifeClaimService;
	private static IClaimAcceptedInfoService claimAcceptedInfoService;

	@BeforeClass
	public static void init() {
		logger.info("LifeAcceptanceLetterTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeClaimService = (ILifeClaimService) factory.getBean("LifeClaimService");
		lifeProposalService = (ILifeProposalService) factory.getBean("LifeProposalService");
		claimAcceptedInfoService = (IClaimAcceptedInfoService) factory.getBean("ClaimAcceptedInfoService");
		logger.info("LifeAcceptanceLetterTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("LifeAcceptanceLetterTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(lifeClaimInformAcceptanceLetter.class.getName());

	}

	// @Test
	public void generateLifeDeathClaimAcceptanceLetter() {
		LifeClaim lifeClaim = lifeClaimService.findLifeClaimByRequestId("GGLC/1312/0000000001/001");
		ClaimAcceptedInfo claimAcceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo("referenceNo", ReferenceType.GROUP_LIFE);
		try {
			Map paramMap = new HashMap();
			paramMap.put("customerName", lifeClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("customerAddress", lifeClaim.getClaimInsuredPerson().getFullResidentAddress());
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			paramMap.put("agent", lifeClaim.getLifePolicy().getAgent().getName());
			paramMap.put("claimNo", lifeClaim.getClaimRequestId());
			paramMap.put("currentDate", claimAcceptedInfo.getInformDate());
			paramMap.put("liabilitiesAmount", claimAcceptedInfo.getClaimAmount());
			paramMap.put("loanAmount", lifeClaim.getTotalLoanAmount());
			paramMap.put("loanInterest", lifeClaim.getTotalLoanInterest());
			paramMap.put("renewalAmount", lifeClaim.getTotalRenewelAmount());
			paramMap.put("renewalInterest", lifeClaim.getTotalRenewelInterest());
			// paramMap.put("totalAmount", lifeClaim.getTotalNetClaimAmount());
			paramMap.put("serviceCharges", claimAcceptedInfo.getServicesCharges());
			paramMap.put("totalAmount", claimAcceptedInfo.getTotalAmount());
			paramMap.put("publicLife", isPublicLife(lifeClaim));

			InputStream inputStream = new FileInputStream("report-template/life/lifeClaimInformAcceptanceLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory("D:/temp/");
			String outputFile = "D:/temp/lifeClaimInformAcceptanceLetter.pdf";
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeClaimAcceptanceLetter", e);
		}
	}

	@Test
	public void preparegenerateLifeDeathClaimAcceptanceLetter() {
		try {
			Map paramMap = new HashMap();
			paramMap.put("customerName", "customerName");
			paramMap.put("customerAddress", "customerAddress");
			paramMap.put("policyNo", "policyNo");
			paramMap.put("agent", "agent");
			paramMap.put("claimNo", "claimNo");
			paramMap.put("currentDate", new Date());
			paramMap.put("liabilitiesAmount", 1000.0);
			paramMap.put("loanAmount", 1000.0);
			paramMap.put("loanInterest", 1000.0);
			paramMap.put("renewalAmount", 1000.0);
			paramMap.put("renewalInterest", 100.0);
			paramMap.put("serviceCharges", 100.0);
			paramMap.put("totalAmount", 5000.0);
			paramMap.put("publicLife", true);

			InputStream inputStream = new FileInputStream("report-template/life/lifeClaimInformAcceptanceLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory("D:/temp/");
			String outputFile = "D:/temp/lifeClaimInformAcceptanceLetter.pdf";
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeClaimAcceptanceLetter", e);
		}
	}

	private static boolean isPublicLife(LifeClaim lifeClaim) {
		if (lifeClaim.getLifePolicy().getPolicyInsuredPersonList().size() == 1) {
			return true;
		}
		return false;
	}

}
