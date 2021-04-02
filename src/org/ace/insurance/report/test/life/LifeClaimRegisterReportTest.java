package org.ace.insurance.report.test.life;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ace.insurance.claimaccept.service.interfaces.IClaimAcceptedInfoService;
import org.ace.insurance.common.ResidentAddress;
import org.ace.insurance.life.claim.service.interfaces.ILifeClaimService;
import org.ace.insurance.life.proposal.service.interfaces.ILifeProposalService;
import org.ace.insurance.report.life.LifeClaimRegisterReport;
import org.ace.insurance.report.life.service.interfaces.ILifeClaimRegisterReportService;
import org.ace.insurance.system.common.branch.Branch;
import org.ace.insurance.system.common.branch.service.interfaces.IBranchService;
import org.ace.insurance.web.manage.enquires.EnquiryCriteria;
import org.ace.insurance.web.util.FileHandler;
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
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

public class LifeClaimRegisterReportTest {

	private static Logger logger = LogManager.getLogger(LifeClaimRegisterReportTest.class);
	private static ILifeProposalService lifeProposalService;
	private static ILifeClaimService lifeClaimService;
	private static IClaimAcceptedInfoService claimAcceptedInfoService;
	private static ILifeClaimRegisterReportService lifeClaimRegisterReportService;
	private static IBranchService branchService;
	private List<String> productIdList = new ArrayList<String>();

	@BeforeClass
	public static void init() {
		logger.info("LifeAcceptanceLetterTest is started.........................................");
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-beans.xml");
		BeanFactory factory = context;
		lifeClaimService = (ILifeClaimService) factory.getBean("LifeClaimService");
		lifeProposalService = (ILifeProposalService) factory.getBean("LifeProposalService");
		lifeClaimRegisterReportService = (ILifeClaimRegisterReportService) factory.getBean("LifeClaimRegisterReportService");
		claimAcceptedInfoService = (IClaimAcceptedInfoService) factory.getBean("ClaimAcceptedInfoService");
		branchService = (IBranchService) factory.getBean("BranchService");
		logger.info("LifeAcceptanceLetterTest instance has been loaded.");

	}

	@AfterClass
	public static void finished() {
		logger.info("LifeAcceptanceLetterTest has been finished.........................................");
	}

	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(LifeClaimRegisterReportTest.class.getName());

	}

	// @Test
	public void generateLifeClaimRegisterReport() {
		String fullTemplateFilePath = "report-template/life/lifeClaimRegisterReport.jrxml";
		List<Branch> branchList = branchService.findAllBranch();
		EnquiryCriteria criteria = new EnquiryCriteria();
		criteria.setEndDate(new Date());
		try {
			List jasperPrintList = new ArrayList();
			Map<String, Object> params = new HashMap<String, Object>();
			double grandTotal = 0.0;
			if (criteria.getBranch() == null) {
				for (Branch branch : branchList) {
					EnquiryCriteria enquiryCriteria = new EnquiryCriteria();
					enquiryCriteria.setEndDate(criteria.getEndDate());
					List<LifeClaimRegisterReport> reports = lifeClaimRegisterReportService.findLifeClaimRegisterReports(enquiryCriteria, productIdList);
					List<LifeClaimRegisterReport> temp = new ArrayList<LifeClaimRegisterReport>();

					double subTotal = 0.0;
					if (reports != null && !reports.isEmpty()) {
						for (LifeClaimRegisterReport report : reports) {
							subTotal = subTotal + report.getTotalSumInsured();
						}
						grandTotal = grandTotal + subTotal;
						for (LifeClaimRegisterReport report : reports) {
							// report.setSubTotal(subTotal);
							temp.add(report);
						}
						params.put("TableDataSource", new JRBeanCollectionDataSource(temp));
						params.put("grandTotal", grandTotal);
						params.put("branch", branch.getName());

						if (branchList.lastIndexOf(branch) == branchList.size() - 1) {
							params.put("lastIndex", true);
						}
						InputStream policyIS = new FileInputStream(fullTemplateFilePath);
						JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
						JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
						jasperPrintList.add(policyJP);
						JRExporter exporter = new JRPdfExporter();
						exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
						FileHandler.forceMakeDirectory("D:/temp/");
						exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/lifeClaimRegisterReport.pdf"));
						exporter.exportReport();
					}
				}
			} else {
				List<LifeClaimRegisterReport> reports = lifeClaimRegisterReportService.findLifeClaimRegisterReports(criteria, productIdList);
				List<LifeClaimRegisterReport> temp = new ArrayList<LifeClaimRegisterReport>();

				double subTotal = 0.0;
				if (reports != null && !reports.isEmpty()) {
					for (LifeClaimRegisterReport report : reports) {
						subTotal = subTotal + report.getTotalSumInsured();
					}
					grandTotal = grandTotal + subTotal;
					for (LifeClaimRegisterReport report : reports) {
						// report.setSubTotal(subTotal);
						temp.add(report);
					}
					params.put("TableDataSource", new JRBeanCollectionDataSource(temp));
					params.put("grandTotal", grandTotal);
					params.put("lastIndex", false);
					params.put("branch", criteria.getBranch().getName());

					InputStream policyIS = new FileInputStream(fullTemplateFilePath);
					JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
					JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
					jasperPrintList.add(policyJP);
					JRExporter exporter = new JRPdfExporter();
					exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
					FileHandler.forceMakeDirectory("D:/temp/");
					exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/lifeClaimRegisterReport.pdf"));
					exporter.exportReport();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void prepareGenerateLifeClaimRegisterReport() {
		String fullTemplateFilePath = "report-template/life/lifeClaimRegisterReport.jrxml";
		try {
			List jasperPrintList = new ArrayList();
			Map<String, Object> params = new HashMap<String, Object>();

			List<LifeClaimRegisterReport> temp = new ArrayList<LifeClaimRegisterReport>();
			ResidentAddress policyInsuredPersonAddress = new ResidentAddress();
			policyInsuredPersonAddress.setResidentAddress("residentAddress");
			LifeClaimRegisterReport lifeClaimRegisterReport = null;
			// lifeClaimRegisterReport.setSubTotal(1000.0);
			temp.add(lifeClaimRegisterReport);
			params.put("TableDataSource", new JRBeanCollectionDataSource(temp));
			params.put("grandTotal", 1000.0);
			params.put("lastIndex", true);
			params.put("branch", "branch");

			InputStream policyIS = new FileInputStream(fullTemplateFilePath);
			JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
			JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
			jasperPrintList.add(policyJP);
			JRExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
			FileHandler.forceMakeDirectory("D:/temp/");
			exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream("D:/temp/lifeClaimRegisterReport.pdf"));
			exporter.exportReport();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
