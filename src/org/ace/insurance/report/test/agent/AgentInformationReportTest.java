package org.ace.insurance.report.test.agent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Utils;
import org.ace.insurance.report.agent.AgentInformationCriteria;
import org.ace.insurance.report.agent.AgentInformationReport;
import org.ace.insurance.report.agent.service.interfaces.IAgentInformationReportService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class AgentInformationReportTest {
	private static Logger logger = LogManager.getLogger(AgentInformationReportTest.class);
	private static IAgentInformationReportService agentInformationReportService;

	@BeforeClass
	public static void init() {
		logger.info("ReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		agentInformationReportService = (IAgentInformationReportService) factory.getBean("AgentInformationReportService");
		logger.info("ReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("ReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(AgentInformationReportTest.class.getName());
	}

	@Test
	public void report() throws Exception {
		AgentInformationCriteria criteria = new AgentInformationCriteria();
		criteria.setStartDate(Utils.getDate("01-06-2013"));
		criteria.setEndDate(Utils.getDate("15-07-2013"));
		List<AgentInformationReport> reportList = agentInformationReportService.findAgentInformation(criteria);
		InputStream inputStream = new FileInputStream("report-template/agent/agentInformationReport.jrxml");
		Map paramMap = new HashMap();
		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(reportList));
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JRBeanCollectionDataSource(reportList));
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/agentInformationReport.pdf");
	}

	@Test
	public void prepareReport() throws Exception {
		List<AgentInformationReport> reportList = new ArrayList<AgentInformationReport>();

		AgentInformationReport agentInformationReport = new AgentInformationReport();
		agentInformationReport.setAgentCode("agentCode");
		agentInformationReport.setAgentName("agentName");
		agentInformationReport.setGender("gender");
		agentInformationReport.setNrc("nrc");
		agentInformationReport.setAppDate(new Date());
		agentInformationReport.setService("service");
		agentInformationReport.setAge("age");
		agentInformationReport.setDob(new Date());
		agentInformationReport.setQualificaiton("qualificaiton");
		agentInformationReport.setTraining("training");
		agentInformationReport.setAddress("address");
		agentInformationReport.setEmail("email");
		agentInformationReport.setMobile("mobile");
		agentInformationReport.setPhoneNo("phoneNo");
		agentInformationReport.setRemark("remark");
		agentInformationReport.setFilePath("filePath");
		agentInformationReport.setLiscenseNo("liscenseNo");
		agentInformationReport.setOutstandingEvent("outstandingEvent");
		agentInformationReport.setGroupType("groupType");
		agentInformationReport.setOrganization("organization");
		reportList.add(agentInformationReport);

		InputStream inputStream = new FileInputStream("report-template/agent/agentInformationReport.jrxml");
		Map paramMap = new HashMap();
		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(reportList));
		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JRBeanCollectionDataSource(reportList));
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/agentInformationReport.pdf");
	}

}
