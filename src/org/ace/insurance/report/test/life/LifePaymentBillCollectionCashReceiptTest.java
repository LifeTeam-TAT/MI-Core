//package org.ace.insurance.report.test.life;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.jasperreports.engine.JREmptyDataSource;
//import net.sf.jasperreports.engine.JRExporter;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.export.JRPdfExporter;
//import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
//
//import org.ace.insurance.life.policy.LifePolicy;
//import org.ace.insurance.life.policy.service.interfaces.ILifePolicyService;
//import org.ace.insurance.payment.Payment;
//import org.ace.insurance.payment.service.interfaces.IPaymentService;
//import org.ace.insurance.report.fire.FireProposalReport;
//import org.ace.insurance.system.common.PaymentChannel;
//import org.ace.insurance.web.manage.life.billcollection.BillCollectionDTO;
//import org.ace.insurance.web.util.FileHandler;
//import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class LifePaymentBillCollectionCashReceiptTest {
//	private static Logger logger = LogManager.getLogger(FireProposalReport.class);
//	private static IPaymentService paymentService;
//	private static ILifePolicyService lifePolicyService;
//
//	@BeforeClass
//	public static void init() {
//		logger.info("LifePaymentBillCollectionCashReceiptTest is started.........................................");
//		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
//		BeanFactory factory = context;
//		paymentService = (IPaymentService) factory.getBean("PaymentService");
//		lifePolicyService = (ILifePolicyService) factory.getBean("LifePolicyService");
//		logger.info("LifePaymentBillCollectionCashReceiptTest instance has been loaded.");
//	}
//
//	public static void main(String[] args) {
//		org.junit.runner.JUnitCore.main(LifePaymentBillCollectionCashReceiptTest.class.getName());
//	}
//
//	public List<BillCollectionDTO> populateData() {
//		List<BillCollectionDTO> result = new ArrayList<BillCollectionDTO>();
//		// Payment payment_1 =
//		// paymentService.findByPolicy("ISLIF009HO000000004701072013").get(0);
//		// LifePolicy lifePolicy_1 =
//		// lifePolicyService.findLifePolicyById("ISLIF009HO000000004701072013");
//		// Payment payment_2 =
//		// paymentService.findByPolicy("ISLIF009HO000000003227062013").get(0);
//		// LifePolicy lifePolicy_2 =
//		// lifePolicyService.findLifePolicyById("ISLIF009HO000000003227062013");
//		// Payment payment_3 =
//		// paymentService.findByPolicy("ISLIF009HO000000003127062013").get(0);
//		// LifePolicy lifePolicy_3 =
//		// lifePolicyService.findLifePolicyById("ISLIF009HO000000003127062013");
//
//		Payment payment_test = paymentService.findPaymentByReferenceNoAndIsComplete("ISLIF009HO000000103026082013", false);
//		LifePolicy lifePolicy_test = lifePolicyService.findLifePolicyById("ISLIF009HO000000103026082013");
//
//		// result.add(new BillCollectionDTO(lifePolicy_1, payment_1, 2));
//		// result.add(new BillCollectionDTO(lifePolicy_2, payment_2, 2));
//		// result.add(new BillCollectionDTO(lifePolicy_3, payment_3, 2));
//		result.add(new BillCollectionDTO(lifePolicy_test, payment_test, 1));
//		return result;
//	}
//
//	@Test
//	public void test() {
//		try {
//			List<BillCollectionDTO> billCollectionList = populateData();
//			List<JasperPrint> jpList = new ArrayList<JasperPrint>();
//			for (BillCollectionDTO line : billCollectionList) {
//				Map paramMap = new HashMap();
//				if (line.getPayment().getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
//					paramMap.put("chequeNo", line.getPayment().getChequeNo());
//					paramMap.put("bank", line.getPayment().getBank().getName());
//					paramMap.put("chequePayment", Boolean.TRUE);
//					paramMap.put("receiptType", "Temporary Receipt");
//					paramMap.put("receiptName", "Receipt No");
//				} else {
//					paramMap.put("chequePayment", Boolean.FALSE);
//					paramMap.put("receiptType", "Cash Receipt");
//					paramMap.put("receiptName", "Cash Receipt No");
//				}
//				paramMap.put("policyNo", line.getLifePolicy().getPolicyNo());
//				paramMap.put("cashReceiptNo", line.getPayment().getReceiptNo());
//				paramMap.put("sumInsured", line.getLifePolicy().getTotalSumInsured());
//				paramMap.put("paymentTimes", line.getPaymentTimes());
//				paramMap.put("basicPremium", line.getPayment().getBasicPremium());
//				paramMap.put("renewalInterest", line.getPayment().getRenewalInterest());
//				paramMap.put("loanInterest", line.getPayment().getLoanInterest());
//				paramMap.put("serviceCharges", line.getPayment().getServicesCharges());
//				paramMap.put("totalAmount", line.getTotalAmount());
//				paramMap.put("confirmDate", new Date());
//				paramMap.put("fromDate", line.getLifePolicy().getActivedPolicyStartDate());
//				paramMap.put("toDate", line.getLifePolicy().getActivedPolicyEndDate());
//				paramMap.put("agent", line.getLifePolicy().getAgent() == null ? "" : line.getLifePolicy().getAgent().getName());
//
//				if ((line.getLifePolicy().getInsuredPersonInfo() != null) && (line.getLifePolicy().getInsuredPersonInfo().size() > 0)) {
//					paramMap.put("insuredPerson", line.getLifePolicy().getInsuredPersonInfo().get(0).getFullName());
//				}
//
//				if (line.getLifePolicy().getCustomer() != null) {
//					paramMap.put("customerAddress", line.getLifePolicy().getCustomerAddress());
//				}
//
//				InputStream inputStream = new FileInputStream("report-template/life/lifePaymentBillCollectionCashReceipt.jrxml");
//				JasperReport jreport = JasperCompileManager.compileReport(inputStream);
//				JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
//				jpList.add(jprint);
//
//			}
//			JRExporter exporter = new JRPdfExporter();
//			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
//			FileHandler.forceMakeDirectory("D:/temp");
//			OutputStream outputStream = new FileOutputStream("D:/temp/billCollection.pdf");
//			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
//			exporter.exportReport();
//			outputStream.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	// @Test
//	public void prepareTest() {
//		try {
//			List<BillCollectionDTO> billCollectionList = populateData();
//			List<JasperPrint> jpList = new ArrayList<JasperPrint>();
//			Payment payment = new Payment();
//			payment.setPaymentChannel(PaymentChannel.CHEQUE);
//			Map paramMap = new HashMap();
//			if (payment.getPaymentChannel().equals(PaymentChannel.CHEQUE)) {
//				paramMap.put("chequeNo", "chequeNo");
//				paramMap.put("bank", "bank");
//				paramMap.put("chequePayment", Boolean.TRUE);
//				paramMap.put("receiptType", "Temporary Receipt");
//				paramMap.put("receiptName", "Receipt No");
//			} else {
//				paramMap.put("chequePayment", Boolean.FALSE);
//				paramMap.put("receiptType", "Cash Receipt");
//				paramMap.put("receiptName", "Cash Receipt No");
//			}
//			paramMap.put("policyNo", "policyNo");
//			paramMap.put("cashReceiptNo", "cashReceiptNo");
//			paramMap.put("sumInsured", 1000.0);
//			paramMap.put("paymentTimes", 1);
//			paramMap.put("paymentTerm", "paymentTerm");
//			paramMap.put("basicPremium", 1000.0);
//			paramMap.put("renewalInterest", 1000.0);
//			paramMap.put("loanInterest", 1000.0);
//			paramMap.put("serviceCharges", 1000.0);
//			paramMap.put("totalAmount", 1000.0);
//			paramMap.put("confirmDate", new Date());
//			paramMap.put("fromDate", new Date());
//			paramMap.put("toDate", new Date());
//			paramMap.put("agent", "agent");
//			paramMap.put("insuredPerson", "insuredPerson");
//			paramMap.put("customerAddress", "customerAddress");
//			paramMap.put("chequeNo", "chequeNo");
//			paramMap.put("bank", "bank");
//			InputStream inputStream = new FileInputStream("report-template/life/lifePaymentBillCollectionCashReceipt.jrxml");
//			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
//			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
//			jpList.add(jprint);
//			JRExporter exporter = new JRPdfExporter();
//			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jpList);
//			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/billCollection.pdf"));
//			exporter.exportReport();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
// }
