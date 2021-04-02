package org.ace.insurance.report.test.customer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.report.customer.CustomerInformationCriteria;
import org.ace.insurance.report.customer.CustomerInformationReport;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class CustomerInfromationReportTest {
	private static Logger logger = LogManager.getLogger(CustomerInfromationReportTest.class);
	private static ICustomerService customerService;

	@BeforeClass
	public static void init() {
		logger.info("CustomerInfromationReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		customerService = (ICustomerService) factory.getBean("CustomerService");
		// customerInformationReportService =
		// (ICustomerInformationReportService)
		// factory.getBean("CustomerInformationReportService");
		logger.info("CustomerInfromationReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("CustomerInfromationReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(CustomerInfromationReportTest.class.getName());
	}

	@Test
	public void generateReport() {
		final String templatePath = "/report-template/customer/";
		String templateFullPath = "D:/temp/";
		String outputFilePdf = "D:/temp/customerInformationReport.pdf";
		CustomerInformationCriteria criteria = new CustomerInformationCriteria();
		criteria.setEndDate(new Date());
		// List<CustomerInformationReport> reportList =
		// customerInformationReportService.findCustomerInformation(criteria);

		// Create the DataSource instance with the given list which
		// in turn to be filled up in the report
		try {
			InputStream inputStream = new FileInputStream("report-template/customer/customerInformationReport.jrxml");
			// JRBeanCollectionDataSource ds = new
			// JRBeanCollectionDataSource(reportList);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			// paramMap.put("TableDataSource", ds);

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/customerInformationReport.pdf");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Test
	public void preparegenerateReport() {
		final String templatePath = "/report-template/customer/";
		String templateFullPath = "D:/temp/";
		String outputFilePdf = "D:/temp/customerInformationReport.pdf";

		List<CustomerInformationReport> reportList = new ArrayList<CustomerInformationReport>();
		CustomerInformationReport customerInformationReport = new CustomerInformationReport();
		customerInformationReport.setCustomerName("customerName");
		customerInformationReport.setGender("gender");
		customerInformationReport.setNrc("nrc");
		customerInformationReport.setAge("age");
		customerInformationReport.setQualificaiton("qualificaiton");
		customerInformationReport.setAddress("address");
		customerInformationReport.setEmail("email");
		customerInformationReport.setMobile("mobile");
		customerInformationReport.setPhoneNo("phoneNo");
		customerInformationReport.setDob(new Date());
		reportList.add(customerInformationReport);

		try {
			InputStream inputStream = new FileInputStream("report-template/customer/customerInformationReport.jrxml");
			JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(reportList);

			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("TableDataSource", ds);

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/customerInformationReport.pdf");
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
