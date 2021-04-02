package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.accept.service.interfaces.IAcceptedInfoService;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.Utils;
import org.ace.insurance.life.claim.service.interfaces.ILifeDisabilityClaimService;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.web.manage.life.claim.LifeClaimDischargeFormDTO;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class LifeDisabilityClaimrDischargeCertificateTest {

	private static Logger logger = LogManager.getLogger(LifeDisabilityClaimrDischargeCertificateTest.class);
	private static ILifeProposalService lifeProposalService;
	private static IAcceptedInfoService acceptedInfoService;
	private static IClaimAcceptedInfoService claimAcceptedInfoService;
	private static ILifeDisabilityClaimService lifeDisabilityClaimService;

	@BeforeClass
	public static void init() {
		logger.info("LifeDisabilityClaimrDischargeCertificateTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeProposalService = (ILifeProposalService) factory.getBean("LifeProposalService");
		acceptedInfoService = (IAcceptedInfoService) factory.getBean("AcceptedInfoService");
		lifeDisabilityClaimService = (ILifeDisabilityClaimService) factory.getBean("LifeDisabilityClaimService");
		logger.info("LifeDisabilityClaimrDischargeCertificateTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("LifeDisabilityClaimrDischargeCertificateTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifeDisabilityClaimrDischargeCertificateTest.class.getName());

	}

	@Test
	public void generateLifeDisabilityClaimDischargeCertificate() {
		LifeClaimDischargeFormDTO discharge = new LifeClaimDischargeFormDTO();
		discharge.setPolicyNo("policyNo");
		discharge.setCustomerName("customerName");
		discharge.setClaimAmount(5000000000.00);
		discharge.setSumInsured(5000000000.00);
		discharge.setPresentDate(new Date());
		discharge.setBeneficiaryName("beneficiaryName");
		discharge.setCommenmanceDate(new Date());
		discharge.setRenewelAmount(2000000000.00);
		discharge.setRenewelInterest(100000000.00);
		discharge.setLoanAmount(15000000);
		discharge.setServiceCharges(4000000.00);
		discharge.setNetClaimAmount(300000000.00);
		discharge.setIdNo("12/784552");
		discharge.setFatherName("Dad Dad");
		discharge.setAddress("address");
		try {
			List jasperPrintList = new ArrayList();
			Map paramMap = new HashMap();

			String presentDate = Utils.formattedDate(discharge.getPresentDate());
			String liabilitiesAmount = Utils.formattedCurrency(discharge.getClaimAmount());
			paramMap.put("policyNo", discharge.getPolicyNo());
			paramMap.put("customerName", discharge.getCustomerName());
			paramMap.put("liabilitiesAmount", liabilitiesAmount);
			paramMap.put("sumInsured", discharge.getSumInsured());
			paramMap.put("presentDate", presentDate);

			paramMap.put("beneficiaryName", discharge.getBeneficiaryName());
			// paramMap.put("disabilityDate",);
			paramMap.put("commencementDate", discharge.getCommenmanceDate());
			paramMap.put("premium", discharge.getRenewelAmount());
			paramMap.put("renewelInterest", discharge.getRenewelInterest());
			paramMap.put("loanAmount", discharge.getLoanAmount());
			paramMap.put("loanInterest", discharge.getLoanInterest());
			paramMap.put("serviceCharges", discharge.getServiceCharges());
			paramMap.put("netAmount", discharge.getNetClaimAmount());
			paramMap.put("witnessName", " ");
			paramMap.put("witnessAddress", " ");
			paramMap.put("nrc", discharge.getIdNo());
			paramMap.put("fatherName", discharge.getFatherName());
			paramMap.put("customerAddress", discharge.getAddress());

			InputStream inputStream = new FileInputStream("report-template/life/dischargeCertificate.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);
			jasperListPDFExport(jasperPrintList, "D:/temp/", "dischargeCertificate.pdf");
			/*
			 * FileHandler.forceMakeDirectory(dirPath); String outputFile =
			 * dirPath + fileName;
			 * JasperExportManager.exportReportToPdfFile(jprint, outputFile);
			 */
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate DischargeCertificate", e);
		}
	}

	private static void jasperListPDFExport(List jasperPrintList, String dirPath, String fileName) {
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		try {
			FileHandler.forceMakeDirectory(dirPath);
			OutputStream outputStream = new FileOutputStream(dirPath + fileName);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.exportReport();
			outputStream.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (JRException je) {
			je.printStackTrace();
		}
	}

}
