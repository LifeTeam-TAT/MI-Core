package org.ace.insurance.report.test.claimvoucher;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.payment.persistence.interfacs.IPaymentDAO;
import org.ace.insurance.report.ClaimVoucher.ClaimVoucherDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ClaimVoucherGenerationTest {
	private static Logger logger = LogManager.getLogger(ClaimVoucherGenerationTest.class);
	private static IPaymentDAO paymentDAO;

	@BeforeClass
	public static void init() {
		logger.info("CashReceiptTestTest is started.........................................");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		paymentDAO = (IPaymentDAO) factory.getBean("PaymentDAO");
		logger.info("CashReceiptTestTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(ClaimVoucherGenerationTest.class.getName());
	}

	// @Test
	// public void generateTLFVoucher() {
	// try {
	//
	// List<TLFVoucherDTO> cashReceiptDTOList =
	// paymentDAO.findTLFVoucher("CASH/1402/0000004192/001");
	// Map paramMap = new HashMap();
	// paramMap.put("TableDataSource", new
	// JRBeanCollectionDataSource(cashReceiptDTOList));
	// InputStream inputStream = new
	// FileInputStream("report-template/TLFVoucher/TLFVoucher.jrxml");
	// JasperReport jreport = JasperCompileManager.compileReport(inputStream);
	// JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new
	// JREmptyDataSource());
	// JasperExportManager.exportReportToPdfFile(jprint,
	// "D:/temp/CashReceiptTest.pdf");
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }

	// }

	@Test
	public void generateTLFVoucher() {
		try {

			List<ClaimVoucherDTO> claimVoucherDTOList = paymentDAO.findClaimVoucher("MCN/1411/0000000693/001", "Own Vehicle Damage");
			if (claimVoucherDTOList != null) {
				System.out.println(claimVoucherDTOList.size());
				Map paramMap = new HashMap();
				paramMap.put("TableDataSource", new JRBeanCollectionDataSource(claimVoucherDTOList));
				InputStream inputStream = new FileInputStream("report-template/ClaimVoucher/ClaimVoucher.jrxml");
				JasperReport jreport = JasperCompileManager.compileReport(inputStream);
				JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
				JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/ClaimVoucherTest.pdf");

			} else {
				System.out.println("nulllllllllll");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
