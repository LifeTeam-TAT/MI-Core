package org.ace.insurance.report.test.agent;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.report.agent.AgentCommissionDetailCriteria;
import org.ace.insurance.report.agent.AgentCommissionDetailReport;
import org.ace.insurance.report.agent.service.interfaces.IAgentCommissionDetailService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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

public class AgentComReportTest {
	private static Logger logger = LogManager.getLogger(AgentComReportTest.class);
	private static IAgentCommissionDetailService agentComReportService;

	@BeforeClass
	public static void init() {
		logger.info("AgentComReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		agentComReportService = (IAgentCommissionDetailService) factory.getBean("AgentCommissionDetailReportService");
		logger.info("AgentComReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("AgentComReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(AgentComReportTest.class.getName());
	}

	// @Test
	public void report() throws Exception {
		List<AgentCommissionDetailReport> agentComReports = agentComReportService.findAgentCommissionDetail(new AgentCommissionDetailCriteria());
		System.out.println("----------------------------------------> " + agentComReports.size());
		System.out.println("------------------------->" + agentComReports.get(263).getAddress());
		InputStream inputStream = new FileInputStream("report-template/agent/agentComReport.jrxml");
		Map paramMap = new HashMap();
		paramMap.put("totalCommission", getTotalCommission(agentComReports));
		paramMap.put("TableDataSource", new JRBeanCollectionDataSource(agentComReports));

		JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JRBeanCollectionDataSource(agentComReports));
		JasperExportManager.exportReportToPdfFile(jasperPrint, "D:/temp/AgentCommessionReport.pdf");
	}

	private double getTotalCommission(List<AgentCommissionDetailReport> agentCommissionList) {
		double totalComm = 0.0;
		for (AgentCommissionDetailReport a : agentCommissionList) {
			totalComm += a.getCommission();
		}
		return totalComm;
	}
}
