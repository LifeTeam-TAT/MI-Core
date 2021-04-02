package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.life.claim.LifeDisabilityClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeDisabilityClaimService;
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

public class LifeDisabilityClaimRejectLetterTest {

	private static Logger logger = LogManager.getLogger(LifeDisabilityClaimRejectLetterTest.class);
	private static ILifeProposalService lifeProposalService;
	private static IAcceptedInfoService acceptedInfoService;
	private static IClaimAcceptedInfoService claimAcceptedInfoService;
	private static ILifeDisabilityClaimService lifeDisabilityClaimService;
	private static String dirPath = "D:/temp/";
	private static String fileName = "LifeDisabilityClaimInformRejectLetter.pdf";

	@BeforeClass
	public static void init() {
		logger.info("CommonInformRejectLetterTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeProposalService = (ILifeProposalService) factory.getBean("LifeProposalService");
		acceptedInfoService = (IAcceptedInfoService) factory.getBean("AcceptedInfoService");

		lifeDisabilityClaimService = (ILifeDisabilityClaimService) factory.getBean("LifeDisabilityClaimService");
		logger.info("CommonInformRejectLetterTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("CommonInformRejectLetterTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifeDisabilityClaimRejectLetterTest.class.getName());

	}

	// @Test
	public static void generateLifeDisabilityClaimRejectLetter() {
		LifeDisabilityClaim disabilityClaim = lifeDisabilityClaimService.findDisabilityClaimByRequestId("GGLC/1401/0000000004/001");
		ClaimAcceptedInfo claimAcceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo("", ReferenceType.LIFE_DIS_CLAIM);
		try {
			Map paramMap = new HashMap();
			paramMap.put("customerName", disabilityClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("currentDate", claimAcceptedInfo.getInformDate());
			if (disabilityClaim.getClaimInsuredPerson() != null) {
				paramMap.put("nrc", disabilityClaim.getClaimInsuredPerson().getIdNo());
			} else {
				paramMap.put("nrc", "");
			}
			paramMap.put("policyNo", disabilityClaim.getLifePolicy().getPolicyNo());
			InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/life/LifeDisabilityClaimInformRejectLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate lifeDisabilityClaimRejectLetter", e);
		}
	}

	@Test
	public void prepareGenerateLifeDisabilityClaimRejectLetter() {
		try {
			Map paramMap = new HashMap();
			paramMap.put("customerName", "customerName");
			paramMap.put("currentDate", "currentDate");
			paramMap.put("nrc", "nrc");
			paramMap.put("policyNo", "policyNo");
			InputStream inputStream = new FileInputStream("report-template/life/LifeDisabilityClaimInformRejectLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate lifeDisabilityClaimRejectLetter", e);
		}
	}

}
