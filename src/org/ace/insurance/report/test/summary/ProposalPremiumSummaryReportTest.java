package org.ace.insurance.report.test.summary;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.common.ErrorCode;
import org.ace.insurance.report.common.ProposalPremiumSummaryReport;
import org.ace.insurance.report.common.ProposalPremiumSummaryReport.ProductPremiumInfo;
import org.ace.insurance.report.common.SummaryReportCriteria;
import org.ace.insurance.report.common.service.interfaces.IProposalPremiumSummaryReportService;
import org.ace.java.component.SystemException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class ProposalPremiumSummaryReportTest {
	private static Logger logger = LogManager.getLogger(ProposalPremiumSummaryReportTest.class);
	private static IProposalPremiumSummaryReportService premiumSummaryReportService;

	@BeforeClass
	public static void init() {
		logger.info("ProposalPremiumSummaryReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		premiumSummaryReportService = (IProposalPremiumSummaryReportService) factory.getBean("ProposalPremiumSummaryReportService");
		logger.info("ProposalPremiumSummaryReportTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(ProposalPremiumSummaryReportTest.class.getName());
	}

	// @Test
	public void generateProposalSummaryReport() {
		try {
			List<ProposalPremiumSummaryReport> proposalSummaryReports = new ArrayList<ProposalPremiumSummaryReport>();
			// SummaryReportCriteria criteria = new SummaryReportCriteria();
			// criteria.setYear(2014);
			// criteria.setMonth(MonthType.JAN);
			// criteria.setReportType("Monthly Report");
			// proposalSummaryReports =
			// premiumSummaryReportService.findProposalPremiumSummaryReport(criteria);

			Map<String, Object> params = new HashMap<String, Object>();
			// params.put("Criteria", criteria);
			params.put("ProposalPremiumSummaryReports", new JRBeanCollectionDataSource(proposalSummaryReports));
			InputStream inputStream = new FileInputStream("report-template/summary/proposalPremiumSummaryReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, params, new JRBeanCollectionDataSource(proposalSummaryReports));
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/ProposalPremiumSummaryReports.pdf");
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate ProposalPremiumSummary Report", e);
		}
	}

	@Test
	public void prepareGenerateProposalPremiumSummaryReport() {
		try {
			List<ProposalPremiumSummaryReport> proposalSummaryReports = new ArrayList<ProposalPremiumSummaryReport>();

			ProposalPremiumSummaryReport report = new ProposalPremiumSummaryReport();
			report.setName("Fire Insurance");
			report.setProductPremiumInfoList(getFireProductPremiumInfoList());

			ProposalPremiumSummaryReport report1 = new ProposalPremiumSummaryReport();
			report1.setName("Life Insurance");
			report1.setProductPremiumInfoList(getLifeProductPremiumInfoList());

			ProposalPremiumSummaryReport report2 = new ProposalPremiumSummaryReport();
			report2.setName("Motor Insurance");
			report2.setProductPremiumInfoList(getMotorProductPremiumInfoList());

			proposalSummaryReports.add(report);
			proposalSummaryReports.add(report1);
			proposalSummaryReports.add(report2);
			Map<String, Object> params = new HashMap<String, Object>();
			SummaryReportCriteria criteria = new SummaryReportCriteria();
			criteria.setYear(2014);
			criteria.setStartDate(new Date());
			criteria.setEndDate(new Date());
			criteria.setMonth(0);
			criteria.setReportType("Monthly Report");
			params.put("Criteria", criteria);
			params.put("ProposalPremiumSummaryReports", new JRBeanCollectionDataSource(proposalSummaryReports));
			params.put("ReportTitle", "ReportTitle");
			params.put("ReportDate", new Date());
			InputStream inputStream = new FileInputStream("report-template/summary/proposalPremiumSummaryReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, params, new JRBeanCollectionDataSource(proposalSummaryReports));
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/ProposalPremiumSummaryReports.pdf");
		} catch (Exception e) {
			throw new SystemException(ErrorCode.SYSTEM_ERROR, "Failed to generate ProposalPremiumSummary Report", e);
		}
	}

	public List<ProductPremiumInfo> getLifeProductPremiumInfoList() {
		List<ProductPremiumInfo> lifeProductPremiumInfoList = new ArrayList<ProductPremiumInfo>();
		ProposalPremiumSummaryReport p = new ProposalPremiumSummaryReport();

		ProductPremiumInfo premiumInfo = p.new ProductPremiumInfo();
		premiumInfo.setAgentTotalPremium(1000.0);
		premiumInfo.setProductName("SPORTMAN");
		premiumInfo.setSalemanTotalPremium(1000.0);

		ProductPremiumInfo premiumInfo1 = p.new ProductPremiumInfo();
		premiumInfo1.setAgentTotalPremium(2000.0);
		premiumInfo1.setProductName("GROUP LIFE");
		premiumInfo1.setSalemanTotalPremium(2000.0);

		ProductPremiumInfo premiumInfo2 = p.new ProductPremiumInfo();
		premiumInfo2.setAgentTotalPremium(3000.0);
		premiumInfo2.setProductName("SNAKE BITE");
		premiumInfo2.setSalemanTotalPremium(3000.0);

		ProductPremiumInfo premiumInfo3 = p.new ProductPremiumInfo();
		premiumInfo3.setAgentTotalPremium(4000.0);
		premiumInfo3.setProductName("PUBLIC LIFE");
		premiumInfo3.setSalemanTotalPremium(4000.0);

		lifeProductPremiumInfoList.add(premiumInfo);
		lifeProductPremiumInfoList.add(premiumInfo1);
		lifeProductPremiumInfoList.add(premiumInfo2);
		lifeProductPremiumInfoList.add(premiumInfo3);
		return lifeProductPremiumInfoList;
	}

	public List<ProductPremiumInfo> getFireProductPremiumInfoList() {
		List<ProductPremiumInfo> fireProductPremiumInfoList = new ArrayList<ProductPremiumInfo>();
		ProposalPremiumSummaryReport p = new ProposalPremiumSummaryReport();

		ProductPremiumInfo premiumInfo = p.new ProductPremiumInfo();
		premiumInfo.setAgentTotalPremium(1000.0);
		premiumInfo.setProductName("FURNITURE");
		premiumInfo.setSalemanTotalPremium(1000.0);

		ProductPremiumInfo premiumInfo1 = p.new ProductPremiumInfo();
		premiumInfo1.setAgentTotalPremium(2000.0);
		premiumInfo1.setProductName("BUILDING");
		premiumInfo1.setSalemanTotalPremium(2000.0);

		ProductPremiumInfo premiumInfo2 = p.new ProductPremiumInfo();
		premiumInfo2.setAgentTotalPremium(3000.0);
		premiumInfo2.setProductName("MACHINERY");
		premiumInfo2.setSalemanTotalPremium(3000.0);

		ProductPremiumInfo premiumInfo3 = p.new ProductPremiumInfo();
		premiumInfo3.setAgentTotalPremium(4000.0);
		premiumInfo3.setProductName("DECLARATION POLICY");
		premiumInfo3.setSalemanTotalPremium(4000.0);

		ProductPremiumInfo premiumInfo4 = p.new ProductPremiumInfo();
		premiumInfo4.setAgentTotalPremium(5000.0);
		premiumInfo4.setProductName("STOCK OF GOODS");
		premiumInfo4.setSalemanTotalPremium(5000.0);

		fireProductPremiumInfoList.add(premiumInfo);
		fireProductPremiumInfoList.add(premiumInfo1);
		fireProductPremiumInfoList.add(premiumInfo2);
		fireProductPremiumInfoList.add(premiumInfo3);
		fireProductPremiumInfoList.add(premiumInfo4);
		return fireProductPremiumInfoList;
	}

	public List<ProductPremiumInfo> getMotorProductPremiumInfoList() {
		List<ProductPremiumInfo> motorProductPremiumInfoList = new ArrayList<ProductPremiumInfo>();
		ProposalPremiumSummaryReport p = new ProposalPremiumSummaryReport();

		ProductPremiumInfo premiumInfo = p.new ProductPremiumInfo();
		premiumInfo.setAgentTotalPremium(1000.0);
		premiumInfo.setProductName("COMMERCIAL CAR");
		premiumInfo.setSalemanTotalPremium(1000.0);

		ProductPremiumInfo premiumInfo1 = p.new ProductPremiumInfo();
		premiumInfo1.setAgentTotalPremium(2000.0);
		premiumInfo1.setProductName("PRIVATE CAR");
		premiumInfo1.setSalemanTotalPremium(2000.0);

		ProductPremiumInfo premiumInfo2 = p.new ProductPremiumInfo();
		premiumInfo2.setAgentTotalPremium(3000.0);
		premiumInfo2.setProductName("FA TRUCK");
		premiumInfo2.setSalemanTotalPremium(3000.0);

		ProductPremiumInfo premiumInfo3 = p.new ProductPremiumInfo();
		premiumInfo3.setAgentTotalPremium(4000.0);
		premiumInfo3.setProductName("COMMERCIAL TRUCK");
		premiumInfo3.setSalemanTotalPremium(4000.0);

		ProductPremiumInfo premiumInfo4 = p.new ProductPremiumInfo();
		premiumInfo4.setAgentTotalPremium(5000.0);
		premiumInfo4.setProductName("FA CAR");
		premiumInfo4.setSalemanTotalPremium(5000.0);

		ProductPremiumInfo premiumInfo5 = p.new ProductPremiumInfo();
		premiumInfo5.setAgentTotalPremium(6000.0);
		premiumInfo5.setProductName("MOTOR CYCLE");
		premiumInfo5.setSalemanTotalPremium(6000.0);

		ProductPremiumInfo premiumInfo6 = p.new ProductPremiumInfo();
		premiumInfo6.setAgentTotalPremium(7000.0);
		premiumInfo6.setProductName("MOBILE PLANT");
		premiumInfo6.setSalemanTotalPremium(7000.0);

		motorProductPremiumInfoList.add(premiumInfo);
		motorProductPremiumInfoList.add(premiumInfo1);
		motorProductPremiumInfoList.add(premiumInfo2);
		motorProductPremiumInfoList.add(premiumInfo3);
		motorProductPremiumInfoList.add(premiumInfo4);
		motorProductPremiumInfoList.add(premiumInfo5);
		motorProductPremiumInfoList.add(premiumInfo6);
		return motorProductPremiumInfoList;
	}
}
