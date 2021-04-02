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

import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.PolicyReferenceType;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.LifeClaimBeneficiary;
import org.ace.insurance.life.claim.persistence.interfaces.ILifeClaimBeneficiaryDAO;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.claim.service.interfaces.ILifeDisabilityClaimService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
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

public class LifeDisabilityClaimCashReceiptTest {

	private static Logger logger = LogManager.getLogger(LifeDisabilityClaimCashReceiptTest.class);
	private static IClaimAcceptedInfoService claimAcceptedInfoService;
	private static ILifeDisabilityClaimService lifeDisabilityClaimService;
	private static ILifeClaimService lifeClaimService;
	private static IPaymentService paymentService;
	private static LifeClaimBeneficiary lifeClaimBeneficiary;
	private static ILifeClaimBeneficiaryDAO lifeClaimBeneficiaryDAO;

	@BeforeClass
	public static void init() {
		logger.info("LifeDisabilityClaimCashReceiptTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeDisabilityClaimService = (ILifeDisabilityClaimService) factory.getBean("LifeDisabilityClaimService");
		lifeClaimService = (ILifeClaimService) factory.getBean("LifeClaimService");
		paymentService = (IPaymentService) factory.getBean("PaymentService");
		logger.info("LifeDisabilityClaimCashReceiptTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("LifeDisabilityClaimCashReceiptTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifeDisabilityClaimCashReceiptTest.class.getName());

	}

	// @Test
	public void generateLifeDisabilityClaimCashReceipt() {
		LifeClaim lifeClaim = lifeClaimService.findLifeClaimByRequestId("GGLC/1401/0000000004/001");
		LifeClaimBeneficiary lifeClaimBeneficiary = lifeClaimService.findLifeClaimBeneficaryByRefundNo("ISLIFC004001000000001102012014");
		List<Payment> paymentList = (List<Payment>) paymentService.findClaimProposal(lifeClaimBeneficiary.getId(), PolicyReferenceType.LIFE_DIS_CLAIM, false);
		PaymentDTO payment = new PaymentDTO(paymentList);
		try {
			List jasperPrintList = new ArrayList();
			Map paramMap = new HashMap();

			paramMap.put("bankAccountNo", payment.getBankAccountNo());
			paramMap.put("bank", "AGT");
			paramMap.put("chequePayment", Boolean.TRUE);
			paramMap.put("receiptType", "Cheque Payment");
			paramMap.put("receiptName", "Payment No");
			paramMap.put("claimNo", lifeClaim.getClaimRequestId());
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			paramMap.put("cashReceiptNo", payment.getReceiptNo());
			paramMap.put("currentDate", payment.getConfirmDate());
			String agentName = (lifeClaim.getLifePolicy().getAgent() == null) ? null : lifeClaim.getLifePolicy().getAgent().getFullName();
			paramMap.put("agent", agentName);
			paramMap.put("insuredPerson", lifeClaim.getLifePolicy().getCustomerName());
			paramMap.put("customerAddress", lifeClaim.getLifePolicy().getCustomerAddress());
			paramMap.put("liabilitiesAmount", payment.getClaimAmount());
			paramMap.put("serviceCharges", payment.getServicesCharges());
			paramMap.put("totalAmount", payment.getTotalClaimAmount());
			paramMap.put("loanAmount", lifeClaim.getClaimInsuredPerson().getLoanAmount());
			paramMap.put("loanInterest", payment.getLoanInterest());
			paramMap.put("renewelAmount", lifeClaim.getClaimInsuredPerson().getRenewelAmount());
			paramMap.put("renewelInterest", payment.getRenewalInterest());

			InputStream inputStream = new FileInputStream("report-template/life/lifeDisabilityClaimCashReceipt.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);
			jasperListPDFExport(jasperPrintList, "D:/temp/", "lifeDisabilityClaimCashReceipt.pdf");
			/*
			 * FileHandler.forceMakeDirectory(dirPath); String outputFile =
			 * dirPath + fileName;
			 * JasperExportManager.exportReportToPdfFile(jprint, outputFile);
			 */
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate FireCashReceipt", e);
		}
	}

	@Test
	public void prepareGenerateLifeDisabilityClaimCashReceipt() {
		try {
			List jasperPrintList = new ArrayList();
			Map paramMap = new HashMap();
			paramMap.put("bankAccountNo", "bankAccountNo");
			paramMap.put("bank", "AGT");
			paramMap.put("chequePayment", Boolean.TRUE);
			paramMap.put("receiptType", "Cheque Payment");
			paramMap.put("receiptName", "Payment No");
			paramMap.put("claimNo", "claimNo");
			paramMap.put("policyNo", "policyNo");
			paramMap.put("cashReceiptNo", "cashReceiptNo");
			paramMap.put("currentDate", new Date());
			paramMap.put("agent", "agentName");
			paramMap.put("insuredPerson", "insuredPerson");
			paramMap.put("customerAddress", "customerAddress");
			paramMap.put("liabilitiesAmount", 1000.0);
			paramMap.put("serviceCharges", 1000.0);
			paramMap.put("totalAmount", 1000.0);
			paramMap.put("loanAmount", 1000.0);
			paramMap.put("loanInterest", 1000.0);
			paramMap.put("renewelAmount", 1000.0);
			paramMap.put("renewelInterest", 1000.0);

			InputStream inputStream = new FileInputStream("report-template/life/lifeDisabilityClaimCashReceipt.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);
			jasperListPDFExport(jasperPrintList, "D:/temp/", "lifeDisabilityClaimCashReceipt.pdf");

		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate FireCashReceipt", e);
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
