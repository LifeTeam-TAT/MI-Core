package org.ace.insurance.report.life.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.ace.insurance.report.life.LifePremiumPaymentCriteria;
import org.ace.insurance.report.life.LifePremiumPaymentReport;
import org.ace.insurance.report.life.persistence.interfaces.ILifePremiumPaymentReportDAO;
import org.ace.insurance.report.life.service.interfaces.ILifePremiumPaymentReportService;
import org.ace.insurance.web.util.FileHandler;
import org.ace.java.component.SystemException;
import org.ace.java.component.persistence.exception.DAOException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

@Service(value = "LifePremiumPaymentReportService")
public class LifePremiumPaymentReportService implements ILifePremiumPaymentReportService {
	@Resource(name = "LifePremiumPaymentReportDAO")
	private ILifePremiumPaymentReportDAO premiumPaymentReportDAO;

	@Transactional(propagation = Propagation.REQUIRED)
	public List<LifePremiumPaymentReport> findPremiumPayment(LifePremiumPaymentCriteria premiumPaymentCriteria, List<String> productIdList) {
		List<LifePremiumPaymentReport> result = null;
		try {
			result = premiumPaymentReportDAO.find(premiumPaymentCriteria, productIdList);
		} catch (DAOException e) {
			throw new SystemException(e.getErrorCode(), "Faield to find PremiumPaymentReport by criteria.", e);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generateLifePremiumPaymentReport(List<LifePremiumPaymentReport> premiumPaymentList, String dirPath, String fileName, String branchName) throws Exception {
		List jasperPrintList = new ArrayList();
		Map<String, Object> params = new HashMap<String, Object>();
		double totalSumInsured = 0.0;
		double totalIncome = 0.0;
		double totalPremium = 0.0;
		for (LifePremiumPaymentReport report : premiumPaymentList) {
			totalSumInsured += report.getSumInsured();
			totalIncome += report.getIncome();
			totalPremium += report.getOneYearPremium();
		}
		params.put("TableDataSource", new JRBeanCollectionDataSource(premiumPaymentList));
		params.put("totalSumInsured", totalSumInsured);
		params.put("totalIncome", totalIncome);
		params.put("totalPremium", totalPremium);
		params.put("branch", branchName);
		InputStream policyIS = Thread.currentThread().getContextClassLoader().getResourceAsStream("report-template/life/lifePremiumPaymentReport.jrxml");
		JasperReport policyJR = JasperCompileManager.compileReport(policyIS);
		JasperPrint policyJP = JasperFillManager.fillReport(policyJR, params, new JREmptyDataSource());
		jasperPrintList.add(policyJP);
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		FileHandler.forceMakeDirectory(dirPath);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, new FileOutputStream(dirPath + fileName));
		exporter.exportReport();

	}

}
