package org.ace.insurance.report.test.agent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.web.common.ntw.eng.AbstractProcessor;
import org.ace.insurance.web.common.ntw.eng.DefaultProcessor;
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

public class AgentCommissionTest {
	private static Logger logger = LogManager.getLogger(AgentCommissionTest.class);

	@BeforeClass
	public static void init() {
		logger.info("AgentComReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		logger.info("AgentComReportTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("AgentComReportTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(AgentCommissionTest.class.getName());
	}

	@Test
	public void report() throws Exception {
		InputStream inputStream = new FileInputStream("report-template/agent/AgentCommission.jrxml");
		List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		Map<String, Object> paramMap3 = new HashMap<String, Object>();
		paramMap3.put("cash", false);
		paramMap3.put("paymenttype", "Cash Payment");
		paramMap3.put("coaCodeCr", null);
		paramMap3.put("coaCodeDr", null);
		paramMap3.put("agentName", "Agent Name");
		paramMap3.put("licenseNo", "Agent Liscense No");
		paramMap3.put("invoiceNo", "Invoice No");
		paramMap3.put("coaNameDr", null);
		paramMap3.put("coaNameCr", null);
		paramMap3.put("nrc", "NRC No");
		paramMap3.put("maxdate", new Date());
		paramMap3.put("mindate", new Date());
		double totalcommission = 12589.00;
		paramMap3.put("amount", totalcommission);
		paramMap3.put("rate", 1.50);
		paramMap3.put("CUR", "USD");
		paramMap3.put("commission", 2000.00);

		AbstractProcessor processor = new DefaultProcessor();
		paramMap3.put("wordAmount", processor.getName(totalcommission));
		paramMap3.put("wordAmountUSD", processor.getName(200.00));
		JasperReport jreport = JasperCompileManager.compileReport(inputStream);
		JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap3, new JREmptyDataSource());
		jasperPrintList.add(jprint);

		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/AgentCommission.pdf"));
		exporter.exportReport();
	}

}
