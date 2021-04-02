//package org.ace.insurance.report.test.life;
//
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.sf.jasperreports.engine.JREmptyDataSource;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperExportManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//
//import org.ace.insurance.common.Name;
//import org.ace.insurance.common.Utils;
//import org.ace.insurance.life.proposal.InsuredPersonBeneficiaries;
//import org.ace.insurance.life.proposal.ProposalInsuredPerson;
//import org.ace.insurance.payment.service.interfaces.IPaymentService;
//import org.ace.insurance.report.life.LifeProposalCriteria;
//import org.ace.insurance.report.life.LifeProposalReport;
//import org.ace.insurance.report.life.service.interfaces.ILifeProposalReportService;
//import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class LifeProposalReportTest {
//	private static Logger logger = LogManager.getLogger(LifeProposalReport.class);
//	private static ILifeProposalReportService lifeProposalReportService;
//	private static IPaymentService paymentService;
//
//	@BeforeClass
//	public static void init() {
//		logger.info("LifeProposalReportTest is started.........................................");
//		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
//		BeanFactory factory = context;
//		lifeProposalReportService = (ILifeProposalReportService) factory.getBean("LifeProposalReportService");
//		logger.info("LifeProposalReportTest instance has been loaded.");
//	}
//
//	public static void main(String[] args) {
//		org.junit.runner.JUnitCore.main(LifeProposalReportTest.class.getName());
//	}
//
//	// @Test
//	public void test() {
//		try {
//			LifeProposalCriteria criteria = new LifeProposalCriteria();
//			criteria.setStartDate(Utils.getDate("31-07-2013"));
//			criteria.setEndDate(Utils.getDate("01-08-2013"));
//			List<LifeProposalReport> lifeProposalList = lifeProposalReportService.findLifeProposal(criteria);
//			Map paramMap = new HashMap();
//			paramMap.put("LifeProposals", new JRBeanCollectionDataSource(lifeProposalList));
//			paramMap.put("totalSumInsured", getTotalSumInsured(lifeProposalList));
//			InputStream inputStream = new FileInputStream("report-template/life/lifeProposalReport.jrxml");
//			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
//			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
//			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifeProposalReport.pdf");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void prepareTest() {
//		try {
//
//			List<LifeProposalReport> LifeProposalReportList = new ArrayList<LifeProposalReport>();
//			LifeProposalReport lifeProposalReport = new LifeProposalReport();
//			lifeProposalReport.setPorposalNo("proposalNo");
//			lifeProposalReport.setProposalDate(new Date());
//			lifeProposalReport.setCustomerName("customerName");
//			lifeProposalReport.setFatherName("fatherName");
//			lifeProposalReport.setSumInsured(1000.0);
//			lifeProposalReport.setAgentName("agentName");
//			lifeProposalReport.setAgentNo("agentNo");
//			lifeProposalReport.setCustomerAddress("customerAddress");
//			lifeProposalReport.setPhNo("phNo");
//			lifeProposalReport.setSubTotal(1000.0);
//			List<ProposalInsuredPerson> proposalInsuredPersonList = new ArrayList<ProposalInsuredPerson>();
//			ProposalInsuredPerson proposalInsuredPerson = new ProposalInsuredPerson();
//			proposalInsuredPersonList.add(proposalInsuredPerson);
//
//			List<InsuredPersonBeneficiaries> insuredPersonBeneficiaryList = new ArrayList<InsuredPersonBeneficiaries>();
//			InsuredPersonBeneficiaries insuredPersonBeneficiaries = new InsuredPersonBeneficiaries();
//			Name name = new Name();
//			name.setFirstName("name");
//			insuredPersonBeneficiaries.setName(name);
//			insuredPersonBeneficiaries.setIdNo("idNo");
//			insuredPersonBeneficiaryList.add(insuredPersonBeneficiaries);
//
//			proposalInsuredPerson.setInsuredPersonBeneficiariesList(insuredPersonBeneficiaryList);
//			lifeProposalReport.setInsuredPersonList(proposalInsuredPersonList);
//			LifeProposalReportList.add(lifeProposalReport);
//
//			Map paramMap = new HashMap();
//			paramMap.put("LifeProposals", new JRBeanCollectionDataSource(LifeProposalReportList));
//			paramMap.put("totalSumInsured", 10000.0);
//			InputStream inputStream = new FileInputStream("report-template/life/lifeProposalReport.jrxml");
//			JasperReport jreport = JasperCompileManager.compileReport(inputStream);
//			JasperPrint jprint = JasperFillManager.fillReport(jreport, paramMap, new JREmptyDataSource());
//			JasperExportManager.exportReportToPdfFile(jprint, "D:/temp/lifeProposalReport.pdf");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public double getTotalSumInsured(List<LifeProposalReport> lifeProposalList) {
//		double total = 0.0;
//		for (LifeProposalReport lifeProposalReport : lifeProposalList) {
//			total += lifeProposalReport.getSumInsured();
//		}
//		return total;
//	}
// }
