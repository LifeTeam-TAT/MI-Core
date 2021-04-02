package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.claimaccept.ClaimAcceptedInfo;
import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.common.PaymentDTO;
import org.ace.insurance.common.ReferenceType;
import org.ace.insurance.life.claim.LifeClaim;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.payment.Payment;
import org.ace.insurance.payment.service.interfaces.IPaymentService;
import org.ace.insurance.system.common.PaymentChannel;
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

public class LifeDeathClaimCashReceiptTest {

	private static Logger logger = LogManager.getLogger(LifeDeathClaimCashReceiptTest.class);
	private static ILifeProposalService lifeProposalService;
	private static ILifeClaimService lifeClaimService;
	private static IClaimAcceptedInfoService claimAcceptedInfoService;
	private static IPaymentService paymentService;

	@BeforeClass
	public static void init() {
		logger.info("LifeAcceptanceLetterTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeClaimService = (ILifeClaimService) factory.getBean("LifeClaimService");
		lifeProposalService = (ILifeProposalService) factory.getBean("LifeProposalService");
		paymentService = (IPaymentService) factory.getBean("PaymentService");
		claimAcceptedInfoService = (IClaimAcceptedInfoService) factory.getBean("ClaimAcceptedInfoService");
		logger.info("LifeAcceptanceLetterTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("LifeAcceptanceLetterTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifeDeathClaimCashReceiptTest.class.getName());

	}

	// @Test
	public void generateLifeDeathClaimCashReceipt() {
		LifeClaim lifeClaim = lifeClaimService.findLifeClaimByRequestId("");
		PaymentDTO payment = getPaymentDTO();
		try {
			List jasperPrintList = new ArrayList();
			Map paramMap = new HashMap();

			if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
				paramMap.put("bankAccountNo", payment.getBankAccountNo());
				paramMap.put("bank", payment.getBank().getName());
				paramMap.put("chequePayment", Boolean.TRUE);
				paramMap.put("receiptType", "Cheque Payment");
				paramMap.put("receiptName", "Payment No");
			} else {
				paramMap.put("chequePayment", Boolean.FALSE);
				paramMap.put("receiptType", "Cash Payment");
				paramMap.put("receiptName", "Payment No");
			}
			paramMap.put("claimNo", lifeClaim.getClaimRequestId());
			paramMap.put("policyNo", lifeClaim.getLifePolicy().getPolicyNo());
			paramMap.put("cashReceiptNo", payment.getReceiptNo());
			paramMap.put("currentDate", payment.getConfirmDate());
			paramMap.put("agent", lifeClaim.getLifePolicy().getAgent().getName());
			paramMap.put("insuredPerson", lifeClaim.getLifePolicy().getCustomerName());
			paramMap.put("customerAddress", lifeClaim.getLifePolicy().getCustomerAddress());
			paramMap.put("liabilitiesAmount", payment.getClaimAmount());
			paramMap.put("serviceCharges", payment.getServicesCharges());
			paramMap.put("totalAmount", payment.getTotalClaimAmount());
			paramMap.put("loanAmount", lifeClaim.getClaimInsuredPerson().getLoanAmount());
			paramMap.put("loanInterest", payment.getLoanInterest());
			paramMap.put("renewelAmount", lifeClaim.getClaimInsuredPerson().getRenewelAmount());
			paramMap.put("renewelInterest", payment.getRenewalInterest());

			InputStream inputStream = new FileInputStream("report-template/life/lifeDeathClaimCashReceipt.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);
			jasperListPDFExport(jasperPrintList, "D:/temp/", "lifeDeathClaimCashReceipt.pdf");
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
	public void prepareGenerateLifeDeathClaimCashReceipt() {
		PaymentDTO payment = new PaymentDTO();
		payment.setPaymentChannel(PaymentChannel.CASHED);
		try {
			List jasperPrintList = new ArrayList();
			Map paramMap = new HashMap();

			if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
				paramMap.put("bankAccountNo", "bankAccountNo");
				paramMap.put("bank", "bank");
				paramMap.put("chequePayment", Boolean.TRUE);
				paramMap.put("receiptType", "Cheque Payment");
				paramMap.put("receiptName", "Payment No");
			} else {
				paramMap.put("chequePayment", Boolean.FALSE);
				paramMap.put("receiptType", "Cash Payment");
				paramMap.put("receiptName", "Payment No");
			}
			paramMap.put("claimNo", "claimNo");
			paramMap.put("policyNo", "policyNo");
			paramMap.put("cashReceiptNo", "cashReceiptNo");
			paramMap.put("currentDate", new Date());
			paramMap.put("agent", "agent");
			paramMap.put("insuredPerson", "insuredPerson");
			paramMap.put("customerAddress", "customerAddress");
			paramMap.put("liabilitiesAmount", 1000.0);
			paramMap.put("serviceCharges", 100.0);
			paramMap.put("totalAmount", 1000.0);
			paramMap.put("loanAmount", 1000.0);
			paramMap.put("loanInterest", 1000.0);
			paramMap.put("renewelAmount", 1000.0);
			paramMap.put("renewelInterest", 1000.0);

			InputStream inputStream = new FileInputStream("report-template/life/lifeDeathClaimCashReceipt.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			jasperPrintList.add(jprint);
			jasperListPDFExport(jasperPrintList, "D:/temp/", "lifeDeathClaimCashReceipt.pdf");

		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate FireCashReceipt", e);
		}
	}

	private static void jasperListPDFExport(List jasperPrintList, String dirPath, String fileName) {
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		try {
			FileHandler.forceMakeDirectory(dirPath);
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
			exporter.exportReport();
		} catch (IOException ie) {
			ie.printStackTrace();
		} catch (JRException je) {
			je.printStackTrace();
		}
	}

	public PaymentDTO getPaymentDTO() {
		List<Payment> payList = paymentService.findByPolicy("");
		PaymentDTO paymentDTO = new PaymentDTO(payList);
		ClaimAcceptedInfo acceptedInfo = claimAcceptedInfoService.findClaimAcceptedInfoByReferenceNo("", ReferenceType.ENDOWMENT_LIFE);
		paymentDTO.setServicesCharges(acceptedInfo.getServicesCharges());
		paymentDTO.setClaimAmount(acceptedInfo.getClaimAmount());
		return paymentDTO;
	}

}
