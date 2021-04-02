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
import org.ace.insurance.life.claim.LifeClaim;
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

public class LifeDisabilityClaimInformAcceptanceLetterTest {

	private static Logger logger = LogManager.getLogger(LifeDisabilityClaimInformAcceptanceLetterTest.class);
	private static ILifeProposalService lifeProposalService;

	private static IAcceptedInfoService acceptedInfoService;
	private static IClaimAcceptedInfoService claimAcceptedInfoService;
	private static ILifeDisabilityClaimService lifeDisabilityClaimService;
	private static String dirPath = "D:/temp/";
	private static String fileName = "lifeDisabilityClaimInformAcceptanceLetter.pdf";

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
		org.junit.runner.JUnitCore.main(LifeDisabilityClaimInformAcceptanceLetterTest.class.getName());

	}

	// @Test
	public static void generateLifeDisabilityClaimAcceptanceLetter() {
		LifeDisabilityClaim disabilityClaim = lifeDisabilityClaimService.findDisabilityClaimByRequestId("Requestid");
		ClaimAcceptedInfo claimAcceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo("referenceNo", ReferenceType.LIFE_DIS_CLAIM);
		try {

			Map paramMap = new HashMap();
			paramMap.put("customerName", disabilityClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("customerAddress", disabilityClaim.getClaimInsuredPerson().getFullResidentAddress());
			paramMap.put("policyNo", disabilityClaim.getLifePolicy().getPolicyNo());
			paramMap.put("agent", disabilityClaim.getLifePolicy().getAgent().getName());
			paramMap.put("claimNo", disabilityClaim.getClaimRequestId());
			paramMap.put("currentDate", claimAcceptedInfo.getInformDate());
			paramMap.put("liabilitiesAmount", claimAcceptedInfo.getClaimAmount());
			paramMap.put("loanAmount", disabilityClaim.getTotalLoanAmount());
			paramMap.put("loanInterest", disabilityClaim.getTotalLoanInterest());
			paramMap.put("renewalAmount", disabilityClaim.getTotalRenewelAmount());
			paramMap.put("renewalInterest", disabilityClaim.getTotalRenewelInterest());
			// paramMap.put("totalAmount", lifeClaim.getTotalNetClaimAmount());
			paramMap.put("serviceCharges", claimAcceptedInfo.getServicesCharges());
			paramMap.put("totalAmount", claimAcceptedInfo.getTotalAmount());
			paramMap.put("waitingPeriod", disabilityClaim.getWaitingPeriod());
			// paramMap.put("disabilityClaimType",disabilityClaim.getDisabilityClaimType());
			paramMap.put("publicLife", isPublicLife(disabilityClaim));

			InputStream inputStream = new FileInputStream("report-template/life/lifeDisabilityClaimInformAcceptanceLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeDisabilityClaimAcceptanceLetter", e);
		}
	}

	@Test
	public void prepaeGenerateLifeDisabilityClaimAcceptanceLetter() {

		try {

			Map paramMap = new HashMap();
			paramMap.put("customerName", "customerName");
			paramMap.put("customerAddress", "customerAddress");
			paramMap.put("policyNo", "policyNo");
			paramMap.put("agent", "agent");
			paramMap.put("claimNo", "claimNo");
			paramMap.put("currentDate", "currentDate");
			paramMap.put("liabilitiesAmount", 1000.0);
			paramMap.put("loanAmount", 1000.0);
			paramMap.put("loanInterest", 1000.0);
			paramMap.put("renewalAmount", 1000.0);
			paramMap.put("renewalInterest", 1000.0);
			paramMap.put("serviceCharges", 100.0);
			paramMap.put("totalAmount", 1000.0);
			paramMap.put("waitingPeriod", 1);
			paramMap.put("publicLife", true);

			InputStream inputStream = new FileInputStream("report-template/life/lifeDisabilityClaimInformAcceptanceLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			FileHandler.forceMakeDirectory(dirPath);
			String outputFile = dirPath + fileName;
			JasperExportManager.exportReportToPdfFile(jprint, outputFile);
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate LifeDisabilityClaimAcceptanceLetter", e);
		}
	}

	private static boolean isPublicLife(LifeClaim lifeClaim) {
		if (lifeClaim.getLifePolicy().getPolicyInsuredPersonList().size() == 1) {
			return true;
		}
		return false;
	}
}
