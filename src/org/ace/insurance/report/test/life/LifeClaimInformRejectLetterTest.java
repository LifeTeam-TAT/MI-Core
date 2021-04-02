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

public class LifeClaimInformRejectLetterTest {

	private static Logger logger = LogManager.getLogger(LifeClaimInformRejectLetterTest.class);
	private static ILifeClaimService lifeClaimService;
	private static IClaimAcceptedInfoService claimAcceptedInfoService;

	@BeforeClass
	public static void init() {
		logger.info("LifeClaimInformRejectLetterTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeClaimService = (ILifeClaimService) factory.getBean("LifeClaimService");
		claimAcceptedInfoService = (IClaimAcceptedInfoService) factory.getBean("ClaimAcceptedInfoService");
		logger.info("LifeClaimInformRejectLetterTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("LifeClaimInformRejectLetterTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifeClaimInformRejectLetterTest.class.getName());

	}

	// @Test
	public void genetateLifeClaimInformRejectLetterTest() {
		LifeClaim lifeClaim = lifeClaimService.findLifeClaimByRequestId("APLC/1401/0000000002/001");
		ClaimAcceptedInfo claimAcceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo("ISLIFC003001000000000108012014", ReferenceType.LIFE_DIS_CLAIM);
		try {
			Map paramMap = new HashMap();
			paramMap.put("customerName", lifeClaim.getClaimInsuredPerson().getFullName());
			paramMap.put("currentDate", claimAcceptedInfo.getInformDate());
			if (lifeClaim.getClaimInsuredPerson() != null) {
				paramMap.put("nrc", lifeClaim.getClaimInsuredPerson().getIdNo());
			} else {
				paramMap.put("nrc", "");
			}
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			InputStream inputStream = new FileInputStream("report-template/life/LifeClaimInformRejectLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/LifeClaimInformRejectLetterTest.pdf");
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate lifeDeathClaimRejectLetter", e);
		}
	}

	@Test
	public void prepareGenetateLifeClaimInformRejectLetterTest() {
		try {
			Map paramMap = new HashMap();
			paramMap.put("customerName", "customerName");
			paramMap.put("currentDate", new Date());
			paramMap.put("nrc", "nrc");
			paramMap.put("policyNo", "policyNo");
			InputStream inputStream = new FileInputStream("report-template/life/LifeClaimInformRejectLetter.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/LifeClaimInformRejectLetterTest.pdf");
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate lifeDeathClaimRejectLetter", e);
		}
	}
}
