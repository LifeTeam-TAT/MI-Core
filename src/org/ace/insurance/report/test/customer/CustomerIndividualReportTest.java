package org.ace.insurance.report.test.customer;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.FamilyInfo;
import org.ace.insurance.common.Name;
import org.ace.insurance.system.common.customer.Customer;
import org.ace.insurance.system.common.customer.service.interfaces.ICustomerService;
import org.ace.insurance.system.common.relationship.RelationShip;
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

public class CustomerIndividualReportTest {
	private static Logger logger = LogManager.getLogger(CustomerIndividualReportTest.class);
	private static ICustomerService customerService;

	@BeforeClass
	public static void init() {
		logger.info("AgentComReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		customerService = (ICustomerService) factory.getBean("CustomerService");
		logger.info("AgentComReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("AgentComReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(CustomerIndividualReportTest.class.getName());
	}

	@Test
	public void report() throws Exception {
		Customer customer = customerService.findCustomerById("ISSYS0010001000000001310062013");
		InputStream inputStream = new FileInputStream("report-template/customer/customerIndividualInformationReport.jrxml");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put("totalCommission", getTotalCommission(agentComReports));
		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(customer.getFamilyInfo()));
		paramMap.put("name", customer.getFullName());
		paramMap.put("nrc", customer.getIdNo());
		paramMap.put("dob", customer.getDateOfBirth());
		paramMap.put("age", "16 Y");
		paramMap.put("qualification", customer.getQualification() == null ? "" : customer.getQualification().getName());
		paramMap.put("address", customer.getFullAddress());
		paramMap.put("mobile", customer.getContentInfo() == null ? "" : customer.getContentInfo().getMobile());
		paramMap.put("telephone", customer.getContentInfo() == null ? "" : customer.getContentInfo().getPhone());
		paramMap.put("email", customer.getContentInfo() == null ? "" : customer.getContentInfo().getEmail());
		paramMap.put("fatherName", customer.getFatherName());

		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JRBeanCollectionDataSource(customer.getFamilyInfo()));
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/CustomerIndividualInformationReport.pdf");
	}

	@Test
	public void prepareReport() throws Exception {

		InputStream inputStream = new FileInputStream("report-template/customer/customerIndividualInformationReport.jrxml");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<FamilyInfo> familyInfoList = new ArrayList<FamilyInfo>();
		FamilyInfo familyInfo = new FamilyInfo();
		Name name = new Name();
		name.setFirstName("John");
		familyInfo.setName(name);
		familyInfo.setIdNo("idNo");
		RelationShip rs = new RelationShip();
		rs.setName("friend");
		familyInfo.setRelationShip(rs);
		familyInfo.setDateOfBirth(new Date());
		familyInfoList.add(familyInfo);

		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(familyInfoList));
		paramMap.put("name", "name");
		paramMap.put("nrc", "nrc");
		paramMap.put("dob", new Date());
		paramMap.put("age", "16 Y");
		paramMap.put("qualification", "qualification");
		paramMap.put("address", "address");
		paramMap.put("mobile", "mobile");
		paramMap.put("telephone", "telephone");
		paramMap.put("email", "email");
		paramMap.put("fatherName", "fatherName");

		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/CustomerIndividualInformationReport.pdf");
	}

}
