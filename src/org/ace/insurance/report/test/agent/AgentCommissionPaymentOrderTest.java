package org.ace.insurance.report.test.agent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.Name;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.system.common.agent.Agent;
import org.ace.insurance.system.common.agent.service.interfaces.IAgentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class AgentCommissionPaymentOrderTest {
	private static Logger logger = LogManager.getLogger(AgentCommissionPaymentOrderTest.class);
	private static IAgentService agentService;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void init() {
		logger.info("AgentComReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		agentService = (IAgentService) factory.getBean("AgentService");
		logger.info("AgentComReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("AgentCommissionPaymentOrderTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(AgentCommissionPaymentOrderTest.class.getName());
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Test
	public void report() throws Exception {
		Agent agent = agentService.findAgentById("ISSYS0020001000000001508062013");
		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		Double totalcomission = 1.20;
		// AbstractMynNumConvertor convertor = new DefaultConvertor();
		// String myanmarTotalAmmoumt = convertor.getName(totalcomission);
		Map paramMap = new HashMap();
		paramMap.put("currentDate", new Date());
		if (agent.getName() == null) {
			paramMap.put("agentName", "");
		} else {
			paramMap.put("agentName", agent.getName());
		}
		if (agent.getIdNo() == null) {
			paramMap.put("idNo", "");
		} else {
			paramMap.put("idNo", agent.getIdNo());
		}
		if (agent.getFullAddress() == null) {
			paramMap.put("address", "");
		} else {
			paramMap.put("address", agent.getFullAddress());
		}
		if (totalcomission == null) {
			paramMap.put("totalcommission", "");
		} else {
			paramMap.put("totalcommission", totalcomission);
		}
		if (agent.getName() == null) {
			paramMap.put("agentCode", "");
		} else {
			paramMap.put("agentCode", agent.getLiscenseNo());
		}
		if (agent.getIdNo() == null) {
			paramMap.put("licenseNo", "");
		} else {
			paramMap.put("licenseNo", agent.getLiscenseNo());
		}
		paramMap.put("myanmarTotalAmmoumt", "myanmarTotalAmmoumt");
		InputStream inputStream = new FileInputStream("report-template/agent/AgentCommissionPaymentOrder.jrxml");
		JasperReport jreport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
		jasperPrintList.add(jprint);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/AgentCommissionPaymentOrder.pdf"));
		exporter.exportReport();

	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	@Test
	public void prepareReport() throws Exception {
		Agent agent = new Agent();
		Name name = new Name();
		name.setFirstName("John Htet");
		agent.setLiscenseNo("A-1074");
		agent.setIdNo("14/PaThaNa(N)146909");
		org.ace.insurance.common.ResidentAddress residentAddress = new ResidentAddress();
		residentAddress.setResidentAddress("no(12),building no(302)");
		agent.setResidentAddress(residentAddress);
		agent.setName(name);

		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		Double totalcomission = 1.20;
		Map paramMap = new HashMap();
		paramMap.put("currentDate", new Date());
		if (agent.getName() == null) {
			paramMap.put("agentName", "");
		} else {
			paramMap.put("agentName", agent.getName());
		}
		if (agent.getIdNo() == null) {
			paramMap.put("idNo", "");
		} else {
			paramMap.put("idNo", agent.getIdNo());
		}
		if (agent.getFullAddress() == null) {
			paramMap.put("address", "");
		} else {
			paramMap.put("address", agent.getFullAddress());
		}
		if (totalcomission == null) {
			paramMap.put("totalcommission", "");
		} else {
			paramMap.put("totalcommission", totalcomission);
		}
		if (agent.getName() == null) {
			paramMap.put("agentCode", "");
		} else {
			paramMap.put("agentCode", agent.getLiscenseNo());
		}
		if (agent.getIdNo() == null) {
			paramMap.put("licenseNo", "");
		} else {
			paramMap.put("licenseNo", agent.getLiscenseNo());
		}
		paramMap.put("myanmarTotalAmmoumt", "myanmarTotalAmmoumt");
		InputStream inputStream = new FileInputStream("report-template/agent/AgentCommissionPaymentOrder.jrxml");
		JasperReport jreport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
		jasperPrintList.add(jprint);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/AgentCommissionPaymentOrder.pdf"));
		exporter.exportReport();

	}

}
