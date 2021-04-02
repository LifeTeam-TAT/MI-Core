package org.ace.insurance.report.test.agent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ace.insurance.common.ContentInfo;
import org.ace.insurance.common.Name;
import org.ace.insurance.common.ProductGroupType;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.report.agent.persistence.interfaces.IAgentSaleReportDAO;
import org.ace.insurance.report.agent.service.interfaces.IAgentSaleReportService;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.ace.insurance.system.common.qualification.Qualification;
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

public class AgentInformationDetailReportTest {
	private static Logger logger = LogManager.getLogger(AgentInformationDetailReportTest.class);
	private static IAgentService agentService;
	private static IAgentSaleReportService agentSaleReportService;
	private static IAgentSaleReportDAO agentSaleReportDAO;

	@BeforeClass
	public static void init() {
		logger.info("AgentComReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		agentService = (IAgentService) factory.getBean("AgentService");
		agentSaleReportService = (IAgentSaleReportService) factory.getBean("AgentSaleReportService");
		agentSaleReportDAO = (IAgentSaleReportDAO) factory.getBean("AgentSaleReportDAO");
		logger.info("AgentComReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("AgentComReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(AgentInformationDetailReportTest.class.getName());
	}

	@Test
	public void report() throws Exception {
		Agent agent = new Agent();
		Name name = new Name();
		name.setFirstName("John Htet");
		agent.setLiscenseNo("A-1074");
		agent.setIdNo("14/PaThaNa(N)146909");
		ResidentAddress residentAddress = new ResidentAddress();
		residentAddress.setResidentAddress("no(12),building no(302)");
		agent.setResidentAddress(residentAddress);
		agent.setDateOfBirth(new Date());
		agent.setCodeNo("codeNo001");
		agent.setAppointedDate(new Date());
		agent.setTraining("John Training");
		agent.setLiscenseNo("liscenseNo001");
		agent.setResidentAddress(residentAddress);
		agent.setName(name);

		Qualification qualification = new Qualification();
		qualification.setName("BA(English)");
		agent.setQualification(qualification);
		ContentInfo agentContentInfo = new ContentInfo();
		agentContentInfo.setEmail("Agent@gmail.com");
		agentContentInfo.setFax("fax1132");
		agentContentInfo.setMobile("092549876");
		agentContentInfo.setPhone("0225487");
		agent.setContentInfo(agentContentInfo);
		agent.setGroupType(ProductGroupType.LIFE);
		agent.setOutstandingEvent("outstandingEvent");

		InputStream inputStream = new FileInputStream("report-template/agent/agentInformationDetailReport.jrxml");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String outputFilePdf = "D:/temp/agentInformationDetailReport.pdf";
		paramMap.put("name", agent.getName());
		paramMap.put("nrc", agent.getIdNo());
		paramMap.put("dob", agent.getDateOfBirth());
		paramMap.put("codeNo", agent.getCodeNo());
		paramMap.put("service", "-");
		paramMap.put("appDate", agent.getAppointedDate());
		paramMap.put("age", "16 Y");
		paramMap.put("training", agent.getTraining() == null ? "" : agent.getTraining());
		paramMap.put("qualification", agent.getQualification() == null ? "" : agent.getQualification().getName());
		paramMap.put("address", agent.getFullAddress());
		paramMap.put("mobile", agent.getContentInfo() == null ? "" : agent.getContentInfo().getMobile());
		paramMap.put("telephone", agent.getContentInfo() == null ? "" : agent.getContentInfo().getPhone());
		paramMap.put("email", agent.getContentInfo() == null ? "" : agent.getContentInfo().getEmail());
		paramMap.put("outstand", agent.getOutstandingEvent() == null ? "" : agent.getOutstandingEvent());
		paramMap.put("licenseNo", agent.getLiscenseNo());
		paramMap.put("typeOfAgent", agent.getGroupType() == null ? "-" : agent.getGroupType().toString());
		paramMap.put("outstand", agent.getOutstandingEvent() == null ? "-" : agent.getOutstandingEvent());
		paramMap.put("organization", agent.getOrganization() == null ? "-" : agent.getOrganization());

		JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
		JasperExportManager.exportReportToPdfFile(jasperPrint, outputFilePdf);
	}
}
