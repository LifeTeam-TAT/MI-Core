package org.ace.insurance.report.test.life;

/**
 * @author NNH
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.report.life.GroupLifeProposalReport;
import org.ace.insurance.report.life.service.interfaces.IGroupLifeProposalReportService;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

public class GroupLifeProposalReportTest {
	private static Logger logger = LogManager.getLogger(GroupLifeProposalReport.class);
	private static IGroupLifeProposalReportService groupLifeProposalReportService;
	private static IBranchService branchService;

	@BeforeClass
	public static void init() {
		logger.info("GroupLifeProposalReportTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		groupLifeProposalReportService = (IGroupLifeProposalReportService) factory.getBean("GroupLifeProposalReportService");
		branchService = (IBranchService) factory.getBean("BranchService");
		logger.info("MotorProposalReportTest instance has been loaded.");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(GroupLifeProposalReportTest.class.getName());
	}

	@Test
	public void prepareTest() {
		try {
			List<GroupLifeProposalReport> mpReportList = new ArrayList<GroupLifeProposalReport>();
			GroupLifeProposalReport groupLifeProposalReport = new GroupLifeProposalReport();
			groupLifeProposalReport.setProposalNo("porposalNo");
			groupLifeProposalReport.setInPersonGroupCodeNo("inPersonGroupCodeNo");
			groupLifeProposalReport.setAgentNameAndCode("agentNameAndCode");
			groupLifeProposalReport.setInsuredpersonName("insuredpersonName");
			groupLifeProposalReport.setAddressAndPhoneNo("addressAndPhoneNo");
			groupLifeProposalReport.setSumInsured(7.00);
			groupLifeProposalReport.setBasicPremium(7.00);
			groupLifeProposalReport.setBranch("branch");
			mpReportList.add(groupLifeProposalReport);
			Map paramMap = new HashMap();
			paramMap.put("grandTotal", 1000.0);
			paramMap.put("grandTotalPremium", 1000.0);
			paramMap.put("lastIndex", true);
			paramMap.put("branch", "branch");
			paramMap.put("LifeProposals", new JRBeanCollectionDataSource(mpReportList));

			InputStream inputStream = new FileInputStream("report-template/life/groupLifeProposalReport.jrxml");
			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/groupLifeProposalReport.pdf");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getTotalReportSumInsured(List<GroupLifeProposalReport> groupLifeProposalList) {
		double total = 0.0;
		for (GroupLifeProposalReport groupLifeProposalReport : groupLifeProposalList) {
			total += groupLifeProposalReport.getSumInsured();
		}
		return total;

	}

	public double getTotalReportPremiun(List<GroupLifeProposalReport> groupLifeProposalList) {
		double total = 0.0;
		for (GroupLifeProposalReport groupLifeProposalReport : groupLifeProposalList) {
			total += groupLifeProposalReport.getBasicPremium();
		}
		return total;

	}

}
